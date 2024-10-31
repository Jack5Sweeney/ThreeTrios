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
   * displayed in the view. THIS METHOD IS FOR TESTING, WE KNOW THAT SWITCHING THE POV
   * SHOULD BE HANDLED IN THE CONTROLLER
   */
  void switchPlayerView();

}