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
 * Represent a square reversi game. Implements all the logic of the game here.
 * As well as the initialize board, which is different from the hexreversi game.
 */
public class SquareReversi implements ReversiModel {
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
   * Constructor.
   *
   * @param size - the given size.
   */
  public SquareReversi(int size) {
    if (size <= 0) {
      throw new IllegalArgumentException("Size cannot be zero or negative");
    }
    this.size = size;
    this.board = new HashSet<>();
    this.blackHintsEnabled = false;
    this.whiteHintsEnabled = false;
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


  // check if game board is full.
  private boolean gameboardFull() {
    for (ICell cell : board) {
      if (cell.getPlayer() == PlayerState.EMPTY) {
        return false;
      }
    }
    return true;
  }


  private boolean noMoreValidMovesForCurrentPlayer() {
    for (ICell cell : board) {
      if (isValidMove(cell, this.currentState)) {
        return false;
      }
    }
    return true;
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
  public boolean cellExists(int q, int r) {
    for (ICell cell : board) {
      if (cell.getQ() == q && cell.getR() == r) {
        return true;
      }
    }
    return false;
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

  @Override
  public boolean isValidMove(ICell cell, GameState player) {
    if (cell.getPlayer() != PlayerState.EMPTY) {
      return false;
    }

    for (int direction = 0; direction < 8; direction++) {
      if (hasFlippableDiscsInDirection(cell, direction)) {
        return true;
      }
    }
    return false;
  }

  private boolean hasFlippableDiscsInDirection(ICell cell, int direction) {
    // Define the direction deltas for the square grid
    int dq = 0;
    int dr = 0;
    switch (direction) {
      case 0: // North
        dq = 0;
        dr = -1;
        break;
      case 1: // North-East
        dq = 1;
        dr = -1;
        break;
      case 2: // East
        dq = 1;
        dr = 0;
        break;
      case 3: // South-East
        dq = 1;
        dr = 1;
        break;
      case 4: // South
        dq = 0;
        dr = 1;
        break;
      case 5: // South-West
        dq = -1;
        dr = 1;
        break;
      case 6: // West
        dq = -1;
        dr = 0;
        break;
      case 7: // North-West
        dq = -1;
        dr = -1;
        break;
      default:
    }

    int q = cell.getQ() + dq;
    int r = cell.getR() + dr;
    boolean hasOpponentCell = false;

    // Traverse in the specified direction
    while (cellExists(q, r)) {
      ICell nextCell = getCell(q, r);
      if (nextCell.getPlayer() == PlayerState.EMPTY) {
        return false;
      } else if (isCellOfCurrentPlayer(nextCell)) {
        return hasOpponentCell;
      } else {
        hasOpponentCell = true;
      }
      q += dq;
      r += dr;
    }
    return false;
  }

  @Override
  public int getCaptureCells(ICell cell, GameState player) {
    int cellCaptured = 0;

    for (int direction = 0; direction < 8; direction++) {
      cellCaptured += countCapturedInDirection(cell, player, direction);
    }
    return cellCaptured;
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
  public boolean isGameStarted() {
    return isGameStarted;
  }


  private int countCapturedInDirection(ICell cell, GameState player, int direction) {
    int captured = 0;
    PlayerState currentPlayer = getPlayerStateFromGameState(player);

    int dq = 0;
    int dr = 0;
    switch (direction) {
      case 0:
        dq = 0;
        dr = -1;
        break; // North
      case 1:
        dq = 1;
        dr = -1;
        break; // Northeast
      case 2:
        dq = 1;
        dr = 0;
        break;  // East
      case 3:
        dq = 1;
        dr = 1;
        break;  // Southeast
      case 4:
        dq = 0;
        dr = 1;
        break;  // South
      case 5:
        dq = -1;
        dr = 1;
        break; // Southwest
      case 6:
        dq = -1;
        dr = 0;
        break; // West
      case 7:
        dq = -1;
        dr = -1;
        break;// Northwest
      default:
    }

    int q = cell.getQ() + dq;
    int r = cell.getR() + dr;
    boolean opponentFound = false;

    while (cellExists(q, r)) {
      ICell nextCell = getCell(q, r);
      if (nextCell.getPlayer() == currentPlayer) {
        if (opponentFound) {
          return captured;
        } else {
          break;
        }
      } else if (nextCell.getPlayer() == PlayerState.EMPTY) {
        break;
      } else {
        opponentFound = true;
        captured++;
      }

      q += dq;
      r += dr;
    }
    return 0;
  }

  @Override
  public ReversiModel getClone() {
    return new SquareReversi(this.getSize());
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

  @Override
  public void gameStarted() {
    this.initializeBoard();
    this.isGameStarted = true;
    this.currentState = GameState.BLACK_TURN;
    this.currentPlayer = blackPlayer;
    this.notifyTurnChanged(currentPlayer.getColor());
  }


  private void notifyTurnChanged(PlayerColor playerColor) {
    for (ModelActionListener listener : listeners) {
      listener.onPlayerTurnChanged(playerColor);
    }
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
  public void initializeBoard() {
    this.board.clear();
    int halfSize = this.getSize() / 2;

    ICell topLeft = new Cell(halfSize - 1, halfSize - 1);
    ICell topRight = new Cell(halfSize, halfSize - 1);
    ICell bottomLeft = new Cell(halfSize - 1, halfSize);
    ICell bottomRight = new Cell(halfSize, halfSize);

    topLeft.setPlayer(PlayerState.BLACK);
    topRight.setPlayer(PlayerState.WHITE);
    bottomLeft.setPlayer(PlayerState.WHITE);
    bottomRight.setPlayer(PlayerState.BLACK);

    this.board.add(topLeft);
    this.board.add(topRight);
    this.board.add(bottomLeft);
    this.board.add(bottomRight);

    // Fill the rest of the board with empty cells.
    for (int q = 0; q < this.getSize(); q++) {
      for (int r = 0; r < this.getSize(); r++) {
        // Skip the cells that we have already initialized.
        if ((q == halfSize - 1 || q == halfSize) && (r == halfSize - 1 || r == halfSize)) {
          continue;
        }
        this.board.add(new Cell(q, r));
      }
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
    flipDiffColorDiscs(clicked);


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


  private void flipDiffColorDiscs(ICell clicked) {
    // The 8 directions in a square grid
    int[][] directions = {
            {0, 1},   // North
            {1, 1},   // Northeast
            {1, 0},   // East
            {1, -1},  // Southeast
            {0, -1},  // South
            {-1, -1}, // Southwest
            {-1, 0},  // West
            {-1, 1}   // Northwest
    };

    for (int[] dir : directions) {
      int dq = dir[0];
      int dr = dir[1];

      int q = clicked.getQ() + dq;
      int r = clicked.getR() + dr;

      List<ICell> discsToFlip = new ArrayList<>();

      while (q >= -this.getSize() && q <= this.getSize()
              && r >= -this.getSize() && r <= this.getSize()) {
        if (!cellExists(q, r)) {
          break;
        }
        ICell cell = getCell(q, r);

        if (cell.getPlayer() == PlayerState.EMPTY) {
          break;
        }

        if (isCellOfCurrentPlayer(cell)) {
          for (ICell cellToFlip : discsToFlip) {
            cellToFlip.flip();
          }
          break;
        }

        discsToFlip.add(cell);
        q += dq;
        r += dr;
      }
    }
  }

  private boolean isCellOfCurrentPlayer(ICell cell) {
    if (currentState == GameState.BLACK_TURN) {
      return cell.getPlayer() == PlayerState.BLACK;
    } else {
      return cell.getPlayer() == PlayerState.WHITE;
    }
  }


  @Override
  public boolean hasValidMoves() {

    for (int q = -this.getSize(); q <= this.getSize(); q++) {
      for (int r = -this.getSize(); r <= this.getSize(); r++) {
        if (cellExists(q, r)) {
          ICell cell = getCell(q, r);
          if (cell.getPlayer() == PlayerState.EMPTY
                  && canCaptureInAnyDirection(cell, this.getCurrentState())) {
            return true;
          }
        }
      }
    }
    return false;
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
  public void addModelListener(ModelActionListener listener) {
    this.listeners.add(listener);
  }

  @Override
  public Player getCurrentPlayer() {
    return currentPlayer;
  }

  private boolean canCaptureInAnyDirection(ICell cell, GameState playerState) {
    int[][] directions = {
            {0, 1},   // North
            {1, 1},   // Northeast
            {1, 0},   // East
            {1, -1},  // Southeast
            {0, -1},  // South
            {-1, -1}, // Southwest
            {-1, 0},  // West
            {-1, 1}   // Northwest
    };

    for (int[] dir : directions) {
      if (canCaptureInDirection(cell, playerState, dir[0], dir[1])) {
        return true;
      }
    }
    return false;
  }

  private boolean canCaptureInDirection(ICell cell, GameState playerState, int dq, int dr) {
    int q = cell.getQ() + dq;
    int r = cell.getR() + dr;
    boolean hasOpponentCell = false;

    while (q >= -this.getSize() && q <= this.getSize()
            && r >= -this.getSize() && r <= this.getSize()) {
      if (!cellExists(q, r)) {
        break;
      }
      ICell nextCell = getCell(q, r);
      PlayerState opponent = (playerState == GameState.BLACK_TURN)
              ? PlayerState.WHITE : PlayerState.BLACK;

      if (nextCell.getPlayer() == PlayerState.EMPTY) {
        break;
      } else if (nextCell.getPlayer() == opponent) {
        hasOpponentCell = true;
      } else if (hasOpponentCell && nextCell.getPlayer()
              == getPlayerStateFromGameState(playerState)) {
        return true;
      } else {
        break;
      }

      q += dq;
      r += dr;
    }

    return false; // Can't capture in this direction
  }

  private PlayerState getPlayerStateFromGameState(GameState gameState) {
    switch (gameState) {
      case BLACK_TURN:
        return PlayerState.BLACK;
      case WHITE_TURN:
        return PlayerState.WHITE;
      default:
        throw new IllegalArgumentException("Invalid game state for player conversion: "
                + gameState);
    }
  }

}
