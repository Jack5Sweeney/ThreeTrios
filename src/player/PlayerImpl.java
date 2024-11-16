package player;

import card.ICard;
import model.IModel;
import strategies.Placement;

import java.util.ArrayList;

/**
 * Represents a player in the game, holding a hand of cards and associated with a specific color.
 * This class implements the {@link IPlayer} interface and provides methods to access and modify
 * the player's color and their hand of cards.
 */
public class PlayerImpl implements IPlayer{

  /**
   * The color representing the player (e.g., RED or BLUE).
   */
  private final PlayerColor playerColor;

  /**
   * The hand of cards that the player holds.
   */
  private final ArrayList<ICard> hand;

  /**
   * Constructs a {@code PlayerImpl} with the specified player color and initial hand of cards.
   *
   * @param playerColor the {@link PlayerColor} representing the player (e.g., RED or BLUE)
   * @param hand        the initial hand of {@link ICard} objects for the player
   */
  public PlayerImpl(PlayerColor playerColor, ArrayList<ICard> hand) {
    this.playerColor = playerColor;
    this.hand = new ArrayList<>(hand);  // Create a defensive copy of the hand
  }

  /**
   * Gets the color associated with this player.
   *
   * @return the {@link PlayerColor} of this player
   */
  @Override
  public PlayerColor getPlayerColor() {
    if (this.playerColor == PlayerColor.RED) {
      return PlayerColor.RED;
    } else {
      return PlayerColor.BLUE;
    }
  }

  /**
   * Retrieves a copy of the player's hand of cards.
   *
   * @return a new {@link ArrayList} containing the player's cards
   */
  @Override
  public ArrayList<ICard> getHand() {
    return new ArrayList<>(this.hand);  // Return a defensive copy of the hand
  }

  /**
   * Adds a card to the player's hand.
   *
   * @param card the {@link ICard} to add to the hand
   */
  @Override
  public void addToHand(ICard card) {
    this.hand.add(card);
  }

  /**
   * Removes a card from the player's hand at the specified index.
   *
   * @param index the index of the card to be removed
   * @return the {@link ICard} that was removed from the hand
   * @throws IndexOutOfBoundsException if the index is out of range
   */
  @Override
  public ICard removeFromHand(int index) {
    return this.hand.remove(index);
  }

  @Override
  public Placement chooseMove(IModel model) {
    return null;
  }
}
