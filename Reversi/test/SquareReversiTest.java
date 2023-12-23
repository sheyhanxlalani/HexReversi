import game.type.SquareReversi;
import game.utility.Cell;
import game.utility.GameState;
import game.utility.ICell;
import game.utility.PlayerState;
import org.junit.Before;
import org.junit.Test;

import static game.utility.GameState.BLACK_TURN;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;


/**
 * Tests for square reversi model.
 */
public class SquareReversiTest {
  private SquareReversi squareGame;
  private SquareReversi squareGameSize6;
  private SquareReversi squareGameInvalid;
  private SquareReversi squareGameSize1;


  @Before
  public void init() {
    squareGame = new SquareReversi(8);
    squareGameSize6 = new SquareReversi(6);
    squareGameSize6 = new SquareReversi(1);

  }

  @Test(expected = IllegalArgumentException.class)
  public void testInitBoardNegSize() {
    squareGameInvalid = new SquareReversi(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInitZeroSize() {
    squareGameInvalid = new SquareReversi(0);
  }

  @Test
  public void testGetQ() {
    squareGame.gameStarted();
    ICell c1 = new Cell(3, 0);
    assertEquals(3, c1.getQ());
  }

  @Test
  public void testGetR() {
    squareGame.gameStarted();
    ICell c1 = new Cell(3, 0);
    assertEquals(0, c1.getR());
  }


  @Test
  public void testIsValidMove() {
    squareGame.gameStarted();
    squareGame.initializeBoard();
    ICell cell = this.squareGame.getCell(5, 3); // Assuming this is a valid position for a move
    assertTrue(squareGame.isValidMove(cell, BLACK_TURN));
  }

  @Test
  public void testMakeMove() {
    squareGame.gameStarted();
    ICell cell = squareGame.getCell(5, 3);
    assertEquals(BLACK_TURN, squareGame.getCurrentState());
    squareGame.makeMove(cell);
    assertEquals(PlayerState.BLACK, cell.getPlayer());
  }

  //test invalid move
  @Test(expected = IllegalArgumentException.class)
  public void testMakeMoveInvalid() {
    squareGame.gameStarted();
    squareGame.initializeBoard();
    ICell cell = new Cell(0, 0);
    squareGame.makeMove(cell);
  }

  @Test
  public void testGetCaptureCells() {
    squareGame.gameStarted();
    squareGame.initializeBoard();
    ICell cell = new Cell(0, 1); // Assuming a valid move position
    assertEquals(0, squareGame.getCaptureCells(cell, BLACK_TURN));
  }


  @Test
  public void testSwitchPlayer() {
    squareGameSize6.initializeBoard();
    GameState initialState = squareGameSize6.getCurrentState();
    squareGameSize6.switchPlayer();
    assertNotEquals(initialState, squareGameSize6.getCurrentState());
  }

  @Test
  public void testGetCell() {
    squareGame.gameStarted();
    squareGame.initializeBoard();
    ICell cell = squareGame.getCell(0, 0);
    assertNotNull(cell);
  }

  @Test
  public void testGameStarted() {
    squareGame.initializeBoard();
    squareGame.gameStarted();
    assertTrue(squareGame.isGameStarted());
  }

  @Test
  public void testInitializeBoard() {
    squareGame.gameStarted();
    squareGameSize6.initializeBoard();
    assertNotNull(squareGameSize6.getBoard());
    assertEquals(4, squareGameSize6.getBoard().size());
  }













}