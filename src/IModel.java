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
   * Retrieves the card at the specified board position. If a card exists at the
   * given location, a new instance of the card is returned to prevent unintended
   * modifications. If no card is present, an exception is thrown.
   *
   * @param boardRow the row index on the board where the card is located
   * @param boardCol the column index on the board where the card is located
   * @return a new instance of the {@link Card} at the specified position
   * @throws IllegalArgumentException if there is no card at the specified position
   */
  Card getCardAt(int boardRow, int boardCol);

  void updateBoard(Card cardAt, int i, int i1);


  void flipCardOwnership(Card cardAt, int i, int i1, PlayerColor playerColor);

  Object getRedPlayer();

  Object getBluePlayer();

  /**
   * Returns the current board with cards on it.
   *
   * @return a 2D array representing the board with cards
   */

  Card[][] getBoard();

  /**
   * Returns the board availability information.
   *
   * @return a 2D array representing the board cell types (e.g., empty, hole, etc.)
   */

  CellType[][] getBoardAvailability();
}
