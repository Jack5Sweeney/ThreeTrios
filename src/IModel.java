/**
 * Interface representing the model for a game.
 * Defines methods to control and manage the game's state, including starting the game,
 * placing cards on the board, retrieving board data, and checking game status.
 */
public interface IModel {

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

  /**
   * Updates the board to reflect changes made by placing a card, including interactions
   * with adjacent cards.
   *
   * @param cardAt the card placed on the board
   * @param row    the row index of the card's position
   * @param col    the column index of the card's position
   */
  void updateBoard(Card cardAt, int row, int col);

  /**
   * Retrieves a new instance of the red player, including their color and hand.
   *
   * @return a new {@link IPlayer} instance representing the red player
   */
  IPlayer getRedPlayer();

  /**
   * Retrieves a new instance of the blue player, including their color and hand.
   *
   * @return a new {@link IPlayer} instance representing the blue player
   */
  IPlayer getBluePlayer();

  /**
   * Provides a deep copy of the current board with all cards.
   *
   * @return a 2D array of {@link Card} objects representing the board, with all attributes copied
   */
  Card[][] getBoard();

  /**
   * Returns the board availability information.
   *
   * @return a 2D array of {@link CellType} objects representing the availability status of each cell
   */
  CellType[][] getBoardAvailability();

  /**
   * Checks if the game has started and throws an exception if it has not.
   *
   * @throws IllegalStateException if the game has not started
   */
  boolean checkGameStarted();

  /**
   * Checks if the game is over and throws an exception if it is.
   *
   * @throws IllegalStateException if the game is over
   */
  boolean checkGameOver();

  /**
   * Retrieves the winning player of the game.
   * If the game ends in a draw, throws an exception as there is no winning player.
   *
   * @return a new {@link IPlayer} instance representing the winning player
   * @throws IllegalStateException if the game ended in a draw with no winning player
   */
  IPlayer getWinningPlayer();
}