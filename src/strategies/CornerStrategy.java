package strategies;

import model.CellType;
import model.Direction;
import model.ICard;
import model.IModel;
import model.PlayerImpl;

public class CornerStrategy implements IStrategy {

  public Placement chooseMove(IModel model, PlayerImpl player) {
    int minVulnerability = Integer.MAX_VALUE;
    Placement bestPlacement = null;
    int bestCardIndex = Integer.MAX_VALUE; // Track the lowest index card for tie-breaking

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
        model.getCardAt(cornerRow, cornerCol); // Check if the position is occupied
      } catch (IllegalArgumentException e) {

        // Skip non-empty or non-playable cells
        if (boardAvailability[cornerRow][cornerCol] != CellType.EMPTY) {
          continue;
        }

        for (int cardIndex = 0; cardIndex < player.getHand().size(); cardIndex++) {
          ICard card = player.getHand().get(cardIndex);

          // Calculate the flip risk based on the highest value in the exposed directions, considering adjacent cells
          int vulnerability = calculateVulnerability(card, directions, model, cornerRow, cornerCol, boardAvailability);

          // Update the best placement if this vulnerability is lower or if it matches the
          // current best flip risk but is closer to the upper-leftmost position
          if (vulnerability < minVulnerability ||
                  (vulnerability == minVulnerability && (bestPlacement == null ||
                          isUpperLeft(cornerRow, cornerCol, bestPlacement) ||
                          (cornerRow == bestPlacement.row && cornerCol == bestPlacement.column
                                  && cardIndex < bestCardIndex)))) {

            minVulnerability = vulnerability;
            bestPlacement = new Placement(cornerRow, cornerCol, cardIndex); // Store card with placement
            bestCardIndex = cardIndex;
          }
        }
      }
    }

    if (boardHeight == 1 || boardWidth == 1) {
      bestPlacement = null;
    }

    // Fallback to uppermost-leftmost open position with card index 0 if no corner moves are found
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
              bestPlacement = new Placement(row, col, 0); // Fallback to first card in hand (index 0)
            }
          }
        }
      }
    }

    return bestPlacement;
  }

  /**
   * Calculates the flip risk by identifying the highest value among the card's exposed directions
   * based on the corner position, ignoring directions facing holes or occupied cells.
   */

  private int calculateVulnerability(ICard card, Direction[] exposedDirections, IModel model, int row, int col, CellType[][] boardAvailability) {
    int minExposedValue = Integer.MAX_VALUE;

    for (Direction direction : exposedDirections) {
      int adjRow = row;
      int adjCol = col;

      switch (direction) {
        case NORTH:
          adjRow--;
          break;
        case SOUTH:
          adjRow++;
          break;
        case EAST:
          adjCol++;
          break;
        case WEST:
          adjCol--;
          break;
      }

      // Ignore if the adjacent cell is out of bounds, a hole, or already occupied
      if (adjRow < 0 || adjRow >= boardAvailability.length || adjCol < 0 || adjCol >= boardAvailability[0].length ||
              boardAvailability[adjRow][adjCol] != CellType.EMPTY) {
        continue;
      }

      // Include the value for directions that are valid and adjacent to an empty cell
      int value = card.getDirectionsAndValues().get(direction).getValue();
      if (value < minExposedValue) {
        minExposedValue = value;
      }
    }
    // If no exposed directions, vulnerability is zero
    if (minExposedValue == Integer.MAX_VALUE) {
      return 0;
    } else {
      return 10 - minExposedValue; // Higher value means lower vulnerability
    }
  }

  private boolean isUpperLeft(int row, int col, Placement bestPlacement) {
    return row < bestPlacement.row || (row == bestPlacement.row && col < bestPlacement.column);
  }
}
