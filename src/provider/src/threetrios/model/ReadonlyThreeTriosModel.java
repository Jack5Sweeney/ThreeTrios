package provider.src.threetrios.model;

import java.util.List;

/**
 * Contains all non-mutation methods from the model. This is used to pass to our gui so that our
 * view does not mess with game logic.
 */
public interface ReadonlyThreeTriosModel {

  /**
   * Returns a copy of the red players hand.
   *
   * @return a copy of the red players hand.
   */
  List<Card> getRedHand();

  /**
   * Returns a copy of the blue players hand.
   *
   * @return a copy of the blue players hand.
   */
  List<Card> getBlueHand();

  /**
   * Returns a string representation of the current board, in the following example format.
   * NAME7 7 3 2 1 HOLE CARDCELL CARDCELL CARDCELL
   * CARDCELL HOLE CARDCELL HOLE HOLE
   * CARDCELL CARDCELL CARDCELL HOLE HOLE
   *
   * @return a string representation of the current board.
   */
  String getBoard();

  /**
   * Indicates the current players turn.
   *
   * @return true if it is red players turn, false if blue players turn.
   */
  boolean getTurn();

  /**
   * Return true if red player won. False if blue player won.
   *
   * @return true if red player won.
   */
  boolean redPlayerWinsModel();

  /**
   * Returns the size of the grid, rows x columns.
   *
   * @return the size of the grid, rows x columns.
   */
  int getGridSize();

  /**
   * Returns the card at a specific row/col. An advantage of our implementation is that we can tell
   * the user whether the thing they pressed on was a card, a cardcell, or a hole as well.
   *
   * @param row is the row the user wants to check. These follow standard cord location as stated
   *            in the readme.
   * @param col is the col the user wants to check. These follow standard cord location as
   *            stated in the readme.
   * @return the card at a specific row/col.
   */
  Object getCardAt(int row, int col);

  /**
   * Retusn the team color of an object at a specific row/col.
   *
   * @param row is the row the user wants to check. These follow standard cord location as stated
   *            in the readme.
   * @param col is the col the user wants to check. These follow standard cord location as stated
   *            in the readme.
   * @return the team color of an object at a specific row/col.
   */
  TeamColor whoOwnsCardAt(int row, int col);

  /**
   * Returns an int representing the score of a team. The score of a team is determined by how many
   * cards it currently has on the board.
   *
   * @param teamColor is the team the user wants to check the score of.
   * @return an int representing the score of a team.
   */
  int getPlayerScore(TeamColor teamColor);

  /**
   * Returns a copy of the board array being used by our model/board class to track card placement.
   *
   * @return a copy of the board array being used by our model/board class to track card placement.
   */
  Object[][] getBoardArray();
}
