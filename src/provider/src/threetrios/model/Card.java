package provider.src.threetrios.model;

/**
 * Card interface for a geneneral card implementation.
 */
public interface Card {

  /**
   * The integer value of this cards north value, 1-9 plus A.
   * Numbers are represented by themselves and A is represented by 10.
   *
   * @return The integer value of this cards north value, 1-9 plus A.
   */
  int getNorthValue();

  /**
   * The integer value of this cards north value, 1-9 plus A.
   * Numbers are represented by themselves and A is represented by 10.
   *
   * @return The integer value of this cards north value, 1-9 plus A.
   */
  int getSouthValue();

  /**
   * The integer value of this cards north value, 1-9 plus A.
   * Numbers are represented by themselves and A is represented by 10.
   *
   * @return The integer value of this cards north value, 1-9 plus A.
   */
  int getEastValue();

  /**
   * The integer value of this cards north value, 1-9 plus A.
   * Numbers are represented by themselves and A is represented by 10.
   *
   * @return The integer value of this cards north value, 1-9 plus A.
   */
  int getWestValue();

  /**
   * Change this cards team color to the opposing team.
   */
  void flip();

  /**
   * Prints the numbers of a card in north, east, south, west order.
   * then prints team color, either "RED" or "BLUE". A is represented as 10.
   *
   * @return a 5 or 6 character representation of a card
   */
  String toString();

  /**
   * Return the team color associated with a card.
   *
   * @return the team color associated with a card.
   */
  TeamColor getColor();

  /**
   * Return the string name associated with a card.
   *
   * @return the string name associated with a card.
   */
  String getName();
}
