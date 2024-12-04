package adapter;

import card.ICard;
import model.IModel;
import player.PlayerColor;
import provider.src.threetrios.model.Card;
import provider.src.threetrios.model.ReadonlyThreeTriosModel;
import provider.src.threetrios.model.TeamColor;

import java.util.ArrayList;
import java.util.List;

public class ModelToReadOnlyThreeTriosModelAdapter implements ReadonlyThreeTriosModel {
  IModel modelDelegate;

  public ModelToReadOnlyThreeTriosModelAdapter(IModel modelDelegate) {
    this.modelDelegate = modelDelegate;
  }

  @Override
  public List<Card> getRedHand() {
    ArrayList<Card> returnHand = new ArrayList<>();
    List<ICard> handToAdapt = modelDelegate.getRedPlayer().getHand();

    for (ICard card : handToAdapt) {
      returnHand.add(new CardToICardAdapter(card));
    }

    return returnHand;
  }

  @Override
  public List<Card> getBlueHand() {
    ArrayList<Card> returnHand = new ArrayList<>();
    List<ICard> handToAdapt = modelDelegate.getBluePlayer().getHand();

    for (ICard card : handToAdapt) {
      returnHand.add(new CardToICardAdapter(card));
    }

    return returnHand;
  }

  @Override
  public String getBoard() {
    return "";
  }

  @Override
  public boolean getTurn() {
    if(modelDelegate.getCurrentPlayerColor().equals(PlayerColor.RED)) {
      return true;
    }
    else {
      return false;
    }
  }

  @Override
  public boolean redPlayerWinsModel() {
    return false;
  }

  @Override
  public int getGridSize() {
    return 0;
  }

  @Override
  public Object getCardAt(int row, int col) {
    ICard cardToAdapt = modelDelegate.getCardAt(row, col);
    return new CardToICardAdapter(cardToAdapt);
  }

  @Override
  public TeamColor whoOwnsCardAt(int row, int col) {
    ICard cardToAdapt = modelDelegate.getCardAt(row, col);
    Card adaptedCard = new CardToICardAdapter(cardToAdapt);
    return adaptedCard.getColor();
  }

  @Override
  public int getPlayerScore(TeamColor teamColor) {
    if(teamColor.equals(TeamColor.RED)) {
     return modelDelegate.getPlayerScore(PlayerColor.RED);
   } else {
      return modelDelegate.getPlayerScore(PlayerColor.BLUE);
    }
  }

  @Override
  public Object[][] getBoardArray() {
    int rows = modelDelegate.getBoard().length;
    int cols = modelDelegate.getBoard()[0].length;

    Object[][] boardArray =  new Object[rows][cols];
    for(int row = 0; row < rows; row++) {
      for(int col = 0; col < cols; col++) {
        if(modelDelegate.getBoard()[row][col] != null) {
          boardArray[row][col] = new CardToICardAdapter(modelDelegate.getCardAt(row,col));
        }
        else {
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
