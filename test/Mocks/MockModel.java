package Mocks;

import model.*;

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


  @Override
  public void placeCard(int row, int col, int cardIndexInHand, IPlayer player) {
    placeCardCalled = true;
    rowPlaced = row;
    colPlaced = col;
    cardIndexPlaced = cardIndexInHand;
    playerPlaced = player;
  }

  @Override
  public void updateCardToPlace(int index, PlayerColor color) {
    updateCardToPlaceCalled = true;
    cardIndexUpdated = index;
    colorUpdated = color;
  }

  @Override
  public IPlayer getPlayerToPlace() {
    return playerToReturn;
  }

  @Override
  public int getCardIndexToPlace() {
    return cardIndexToReturn;
  }

  @Override
  public CardImpl getCardAt(int boardRow, int boardCol) {
    return null;
  }

  @Override
  public IPlayer getRedPlayer() {
    return null;
  }

  @Override
  public IPlayer getBluePlayer() {
    return null;
  }

  @Override
  public CardImpl[][] getBoard() {
    return new CardImpl[0][];
  }

  @Override
  public CellType[][] getBoardAvailability() {
    return new CellType[0][];
  }

  @Override
  public boolean checkGameStarted() {
    return false;
  }

  @Override
  public boolean checkGameOver() {
    return false;
  }

  @Override
  public IPlayer getWinningPlayer() {
    return null;
  }

  @Override
  public int getPlayerScore(PlayerColor playerColor) {
    return 0;
  }

  @Override
  public int calculateFlips(int row, int col, ICard card) {
    return 0;
  }

  @Override
  public void startGame() {

  }
}
