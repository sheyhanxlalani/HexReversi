package strategy;

import java.util.Optional;

import game.utility.GameState;
import game.utility.ICell;
import game.utility.PlayerState;
import game.ReadonlyReversiModel;


/**
 * Represent one of the strategy of a reversi game.
 * Capture as many pieces on this turn as possible.
 */
public class MaxCaptureStrategy implements ReversiStrategy {
  @Override
  public Optional<ICell> determineMove(ReadonlyReversiModel model, GameState player) {
    ICell bestMove = null;
    int maxCaptureCount = 0;
    boolean passRequired = true;

    for (ICell cell : model.getBoard()) {
      if (cell.getPlayer() == PlayerState.EMPTY && model.isValidMove(cell, player)) {
        int captured = model.getCaptureCells(cell, player);
        if (captured > maxCaptureCount || (captured == maxCaptureCount
                && isUpperLeft(cell, bestMove))) {
          maxCaptureCount = captured;
          bestMove = cell;
          passRequired = false;
        }
      }
    }
    if (passRequired) {
      throw new IllegalStateException("Strategy failed to make a choice!");
      //This will help to implement how the player can pass.
      // I intended to use a try catch and call wannaPass for the player.
      // wannaPass is a method that we implemented in our model.
    }
    return Optional.of(bestMove);
  }

  // check if the best move cell is the upper left of this cell.
  private boolean isUpperLeft(ICell cell, ICell bestMove) {
    if (bestMove == null) {
      return true;
    }
    // Compare based on axial coordinates (q, r)
    return (cell.getR() < bestMove.getR() || (cell.getR() == bestMove.getR()
            && cell.getQ() < bestMove.getQ()));
  }
}