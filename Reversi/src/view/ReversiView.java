package view;

import controller.ViewFeatures;
import player.PlayerColor;

/**
 * A representative of the Reversi game view.
 * This is implemented using GUIs: JPanel and JView.
 */
public interface ReversiView {

  /**
   * Refresh the view to reflect any changes in the game state.
   */
  void refresh();

  /**
   * Tell the player that their moves are invalid.
   */
  void showMessage(String message);

  /**
   * Update the score display of players.
   */
  void updateScore(int blackScore, int whiteScore);

  /**
   * change the color based on the current player.
   */
  void indicatePlayerTurn(PlayerColor player);

  /**
   * Set the game visible.
   *
   * @param b - true if want to make it visible.
   */
  void setVisible(boolean b);

  /**
   * Set the listener for the view.
   * @param listener - the View Features.
   */
  void setViewFeaturesListener(ViewFeatures listener);

  /**
   * Indicate the color of this player.
   * @param player - the player.
   */
  void indicatePlayerColor(PlayerColor player);

}
