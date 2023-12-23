import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import game.utility.Cell;
import game.utility.GameState;
import game.type.HexReversi;
import game.utility.ICell;
import game.utility.PlayerState;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

/**
 * Represent examples of a hexagon reversi model.
 */
public class ReversiModelTest {
  private HexReversi hexGame;
  private HexReversi hexGameSizeOf6;
  private HexReversi hexGameInvalid;
  private HexReversi hexGameSize1;


  @Before
  public void init() {
    hexGame = new HexReversi(2);
    hexGameSizeOf6 = new HexReversi(6);
    hexGameSize1 = new HexReversi(1);

  }

  @Test(expected = IllegalArgumentException.class)
  public void testInitBoardNegSize() {
    hexGameInvalid = new HexReversi(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInitZeroSize() {
    hexGameInvalid = new HexReversi(0);
  }


  @Test
  public void testInitBoardSizeOne() {
    hexGameSize1.gameStarted();
    assertEquals(7, hexGameSize1.getBoard().size()); // 1 center + 6 surroundings
  }

  @Test
  public void testInitBoardSizeTwo() {
    hexGame.gameStarted();
    assertEquals(19, hexGame.getBoard().size()); // 1c + 6 1's + 12 2's.
  }

  @Test
  public void testStartGame() {
    hexGame.gameStarted();

    assertEquals(19, hexGame.getBoard().size()); // 1c + 6 1's + 12 2's.
  }


  @Test
  public void testGetQ() {
    hexGame.gameStarted();
    ICell c1 = new Cell(3, 0);
    assertEquals(3, c1.getQ());
  }

  @Test
  public void testGetR() {
    hexGame.gameStarted();
    ICell c1 = new Cell(3, 0);
    assertEquals(0, c1.getR());
  }


  @Test(expected = IllegalStateException.class)
  public void testMakeMoveOnNotEmptyCell() {
    hexGame.gameStarted();
    ICell cell = hexGame.getCell(-1, 1);
    hexGame.makeMove(cell);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMakeMoveOnAValidCellButInvalidMove() {
    hexGame.gameStarted();
    ICell cell = hexGame.getCell(2, -2);
    // Check if the cell is empty before move
    Assert.assertEquals(PlayerState.EMPTY, cell.getPlayer());
    hexGame.makeMove(cell);
    // Check if the cell is not empty after move
    Assert.assertNotEquals(PlayerState.EMPTY, cell.getPlayer());
  }

  @Test(expected = IllegalStateException.class)
  public void testNullForMakeMove() {
    hexGame.gameStarted();
    ICell cell = null;
    // Check if the cell is empty before move
    hexGame.makeMove(cell);
  }


  @Test
  public void testMakeMoveOnValidCellAndValidMove() {
    hexGameSizeOf6.gameStarted();
    assertEquals(127, hexGameSizeOf6.getBoard().size());
    assertEquals(6, hexGameSizeOf6.getSize());
    ICell cell1 = hexGameSizeOf6.getCell(-1, -1);
    ICell flippedByCell1 = hexGameSizeOf6.getCell(-1, 0);
    ICell cellOnTheOtherSide1 = hexGameSizeOf6.getCell(-1, 1);
    Assert.assertEquals(PlayerState.EMPTY, cell1.getPlayer());
    Assert.assertEquals(PlayerState.WHITE, flippedByCell1.getPlayer());
    Assert.assertEquals(PlayerState.BLACK, cellOnTheOtherSide1.getPlayer());
    hexGameSizeOf6.makeMove(cell1);
    Assert.assertEquals(PlayerState.BLACK, cell1.getPlayer());
    Assert.assertEquals(PlayerState.BLACK, flippedByCell1.getPlayer());

    // test another valid move
    ICell cell2 = hexGameSizeOf6.getCell(-2, -1);
    ICell cellOnTheOtherSide2 = hexGameSizeOf6.getCell(1, -1);
    ICell flipped1ByCell2 = hexGameSizeOf6.getCell(0, -1);
    ICell flipped2ByCell2 = hexGameSizeOf6.getCell(-1, -1);
    Assert.assertEquals(PlayerState.BLACK, flipped1ByCell2.getPlayer());
    Assert.assertEquals(PlayerState.BLACK, flipped2ByCell2.getPlayer());
    Assert.assertEquals(PlayerState.WHITE, cellOnTheOtherSide2.getPlayer());
    Assert.assertEquals(PlayerState.EMPTY, cell2.getPlayer());
    hexGameSizeOf6.makeMove(cell2);
    Assert.assertEquals(PlayerState.WHITE, cell2.getPlayer());
    Assert.assertEquals(PlayerState.WHITE, flipped1ByCell2.getPlayer());
    Assert.assertEquals(PlayerState.WHITE, flipped2ByCell2.getPlayer());
  }


  @Test
  public void testCellExists() {
    hexGameSizeOf6.gameStarted();
    assertEquals(127, hexGameSizeOf6.getBoard().size());
    Assert.assertTrue(hexGameSizeOf6.cellExists(-2, -1));
    Assert.assertFalse(hexGameSizeOf6.cellExists(-21, -1));
  }

  //tests is Game Over method
  @Test
  public void testIsGameOverBlack() {
    hexGame.gameStarted();
    assertEquals(19, hexGame.getBoard().size());
    //flip all the cells as white

    ICell cell5 = hexGame.getCell(0, -2);
    ICell cell6 = hexGame.getCell(1, -2);
    ICell cell7 = hexGame.getCell(2, -2);
    ICell cell8 = hexGame.getCell(-1, -1);
    ICell cell9 = hexGame.getCell(0, -1);
    ICell cell10 = hexGame.getCell(1, -1);
    ICell cell11 = hexGame.getCell(2, -1);
    ICell cell12 = hexGame.getCell(-2, 0);
    ICell cell13 = hexGame.getCell(-1, 0);
    ICell cell14 = hexGame.getCell(1, 0);
    ICell cell15 = hexGame.getCell(2, 0);
    ICell cell16 = hexGame.getCell(-2, 1);
    ICell cell17 = hexGame.getCell(-1, 1);
    ICell cell18 = hexGame.getCell(0, 1);
    ICell cell19 = hexGame.getCell(1, 1);
    ICell cell20 = hexGame.getCell(-2, 2);
    ICell cell21 = hexGame.getCell(0, 2);
    ICell cell22 = hexGame.getCell(-1, 2);
    ICell cell23 = hexGame.getCell(0, 0);

    //flip all the cells as black
    cell5.setPlayer(PlayerState.BLACK);
    cell6.setPlayer(PlayerState.BLACK);
    cell7.setPlayer(PlayerState.BLACK);
    cell8.setPlayer(PlayerState.BLACK);
    cell9.setPlayer(PlayerState.BLACK);
    cell10.setPlayer(PlayerState.BLACK);
    cell11.setPlayer(PlayerState.BLACK);
    cell12.setPlayer(PlayerState.BLACK);
    cell13.setPlayer(PlayerState.BLACK);
    cell14.setPlayer(PlayerState.BLACK);
    cell15.setPlayer(PlayerState.BLACK);
    cell16.setPlayer(PlayerState.BLACK);
    cell17.setPlayer(PlayerState.BLACK);
    cell18.setPlayer(PlayerState.BLACK);
    cell19.setPlayer(PlayerState.BLACK);
    cell20.setPlayer(PlayerState.BLACK);
    cell21.setPlayer(PlayerState.BLACK);
    cell22.setPlayer(PlayerState.BLACK);
    cell23.setPlayer(PlayerState.BLACK);

    Assert.assertTrue(hexGame.isGameOver());
  }

  //tests the returnWinner method
  @Test
  public void testReturnWinner() {
    hexGameSizeOf6.gameStarted();
    assertEquals(127, hexGameSizeOf6.getBoard().size());
    //flip all the cells as white
    for (ICell cell : hexGameSizeOf6.getBoard()) {
      cell.setPlayer(PlayerState.WHITE);
    }
    Assert.assertEquals(GameState.WHITE_WIN, hexGameSizeOf6.returnWinner());
  }

  @Test
  public void testGameOverPassTwice() {
    hexGame.gameStarted();
    int countPass = hexGame.getCountPass();
    hexGame.wannaPass();
    hexGame.wannaPass();
    Assert.assertEquals(countPass, 2);

    Assert.assertTrue(hexGame.isGameOver());
  }

  @Test
  public void testIfGameIsTied() {
    hexGameSizeOf6.gameStarted();
    assertEquals(127, hexGameSizeOf6.getBoard().size());
    hexGameSizeOf6.wannaPass();
    hexGameSizeOf6.wannaPass();
    Assert.assertTrue(hexGameSizeOf6.isGameOver());
  }

  //tests the returnWinner method
  @Test
  public void testReturnWinnerTie() {
    hexGame.gameStarted();
    assertEquals(19, hexGame.getBoard().size());
    ICell cell5 = hexGame.getCell(0, -2);
    ICell cell6 = hexGame.getCell(1, -2);
    ICell cell7 = hexGame.getCell(2, -2);
    ICell cell8 = hexGame.getCell(-1, -1);
    ICell cell9 = hexGame.getCell(0, -1);
    ICell cell10 = hexGame.getCell(1, -1);
    ICell cell11 = hexGame.getCell(2, -1);
    ICell cell12 = hexGame.getCell(-2, 0);
    ICell cell13 = hexGame.getCell(-1, 0);
    ICell cell14 = hexGame.getCell(1, 0);
    ICell cell15 = hexGame.getCell(2, 0);
    ICell cell16 = hexGame.getCell(-2, 1);
    ICell cell17 = hexGame.getCell(-1, 1);
    ICell cell18 = hexGame.getCell(0, 1);
    ICell cell19 = hexGame.getCell(1, 1);
    ICell cell20 = hexGame.getCell(-2, 2);
    ICell cell21 = hexGame.getCell(0, 2);
    ICell cell22 = hexGame.getCell(-1, 2);
    ICell cell23 = hexGame.getCell(0, 0);

    cell5.setPlayer(PlayerState.BLACK);
    cell6.setPlayer(PlayerState.BLACK);
    cell7.setPlayer(PlayerState.BLACK);
    cell8.setPlayer(PlayerState.BLACK);
    cell9.setPlayer(PlayerState.BLACK);
    cell10.setPlayer(PlayerState.BLACK);
    cell11.setPlayer(PlayerState.BLACK);
    cell12.setPlayer(PlayerState.BLACK);
    cell13.setPlayer(PlayerState.BLACK);
    cell14.setPlayer(PlayerState.BLACK);
    cell15.setPlayer(PlayerState.BLACK);
    cell16.setPlayer(PlayerState.BLACK);
    cell17.setPlayer(PlayerState.BLACK);
    cell18.setPlayer(PlayerState.BLACK);
    cell19.setPlayer(PlayerState.BLACK);
    cell20.setPlayer(PlayerState.WHITE);
    cell21.setPlayer(PlayerState.WHITE);
    cell22.setPlayer(PlayerState.WHITE);
    cell23.setPlayer(PlayerState.WHITE);

    Assert.assertEquals(GameState.BLACK_WIN, hexGame.returnWinner());

  }

  @Test
  public void gameIsOverWhenThereAreNoMoreDiscs() {
    hexGame.gameStarted();
    assertEquals(19, hexGame.getBoard().size());

    ICell cell5 = hexGame.getCell(0, -2);
    ICell cell6 = hexGame.getCell(1, -2);
    ICell cell7 = hexGame.getCell(2, -2);
    ICell cell8 = hexGame.getCell(-1, -1);
    ICell cell9 = hexGame.getCell(0, -1);
    ICell cell10 = hexGame.getCell(1, -1);
    ICell cell11 = hexGame.getCell(2, -1);
    ICell cell12 = hexGame.getCell(-2, 0);
    ICell cell13 = hexGame.getCell(-1, 0);
    ICell cell14 = hexGame.getCell(1, 0);
    ICell cell15 = hexGame.getCell(2, 0);
    ICell cell16 = hexGame.getCell(-2, 1);
    ICell cell17 = hexGame.getCell(-1, 1);
    ICell cell18 = hexGame.getCell(0, 1);
    ICell cell19 = hexGame.getCell(1, 1);
    ICell cell20 = hexGame.getCell(-2, 2);
    ICell cell21 = hexGame.getCell(0, 2);
    ICell cell22 = hexGame.getCell(-1, 2);
    ICell cell23 = hexGame.getCell(0, 0);

    cell5.setPlayer(PlayerState.BLACK);
    cell6.setPlayer(PlayerState.BLACK);
    cell7.setPlayer(PlayerState.BLACK);
    cell8.setPlayer(PlayerState.BLACK);
    cell9.setPlayer(PlayerState.BLACK);
    cell10.setPlayer(PlayerState.BLACK);
    cell11.setPlayer(PlayerState.BLACK);
    cell12.setPlayer(PlayerState.BLACK);
    cell13.setPlayer(PlayerState.BLACK);
    cell14.setPlayer(PlayerState.BLACK);
    cell15.setPlayer(PlayerState.BLACK);
    cell16.setPlayer(PlayerState.BLACK);
    cell17.setPlayer(PlayerState.BLACK);
    cell18.setPlayer(PlayerState.BLACK);
    cell19.setPlayer(PlayerState.BLACK);
    cell20.setPlayer(PlayerState.EMPTY);
    cell21.setPlayer(PlayerState.EMPTY);
    cell22.setPlayer(PlayerState.EMPTY);
    cell23.setPlayer(PlayerState.EMPTY);

    Assert.assertEquals(true, hexGame.isGameOver());
  }


  @Test
  public void testMakeMoveWhenCellIsNull() {
    hexGameSizeOf6.gameStarted();
    assertThrows(IllegalStateException.class, () -> hexGameSizeOf6.makeMove(null));
  }


}
