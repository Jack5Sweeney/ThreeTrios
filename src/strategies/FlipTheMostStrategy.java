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

    int boardHeight = model.getBoard().length;
    int boardWidth = model.getBoard()[0].length;

    CellType[][] boardAvailability = model.getBoardAvailability();

    for (int row = 0; row < boardHeight; row++) {
      for (int col = 0; col < boardWidth; col++) {
        // Check if the position is open before considering it

        try {
          model.getCardAt(row,col);
        }
        catch (IllegalArgumentException e) {

          if (boardAvailability[row][col] != CellType.EMPTY) {
            continue;
          }


          for (int cardIndex = 0; cardIndex < player.getHand().size(); cardIndex++) {
            ICard card = player.getHand().get(cardIndex);

            // Calculate flips for placing this card at (row, col)
            int flips = model.calculateFlips(row, col, card);

            // Update best placement based on flip count and tiebreaker rules
            if (flips > maxFlips ||
                (flips == maxFlips && (bestPlacement == null || isUpperLeft(row, col, bestPlacement)))) {
              maxFlips = flips;
              bestPlacement = new Placement(row, col);
            }

          }
        }
      }
    }

    // Fallback to uppermost-leftmost open position if no flips are possible
    if (maxFlips == 0) {
      for (int row = 0; row < boardHeight; row++) {
        for (int col = 0; col < boardWidth; col++) {
          if (model.getBoard()[row][col] == null) { // Only select if the spot is open
            bestPlacement = new Placement(row, col);
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
