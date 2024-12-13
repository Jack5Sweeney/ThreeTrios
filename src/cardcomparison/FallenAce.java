package cardcomparison;

import card.Direction;
import card.ICard;

/**
 * Implements the "Fallen Ace" variant rule as a card comparison strategy.
 *
 * <p>The "Fallen Ace" rule modifies the base strategy:
 * <ul>
 *   <li>An Ace (value 1) defeats a card with a value of 10.</li>
 *   <li>A card with a value of 10 loses to an Ace (value 1).</li>
 *   <li>For all other cases, the comparison uses the base strategy.</li>
 * </ul>
 */
public class FallenAce implements CardComparisonStrategy {

  /** The base strategy for standard comparisons. */
  private final CardComparisonStrategy baseStrategy;

  /**
   * Creates a new FallenAce strategy with the given base strategy.
   *
   * @param baseStrategy the strategy to use for standard comparisons
   * @throws IllegalArgumentException if {@code baseStrategy} is {@code null}
   */
  public FallenAce(CardComparisonStrategy baseStrategy) {
    if (baseStrategy == null) {
      throw new IllegalArgumentException("Base strategy cannot be null.");
    }
    this.baseStrategy = baseStrategy;
  }

  /**
   * Compares two cards based on the "Fallen Ace" rule.
   *
   * <p>Rules:
   * <ul>
   *   <li>If the placed card is an Ace (1) and the adjacent card is 10, the placed card wins.</li>
   *   <li>If the placed card is 10 and the adjacent card is an Ace (1), the placed card loses.</li>
   *   <li>All other cases use the base strategy.</li>
   * </ul>
   *
   * @param placedCard the card being placed
   * @param adjacentCard the adjacent card
   * @param placedDirection the direction of the placed card
   * @param adjacentDirection the direction of the adjacent card
   * @return {@code true} if the placed card wins; {@code false} otherwise
   * @throws IllegalArgumentException if any parameter is {@code null}
   */
  @Override
  public boolean compare(
          ICard placedCard,
          ICard adjacentCard,
          Direction placedDirection,
          Direction adjacentDirection) {

    if (placedCard == null || adjacentCard == null || placedDirection == null ||
            adjacentDirection == null) {
      throw new IllegalArgumentException("Arguments cannot be null.");
    }

    int placedValue = placedCard.getDirectionsAndValues()
            .get(placedDirection)
            .getValue();

    int adjacentValue = adjacentCard.getDirectionsAndValues()
            .get(adjacentDirection)
            .getValue();

    // Fallen Ace special rules
    if (placedValue == 1 && adjacentValue == 10) {
      return true;
    } else if (placedValue == 10 && adjacentValue == 1) {
      return false;
    } else {
      return baseStrategy.compare(placedCard, adjacentCard, placedDirection, adjacentDirection);
    }
  }
}
