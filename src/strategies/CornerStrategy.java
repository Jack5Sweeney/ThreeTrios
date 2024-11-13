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

    // Exposed directions for each corner based on board edges
    Direction[][] exposedDirections = {
            {Direction.EAST, Direction.SOUTH},     // top-left
            {Direction.WEST, Direction.SOUTH},     // top-right
            {Direction.EAST, Direction.NORTH},     // bottom-left
            {Direction.WEST, Direction.NORTH}      // bottom-right
    };

    CellType[][] boardAvailability = model.getBoardAvailability();

    // Iterate through each corner position
    for (int i = 0; i < corners.length; i++) {
      int cornerRow = corners[i][0];
      int cornerCol = corners[i][1];

      // Check if this corner is EMPTY; if not, skip it
      if (boardAvailability[cornerRow][cornerCol] != CellType.EMPTY) {
        continue;
      }

      Direction[] directions = exposedDirections[i];

      for (int cardIndex = 0; cardIndex < player.getHand().size(); cardIndex++) {
        ICard card = player.getHand().get(cardIndex);

        // Calculate flip risk, considering only relevant exposed directions
        int flipRisk = calculateAdjustedFlipRisk(model, card, cornerRow, cornerCol, directions, boardAvailability);

        // Update best placement if this flip risk is lower, or if tied, based on upper-left rule
        if (flipRisk < minFlipRisk ||
                (flipRisk == minFlipRisk && (bestPlacement == null || isUpperLeft(cornerRow, cornerCol, bestPlacement)))) {
          minFlipRisk = flipRisk;
          bestPlacement = new Placement(cornerRow, cornerCol);
        }
      }
    }

    // Fallback to upper-leftmost open position if no corner moves are found
    if (bestPlacement == null) {
      for (int row = 0; row < boardHeight; row++) {
        for (int col = 0; col < boardWidth; col++) {
          if (boardAvailability[row][col] == CellType.EMPTY && model.getBoard()[row][col] == null) {
            if (bestPlacement == null || isUpperLeft(row, col, bestPlacement)) {
              bestPlacement = new Placement(row, col); // Fallback to upper-leftmost empty cell
            }
          }
        }
      }
    }

    return bestPlacement;
  }

  /**
   * Calculates the flip risk by identifying the highest value among the card's exposed directions,
   * ignoring directions that are adjacent to `HOLE` cells.
   */
  private int calculateAdjustedFlipRisk(IModel model, ICard card, int row, int col, Direction[] exposedDirections, CellType[][] boardAvailability) {
    int maxExposedValue = 0;

    for (Direction direction : exposedDirections) {
      int adjRow = row + getRowOffset(direction);
      int adjCol = col + getColOffset(direction);

      // Skip direction if adjacent cell is a HOLE (not considered a real "edge")
      if (isValidPosition(adjRow, adjCol, boardAvailability) && boardAvailability[adjRow][adjCol] != CellType.HOLE) {
        int value = card.getDirectionsAndValues().get(direction).getValue();
        maxExposedValue = Math.max(maxExposedValue, value);
      }
    }
    return maxExposedValue;
  }

  private boolean isUpperLeft(int row, int col, Placement bestPlacement) {
    return row < bestPlacement.row || (row == bestPlacement.row && col < bestPlacement.column);
  }

  // Utility methods for direction offsets
  private int getRowOffset(Direction direction) {
    switch (direction) {
      case NORTH: return -1;
      case SOUTH: return 1;
      default: return 0;
    }
  }

  private int getColOffset(Direction direction) {
    switch (direction) {
      case WEST: return -1;
      case EAST: return 1;
      default: return 0;
    }
  }

  // Validates if the position is within bounds
  private boolean isValidPosition(int row, int col, CellType[][] boardAvailability) {
    return row >= 0 && row < boardAvailability.length && col >= 0 && col < boardAvailability[0].length;
  }
}
