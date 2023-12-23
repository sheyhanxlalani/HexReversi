package game.utility;

/**
 * Interface to represent a hexagon cell of the board.
 * Implementers shoould have the ability to set Player State on the cell
 * and get its coordinates.
 */
public interface ICell {

  /**
   * Set the current state of the correct player.
   *
   * @param player - state.
   */
  void setPlayer(PlayerState player);

  /**
   * Return the string version of the player.
   *
   * @return a letter to represent the player of empty cell.
   */
  String toString();

  /**
   * Return the q of this cell.
   *
   * @return the q - int.
   */
  int getQ();

  /**
   * Get r of this cell.
   *
   * @return the r of this cell - int.
   */
  int getR();

  /**
   * Get the player that played this cell.
   *
   * @return the player state of this cell.
   */
  PlayerState getPlayer();

  /**
   * Change the current state of this cell.
   * Black to white and white to black.
   */
  void flip();

  /**
   * Change the selection state of this cell.
   *
   * @param selected - the new selection state.
   */
  void setSelected(Boolean selected);


}
