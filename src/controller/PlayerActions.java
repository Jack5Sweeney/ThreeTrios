package controller;

/**
 * Interface representing the actions a player needs to do.
 */
public interface PlayerActions {
  /**
   * Requests the player to choose their next move.
   * For machine players, this triggers a strategy-based decision.
   * For human players, it waits for interaction through the view.
   *
   */
  void choosePlayerMove();
}
