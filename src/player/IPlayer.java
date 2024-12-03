package player;

import card.ICard;
import model.IModel;
import strategies.Placement;

import java.util.List;

/**
 * Interface representing a player in a Triple Triad game. A player is associated with a
 * specific color and manages a hand of cards, allowing cards to be added or removed.
 */
public interface IPlayer {

  /**
   * Retrieves the color associated with the player.
   *
   * @return the {@link PlayerColor} representing the player (e.g., RED or BLUE)
   */
  PlayerColor getPlayerColor();

  /**
   * Retrieves a copy of the player's current hand of cards.
   *
   * @return a list of {@link ICard} objects representing the player's hand
   */
  List<ICard> getHand();

  /**
   * Adds a card to the player's hand.
   *
   * @param card the {@link ICard} to be added to the hand
   */
  void addToHand(ICard card);

  /**
   * Removes a card from the player's hand at the specified index.
   *
   * @param index the index of the card to be removed
   * @return the {@link ICard} that was removed from the hand
   * @throws IndexOutOfBoundsException if the index is out of range
   */
  ICard removeFromHand(int index);

  Placement chooseMove(IModel model);
}
