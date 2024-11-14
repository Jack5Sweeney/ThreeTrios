package ControllerTesting;

import controller.Features;
import model.ICard;
import model.IPlayer;
import model.PlayerColor;
import view.IViewFrameGUI;

/**
 * A mock implementation of the {@link IViewFrameGUI} interface used for testing purposes.
 * It tracks method calls and stores parameters to verify interactions with the view during
 * tests.
 */
public class MockView implements IViewFrameGUI {
  public boolean addFeaturesCalled = false;
  public boolean makeVisibleCalled = false;
  public boolean updateBoardCalled = false;
  public boolean updateHandCalled = false;
  public int highlightedRow;
  public PlayerColor highlightedColor;
  public MockModel mockModel;

  /**
   * Constructs a {@code MockView} associated with the specified {@link MockModel}.
   *
   * @param mockModel the mock model to associate with this view
   */
  public MockView(MockModel mockModel) {
    this.mockModel = mockModel;
  }

  /**
   * Tracks that the {@code addFeatures} method was called with the specified features.
   *
   * @param features the features to add to the view
   */
  @Override
  public void addFeatures(Features features) {
    addFeaturesCalled = true;
  }

  /**
   * Tracks that the {@code makeVisible} method was called to display the view.
   */
  @Override
  public void makeVisible() {
    makeVisibleCalled = true;
  }

  /**
   * Tracks the row and color of the highlighted card.
   *
   * @param row   the row of the card to highlight
   * @param color the color associated with the highlighted card
   */
  @Override
  public void highlightCard(int row, PlayerColor color) {
    highlightedRow = row;
    highlightedColor = color;
  }

  /**
   * Tracks that the {@code updateBoard} method was called with the provided board state.
   *
   * @param boardWithCard the current board state, including any placed cards
   */
  @Override
  public void updateBoard(ICard[][] boardWithCard) {
    updateBoardCalled = true;
  }

  /**
   * Tracks that the {@code updateHand} method was called with the specified card index and
   * player.
   *
   * @param cardIndexToPlace the index of the card being placed
   * @param playerPlacing    the player placing the card
   */
  @Override
  public void updateHand(int cardIndexToPlace, IPlayer playerPlacing) {
    updateHandCalled = true;
  }

  /**
   * Refreshes the view. No action is performed in this mock implementation.
   */
  @Override
  public void refresh() {
    // No action needed for refresh in the mock
  }
}
