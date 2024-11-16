package controllertesting;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Test class for the ControllerGUIImpl class.
 */
public class TestGUIController {

  /**private ControllerGUIImpl controller;
  private MockModel readOnlyMockModel;
  private MockModel model;
  private MockView viewMock;

  @Before
  public void setUp() {

    // Initialize mock model and view for testing
    readOnlyMockModel = new MockModel();
    viewMock = new MockView(readOnlyMockModel);
    model = new MockModel();
  }

  @Test
  public void testNullViewInConstructor() {
    try {
      controller = new ControllerGUIImpl(null, readOnlyMockModel);
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
      controller = new ControllerGUIImpl(viewMock, readOnlyMockModel);
      controller.playGame(null);
      fail("Model cannot be null");
    } catch (IllegalArgumentException e) {
      // Successfully caught IllegalArgumentException
    }
  }

  @Test
  public void testPlayGameInitializesCorrectly() {
    controller = new ControllerGUIImpl(viewMock, readOnlyMockModel);
    controller.playGame(model);
    assertTrue("Model's startGame method should be called", readOnlyMockModel.startGameCalled);
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
  **/
}
