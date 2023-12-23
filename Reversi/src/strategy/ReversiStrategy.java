package strategy;

import java.util.Optional;
import game.utility.GameState;
import game.utility.ICell;
import game.ReadonlyReversiModel;

/**
 * Represent the game strategy for reversi game.
 */
public interface ReversiStrategy {
  /**
   * Determine the best move for this reversi game for the current player.
   *
   * @param model  - a read only reversi model.
   * @param player - the current player.
   * @return the best move for the current player.
   */
  Optional<ICell> determineMove(ReadonlyReversiModel model, GameState player);

}