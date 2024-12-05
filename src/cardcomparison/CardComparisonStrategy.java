package cardcomparison;

import card.Direction;
import card.ICard;

/**
 * Interface representing a strategy for comparing cards.
 *
 * <p>This interface defines the contract for comparing two cards based on their placement
 * and adjacency directions.
 */
public interface CardComparisonStrategy {

  /**
   * Compares two cards based on their placement and adjacency directions.
   *
   * @param placedCard the card being placed
   * @param adjacentCard the card adjacent to the placed card
   * @param placedDirection the direction of the placed card
   * @param adjacentDirection the direction of the adjacent card
   * @return {@code true} if the cards satisfy the comparison strategy; {@code false} otherwise
   */
  boolean compare(
      ICard placedCard,
      ICard adjacentCard,
      Direction placedDirection,
      Direction adjacentDirection);
}
