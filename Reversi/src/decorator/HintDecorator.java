package decorator;

import java.util.Set;

import controller.ModelActionListener;
import game.utility.GameState;
import game.utility.ICell;
import game.ReversiModel;
import player.Player;

/**
 * Hint decorator - Allows the users to display hints on the selected cell.
 * This takes in a reversi model and allows users to enable hints.
 */
public class HintDecorator implements ReversiModel {
  private ReversiModel decoratedModel;
  private boolean blackHintsEnabled;

  private boolean whiteHintsEnabled;


  /**
   * Hint Decorator constructor.
   * @param decoratedModel - reversi model.
   */
  public HintDecorator(ReversiModel decoratedModel) {
    this.decoratedModel = decoratedModel;
    this.blackHintsEnabled = false;
    this.whiteHintsEnabled = false;
  }


  @Override
  public int[] getScores() {
    return decoratedModel.getScores();
  }

  @Override
  public boolean isGameOver() throws IllegalStateException {
    return decoratedModel.isGameOver();
  }

  @Override
  public int getSize() {
    return decoratedModel.getSize();
  }

  @Override
  public ICell getCell(int q, int r) {
    return decoratedModel.getCell(q, r);
  }

  @Override
  public boolean cellExists(int q, int r) {
    return decoratedModel.cellExists(q, r);
  }

  @Override
  public GameState returnWinner() {
    return decoratedModel.returnWinner();
  }

  @Override
  public GameState getCurrentState() {
    return decoratedModel.getCurrentState();
  }

  @Override
  public boolean isValidMove(ICell cell, GameState player) {
    return decoratedModel.isValidMove(cell, player);
  }

  @Override
  public int getCaptureCells(ICell cell, GameState player) {
    return decoratedModel.getCaptureCells(cell, player);
  }

  @Override
  public void wannaPass() {
    decoratedModel.wannaPass();
  }

  @Override
  public boolean isGameStarted() {
    return decoratedModel.isGameStarted();
  }

  @Override
  public ReversiModel getClone() {
    return decoratedModel.getClone();
  }

  @Override
  public void toggleBlackHints() {
    this.blackHintsEnabled = !this.blackHintsEnabled;
    if (decoratedModel instanceof ReversiModel) {
      decoratedModel.toggleBlackHints();
    }
  }

  @Override
  public void toggleWhiteHints() {
    this.whiteHintsEnabled = !this.whiteHintsEnabled;
    if (decoratedModel instanceof ReversiModel) {
      decoratedModel.toggleWhiteHints();
    }
  }

  @Override
  public boolean isBlackHintsEnabled() {
    return blackHintsEnabled;
  }

  @Override
  public boolean isWhiteHintsEnabled() {
    return whiteHintsEnabled;
  }

  @Override
  public int getCountPass() {
    return decoratedModel.getCountPass();
  }


  @Override
  public void gameStarted() {
    decoratedModel.gameStarted();
  }

  @Override
  public Set<ICell> getBoard() {
    return decoratedModel.getBoard();
  }

  @Override
  public void initializeBoard() {
    decoratedModel.initializeBoard();
  }

  @Override
  public void makeMove(ICell clicked) throws IllegalStateException, IllegalArgumentException {
    decoratedModel.makeMove(clicked);
  }

  @Override
  public void setModelListener(ModelActionListener listener) {
    decoratedModel.setModelListener(listener);
  }

  @Override
  public boolean hasValidMoves() {
    return decoratedModel.hasValidMoves();
  }

  @Override
  public void switchPlayer() {
    decoratedModel.switchPlayer();
  }

  @Override
  public void setBlackPlayer(Player player) {
    decoratedModel.setBlackPlayer(player);
  }

  @Override
  public void setWhitePlayer(Player player) {
    decoratedModel.setWhitePlayer(player);
  }

  @Override
  public void addModelListener(ModelActionListener listener) {
    decoratedModel.addModelListener(listener);
  }

  @Override
  public Player getCurrentPlayer() {
    return decoratedModel.getCurrentPlayer();
  }
}
