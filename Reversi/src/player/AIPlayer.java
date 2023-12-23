package player;

import java.util.Optional;

import adapter.ProviderStrategyAdapter;
import adapter.ReversiModelImpl;
import controller.PlayerActionListener;
import game.utility.GameState;
import game.utility.ICell;
import game.ReversiModel;
import strategy.ReversiStrategy;

/**
 * To represent a AI player of the reversi game.
 * This class implements Player interfaces and encapsulates the behavior and properties of
 * of an AI Playe by using decision-making strategy.
 * The AI players uses a specified strategy to determ
 */
public class AIPlayer implements Player {
  private PlayerActionListener actionListener;
  private ReversiStrategy strategy;
  private ReversiModel model;
  private boolean isAI;
  private PlayerColor color;
  private ProviderStrategyAdapter providerStrategy;
  private ReversiModelImpl providerModel;

  /**
   * AI Player constructor.
   *
   * @param strategy - the given strategy.
   */
  public AIPlayer(ReversiStrategy strategy, ReversiModel model, PlayerColor color) {
    this.strategy = strategy;
    this.model = model;
    this.isAI = true;
    this.color = color;
  }

  public AIPlayer(ReversiStrategy providerStrategy, PlayerColor color) {
    this.strategy = providerStrategy;
    this.color = color;
  }



  @Override
  public void play(GameState player) {
    try {
      Optional<ICell> bestMove = strategy.determineMove(model, player);
      bestMove.ifPresent(cell -> actionListener.onMoveMade(cell.getQ(), cell.getR()));
    } catch (IllegalStateException e) {
      // No valid move, so pass
      actionListener.onPassTurn();
    }
  }


  @Override
  public void setActionListener(PlayerActionListener listener) {
    this.actionListener = listener;
  }

  @Override
  public void setColor(PlayerColor color) {
    this.color = color;
  }

  @Override
  public PlayerColor getColor() {
    return this.color;
  }

  @Override
  public boolean isAI() {
    return this.isAI;
  }
}
