package game.utility;


/**
 * Represent game state of a reversi game.
 * It can be either white win, black win. Also used to indicate the player turns.
 */
public enum GameState {
  WHITE_TURN, BLACK_TURN,
  TIE, WHITE_WIN, BLACK_WIN
}
