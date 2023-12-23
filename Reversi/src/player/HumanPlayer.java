package player;

import controller.PlayerActionListener;
import game.utility.GameState;


/**
 * To represent a human player of reversi game.
 * This class implements the Player interface and encapsulates the behavior and properties of
 * a human player. This class also handles with the game board through the player listener.
 */
public class HumanPlayer implements Player {
  private PlayerActionListener actionListener;
  private boolean isMyTurn;
  private boolean isAI;
  private PlayerColor color;


  /**
   * Human player constructor.
   */
  public HumanPlayer(PlayerColor color) {
    this.isMyTurn = false;
    this.isAI = false;
    this.color = color;
  }

  @Override
  public void play(GameState player) {
    isMyTurn = true;
  }

  /**
   * When the human player clicks on a cell, get its q and r, then
   * trigger action listener to response to this move.
   *
   * @param q - q of this hex cell.
   * @param r - r of this hex cell.
   */
  public void playerMakeMove(int q, int r) {
    if (actionListener != null) {
      actionListener.onMoveMade(q, r);
    }
  }

  @Override
  public void setActionListener(PlayerActionListener listener) {
    this.actionListener = listener;
  }

  @Override
  public void setColor(PlayerColor color) {
    this.color = color;
  }

  @Override
  public PlayerColor getColor() {
    return this.color;
  }

  @Override
  public boolean isAI() {
    return this.isAI;
  }
}