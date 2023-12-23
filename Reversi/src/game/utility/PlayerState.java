package game.utility;

/**
 * To represent the state of the cell either black occupied, white occupied, or empty..
 */
public enum PlayerState {
  EMPTY("_"), BLACK("X"), WHITE("O");
  private final String letter;

  PlayerState(String letter) {
    this.letter = letter;
  }

  /**
   * Get the letter representation of this player.
   */
  public String getLetterRep() {
    return this.letter;
  }
}