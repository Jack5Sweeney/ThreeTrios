package controllertesting;

import view.IViewFrameGUI;
import controller.Features;
import player.PlayerColor;
import card.ICard;

import java.util.List;

/**
 * Mock implementation of the IViewFrameGUI for testing purposes.
 */
public class MockView implements IViewFrameGUI {

  public String lastErrorMessage; // Stores the most recent error message
  public String lastGameOverMessage; // Stores the most recent game-over message
  public boolean interactionsEnabled; // Tracks whether interactions are enabled
  public String title; // Stores the last set title

  public int highlightedRow;
  public PlayerColor highlightedColor;

  public MockView(MockModelForControllerGUI model) {
    // Implementation not needed in the mock
  }

  @Override
  public void showErrorMessage(String message) {
    lastErrorMessage = message; // Capture the error message
  }

  @Override
  public void showGameOver(String message) {
    lastGameOverMessage = message; // Capture the game-over message
  }

  @Override
  public void enableInteractions() {
    interactionsEnabled = true;
  }

  @Override
  public void disableInteractions() {
    interactionsEnabled = false;
  }

  @Override
  public void setTitle(String title) {
    this.title = title; // Capture the last set title
  }

  // Stub methods for other interface methods
  @Override
  public void refresh() {
    // Implementation not needed in the mock
  }

  @Override
  public void makeVisible() {
    // Implementation not needed in the mock
  }

  @Override
  public void addFeatures(Features features) {
    // Implementation not needed in the mock
  }

  @Override
  public void highlightCard(int row, PlayerColor color) {
    highlightedRow = row;
    highlightedColor = color;
  }

  @Override
  public void updateBoard(ICard[][] board) {
    // Implementation not needed in the mock
  }

  @Override
  public void refreshHands(List<ICard> redHand, List<ICard> blueHand) {
    // Implementation not needed in the mock
  }

  @Override
  public void showFlipCounts(int[][] flipCounts) {

  }

  @Override
  public void bringToFront() {
    // Implementation not needed in the mock
  }
}
