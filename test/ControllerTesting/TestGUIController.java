package ControllerTesting;

import controller.*;
import gameConfiguration.ConfigGame;
import model.*;
import org.junit.Before;
import org.junit.Test;
import view.IViewFrameGUI;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Test class for the ControllerGUIImpl class.
 */
public class TestGUIController {

  private ControllerGUIImpl controller;
  private MockModel readOnlyModel;
  private MockModel model;
  private MockView viewMock;

  @Before
  public void setUp() {
    PlayerImpl redPlayer = new PlayerImpl(PlayerColor.RED, new ArrayList<>());
    PlayerImpl bluePlayer = new PlayerImpl(PlayerColor.BLUE, new ArrayList<>());
    ArrayList<IPlayer> players = new ArrayList<>(List.of(redPlayer, bluePlayer));

    ConfigGame gameConfigurator = new ConfigGame("board.config", "cards.database");

    // Initialize mock model and view for testing
    readOnlyModel = new MockModel();
    viewMock = new MockView();
    model = new MockModel();

    // Initialize controller with mock model and view
    controller = new ControllerGUIImpl(viewMock, readOnlyModel);
  }

  @Test
  public void testNullViewInConstructor() {
    try {
      controller = new ControllerGUIImpl(null, readOnlyModel);
      fail("Read-only-model cannot be null");
    } catch (IllegalArgumentException e) {
      // Successfully caught IllegalArgumentException
    }
  }

  @Test
  public void testNullReadOnlyModelInConstructor() {
    try {
      controller = new ControllerGUIImpl(viewMock, null);
      fail("Read-only-model cannot be null");
    } catch (IllegalArgumentException e) {
      // Successfully caught IllegalArgumentException
    }
  }

  @Test
  public void testNullModelInPlayGame() {
    try {
      controller.playGame(null);
      fail("Model cannot be null");
    } catch (IllegalArgumentException e) {
      // Successfully caught IllegalArgumentException
    }
  }

  @Test
  public void testPlayGameInitializesCorrectly() {
    controller.playGame(model);
    assertTrue("Model's startGame method should be called", readOnlyModel.startGameCalled);
    assertTrue("View's addFeatures method should be called", viewMock.addFeaturesCalled);
    assertTrue( "View's makeVisible method should be called", viewMock.makeVisibleCalled);
  }

  @Test
  public void testHandleCellClickUpdatesModelAndView() {
    int row = 1;
    int col = 1;
    model.cardIndexToReturn = 2; // Mock the card index to be returned by model
    model.playerToReturn = new MockPlayer(PlayerColor.RED); // Mock the player

    controller.handleCellClick(row, col);

    // Verify interactions with model and view
    assertEquals(row, model.rowPlaced);
    assertEquals(col, model.colPlaced);
    assertEquals(2, model.cardIndexPlaced);
    assertEquals(PlayerColor.RED, model.playerPlaced.getPlayerColor());
    assertTrue("View's updateBoard method should be called", viewMock.updateBoardCalled);
    assertTrue("View's updateHand method should be called", viewMock.updateHandCalled);
  }

  @Test
  public void testHandleCardClickUpdatesModelAndView() {
    int row = 0;
    PlayerColor color = PlayerColor.RED;

    controller.handleCardClick(row, color);

    assertEquals(row, viewMock.highlightedRow);
    assertEquals(color, viewMock.highlightedColor);
    assertEquals(row, model.cardIndexUpdated);
    assertEquals(color, model.colorUpdated);
  }
}
