package teststrategies;

import card.CardImpl;
import card.CellType;
import card.ICard;
import controller.ModelObserver;
import model.*;
import player.IPlayer;
import player.PlayerColor;

import java.util.ArrayList;
import java.util.List;

/**
 * A mock implementation of the {@link IModel} interface used for testing purposes.
 * Records the coordinates checked on the board, allowing verification of interactions
 * within tests.
 */
public class MockModelRecordCoordinatesChecked implements IModel {
  public ArrayList<ArrayList<Integer>> cordLog;
  private final ICard[][] boardWithCards;
  private final CellType[][] boardAvailability;

  /**
   * Constructs a {@code MockModelRecordCoordinatesChecked} with a specified board configuration.
   *
   * @param boardWithCards   the initial state of the board with cards
   * @param boardAvailability the availability of each cell on the board
   */
  public MockModelRecordCoordinatesChecked(ICard[][] boardWithCards,
                                           CellType[][] boardAvailability) {
    this.cordLog = new ArrayList<ArrayList<Integer>>();
    this.boardWithCards = boardWithCards;
    this.boardAvailability = boardAvailability;
  }

  /**
   * Mock implementation of {@code placeCard} that takes the parameters but performs no action.
   *
   * @param boardRow         the row on the board where the card would be placed
   * @param boardCol         the column on the board where the card would be placed
   * @param cardIndexInHand  the index of the card in the player's hand
   * @param player           the player placing the card
   */
  @Override
  public void placeCard(int boardRow, int boardCol, int cardIndexInHand, IPlayer player) {
    // No action needed for placeCard in the mock
  }

  /**
   * Mock implementation of {@code updateCardToPlace} that takes the parameters but performs
   * no action.
   *
   * @param index the index of the card in the player's hand to update
   * @param color the color of the card to be updated
   */
  @Override
  public void updateCardToPlace(int index, PlayerColor color) {
    // No action needed for updateCardToPlace in the mock
  }

  /**
   * Returns the player who is next to place a card. Always returns null in this mock.
   *
   * @return null, as no player is set in the mock
   */
  @Override
  public IPlayer getPlayerToPlace() {
    return null;
  }

  /**
   * Returns the index of the card to be placed. Always returns 0 in this mock.
   *
   * @return 0, as no specific card index is set in the mock
   */
  @Override
  public int getCardIndexToPlace() {
    return 0;
  }

  /**
   * Stub method in mock.
   *
   * @param observer the observer to be added; must not be null
   */
  @Override
  public void addObserver(ModelObserver observer) {

  }

  /**
   * Records the coordinates of the cell being accessed and always returns null.
   *
   * @param boardRow the row of the board cell being accessed
   * @param boardCol the column of the board cell being accessed
   * @return null, as no specific card is associated with the cell
   */
  @Override
  public CardImpl getCardAt(int boardRow, int boardCol) {
    cordLog.add(new ArrayList<>(List.of(boardRow, boardCol)));
    return null;
  }

  /**
   * Returns the red player. Always returns null in this mock.
   *
   * @return null, as no specific player is associated with the mock
   */
  @Override
  public IPlayer getRedPlayer() {
    return null;
  }

  /**
   * Returns the blue player. Always returns null in this mock.
   *
   * @return null, as no specific player is associated with the mock
   */
  @Override
  public IPlayer getBluePlayer() {
    return null;
  }

  /**
   * Returns the board setup with cards, as initially provided.
   *
   * @return the 2D array representing the board with cards
   */
  public ICard[][] getBoard() {
    return this.boardWithCards;
  }

  /**
   * Returns the cell availability status for each position on the board.
   *
   * @return the 2D array representing the cell availability on the board
   */
  @Override
  public CellType[][] getBoardAvailability() {
    return this.boardAvailability;
  }

  /**
   * Checks if the game has started. Always returns false in this mock.
   *
   * @return false, as no game state is managed in the mock
   */
  @Override
  public boolean checkGameStarted() {
    return false;
  }

  /**
   * Checks if the game is over. Always returns false in this mock.
   *
   * @return false, as no game state is managed in the mock
   */
  @Override
  public boolean checkGameOver() {
    return false;
  }

  /**
   * Retrieves the winning player. Always returns null in this mock.
   *
   * @return null, as no winning player is determined in the mock
   */
  @Override
  public IPlayer getWinningPlayer() {
    return null;
  }

  /**
   * Returns the score of the specified player color. Always returns 0 in this mock.
   *
   * @param playerColor the color of the player whose score is being retrieved
   * @return 0, as scores are not tracked in the mock
   */
  @Override
  public int getPlayerScore(PlayerColor playerColor) {
    return 0;
  }

  /**
   * Calculates the number of flips that would occur for placing a card in a given position.
   * Always returns 0 in this mock.
   *
   * @param row  the row of the board cell
   * @param col  the column of the board cell
   * @param card the card being placed
   * @return 0, as flips are not calculated in the mock
   */
  @Override
  public int calculateFlips(int row, int col, ICard card) {
    return 0;
  }

  /**
   * Mock implementation of {@code startGame} that does nothing.
   */
  @Override
  public void startGame() {
    // No action needed for startGame in the mock
  }

  /**
   * Returns the color of the player who is currently taking a turn. Always returns null
   * in this mock.
   *
   * @return null, as no player color is managed in the mock
   */
  @Override
  public PlayerColor getCurrentPlayerColor() {
    return null;
  }
}
