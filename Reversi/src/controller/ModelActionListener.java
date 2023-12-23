package controller;

import game.utility.GameState;
import player.PlayerColor;

/**
 * Interface for listening to actions and state changes of the reversi game
 * model. Implementers of this interface are used to respond to various events
 * in the game, such as changes in player turns, game over, score updates,
 * and the start of the game.
 * This interface is essential for connecting the game logic with components
 * that handle game events, like user interfaces or game controllers.
 */
public interface ModelActionListener {

  /**
   * Called when the turn changes from one player to another.
   * Notifies the listeners about which player's turn it s now, based on the player's
   * color
   * @param playerColor - the color of the current player.
   */
  void onPlayerTurnChanged(PlayerColor playerColor);

  /**
   * Invoked when the game is over. this method is called to signal the players that game is over.
   * It can be a tie, win ,or lose
   */
  void onGameOver(GameState winner);

  /**
   * Triggered when there is an update in the game's score. This method is called after a move
   * that impacts the game's coring. Allow listeners to update the score display.
   */
  void onScoreUpdate();

  /**
   * Notifies when a new game is started
   * Use to initialize the new game state in response to the new game session.
   */
  void onGameStarted();

}
