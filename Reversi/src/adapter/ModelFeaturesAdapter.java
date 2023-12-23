package adapter;

import controller.ModelActionListener;
import player.PlayerColor;
import provider.cs3500.reversi.model.ModelFeatures;
import provider.cs3500.reversi.model.PlayerTile;

/**
 * Model listener adapter.
 * This is used as a bridge for our model listeners to communicate and convert.
 */
public class ModelFeaturesAdapter implements ModelFeatures {
  private final ModelActionListener listener;

  /**
   * The constructor of the model features adapter.
   * @param listener - our listener.
   */
  public ModelFeaturesAdapter(ModelActionListener listener) {
    this.listener = listener;
  }

  @Override
  public void turnChanged(PlayerTile t) {
    PlayerColor color = convertPlayerTileToColor(t);
    listener.onPlayerTurnChanged(color);
  }

  private PlayerColor convertPlayerTileToColor(PlayerTile t) {
    return t == PlayerTile.FIRST ? PlayerColor.BLACK : PlayerColor.WHITE;
  }
}
