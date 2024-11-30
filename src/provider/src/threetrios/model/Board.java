package provider.src.threetrios.model;

import java.util.List;

/**
 * Interface for our general board implementations.
 */
public interface Board {

  /**
   * Create and fill 2d array based on given dimensions and holesandcards.
   *
   * @return the number of cardcells in the given String
   * @throws IllegalArgumentException if number of cardcells on board is even, or 0.
   */
  int setUp();

  /**
   * Return a copy of the card at (xCord, yCord).
   *
   * @param row is the intended x cordinate. 0 index based
   * @param col is the intended y cordinate. 0 index based
   * @return a copy of the card at (xCord, yCord)
   * @throws IllegalArgumentException if card is out of bounds.
   * @throws IllegalStateException if the board is not set up.
   * @throws IllegalStateException if you are filling board with null values.
   */
  Object getCardAt(int row, int col);

  /**
   * Creates a list of all adjacent cards to the given card.
   *
   * @param card is the card you want to find adjacent cards for.
   * @return a list of all adjacent cards to the given card.
   * @throws IllegalStateException if the board is not set up.
   * @throws IllegalArgumentException if the given card is null.
   */
  List<Object> getAdjacentCards(Card card);

  /**
   * Get adjacent cards given a coordinate.
   * @param row is the row you want to check adjacent cards of.
   * @param col is the col you want to check adjacent cards of.
   * @return a list of adjacent cards of the card at (row,col)
   */
  List<Object> getAdjacentCards(int row, int col);

  /**
   * Makes a string representation of a current board layout of cards, holes, and open cardcells.
   *
   * @return a string representation of a current board layout of cards, holes, and open cardcells.
   * @throws IllegalStateException if board is not set up
   */
  String boardToString();

  /**
   * Places a given card at a given row and column, in our Object[][].
   *
   * @param row  is the row you want to place your card at [0 index based]
   * @param col  is the row you want to place your card at [0 index based]
   * @param card is the card you want to place, must be in a players hand.
   * @throws IllegalArgumentException if the row is out of bounds
   * @throws IllegalArgumentException if the col is out of bounds
   * @throws IllegalArgumentException if given card is null
   * @throws IllegalStateException if board is not set up
   */
  void setCardAt(int row, int col, Card card);

  /**
   * Retruns true if red player won. False if blue won.
   * @return true if red player won.
   */
  boolean redPlayerWins();

  /**
   * Get a players score, equivalent to how many cards they have showing on the board.
   * @param teamColor is the team you want to check the score of.
   * @return a players score, equivalent to how many cards they have showing on the board.
   */
  int getPlayerScore(TeamColor teamColor);

  /**
   * Get a copy of a boarad 2d array.
   * @return a copy of a boarad 2d array.
   */
  Object[][] getBoardArray();

  /**
   * Return how many rows a board is.
   * @return how many rows a board is.
   */
  int getRow();

  /**
   * Return how many boards a board is.
   * @return how many boards a board is.
   */
  int getCol();

  void setBoard(Object[][] board);
}
