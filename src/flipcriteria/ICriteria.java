package flipcriteria;

import java.util.List;

import card.ICard;
import model.IModel;
import player.IPlayer;

/**
 * Represents a criteria for flipping cards on the board based on specific rules.
 *
 * <p>The {@code ICriteria} interface provides a method for applying custom
 * flipping rules to the game board. Implementations of this interface define
 * specific rules (e.g., "Same", "Plus") for determining which cards are flipped
 * after a card is placed on the board.
 */
public interface ICriteria {

  /**
   * Applies the flipping criteria based on the specific rules defined by the implementation.
   *
   * @param model      The game model, providing access to the game state and board.
   * @param placedCard The card that was just placed on the board.
   * @param x          The x-coordinate where the card was placed.
   * @param y          The y-coordinate where the card was placed.
   * @param player     The player who placed the card.
   * @return A list of positions (represented as int arrays of size 2) that indicate
   *         the coordinates of the cards to be flipped. If no cards are flipped,
   *         the list will be empty.
   */
  List<int[]> applyFlipCriteria(IModel model, ICard placedCard, int x, int y, IPlayer player);
}
