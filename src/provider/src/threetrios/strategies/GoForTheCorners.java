package provider.src.threetrios.strategies;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import threetrios.model.Card;
import threetrios.model.CellType;
import threetrios.model.Model;
import threetrios.model.TeamColor;

/**
 * Class representing a goForTheCorner move strategy, which prioritizes placing cards in corners
 * because they expose less sides to be attacked from, also places the strongest card for the given
 * corner. Defaults to the lowest index card in hand at the top left position. For ties and once
 * all corners are filled.
 */
public class GoForTheCorners implements MoveStrategy {
  List<Card> playerHand = new ArrayList<>();

  @Override
  public Move chooseMove(Model model, TeamColor color) {
    return tryToGoForTheCorners(model, color);
  }

  private Move tryToGoForTheCorners(Model model, TeamColor color) {
    Object[][] boardCopy = model.getBoardArray();

    if (color == TeamColor.RED) {
      playerHand = model.getRedHand();
    } else if (color == TeamColor.BLUE) {
      playerHand = model.getBlueHand();
    }

    // find the card that would be strongest for each corner
    Card bestTopLeftPick = bestTopLeftPick(playerHand);
    Card bestBottomLeftPick = bestBottomLeftPick(playerHand);
    Card bestTopRightPick = bestTopRightPick(playerHand);
    Card bestBottomRightPick = bestBottomRightPick(playerHand);

    // find the strength of each card in its respective corner
    int topleftvalues = bestTopLeftPick.getSouthValue() + bestTopLeftPick.getEastValue();
    int toprightvalues = bestTopRightPick.getSouthValue() + bestTopRightPick.getWestValue();
    int bottomleftvalues = bestBottomLeftPick.getNorthValue() + bestBottomLeftPick.getEastValue();
    int bottomrightvalues = bestBottomRightPick.getNorthValue() + bestBottomRightPick.getWestValue()
            ;

    // Use a list of Map.Entry to keep track of both strength values and their corner names
    List<Map.Entry<Integer, String>> strengthAndCard = new ArrayList<>();
    strengthAndCard.add(new AbstractMap.SimpleEntry<>(topleftvalues, "Top Left"));
    strengthAndCard.add(new AbstractMap.SimpleEntry<>(toprightvalues, "Top Right"));
    strengthAndCard.add(new AbstractMap.SimpleEntry<>(bottomleftvalues, "Bottom Left"));
    strengthAndCard.add(new AbstractMap.SimpleEntry<>(bottomrightvalues, "Bottom Right"));

    // Sort the list based on the strength values (the first element in each pair)
    // Sort in descending order
    Collections.sort(strengthAndCard, (a, b) -> Integer.compare(b.getKey(), a.getKey()));

    for (Map.Entry<Integer, String> entry : strengthAndCard) {
      // start at our most powerful card, go to second, then third...

      if (entry.getValue().equals("Top Right")) {
        // try and place top right
        if (boardCopy[0][boardCopy[0].length - 1] == CellType.CARDCELL) {
          boardCopy[0][boardCopy[0].length - 1] = bestTopRightPick;
          return new Move(0, boardCopy[0].length - 1, bestTopRightPick);
        }
      } else if (entry.getValue().equals("Top Left")) {
        // try and place top left
        if (boardCopy[0][0] == CellType.CARDCELL) {
          boardCopy[0][0] = bestTopLeftPick;
          return new Move(0, 0, bestTopLeftPick);
        }
      } else if (entry.getValue().equals("Bottom Right")) {
        // try and place bottom right
        if (boardCopy[boardCopy.length - 1][boardCopy[0].length - 1] == CellType.CARDCELL) {
          boardCopy[boardCopy.length - 1][boardCopy[0].length - 1] = bestBottomRightPick;
          return new Move(boardCopy.length - 1, boardCopy[0].length - 1,
                  bestBottomRightPick);
        }
      } else if (entry.getValue().equals("Bottom Left")) {
        // try and place bottom left
        if (bottomleftvalues > bottomrightvalues && boardCopy[boardCopy.length - 1][0]
                == CellType.CARDCELL) {
          boardCopy[boardCopy.length - 1][0] = bestBottomLeftPick;
          return new Move(boardCopy.length - 1, 0, bestBottomLeftPick);
        }
      }
    }

    // if none of the corners are available
    System.out.println("IT USED THE RULE FOR NO CORNERS AVAILABLE ON THIS TURN");
    return tieBreakerRule(playerHand, boardCopy);
  }

  private Card bestTopLeftPick(List<Card> hand) {
    int currentBest = hand.get(0).getEastValue() + hand.get(0).getSouthValue();
    Card currentBestCard = hand.get(0);

    for (Card card : hand) {
      if (card.getEastValue() + card.getSouthValue() > currentBest) {
        currentBestCard = card;
        currentBest = card.getEastValue() + card.getSouthValue();
      }
    }

    return currentBestCard;
  }

  private Card bestBottomLeftPick(List<Card> hand) {
    int currentBest = hand.get(0).getEastValue() + hand.get(0).getNorthValue();
    Card currentBestCard = hand.get(0);

    for (Card card : hand) {
      if (card.getEastValue() + card.getNorthValue() > currentBest) {
        currentBestCard = card;
        currentBest = card.getEastValue() + card.getNorthValue();
      }
    }

    return currentBestCard;
  }

  private Card bestTopRightPick(List<Card> hand) {
    int currentBest = hand.get(0).getWestValue() + hand.get(0).getSouthValue();
    Card currentBestCard = hand.get(0);

    for (Card card : hand) {
      if (card.getWestValue() + card.getSouthValue() > currentBest) {
        currentBestCard = card;
        currentBest = card.getWestValue() + card.getSouthValue();
      }
    }

    return currentBestCard;
  }

  private Card bestBottomRightPick(List<Card> hand) {
    int currentBest = hand.get(0).getWestValue() + hand.get(0).getNorthValue();
    Card currentBestCard = hand.get(0);

    for (Card card : hand) {
      if (card.getWestValue() + card.getNorthValue() > currentBest) {
        currentBestCard = card;
        currentBest = card.getWestValue() + card.getNorthValue();
      }
    }

    return currentBestCard;
  }

  private Move tieBreakerRule(List<Card> playerHand, Object[][] boardCopy) {
    // chose most top left position, and place players hand first card there

    Card noMoveCard = playerHand.get(0);

    double currentLeastDistance = Integer.MAX_VALUE;
    int smallestRow = Integer.MAX_VALUE;
    int smallestCol = Integer.MAX_VALUE;

    for (int row = 0; row < boardCopy.length; row++) {
      // for every row in our grid

      for (int col = 0; col < boardCopy[0].length; col++) {
        // for every col in our grid, now we are checking every element of our grid

        if (boardCopy[row][col].equals(CellType.CARDCELL)) {
          // if you find a cardcell, check if it is the leftmost cardcell
          // we have seen so far, if so update
          double distance = Math.sqrt(Math.pow(row, 2) + Math.pow(col, 2));

          if (distance < currentLeastDistance) {
            currentLeastDistance = distance;
            smallestRow = row;
            smallestCol = col;
          }
        }
      }
    }

    return new Move(smallestRow, smallestCol, noMoveCard);
  }
}
