package game.utility;

import java.util.Objects;

/**
 * To represent a hexagon cell of the board.
 */
public class Cell extends CoordinateSystem implements ICell {




  private PlayerState currentPlayer;
  private boolean isSelected;


  /**
   * To represent a cell constructor.
   *
   * @param q - column.
   * @param r - row.
   */
  public Cell(int q, int r) {
    super(q, r);
    int s = -q - r;
    this.currentPlayer = PlayerState.EMPTY;
    this.isSelected = false;
  }

  /**
   * Set the current state of the correct player.
   *
   * @param player - state.
   */
  public void setPlayer(PlayerState player) {
    this.currentPlayer = player;
  }


  /**
   * return the string version of the player.
   *
   * @return a letter to represent the player of empty cell.
   */
  @Override
  public String toString() {
    return currentPlayer.getLetterRep();
  }

  /**
   * Return the q of this cell.
   *
   * @return the q - int.
   */
  public int getQ() {
    return this.q;
  }

  /**
   * Get r of this cell.
   *
   * @return the r of this cell - int.
   */
  public int getR() {
    return this.r;
  }

  /**
   * Get the player that played this cell.
   *
   * @return the player state of this cell.
   */
  public PlayerState getPlayer() {
    return this.currentPlayer;
  }


  /**
   * Change the current state of this cell.
   * Black to white and white to black.
   */
  public void flip() {
    if (this.currentPlayer == PlayerState.BLACK) {
      this.currentPlayer = PlayerState.WHITE;
    } else if (this.currentPlayer == PlayerState.WHITE) {
      this.currentPlayer = PlayerState.BLACK;
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Cell cell = (Cell) o;
    return q == cell.q && r == cell.r;
  }

  @Override
  public int hashCode() {
    return Objects.hash(q, r);
  }



  @Override
  public void setSelected(Boolean selected) {
    this.isSelected = selected;
  }

}
