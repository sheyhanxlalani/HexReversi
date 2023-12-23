package adapter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import game.utility.Cell;
import game.utility.GameState;
import game.type.HexReversi;
import game.utility.ICell;
import game.ReadonlyReversiModel;
import provider.cs3500.reversi.model.CustomPoint2D;
import provider.cs3500.reversi.model.PlayerTile;
import provider.cs3500.reversi.model.ReadOnlyReversiModel;
import strategy.ReversiStrategy;

/**
 * Provider strategy adapter. This acts as the bridge between our Reversi Strategy
 * and their strategy.
 */
public class ProviderStrategyAdapter implements ReversiStrategy {
  public provider.cs3500.reversi.strategy.ReversiStrategy providerStrategy;


  /**
   * The constructor of ProviderStrategyAdapter.
   *
   * @param strategy - the provider's strategy.
   */
  public ProviderStrategyAdapter(provider.cs3500.reversi.strategy.ReversiStrategy strategy) {
    this.providerStrategy = strategy;
  }


  @Override
  public Optional<ICell> determineMove(ReadonlyReversiModel model, GameState player) {
    ReadOnlyReversiModel providerModel = convertToModel(model);
    PlayerTile playerTurn = convertPlayerStateToPlayerTile(player);

    Optional<CustomPoint2D> providerMove = providerStrategy.chooseMove(providerModel, playerTurn);
    return providerMove.map(this::convertPointToCell);
  }


  private ICell convertPointToCell(CustomPoint2D point) {
    return new Cell(point.getDim2(), point.getDim1());
  }

  private PlayerTile convertPlayerStateToPlayerTile(GameState playerState) {
    switch (playerState) {
      case BLACK_TURN:
        return PlayerTile.FIRST;
      case WHITE_TURN:
        return PlayerTile.SECOND;
      default:
        throw new IllegalArgumentException("Unknown player state");
    }
  }

  private ReadOnlyReversiModel convertToModel(ReadonlyReversiModel model) {
    Map<CustomPoint2D, PlayerTile> boardMap = new HashMap<>();
    for (ICell cell : model.getBoard()) {
      CustomPoint2D point = new CustomPoint2DImpl(cell.getR(), cell.getQ());
      PlayerTile playerTile = convertPlayerStateToPlayerTile(model.getCurrentState());
      boardMap.put(point, playerTile);
    }
    return new ReversiModelImpl((HexReversi) model, model.getSize());
  }

}
