package view;

import card.CellTypeContents;
import controller.Features;

/**
 * Interface for the board panel within the GUI view. Defines methods for setting up
 * interactive components and handling user interactions on the game board.
 */
public interface IViewBoardPanelGUI {

  /**
   * Adds a click listener to the board panel to handle user interactions. The listener
   * allows the controller to process click events on the board.
   *
   * @param features the controller's features that define the actions available to handle
   *                user input
   */
  void addClickListener(Features features);

  /**
   * Enables user interactions in the view, allowing the player to make moves and select cards.
   * This method is typically called when it is the player's turn.
   */
  void enableInteractions();

  /**
   * Disables user interactions in the view, preventing the player from making moves or
   * selecting cards. This method is typically called when it is not the player's turn.
   */
  void disableInteractions();

}
