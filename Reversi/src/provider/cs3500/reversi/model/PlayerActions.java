package provider.cs3500.reversi.model;

import java.util.Optional;

/**
 * The provider code. for playerActions. Control their player.
 */
public interface PlayerActions {

  /**
   * Passes the player's turn.
   */
  void pass();

  /**
   * Places a tile at the given location.
   * @param move the location to place the tile at
   */
  void placeTile(Optional<CustomPoint2D> move);
}
