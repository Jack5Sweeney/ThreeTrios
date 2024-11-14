package strategies;

/**
 * Represents a placement on the board, including the row and column
 * position, along with the index of the card to be placed.
 */
public class Placement {
  public final int row;
  public final int column;
  public final int cardIndex;

  /**
   * Constructs a new {@code Placement} with the specified row, column, and card index.
   *
   * @param row       the row position on the board for the placement
   * @param column    the column position on the board for the placement
   * @param cardIndex the index of the card in the player's hand to be placed
   */
  public Placement(int row, int column, int cardIndex) {
    this.row = row;
    this.column = column;
    this.cardIndex = cardIndex;
  }
}

