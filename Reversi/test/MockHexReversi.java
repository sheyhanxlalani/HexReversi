
import java.util.Set;

import game.utility.Cell;
import game.utility.GameState;
import game.type.HexReversi;
import game.utility.ICell;
import game.ReadonlyReversiModel;
import player.Player;

/**
 * A Mock HexReversi model used to test.
 */
public class MockHexReversi extends HexReversi implements ReadonlyReversiModel {
  private final StringBuilder log;

  private Player currentPlayer;

  /**
   * Constructor for the mock hex reversi game.
   */
  public MockHexReversi(int size, StringBuilder log) {
    super(size);
    this.log = log;
  }

  @Override
  public void gameStarted() {
    log.append("Game is started.\n");
    boolean isGameStarted = true;
  }

  @Override
  public Set<ICell> getBoard() {
    log.append("Strategy retrieved the board: \n");
    return super.getBoard();
  }

  @Override
  public void makeMove(ICell clicked) {
    log.append("Try to make move: " + clicked.getQ() + ", " + clicked.getR() + "\n");
  }


  @Override
  public boolean isValidMove(ICell cell, GameState player) {
    log.append("Checking move: ").append(cell.getQ() + ", ").append(cell.getR()).append("\n");
    return super.isValidMove(cell, player);
  }

  @Override
  public int getCaptureCells(ICell cell, GameState player) {
    log.append("Get captured cells for: ").append(cell.getQ() + ", " + cell.getR()).append("\n");
    return super.getCaptureCells(cell, player);
  }

  @Override
  public int getCountPass() {
    return 0;
  }

  @Override
  public Player getCurrentPlayer() {
    log.append("get current player: " + currentPlayer.getColor().toString()).append("\n");
    return currentPlayer;
  }

  /**
   * Setting the current player for this game. Use to test.
   * @param player - player that want to be current player.
   */
  public void setCurrentPlayer(Player player) {
    this.currentPlayer = player;
  }

  @Override
  public boolean isGameOver() {
    log.append("Game is over");
    return true;
  }

  @Override
  public Cell getCell(int q, int r) {
    log.append("Get cell: " + q + ", " + r);
    return new Cell(q, r);
  }
}

