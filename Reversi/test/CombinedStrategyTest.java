import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

import game.type.BoardType;
import game.utility.Cell;
import game.utility.GameState;
import game.utility.ICell;
import game.utility.PlayerState;
import game.ReadonlyReversiModel;
import strategy.AvoidCornerCellStrategy;
import strategy.CombinedStrategy;
import strategy.CornerStrategy;
import strategy.MaxCaptureStrategy;
import strategy.MinimaxStrategy;
import strategy.ReversiStrategy;

/**
 * Represent a combined strategy for HexReversi.
 */
public class CombinedStrategyTest {
  @Test
  public void testCombinedMockStrategy() {
    StringBuilder log = new StringBuilder();

    ReadonlyReversiModel mockModel = new MockHexReversi(5, log); // Your mock game state
    GameState currentPlayer = GameState.BLACK_TURN;

    // Mock strategies
    ReversiStrategy mockStrategy1 = (model, player) -> Optional.empty();
    ReversiStrategy mockStrategy2 = (model, player) -> Optional.of(new Cell(0, 1));

    CombinedStrategy combinedStrategy = new CombinedStrategy();
    combinedStrategy.addStrategy(mockStrategy1);
    combinedStrategy.addStrategy(mockStrategy2);

    Optional<ICell> chosenMove = combinedStrategy.determineMove(mockModel, currentPlayer);

    Assert.assertTrue(chosenMove.isPresent());
    Assert.assertEquals(new Cell(0, 1), chosenMove.get());
  }

  @Test
  public void testNoValidMoves() {
    StringBuilder log = new StringBuilder();
    ReadonlyReversiModel mockModel = new MockHexReversi(5, log);
    GameState currentPlayer = GameState.WHITE_TURN;

    // Both mock strategies return no move
    ReversiStrategy mockStrategy1 = (model, player) -> Optional.empty();
    ReversiStrategy mockStrategy2 = (model, player) -> Optional.empty();

    CombinedStrategy combinedStrategy = new CombinedStrategy();
    combinedStrategy.addStrategy(mockStrategy1);
    combinedStrategy.addStrategy(mockStrategy2);

    Optional<ICell> chosenMove = combinedStrategy.determineMove(mockModel, currentPlayer);
    Assert.assertFalse(chosenMove.isPresent());
  }

  @Test
  public void testCombined4Strategies() {
    StringBuilder log = new StringBuilder();

    ReadonlyReversiModel mockModel = new MockHexReversi(5, log);
    GameState currentPlayer = GameState.BLACK_TURN;

    CombinedStrategy combinedStrategy = new CombinedStrategy();
    ReversiStrategy strategy1 = new MaxCaptureStrategy();
    ReversiStrategy strategy2 = new AvoidCornerCellStrategy(BoardType.HEXAGONAL);
    ReversiStrategy strategy3 = new CornerStrategy(BoardType.HEXAGONAL);
    ReversiStrategy strategy4 = new MinimaxStrategy(3);

    combinedStrategy.addStrategy(strategy1);
    combinedStrategy.addStrategy(strategy2);
    combinedStrategy.addStrategy(strategy3);
    combinedStrategy.addStrategy(strategy4);
    Optional<ICell> chosenMove = combinedStrategy.determineMove(mockModel, currentPlayer);
    Cell cell = new Cell(1, -2);
    Assert.assertEquals(cell.getQ(), chosenMove.get().getQ());
    Assert.assertEquals(cell.getR(), chosenMove.get().getR());
  }

