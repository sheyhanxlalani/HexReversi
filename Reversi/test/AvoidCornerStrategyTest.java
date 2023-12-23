import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

import game.type.BoardType;
import game.utility.GameState;
import game.utility.ICell;
import game.utility.PlayerState;
import game.ReadonlyReversiModel;
import strategy.AvoidCornerCellStrategy;
import strategy.ReversiStrategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Represent an example of how avoid corner strategy works.
 */
public class AvoidCornerStrategyTest {
  @Test
  public void testAvoidCornerCells() {
    StringBuilder log = new StringBuilder();

    ReadonlyReversiModel mock = new MockHexReversi(3, log);
    GameState currentPlayer = GameState.BLACK_TURN;
    ReversiStrategy strategy = new AvoidCornerCellStrategy(BoardType.HEXAGONAL);

    // Setup the initial board state
    // [Adjust this setup to ensure (1, -2) is the optimal move]

    Optional<ICell> bestMove = strategy.determineMove(mock, currentPlayer);
    assertNotNull(bestMove);
    assertTrue(bestMove.isPresent());

    // Assuming (1, -2) is the expected best move
    ICell expectedMove = mock.getCell(1, -2);
    assertEquals(expectedMove.getQ(), bestMove.get().getQ());
    assertEquals(expectedMove.getR(), bestMove.get().getR());
  }


  @Test(expected = IllegalStateException.class)
  public void testNoBestMoveFound() {
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
    cell12.setPlayer(PlayerState.BLACK);

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
    ReversiStrategy strategy = new AvoidCornerCellStrategy(BoardType.HEXAGONAL);
    Optional<ICell> bestMove = strategy.determineMove(mock, currentPlayer);
    Assert.assertFalse(bestMove.isPresent());

  }
}
