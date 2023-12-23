package strategy;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Optional;

import game.utility.GameState;
import game.utility.ICell;
import game.utility.PlayerState;
import game.ReadonlyReversiModel;
import game.ReversiModel;

/**
 * Minimax strategy that goes through all the game's possible future state.
 * Calculate the best move that the opponent can make.
 */
public class MinimaxStrategy implements ReversiStrategy {
  private final int depth;

  /**
   * Constructor for minimax strategy class.
   *
   * @param depth - how depth you wanna search.
   */
  public MinimaxStrategy(int depth) {
    this.depth = depth;
  }

  @Override
  public Optional<ICell> determineMove(ReadonlyReversiModel model, GameState player) {
    return Optional.ofNullable(minimax(model, depth, player, true, Double.NEGATIVE_INFINITY,
            Double.POSITIVE_INFINITY).getKey());
  }

  // implement the minimax algo. check ing throughout the tree with the given depth, player.
  private Map.Entry<ICell, Double> minimax(ReadonlyReversiModel model, int depth,
                                          GameState player,
                                          boolean maximizingPlayer,
                                          double alpha, double beta) {
    if (depth == 0 || model.isGameOver()) {
      return new AbstractMap.SimpleEntry<>(null, evaluateBoard(model, player));
    }
    double bestScore = maximizingPlayer ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
    ICell bestMove = null;

    for (ICell cell : model.getBoard()) {
      if (cell.getPlayer() == PlayerState.EMPTY
              && model.isValidMove(cell, player)) {
        ReversiModel clone = model.getClone();
        clone.makeMove(cell);
        // Calculate the opponent based on the current player
        GameState opponent = (player == GameState.BLACK_TURN)
                ? GameState.WHITE_TURN
                : GameState.BLACK_TURN;
        // recursively call
        double score = minimax(clone, depth - 1, opponent,
                !maximizingPlayer, alpha, beta).getValue();
        if (maximizingPlayer) {
          if (score > bestScore) {
            bestScore = score;
            bestMove = cell;
          }
          alpha = Math.max(alpha, score);
        } else {
          if (score < bestScore) {
            bestScore = score;
            bestMove = cell;
          }
          beta = Math.min(beta, score);
        }
        // alpha beta pruning
        if (beta <= alpha) {
          break;
        }
      }
    }
    return new AbstractMap.SimpleEntry<>(bestMove, bestScore);
  }

  // evalute the board to see if the current player has any good move.
  // scoring based on difference in numbers of discs
  private Double evaluateBoard(ReadonlyReversiModel model, GameState player) {
    int[] scores = model.getScores();
    return (double) (player == GameState.BLACK_TURN
            ? scores[0] - scores[1] : scores[1] - scores[0]);
  }

}
