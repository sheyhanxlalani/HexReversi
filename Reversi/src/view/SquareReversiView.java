package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;


import controller.ViewFeatures;
import game.ReadonlyReversiModel;
import player.PlayerColor;

/**
 * Square Reversi View, extends the JFrame. Create a window on the screen
 * for the square reversi game. This class is responsible for drawing the board
 * on the windown and makes the keyEveents/mouseClicks work.
 */
public class SquareReversiView extends JFrame implements ReversiView {
  private final ReadonlyReversiModel model;
  private JLabel scoreLabel;
  private JLabel playerTurnLabel;
  private JLabel playerColor;
  private ViewFeatures controller;
  private JSquareReversiPanel panel;
  private PlayerColor color;

  /**
   * Constructor.
   * @param model - reversi model.
   * @param color - color of the player.
   */
  public SquareReversiView(ReadonlyReversiModel model, PlayerColor color) {
    this.model = model;
    this.color = color;

    this.setTitle("Square Reversi game");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Initialize the status labels
    this.scoreLabel = new JLabel();
    this.playerTurnLabel = new JLabel("Player turn: ");
    this.playerColor = new JLabel("Your color is: ");

    this.setLayout(new BorderLayout());
    // Add the panel for the reversi board
    this.setPreferredSize(new Dimension(500, 500));
    // Add the status labels to the bottom of the window
    JPanel statusPanel = new JPanel(new GridLayout(1, 2));
    statusPanel.add(scoreLabel);
    statusPanel.add(playerTurnLabel);
    statusPanel.add(playerColor);
    this.add(statusPanel, BorderLayout.SOUTH);

    // Pack and display the frame
    this.pack();
    this.setVisible(true);
    this.setViewFeaturesListener(controller);
  }

  @Override
  public void refresh() {
    this.repaint();
  }

  @Override
  public void showMessage(String message) {
    System.out.print(message);
    JOptionPane.showMessageDialog(this, message,
            "Message", JOptionPane.ERROR_MESSAGE);
  }


  @Override
  public void updateScore(int blackScore, int whiteScore) {
    scoreLabel.setText("Black: " + blackScore + " White: " + whiteScore);
  }

  @Override
  public void indicatePlayerTurn(PlayerColor player) {
    playerTurnLabel.setText("Player turn: " + player.toString());
    if (this.color == player) {
      panel.activatePanel(); // Activate the panel if it's this player's turn
    }
  }

  @Override
  public void indicatePlayerColor(PlayerColor player) {
    playerColor.setText("Your color is: " + player.toString());
  }

  @Override
  public void setViewFeaturesListener(ViewFeatures controller) {
    this.controller = controller;
    if (panel != null) {
      // Pass the controller to the panel
      panel.setController(controller);
    } else {
      panel = new JSquareReversiPanel(model, controller, color);
      this.add(panel, BorderLayout.CENTER);
      this.pack();
    }
  }
}
