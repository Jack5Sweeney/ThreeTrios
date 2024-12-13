package cardcomparison;

import card.Direction;
import card.ICard;

public class Reverse implements CardComparisonStrategy {

  public Reverse() {
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

    return placedValue < adjacentValue;
  }
}
