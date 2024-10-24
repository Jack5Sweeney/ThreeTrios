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
   * Places a card on the board at the specified row and column.
   *
   * @param boardRow  the row on the board where the card is to be placed
   * @param boardCol  the column on the board where the card is to be placed
   * @param cardIndex the index of the card in the player's hand to place on the board
   * @param player    the {@link PlayerColor} of the player placing the card
   * @throws IllegalArgumentException if the specified position is invalid or the card index is out of bounds
   */
  void placeCard(int boardRow, int boardCol, int cardIndex, PlayerColor player);
}
