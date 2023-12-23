package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Dimension;


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import controller.ViewFeatures;
import game.ReadonlyReversiModel;
import player.PlayerColor;

/**
 * Represent a JView of the hexagon reversi game.
 * Extends the JFrame and implements ReversiView
 */
public class HexReversiView extends JFrame implements ReversiView {
  private final ReadonlyReversiModel model;
  private JLabel scoreLabel;
  private JLabel playerTurnLabel;
  private JLabel playerColor;
  private ViewFeatures controller;
  private JHexReversiPanel panel;
  private PlayerColor color;



  /**
   * Constructor for hex reversi game.
   *
   * @param model - the model of the displayed game.
   */
  public HexReversiView(ReadonlyReversiModel model, PlayerColor color) {
    this.model = model;
    this.color = color;

    this.setTitle("Hexagon Reversi game");
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
      panel = new JHexReversiPanel(model, controller, color);
      this.add(panel, BorderLayout.CENTER);
      this.pack();
    }
  }
}
