package adapter;

import provider.src.threetrios.controller.PlayerActionsListener;
import view.IViewFrameGUI;
import provider.src.threetrios.view.IGUIView;
import controller.Features;
import card.ICard;
import player.PlayerColor;

import java.util.List;

/**
 * Object adapter to bridge between {@link IViewFrameGUI} and {@link IGUIView}.
 * This class adapts methods from {@link IViewFrameGUI} to the {@link IGUIView} interface.
 */
public class IGUIViewToIViewFrameGUIAdapter implements IViewFrameGUI {

  private final IGUIView providedView;

  /**
   * Constructs an adapter for the given {@link IGUIView}.
   *
   * @param providedView the provided IGUIView instance
   */
  public IGUIViewToIViewFrameGUIAdapter(IGUIView providedView) {
    this.providedView = providedView;
  }

  /**
   * Refreshes the view by delegating to the provided view's refresh method.
   */
  @Override
  public void refresh() {
    providedView.refresh();
  }

  /**
   * Makes the view visible by delegating to the provided view's setVisible method.
   */
  @Override
  public void makeVisible() {
    providedView.setVisible();
  }

  /**
   * Adds features (controller actions) to the view by adapting {@link Features}
   * to the {@link PlayerActionsListener}.
   *
   * @param features the features to add
   */
  @Override
  public void addFeatures(Features features) {
    providedView.addListener(new FeaturesToPlayerActionListener(features));
  }

  /**
   * Highlights a card at the specified row and for the given player color.
   * <p>Not implemented as highlighting is handled by the provider's implementation.</p>
   *
   * @param row   the row of the card to highlight
   * @param color the player's color
   */
  @Override
  public void highlightCard(int row, PlayerColor color) {
    // Highlighting is handled in the provider's implementation
  }

  /**
   * Updates the board with the given cards.
   * <p>Not fully adapted as the provided view manages board state internally.</p>
   *
   * @param boardWithCard a 2D array representing the board
   */
  @Override
  public void updateBoard(ICard[][] boardWithCard) {
    providedView.refresh();
  }

  /**
   * Enables interactions in the view.
   * <p>Not implemented as the provider does not handle enabling/disabling interactions.</p>
   */
  @Override
  public void enableInteractions() {
    // Provider does not enable/disable interactions
  }

  /**
   * Disables interactions in the view.
   * <p>Not implemented as the provider does not handle enabling/disabling interactions.</p>
   */
  @Override
  public void disableInteractions() {
    // Provider does not enable/disable interactions
  }

  /**
   * Displays an error message in the view.
   * <p>Not implemented as error handling is not part of this adaptation.</p>
   *
   * @param message the error message to display
   */
  @Override
  public void showErrorMessage(String message) {
    // Not implemented as error handling is not part of the adaptation
  }

  /**
   * Displays a game over message in the view.
   * <p>Not implemented as game over handling is not part of this adaptation.</p>
   *
   * @param message the game over message to display
   */
  @Override
  public void showGameOver(String message) {
    // Not implemented as game over handling is not part of the adaptation
  }

  /**
   * Sets the title of the view.
   * <p>Not implemented as title management is not needed in this adaptation.</p>
   *
   * @param title the title to set
   */
  @Override
  public void setTitle(String title) {
    // Not implemented as title management is not needed in this adaptation
  }

  /**
   * Brings the view to the front.
   * <p>Not implemented as this functionality is not needed in the adaptation.</p>
   */
  @Override
  public void bringToFront() {
    // Not implemented as this functionality is not needed in the adaptation
  }

  /**
   * Refreshes the hands of the red and blue players.
   * <p>Not fully adapted as the provided view refreshes all states, including hands.</p>
   *
   * @param redHand  the red player's hand
   * @param blueHand the blue player's hand
   */
  @Override
  public void refreshHands(List<ICard> redHand, List<ICard> blueHand) {
    providedView.refresh();
  }

  @Override
  public void enableHints(int[][] flipCounts) {

  }

  @Override
  public void disableHints() {

  }
}
