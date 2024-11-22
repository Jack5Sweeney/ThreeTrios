package view;

import controller.Features;
import card.ICard;
import player.PlayerColor;

import java.util.List;

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

  /**
   * Highlights a card belonging to a specified player at a given index in the player's hand.
   * This visually indicates that the card is selected by the player.
   *
   * @param row   the row (index) where the card is located within the player's hand (0-indexed)
   * @param color the color representing the player who selected the card
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
   * Enables user interactions in the view, allowing the player to make moves and select cards.
   * This method is typically called when it is the player's turn.
   */
  void enableInteractions();

  /**
   * Disables user interactions in the view, preventing the player from making moves or
   * selecting cards. This method is typically called when it is not the player's turn.
   */
  void disableInteractions();

  /**
   * Displays an error message to the user in the view. This can be used to notify the user
   * of invalid actions, such as attempting to make a move when it is not their turn.
   *
   * @param message the error message to be displayed
   */
  void showErrorMessage(String message);

  /**
   * Displays a game-over message in the view, informing the player of the outcome of the game.
   * This could be a win, loss, or tie message.
   *
   * @param message the message indicating the result of the game
   */
  void showGameOver(String message);

  /**
   * Sets the title of the game window or view to the specified text. This can be used to
   * display status information, such as whose turn it is.
   *
   * @param title the title text to display in the game window
   */
  void setTitle(String title);

  /**
   * Brings the view window to the front, making it the active window.
   */
  void bringToFront();

  void refreshHands(List<ICard> redHand, List<ICard> blueHand);
}
