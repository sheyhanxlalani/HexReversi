import adapter.ProviderStrategyAdapter;
import adapter.ReversiModelImpl;
import game.ReversiModel;
import game.type.BoardType;
import player.AIPlayer;
import player.HumanPlayer;
import player.Player;
import player.PlayerColor;
import provider.cs3500.reversi.strategy.PlaceForCornerOptimalThenHighestScore;
import provider.cs3500.reversi.strategy.PlaceForHighestScoreStrategy;
import strategy.AvoidCornerCellStrategy;
import strategy.CombinedStrategy;
import strategy.CornerStrategy;
import strategy.MaxCaptureStrategy;
import strategy.MinimaxStrategy;
import strategy.ReversiStrategy;

/**
 * Utility class for reversi main game. Containing helper related to player creation
 * Provide the static methods to parse player types and strategy for AI player.
 */
public class ReversiUtils {

  // Private constructor to prevent instantiation
  private ReversiUtils() {
  }

  /**
   * Use to create player in the main method.
   *
   * @param model      - the reversi model.
   * @param playerType - either human or AI player.
   * @param color      - the wanted color of the player.
   * @return a player either Human or AI player.
   */
  public static Player createPlayer(ReversiModel model, String playerType,
                                    PlayerColor color, BoardType boardType) {
    if (playerType.toLowerCase().startsWith("human")) {
      return new HumanPlayer(color);
    } else if (playerType.toLowerCase().startsWith("ai")) {
      String strategyPart = playerType.length() > 2 ? playerType.substring(2) : "";
      String strategyName = strategyPart.isEmpty() ? "maxcapture"
              : extractStrategyName(strategyPart); // default to maxcapture
      System.out.println("Extracted Strategy Name: " + strategyName);

      String depthStr = strategyPart.length() > strategyName.length()
              ? strategyPart.substring(strategyName.length()) : "";
      ReversiStrategy strategy = returnStrategy(strategyName, depthStr, boardType);
      return new AIPlayer(strategy, model, color);
    } else {
      throw new IllegalArgumentException("Unknown player type: " + playerType);
    }
  }


  /**
   * Extract the strategy name from the configuration.
   *
   * @param strategyPart - name of the strategy
   * @return the name of the reversi strategy.
   */
  private static String extractStrategyName(String strategyPart) {
    // Add all strategy names here, longest names first
    String[] strategyNames = {"getcorner", "avoidcorner", "minimax", "maxcapture", "combined"};
    for (String name : strategyNames) {
      if (strategyPart.toLowerCase().startsWith(name)) {
        return name;
      }
    }
    return strategyPart; // Default case, returns the whole string
  }

  /**
   * Initialize the reversi strategy accoding to the extracted name of it.
   *
   * @param strategyName - the strategy name from the configuration.
   * @param depthStr     - (for minimax only) how depth you want the minimax strategy to go.
   * @return a reversi strategy.
   */
  private static ReversiStrategy returnStrategy(String strategyName,
                                                String depthStr, BoardType boardType) {
    switch (strategyName.toLowerCase()) {
      case "maxcapture":
        return new MaxCaptureStrategy();
      case "avoidcorner":
        return new AvoidCornerCellStrategy(boardType);
      case "getcorner":
        return new CornerStrategy(boardType);
      case "minimax":
        int depth = depthStr.isEmpty() ? 5 : Integer.parseInt(depthStr);
        return new MinimaxStrategy(depth);
      case "combined":
        return new CombinedStrategy();
      default:
        throw new IllegalArgumentException("Unknown strategy: " + strategyName);
    }
  }

  /**
   * Create a player for the provider code.
   * @param model - the model adapter.
   * @param playerType - the player type.
   * @param color - the color of the player.
   * @return -
   */
  public static Player createProviderPlayer(ReversiModelImpl model,
                                            String playerType, PlayerColor color) {
    if (playerType.toLowerCase().startsWith("providerhuman")) {
      return new HumanPlayer(color); // or any other constructor suitable for
      // your provider's human player
    } else if (playerType.toLowerCase().startsWith("providerai")) {
      String strategyPart = playerType.substring("providerai".length());
      provider.cs3500.reversi.strategy.ReversiStrategy strategy
              = createProviderStrategy(strategyPart);
      //Using ProviderStrategyAdapter
      return new AIPlayer(new ProviderStrategyAdapter(strategy), color);
    }
    throw new IllegalArgumentException("Unknown provider player type: " + playerType);
  }

  private static provider.cs3500.reversi.strategy.ReversiStrategy
      createProviderStrategy(String strategyPart) {
    switch (strategyPart) {
      case "1":
        return new PlaceForHighestScoreStrategy();
      case "2":
        return new PlaceForCornerOptimalThenHighestScore();
      default:
        throw new IllegalArgumentException("Unknown provider strategy: " + strategyPart);
    }
  }
}
