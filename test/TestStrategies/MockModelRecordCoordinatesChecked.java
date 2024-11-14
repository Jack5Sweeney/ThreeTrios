package TestStrategies;

import model.ICard;
import model.IModel;
import model.IPlayer;
import model.PlayerColor;
import model.CellType;
import model.CardImpl;

import java.util.ArrayList;
import java.util.List;


public class MockModelRecordCoordinatesChecked implements IModel {
   public ArrayList<ArrayList<Integer>> cordLog;
   private ICard[][] boardWithCards;
   private CellType[][] boardAvailability;

   public MockModelRecordCoordinatesChecked(ICard[][] boardWithCards,
                                            CellType[][] boardAvailability) {
    this.cordLog = new ArrayList<ArrayList<Integer>>();
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
    cordLog.add(new ArrayList<Integer>(List.of(boardRow, boardCol)));
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
    return 0;
  }

  @Override
  public void startGame() {

  }

  @Override
  public PlayerColor getCurrentPlayerColor() {
    return null;
  }
}
