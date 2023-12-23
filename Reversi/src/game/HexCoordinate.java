package game;

/**
 * Represent a hexagon coordinate system.
 * The origin starts at 0,0.
 */
public class HexCoordinate {
  public final int q; // col
  public final int r; // row


  /**
   * HexCoordinate constructor.
   * @param q - col.
   * @param r - row.
   */
  public HexCoordinate(int q, int r) {
    this.q = q;
    this.r = r;
  }

}
