package view;

import java.io.IOException;
import game.ReversiModel;
import game.utility.ICell;


/**
 * Represent a textual view of a square reversi game.
 */
public class SquareReversiTextualView implements TextualView {

  private final ReversiModel model;
  private final Appendable out;

  /**
   * Constructor.
   * @param model - a Square reversi model.
   */
  public SquareReversiTextualView(ReversiModel model) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null for view");
    }
    this.model = model;
    this.out = null;
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }

  }

  @Override
  public String toString() {
    StringBuilder displayString = new StringBuilder();
    int size = model.getSize();
    for (int r = 0; r < size; r++) {
      for (int q = 0; q < size; q++) {
        if (model.cellExists(q, r)) {
          ICell cell = model.getCell(q, r);
          displayString.append(cell.toString()).append(" ");
        }
      }
      displayString.append("\n");

    }
    return displayString.toString();

  }

  @Override
  public void render() throws IOException {
    if (out != null) {
      out.append(this.toString());
    } else {
      throw new IllegalStateException("Appendable not set");
    }
  }
}
