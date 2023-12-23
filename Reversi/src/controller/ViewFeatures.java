package controller;

/**
 * It proves methods that define user interactions in the game interface (view).
 * It enables the communication of user's interaction with view to the game controller.
 */
public interface ViewFeatures {
  /**
   * Cell selected (clicked) on GUI displayed. Get the q and r for that cell.
   */
  void selectedCell(int q, int r);

  /**
   * Quit the current game.
   */
  void quit();

  /**
   * Player wanna pass. Check if there is at least 1 valid move for this player.
   * If there is, tell the player that they can't pass.
   * If it is not their turn, they can't pass either.
   */
  void pass();

  /**
   * Ending the turn of the current player. Check if game is over.
   */
  void endTurn();


  /**
   * show the hints for black player.
   */
  void toggleBlackHints();

  /**
   * shod the hints for white player.
   */
  void toggleWhiteHints();
}
