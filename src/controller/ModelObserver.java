package controller;

import model.PlayerColor;

/**
 * Interface representing a listener for updates on the game's status. Implementations
 * of this interface can be used to receive notifications when the game's turn changes
 * or when the game ends.
 */
public interface ModelObserver {

  /**
   * Called when the turn changes in the game. This method notifies the listener
   * that the current player has changed, allowing for any necessary updates to
   * be made in the view or controller.
   *
   * @param currentPlayer the color of the player whose turn it currently is
   */
  void onTurnChanged(PlayerColor currentPlayer);

  /**
   * Called when the game is over. This method notifies the listener about the
   * end of the game and provides the color of the winning player.
   *
   * @param winningPlayer the color of the player who won the game; could be null
   *                      if the game ends in a draw or if there is no specific winner
   */
  void onGameOver(PlayerColor winningPlayer);
}