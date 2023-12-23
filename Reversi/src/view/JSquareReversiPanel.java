package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Point;


import javax.swing.JPanel;


import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import controller.ViewFeatures;
import game.utility.GameState;
import game.utility.ICell;
import game.utility.PlayerState;
import game.ReadonlyReversiModel;
import player.PlayerColor;

/**
 * Represent a Square Reversi panel. Extends JPanel.
 */
public class JSquareReversiPanel extends JPanel implements KeyListener {
  private ReadonlyReversiModel model;
  private ViewFeatures controller;
  private PlayerColor playerColor;

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
  private Map<ICell, SquareCellImage> cellImages = new HashMap<>();

  /**
   * Constructor for JSquareReversi.
   * @param model - a square reversi model.
   * @param controller - a controller.
   * @param color - a player color.
   */
  public JSquareReversiPanel(ReadonlyReversiModel model, ViewFeatures controller,
                             PlayerColor color) {
    this.model = model;
    this.controller = controller;
    this.playerColor = color;
    this.setMinimumSize(new Dimension(500, 500));
    this.panelHeight = this.getMinimumSize().height;
    this.panelWidth = this.getMinimumSize().width;
    setBackground(Color.DARK_GRAY);
    this.model = Objects.requireNonNull(model);
    this.setLayout(null); // for absolute positioning
    this.requestFocusInWindow(true);
    this.setFocusable(true);
    createCellImages();
    addFocusListener(new FocusAdapter() {
      @Override
      public void focusGained(FocusEvent e) {
        System.out.println("Panel gained focus " + playerColor.toString());
      }

      @Override
      public void focusLost(FocusEvent e) {
        System.out.println("Panel lost focus " + playerColor.toString());
      }
    });

    this.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        boolean cellFound = false;
        int offsetX = (getWidth() - panelWidth) / 2 - 170;
        int offsetY = (getHeight() - panelHeight) / 2 - 20;
        Point translatedPoint = new Point(e.getX() - offsetX, e.getY() - offsetY);
        for (Map.Entry<ICell, SquareCellImage> entry : cellImages.entrySet()) {
          ICell cell = entry.getKey();
          SquareCellImage cellImg = entry.getValue();
          if (cellImg.isPointInside(translatedPoint)) {
            if (cell.equals(selectedCell)) {
              deselectCell(selectedCell);
            } else {
              if (selectedCell != null) {
                deselectCell(selectedCell);
              }
              System.out.println("Selected cell: " + cell.getQ() + ", " + cell.getR());
              selectCell(cell);
            }
            selectedCell = cell;
            cellFound = true;
            break;
          }
        }
        if (!cellFound) {
          System.out.println("MROOEORJOE");
        }
        repaint();
      }
    });

    this.addKeyListener(this);
  }

  // In the selectCell method:
  private void selectCell(ICell clickedCell) {
    if (selectedCell != null) {
      selectedCell.setSelected(false);
      updateCellColor(selectedCell, Color.LIGHT_GRAY); // Resetting the previous selected cell color
    }
    clickedCell.setSelected(true);
    selectedCell = clickedCell;
    updateCellColor(selectedCell, Color.CYAN); // Set the selected cell color to cyan
    repaint();
  }

  private void updateCellColor(ICell cell, Color color) {
    SquareCellImage cellImage = cellImages.get(cell);
    if (cellImage != null) {
      cellImage.setColor(color);
    }
  }


  // deselectCell method - reset color of deselected cell
  private void deselectCell(ICell currentCell) {
    if (currentCell != null) {
      updateCellColor(currentCell, Color.LIGHT_GRAY);
      currentCell.setSelected(false);
      this.selectedCell = null;
    }
  }

  private void createCellImages() {
    int size = model.getSize();
    int cellSize = calculateCellSize(panelWidth, panelHeight, size);
    for (int q = 0; q < size; q++) {
      for (int r = 0; r < size; r++) {
        ICell cell = model.getCell(q, r);
        Color cellColor = Color.LIGHT_GRAY;
        SquareCellImage cellImage = new SquareCellImage(cellColor, q, r, cellSize);
        cellImages.put(cell, cellImage);
      }
    }
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    // Calculate the cell size
    int cellSize = calculateCellSize(panelWidth, panelHeight, model.getSize());

    // Calculate the total size of the board
    int totalBoardWidth = cellSize * model.getSize();
    int totalBoardHeight = cellSize * model.getSize();

    // Calculate offsets to center the board
    int offsetX = (this.getWidth() - totalBoardWidth) / 2;
    int offsetY = (this.getHeight() - totalBoardHeight) / 2;

    // Apply the offset
    g2d.translate(offsetX, offsetY);

    // Draw each cell
    for (Map.Entry<ICell, SquareCellImage> entry : cellImages.entrySet()) {
      SquareCellImage cellImage = entry.getValue();
      cellImage.draw(g2d); // Ensure cellImage.draw() uses internal cellSize for drawing
      drawDisc(g2d, entry.getKey(), cellImage);

      // Draw hints if enabled
      if (selectedCell != null
              && selectedCell.getPlayer() == PlayerState.EMPTY
              && ((model.isBlackHintsEnabled()
              && model.getCurrentState() == GameState.BLACK_TURN)
              || (model.isWhiteHintsEnabled()
              && model.getCurrentState() == GameState.WHITE_TURN))) {
        int flippableDiscs = model.getCaptureCells(selectedCell, model.getCurrentState());
        drawHint(g2d, selectedCell, cellSize, flippableDiscs);
      }
    }
  }

  private void drawHint(Graphics2D g2d, ICell cell, int cellSize, int flippableDiscs) {
    // Set font color and size
    g2d.setColor(Color.BLACK);
    g2d.setFont(new Font("Arial", Font.BOLD, cellSize / 2));

    // Calculate position to draw the hint
    String hint = String.valueOf(flippableDiscs);
    FontMetrics metrics = g2d.getFontMetrics();
    int x = cell.getQ() * cellSize + (cellSize - metrics.stringWidth(hint)) / 2;
    int y = cell.getR() * cellSize + ((cellSize - metrics.getHeight()) / 2) + metrics.getAscent();

    // Draw the hint
    g2d.drawString(hint, x, y);
  }

  private void drawDisc(Graphics2D g2d, ICell cell, SquareCellImage cellImage) {
    // Check if the cell is occupied by a player
    if (cell.getPlayer() == PlayerState.EMPTY) {
      return; // If the cell is empty, no disc needs to be drawn
    }

    // Determine the color of the disc based on the player
    Color discColor = (cell.getPlayer() == PlayerState.BLACK) ? Color.BLACK : Color.WHITE;

    // Calculate the center and radius of the disc
    int cellSize = cellImage.getCellSize();
    int x = cell.getQ() * cellSize; // Cell's top-left x coordinate
    int y = cell.getR() * cellSize; // Cell's top-left y coordinate
    double radius = cellSize * 0.4; // Radius is set to 40% of the cell size
    double centerX = x + radius + 3; // Center X of the disc
    double centerY = y + radius + 3; // Center Y of the disc

    // Draw the disc
    g2d.setColor(discColor);
    g2d.fillOval((int) (centerX - radius),
            (int) (centerY - radius),
            (int) (2 * radius),
            (int) (2 * radius));
  }


  private int calculateCellSize(int panelWidth, int panelHeight, int boardSize) {
    int cellWidth = panelWidth / boardSize / 2;
    int cellHeight = panelHeight / boardSize / 2;
    return Math.min(cellWidth, cellHeight);
  }

  @Override
  public void keyTyped(KeyEvent e) {
    // do nothing
  }

  @Override
  public void keyPressed(KeyEvent e) {
    System.out.println("Key pressed");
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
        if (selectedCell != null) {
          controller.selectedCell(selectedCell.getQ(), selectedCell.getR());
          deselectCell(selectedCell);
        }
        break;

      case KeyEvent.VK_G:
        controller.toggleBlackHints();
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
      int newQ = selectedCell.getQ();
      int newR = selectedCell.getR() + 1;

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
      int newQ = selectedCell.getQ() - 1;
      int newR = selectedCell.getR();

      if (model.cellExists(newQ, newR)) {
        selectCell(model.getCell(newQ, newR));
        this.repaint();

      }
    }
  }

  // move up from the selected cell
  private void moveCellRight() {
    if (selectedCell != null) {
      int newQ = selectedCell.getQ() + 1;
      int newR = selectedCell.getR();

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
      int newQ = selectedCell.getQ();
      int newR = selectedCell.getR() - 1;

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
  }

  public void activatePanel() {
    requestFocusInWindow(); // Call this method when it's this player's turn
  }
}
