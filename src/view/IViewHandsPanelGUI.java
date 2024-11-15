package view;

import controller.Features;

import java.awt.*;

/**
 * Interface for the hands panel within the GUI view. This panel displays the player's hand
 * and defines methods to handle user interactions with the cards in the hand.
 */
public interface IViewHandsPanelGUI {

  /**
   * Adds a click listener to the hands panel to handle user interactions. This allows
   * the controller to process click events on cards within the player's hand.
   *
   * @param features the controller's features that define the actions available to handle
   *                 user input
   */
  void addClickListener(Features features);

  /**
   * Removes the card at the specified index from the hand, setting the card reference
   * at that index to {@code null}. This method clears and rebuilds the panel to
   * reflect the change, including empty spots for removed cards.
   *
   * @param index the index of the card to remove in the hand
   */
  void removeCardAtIndex(int index);

  /**
   * Enables interaction with all components within this container. This method
   * iterates through each component and enables it, allowing the user to interact
   * with the view.
   */
   void enableInteraction();

  /**
   * Disables interaction with all components within this container. This method
   * iterates through each component and disables it, preventing any user interaction
   * with the view.
   */
   void disableInteraction();
}

