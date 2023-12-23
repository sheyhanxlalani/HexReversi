import controller.Controller;
import game.type.HexReversi;
import game.ReversiModel;
import player.AIPlayer;
import player.HumanPlayer;
import player.Player;
import player.PlayerColor;
import strategy.MaxCaptureStrategy;
import strategy.ReversiStrategy;
import view.HexReversiView;
import view.ReversiView;

/**
 * An example of how human player plays against AI player.
 */
public class HumanVsAITest {

  /**
   * Private constructor so this utility class cannot be instantiated.
   *
   * @param args - command line arguments.
   */
  public static void main(String[] args) {
    int size = args.length > 0 ? Integer.parseInt(args[0]) : 3;
    // Initialize model and players
    ReversiModel model = new HexReversi(size);
    Player blackPlayer = new HumanPlayer(PlayerColor.BLACK);
    ReversiStrategy strategy = new MaxCaptureStrategy();
    Player whitePlayer = new AIPlayer(strategy, model, PlayerColor.WHITE);
    model.setBlackPlayer(blackPlayer);
    model.setWhitePlayer(whitePlayer);
    model.gameStarted();
    // Initialize views
    ReversiView viewPlayer1 = new HexReversiView(model, PlayerColor.BLACK);
    ReversiView viewPlayer2 = new HexReversiView(model, PlayerColor.WHITE);
    // Initialize and set up controllers
    Controller controller1 = new Controller(model, blackPlayer, viewPlayer1);
    Controller controller2 = new Controller(model, whitePlayer, viewPlayer2);
    // Register controllers as model listeners
    model.addModelListener(controller1);
    model.addModelListener(controller2);
  }
}
