package view;

import controller.Features;
import model.CardImpl;
import model.ICard;
import model.IPlayer;
import model.PlayerColor;

/**
 * Interface for the main GUI frame of the view component. Defines methods to manage
 * the visibility, refresh functionality, and interaction setup of the view during the game.
 */
public interface IViewFrameGUI {

  /**
   * Refreshes the view to reflect any changes in the game state.
   * This method should be called whenever there is an update in the model that the view needs
   * to display.
   */
  void refresh();

  /**
   * Makes the view visible, initiating the game session for the user.
   * This method is typically called once the game setup is complete.
   */
  void makeVisible();

  /**
   * Adds feature handlers to the view to manage user interactions. This connects the
   * controller's action handlers to the view, allowing user inputs to be processed.
   *
   * @param features the controller's features that handle user interactions
   */
  void addFeatures(Features features);

  /** Highlights a card belonging to a given player at a given index (row in the hand)
   *
   * @param row the row the card is located at (0 indexed)
   * @param color the color the card to be flipped.
   */
  void highlightCard(int row, PlayerColor color);

  void updateBoard(ICard[][] boardWithCard);

  void updateHand(int cardIndexToPlace, IPlayer playerPlacing);
}
