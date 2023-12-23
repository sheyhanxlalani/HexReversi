package view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;

/**
 * To represent an image of a hexagon cell. (extending Path2D.Double)
 */
public class CellImage extends Path2D.Double {
  private Color c;

  /**
   * Cell image constructor.
   *
   * @param c       - color.
   * @param q       - q (col) of the cell.
   * @param r       - r (row) of the cell.
   * @param size    - the size of a single cell.
   * @param centerX - the center on x axis of the cell.
   * @param centerY - the center on y axis of the cell.
   */
  public CellImage(Color c, int q, int r, double size, double centerX, double centerY) {
    this.c = c;
    this.createCell(centerX, centerY, size);

  }


  // create the image of a single hexagon cell.
  private void createCell(double centerX, double centerY, double size) {
    // calculate vertices for the cell
    double angleDegree = 60.0;
    double angleRadius = Math.PI / 180.0;
    moveTo(centerY + size * Math.sin(0), centerX + size);
    for (int i = 1; i <= 6; i++) {
      lineTo(centerY + size
              * Math.sin(angleDegree * i * angleRadius), centerX + size
              * Math.cos(angleDegree * i * angleRadius));
    }
    closePath();
  }

  /**
   * set the color for this cell.
   */
  public void setColor(Color color) {
    this.c = color;
  }


  /**
   * Draw this hexagon cell.
   *
   * @param g2 - graphics.
   */
  public void draw(Graphics2D g2) {
    // Save the current state of the graphics context
    Color originalColor = g2.getColor();

    // Fill the hexagon
    g2.setColor(this.c);
    g2.fill(this);

    // Draw the hexagon outline
    g2.setColor(Color.BLACK);
    g2.draw(this);

    // Restore the original state
    g2.setColor(originalColor);
  }

}
