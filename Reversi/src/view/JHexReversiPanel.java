package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.FontMetrics;

import javax.swing.JPanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import controller.ViewFeatures;
import game.utility.ICell;
import game.utility.PlayerState;
import game.ReadonlyReversiModel;
import player.PlayerColor;


/**
 * Represent the panel of the hexagon reversi game. Manage the panel here.
 */
public class JHexReversiPanel extends JPanel implements KeyListener {
  private final ReadonlyReversiModel model;
  private final PlayerColor color;

  /**
   * Height of the panel.
   */
  public final int panelHeight;

  /**
   * Width of the panel.
   */
  public final int panelWidth;
  private ICell selectedCell; // used to store the selected cell.
  // a map used to store the cell and its image.
  private Map<ICell, HexCellImage> cellImages = new HashMap<>();

  private ViewFeatures controller;

  /**
   * Constructor for JHexReversiPanel.
   *
   * @param model - the model of a reversi game.
   * @param color - take in a color of a player.
   */
  public JHexReversiPanel(ReadonlyReversiModel model, ViewFeatures controller, PlayerColor color) {
    this.color = color;
    this.setMinimumSize(new Dimension(500, 500));
    this.panelHeight = this.getMinimumSize().height;
    this.panelWidth = this.getMinimumSize().width;
    setBackground(Color.DARK_GRAY);
    this.model = Objects.requireNonNull(model);
    this.controller = controller;
    //this.featuresListeners = new ArrayList<>(); for later
    this.setLayout(null); // for absolute positioning
    this.setFocusable(true);
    this.requestFocusInWindow();
    this.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        for (Map.Entry<ICell, HexCellImage> entry : cellImages.entrySet()) {
          ICell cell = entry.getKey();

          HexCellImage cellImg = entry.getValue();
          if (cellImg.contains(e.getPoint())) {
            if (cell.equals(selectedCell)) {
              deselectCell(selectedCell);
              break;
            }
            cell.setSelected(true);
            if (selectedCell != null) {
              selectCell(selectedCell);
            }
            selectedCell = cell;
            System.out.println("HMMM");
            repaint();
          } else {
            System.out.println("MROOEORJOE");
            repaint();
          }
        }
      }
    });
  }

  private void drawHint(Graphics2D g2d, Point2D.Double center, double hexSize, int number) {

    g2d.setColor(Color.BLACK);

    Font font = new Font("Arial", Font.BOLD, (int) (hexSize / 1.5));
    g2d.setFont(font);

    FontMetrics metrics = g2d.getFontMetrics(font);
    String textToDisplay = number > 0 ? String.valueOf(number) : "0";
    int x = (int) (center.x - metrics.stringWidth(textToDisplay) / 2 + 5);
    int y = (int) (center.y - metrics.getHeight() / 2 + metrics.getAscent() - 7);

    // Draw the hint text.
    g2d.drawString(textToDisplay, y, x);

  }


  // use to draw cell Image for each cell in the board.
  private void drawEachCellImage(int currentWidth, int currentHeight) {
    double hexSize = currentWidth / 25.0;
    double offsetX = (currentWidth / 2.0) - 20;
    double offsetY = (currentHeight / 2.0);

    cellImages.clear(); // Clear the existing cell images
    for (ICell cell : model.getBoard()) {
      Point2D.Double center = convertAxialToPixel(cell.getQ(), cell.getR(),
              hexSize, offsetX, offsetY);
      HexCellImage cellImg = new HexCellImage(Color.LIGHT_GRAY, cell.getQ(),
              cell.getR(), hexSize, center.x, center.y);
      cellImages.put(cell, cellImg);
    }
  }

  // select this cell. change the state of the cell.
  private void selectCell(ICell clickedCell) {
    if (selectedCell != null) {
      System.out.println("Cell clicked: " + clickedCell.getQ() + ", " + clickedCell.getR());
      selectedCell.setSelected(false); // Select the new cell
      clickedCell.setSelected(true);
    }
    selectedCell = clickedCell;
    System.out.println("Select cell " + selectedCell.getQ() + ", " + selectedCell.getR());
    HexCellImage image = cellImages.get(selectedCell);
    if (image != null) {
      System.out.println(image);
      image.setColor(Color.CYAN);
    }
  }

  // deselect a cell
  private void deselectCell(ICell currentCell) {
    if (currentCell != null) {
      currentCell.setSelected(false);
      this.selectedCell = null;
    }

  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    // Dynamically calculate the size of the panel
    int currentWidth = getWidth();
    int currentHeight = getHeight();
    double hexSize = currentWidth / 25.0;
    double offsetX = (currentWidth / 2.0) - 10;
    double offsetY = (currentHeight / 2.0);

    drawEachCellImage(currentWidth, currentHeight);


    for (ICell cell : model.getBoard()) {
      Point2D.Double center = convertAxialToPixel(cell.getQ(),
              cell.getR(), hexSize, offsetX, offsetY);
      Color hexColor = Color.LIGHT_GRAY;
      Color circleColor = null;

      if (cell.getPlayer() == PlayerState.BLACK) {
        circleColor = Color.BLACK;
        if (cell.equals(selectedCell)) {
          hexColor = Color.CYAN;
        }
      } else if (cell.getPlayer() == PlayerState.WHITE) {
        circleColor = Color.WHITE;
        if (cell.equals(selectedCell)) {
          hexColor = Color.CYAN;
        }
      } else if (cell.equals(selectedCell)) {
        hexColor = Color.CYAN;
        circleColor = Color.CYAN;
      }

      // Draw cell
      HexCellImage cellImg = new HexCellImage(hexColor, cell.getQ(),
              cell.getR(), hexSize, center.x, center.y);
      cellImg.draw(g2d);

      if (circleColor != null) {
        drawCircleInsideCell(g2d, center, hexSize, circleColor);
      }

      if (color == PlayerColor.BLACK && model.isBlackHintsEnabled()
              || color == PlayerColor.WHITE && model.isWhiteHintsEnabled()) {
        if ((selectedCell != null) && selectedCell.getPlayer().equals(PlayerState.EMPTY)) {
          int flippableDiscs = model.getCaptureCells(selectedCell, model.getCurrentState());
          Point2D.Double cellCenter = convertAxialToPixel(selectedCell.getQ(),
                  selectedCell.getR(), hexSize, offsetX, offsetY);
          drawHint(g2d, cellCenter, hexSize, flippableDiscs);
        }
      }

    }


    g2d.dispose();
  }


  // draw the player discs.
  private void drawCircleInsideCell(Graphics g2d, Point2D.Double center, double hexSize, Color
          color) {
    int radius = (int) (hexSize / 2);
    int x = (int) (center.x - radius);
    int y = (int) (center.y - radius);

    g2d.setColor(color);
    g2d.fillOval(y, x, radius * 2, radius * 2);
  }

  /**
   * Converts axial or offset coordinates to pixel coordinates for drawing the hexagonal cells.
   *
   * @param q       The q-coordinate in the axial or offset coordinate system.
   * @param r       The r-coordinate in the axial or offset coordinate system.
   * @param size    The size of the hexagons.
   * @param offsetX The offset in the x-direction to center the board.
   * @param offsetY The offset in the y-direction to center the board.
   * @return The center point in pixel coordinates for the given hexagon.
   */
  private Point2D.Double convertAxialToPixel(int q, int r, double size, double offsetX,
                                             double offsetY) {
    double x = size * (3.0 / 2 * q);
    double y = size * (Math.sqrt(3) / 2 * q + Math.sqrt(3) * r);
    return new Point2D.Double(x + offsetX, y + offsetY);
  }


  @Override
  public void keyTyped(KeyEvent e) {
    // do nothing
  }

  @Override
  public void keyPressed(KeyEvent e) {
    switch (e.getKeyCode()) {

      case KeyEvent.VK_UP:
        moveCellUp();
        System.out.println("Up should be repaint");
        KeyListener[] listeners = this.getKeyListeners();
        System.out.println("Number of KeyListeners: " + listeners.length);

        break;

      case KeyEvent.VK_DOWN:
        moveCellDown();
        break;

      case KeyEvent.VK_LEFT:
        moveCellLeft();
        break;

      case KeyEvent.VK_RIGHT:
        moveCellRight();
        break;

      case KeyEvent.VK_SPACE:
        controller.pass();
        break;

      case KeyEvent.VK_ENTER:
        System.out.println("\nWhen hit enter, selected cell is: "
                + selectedCell.getQ() + ", " + selectedCell.getR());
        if (selectedCell != null) {
          controller.selectedCell(selectedCell.getQ(), selectedCell.getR());
          deselectCell(selectedCell);
        }
        break;

      case KeyEvent.VK_G:
        controller.toggleBlackHints();
        System.out.println("\nblck hint");
        break;

      case KeyEvent.VK_H:
        controller.toggleWhiteHints();
        break;
      default:
    }
    this.repaint();
  }

  // move right from the selected cell
  private void moveCellDown() {
    if (selectedCell != null) {
      int newQ = selectedCell.getQ() + 1;
      int newR = selectedCell.getR() - 1;

      if (model.cellExists(newQ, newR)) {
        System.out.println("new cell: " + newQ + ", " + newR);
        selectCell(model.getCell(newQ, newR));
        this.repaint();
      }
    }
  }


  // move the selected cell to the left
  private void moveCellLeft() {
    if (selectedCell != null) {
      int newQ = selectedCell.getQ();
      int newR = selectedCell.getR() - 1;

      if (model.cellExists(newQ, newR)) {
        selectCell(model.getCell(newQ, newR));
        this.repaint();

      }
    }
  }

  // move up from the selected cell
  private void moveCellRight() {
    if (selectedCell != null) {
      int newQ = selectedCell.getQ();
      int newR = selectedCell.getR() + 1;

      if (model.cellExists(newQ, newR)) {
        System.out.println(newQ + " " + newR);
        selectCell(model.getCell(newQ, newR));
        this.repaint();
      }
    }
  }

  // move down from the selected cell
  private void moveCellUp() {
    if (selectedCell != null) {
      int newQ = selectedCell.getQ() - 1;
      int newR = selectedCell.getR() + 1;

      if (model.cellExists(newQ, newR)) {
        selectCell(model.getCell(newQ, newR));
        this.repaint();
      }
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    // do nothing
  }

  public void setController(ViewFeatures controller) {
    this.controller = controller;
    this.addKeyListener(this); // Make sure panel is listening to key events
  }
}