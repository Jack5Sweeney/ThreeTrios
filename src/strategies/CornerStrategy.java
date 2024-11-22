package strategies;

import card.CellType;
import card.Direction;
import card.ICard;
import model.IModel;
import player.IPlayer;

/**
 * A strategy that attempts to place cards in the corners of the board,
 * minimizing vulnerability based on the exposure of specific directions.
 * If no optimal corner is available, it defaults to the upper-leftmost open cell.
 */
public class CornerStrategy implements IStrategy {

  /**
   * Determines the best placement on the board for the player based on corner vulnerability.
   * If no optimal corner placement is found, defaults to the upper-leftmost open cell.
   *
   * @param model  the game model containing the board and its status
   * @param player the player making the move, containing their current hand of cards
   * @return the optimal {@link Placement} for the player's next move
   */
  public Placement chooseMove(IModel model, IPlayer player) {
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

          // Calculate the vulnerability for placing this card at the corner
          int vulnerability = calculateVulnerability(card, directions, model, cornerRow,
              cornerCol, boardAvailability);

          // Update the best placement based on vulnerability, card index, and position
          if (vulnerability < minVulnerability || vulnerability == minVulnerability &&
              (bestPlacement == null || cardIndex < bestCardIndex)) {

            minVulnerability = vulnerability;
            bestPlacement = new Placement(cornerRow, cornerCol, cardIndex);
            bestCardIndex = cardIndex;
          }
        }
      }
    }

    // Bypass corner checks if the board has only one row or one column
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
              bestPlacement = new Placement(row, col, 0);
            }
          }
        }
      }
    }

    return bestPlacement;
  }

  /**
   * Calculates the vulnerability score for placing a card in a specific corner by summing
   * adjusted values based on exposed directions. Lower scores indicate less vulnerability.
   *
   * @param card              the card being evaluated for placement
   * @param exposedDirections the directions exposed by placing in a particular corner
   * @param model             the game model for board information
   * @param row               the row position of the potential placement
   * @param col               the column position of the potential placement
   * @param boardAvailability the current state of board cell availability
   * @return the computed vulnerability score based on adjacent cells
   */
  private int calculateVulnerability(ICard card, Direction[] exposedDirections,
                                     IModel model, int row, int col,
                                     CellType[][] boardAvailability) {
    int totalVulnerability = 0;

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
        default:
          break;
      }

      // Only add to vulnerability if the adjacent cell is open and within bounds
      if (adjRow >= 0 && adjRow < boardAvailability.length && adjCol >= 0 && adjCol
          < boardAvailability[0].length &&
          boardAvailability[adjRow][adjCol] == CellType.EMPTY) {
        int value = card.getDirectionsAndValues().get(direction).getValue();
        totalVulnerability += (10 - value);
      }
    }

    return totalVulnerability;
  }

  /**
   * Determines if the specified cell is located higher and further left than the
   * current best placement on the board.
   *
   * @param row           the row of the cell to evaluate
   * @param col           the column of the cell to evaluate
   * @param bestPlacement the current best placement on the board
   * @return true if the cell is in a more upper-left position than bestPlacement, false otherwise
   */
  private boolean isUpperLeft(int row, int col, Placement bestPlacement) {
    return row < bestPlacement.row || (row == bestPlacement.row && col < bestPlacement.column);
  }
}
