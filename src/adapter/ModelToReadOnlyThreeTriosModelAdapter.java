package adapter;

import card.ICard;
import model.IModel;
import player.PlayerColor;
import provider.src.threetrios.model.Card;
import provider.src.threetrios.model.ReadonlyThreeTriosModel;
import provider.src.threetrios.model.TeamColor;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter class to bridge the gap between {@link IModel} and {@link ReadonlyThreeTriosModel}.
 * This class allows a readonly view of the model, providing only necessary functionality.
 */
public class ModelToReadOnlyThreeTriosModelAdapter implements ReadonlyThreeTriosModel {
  private final IModel modelDelegate;

  /**
   * Constructs an adapter with the given model delegate.
   *
   * @param modelDelegate the model to adapt
   */
  public ModelToReadOnlyThreeTriosModelAdapter(IModel modelDelegate) {
    this.modelDelegate = modelDelegate;
  }

  /**
   * Gets the red player's hand as a list of {@link Card} objects.
   *
   * @return the red player's hand
   */
  @Override
  public List<Card> getRedHand() {
    List<Card> returnHand = new ArrayList<>();
    List<ICard> handToAdapt = modelDelegate.getRedPlayer().getHand();

    for (ICard card : handToAdapt) {
      returnHand.add(new CardToICardAdapter(card));
    }

    return returnHand;
  }

  /**
   * Gets the blue player's hand as a list of {@link Card} objects.
   *
   * @return the blue player's hand
   */
  @Override
  public List<Card> getBlueHand() {
    List<Card> returnHand = new ArrayList<>();
    List<ICard> handToAdapt = modelDelegate.getBluePlayer().getHand();

    for (ICard card : handToAdapt) {
      returnHand.add(new CardToICardAdapter(card));
    }

    return returnHand;
  }

  /**
   * Gets the board as a string representation.
   *
   * @return the board string
   */
  @Override
  public String getBoard() {
    return "";
  }

  /**
   * Determines if it is the red player's turn.
   *
   * @return {@code true} if it is red player's turn, otherwise {@code false}
   */
  @Override
  public boolean getTurn() {
    return modelDelegate.getCurrentPlayerColor().equals(PlayerColor.RED);
  }

  /**
   * Determines if the red player wins the model.
   *
   * @return {@code false} by default (not yet implemented)
   */
  @Override
  public boolean redPlayerWinsModel() {
    return false;
  }

  /**
   * Gets the grid size of the board.
   *
   * @return the grid size
   */
  @Override
  public int getGridSize() {
    return 0;
  }

  /**
   * Gets the card at a specified row and column.
   *
   * @param row the row of the card
   * @param col the column of the card
   * @return the card at the specified position
   */
  @Override
  public Object getCardAt(int row, int col) {
    ICard cardToAdapt = modelDelegate.getCardAt(row, col);
    return new CardToICardAdapter(cardToAdapt);
  }

  /**
   * Determines the team color of the card owner at the specified row and column.
   *
   * @param row the row of the card
   * @param col the column of the card
   * @return the team color of the card owner
   */
  @Override
  public TeamColor whoOwnsCardAt(int row, int col) {
    ICard cardToAdapt = modelDelegate.getCardAt(row, col);
    Card adaptedCard = new CardToICardAdapter(cardToAdapt);
    return adaptedCard.getColor();
  }

  /**
   * Gets the score of the player based on the team color.
   *
   * @param teamColor the team color
   * @return the score of the specified player
   */
  @Override
  public int getPlayerScore(TeamColor teamColor) {
    if (teamColor.equals(TeamColor.RED)) {
      return modelDelegate.getPlayerScore(PlayerColor.RED);
    } else {
      return modelDelegate.getPlayerScore(PlayerColor.BLUE);
    }
  }

  /**
   * Gets the board as a 2D array of objects.
   * Cells are either cards or converted availability states.
   *
   * @return the 2D array representation of the board
   */
  @Override
  public Object[][] getBoardArray() {
    int rows = modelDelegate.getBoard().length;
    int cols = modelDelegate.getBoard()[0].length;

    Object[][] boardArray = new Object[rows][cols];
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        if (modelDelegate.getBoard()[row][col] != null) {
          boardArray[row][col] = new CardToICardAdapter(modelDelegate.getCardAt(row, col));
        } else {
          boardArray[row][col] =
                  new CellTypeToProviderCellTypeAdapter(
                          modelDelegate.getBoardAvailability()[row][col])
                          .convert();
        }
      }
    }
    return boardArray;
  }
}
