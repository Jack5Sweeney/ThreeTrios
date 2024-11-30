package provider.src.threetrios.model;

import threetrios.controller.ModelStatusListener;

/**
 * Model interface for general game functionality.
 */
public interface Model extends ReadonlyThreeTriosModel {

  /**
   * rows, columns, holesAndCells are to be passed when startGame calls setupGame. Handsize check
   * based on rows/columns. Calculate hand size, create deck,deal cards to players,
   * call the board to set up.
   *
   * @param rows          is the number of rows of our game.
   * @param columns       is the number of columns of our game.
   * @param holesAndCells is the layout of holes and card cells for our board.
   * @throws IllegalStateException    if game already started.
   * @throws IllegalArgumentException if rows x columns is even, or either are 0.
   */
  void startGame(int rows, int columns, String holesAndCells, String cardFile);

  /**
   * Tells a card to move to given coordinates.
   *
   * @param rows    is the x where the card should move to.
   * @param columns is the y where the card should move to.
   * @param card    is the card the user wants to place
   * @throws IllegalArgumentException if board (row,column) is a hole.
   * @throws IllegalArgumentException if coordinates are out of bounds.
   */
  void placeCard(int rows, int columns, Card card);

  /**
   * Tells card to flip to opposing team to whatever it is currently assigned.
   *
   * @throws IllegalArgumentException if coordinates are out of bounds.
   */
  void flipCard(Card card);

  /**
   * Used to check if game is over. Ends when all empty card cells are filled.
   *
   * @return true if the game is over, false if the game is ongoing or not started yet.
   */
  boolean isGameOver();

  /**
   * When a card does adjacent battle, it compares its respective values to the surrounding cards,
   * and also has possibility for combo battles.
   *
   * @param card is the card that will adjacent battle its surrounding cards.
   */
  int adjacentBattle(Card card, int row, int col, boolean flipCard, int howManyCardsCouldFlip);

  /**
   * For testing purposes only.
   * This makes testing game scenarios much simpler, will be deleted in future implementation.
   * Changes the turn flag to the opposing player to whichever player is current.
   */
  void changeTurn();

  ///////////////////////////////////////////////////

  /**
   * Adds given listener to models list of listeners.
   * @param listener is the listener to add to the list.
   */
  void addListener(ModelStatusListener listener);

  /**
   * Give all listeners an updated snapshot of the game, by calling modelStatus method
   * from ModelStatusListener.
   */
  void updateListeners();

  void setBoard(Object[][] board);
}
