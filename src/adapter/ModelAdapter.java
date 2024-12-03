package adapter;

import card.CellType;
import card.ICard;
import model.IModel;
import model.ModelImpl;
import player.IPlayer;
import provider.src.threetrios.model.Card;
import provider.src.threetrios.model.ReadonlyThreeTriosModel;
import provider.src.threetrios.model.TeamColor;

import java.util.ArrayList;
import java.util.List;

public class ModelAdapter implements ReadonlyThreeTriosModel {
  IModel modelDelegate;
  public ModelAdapter(IModel modelDelegate) {
    this.modelDelegate = modelDelegate;
  }

  @Override
  public List<Card> getRedHand() {
    return modelDelegate.getRedPlayer().getHand();
  }

  @Override
  public List<Card> getBlueHand() {
    return List.of();
  }

  @Override
  public String getBoard() {
    return "";
  }

  @Override
  public boolean getTurn() {
    return false;
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
    return null;
  }

  @Override
  public TeamColor whoOwnsCardAt(int row, int col) {
    return null;
  }

  @Override
  public int getPlayerScore(TeamColor teamColor) {
    return 0;
  }

  @Override
  public Object[][] getBoardArray() {
    return new Object[0][];
  }
}
