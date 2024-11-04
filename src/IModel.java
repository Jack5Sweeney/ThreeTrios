/**
 * Interface representing the full model for a game, extending the read-only model
 * and adding mutator methods to control and manage the game's state.
 */
public interface IModel extends ReadOnlyIModel{

  /**
   * Starts the game by configuring the board, setting up initial conditions, and preparing
   * the card deck.
   *
   * @throws IllegalArgumentException if there is an issue reading the configuration file
   *                                  or if the file has an invalid format
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
  void placeCard(int boardRow, int boardCol, int cardIndexInHand, IPlayer player);
}