package view;

import controller.Features;
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

  /** Highlights a card belonging to a given player at a given index (row in the hand).
   *
   * @param row the row the card is located at (0 indexed)
   * @param color the color the card to be flipped.
   */
  void highlightCard(int row, PlayerColor color);

  /**
   * Updates the board display based on the provided 2D array of {@link ICard} objects.
   * Each cell on the board is updated to show a card or remain empty if null.
   * Replaces components on the board with {@link CardPanelGUIImpl} panels where cards
   * are present, or blank panels for empty cells.
   *
   * @param boardWithCard a 2D array representing the board, where each {@code ICard}
   *                      can be a card to display, or {@code null} for an empty cell
   */
  void updateBoard(ICard[][] boardWithCard);

  /**
   * Updates the hand panel of the specified player by removing the card at the given index.
   * Refreshes the display to reflect the removal.
   *
   * @param cardIndexToPlace the index of the card in the player's hand to be removed
   * @param playerPlacing    the player whose hand is being updated
   */
  void updateHand(int cardIndexToPlace, IPlayer playerPlacing);
}
