package strategies;

import model.IModel;
import model.PlayerImpl;

/**
 * Interface representing a strategy for determining a player's move.
 * Classes implementing this interface define specific approaches for
 * selecting the best placement for a card on the board.
 */
public interface IStrategy {

  /**
   * Chooses the optimal move for the player based on the current game model.
   *
   * @param model  the game model containing the current board state and other game information
   * @param player the player making the move, with access to their current hand of cards
   * @return a {@link Placement} representing the selected position and card index for the move
   */
  Placement chooseMove(IModel model, PlayerImpl player);

}
