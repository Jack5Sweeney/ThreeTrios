package controllertesting;

import controller.ModelObserver;
import model.*;
import card.*;
import player.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A mock implementation of the IModel interface for testing ControllerGUIImpl.
 * Tracks interactions and provides mock values for required methods.
 */
public class MockModelForControllerGUI implements IModel {

  // Tracks coordinates logged during getCardAt
  public final List<List<Integer>> cordLog = new ArrayList<>();

  // Mock board and availability arrays
  private final ICard[][] boardWithCards;
  private final CellType[][] boardAvailability;

  // Constructor to set up mock board and availability
  public MockModelForControllerGUI(ICard[][] boardWithCards, CellType[][] boardAvailability) {
    this.boardWithCards = boardWithCards;
    this.boardAvailability = boardAvailability;
  }

  @Override
  public void placeCard(int boardRow, int boardCol, int cardIndexInHand, IPlayer player) {
    // No action needed; placeholder for ControllerGUI testing
  }

  @Override
  public void updateCardToPlace(int index, PlayerColor color) {
    // No action needed; placeholder for ControllerGUI testing
  }

  @Override
  public void addObserver(ModelObserver observer) {
    // No action needed for the mock
  }

  @Override
  public CardImpl getCardAt(int boardRow, int boardCol) {
    // Log accessed coordinates
    cordLog.add(List.of(boardRow, boardCol));
    return null; // Return null since no real cards are involved in the mock
  }

  @Override
  public ICard[][] getBoard() {
    return this.boardWithCards;
  }

  @Override
  public CellType[][] getBoardAvailability() {
    return this.boardAvailability;
  }

  @Override
  public PlayerColor getCurrentPlayerColor() {
    return null; // Mock player color
  }

  @Override
  public int calculateFlips(int row, int col, ICard card) {
    return 0; // Return 0 flips as default for testing
  }

  @Override
  public boolean checkGameStarted() {
    return false; // Default to false for testing
  }

  @Override
  public boolean checkGameOver() {
    return false; // Default to false for testing
  }

  @Override
  public IPlayer getWinningPlayer() {
    return null; // No winner in the mock
  }

  @Override
  public int getPlayerScore(PlayerColor playerColor) {
    return 0; // Default to score 0 for testing
  }

  @Override
  public void startGame() {
    // No action needed; placeholder for testing
  }

  @Override
  public IPlayer getPlayerToPlace() {
    return null; // Return null for the player
  }

  @Override
  public int getCardIndexToPlace() {
    return 0; // Default card index
  }

  @Override
  public IPlayer getRedPlayer() {
    return null; // Return null for the red player
  }

  @Override
  public IPlayer getBluePlayer() {
    return null; // Return null for the blue player
  }
}
