package game.type;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import controller.ModelActionListener;
import game.utility.Cell;
import game.utility.GameState;
import game.utility.ICell;
import game.utility.PlayerState;
import game.ReversiModel;
import player.Player;
import player.PlayerColor;


/**
 * Represent a hexagon version of the reversi game implemented in Java,
 * This class is a part of the 'game package and offers a hexagon version of the
 * traditional Reversi game instead of a square one.
 * It implements the "ReversiMode" interface and is responsible for managing the game's
 * state, players, moves, and rules.
 * This model supports for 2 players, differentiated by their disc colors (black and white)
 */
public class HexReversi implements ReversiModel {
  private final int size; // the size of the game board
  public Set<ICell> board; // the game board
  private boolean isGameStarted; // check if game is started
  // track the numbers of move in the game
  private GameState currentState; // keep track of the current player.
  public int countPass; // keep track of pass times.
  private ModelActionListener modelListener;
  private Player currentPlayer;
  private Player blackPlayer;
  private Player whitePlayer;
  private List<ModelActionListener> listeners = new ArrayList<>();
  private boolean blackHintsEnabled;
  private boolean whiteHintsEnabled;


  /**
   * HexReversi constructor.
   *
   * @param size - size of the board?.
   */
  public HexReversi(int size) {
    // Below is a class invariant it ensures that the size of the board cannot be 0 or negative
    // Size of the board is remained unchanged throughout the game and this makes sure that
    // the size of cannot be 0 or negative.
    if (size <= 0) {
      throw new IllegalArgumentException("Size cannot be zero or negative");
    }
    this.size = size;
    this.board = new HashSet<>();
    this.blackHintsEnabled = false;
    this.whiteHintsEnabled = false;


  }

  /**
   * Convenience constructor.
   */
  public HexReversi(Set<ICell> board, int size) {
    this(size);
    this.board = board;
  }

  @Override
  public Set<ICell> getBoard() {
    Set<ICell> copy = new HashSet<>();
    for (ICell cell : board) {
      copy.add(cell);
    }
    return copy;
  }

  @Override
  public void gameStarted() {
    this.initializeBoard();
    this.isGameStarted = true;
    this.currentState = GameState.BLACK_TURN;
    this.currentPlayer = blackPlayer;
    this.notifyTurnChanged(currentPlayer.getColor());


    getCell(0, 0).setPlayer(PlayerState.EMPTY); // Center cell is empty
    getCell(0, 1).setPlayer(PlayerState.WHITE);
    getCell(-1, 1).setPlayer(PlayerState.BLACK);
    getCell(-1, 0).setPlayer(PlayerState.WHITE);
    getCell(0, -1).setPlayer(PlayerState.BLACK);
    getCell(1, -1).setPlayer(PlayerState.WHITE);
    getCell(1, 0).setPlayer(PlayerState.BLACK);
  }


  // Creates the hexagonal game board by adding cells layer by layer in a spiral manner.
  // uses the cubic coordinate system to determine each cell's position.
  @Override
  public void initializeBoard() {
    this.board.clear();
    // track the number of cells in the board.
    int numCells = 0;

    for (int r = -size; r <= 0; r++) {
      board.add(new Cell(0, r));
    }

    for (int r = -size; r <= size; r++) {
      int q1 = Math.max(-size, -r - size);
      int q2 = Math.min(size, -r + size);
      for (int q = q1; q <= q2; q++) {
        if (q != 0 || r > 0) {  // Avoid duplicating the cells added in the first loop
          board.add(new Cell(q, r));
          numCells++;
        }
      }
    }
  }


  @Override
  public void addModelListener(ModelActionListener listener) {
    this.listeners.add(listener);
  }

  /**
   * Notify the controller about the change in the player's turn.
   *
   * @param playerColor The color of the player whose turn it is now.
   */
  private void notifyTurnChanged(PlayerColor playerColor) {
    for (ModelActionListener listener : listeners) {
      listener.onPlayerTurnChanged(playerColor);
    }
  }


