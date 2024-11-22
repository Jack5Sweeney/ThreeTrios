package player;

import card.ICard;
import model.IModel;
import strategies.IStrategy;
import strategies.Placement;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the {@link IPlayer} interface, representing an AI-controlled player.
 * The AI uses a provided strategy to make decisions and maintains a hand of cards.
 */
public class AIPlayerImpl implements IPlayer {

  private final IStrategy strategy;
  private final PlayerColor playerColor;
  private final List<ICard> hand;

  /**
   * Constructs an {@code AIPlayerImpl} with the specified strategy, player color, and initial hand.
   *
   * @param strategy the strategy the AI player will use to decide moves
   * @param playerColor the color representing this player
   * @param hand the initial hand of cards for the player
   */
  public AIPlayerImpl(IStrategy strategy, PlayerColor playerColor, ArrayList<ICard> hand) {
    this.strategy = strategy;
    this.playerColor = playerColor;
    this.hand = new ArrayList<>(hand); // Defensive copy of the provided hand
  }

  /**
   * Determines the move to make based on the current state of the game.
   *
   * @param model the current state of the game model
   * @return the move chosen by the AI player's strategy
   */
  @Override
  public Placement chooseMove(IModel model) {
    return strategy.chooseMove(model, this);
  }

  /**
   * Returns the color representing this player.
   *
   * @return the player's color
   */
  @Override
  public PlayerColor getPlayerColor() {
    return this.playerColor;
  }

  /**
   * Returns a copy of the player's current hand to prevent external modification.
   *
   * @return a defensive copy of the player's hand
   */
  @Override
  public ArrayList<ICard> getHand() {
    return new ArrayList<>(this.hand);
  }

  /**
   * Adds a card to the player's hand.
   *
   * @param card the card to add
   */
  @Override
  public void addToHand(ICard card) {
    this.hand.add(card);
  }

  /**
   * Removes and returns a card from the player's hand at the specified index.
   *
   * @param index the index of the card to remove
   * @return the card removed from the player's hand
   * @throws IndexOutOfBoundsException if the index is out of range
   */
  @Override
  public ICard removeFromHand(int index) {
    return this.hand.remove(index);
  }
}
