package player;

import card.ICard;
import model.IModel;
import strategies.IStrategy;
import strategies.Placement;

import java.util.ArrayList;
import java.util.List;

public class AIPlayerImpl implements IPlayer {

  IStrategy strategy;
  PlayerColor playerColor;
  List<ICard> hand;

  public AIPlayerImpl(IStrategy strategy, PlayerColor playerColor, ArrayList<ICard> hand) {
    this.playerColor = playerColor;
    this.hand = new ArrayList<>(hand);
    this.strategy = strategy;
  }

  @Override
  public Placement chooseMove(IModel model) {
    return strategy.chooseMove(model, this);
  }

  @Override
  public PlayerColor getPlayerColor() {
    if (this.playerColor == PlayerColor.RED) {
      return PlayerColor.RED;
    } else {
      return PlayerColor.BLUE;
    }
  }

  @Override
  public ArrayList<ICard> getHand() {
    return new ArrayList<>(this.hand);  // Return a defensive copy of the hand
  }

  @Override
  public void addToHand(ICard card) {
    this.hand.add(card);
  }

  @Override
  public ICard removeFromHand(int index) {
    return this.hand.remove(index);
  }
}