  @Override
  public void makeMove(ICell clicked) {
    //check if the game is started.
    if (!isGameStarted) {
      throw new IllegalStateException("Game has not started, can't make a move.");
    }

    // ensure the clicked cell is not null
    if (clicked == null) {
      throw new IllegalStateException("Cell cannot be null");
    }

    // ensure the clicked cell is not empty
    if (clicked.getPlayer() != PlayerState.EMPTY) {
      throw new IllegalStateException("Cell is not empty");
    }

    // ensure that the move to the clicked cell is valid
    if (!isValidMove(clicked, this.getCurrentState())) {
      throw new IllegalArgumentException("Invalid move");
    }

    // set the player for the clicked cell.
    if (currentState == GameState.BLACK_TURN) {
      clicked.setPlayer(PlayerState.BLACK);
    } else {
      clicked.setPlayer(PlayerState.WHITE);
    }

    // flip the different-colored discs in all 6 directions of that clicked cell.
    for (int direction = 0; direction < 6; direction++) {
      flipDiffColorDiscs(clicked, direction);
    }

    for (ModelActionListener listener : listeners) {
      if (listener != null) {
        listener.onScoreUpdate();
      }
    }
    // switch the current player
    switchPlayer();

  }

  @Override
  public void setModelListener(ModelActionListener listener) {
    this.modelListener = listener;
  }

  @Override
  public boolean isGameStarted() {
    return isGameStarted;
  }

  @Override
  public ReversiModel getClone() {
    return new HexReversi(this.getBoard(), this.getSize());
  }

  // if it is possible, flip all the discs that have different colors in this direction.
  private void flipDiffColorDiscs(ICell clicked, int direction) {
    int dq = 0;
    int dr = 0;
    if (direction == 0) {
      dq = 0;
      dr = 1;
    } else if (direction == 1) {
      dq = -1;
      dr = 1;
    } else if (direction == 2) {
      dq = -1;
      dr = 0;
    } else if (direction == 3) {
      dq = 0;
      dr = -1;
    } else if (direction == 4) {
      dq = 1;
      dr = -1;
    } else if (direction == 5) {
      dq = 1;
      dr = 0;
    }

    int q = clicked.getQ() + dq;
    int r = clicked.getR() + dr;

    List<ICell> discsToFlip = new ArrayList<>();

    while (q >= -size && q <= size && r >= -size && r <= size) {
      if (!cellExists(q, r)) {
        return;
      }
      ICell cell = getCell(q, r);

      if (cell.getPlayer() == PlayerState.EMPTY) {
        return;
      }

      if (isCellOfCurrentPlayer(cell)) {
        for (ICell cellToFlip : discsToFlip) {
          cellToFlip.flip();
        }
        return;  // flipped all the discs and can exit
      }

      discsToFlip.add(cell);
      q += dq;
      r += dr;

    }
  }

  @Override
  public boolean isValidMove(ICell cell, GameState player) {
    // ensure that the cell is empty and that the move is made by the current player
    if (cell.getPlayer() != PlayerState.EMPTY) {
      return false;
    }

    // check in all 6 directions if there are any opposing discs to flip
    for (int direction = 0; direction < 6; direction++) {
      if (wouldFlipOpposingDisks(cell, direction)) {
        return true;
      }
    }
    return false;
  }


  // check if we can flip the opposing disks in the given direction of the given cell
  private boolean wouldFlipOpposingDisks(ICell cell, int direction) {
    int dq = 0;
    int dr = 0;

    // directions to check based on the given direction
    if (direction == 0) {
      dq = 0;
      dr = 1;
    } else if (direction == 1) {
      dq = -1;
      dr = 1;
    } else if (direction == 2) {
      dq = -1;
      dr = 0;
    } else if (direction == 3) {
      dq = 0;
      dr = -1;
    } else if (direction == 4) {
      dq = 1;
      dr = -1;
    } else if (direction == 5) {
      dq = 1;
      dr = 0;
    }

    int q = cell.getQ() + dq;
    int r = cell.getR() + dr;
    boolean hasOpponentCell = false;

    while (cellExists(q, r)) { // Check if the cell exists
      ICell adjCell = getCell(q, r);
      if (adjCell.getPlayer() == PlayerState.EMPTY) {
        return false;
      } else if (isCellOfCurrentPlayer(adjCell)) {
        return hasOpponentCell;
      } else {
        hasOpponentCell = true;
      }
      q += dq;
      r += dr;
    }
    return false;
  }

