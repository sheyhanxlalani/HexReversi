import game.type.HexReversi;
import game.utility.ICell;
import player.PlayerColor;
import view.HexReversiView;
import view.ReversiView;


/**
 * An example used to test the view of the game.
 */
public class CorrectlyDisplayTest {

  /**
   * Private constructor so this utility class cannot be instantiated.
   *
   * @param args - command line arguments.
   */
  public static void main(String[] args) {
    int size = args.length > 0 ? Integer.parseInt(args[0]) : 3;
    // Default to size 3 if no argument provided
    HexReversi model = new HexReversi(3);
    ICell cell1 = model.getCell(1, 1);
    ICell cell2 = model.getCell(1, -2);

    model.makeMove(cell1);
    model.makeMove(cell2);
    ReversiView view = new HexReversiView(model, PlayerColor.WHITE);
    view.setVisible(true);
  }

}