  @Test(expected = IllegalStateException.class)
  public void testNoValidMoveForAnyStrategy() {
    StringBuilder log = new StringBuilder();

    ReadonlyReversiModel mock = new MockHexReversi(3, log);
    ICell cell1 = mock.getCell(0, -3);
    cell1.setPlayer(PlayerState.WHITE);
    ICell cell2 = mock.getCell(3, -3);
    cell2.setPlayer(PlayerState.WHITE);
    ICell cell3 = mock.getCell(3, 0);
    cell3.setPlayer(PlayerState.WHITE);
    ICell cell4 = mock.getCell(0, 3);
    cell4.setPlayer(PlayerState.WHITE);
    ICell cell5 = mock.getCell(-3, 3);
    cell5.setPlayer(PlayerState.WHITE);
    ICell cell6 = mock.getCell(-3, 0);
    cell6.setPlayer(PlayerState.WHITE);

    ICell cell7 = mock.getCell(-1, -1);
    cell7.setPlayer(PlayerState.WHITE);
    ICell cell8 = mock.getCell(1, -2);
    cell8.setPlayer(PlayerState.WHITE);
    ICell cell9 = mock.getCell(2, -1);
    cell9.setPlayer(PlayerState.WHITE);
    ICell cell10 = mock.getCell(1, 1);
    cell10.setPlayer(PlayerState.WHITE);
    ICell cell11 = mock.getCell(-2, 1);
    cell11.setPlayer(PlayerState.WHITE);
    ICell cell12 = mock.getCell(-1, 2);
    cell12.setPlayer(PlayerState.WHITE);

    ICell cell13 = mock.getCell(0, -1);
    cell13.setPlayer(PlayerState.WHITE);
    ICell cell14 = mock.getCell(1, -1);
    cell14.setPlayer(PlayerState.WHITE);
    ICell cell15 = mock.getCell(1, 0);
    cell15.setPlayer(PlayerState.WHITE);
    ICell cell16 = mock.getCell(0, 1);
    cell16.setPlayer(PlayerState.WHITE);
    ICell cell17 = mock.getCell(-1, 1);
    cell17.setPlayer(PlayerState.WHITE);
    ICell cell18 = mock.getCell(-1, 0);
    cell18.setPlayer(PlayerState.WHITE);
    GameState currentPlayer = GameState.BLACK_TURN;

    CombinedStrategy combinedStrategy = new CombinedStrategy();
    ReversiStrategy strategy1 = new MaxCaptureStrategy();
    ReversiStrategy strategy2 = new AvoidCornerCellStrategy(BoardType.HEXAGONAL);
    combinedStrategy.addStrategy(strategy1);
    combinedStrategy.addStrategy(strategy2);
    Optional<ICell> chosenMove = combinedStrategy.determineMove(mock, currentPlayer);
  }

  @Test
  public void testMaxCaptureAndCornerCombined() {
    StringBuilder log = new StringBuilder();

    ReadonlyReversiModel mock = new MockHexReversi(3, log);
    ICell cell1 = mock.getCell(-3, 0);
    cell1.setPlayer(PlayerState.WHITE);
    ICell cell2 = mock.getCell(-3, 1);
    cell2.setPlayer(PlayerState.BLACK);
    ICell cell3 = mock.getCell(-3, 2);
    cell3.setPlayer(PlayerState.BLACK);
    ICell cell4 = mock.getCell(-2, -1);
    cell4.setPlayer(PlayerState.BLACK);
    ICell cell5 = mock.getCell(-1, -2);
    cell5.setPlayer(PlayerState.BLACK);

    GameState currentPlayer = GameState.WHITE_TURN;

    CombinedStrategy combinedStrategy = new CombinedStrategy();
    ReversiStrategy strategy1 = new MaxCaptureStrategy();
    ReversiStrategy strategy2 = new CornerStrategy(BoardType.HEXAGONAL);
    combinedStrategy.addStrategy(strategy1);
    combinedStrategy.addStrategy(strategy2);
    Optional<ICell> chosenMove = combinedStrategy.determineMove(mock, currentPlayer);
    Cell cell = new Cell(1, -2);
    Assert.assertEquals(cell.getQ(), chosenMove.get().getQ());
    Assert.assertEquals(cell.getR(), chosenMove.get().getR());
  }


}