  // check if the selected cell is the same color as the current player.
  private boolean isCellOfCurrentPlayer(ICell cell) {
    if (currentState == GameState.BLACK_TURN) {
      return cell.getPlayer() == PlayerState.BLACK;
    } else {
      return cell.getPlayer() == PlayerState.WHITE;
    }
  }

  @Override
  public int[] getScores() {
    int blackScore = 0;
    int whiteScore = 0;
    for (ICell cell : board) {
      if (cell.getPlayer() == PlayerState.BLACK) {
        blackScore++;
      } else if (cell.getPlayer() == PlayerState.WHITE) {
        whiteScore++;
      }
    }
    return new int[]{blackScore, whiteScore};
  }


  @Override
  public boolean isGameOver() throws IllegalStateException {
    if (!isGameStarted) {
      throw new IllegalStateException("Game cannot be over if game hasn't started");
    }

    // Check if either player has no discs left
    int blackDiscs = 0;
    int whiteDiscs = 0;
    for (ICell cell : board) {
      if (cell.getPlayer() == PlayerState.BLACK) {
        blackDiscs++;
      } else if (cell.getPlayer() == PlayerState.WHITE) {
        whiteDiscs++;
      }
    }
    if (blackDiscs == 0 || whiteDiscs == 0) {
      return true;
    }

    // Check if game board is full
    if (gameboardFull()) {
      return true;
    }

    // Check if both players have passed in succession
    if (countPass >= 2) {
      return true;
    }

    // Check if there are no more valid moves for the current player
    return noMoreValidMovesForCurrentPlayer();
  }

  @Override
  public GameState returnWinner() {
    if (isGameOver()) {
      int blackCount = 0;
      int whiteCount = 0;

      for (ICell cell : board) {
        if (cell.getPlayer() == PlayerState.BLACK) {
          blackCount++;
        } else if (cell.getPlayer() == PlayerState.WHITE) {
          whiteCount++;
        }
        if (blackCount > whiteCount) {
          return GameState.BLACK_WIN;
        } else if (blackCount < whiteCount) {
          return GameState.WHITE_WIN;
        }
      }
    }
    return GameState.TIE;
  }

  @Override
  public GameState getCurrentState() {
    return currentState;
  }

  private boolean noMoreValidMovesForCurrentPlayer() {
    for (ICell cell : board) {
      if (isValidMove(cell, this.currentState)) {
        return false;
      }
    }
    return true;
  }


  // check if game board is full.
  private boolean gameboardFull() {
    for (ICell cell : board) {
      if (cell.getPlayer() == PlayerState.EMPTY) {
        return false;
      }
    }
    return true;
  }

  @Override
  public void wannaPass() {
    if (!hasValidMoves()) {
      switchPlayer();
      countPass++;
    }
    if (modelListener != null) {
      modelListener.onScoreUpdate();
    }
  }

  @Override
  public int getCaptureCells(ICell cell, GameState player) {
    int cellCaptured = 0;

    for (int direction = 0; direction < 6; direction++) {
      cellCaptured += countCellCapturedInEachDirection(cell, player, direction);
    }
    return cellCaptured;
  }

