package provider.src.threetrios.view;

import java.io.IOException;

/**
 * View interface for textual visualization of a three trios game.
 */
public interface View {

  /**
   * Renders a model in some manner (e.g. as text, or as graphics, etc.).
   * This is not used yet, but will be important in future assignments for our
   * full MVC.
   *
   * @throws IOException if the rendering fails for some reason
   */
  void render() throws IOException;
}
