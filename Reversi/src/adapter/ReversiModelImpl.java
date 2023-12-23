package adapter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import game.utility.GameState;
import game.utility.ICell;
import game.utility.PlayerState;
import player.Player;
import player.PlayerColor;
import provider.cs3500.reversi.model.CustomPoint2D;
import provider.cs3500.reversi.model.ModelFeatures;
import provider.cs3500.reversi.model.PlayerTile;
import provider.cs3500.reversi.model.ReversiModel;

import static game.utility.GameState.BLACK_WIN;
import static game.utility.GameState.WHITE_TURN;
import static game.utility.GameState.WHITE_WIN;


/**
 * The ReversiModel Adapter for the provider's code.
 * This acts as the bridge between our model and the provider's model.
 */
public class ReversiModelImpl implements ReversiModel {
  private Map<CustomPoint2D, PlayerTile> board;
  private int boardSize;
  private game.ReversiModel adapt;
  private Player player;
  ModelFeatures listener;


  /**
   * The model adapter constructor.
   *
   * @param adapt - our model.
   * @param size  - the given size.
   */
  public ReversiModelImpl(game.ReversiModel adapt, int size) {
    this.adapt = adapt;
    this.boardSize = size;
    this.board = new HashMap<>();
    this.listener = null;
  }


  @Override
  public PlayerTile getTurn() {
    return convertPlayerStateToPlayerTile(adapt.getCurrentState());
  }


  private PlayerTile convertPlayerStateToPlayerTile(GameState playerState) {
    if (playerState.equals(GameState.BLACK_TURN)) {
      return PlayerTile.FIRST;
    } else if (player.equals(WHITE_TURN)) {
      return PlayerTile.SECOND;
    }
    throw new IllegalArgumentException("Unknown player state");
  }


  @Override
  public PlayerTile getTileAt(CustomPoint2D point) {
    ICell cell = adapt.getCell(point.getDim2(), point.getDim1());
    return returnPlayerStateOfThisCell(cell);
  }

  private PlayerTile returnPlayerStateOfThisCell(ICell cell) {
    PlayerState player = cell.getPlayer();
    if (player.equals(PlayerState.BLACK)) {
      return PlayerTile.FIRST;
    } else {
      return PlayerTile.SECOND;
    }
  }


  @Override
  public boolean isGameOver() {
    return adapt.isGameOver();
  }

  @Override
  public PlayerTile whoIsWinning() {
    GameState winner = adapt.returnWinner();
    return getWinner(winner);
  }

  private PlayerTile getWinner(GameState winner) {
    if (winner.equals(BLACK_WIN)) {
      return PlayerTile.FIRST;
    } else if (winner.equals(WHITE_WIN)) {
      return PlayerTile.SECOND;
    }
    throw new IllegalArgumentException("No winning");
  }

  @Override
  public boolean playerCanMove(PlayerTile t) {
    return adapt.hasValidMoves();
  }

  @Override
  public Set<CustomPoint2D> playerMoves(PlayerTile t) {
    Set<CustomPoint2D> validMoves = new HashSet<>();
    GameState gameState = convertPlayerTileToGameState(t);

    // Iterate through all cells on the board
    for (ICell cell : adapt.getBoard()) {
      if (cell.getPlayer() == PlayerState.EMPTY && adapt.isValidMove(cell, gameState)) {
        validMoves.add(new CustomPoint2DImpl(cell.getR(), cell.getQ()));
      }
    }

    return validMoves;
  }

  private GameState convertPlayerTileToGameState(PlayerTile t) {
    if (t == PlayerTile.FIRST) {
      return GameState.BLACK_TURN;
    } else if (t == PlayerTile.SECOND) {
      return WHITE_TURN;
    }
    throw new IllegalArgumentException("Invalid player tile");
  }

  @Override
  public int playerScore(PlayerTile t) {
    return getPlayerScoreFromAdapter(t);
  }

  private int getPlayerScoreFromAdapter(PlayerTile t) {
    int[] scores = adapt.getScores();
    switch (t) {
      case FIRST:
        return scores[0];
      case SECOND:
        return scores[1];
      default:
    }
    return 0;
  }

  @Override
  public boolean isSpotEmpty(CustomPoint2D point) {
    ICell cell = adapt.getCell(point.getDim2(), point.getDim1());
    return cell.getPlayer().equals(PlayerState.EMPTY);
  }

  @Override
  public int getBoardSize() {
    return adapt.getSize() * 2 + 1;
  }

  @Override
  public int getRowWidth(int row) {
    if (row >= -this.boardSize && row <= this.boardSize) {
      return this.boardSize + (this.boardSize - Math.abs(row)) + 1;
    } else {
      return 0; // Return 0 for rows outside the valid range
    }
  }


  @Override
  public Map<CustomPoint2D, PlayerTile> getBoard() {
    Set<ICell> ourBoard = adapt.getBoard();
    for (ICell cell : ourBoard) {
      CustomPoint2D point = new CustomPoint2DImpl(cell.getR(), cell.getQ());
      PlayerTile tile = returnPlayerStateOfThisCell(cell);
      board.put(point, tile);
    }
    return board;
  }

  @Override
  public int getScoreIfMovePlayed(CustomPoint2D moveToPlay, PlayerTile turn) {
    Map<CustomPoint2D, PlayerTile> copy = new HashMap<>(board);
    return calculateScore(copy, turn); // Calculate and return the score
  }

  private int calculateScore(Map<CustomPoint2D, PlayerTile> theBoard, PlayerTile turn) {
    int score = 0;
    for (PlayerTile tile : board.values()) {
      if (tile == turn) {
        score++;
      }
    }
    return score;
  }

  @Override
  public void startGame() {
    adapt.gameStarted();
  }


  @Override
  public void placeTile(CustomPoint2D point, PlayerTile t) {
    GameState gameState = convertPlayerTileToGameState(t);

    ICell cell = adapt.getCell(point.getDim2(), point.getDim1());

    if (adapt.isValidMove(cell, gameState)) {
      if (t == PlayerTile.FIRST) {
        cell.setPlayer(PlayerState.BLACK);
      } else if (t == PlayerTile.SECOND) {
        cell.setPlayer(PlayerState.WHITE);
      }
      adapt.makeMove(cell);
    }
  }

  @Override
  public void pass(PlayerTile t) {
    adapt.wannaPass();
  }

  @Override
  public void subscribeForTurnNotifs(ModelFeatures listener) {
    this.listener = listener;
  }

  /**
   * Set the player for this model.
   *
   * @param player - this player.
   */
  public void setPlayer(Player player) {
    if (player.getColor().equals(PlayerColor.WHITE)) {
      adapt.setWhitePlayer(player);
    } else {
      adapt.setBlackPlayer(player);
    }
  }

}
