import controller.ViewFeatures;
import game.ReadonlyReversiModel;
import player.PlayerColor;
import view.ReversiView;

/**
 * The MockView for the Reversi game.
 */
public class MockView implements ReversiView {
  private final StringBuilder log;

  /**
   * Constructor for the mock view.
   */
  public MockView(ReadonlyReversiModel model, StringBuilder log) {
    this.log = log;
  }

  @Override
  public void refresh() {
    log.append("Refreshing the view\n");
  }

  @Override
  public void showMessage(String message) {
    log.append("Showing message: " + message + "\n");
  }

  @Override
  public void updateScore(int blackScore, int whiteScore) {
    log.append("Updating score: " + blackScore + ", " + whiteScore + "\n");
  }

  @Override
  public void indicatePlayerTurn(PlayerColor player) {
    log.append("Indicating player turn: " + player.toString() + "\n");

  }

  @Override
  public void setVisible(boolean b) {
    log.append("Setting visible: " + b + "\n");
  }

  @Override
  public void setViewFeaturesListener(ViewFeatures listener) {
    log.append("Adding listener as called in view\n");
  }

  @Override
  public void indicatePlayerColor(PlayerColor player) {
    log.append("Indicating player color: " + player.toString() + "\n");
  }


}