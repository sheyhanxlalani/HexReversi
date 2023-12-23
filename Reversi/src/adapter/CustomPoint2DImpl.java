package adapter;

import provider.cs3500.reversi.model.CustomPoint2D;

/**
 * The adapter for the provider's CustomPoint2D. Act as the adpter of
 * their custom point 2D and our Cell.
 */
public class CustomPoint2DImpl implements CustomPoint2D {
  private int dim1;
  private int dim2;

  /**
   * Constructor of CustomPoint2D.
   * @param dim1 - dimension point 1.
   * @param dim2 - dimension point 2.
   */
  public CustomPoint2DImpl(int dim1, int dim2) {
    this.dim1 = dim1;
    this.dim2 = dim2;
  }

  @Override
  public int getDim1() {
    return dim1;
  }

  @Override
  public int getDim2() {
    return dim2;
  }
}
