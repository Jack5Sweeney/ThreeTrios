package strategies;

import model.CellType;
import model.ICard;
import model.IModel;
import model.ModelImpl;
import model.PlayerImpl;

public class FlipTheMostStrategy implements IStrategy {

  public Placement chooseMove(IModel model, PlayerImpl player) {
    int maxFlips = 0;
    Placement bestPlacement = null;
    int bestCardIndex = Integer.MAX_VALUE; // Used to track the lowest index card for tie-breaking

    int boardHeight = model.getBoard().length;
    int boardWidth = model.getBoard()[0].length;
    CellType[][] boardAvailability = model.getBoardAvailability();

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
                            (row == bestPlacement.row && col == bestPlacement.column && cardIndex < bestCardIndex)))) {

              maxFlips = flips;
              bestPlacement = new Placement(row, col, cardIndex);
              bestCardIndex = cardIndex;
            }
          }
        }
      }
    }

    // Fallback to the uppermost-leftmost open position with card index 0 if no flips are possible
    if (bestPlacement == null) {
      for (int row = 0; row < boardHeight; row++) {
        for (int col = 0; col < boardWidth; col++) {
          if (boardAvailability[row][col] == CellType.EMPTY && model.getBoard()[row][col] == null) {
            bestPlacement = new Placement(row, col, 0); // Fallback to the first card in hand (index 0)
            return bestPlacement;
          }
        }
      }
    }

    return bestPlacement;
  }

  private boolean isUpperLeft(int row, int column, Placement bestPlacement) {
    return row < bestPlacement.row || (row == bestPlacement.row && column < bestPlacement.column);
  }
}
