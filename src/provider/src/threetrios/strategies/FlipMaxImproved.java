package provider.src.threetrios.strategies;

import java.util.ArrayList;
import java.util.List;

import threetrios.model.Card;
import threetrios.model.CellType;
import threetrios.model.Model;
import threetrios.model.TeamColor;

/**
 * Class representing a flipMax move strategy, which places the card from the players hand that
 * would flip the most cards if placed on the current board. Defaults to the lowest index card in
 * hand at the top left position. Or lowest index of the tied cards at the top left position.
 */
public class FlipMaxImproved implements MoveStrategy {
  @Override
  public Move chooseMove(Model model, TeamColor color) {
    return tryToFlipMax(model, color);
  }

  private Move tryToFlipMax(Model model, TeamColor color) {
    Move bestMove = null;
    List<Card> playerHand = new ArrayList<>();
    int currentGreatestBattleScore = 0;
    List<Move> listOfTiedMoves = new ArrayList<>();

    if (color == TeamColor.RED) {
      playerHand = model.getRedHand();
    } else if (color == TeamColor.BLUE) {
      playerHand = model.getBlueHand();
    }

    if (playerHand.size() == 1) {
      for (int row = 0; row < model.getBoardArray().length; row++) {
        // for every row in our grid
        for (int col = 0; col < model.getBoardArray()[0].length; col++) {
          // for every col in our grid, now we are checking every element of our grid
          if (model.getBoardArray()[row][col].equals(CellType.CARDCELL)) {
            return new Move(row, col, playerHand.get(0));
          }
        }
      }
    }

    // try every card in the players hand, in every cardcell position
    // check how many cards it would flip if placed there
    // update the bestMove trackers

    for (int cardIdx = 0; cardIdx < playerHand.size(); cardIdx++) {
      // for every card in the current players hand

      for (int row = 0; row < model.getBoardArray().length; row++) {
        // for every row in our grid

        for (int col = 0; col < model.getBoardArray()[0].length; col++) {
          // for every col in our grid, now we are checking every element of our grid

          if (model.getBoardArray()[row][col].equals(CellType.CARDCELL)) {
            // if you are at a cardcell, check if placing a card there is a new best move

            int battleScore = model.adjacentBattle(playerHand.get(cardIdx), row, col, false,
                    0);
            // how much this card could flip if placed at board[row][col]

            if (battleScore > currentGreatestBattleScore) {
              // if you find a new best move to make
              currentGreatestBattleScore = battleScore;
              bestMove = new Move(row, col, playerHand.get(cardIdx));

              listOfTiedMoves.clear();
              listOfTiedMoves.add(new Move(row, col, playerHand.get(cardIdx)));
            } else if (battleScore == currentGreatestBattleScore) {
              // if you find a move that is tied for best move to make
              listOfTiedMoves.add(new Move(row, col, playerHand.get(cardIdx)));
            }
          }
        }
      }
    }

    if (listOfTiedMoves.size() > 1) {
      bestMove = tieBreakerRule(listOfTiedMoves);
    }

    return bestMove;
  }

  private Move tieBreakerRule(List<Move> listOfTiedMoves) {
    // chose most top left position, and place players hand first card there

    double currentLeastDistance = Integer.MAX_VALUE;
    List<Move> listOfTiedTiedMoves = new ArrayList<>();

    for (int tiedMoveIdx = 0; tiedMoveIdx < listOfTiedMoves.size(); tiedMoveIdx++) {
      // for every tied move we have

      if (listOfTiedMoves.get(tiedMoveIdx).calcDistanceFromTopLeftCorner() < currentLeastDistance) {
        // if you find a move that is closer to 0,0 , then update
        currentLeastDistance = listOfTiedMoves.get(tiedMoveIdx).calcDistanceFromTopLeftCorner();

        listOfTiedTiedMoves.clear();
        listOfTiedTiedMoves.add(listOfTiedMoves.get(tiedMoveIdx));
      } else if (listOfTiedMoves.get(tiedMoveIdx).calcDistanceFromTopLeftCorner()
              == currentLeastDistance) {
        // if you find a move that is tied for being closer to 0,0 , then update
        listOfTiedTiedMoves.add(listOfTiedMoves.get(tiedMoveIdx));
      }

    }

    // the first thing in our list will always have the smallest card idx
    return listOfTiedTiedMoves.get(0);
  }

}
