package game.utility;

/**
 * Represent a hexagon coordinate system.
 * The origin starts at 0,0.
 */
public class CoordinateSystem {
  public final int q; // col
  public final int r; // row


  /**
   * HexCoordinate constructor.
   * @param q - col.
   * @param r - row.
   */
  public CoordinateSystem(int q, int r) {
    this.q = q;
    this.r = r;
  }

}
