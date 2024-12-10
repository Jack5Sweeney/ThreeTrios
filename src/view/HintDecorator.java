package view;

import java.awt.*;
import java.util.List;

import javax.swing.*;

import card.CellTypeContents;
import card.ICard;
import controller.Features;
import model.ReadOnlyIModel;
import player.PlayerColor;

/**
 * A decorator class for the {@link IViewFrameGUI} interface that enhances the view
 * by adding hint generation functionality. This class wraps an existing view
 * and provides additional behavior for managing hints during the game.
 */
public class HintDecorator implements IViewFrameGUI {

  /**
   * The original view that this decorator wraps.
   */
  private final IViewFrameGUI delegate;

  /**
   * The game model, which provides the state of the game.
   */
  private final ReadOnlyIModel model;

  /**
   * The color of the player for whom the hints are being generated.
   */
  private final PlayerColor playerColor;

  /**
   * Indicates whether hints are currently enabled.
   */
  private boolean hintsEnabled;

  /**
   * Stores flip counts for hint generation. This array is used to keep track of
   * the number of flips for each cell.
   */
  private int[][] flipCounts;

  private JLabel flipCountLabel;

  /**
   * Constructs a {@code HintDecorator} object.
   *
   * @param delegate    the original view to decorate
   * @param model       the game model
   * @param playerColor the color of the player for whom hints are generated
   */
  public HintDecorator(IViewFrameGUI delegate, ReadOnlyIModel model, PlayerColor playerColor) {
    this.delegate = delegate;
    this.model = model;
    this.playerColor = playerColor;
    this.hintsEnabled = false; // Default to hints being disabled
  }

  /**
   * Enables hint generation and updates the view with hints.
   *
   * @param flipCounts the matrix representing the flip counts for each cell
   */
  @Override
  public void enableHints(int[][] flipCounts) {
    this.hintsEnabled = true;
    generateHints();
  }

  /**
   * Disables hint generation and clears all hints from the view.
   */
  @Override
  public void disableHints() {
    this.hintsEnabled = false;
    clearHints();
  }

  /**
   * Generates hints for the player based on the currently highlighted card
   * and the game board state.
   */
  private void generateHints() {
    int rows = model.getBoard().length;
    int cols = model.getBoard()[0].length;

    // Get the index of the currently highlighted card
    int highlightedCardIndex = delegate.getHighlightedCardIndex(playerColor);

    // If no card is highlighted, clear hints and return
    if (highlightedCardIndex == -1) {
      clearHints();
      return;
    }

    // Retrieve the player's hand
    List<ICard> hand = playerColor == PlayerColor.RED
            ? model.getRedPlayer().getHand()
            : model.getBluePlayer().getHand();

    // Get the highlighted card from the hand
    ICard highlightedCard = hand.get(highlightedCardIndex);

    // Iterate over all cells and calculate flip counts for the highlighted card
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        int totalFlips = model.calculateFlips(row, col, highlightedCard);

        Component cell = delegate.getBoardComponent(row, col);
        if (cell instanceof ViewBoardPanelGUIImpl.CellPanel) {
          ViewBoardPanelGUIImpl.CellPanel cellPanel = (ViewBoardPanelGUIImpl.CellPanel) cell;
          if (cellPanel.getCellType() == CellTypeContents.EMPTY) {
            cellPanel.setFlipCount(totalFlips);
          } else {
            cellPanel.clearFlipCount();
          }
        }
      }
    }
  }

  /**
   * Clears all hints from the game board by resetting the flip counts in all cells.
   */
  private void clearHints() {
    int rows = model.getBoard().length;
    int cols = model.getBoard()[0].length;

    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        Component cell = delegate.getBoardComponent(row, col);
        if (cell instanceof ViewBoardPanelGUIImpl.CellPanel) {
          ((ViewBoardPanelGUIImpl.CellPanel) cell).clearFlipCount();
        }
      }
    }
  }

  /**
   * Refreshes the view to reflect the current state of the game.
   */
  @Override
  public void refresh() {
    delegate.refresh();
  }

  /**
   * Makes the view visible to the user.
   */
  @Override
  public void makeVisible() {
    delegate.makeVisible();
  }

  /**
   * Adds the features of the controller to the view, enabling user interaction.
   *
   * @param features the controller features to add
   */
  @Override
  public void addFeatures(Features features) {
    delegate.addFeatures(features);
  }

  /**
   * Highlights a card in the view based on the player's color and the row index.
   *
   * @param row   the row index of the card to highlight
   * @param color the color of the player whose card is to be highlighted
   */
  @Override
  public void highlightCard(int row, PlayerColor color) {
    delegate.highlightCard(row, color);
  }

  /**
   * Updates the board with the given cards.
   *
   * @param boardWithCard a 2D array of cards to update the board with
   */
  @Override
  public void updateBoard(ICard[][] boardWithCard) {
    delegate.updateBoard(boardWithCard);
  }

  /**
   * Enables user interactions in the view.
   */
  @Override
  public void enableInteractions() {
    delegate.enableInteractions();
  }

  /**
   * Disables user interactions in the view.
   */
  @Override
  public void disableInteractions() {
    delegate.disableInteractions();
  }

  /**
   * Displays an error message to the user.
   *
   * @param message the error message to display
   */
  @Override
  public void showErrorMessage(String message) {
    delegate.showErrorMessage(message);
  }

  /**
   * Displays a game-over message to the user.
   *
   * @param message the game-over message to display
   */
  @Override
  public void showGameOver(String message) {
    delegate.showGameOver(message);
  }

  /**
   * Sets the title of the view.
   *
   * @param title the new title of the view
   */
  @Override
  public void setTitle(String title) {
    delegate.setTitle(title);
  }

  /**
   * Brings the view to the front of the screen.
   */
  @Override
  public void bringToFront() {
    delegate.bringToFront();
  }

  /**
   * Refreshes the display of the players' hands.
   *
   * @param redHand  the red player's hand
   * @param blueHand the blue player's hand
   */
  @Override
  public void refreshHands(List<ICard> redHand, List<ICard> blueHand) {
    delegate.refreshHands(redHand, blueHand);
  }

  /**
   * Retrieves the board component at a specific row and column.
   *
   * @param row the row index
   * @param col the column index
   * @return the board component at the specified location
   */
  @Override
  public Component getBoardComponent(int row, int col) {
    return delegate.getBoardComponent(row, col);
  }

  /**
   * Retrieves the index of the currently highlighted card for a specific player.
   *
   * @param color the color of the player
   * @return the index of the highlighted card, or -1 if no card is highlighted
   */
  @Override
  public int getHighlightedCardIndex(PlayerColor color) {
    return delegate.getHighlightedCardIndex(color);
  }
}
