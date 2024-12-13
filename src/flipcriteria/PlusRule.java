package flipcriteria;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import card.ICard;
import card.Direction;
import model.IModel;
import player.IPlayer;


/**
 * Implements the "Plus" rule.
 * If at least two adjacent opponent's cards have the same sum of attack values in opposing
 * directions with the placed card, those opponent's cards are flipped to the current player.
 */
public class PlusRule implements ICriteria {

  @Override
  public List<int[]> applyFlipCriteria(IModel model, ICard placedCard, int row, int col,
                                       IPlayer player) {
    int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    Direction[] dirEnums = {Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST};
    Map<Integer, List<int[]>> sumMap = new HashMap<>();
    Set<int[]> flippedPositionsSet = new HashSet<>();

    // Iterate over adjacent positions
    for (int directionIndex = 0; directionIndex < directions.length; directionIndex++) {
      int adjRow = row + directions[directionIndex][0];
      int adjCol = col + directions[directionIndex][1];

      if (model.isValidPosition(adjRow, adjCol) && model.getCardAt(adjRow, adjCol) != null) {
        ICard adjacentCard = model.getCardAt(adjRow, adjCol);

        if (!adjacentCard.getPlayerColor().equals(placedCard.getPlayerColor())) {
          Direction placedDir = dirEnums[directionIndex];
          Direction adjOppositeDir = model.getOppositeDirection(placedDir);

          int placedValue = placedCard.getDirectionsAndValues().get(placedDir).getValue();
          int opposingValue = adjacentCard.getDirectionsAndValues().get(adjOppositeDir).getValue();
          int sum = placedValue + opposingValue;

          sumMap.putIfAbsent(sum, new ArrayList<>());
          sumMap.get(sum).add(new int[]{adjRow, adjCol});
        }
      }
    }

    // Flip cards if there are at least two with the same sum
    for (Map.Entry<Integer, List<int[]>> entry : sumMap.entrySet()) {
      List<int[]> positionsWithSameSum = entry.getValue();
      if (positionsWithSameSum.size() >= 2) {
        flippedPositionsSet.addAll(positionsWithSameSum);
      }
    }

    return new ArrayList<>(flippedPositionsSet);
  }


}
