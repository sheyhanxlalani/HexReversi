import adapter.ControllerImpl;
import adapter.ReversiModelImpl;
import controller.Controller;
import decorator.HintDecorator;
import game.type.BoardType;
import game.type.HexReversi;
import game.ReversiModel;
import game.type.SquareReversi;
import player.Player;
import player.PlayerColor;
import provider.cs3500.reversi.view.ReversiGraphicsView;
import view.HexReversiView;
import view.ReversiView;
import view.SquareReversiView;

/**
 * The main class for the Reversi game.
 * Create the model and view and then connect them.
 */
public final class Reversi {


  /**
   * Private constructor so this utility class cannot be instantiated.
   *
   * @param args - command line arguments.
   */
  public static void main(String[] args) {
    // Default values
    int size = args.length > 0 ? Integer.parseInt(args[0]) : 3; //Board size is the first argument
    String boardTypeStr = args.length > 1 ? args[1] : "hex";
    String player1Type = args.length > 2 ? args[2] : "human"; //Player 1 type is the second argument
    // Player 2 type is the third argument
    String player2Type = args.length > 3 ? args[3] : "providerHuman";
    BoardType boardType = boardTypeStr.equalsIgnoreCase("square")
            ? BoardType.SQUARE : BoardType.HEXAGONAL;


    // Initialize model
    ReversiModel model = boardType == BoardType.SQUARE
            ? new SquareReversi(size) : new HexReversi(size);
    HintDecorator decoratedModel = new HintDecorator(model);
    Player blackPlayer = ReversiUtils.createPlayer(decoratedModel, player1Type,
            PlayerColor.BLACK, boardType);
    decoratedModel.setBlackPlayer(blackPlayer);
    decoratedModel.gameStarted();

    // Initialize view for Player 1
    ReversiView viewPlayer1 = boardType == BoardType.SQUARE ? new SquareReversiView(decoratedModel,
            PlayerColor.BLACK)
            : new HexReversiView(decoratedModel, PlayerColor.BLACK);
    Controller controller1 = new Controller(decoratedModel, blackPlayer, viewPlayer1);
    decoratedModel.addModelListener(controller1);

    // Initialize Player 2
    Player whitePlayer;

    if (player2Type.startsWith("provider")) {
      ReversiModelImpl providerModel = new ReversiModelImpl(decoratedModel, size);
      whitePlayer = ReversiUtils.createProviderPlayer(providerModel, player2Type,
              PlayerColor.WHITE);
      providerModel.setPlayer(whitePlayer);
      ReversiGraphicsView viewPlayer2 = new ReversiGraphicsView(providerModel);
      ControllerImpl controller2 = new ControllerImpl(decoratedModel, whitePlayer, viewPlayer2);
      providerModel.subscribeForTurnNotifs(controller2);
    } else {
      ReversiView viewPlayer2 = boardType == BoardType.SQUARE
              ? new SquareReversiView(decoratedModel, PlayerColor.BLACK)
              : new HexReversiView(decoratedModel, PlayerColor.BLACK);
      whitePlayer = ReversiUtils.createPlayer(decoratedModel, player2Type,
              PlayerColor.WHITE, boardType);
      Controller controller2 = new Controller(decoratedModel, whitePlayer, viewPlayer2);
      decoratedModel.addModelListener(controller2);
      decoratedModel.setWhitePlayer(whitePlayer);
    }

  }
}
