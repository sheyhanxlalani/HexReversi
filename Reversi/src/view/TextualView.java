package view;

import java.io.IOException;

/**
 * to represent a textual view of the game.
 */
public interface TextualView {

  /**
   * render the view of the game as text or graphics,...
   *
   * @throws IOException of rendering fails for some reasons.
   */
  void render() throws IOException;
}
