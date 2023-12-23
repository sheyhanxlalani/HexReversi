package controller;

/**
 * This interface defines the actions related to player interactions
 * in a board game. Implementers of this interface are responsible for
 * handling events triggered by the player actions, such as making move, passing
 * a turn, or indicating a player's color, which will be displayed on the panel of the
 * window.
 */
public interface PlayerActionListener {

  /**
   * Invoke when a player makes a move on the game board.
   * It is called with the coordinates of the cell on which the move is made.
   *
   * @param q - the q coordinate of the cell on the board.
   * @param r - the r coordinate of the cell on the board.
   */
  void onMoveMade(int q, int r);

  /**
   * It is called when a player decides to pass their turn. This can occur in situation where the
   * has no valid move.
   */
  void onPassTurn();

  /**
   * Notifies the player's color. This method is used at the start of the game or when it is
   * needed to tell the player which color they use.
   */
  void indicateMyColor();
}