  // count the cell captured in this direction.
  private int countCellCapturedInEachDirection(ICell cell,
                                               GameState player, int direction) {
    int cellCaptured = 0;
    int dq = 0;
    int dr = 0;

    switch (direction) {
      case 0:
        dq = 0;
        dr = 1;
        break;
      case 1:
        dq = -1;
        dr = 1;
        break;
      case 2:
        dq = -1;
        dr = 0;
        break;
      case 3:
        dq = 0;
        dr = -1;
        break;
      case 4:
        dq = 1;
        dr = -1;
        break;
      case 5:
        dq = 1;
        dr = 0;
        break;
      default:
    }
    int q = cell.getQ() + dq;
    int r = cell.getR() + dr;
    boolean hasOpponentCell = false;
    while (q >= -size && q <= size && r >= -size && r <= size) {
      if (!cellExists(q, r)) {
        return 0;
      }
      ICell nextCell = getCell(q, r);
      if (nextCell.getPlayer() == PlayerState.EMPTY) {
        break; // no opponent cell
      } else if (nextCell.getPlayer() == PlayerState.WHITE && player == GameState.BLACK_TURN
              || nextCell.getPlayer() == PlayerState.BLACK && player == GameState.WHITE_TURN) {
        hasOpponentCell = true;
        cellCaptured++;
      } else {
        if (hasOpponentCell) {
          return cellCaptured;
        }
        break; //found player's cell but no opponent
      }
      q += dq;
      r += dr;
    }
    return 0; // no flippable cell

  }

  @Override
  public boolean hasValidMoves() {
    for (ICell cell : board) {
      if (isValidMove(cell, this.currentState)) {
        return true;
      }
    }
    return false;
  }


  @Override
  public int getSize() {
    return this.size;
  }

  @Override
  public ICell getCell(int q, int r) {
    if (!isGameStarted) {
      throw new IllegalStateException("Game has not started cant get a cell");
    }

    if (!cellExists(q, r)) {
      System.out.println("this cell: " + q + ", " + r);
      throw new IllegalStateException("Cell does not exist: " + q + ", " + r);
    }

    for (ICell cell : board) {
      if (cell.getQ() == q && cell.getR() == r) {
        System.out.println("q: " + q + ", r: " + r);
        return cell;
      }
    }
    System.out.println("cell: " + q + " " + r);
    throw new IllegalArgumentException("No cell found");
  }

  @Override
  public void switchPlayer() {
    if (currentState == GameState.BLACK_TURN) {
      currentState = GameState.WHITE_TURN;
      currentPlayer = whitePlayer;
    } else {
      currentState = GameState.BLACK_TURN;
      currentPlayer = blackPlayer;
    }
    if (modelListener != null) {
      for (ModelActionListener listener : listeners) {
        listener.onPlayerTurnChanged(currentPlayer.getColor());
      }
    }
  }


  @Override
  public Player getCurrentPlayer() {
    return currentPlayer;
  }

  @Override
  public boolean cellExists(int q, int r) {
    for (ICell cell : board) {
      if (cell.getQ() == q && cell.getR() == r) {
        return true;
      }
    }
    return false;
  }

  @Override
  public void setBlackPlayer(Player player) {
    if (player.getColor() != PlayerColor.BLACK) {
      throw new IllegalArgumentException("Player color must be BLACK.");
    }
    blackPlayer = player;
  }

  @Override
  public void setWhitePlayer(Player player) {
    if (player.getColor() != PlayerColor.WHITE) {
      throw new IllegalArgumentException("Player color must be WHITE.");
    }
    whitePlayer = player;
  }

  @Override
  public void toggleBlackHints() {
    if (currentState == GameState.BLACK_TURN) {
      blackHintsEnabled = !blackHintsEnabled;
    } else {
      throw new IllegalStateException("Not your turn! Can't show hints for your opponents!");
    }
  }

  @Override
  public void toggleWhiteHints() {
    if (currentState == GameState.WHITE_TURN) {
      whiteHintsEnabled = !whiteHintsEnabled;
    } else {
      throw new IllegalStateException("Not your turn! Can't show hints for your opponents!");
    }
  }

  @Override
  public boolean isBlackHintsEnabled() {
    return blackHintsEnabled;
  }

  @Override
  public boolean isWhiteHintsEnabled() {
    return whiteHintsEnabled;
  }

  @Override
  public int getCountPass() {
    return countPass;
  }


}
