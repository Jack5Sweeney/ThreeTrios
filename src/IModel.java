/**
 * Interface representing the model for a game.
 * Defines methods to control and manage the game's state.
 */
public interface IModel {

  /**
   * Starts the game by setting up the board dimensions and other configurations.
   * @throws IllegalArgumentException if the config file is not found, has an invalid format,
   *                                  or there is an issue reading the file
   */
  void startGame();


}
