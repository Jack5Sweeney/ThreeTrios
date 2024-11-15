package model;

/**
 * Read-only interface representing the view-only model for a game.
 * Defines methods to observe the game's state without the ability to modify it.
 */
public interface ReadOnlyIModel {
  /**
   * Retrieves the card at the specified board position. If a card exists at the
   * given location, a new instance of the card is returned to prevent unintended
   * modifications. If no card is present, an exception is thrown.
   *
   * @param boardRow the row index on the board where the card is located
   * @param boardCol the column index on the board where the card is located
   * @return a new instance of the {@link CardImpl} at the specified position
   * @throws IllegalArgumentException if there is no card at the specified position
   */
  CardImpl getCardAt(int boardRow, int boardCol);

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
   * @return a 2D array of {@link CardImpl} objects representing the board, with all
   *         attributes copied
   */
  ICard[][] getBoard();

  /**
   * Returns the board availability information.
   *
   * @return a 2D array of {@link CellType} objects representing the availability status of each
   *         cell
   */
  CellType[][] getBoardAvailability();

  /**
   * Checks if the game has started.
   *
   * @return true if the game has started, false otherwise
   */
  boolean checkGameStarted();

  /**
   * Checks if the game is over.
   *
   * @return true if the game is over, false otherwise
   */
  boolean checkGameOver();

  /**
   * Retrieves a copy of the winning player of the game. If the game is currently in a draw it
   * returns null.
   *
   * @return a new {@link IPlayer} instance representing the winning player
   * @throws IllegalStateException if the game is not started.
   */
  IPlayer getWinningPlayer();

  /**
   * Retrieves the score of a specified player based on the number of cards they own on the board.
   *
   * @param playerColor the color of the player whose score is to be retrieved
   * @return the score of the specified player
   */
  int getPlayerScore(PlayerColor playerColor);

  /**
   * Calculates the number of cards a player can flip by placing a given card at a
   * specified position.
   *
   * @param row  the row on the board where the card will be placed
   * @param col  the column on the board where the card will be placed
   * @param card the card being placed
   * @return the number of cards that would be flipped by this move
   */
  int calculateFlips(int row, int col, ICard card);


  /**
   * Starts the game by configuring the board, setting up initial conditions, and preparing
   * the card deck.
   *
   * @throws IllegalArgumentException if there is an issue reading the configuration file
   *                                  or if the file has an invalid format
   */
  void startGame();

  /**
   * Method to get the current players color for the view to visualize the current player.
   *
   * @return the current players color
   */
  PlayerColor getCurrentPlayerColor();

  /**
   * Gets the player who is currently set to place a card on the board.
   *
   * @return the player who is about to place a card
   */
  IPlayer getPlayerToPlace();

  /**
   * Gets the index of the card that is set to be placed on the board from the player's hand.
   *
   * @return the index of the card to place
   */
  int getCardIndexToPlace();

}

