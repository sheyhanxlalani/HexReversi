package game;

import java.util.Set;
import controller.ModelActionListener;
import game.utility.GameState;
import game.utility.ICell;

/**
 * Represents a read-only view of a reversi game. This interface is used to allow
 * access to the game's state without permitting any modification to the game itself.
 * It is useful where only viewing the game state is necessary.
 */
public interface ReadonlyReversiModel {
  /**
   * return the current score for the current player.
   *
   * @return the score for each player.
   */
  int[] getScores();

  /**
   * Return a copy of a board.
   *
   * @return a copy.
   */
  Set<ICell> getBoard();

  /**
   * Signal if the game is over or not.
   *
   * @return true if the game is over, false otherwise.
   * @throws IllegalStateException if the game hasn't been started.
   */
  boolean isGameOver() throws IllegalStateException;

  /**
   * Get the size of board of the model.
   *
   * @return - the size of the board.
   */
  int getSize();

  /**
   * Return the cell at the corresponding coordinates.
   *
   * @param q - q of the hex cell.
   * @param r - r of the hex cell.
   * @return - the cell with the right coordinates.
   */
  ICell getCell(int q, int r);

  /**
   * Checking if the cell exists in the board.
   *
   * @param q - q coordinate.
   * @param r - r coordinate.
   * @return true if the cell exists, false if there is no cell at the given coordinates.
   */
  boolean cellExists(int q, int r);

  /**
   * When the game is over.
   *
   * @return the gameState, whether black wins, white wins, or ties.
   */
  GameState returnWinner();

  /**
   * Return the current player.
   *
   * @return the current player.
   */
  GameState getCurrentState();

  /**
   * check if this move is valid.
   *
   * @param cell   - the selected cell.
   * @param player - the current player.
   * @return
   */
  boolean isValidMove(ICell cell, GameState player);

  /**
   * get the captured cells for this move.
   *
   * @param cell   - the selected cell.
   * @param player - the current player.
   * @return
   */
  int getCaptureCells(ICell cell, GameState player);

  /**
   * if there is no more valid move, pass.
   * if there are two passes in a row, game is over.
   */
  void wannaPass();


  /**
   * Set the model listeners for this game model. This listener is notified
   * of different events and updates in the reversi game.
   *
   * @param controller - the model listener that will receive the game event notification.
   */
  void setModelListener(ModelActionListener controller);

  /**
   * Checking if game is started.
   */
  boolean isGameStarted();


  /**
   * Creates a modifiable copy of this game.
   * @return the modifiable clone
   */
  ReversiModel getClone();


  /**
   * This is the hint for block player.
   */
  void toggleBlackHints();

  /**
   * This is the hint for the white player.
   */
  void toggleWhiteHints();

  /**
   * Enable tto display black hint.
   * @return - yes if true.
   */
  boolean isBlackHintsEnabled();

  /**
   * Enable tto display white hint.
   * @return - yes if true.
   */
  boolean isWhiteHintsEnabled();

  /**
   * Get count pass.
   * @return times that players pass.
   */
  int getCountPass();
}