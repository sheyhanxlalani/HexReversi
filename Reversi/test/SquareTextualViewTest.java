import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import game.ReversiModel;
import game.type.SquareReversi;
import game.utility.Cell;
import view.SquareReversiTextualView;


/**
 * Tests for square reversi view text.
 */
public class SquareTextualViewTest {
  private ReversiModel model;
  private SquareReversiTextualView view;

  @Before
  public void init() {
    model = new SquareReversi(8);
    view = new SquareReversiTextualView(model);
  }

  @Test
  public void testGameStarted() {
    model.gameStarted();
    Assert.assertEquals("_ _ _ _ _ _ _ _ \n" +
            "_ _ _ _ _ _ _ _ \n" +
            "_ _ _ _ _ _ _ _ \n" +
            "_ _ _ X O _ _ _ \n" +
            "_ _ _ O X _ _ _ \n" +
            "_ _ _ _ _ _ _ _ \n" +
            "_ _ _ _ _ _ _ _ \n" +
            "_ _ _ _ _ _ _ _ \n", view.toString());
  }

  @Test
  public void testMakeMove() {
    model.gameStarted();
    model.makeMove(new Cell(5, 3));
    Assert.assertEquals("_ _ _ _ _ _ _ _ \n" +
            "_ _ _ _ _ _ _ _ \n" +
            "_ _ _ _ _ _ _ _ \n" +
            "_ _ _ X X _ _ _ \n" +
            "_ _ _ O X _ _ _ \n" +
            "_ _ _ _ _ _ _ _ \n" +
            "_ _ _ _ _ _ _ _ \n" +
            "_ _ _ _ _ _ _ _ \n", view.toString());
  }

  @Test
  public void testMakeMove2() {
    model.gameStarted();
    model.makeMove(new Cell(2, 2));
    Assert.assertEquals("_ _ _ _ _ _ _ _ \n" +
            "_ _ _ _ _ _ _ _ \n" +
            "_ _ _ _ _ _ _ _ \n" +
            "_ _ X X X _ _ _ \n" +
            "_ _ _ X O _ _ _ \n" +
            "_ _ _ _ _ _ _ _ \n" +
            "_ _ _ _ _ _ _ _ \n" +
            "_ _ _ _ _ _ _ _ \n", view.toString());
  }

  @Test
  public void testMakeMove3() {
    model.gameStarted();
    model.makeMove(model.getCell(4, 2));
    Assert.assertEquals("_ _ _ _ _ _ _ _ \n" +
            "_ _ _ _ _ _ _ _ \n" +
            "_ _ _ _ X _ _ _ \n" +
            "_ _ _ X X _ _ _ \n" +
            "_ _ _ O X _ _ _ \n" +
            "_ _ _ _ _ _ _ _ \n" +
            "_ _ _ _ _ _ _ _ \n" +
            "_ _ _ _ _ _ _ _ \n", view.toString());
  }




  @Test
  public void testMakeMove4() {
    model.gameStarted();
    model.switchPlayer();
    model.makeMove(model.getCell(5, 4));
    Assert.assertEquals("_ _ _ _ _ _ _ _ \n" +
            "_ _ _ _ _ _ _ _ \n" +
            "_ _ _ _ _ _ _ _ \n" +
            "_ _ _ X O _ _ _ \n" +
            "_ _ _ O O O _ _ \n" +
            "_ _ _ _ _ _ _ _ \n" +
            "_ _ _ _ _ _ _ _ \n" +
            "_ _ _ _ _ _ _ _ \n", view.toString());
  }

  @Test
  public void testMakeMove5() {
    model.gameStarted();
    model.makeMove(model.getCell(4, 2));
    model.makeMove(model.getCell(3, 2));
    model.makeMove(model.getCell(2, 2));
    Assert.assertEquals("_ _ _ _ _ _ _ _ \n" +
            "_ _ _ _ _ _ _ _ \n" +
            "_ _ X X X _ _ _ \n" +
            "_ _ _ X X _ _ _ \n" +
            "_ _ _ O X _ _ _ \n" +
            "_ _ _ _ _ _ _ _ \n" +
            "_ _ _ _ _ _ _ _ \n" +
            "_ _ _ _ _ _ _ _ \n", view.toString());
  }


  @Test
  public void testMakeMove6() {
    model.gameStarted();
    model.makeMove(model.getCell(4, 2));
    model.makeMove(model.getCell(3, 2));
    model.makeMove(model.getCell(2, 2));
    model.makeMove(model.getCell(5, 2));

    Assert.assertEquals("_ _ _ _ _ _ _ _ \n" +
            "_ _ _ _ _ _ _ _ \n" +
            "_ _ X X X O _ _ \n" +
            "_ _ _ X O _ _ _ \n" +
            "_ _ _ O X _ _ _ \n" +
            "_ _ _ _ _ _ _ _ \n" +
            "_ _ _ _ _ _ _ _ \n" +
            "_ _ _ _ _ _ _ _ \n", view.toString());
  }



}
