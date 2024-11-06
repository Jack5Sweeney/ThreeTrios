package controller;

/**
 * Interface representing the features and actions that the view can trigger within the controller.
 * These methods define user interaction events, such as clicking on cells or cards, that are handled
 * by the controller implementation.
 */
public interface Features {

  /**
   * Handles the event when a cell on the game grid is clicked. This event is typically used to
   * trigger game actions related to the grid-based board.
   *
   * @param row the row index of the clicked cell
   * @param col the column index of the clicked cell
   */
  void handleCellClick(int row, int col);

  /**
   * Handles the event when a card in the player's hand is clicked. This allows the controller
   * to process card-based interactions within the game.
   *
   * @param row the row index representing the position of the clicked card
   */
  void handleCardClick(int row);
}
