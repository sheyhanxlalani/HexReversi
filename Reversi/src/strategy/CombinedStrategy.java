package strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import game.utility.GameState;
import game.utility.ICell;
import game.ReadonlyReversiModel;

/**
 * A way to combine the strategy (flexible for how many strategies the.
 * user wanna combine..
 */
public class CombinedStrategy implements ReversiStrategy {
  private final List<ReversiStrategy> strategies;

  /**
   * Combined strategy constructor.
   */
  public CombinedStrategy() {
    strategies = new ArrayList<>();
  }

  /**
   * Add thhe given strategy to play on this current player.
   *
   * @param strategy - given Reversi strategy.
   */
  public void addStrategy(ReversiStrategy strategy) {
    strategies.add(strategy);
  }

  @Override
  public Optional<ICell> determineMove(ReadonlyReversiModel model, GameState player) {
    for (ReversiStrategy strategy : strategies) {
      Optional<ICell> bestMove = strategy.determineMove(model, player);
      if (bestMove.isPresent()) {
        return bestMove;
      }
    }
    return Optional.empty(); // No valid move found by any strategy
  }

}
