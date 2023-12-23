package strategy;

import java.util.Optional;

import game.type.BoardType;
import game.utility.GameState;
import game.utility.ICell;
import game.utility.PlayerState;
import game.ReadonlyReversiModel;

/**
 * Represent one of the strategy of reversi game.
 * go for the corners: discs in corners cannot be captured,
 * because they don’t have cells on their other side.
 */
public class CornerStrategy implements ReversiStrategy {

  private BoardType boardType;


  public CornerStrategy(BoardType boardType) {
    this.boardType = boardType;
  }

  @Override
  public Optional<ICell> determineMove(ReadonlyReversiModel model, GameState player) {
    int size = model.getSize();
    int[][] corners = getCornerCoordinates(model.getSize());

    ICell bestMove = null;
    for (int[] corner : corners) {
      ICell cell = model.getCell(corner[0], corner[1]);
      if (model.cellExists(cell.getQ(), cell.getR())
              && cell.getPlayer() == PlayerState.EMPTY
              && model.isValidMove(cell, player)) {
        return Optional.of(cell);
      }
    }

    // if no corner move is valid, return any valid move (FALLBACK)
    for (ICell cell : model.getBoard()) {
      if (cell.getPlayer() == PlayerState.EMPTY && model.isValidMove(cell, player)) {
        bestMove = cell;
        break;
      }
    }

    if (bestMove == null) {
      throw new IllegalStateException("No best move found for go for corner");
      // This will help to implement how the player can pass.
      // I intended to use a try catch and call wannaPass for the player.
      // wannaPass is a method that we implemented in our model.
    }
    return Optional.of(bestMove);
  }

  private int[][] getCornerCoordinates(int size) {
    if (boardType == BoardType.HEXAGONAL) {
      return new int[][] {
              {size, -size}, {size, 0}, {-size, size}, {-size, 0}, {0, size}, {0, -size}
      };
    } else if (boardType == BoardType.SQUARE) {
      return new int[][] {
              {0, 0},
              {0, size - 1},
              {size - 1, 0},
              {size - 1, size - 1}
      };
    } else {
      throw new IllegalArgumentException("Unknown board type: " + boardType);
    }
  }
}
