/**
 * Interface representing the model for a game.
 * Defines methods to control and manage the game's state, including starting the game and
 * placing cards on the board.
 */
public interface IModel {

  /**
   * Starts the game by setting up the board dimensions and other configurations.
   *
   * @throws IllegalArgumentException if the configuration file is not found, has an invalid format,
   *                                  or if there is an issue reading the file
   */
  void startGame();

  /**
   * Places a card on the board for a specified player at the given row and column
   * position, removing it from the player's hand.
   *
   * @param boardRow        the row on the board where the card will be placed
   * @param boardCol        the column on the board where the card will be placed
   * @param cardIndexInHand the index of the card in the player's hand
   * @param player          the player who is placing the card
   * @throws IllegalArgumentException if the player is null, if the card index is invalid,
   *                                  or if the placement on the board is invalid
   */
  void placeCard(int boardRow, int boardCol, int cardIndexInHand, PlayerImpl player);

  /**
   * Retrieves the card at the specified location on the board.
   * Returns a new instance of the card to prevent unintended modifications.
   *
   * @param boardRow the row of the card's position on the board
   * @param boardCol the column of the card's position on the board
   * @return a copy of the {@link Card} at the specified board position
   * @throws IllegalArgumentException if the specified board position is out of bounds
   */
  Card getCardAt(int boardRow, int boardCol);
}
