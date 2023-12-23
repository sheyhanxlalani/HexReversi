import org.junit.Test;

import java.util.Optional;

import game.utility.GameState;
import game.utility.ICell;
import game.utility.PlayerState;
import game.ReadonlyReversiModel;
import strategy.MinimaxStrategy;
import strategy.ReversiStrategy;

import static org.junit.Assert.assertEquals;

/**
 * Represent an example of how Minimax strategy works.
 */
public class MinimaxTest {
  @Test
  public void testMinimax() {
    StringBuilder log = new StringBuilder();
    ReadonlyReversiModel mock = new MockHexReversi(3, log);
    ICell cell15 = mock.getCell(1, 0);
    cell15.setPlayer(PlayerState.WHITE);
    ICell cell16 = mock.getCell(2, -1);
    cell16.setPlayer(PlayerState.WHITE);
    ICell cell17 = mock.getCell(0, 1);
    cell17.setPlayer(PlayerState.BLACK);

    GameState opponent = GameState.WHITE_TURN;

    ReversiStrategy strategy = new MinimaxStrategy(3);
    Optional<ICell> chosen = strategy.determineMove(mock, opponent);
    if (chosen.isPresent()) {
      ICell expectedBestMove = mock.getCell(3, -2);
      assertEquals(expectedBestMove, chosen.get());
    }

  }
}
