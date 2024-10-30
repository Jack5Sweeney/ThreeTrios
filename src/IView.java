/**
 * Interface representing the view for a game.
 * Defines methods to display the current game state and provide output to the user.
 */
public interface IView {

  /**
   * Provides a string representation of the current game state.
   *
   * @return a {@code String} representing the board, player statuses, or other elements
   *         relevant to the current state of the game
   */
  String toString();

  /**
   * Switches the view to the next player by updating the current player
   * displayed in the view. This method checks the current player's color:
   * if the player is RED, it switches to BLUE, and if the player is BLUE,
   * it switches to RED. This allows the game to alternate views between
   * players based on the turn sequence.
   */
  void switchPlayerView();

}