import controller.ModelActionListener;
import controller.PlayerActionListener;
import org.junit.Before;
import org.junit.Test;

import player.Player;
import player.PlayerColor;
import controller.Controller;
import view.ReversiView;

import static game.utility.GameState.BLACK_WIN;
import static org.junit.Assert.assertEquals;


/**
 * Test the controller.
 */
public class ControllerTest {
  private StringBuilder modelLog;
  private StringBuilder playerLog;
  private StringBuilder viewLog;
  private ModelActionListener modelListener;
  private PlayerActionListener playerListener;
  private MockHexReversi mockModel;

  @Before
  public void init() {
    modelLog = new StringBuilder();
    playerLog = new StringBuilder();
    viewLog = new StringBuilder();

    mockModel = new MockHexReversi(5, modelLog);
    Player mockPlayer = new MockPlayer(playerLog);
    Player anotherMockPlayer = new MockPlayer(new StringBuilder());

    mockModel.setBlackPlayer(mockPlayer);
    mockModel.setWhitePlayer(anotherMockPlayer);
    mockModel.setCurrentPlayer(mockPlayer);

    ReversiView mockView = new MockView(mockModel, viewLog);
    modelListener = new Controller(mockModel, mockPlayer, mockView);
    playerListener = (PlayerActionListener) modelListener;
  }



  @Test
  public void testTurnMade() {
    modelListener.onGameStarted();
    modelListener.onPlayerTurnChanged(PlayerColor.BLACK);
    assertEquals("Adding listener as called in view\n" +
            "Indicating player color: WHITE\n" +
            "Updating score: 0, 0\n" +
            "Refreshing the view\n" +
            "Showing message: Game has started!\n" +
            "Indicating player turn: WHITE\n" +
            "Refreshing the view\n" +
            "Showing message: Game has started!\n" +
            "Indicating player turn: WHITE\n" +
            "Refreshing the view\n" +
            "Indicating player turn: BLACK\n" +
            "Showing message: Game Over! It's a tie!\n", viewLog.toString());
  }

  @Test
  public void testGameStarted() {
    modelListener.onGameStarted();
    assertEquals("Adding listener as called in view\n" +
            "Indicating player color: WHITE\n" +
            "Updating score: 0, 0\n" +
            "Refreshing the view\n" +
            "Showing message: Game has started!\n" +
            "Indicating player turn: WHITE\n" +
            "Refreshing the view\n" +
            "Showing message: Game has started!\n" +
            "Indicating player turn: WHITE\n" +
            "Refreshing the view\n", viewLog.toString());
  }

  @Test
  public void testMoveMade() {
    mockModel.gameStarted();
    modelListener.onGameStarted();
    modelListener.onGameStarted();
    playerListener.onMoveMade(1, 1);
    assertEquals("Adding listener as called in player\n" +
            "Getting color\n" +
            "Getting color\n" +
            "Getting color\n" +
            "Checking if it is AI\n" +
            "Getting color\n" +
            "Getting color\n" +
            "Checking if it is AI\n" +
            "Getting color\n" +
            "Getting color\n" +
            "Checking if it is AI\n", playerLog.toString());
  }

  @Test
  public void testColorIndication() {
    playerListener.indicateMyColor();
    assertEquals("Adding listener as called in player\n" +
            "Getting color\n" +
            "Getting color\n" +
            "Getting color\n" +
            "Checking if it is AI\n" +
            "Getting color\n", playerLog.toString());
  }

  @Test
  public void testPass() {
    playerListener.onPassTurn();
    assertEquals("Adding listener as called in player\n" +
            "Getting color\n" +
            "Getting color\n" +
            "Getting color\n" +
            "Checking if it is AI\n", playerLog.toString());
  }

  @Test
  public void testOnGameOver() {
    modelListener.onGameOver(BLACK_WIN);
    assertEquals("Adding listener as called in view\n" +
            "Indicating player color: WHITE\n" +
            "Updating score: 0, 0\n" +
            "Refreshing the view\n" +
            "Showing message: Game has started!\n" +
            "Indicating player turn: WHITE\n" +
            "Refreshing the view\n" +
            "Showing message: Game Over! Black wins!\n", viewLog.toString());
  }


  @Test
  public void testOnGameStarted() {
    this.modelListener.onGameStarted();
    assertEquals("Adding listener as called in view\n" +
            "Indicating player color: WHITE\n" +
            "Updating score: 0, 0\n" +
            "Refreshing the view\n" +
            "Showing message: Game has started!\n" +
            "Indicating player turn: WHITE\n" +
            "Refreshing the view\n" +
            "Showing message: Game has started!\n" +
            "Indicating player turn: WHITE\n" +
            "Refreshing the view\n", viewLog.toString());

  }

  @Test
  public void testScoreUpdating() {
    this.modelListener.onScoreUpdate();
    assertEquals("Adding listener as called in view\n" +
            "Indicating player color: WHITE\n" +
            "Updating score: 0, 0\n" +
            "Refreshing the view\n" +
            "Showing message: Game has started!\n" +
            "Indicating player turn: WHITE\n" +
            "Refreshing the view\n" +
            "Updating score: 0, 0\n" +
            "Refreshing the view\n", viewLog.toString());

  }

  @Test
  public void testGameOver() {
    mockModel.isGameStarted();
    modelListener.onGameStarted();
    modelListener.onGameOver(BLACK_WIN);
    assertEquals("get current player: WHITE\n" +
            "get current player: WHITE\n" +
            "Game is over", modelLog.toString());


  }

  @Test
  public void testPlayerPassTurn() {
    this.playerListener.onPassTurn();
    assertEquals("Adding listener as called in player\n" +
            "Getting color\n" +
            "Getting color\n" +
            "Getting color\n" +
            "Checking if it is AI\n", playerLog.toString());

  }

  @Test
  public void testPlayerMoveMade() {

    mockModel.getCell(1, 1);
    this.playerListener.onMoveMade(1, 1);
    assertEquals("Adding listener as called in player\n" +
            "Getting color\n" +
            "Getting color\n" +
            "Getting color\n" +
            "Checking if it is AI\n", playerLog.toString());
  }

  @Test
  public void testPlayerColor() {
    this.playerListener.indicateMyColor();
    assertEquals("Adding listener as called in player\n" +
            "Getting color\n" +
            "Getting color\n" +
            "Getting color\n" +
            "Checking if it is AI\n" +
            "Getting color\n", playerLog.toString());
  }
}