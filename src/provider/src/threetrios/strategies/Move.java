package provider.src.threetrios.strategies;

import provider.src.threetrios.model.Card;

/**
 * A move is something that is created after a strategy makes it decision on where a player should
 * move. Because of that, a move is not only coordinates of where a card should be placed but also
 * what card should be placed there, as some cards are stronger than others in certain positions.
 */
public class Move implements IMove {
  private final int row;
  private final int col;
  private final Card card;

  /**
   * Construct a move with a row, col, and card.
   * @param row is the intened row of a move.
   * @param col is the intended col of a move.
   * @param card is the intended card of a move.
   */
  public Move(int row, int col, Card card) {
    this.row = row;
    this.col = col;
    this.card = card;
  }

  @Override
  public double calcDistanceFromTopLeftCorner() {
    return Math.sqrt(Math.pow(row, 2) + Math.pow(col, 2));
  }

  @Override
  public int getRow() {
    return this.row;
  }

  @Override
  public int getCol() {
    return this.col;
  }

  @Override
  public Card getCard() {
    return this.card;
  }

  @Override
  public String toString() {
    return "Row: " + row + " Col: " + col + " Card: " + card;
  }
}