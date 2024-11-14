package controllertesting;

import model.CellType;
import model.ICard;
import model.IModel;
import model.IPlayer;
import model.PlayerColor;
import model.PlayerImpl;
import model.CardImpl;

import java.util.ArrayList;

/**
 * A mock implementation of the {@link IModel} interface used for testing purposes.
 * It tracks method calls and parameters to verify interactions with the model during
 * tests.
 */
public class MockModel implements IModel {

  public boolean startGameCalled = false;
  public boolean placeCardCalled = false;
  public int rowPlaced;
  public int colPlaced;
  public int cardIndexPlaced;
  public IPlayer playerPlaced;
  public boolean updateCardToPlaceCalled = false;
  public int cardIndexToReturn;
  public IPlayer playerToReturn;
  public int cardIndexUpdated;
  public PlayerColor colorUpdated;

  /**
   * Mock implementation of {@code placeCard} that tracks the parameters used for the call.
   *
   * @param row            the row on the board where the card is placed
   * @param col            the column on the board where the card is placed
   * @param cardIndexInHand the index of the card in the player's hand
   * @param player         the player placing the card
   */
  @Override
  public void placeCard(int row, int col, int cardIndexInHand, IPlayer player) {
    placeCardCalled = true;
    rowPlaced = row;
    colPlaced = col;
    cardIndexPlaced = cardIndexInHand;
    playerPlaced = player;
  }

  /**
   * Mock implementation of {@code updateCardToPlace} that tracks the parameters used for the
   * call.
   *
   * @param index the index of the card in the player's hand to update
   * @param color the color of the card to be updated
   */
  @Override
  public void updateCardToPlace(int index, PlayerColor color) {
    updateCardToPlaceCalled = true;
    cardIndexUpdated = index;
    colorUpdated = color;
  }

  /**
   * Returns the player designated to place the next card, as set in tests.
   *
   * @return the player to place the next card
   */
  @Override
  public IPlayer getPlayerToPlace() {
    return playerToReturn;
  }

  /**
   * Returns the index of the card to place, as set in tests.
   *
   * @return the card index for the next placement
   */
  @Override
  public int getCardIndexToPlace() {
    return cardIndexToReturn;
  }

  /**
   * Retrieves the card at a specified board position. Always returns null in this mock.
   *
   * @param boardRow the row of the board cell
   * @param boardCol the column of the board cell
   * @return null, as this method is not implemented in the mock
   */
  @Override
  public CardImpl getCardAt(int boardRow, int boardCol) {
    return null;
  }

  /**
   * Retrieves the red player. Returns a new {@link PlayerImpl} with an empty hand.
   *
   * @return the red player
   */
  @Override
  public IPlayer getRedPlayer() {
    return new PlayerImpl(PlayerColor.RED, new ArrayList<ICard>());
  }

  /**
   * Retrieves the blue player. Returns a new {@link PlayerImpl} with an empty hand.
   *
   * @return the blue player
   */
  @Override
  public IPlayer getBluePlayer() {
    return new PlayerImpl(PlayerColor.BLUE, new ArrayList<ICard>());
  }

  /**
   * Returns a representation of the board, initialized as a 1x1 array.
   *
   * @return a 1x1 array of {@link ICard}, representing the board
   */
  @Override
  public ICard[][] getBoard() {
    return new CardImpl[1][1];
  }

  /**
   * Returns the availability status of each cell on the board.
   *
   * @return an empty array, as this is a mock implementation
   */
  @Override
  public CellType[][] getBoardAvailability() {
    return new CellType[0][0];
  }

  /**
   * Checks if the game has started. Always returns false in this mock.
   *
   * @return false, as the game is not started in the mock
   */
  @Override
  public boolean checkGameStarted() {
    return false;
  }

  /**
   * Checks if the game is over. Always returns false in this mock.
   *
   * @return false, as the game is not over in the mock
   */
  @Override
  public boolean checkGameOver() {
    return false;
  }

  /**
   * Retrieves the winning player. Always returns null in this mock.
   *
   * @return null, as there is no winning player in the mock
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
   * Mock implementation of {@code startGame}, which does nothing but can be tracked in tests.
   */
  @Override
  public void startGame() {
    startGameCalled = true;
  }

  /**
   * Returns the color of the player who is currently taking a turn. Always returns red in
   * this mock.
   *
   * @return {@link PlayerColor#RED}, as the current player color
   */
  @Override
  public PlayerColor getCurrentPlayerColor() {
    return PlayerColor.RED;
  }
}
