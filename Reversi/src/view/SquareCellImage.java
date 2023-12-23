package view;

import java.awt.Color;
import java.awt.geom.Path2D;
import java.awt.Graphics2D;

import java.awt.geom.Point2D;

/**
 * SquareCellImage - an image of a square cell. Extend Path2D Double.
 */
public class SquareCellImage extends Path2D.Double {
  private Color color;
  private int q; // Row index
  private int r; // Column index
  private int cellSize; // Size of the cell

  /**
   * Constructor.
   * @param color - the color of cell.
   * @param q - q of cell.
   * @param r - r of cell.
   * @param cellSize - size of the cell.
   */
  public SquareCellImage(Color color, int q, int r, int cellSize) {
    this.color = color;
    this.q = q;
    this.r = r;
    this.cellSize = cellSize;
    createCell();
  }

  private void createCell() {
    double x = q * cellSize;
    double y = r * cellSize;
    moveTo(x, y);
    lineTo(x + cellSize, y);
    lineTo(x + cellSize, y + cellSize);
    lineTo(x, y + cellSize);
    closePath();
  }

  /**
   * Set color of the cell.
   * @param color - the color of the cell.
   */
  public void setColor(Color color) {
    this.color = color;
  }

  /**
   * Draw the cell.
   * @param g2 - graphic.
   */
  public void draw(Graphics2D g2) {
    // Save the current state of the graphics context
    Color originalColor = g2.getColor();

    // Fill the square
    g2.setColor(this.color);
    g2.fill(this);

    // Draw the square outline
    g2.setColor(Color.BLACK);
    g2.draw(this);

    // Restore the original state
    g2.setColor(originalColor);
  }

  /**
   * Get color of the cell.
   * @return - the color of the cell.
   */
  public Color getColor() {
    return color;
  }

  /**
   * Get cell size.
   * @return - size of the cell.
   */
  public int getCellSize() {
    return cellSize;
  }

  /**
   * Check if the clicked point is inside the cell.
   * @param p - the clicked point.
   * @return - true if it is.
   */
  public boolean isPointInside(Point2D p) {
    double x = this.q * cellSize + 300;
    double y = this.r * cellSize + 150;
    return x <= p.getX() && p.getX() < x + cellSize && y <= p.getY() && p.getY() < y + cellSize;
  }

  /**
   * Get X coordinate of the cell.
   * @return - x coord.
   */
  public int getX() {
    return this.q;
  }

  /**
   * Get Y coordinate of the cell.
   * @return - y coordinate.
   */
  public int getY() {
    return this.r;
  }

}
