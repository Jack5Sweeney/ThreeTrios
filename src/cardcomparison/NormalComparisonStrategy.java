package cardcomparison;

import card.Direction;
import card.ICard;

/**
 * A comparison strategy that evaluates whether a placed card's value
 * in a specific direction is greater than the adjacent card's value
 * in the corresponding direction.
 */
public class NormalComparisonStrategy implements CardComparisonStrategy {

  /**
   * Compares two cards based on their values in the specified directions.
   *
   * <p>This strategy checks if the value of the placed card in the given
   * direction is greater than the value of the adjacent card in its corresponding
   * direction.
   *
   * @param placedCard the card being placed
   * @param adjacentCard the card adjacent to the placed card
   * @param placedDirection the direction of the placed card to compare
   * @param adjacentDirection the direction of the adjacent card to compare
   * @return {@code true} if the placed card's value is greater than the adjacent
   *         card's value in the respective directions; {@code false} otherwise
   */
  @Override
  public boolean compare(
      ICard placedCard,
      ICard adjacentCard,
      Direction placedDirection, 
      Direction adjacentDirection) {

    int placedValue = placedCard.getDirectionsAndValues()
        .get(placedDirection)
        .getValue();

    int adjacentValue = adjacentCard.getDirectionsAndValues()
        .get(adjacentDirection)
        .getValue();

    return placedValue > adjacentValue;
  }
}
