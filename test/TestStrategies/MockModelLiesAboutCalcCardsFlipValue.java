package TestStrategies;

import model.*;

import java.util.ArrayList;

public class MockModelLiesAboutCalcCardsFlipValue implements IModel {
  private ICard[][] boardWithCards;
  private CellType[][] boardAvailability;

  public MockModelLiesAboutCalcCardsFlipValue(ICard[][] boardWithCards, CellType[][] boardAvailability) {
    this.boardWithCards = boardWithCards;
    this.boardAvailability = boardAvailability;
  }

  @Override
  public void placeCard(int boardRow, int boardCol, int cardIndexInHand, IPlayer player) {

  }

  @Override
  public void updateCardToPlace(int index, PlayerColor color) {

  }

  @Override
  public IPlayer getPlayerToPlace() {
    return null;
  }

  @Override
  public int getCardIndexToPlace() {
    return 0;
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
  public ICard[][] getBoard() {
    return this.boardWithCards;
  }

  @Override
  public CellType[][] getBoardAvailability() {
    return this.boardAvailability;
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
    if (row == 0 && col == 1) {
      return 100;
    }
    else return 0;
  }

  @Override
  public void startGame() {

  }

  @Override
  public PlayerColor getCurrentPlayerColor() {
    return null;
  }
}
