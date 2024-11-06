package view;

/**
 * Interface representing the view for a game.
 * Defines methods to display the current game state and provide output to the user.
 */
public interface ITextView {

  /**
   * Provides a string representation of the current game state.
   *
   * @return a {@code String} representing the board, player statuses, or other elements
   *         relevant to the current state of the game
   */
  String toString();
}