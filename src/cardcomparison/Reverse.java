package cardcomparison;

import card.Direction;
import card.ICard;

/**
 * Implements the "Reverse" variant rule as a card comparison strategy.
 *
 * <p>The "Reverse" rule modifies the comparison such that:
 * <ul>
 *   <li>The placed card wins if its value in a specific direction is strictly less than
 *       the value of the adjacent card in the corresponding direction.</li>
 *   <li>Otherwise, the placed card loses.</li>
 * </ul>
 * This is the opposite of the normal comparison rule.
 */
public class Reverse implements CardComparisonStrategy {

  /**
   * Constructs a {@code Reverse} strategy.
   *
   * <p>This constructor is intentionally empty because the "Reverse" strategy does not
   * require any additional state or initialization.
   */
  public Reverse() {
    // Empty constructor: No state or dependencies are required for this strategy.
  }

  /**
   * Compares two cards based on the "Reverse" rule.
   *
   * @param placedCard the card being placed on the board
   * @param adjacentCard the card adjacent to the placed card
   * @param placedDirection the direction of the placed card being compared
   * @param adjacentDirection the direction of the adjacent card being compared
   * @return {@code true} if the placed card's value in the specified direction is less than
   *         the adjacent card's value; {@code false} otherwise
   * @throws IllegalArgumentException if any of the parameters are {@code null}
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

    // Reverse comparison: Placed card wins if its value is less than the adjacent card's value.
    return placedValue < adjacentValue;
  }
}
