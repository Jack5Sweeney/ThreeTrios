package view;

import controller.Features;

/**
 * Interface for the hands panel within the GUI view. This panel displays the player's hand
 * and defines methods to handle user interactions with the cards in the hand.
 */
public interface IViewHandsPanelGUI {

  /**
   * Adds a click listener to the hands panel to handle user interactions. This allows
   * the controller to process click events on cards within the player's hand.
   *
   * @param features the controller's features that define the actions available to handle user input
   */
  void addClickListener(Features features);
}