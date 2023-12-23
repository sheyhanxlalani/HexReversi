import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import game.type.HexReversi;
import game.utility.ICell;
import game.utility.PlayerState;
import view.HexReversiTextualView;

import static org.junit.Assert.assertEquals;

/**
 * Represent an example of reversi textual view.
 */
public class ReversiTextualViewTest {
  private HexReversi hexGame1;
  private HexReversi hexGame2;
  private HexReversi hexGameSizeOf6;


  private HexReversiTextualView view1;
  private HexReversiTextualView view2;
  private HexReversiTextualView view4;


  @Before
  public void init() {
    hexGame1 = new HexReversi(1);
    hexGame2 = new HexReversi(2);
    HexReversi hexGame3 = new HexReversi(3);
    hexGameSizeOf6 = new HexReversi(6);


    view1 = new HexReversiTextualView(hexGame1);
    view2 = new HexReversiTextualView(hexGame2);
    HexReversiTextualView view3 = new HexReversiTextualView(hexGame3);
    view4 = new HexReversiTextualView(hexGameSizeOf6);

  }

  @Test
  public void testTextViewRandom() {
    this.hexGame1.gameStarted();
    Assert.assertEquals(" X O \nO _ X \n X O \n", view1.toString());
  }


  @Test
  public void testTextViewRandomSizeTwo() {
    this.hexGame2.gameStarted();
    Assert.assertEquals(19, this.hexGame2.getBoard().size());
    Assert.assertEquals("  _ _ _ \n _ X O _ \n_ O _ X _ \n _ X O _ \n  _ _ _ \n",
            view2.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullModelForTextualView() {
    HexReversiTextualView view3 = new HexReversiTextualView(null);
    Assert.assertEquals(" X O \nO _ O \n X X \n", view3.toString());
  }

  @Test
  public void testMakeMoveOnValidCellAndValidMove() {
    hexGameSizeOf6.gameStarted();
    assertEquals(127, hexGameSizeOf6.getBoard().size());
    assertEquals(6, hexGameSizeOf6.getSize());
    Assert.assertEquals("      _ _ _ _ _ _ _ \n"
                    + "     _ _ _ _ _ _ _ _ \n"
                    + "    _ _ _ _ _ _ _ _ _ \n"
                    + "   _ _ _ _ _ _ _ _ _ _ \n"
                    + "  _ _ _ _ _ _ _ _ _ _ _ \n"
                    + " _ _ _ _ _ X O _ _ _ _ _ \n"
                    + "_ _ _ _ _ O _ X _ _ _ _ _ \n"
                    + " _ _ _ _ _ X O _ _ _ _ _ \n"
                    + "  _ _ _ _ _ _ _ _ _ _ _ \n"
                    + "   _ _ _ _ _ _ _ _ _ _ \n"
                    + "    _ _ _ _ _ _ _ _ _ \n"
                    + "     _ _ _ _ _ _ _ _ \n"
                    + "      _ _ _ _ _ _ _ \n",
            view4.toString());
    ICell cell1 = hexGameSizeOf6.getCell(-1, -1);
    ICell flippedByCell1 = hexGameSizeOf6.getCell(-1, 0);
    ICell cellOnTheOtherSide1 = hexGameSizeOf6.getCell(-1, 1);
    Assert.assertEquals(PlayerState.EMPTY, cell1.getPlayer());
    Assert.assertEquals(PlayerState.WHITE, flippedByCell1.getPlayer());
    Assert.assertEquals(PlayerState.BLACK, cellOnTheOtherSide1.getPlayer());
    hexGameSizeOf6.makeMove(cell1);
    Assert.assertEquals("      _ _ _ _ _ _ _ \n"
                    + "     _ _ _ _ _ _ _ _ \n"
                    + "    _ _ _ _ _ _ _ _ _ \n"
                    + "   _ _ _ _ _ _ _ _ _ _ \n"
                    + "  _ _ _ _ _ _ _ _ _ _ _ \n"
                    + " _ _ _ _ _ X O _ _ _ _ _ \n"
                    + "_ _ _ _ _ O _ X _ _ _ _ _ \n"
                    + " _ _ _ _ _ X X _ _ _ _ _ \n"
                    + "  _ _ _ _ _ X _ _ _ _ _ \n"
                    + "   _ _ _ _ _ _ _ _ _ _ \n"
                    + "    _ _ _ _ _ _ _ _ _ \n"
                    + "     _ _ _ _ _ _ _ _ \n"
                    + "      _ _ _ _ _ _ _ \n",
            view4.toString());
    Assert.assertEquals(PlayerState.BLACK, cell1.getPlayer());
    Assert.assertEquals(PlayerState.BLACK, flippedByCell1.getPlayer());

  }


}
