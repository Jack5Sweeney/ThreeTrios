package model;

import strategies.Placement;

public interface IPlayerActions extends IPlayer {
  /**
   * Requests the player to choose their next move.
   * For machine players, this triggers a strategy-based decision.
   * For human players, it waits for interaction through the view.
   *
   * @return the Placement (row, column, and card index) representing the chosen move.
   */
  Placement chooseMove();
}
