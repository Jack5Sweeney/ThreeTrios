package ControllerTesting;

import controller.Features;
import model.ICard;
import model.IPlayer;
import model.PlayerColor;
import view.IViewFrameGUI;

public class MockView implements IViewFrameGUI {
  public boolean addFeaturesCalled = false;
  public boolean makeVisibleCalled = false;
  public boolean updateBoardCalled = false;
  public boolean updateHandCalled = false;
  public int highlightedRow;
  public PlayerColor highlightedColor;

  @Override
  public void addFeatures(Features features) {
    addFeaturesCalled = true;
  }

  @Override
  public void makeVisible() {
    makeVisibleCalled = true;
  }

  @Override
  public void highlightCard(int row, PlayerColor color) {
    highlightedRow = row;
    highlightedColor = color;
  }

  @Override
  public void updateBoard(ICard[][] boardWithCard) {
    updateBoardCalled = true;
  }

  @Override
  public void updateHand(int cardIndexToPlace, IPlayer playerPlacing) {
    updateHandCalled = true;
  }

  @Override
  public void refresh() {
    // No action needed for refresh in the mock
  }
}