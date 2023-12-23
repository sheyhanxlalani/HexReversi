package adapter;

import java.awt.geom.Point2D;
import java.util.Optional;

import javax.swing.Timer;

import controller.ModelActionListener;
import controller.PlayerActionListener;
import game.utility.GameState;
import game.utility.ICell;
import game.ReversiModel;
import player.Player;
import player.PlayerColor;
import provider.cs3500.reversi.model.CustomPoint2D;
import provider.cs3500.reversi.model.ModelFeatures;
import provider.cs3500.reversi.model.PlayerActions;
import provider.cs3500.reversi.model.PlayerTile;
import provider.cs3500.reversi.view.ReversiGraphicsView;
import provider.cs3500.reversi.view.ViewFeatures;


/**
 * The adapter of the provider's controller. Implement their ViewFeatures, ModelFeatures,
 * PlayerActions and our PlayerActionListener.
 * Act as a bridge between our controller.
 */
public class ControllerImpl implements ViewFeatures, ModelFeatures, PlayerActions,
        PlayerActionListener, ModelActionListener {
  private final ReversiModel model;
  private final ReversiGraphicsView view;
  private final Player player;

  /**
   * The controller adapter that allows their controller to work with our model.
   *
   * @param model  - our model.
   * @param player - a plaery.
   * @param view   - their view.
   */
  public ControllerImpl(ReversiModel model, Player player, ReversiGraphicsView view) {
    this.model = model;
    this.view = view;
    this.player = player;
    this.model.setModelListener(this);
    this.player.setActionListener(this);
    this.view.addFeatures(this);
    this.view.setVisible(true);
  }

  @Override
  public void turnChanged(PlayerTile t) {
    model.switchPlayer();
    view.update();
    view.showMessage("Its your turn!");
  }

  @Override
  public void pass() {
    model.wannaPass();
    view.showMessage("Player: " + player.getColor().toString() + " passed!");
  }

  @Override
  public void placeTile(Optional<CustomPoint2D> move) {
    if (move.isPresent()) {
      try {
        ICell cell = model.getCell(move.get().getDim2(), move.get().getDim1());
        model.makeMove(cell);
        view.update();
      } catch (IllegalArgumentException e) {
        view.showMessage("Invalid move for: " + player.getColor().toString());
      }
    }
  }


  @Override
  public void selectTile(Point2D logicalP) {
    model.getCell((int) logicalP.getX(), (int) logicalP.getY());
    view.selectTile(logicalP);
  }

  @Override
  public void onMoveMade(int q, int r) {
    // Logic to handle a move made by the player
    ICell cell = model.getCell(q, r);
    if (model.isValidMove(cell, model.getCurrentState())) {
      model.makeMove(cell);
      view.update();
    }
  }

  @Override
  public void onPassTurn() {
    model.wannaPass();
    view.update();
    view.showMessage("Pass!");
  }

  @Override
  public void indicateMyColor() {
    view.showMessage("Your color is: " + player.getColor().toString());
  }

  @Override
  public void onPlayerTurnChanged(PlayerColor playerColor) {
    if (model.isGameOver()) {
      onGameOver(model.returnWinner());
      return;
    }
    if (this.player.isAI() && this.player.getColor().equals(playerColor)) {
      placeAIMove();
    } else if (this.player.getColor().equals(playerColor)) {
      view.showMessage("It's your turn!");
    }
    view.update();
  }

  // Initiates the AI move-making process
  private void placeAIMove() {
    Timer timer = new Timer(1000, e -> {
      if (!model.isGameOver()) {
        try {
          player.play(model.getCurrentState());
        } catch (IllegalStateException ex) {
          onPassTurn();
        }
        view.update();
      }
    });
    timer.setRepeats(false);
    timer.start();
  }

  @Override
  public void onGameOver(GameState winner) {
    model.isGameOver();
    view.showMessage("Game over.");
  }

  @Override
  public void onScoreUpdate() {
    view.update();
  }

  @Override
  public void onGameStarted() {
    model.gameStarted();
    view.showMessage("Game started");
  }
}
