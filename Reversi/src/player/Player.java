package player;


import controller.PlayerActionListener;
import game.utility.GameState;

/**
 * To represent a player of reversi game, either AI player or human player
 * This interface defines the essential actions and characteristics of a player
 * in the game. Classes implemented this interface should encapsulate player's details such
 * as player's color (black or white) and whether the player is controller by AI or a human.
 */
public interface Player {

  /**
   * Play the move for this player (either AIPlayer or human player).
   *
   * @param player - this current player (AI or human).
   */
  void play(GameState player);

  /**
   * Set the action listener for this player (AI - Human).
   *
   * @param listener - listener for this player.
   */
  void setActionListener(PlayerActionListener listener);

  /**
   * Assign the color for this player. Either black or white.
   *
   * @param color - the color that used to assign player.
   */
  void setColor(PlayerColor color);

  /**
   * Getter of the player's color.
   *
   * @return - the color of this player.
   */
  PlayerColor getColor();

  /**
   * Checking if this player is an AI player.
   *
   * @return - true if the player is an AI player.
   */
  boolean isAI();
}

