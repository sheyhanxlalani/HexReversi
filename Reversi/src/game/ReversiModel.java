package game;

import java.util.Set;

import controller.ModelActionListener;
import game.utility.ICell;
import player.Player;

/**
 * Represent a reversi game implemented in Java
 * This class defines functionalities for a Reversi game model.
 * This interface outlines the methods required for managing the state and
 * interactions of a reversi game.
 * Implementations of this interface
 * should manage the game logic and rules for a Reversi game.
 */
public interface ReversiModel extends ReadonlyReversiModel {
  /**
   * Starts a new game of Reversi. This method initializes the game state,
   * setting up the board and preparing it for the first move.
   */
  void gameStarted();

  /**
   * make a copy of the current game board board.
   *
   * @return a copy of the board.
   */
  Set<ICell> getBoard();

  /**
   * Initializes the game board for a new game of Reversi.
   */
  void initializeBoard();

  /**
   * A player makes a move, if the move is valid, then play the move and change the game state
   * to the next player.
   * Check if the selected cell is legal.
   *
   * @throws IllegalStateException if the game is not started yet.
   * @throws IllegalStateException if the Cell is a null.
   * @throws IllegalStateException if the move is not allowable.
   *                               For example, if there is NO disc with the same color
   *                               as the clicked
   *                               cell at the end point (in at least 1 direction) or
   *                               there is an empty
   *                               cell in between the two end points of EVERY DIRECTIONS.
   */
  void makeMove(ICell clicked) throws IllegalStateException, IllegalArgumentException;

  /**
   * Sets a listener (controller) for this game model. The listener
   * is notified of changes in the game state, such as move completion, player
   * changes, and game end.   * @param listener - The ModelActionListener
   * to be notified of game events.
   */
  void setModelListener(ModelActionListener listener);

  /**
   * Check if there is a valid move for this current player.
   *
   * @return true if there is at least one.
   */
  boolean hasValidMoves();

  /**
   * Switch the player turn.
   * Black to white and white to black.
   */
  void switchPlayer();


  /**
   * Set the black player for this model.
   *
   * @param player - player that will be identified as black color.
   */
  void setBlackPlayer(Player player);

  /**
   * Set the white player for this model.
   *
   * @param player - player that will be identified as white color.
   */
  void setWhitePlayer(Player player);

  /**
   * Add model listener to this model for each player.
   *
   * @param listener - the given listener for each player.
   */
  void addModelListener(ModelActionListener listener);

  /**
   * Get the current player of the game.
   *
   * @return the current player.
   */
  Player getCurrentPlayer();
}
