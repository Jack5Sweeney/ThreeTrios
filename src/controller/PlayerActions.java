package controller;

import strategies.Placement;

public interface PlayerActions {
  /**
   * Requests the player to choose their next move.
   * For machine players, this triggers a strategy-based decision.
   * For human players, it waits for interaction through the view.
   *
   * @return the Placement (row, column, and card index) representing the chosen move.
   */
  void choosePlayerMove();
}
