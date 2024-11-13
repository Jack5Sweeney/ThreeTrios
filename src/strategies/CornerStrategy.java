package strategies;

import model.CellType;
import model.Direction;
import model.ICard;
import model.IModel;
import model.PlayerImpl;

public class CornerStrategy implements IStrategy {

  public Placement chooseMove(IModel model, PlayerImpl player) {
    int minFlipRisk = Integer.MAX_VALUE;
    Placement bestPlacement = null;

    int boardHeight = model.getBoard().length;
    int boardWidth = model.getBoard()[0].length;

    // Define the four corner positions and their exposed directions
    int[][] corners = {
            {0, 0},                     // top-left (exposes EAST and SOUTH)
            {0, boardWidth - 1},        // top-right (exposes WEST and SOUTH)
            {boardHeight - 1, 0},       // bottom-left (exposes EAST and NORTH)
            {boardHeight - 1, boardWidth - 1}  // bottom-right (exposes WEST and NORTH)
    };

    // Exposed directions corresponding to each corner
    Direction[][] exposedDirections = {
            {Direction.EAST, Direction.SOUTH},     // top-left
            {Direction.WEST, Direction.SOUTH},     // top-right
            {Direction.EAST, Direction.NORTH},     // bottom-left
            {Direction.WEST, Direction.NORTH}      // bottom-right
    };

    CellType[][] boardAvailability = model.getBoardAvailability();


    // Iterate through each card in hand and each corner position
    for (int i = 0; i < corners.length; i++) {
      int cornerRow = corners[i][0];
      int cornerCol = corners[i][1];
      Direction[] directions = exposedDirections[i];

      try {
        model.getCardAt(cornerRow, cornerCol);
      } catch (IllegalArgumentException e) {

        if (boardAvailability[cornerRow][cornerCol] != CellType.EMPTY) {
          continue;
        }

        for (int cardIndex = 0; cardIndex < player.getHand().size(); cardIndex++) {
          ICard card = player.getHand().get(cardIndex);

          // Calculate the flip risk based on the highest value in the exposed directions
          int flipRisk = calculateHighestExposedValue(card, directions);

          // Update the best placement if this flip risk is lower or if it matches the
          // current best flip risk but is closer to the upper-leftmost position
          if (flipRisk < minFlipRisk ||
              (flipRisk == minFlipRisk && (bestPlacement == null || isUpperLeft(cornerRow, cornerCol, bestPlacement)))) {
            minFlipRisk = flipRisk;
            bestPlacement = new Placement(cornerRow, cornerCol); // Store card with placement
          }
        }
      }
    }

    // Fallback to uppermost-leftmost open position if no corner moves are found
    if (bestPlacement == null) {
      for (int row = 0; row < boardHeight; row++) {
        for (int col = 0; col < boardWidth; col++) {
          if (boardAvailability[row][col] != CellType.EMPTY) {
            continue;
          }
          try {
            model.getCardAt(row, col);
          } catch (IllegalArgumentException e) {
            if (bestPlacement == null || isUpperLeft(row, col, bestPlacement)) {
              bestPlacement = new Placement(row, col); // Fallback to first card in hand
            }
          }
        }
        }
      }

    return bestPlacement;
  }

  /**
   * Calculates the flip risk by identifying the highest value among the card's exposed directions
   * based on the corner position.
   */

  private int calculateHighestExposedValue(ICard card, Direction[] exposedDirections) {
    int maxExposedValue = 0;
    for (Direction direction : exposedDirections) {
      int value = card.getDirectionsAndValues().get(direction).getValue();
      if (value > maxExposedValue) {
        maxExposedValue = value;
      }
    }
    return maxExposedValue;
  }

  private boolean isUpperLeft(int row, int col, Placement bestPlacement) {
    return row < bestPlacement.row || (row == bestPlacement.row && col < bestPlacement.column);
  }
}
