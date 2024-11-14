package strategies;

import model.CellType;
import model.ICard;
import model.IModel;
import model.PlayerImpl;

/**
 * A strategy that aims to place a card in a position on the board that flips the maximum
 * number of opponent's cards. If no optimal position is found, it defaults to the
 * upper-leftmost open cell.
 */
public class FlipTheMostStrategy implements IStrategy {

  /**
   * Determines the best placement on the board for the player based on maximizing the number
   * of flips. If no high-flip move is available, defaults to the upper-leftmost open cell.
   *
   * @param model  the game model containing the board and flip calculation methods
   * @param player the player making the move, containing their current hand of cards
   * @return the optimal {@link Placement} for the player's next move
   */
  public Placement chooseMove(IModel model, PlayerImpl player) {
    int maxFlips = 0;
    Placement bestPlacement = null;
    int bestCardIndex = Integer.MAX_VALUE;

    int boardHeight = model.getBoard().length;
    int boardWidth = model.getBoard()[0].length;
    CellType[][] boardAvailability = model.getBoardAvailability();

    // Iterate through each cell on the board
    for (int row = 0; row < boardHeight; row++) {
      for (int col = 0; col < boardWidth; col++) {
        // Check if the position is open before considering it
        try {
          model.getCardAt(row, col);
        } catch (IllegalArgumentException e) {
          // Skip non-empty or non-playable cells
          if (boardAvailability[row][col] != CellType.EMPTY) {
            continue;
          }

          // Iterate through each card in the player's hand
          for (int cardIndex = 0; cardIndex < player.getHand().size(); cardIndex++) {
            ICard card = player.getHand().get(cardIndex);

            // Calculate flips for placing this card at (row, col)
            int flips = model.calculateFlips(row, col, card);

            // Update best placement based on flip count, position, and card index
            if (flips > maxFlips ||
                (flips == maxFlips && (bestPlacement == null ||
                    isUpperLeft(row, col, bestPlacement) ||
                    (row == bestPlacement.row && col == bestPlacement.column && cardIndex
                        < bestCardIndex)))) {

              maxFlips = flips;
              bestPlacement = new Placement(row, col, cardIndex);
              bestCardIndex = cardIndex;
            }
          }
        }
      }
    }

    // Fallback to the upper-leftmost open position with card index 0 if no flips are possible
    if (bestPlacement == null) {
      for (int row = 0; row < boardHeight; row++) {
        for (int col = 0; col < boardWidth; col++) {
          if (boardAvailability[row][col] == CellType.EMPTY &&
              model.getBoard()[row][col] == null) {
            bestPlacement = new Placement(row, col, 0);
            return bestPlacement;
          }
        }
      }
    }

    return bestPlacement;
  }

  /**
   * Checks if a given cell is located higher and further left than the current best placement.
   *
   * @param row           the row of the cell to check
   * @param column        the column of the cell to check
   * @param bestPlacement the current best placement based on highest flips
   * @return true if the cell is in a more upper-left position than the bestPlacement,
   *         false otherwise
   */
  private boolean isUpperLeft(int row, int column, Placement bestPlacement) {
    return row < bestPlacement.row || (row == bestPlacement.row && column < bestPlacement.column);
  }
}
