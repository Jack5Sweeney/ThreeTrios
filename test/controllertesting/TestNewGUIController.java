package controllertesting;

import card.CellTypeContents;
import gameconfig.ConfigGame;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import card.ICard;
import controller.ControllerGUIImpl;
import player.PlayerColor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Test class for the ControllerGUIImpl class.
 */

public class TestNewGUIController {


  private ControllerGUIImpl controller;
  private MockView viewMock;
  private MockModelForControllerGUI model;
  private MockPlayer mockPlayer;
  private MockView mockView;

  @Before
  public void setUp() {
    ICard[][] boardWithCards = new ICard[][]{};
    ConfigGame configGame = new ConfigGame("board.config", "card.database");
    // Initialize mock model and view for testing
    MockModelForControllerGUI readOnlyMockModel = new MockModelForControllerGUI(boardWithCards,
        configGame.getBoard());
    mockView = new MockView(readOnlyMockModel);
    model = new MockModelForControllerGUI(boardWithCards,
        configGame.getBoard());
    mockPlayer = new MockPlayer(PlayerColor.RED);
    MockPlayer mockPlayer2 = new MockPlayer(PlayerColor.BLUE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithNullView() {
    new ControllerGUIImpl(null, model, mockPlayer);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithNullModel() {
    new ControllerGUIImpl(mockView, null, mockPlayer);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithNullPlayer() {
    new ControllerGUIImpl(mockView, model, null);
  }

  @Test
  public void testControllerLogsCorrectCoordinates() {
    ICard[][] mockBoard = new ICard[3][3];
    CellTypeContents[][] mockAvailability = new CellTypeContents[3][3];
    MockModelForControllerGUI mockModel = new MockModelForControllerGUI(mockBoard,
        mockAvailability);
    // need to make a mock view
    // need to make mock players but we can do this by just creating players.

    ControllerGUIImpl controller = new ControllerGUIImpl(mockView, mockModel, mockPlayer);
    controller.handleCellClick(2, 3);

    // Verify coordinates were logged
    assertEquals(List.of(2, 3), mockModel.cordLog.get(0)); // Example: Controller accesses (1,1)
  }

  @Test
  public void testHandleCellClickNotPlayerTurn() {
    controller = new ControllerGUIImpl(viewMock, model, mockPlayer);
    controller.handleCellClick(1, 1);
    assertEquals("It's not your turn.", viewMock.lastErrorMessage);
  }

  @Test
  public void testHandleCellClickNoCardSelected() {
    controller = new ControllerGUIImpl(viewMock, model, mockPlayer);
    controller.handleCellClick(1, 1);
    assertEquals("Please select a card first.", viewMock.lastErrorMessage);
  }

  @Test
  public void testHandleCardClickInvalidPlayer() {
    controller = new ControllerGUIImpl(viewMock, model, mockPlayer);
    controller.handleCardClick(0, PlayerColor.BLUE);
    assertEquals("It's not your turn or invalid card selection.", viewMock.lastErrorMessage);
  }

  @Test
  public void testHighlightCardValidInput() {

    ControllerGUIImpl controller = new ControllerGUIImpl(mockView, model, mockPlayer);

    controller.handleCardClick(2, PlayerColor.RED);
    assertEquals(2, mockView.highlightedRow);
    assertEquals(PlayerColor.RED, mockView.highlightedColor);
  }

  @Test
  public void testHighlightCardInvalidPlayer() {

    // in all of these test cases model is the mockModel by the way*

    ControllerGUIImpl controller = new ControllerGUIImpl(mockView, model, mockPlayer);
    controller.handleCardClick(1, PlayerColor.BLUE);
    // Verify that highlightCard
    assertEquals(0, mockView.highlightedRow);
    assertNull(mockView.highlightedColor); // Should not update color for invalid player
    assertEquals("It's not your turn or invalid card selection.", mockView.lastErrorMessage);
  }

  @Test
  public void testOnTurnChangedOpponentTurn() {
    controller = new ControllerGUIImpl(mockView, model, mockPlayer);
    // Simulate turn change to opponent
    controller.onTurnChanged(PlayerColor.BLUE);
    assertFalse(viewMock.interactionsEnabled);
    assertEquals("Waiting for opponent...", viewMock.title);
  }

  @Test
  public void testOnTurnChangedPlayersTurn() {
    controller = new ControllerGUIImpl(mockView, model, mockPlayer);
    // Simulate turn change to the player
    controller.onTurnChanged(PlayerColor.RED);
    assertTrue(viewMock.interactionsEnabled);
    assertEquals("Your turn, RED", viewMock.title);
  }

  @Test
  public void testOnGameOverTie() {
    controller = new ControllerGUIImpl(mockView, model, mockPlayer);
    controller.onGameOver(null); // Simulate a tie
    assertEquals("The game is a tie!", viewMock.lastGameOverMessage);
    assertFalse(viewMock.interactionsEnabled); // Interactions should be disabled
  }

  @Test
  public void testOnGameOverWin() {
    controller = new ControllerGUIImpl(mockView, model, mockPlayer);
    controller.onGameOver(PlayerColor.RED); // Simulate player win
    assertEquals("You win!", viewMock.lastGameOverMessage);
    assertFalse(viewMock.interactionsEnabled);
  }

  @Test
  public void testOnGameOverLoss() {
    controller = new ControllerGUIImpl(mockView, model, mockPlayer);
    controller.onGameOver(PlayerColor.BLUE); // Simulate player loss
    assertEquals("You lose.", viewMock.lastGameOverMessage);
    assertFalse(viewMock.interactionsEnabled);
  }
}
