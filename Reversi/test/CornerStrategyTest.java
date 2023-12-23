import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

import game.type.BoardType;
import game.utility.Cell;

import game.utility.GameState;
import game.utility.ICell;
import game.utility.PlayerState;
import game.ReadonlyReversiModel;
import strategy.CornerStrategy;
import strategy.ReversiStrategy;


/**
 * Represent an example of how get corner strategy works.
 */
public class CornerStrategyTest {
  @Test
  public void testGetCornerStrategy_CornerCellValid() {
    StringBuilder log = new StringBuilder();

    ReadonlyReversiModel mock = new MockHexReversi(3, log);
    ICell cell1 = mock.getCell(0, 1);
    cell1.setPlayer(PlayerState.WHITE);
    ICell cell2 = mock.getCell(1, 1);
    cell2.setPlayer(PlayerState.WHITE);
    ICell cell3 = mock.getCell(1, 0);
    cell3.setPlayer(PlayerState.WHITE);
    ICell cell4 = mock.getCell(-1, 1);
    cell4.setPlayer(PlayerState.WHITE);
    ICell cell5 = mock.getCell(1, 2);
    cell5.setPlayer(PlayerState.BLACK);
    ICell cell6 = mock.getCell(-1, 0);
    cell6.setPlayer(PlayerState.BLACK);
    ICell cell7 = mock.getCell(0, -1);
    cell7.setPlayer(PlayerState.BLACK);
    ICell cell8 = mock.getCell(1, -1);
    cell8.setPlayer(PlayerState.BLACK);
    ICell cell9 = mock.getCell(0, -2);
    cell9.setPlayer(PlayerState.WHITE);
    GameState currentPlayer = GameState.BLACK_TURN;
    ReversiStrategy strategy = new CornerStrategy(BoardType.HEXAGONAL);
    Optional<ICell> chosenMove = strategy.determineMove(mock, currentPlayer);
    Cell expectedMove = new Cell(0, -3);
    Assert.assertEquals(expectedMove, chosenMove.get());

  }


  @Test
  public void testGetCornerStrategy_CornerCellInValid_ButStillHaveValidMove() {
    StringBuilder log = new StringBuilder();

    ReadonlyReversiModel mock = new MockHexReversi(3, log);
    ICell cell1 = mock.getCell(0, 1);
    cell1.setPlayer(PlayerState.WHITE);
    ICell cell2 = mock.getCell(1, 1);
    cell2.setPlayer(PlayerState.WHITE);
    ICell cell3 = mock.getCell(1, 0);
    cell3.setPlayer(PlayerState.WHITE);
    ICell cell4 = mock.getCell(-1, 1);
    cell4.setPlayer(PlayerState.WHITE);
    ICell cell5 = mock.getCell(1, 2);
    cell5.setPlayer(PlayerState.BLACK);
    ICell cell6 = mock.getCell(-1, 0);
    cell6.setPlayer(PlayerState.BLACK);
    ICell cell7 = mock.getCell(0, -1);
    cell7.setPlayer(PlayerState.BLACK);
    ICell cell8 = mock.getCell(1, -1);
    cell8.setPlayer(PlayerState.BLACK);
    ICell cell9 = mock.getCell(0, -2);
    cell9.setPlayer(PlayerState.BLACK);
    GameState currentPlayer = GameState.BLACK_TURN;
    ReversiStrategy strategy = new CornerStrategy(BoardType.HEXAGONAL);
    Optional<ICell> chosenMove = strategy.determineMove(mock, currentPlayer);
    ICell expectedMove = new Cell(0, -3);
    Assert.assertNotEquals(expectedMove.getQ(), chosenMove.get().getQ());
    Assert.assertNotEquals(expectedMove.getR(), chosenMove.get().getR());
  }


