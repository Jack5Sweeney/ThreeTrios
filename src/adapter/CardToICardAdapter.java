package adapter;

import card.Direction;
import card.ICard;
import player.PlayerColor;
import provider.src.threetrios.model.Card;
import provider.src.threetrios.model.TeamColor;

public class CardToICardAdapter implements Card {
  private final ICard iCard;
  public CardToICardAdapter(ICard card) {
    this.iCard = card;
  }
  @Override
  public int getNorthValue() {
    return this.iCard.getDirectionsAndValues().get(Direction.NORTH).getValue();
  }

  @Override
  public int getSouthValue() {
    return this.iCard.getDirectionsAndValues().get(Direction.SOUTH).getValue();
  }

  @Override
  public int getEastValue() {
    return this.iCard.getDirectionsAndValues().get(Direction.EAST).getValue();
  }

  @Override
  public int getWestValue() {
    return this.iCard.getDirectionsAndValues().get(Direction.WEST).getValue();
  }

  @Override
  public void flip() {
  }

  @Override
  public TeamColor getColor() {
    if(this.iCard.getPlayerColor().equals(PlayerColor.BLUE)) {
      return TeamColor.BLUE;
    }
    else {
      return TeamColor.RED;
    }
  }

  @Override
  public String getName() {
    return this.iCard.getName();
  }
}
