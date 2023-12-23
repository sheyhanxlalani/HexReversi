import org.junit.Assert;
import org.junit.Test;


import java.util.Optional;

import game.utility.Cell;
import game.utility.GameState;
import game.utility.ICell;
import game.utility.PlayerState;
import game.ReadonlyReversiModel;
import strategy.MaxCaptureStrategy;

/**
 * Represent an example of how capturing the most cells opponent works.
 */
public class MaxCaptureStrategyTest {
  @Test
  public void testChooseMove_MaxCapture_SmallSize() {
    StringBuilder log = new StringBuilder();
    ReadonlyReversiModel mock = new MockHexReversi(3, log);
    MaxCaptureStrategy strategy = new MaxCaptureStrategy();
    GameState currentPlayer = GameState.BLACK_TURN;
    Optional<ICell> chosenMove = strategy.determineMove(mock, currentPlayer);
    log.append("Chosen Move: " + chosenMove.get().getQ() + ", " + chosenMove.get().getR());
    Assert.assertEquals("Strategy retrieved the board: \n" +
            "Checking move: 2, 1\n" +
            "Checking move: 0, 0\n" +
            "Checking move: -2, -1\n" +
            "Checking move: -2, 0\n" +
            "Checking move: 0, 2\n" +
            "Checking move: -2, 1\n" +
            "Get captured cells for: -2, 1\n" +
            "Checking move: 0, 3\n" +
            "Checking move: -2, 2\n" +
            "Checking move: -2, 3\n" +
            "Checking move: 3, -3\n" +
            "Checking move: 3, -2\n" +
            "Checking move: 1, -3\n" +
            "Checking move: 3, -1\n" +
            "Checking move: 1, -2\n" +
            "Get captured cells for: 1, -2\n" +
            "Checking move: 3, 0\n" +
            "Checking move: -1, -2\n" +
            "Checking move: -1, -1\n" +
            "Get captured cells for: -1, -1\n" +
            "Checking move: 1, 1\n" +
            "Get captured cells for: 1, 1\n" +
            "Checking move: 1, 2\n" +
            "Checking move: -3, 0\n" +
            "Checking move: -1, 2\n" +
            "Get captured cells for: -1, 2\n" +
            "Checking move: -3, 1\n" +
            "Checking move: -1, 3\n" +
            "Checking move: -3, 2\n" +
            "Checking move: -3, 3\n" +
            "Checking move: 2, -3\n" +
            "Checking move: 2, -2\n" +
            "Checking move: 0, -3\n" +
            "Checking move: 2, -1\n" +
            "Get captured cells for: 2, -1\n" +
            "Checking move: 0, -2\n" +
            "Checking move: 2, 0\n" +
            "Chosen Move: 1, -2", log.toString());
    Cell expectedMove = new Cell(1, -2);
    Assert.assertEquals(expectedMove, chosenMove.get());
  }

  @Test
  public void testChooseMove_MaxCapture_BiggerSize() {
    StringBuilder log = new StringBuilder();
    ReadonlyReversiModel mock = new MockHexReversi(3, log);
    MaxCaptureStrategy strategy = new MaxCaptureStrategy();
    GameState currentPlayer = GameState.BLACK_TURN;
    Optional<ICell> chosenMove = strategy.determineMove(mock, currentPlayer);
    Cell expectedMove = new Cell(1, -2);
    Assert.assertEquals(expectedMove, chosenMove.get());

    // check if the board is being modified by the strategy
    Cell check = new Cell(1, -2);
    Assert.assertEquals(PlayerState.EMPTY, check.getPlayer());

    // check another player, expected the same spot (upper-left)
    GameState currentPlayer1 = GameState.WHITE_TURN;
    Optional<ICell> chosenMove1 = strategy.determineMove(mock, currentPlayer1);
    Cell expectedMove1 = new Cell(1, -2);
    Assert.assertEquals(expectedMove1, chosenMove1.get());
  }

  @Test
  public void testUsingMock_SeeIfItWouldCaptureTheMost() {
    StringBuilder log = new StringBuilder();
    ReadonlyReversiModel mock = new MockHexReversi(5, log);

    // set up a state where white have 2 options
    ICell cell1 = mock.getCell(1, -1);
    cell1.setPlayer(PlayerState.WHITE);
    ICell cell2 = mock.getCell(1, -2);
    cell2.setPlayer(PlayerState.WHITE);
    ICell cell3 = mock.getCell(1, 1);
    cell3.setPlayer(PlayerState.BLACK);

    MaxCaptureStrategy strategy = new MaxCaptureStrategy();
    GameState currentPlayer = GameState.BLACK_TURN;
    Optional<ICell> chosenMove = strategy.determineMove(mock, currentPlayer);

    Cell expectedMove = new Cell(1, -3);
    Assert.assertEquals(expectedMove, chosenMove.get());

  }

  @Test(expected = IllegalStateException.class)
  public void testWhenPassIsRequired() {
    StringBuilder log = new StringBuilder();

    ReadonlyReversiModel mock = new MockHexReversi(3, log);
    // set up a state where white have 2 options
    ICell cell1 = mock.getCell(0, -1);
    cell1.setPlayer(PlayerState.WHITE);
    ICell cell2 = mock.getCell(1, -1);
    cell2.setPlayer(PlayerState.WHITE);
    ICell cell3 = mock.getCell(0, 1);
    cell3.setPlayer(PlayerState.WHITE);
    ICell cell4 = mock.getCell(1, 0);
    cell4.setPlayer(PlayerState.WHITE);
    ICell cell5 = mock.getCell(-1, 1);
    cell5.setPlayer(PlayerState.WHITE);
    ICell cell6 = mock.getCell(-1, 0);
    cell6.setPlayer(PlayerState.WHITE);
    MaxCaptureStrategy strategy = new MaxCaptureStrategy();
    GameState currentPlayer = GameState.BLACK_TURN;
    strategy.determineMove(mock, currentPlayer);
  }


}
