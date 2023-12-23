package view;

import java.io.IOException;

import game.utility.ICell;
import game.ReversiModel;

/**
 * Represent a hex reversi textual view.
 */
public class HexReversiTextualView implements TextualView {
  private final ReversiModel model;
  private final Appendable out;

  /**
   * HexReversiTextualView constructors.
   *
   * @param model - a reversi model
   */
  public HexReversiTextualView(ReversiModel model) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null for view");
    }
    this.model = model;
    this.out = null;

  }

  /**
   * Convert the game display to String view.
   *
   * @return - a string of the Hex Reversi game.
   */
  @Override
  public String toString() {
    StringBuilder displayString = new StringBuilder();
    int size = model.getSize();

    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }

    // main loop for diagonal index
    for (int diagonalIndex = -size; diagonalIndex <= size; diagonalIndex++) {
      //startRow, endRow = the boundaries of our current slice(?) )diagnol
      int startRow = Math.max(-size, -diagonalIndex - size);
      int endRow = Math.min(size, -diagonalIndex + size);

      // calculate row padding
      int padding = Math.abs(diagonalIndex);

      for (int i = 0; i < padding; i++) {
        displayString.append(" "); // 2 spaces for each level
      }

      //row loop
      for (int currentRow = startRow; currentRow <= endRow; currentRow++) {
        // calculate col inde
        int col = -diagonalIndex - currentRow;
        System.out.println("Col: " + col + ", Row: " + currentRow);
        // cell check and append (CHECK IF THE CELL IS EXISTING)
        if (model.cellExists(col, currentRow)) {
          ICell cell = model.getCell(col, currentRow);

          displayString.append(cell.toString()).append(" ");
        }
      }
      // end of row
      displayString.append("\n");
    }
    return displayString.toString();
  }


  @Override
  public void render() throws IOException {
    if (out != null) {
      out.append(this.toString());
    } else {
      throw new IllegalStateException("Appendable no work");
    }

  }
}
