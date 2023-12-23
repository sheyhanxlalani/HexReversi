package strategy;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import game.type.BoardType;
import game.utility.GameState;
import game.utility.ICell;
import game.utility.PlayerState;
import game.ReadonlyReversiModel;

/**
 * Represent a strategy of the reversi game.
 * avoid the cells next to corners: because you’re just
 * giving your opponent the ability to get a corner on their next turn.
 */
public class AvoidCornerCellStrategy implements ReversiStrategy {

  private BoardType boardType;


  public AvoidCornerCellStrategy(BoardType boardType) {
    this.boardType = boardType;
  }

  @Override
  public Optional<ICell> determineMove(ReadonlyReversiModel model, GameState player) {
    ICell bestMove = null;
    Set<ICell> corners = cornerCells(model);
    int minDistToCorner = Integer.MAX_VALUE; // ensure that any valid dist will be smaller

    for (ICell cell : model.getBoard()) {
      if (cell.getPlayer() == PlayerState.EMPTY
              && model.isValidMove(cell, player)) {
        int distance = minDistanceFromCornerCells(cell, corners);

        if (distance > minDistToCorner) {
          bestMove = cell;
          minDistToCorner = distance;
        }
      }
    }

    if (bestMove == null) {
      throw new IllegalStateException("No best move founded for avoid corner");
      //This will help to implement how the player can pass.
      // I intended to use a try catch and call wannaPass for the player.
      // wannaPass is a method that we implemented in our model.
    }

    return Optional.of(bestMove);
  }

  // check the lowest distance from the corner cells.
  private int minDistanceFromCornerCells(ICell cell, Set<ICell> corners) {
    int minDist = Integer.MAX_VALUE;
    for (ICell corner : corners) {
      int dist = Math.abs(corner.getQ() - cell.getQ()) + Math.abs(corner.getR() - cell.getR());
      if (dist < minDist) {
        minDist = dist;

      }
    }
    return minDist;

  }

  // set up the corners cell set.
  private Set<ICell> cornerCells(ReadonlyReversiModel model) {
    Set<ICell> corners = new HashSet<>();
    int size = model.getSize();

    if (boardType == BoardType.HEXAGONAL) {
      // Add corners for hexagonal board
      corners.add(model.getCell(0, -size));
      corners.add(model.getCell(size, -size));
      corners.add(model.getCell(-size, 0));
      corners.add(model.getCell(size, 0));
      corners.add(model.getCell(-size, size));
      corners.add(model.getCell(0, size));
    } else if (boardType == BoardType.SQUARE) {
      // Add corners for square board
      corners.add(model.getCell(0, 0));
      corners.add(model.getCell(0, size - 1));
      corners.add(model.getCell(size - 1, 0));
      corners.add(model.getCell(size - 1, size - 1));
    }
    return corners;
  }

}