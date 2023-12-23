
package controller;

import javax.swing.Timer;

import decorator.HintDecorator;
import game.utility.GameState;
import game.ReversiModel;
import player.Player;
import player.PlayerColor;
import view.ReversiView;


/**
 * The Controller for the Reversi game.
 * Take in the user clicks and play the game.
 * Then modify the GUI view.
 */
public class Controller implements ViewFeatures, ModelActionListener, PlayerActionListener {
  private final ReversiView view;
  private final Player player;
  private ReversiModel reversi;

  private static final int aiDelay = 1000; // in miliseconds.

  /**
   * Controller constructor.
   *
   * @param reversi - ar reversi model
   * @param player  - a player model.
   * @param view    - the view of the reversi game.
   */
  public Controller(ReversiModel reversi, Player player, ReversiView view) {
    this.player = player;
    this.view = view;
    this.reversi = new HintDecorator(reversi);
    this.view.setViewFeaturesListener(this);
    this.player.setActionListener(this);
    this.reversi.setModelListener(this);
    this.indicateMyColor();
    this.onScoreUpdate();
    this.onGameStarted();
  }


  /////////VIEW FEATURES//////
  @Override
  public void selectedCell(int q, int r) {
    try {
      if (!reversi.isValidMove(reversi.getCell(q, r), reversi.getCurrentState())) {
        view.showMessage("Invalid move for " + this.player.getColor().toString());
      } else {
        System.out.println("Cell being passed in makeMove is: " + q + ", " + r);
        reversi.makeMove(reversi.getCell(q, r));
        endTurn();
      }
    } catch (Exception e) {
      System.out.println("Controller show as: " + q + ", " + r);
      view.showMessage("An error occurred: " + e.getMessage());
    }
    view.refresh();
  }

  @Override
  public void quit() {
    System.exit(0);
  }

  @Override
  public void pass() {
    // Check if it's currently the player's turn.
    if (!isMoveAllowed()) {
      view.showMessage("It's not your turn!");
      return;
    }

    if (!reversi.hasValidMoves()) {
      reversi.wannaPass();
      endTurn();
      view.showMessage("No valid moves. Turn passed to the next player.");
      endTurn();
    } else {

      view.showMessage("Cannot pass turn right now. You have valid moves!");
    }
  }

  private boolean isMoveAllowed() {
    return reversi.getCurrentState() == GameState.BLACK_TURN
            && player.getColor() == PlayerColor.BLACK
            || reversi.getCurrentState() == GameState.WHITE_TURN
            && player.getColor() == PlayerColor.WHITE;
  }

  @Override
  public void endTurn() {
    if (reversi.isGameOver()) {
      onGameOver(reversi.returnWinner());

    }
    view.refresh();
  }

  @Override
  public void toggleBlackHints() {
    try {
      reversi.toggleBlackHints();
      String message = reversi.isBlackHintsEnabled() ? "Hints are being shown for Black player"
              : "Hints are disabled for Black player";
      view.showMessage(message);
    } catch (Exception e) {
      view.showMessage("Not your turn, can't show you hints for your opponents");
    }

    view.refresh(); // Refresh the view to reflect the change
  }

  @Override
  public void toggleWhiteHints() {
    try {
      reversi.toggleWhiteHints();
      String message = reversi.isWhiteHintsEnabled() ? "Hints are being shown for White player"
              : "Hints are disabled for White player";
      view.showMessage(message);
    } catch (Exception e) {
      view.showMessage(" Not your turn! Can't show hints for your opponents!\n");
    }
    view.refresh(); // Refresh the view to reflect the change
  }


  ///////MODEL LISTENERS////////
  @Override
  public void onPlayerTurnChanged(PlayerColor playerColor) {
    view.indicatePlayerTurn(playerColor);

    if (reversi.isGameOver()) {
      onGameOver(reversi.returnWinner());
      return;
    }

    if (this.player.isAI() && this.player.getColor().equals(playerColor)) {
      placeAIMove();
      endTurn();

    } else if (this.player.getColor().equals(playerColor)) {
      view.showMessage("It's your turn!");
    }

    view.refresh();
  }


  @Override
  public void onGameStarted() {
    view.showMessage("Game has started!");
    Player firstPlayer = reversi.getCurrentPlayer();
    System.out.println("Current player: " + firstPlayer.toString());
    view.indicatePlayerTurn(firstPlayer.getColor());
    view.refresh();

    if (firstPlayer.isAI()) {
      placeAIMove();
    }
  }

  // Initiates the AI move-making process
  private void placeAIMove() {
    Timer timer = new Timer(aiDelay, e -> {
      if (!reversi.isGameOver()) {
        try {
          player.play(reversi.getCurrentState());
        } catch (IllegalStateException ex) {
          onPassTurn();

        }
        view.refresh();
      }
    });
    timer.setRepeats(false);
    timer.start();
  }

  @Override
  public void onGameOver(GameState winner) {
    if (reversi.isGameOver()) {
      switch (winner) {
        case BLACK_WIN:
          view.showMessage("Game Over! Black wins!");
          break;
        case WHITE_WIN:
          view.showMessage("Game Over! White wins!");
          break;
        default:
          view.showMessage("Game Over! It's a tie!");
          break;
      }
    }
  }

  @Override
  public void onScoreUpdate() {
    int[] scores = reversi.getScores();
    int blackScore = scores[0];
    int whiteScore = scores[1];
    view.updateScore(blackScore, whiteScore);
    view.refresh();
  }

  //////PLAYER LISTENER//////
  // automatically pass
  @Override
  public void onMoveMade(int q, int r) {
    reversi.makeMove(reversi.getCell(q, r));
    if (reversi.isGameOver()) {
      onGameOver(reversi.returnWinner());
    } else {
      endTurn();
    }
    view.refresh();

  }


  @Override
  public void indicateMyColor() {
    view.indicatePlayerColor(player.getColor());
  }

  @Override
  public void onPassTurn() {
    if (!reversi.hasValidMoves()) {
      reversi.wannaPass();
      endTurn();

      view.showMessage("No valid moves. Turn passed to the next player.");
      reversi.switchPlayer();
    } else {
      view.showMessage("Error: Attempted to pass turn when moves are available.");
    }
  }


}