  @Test(expected = IllegalStateException.class)
  public void testGetCornerStrategy_CornerCellInValid_NoHaveValidMove() {
    StringBuilder log = new StringBuilder();

    ReadonlyReversiModel mock = new MockHexReversi(3, log);
    ICell cell1 = mock.getCell(0, -3);
    cell1.setPlayer(PlayerState.BLACK);
    ICell cell2 = mock.getCell(1, -3);
    cell2.setPlayer(PlayerState.BLACK);
    ICell cell3 = mock.getCell(2, -3);
    cell3.setPlayer(PlayerState.BLACK);
    ICell cell4 = mock.getCell(3, -3);
    cell4.setPlayer(PlayerState.BLACK);
    ICell cell5 = mock.getCell(-1, -2);
    cell5.setPlayer(PlayerState.EMPTY);
    ICell cell6 = mock.getCell(0, -2);
    cell6.setPlayer(PlayerState.BLACK);
    ICell cell7 = mock.getCell(1, -2);
    cell7.setPlayer(PlayerState.BLACK);
    ICell cell8 = mock.getCell(2, -2);
    cell8.setPlayer(PlayerState.BLACK);
    ICell cell10 = mock.getCell(-2, -1);
    cell10.setPlayer(PlayerState.BLACK);
    ICell cell11 = mock.getCell(-1, -1);
    cell11.setPlayer(PlayerState.BLACK);
    ICell cell12 = mock.getCell(0, -1);
    cell12.setPlayer(PlayerState.BLACK);
    ICell cell13 = mock.getCell(1, -1);
    cell13.setPlayer(PlayerState.BLACK);
    ICell cell14 = mock.getCell(2, -1);
    cell14.setPlayer(PlayerState.BLACK);
    ICell cell15 = mock.getCell(3, -1);
    cell15.setPlayer(PlayerState.BLACK);
    ICell cell16 = mock.getCell(-3, 0);
    cell16.setPlayer(PlayerState.BLACK);
    ICell cell17 = mock.getCell(-3, 1);
    cell17.setPlayer(PlayerState.BLACK);
    ICell cell18 = mock.getCell(-3, 2);
    cell18.setPlayer(PlayerState.BLACK);
    ICell cell19 = mock.getCell(-3, 3);
    cell19.setPlayer(PlayerState.BLACK);
    ICell cell20 = mock.getCell(-2, 0);
    cell20.setPlayer(PlayerState.BLACK);
    ICell cell21 = mock.getCell(-2, 1);
    cell21.setPlayer(PlayerState.BLACK);
    ICell cell22 = mock.getCell(-2, 2);
    cell22.setPlayer(PlayerState.BLACK);
    ICell cell23 = mock.getCell(-1, 0);
    cell23.setPlayer(PlayerState.BLACK);
    ICell cell24 = mock.getCell(-2, 1);
    cell24.setPlayer(PlayerState.BLACK);
    ICell cell25 = mock.getCell(-2, 2);
    cell25.setPlayer(PlayerState.BLACK);
    ICell cell26 = mock.getCell(-2, 3);
    cell26.setPlayer(PlayerState.BLACK);
    ICell cell27 = mock.getCell(0, 1);
    cell27.setPlayer(PlayerState.BLACK);
    ICell cell28 = mock.getCell(0, 2);
    cell28.setPlayer(PlayerState.BLACK);
    ICell cell29 = mock.getCell(0, 3);
    cell29.setPlayer(PlayerState.BLACK);
    ICell cell30 = mock.getCell(1, 0);
    cell30.setPlayer(PlayerState.BLACK);
    ICell cell31 = mock.getCell(1, 1);
    cell31.setPlayer(PlayerState.BLACK);
    ICell cell32 = mock.getCell(1, 2);
    cell32.setPlayer(PlayerState.BLACK);
    ICell cell33 = mock.getCell(2, 0);
    cell33.setPlayer(PlayerState.BLACK);
    ICell cell34 = mock.getCell(2, 1);
    cell34.setPlayer(PlayerState.BLACK);
    ICell cell35 = mock.getCell(3, 0);
    cell35.setPlayer(PlayerState.BLACK);

    GameState currentPlayer = GameState.WHITE_TURN;
    ReversiStrategy strategy = new CornerStrategy(BoardType.HEXAGONAL);
    strategy.determineMove(mock, currentPlayer);
  }

  @Test
  public void testGetCornerStrategyTestsForAllCorners() {
    StringBuilder log = new StringBuilder();
    ReadonlyReversiModel mock = new MockHexReversi(3, log);
    ReversiStrategy strategy = new CornerStrategy(BoardType.HEXAGONAL);
    GameState currentPlayer = GameState.BLACK_TURN;
    Optional<ICell> chosenMove = strategy.determineMove(mock, currentPlayer);
    log.append("Chosen Move: " + chosenMove.get().getQ() + ", " + chosenMove.get().getR());
    Assert.assertEquals("Checking move: 3, -3\n"
            + "Checking move: -3, 3\n"
            + "Checking move: 3, 0\n"
            + "Checking move: -3, 0\n"
            + "Checking move: 0, 3\n"
            + "Checking move: 0, -3\n"
            + "Strategy retrieved the board: \n"
            + "Checking move: 2, 1\n"
            + "Checking move: 0, 0\n"
            + "Checking move: -2, -1\n"
            + "Checking move: -2, 0\n"
            + "Checking move: 0, 2\n"
            + "Checking move: -2, 1\n"
            + "Chosen Move: -2, 1", log.toString());
    Cell expectedMove = new Cell(-2, 1);
    Assert.assertEquals(expectedMove, chosenMove.get());
  }

  @Test
  public void testGetCornerStrategyForSquareReversi() {
    StringBuilder log = new StringBuilder();
    ReadonlyReversiModel mock = new MockSquareModel(4, log);
    ReversiStrategy strategy = new CornerStrategy(BoardType.SQUARE);
    GameState currentPlayer = GameState.BLACK_TURN;
    Optional<ICell> chosenMove = strategy.determineMove(mock, currentPlayer);
    log.append("Chosen Move: " + chosenMove.get().getQ() + ", " + chosenMove.get().getR());
    Assert.assertEquals("Checking move: 0, 0\n"
            + "Checking move: 1, 0\n"
            + "Checking move: 2, 0\n"
            + "Checking move: 3, 0\n"
            + "Checking move: 0, 1\n"
            + "Checking move: 0, 2\n"
            + "Checking move: 0, 3\n"
            + "Checking move: 1, 3\n"
            + "Checking move: 2, 3\n"
            + "Checking move: 3, 3", log.toString());
    Cell expectedMove = new Cell(0, 0);
    Assert.assertEquals(expectedMove, chosenMove.get());
  }

  @Test
  public void testGetCornerStrategy_CornerCellValidForSquare() {
    StringBuilder log = new StringBuilder();

    ReadonlyReversiModel mock = new MockSquareModel(4, log);
    ICell cell1 = mock.getCell(3, 3);
    cell1.setPlayer(PlayerState.WHITE);
    GameState currentPlayer = GameState.WHITE_TURN;
    ReversiStrategy strategy = new CornerStrategy(BoardType.SQUARE);
    Optional<ICell> chosenMove = strategy.determineMove(mock, currentPlayer);
    Cell expectedMove = new Cell(0, 0);
    Assert.assertEquals(expectedMove, chosenMove.get());
  }


}
