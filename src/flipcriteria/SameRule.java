package flipcriteria;

import card.ICard;
import card.Direction;
import model.IModel;
import player.IPlayer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Implements the "Same" rule.
 * If at least two adjacent cards share the same value in the opposing direction of the placed card,
 * those opponent's cards among them are flipped to the current player.
 */
public class SameRule implements ICriteria {

  @Override
  public List<int[]> applyFlipCriteria(IModel model, ICard placedCard, int row, int col,
                                       IPlayer player) {
    int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    Direction[] dirEnums = {Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST};
    Set<int[]> matchingPositions = new HashSet<>();

    // Debugging log: Placed card details
    System.out.println("Applying SameRule for placed card at (" + row + ", " + col + ") by "
            + player.getPlayerColor());

    // Iterate over adjacent positions
    for (int directionIndex = 0; directionIndex < directions.length; directionIndex++) {
      int adjRow = row + directions[directionIndex][0];
      int adjCol = col + directions[directionIndex][1];

      if (model.isValidPosition(adjRow, adjCol)) {
        ICard adjacentCard = model.getCardAt(adjRow, adjCol);

        if (adjacentCard != null &&
                !adjacentCard.getPlayerColor().equals(placedCard.getPlayerColor())) {
          Direction placedDir = dirEnums[directionIndex];
          Direction adjOppositeDir = model.getOppositeDirection(placedDir);

          int placedValue = placedCard.getDirectionsAndValues().get(placedDir).getValue();
          int opposingValue = adjacentCard.getDirectionsAndValues().get(adjOppositeDir).getValue();

          // Debugging log: Value comparisons
          System.out.println("Comparing placed value (" + placedValue + ") with opposing value " +
                  "(" + opposingValue + ") at (" + adjRow + ", " + adjCol + ")");

          if (placedValue == opposingValue) {
            matchingPositions.add(new int[]{adjRow, adjCol});
          }
        }
      }
    }

    // Debugging log: Matching positions
    System.out.println("Matching positions: " + matchingPositions.size());

    // Flip cards only if there are at least two matches
    if (matchingPositions.size() < 2) {
      return new ArrayList<>();
    }

    List<int[]> flippedPositions = new ArrayList<>();
    for (int[] pos : matchingPositions) {
      ICard adjacentCard = model.getCardAt(pos[0], pos[1]);
      if (!adjacentCard.getPlayerColor().equals(player.getPlayerColor())) {
        flippedPositions.add(pos);

        // Debugging log: Flip action
        System.out.println("Flipping card at (" + pos[0] + ", " + pos[1] + ")");
      }
    }

    return flippedPositions;
  }
}
