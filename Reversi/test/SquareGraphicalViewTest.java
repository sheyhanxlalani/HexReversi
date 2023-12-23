import controller.Controller;
import game.ReversiModel;
import game.type.SquareReversi;
import player.HumanPlayer;
import player.Player;
import player.PlayerColor;
import view.ReversiView;
import view.SquareReversiView;


/**
 * SquareGraphicalViewTest test.
 */
public class SquareGraphicalViewTest {

  /**
   * Private constructor so this utility class cannot be instantiated.
   *
   * @param args - command line arguments.
   */
  public static void main(String[] args) {
    ReversiModel model = new SquareReversi(8);
    Player blackPlayer = new HumanPlayer(PlayerColor.BLACK);
    model.setBlackPlayer(blackPlayer);
    model.gameStarted();
    ReversiView view1 = new SquareReversiView(model, PlayerColor.BLACK);
    Controller controller1 = new Controller(model, blackPlayer, view1);
    model.addModelListener(controller1);

    Player whitePlayer = new HumanPlayer(PlayerColor.WHITE);
    model.setWhitePlayer(whitePlayer);
    ReversiView view2 = new SquareReversiView(model, PlayerColor.WHITE);
    Controller controller2 = new Controller(model, whitePlayer, view2);
    model.addModelListener(controller2);


  }
}
