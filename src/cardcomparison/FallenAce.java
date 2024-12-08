package cardcomparison;

import card.Direction;
import card.ICard;

public class FallenAce implements CardComparisonStrategy {
  private final CardComparisonStrategy baseStrategy;

  public FallenAce(CardComparisonStrategy baseStrategy) {
    this.baseStrategy = baseStrategy;
  }

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

    if (placedValue == 1 && adjacentValue == 10) {
      return true;
    }
    else if (placedValue == 10 && adjacentValue == 1) {
      return false;
    }
    else {
      return baseStrategy.compare(placedCard, adjacentCard, placedDirection, adjacentDirection);
    }
  }
}
