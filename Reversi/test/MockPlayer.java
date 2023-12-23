import controller.PlayerActionListener;
import game.utility.GameState;
import player.Player;
import player.PlayerColor;

/**
 * Mock player to test.
 */
public class MockPlayer implements Player {
  private final StringBuilder log;

  /**
   * Constructor for the mock player.
   */
  public MockPlayer(StringBuilder log) {
    this.log = log;
  }

  @Override
  public void play(GameState player) {
    log.append("Playing\n");
  }

  @Override
  public void setActionListener(PlayerActionListener listener) {
    log.append("Adding listener as called in player\n");
  }

  @Override
  public void setColor(PlayerColor color) {
    log.append("Setting color: " + color.toString() + "\n");
  }

  @Override
  public PlayerColor getColor() {
    log.append("Getting color\n");
    return PlayerColor.WHITE;
  }

  @Override
  public boolean isAI() {
    log.append("Checking if it is AI\n");
    return true;
  }


}