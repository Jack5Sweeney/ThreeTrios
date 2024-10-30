import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

public class TestModel {

  private IModel simpleModel;
  private IModel model;
  private IModel modelForRulesTesting;
  private IModel easyWinModel;

  private PlayerImpl redPlayer;
  private PlayerImpl bluePlayer;
  private PlayerImpl redPlayer1;
  private PlayerImpl bluePlayer1;
  private ArrayList<IPlayer> players;
  private ArrayList<IPlayer> players1;

  @Before
  public void setup() {
    redPlayer = new PlayerImpl(PlayerColor.RED, new ArrayList<>());
    bluePlayer = new PlayerImpl(PlayerColor.BLUE, new ArrayList<>());
    players = new ArrayList<>(List.of(redPlayer, bluePlayer));
    model = new ModelImpl("board.config", "card.database", players);
    simpleModel = new ModelImpl("simpleBoard.config", "simpleCard.database", players);
    easyWinModel = new ModelImpl("1x1Board.config", "simpleCard.database", players);

    redPlayer1 = new PlayerImpl(PlayerColor.RED, new ArrayList<>());
    bluePlayer1 = new PlayerImpl(PlayerColor.BLUE, new ArrayList<>());
    players1 = new ArrayList<>(List.of(redPlayer1, bluePlayer1));
    modelForRulesTesting = new ModelImpl("simpleBoard.config", "simpleCard.database", players1);

    modelForRulesTesting.startGame();

  }

  @Test
  public void testBattleWithoutFlipDueToLowerValue() {

    modelForRulesTesting.placeCard(1, 1, 0, modelForRulesTesting.getRedPlayer());
    modelForRulesTesting.placeCard(1, 2, 1, modelForRulesTesting.getBluePlayer());

    // The strong Red card should not flip as it has a higher attack value
    assertEquals(
        PlayerColor.RED, modelForRulesTesting.getCardAt(1, 1).getPlayer());
  }

  @Test
  public void testPlaceAndFlipAdjacentCard() {
    // Place a weaker blue card on the board at (1, 1)
    Card blueCard = new Card(PlayerColor.BLUE, "BlueCard",
        DirectionValue.TWO, DirectionValue.TWO, DirectionValue.TWO, DirectionValue.TWO);
    (modelForRulesTesting.getBluePlayer()).getHand().add(blueCard);
    modelForRulesTesting.placeCard(1, 1, 0, modelForRulesTesting.getBluePlayer());

    // Ensure the initial card is blue
    assertEquals(PlayerColor.BLUE, modelForRulesTesting.getCardAt(1, 1).getPlayer());

    // Place a stronger red card adjacent to the blue card at (1, 2)
    Card redCard = new Card(PlayerColor.RED, "RedCard",
        DirectionValue.FIVE, DirectionValue.FIVE, DirectionValue.FIVE, DirectionValue.FIVE);
    (modelForRulesTesting.getRedPlayer()).getHand().add(redCard);
    modelForRulesTesting.placeCard(1, 2, 0, modelForRulesTesting.getRedPlayer());

    // Verify that the blue card at (1, 1) has flipped to red
    assertEquals(PlayerColor.RED, modelForRulesTesting.getCardAt(1, 1).getPlayer());
  }

  // tests the propagation of the cards

  @Test
  public void testPlaceAndFlipAdjacentCardWithProp() {
    // Place a weaker blue card on the board at (1, 1)
    Card blueCard = new Card(PlayerColor.BLUE, "BlueCard",
        DirectionValue.TWO, DirectionValue.TWO, DirectionValue.TWO, DirectionValue.TWO);
    (modelForRulesTesting.getBluePlayer()).getHand().add(blueCard);
    modelForRulesTesting.placeCard(1, 1, 0, modelForRulesTesting.getBluePlayer());

    // Place a weaker blue card on the board at (1, 0)
    Card blueCard1 = new Card(PlayerColor.BLUE, "BlueCard",
        DirectionValue.ONE, DirectionValue.ONE, DirectionValue.ONE, DirectionValue.ONE);
    (modelForRulesTesting.getBluePlayer()).getHand().add(blueCard1);
    modelForRulesTesting.placeCard(1, 0, 0, modelForRulesTesting.getBluePlayer());

    // Ensure the initial card is blue
    assertEquals(PlayerColor.BLUE, modelForRulesTesting.getCardAt(1, 1).getPlayer());
    assertEquals(PlayerColor.BLUE, modelForRulesTesting.getCardAt(1, 0).getPlayer());

    // Place a stronger red card adjacent to the blue card at (1, 2)
    Card redCard = new Card(PlayerColor.RED, "RedCard",
        DirectionValue.FIVE, DirectionValue.FIVE, DirectionValue.FIVE, DirectionValue.FIVE);
    (modelForRulesTesting.getRedPlayer()).getHand().add(redCard);
    modelForRulesTesting.placeCard(1, 2, 0, modelForRulesTesting.getRedPlayer());

    // Verify that the blue card at (1, 1) has flipped to red
    assertEquals(PlayerColor.RED, modelForRulesTesting.getCardAt(1, 1).getPlayer());
    assertEquals(PlayerColor.RED, modelForRulesTesting.getCardAt(1, 0).getPlayer());
  }

  // this tests to see if the first card that flips will flip a card with greater value
  // it should not...

  @Test
  public void testPlaceAndFlipAdjacentCardWithNoPropAvailable() {
    // Place a weaker blue card on the board at (1, 1)
    Card blueCard = new Card(PlayerColor.BLUE, "BlueCard",
        DirectionValue.TWO, DirectionValue.TWO, DirectionValue.TWO, DirectionValue.TWO);
    (modelForRulesTesting.getBluePlayer()).getHand().add(blueCard);
    modelForRulesTesting.placeCard(1, 0, 0, modelForRulesTesting.getBluePlayer());

    // Place a weaker blue card on the board at (1, 0)
    Card blueCard1 = new Card(PlayerColor.BLUE, "BlueCard",
        DirectionValue.ONE, DirectionValue.ONE, DirectionValue.ONE, DirectionValue.ONE);
    (modelForRulesTesting.getBluePlayer()).getHand().add(blueCard1);
    modelForRulesTesting.placeCard(1, 1, 0, modelForRulesTesting.getBluePlayer());

    // Ensure the initial card is blue
    assertEquals(PlayerColor.BLUE, modelForRulesTesting.getCardAt(1, 1).getPlayer());
    assertEquals(PlayerColor.BLUE, modelForRulesTesting.getCardAt(1, 0).getPlayer());

    // Place a stronger red card adjacent to the blue card at (1, 2)
    Card redCard = new Card(PlayerColor.RED, "RedCard",
        DirectionValue.FIVE, DirectionValue.FIVE, DirectionValue.FIVE, DirectionValue.FIVE);
    (modelForRulesTesting.getRedPlayer()).getHand().add(redCard);
    modelForRulesTesting.placeCard(1, 2, 0, modelForRulesTesting.getRedPlayer());

    // Verify that the blue card at (1, 1) has flipped to red
    assertEquals(PlayerColor.RED, modelForRulesTesting.getCardAt(1, 1).getPlayer());
    assertEquals(PlayerColor.BLUE, modelForRulesTesting.getCardAt(1, 0).getPlayer());
  }

  // test to see if it propogates when there are two cards that are the same value after one is
  // flipped

  @Test
  public void testPlaceAndFlipAdjacentCardWithPropAvailableButTie() {
    // Place a weaker blue card on the board at (1, 1)
    Card blueCard = new Card(PlayerColor.BLUE, "BlueCard",
        DirectionValue.ONE, DirectionValue.ONE, DirectionValue.ONE, DirectionValue.ONE);
    (modelForRulesTesting.getBluePlayer()).getHand().add(blueCard);
    modelForRulesTesting.placeCard(1, 0, 0, modelForRulesTesting.getBluePlayer());

    // Place a weaker blue card on the board at (1, 0)
    Card blueCard1 = new Card(PlayerColor.BLUE, "BlueCard",
        DirectionValue.ONE, DirectionValue.ONE, DirectionValue.ONE, DirectionValue.ONE);
    (modelForRulesTesting.getBluePlayer()).getHand().add(blueCard1);
    modelForRulesTesting.placeCard(1, 1, 0, modelForRulesTesting.getBluePlayer());

    // Ensure the initial card is blue
    assertEquals(PlayerColor.BLUE, modelForRulesTesting.getCardAt(1, 1).getPlayer());
    assertEquals(PlayerColor.BLUE, modelForRulesTesting.getCardAt(1, 0).getPlayer());

    // Place a stronger red card adjacent to the blue card at (1, 2)
    Card redCard = new Card(PlayerColor.RED, "RedCard",
        DirectionValue.FIVE, DirectionValue.FIVE, DirectionValue.FIVE, DirectionValue.FIVE);
    (modelForRulesTesting.getRedPlayer()).getHand().add(redCard);
    modelForRulesTesting.placeCard(1, 2, 0, modelForRulesTesting.getRedPlayer());

    // Verify that the blue card at (1, 1) has flipped to red
    assertEquals(PlayerColor.RED, modelForRulesTesting.getCardAt(1, 1).getPlayer());
    assertEquals(PlayerColor.BLUE, modelForRulesTesting.getCardAt(1, 0).getPlayer());
  }

  @Test
  public void testPropegationOfMultipleCardsWithDifferentDirections() {
    modelForRulesTesting.placeCard(0, 0, 4, modelForRulesTesting.getRedPlayer());
    modelForRulesTesting.placeCard(1, 0, 2, modelForRulesTesting.getRedPlayer());
    modelForRulesTesting.placeCard(2, 0, 1, modelForRulesTesting.getRedPlayer());
    modelForRulesTesting.placeCard(2, 1, 1, modelForRulesTesting.getRedPlayer());
    modelForRulesTesting.placeCard(2, 2, 0, modelForRulesTesting.getBluePlayer());

    assertEquals(PlayerColor.BLUE, modelForRulesTesting.getCardAt(0, 0).getPlayer());
    assertEquals(PlayerColor.BLUE, modelForRulesTesting.getCardAt(1, 0).getPlayer());
    assertEquals(PlayerColor.BLUE, modelForRulesTesting.getCardAt(2, 0).getPlayer());
    assertEquals(PlayerColor.BLUE, modelForRulesTesting.getCardAt(2, 1).getPlayer());
    assertEquals(PlayerColor.BLUE, modelForRulesTesting.getCardAt(2, 2).getPlayer());

  }

  @Test
  public void testTryingToPropWithDiag() {
    modelForRulesTesting.placeCard(0, 0, 0, modelForRulesTesting.getRedPlayer());
    modelForRulesTesting.placeCard(1, 1, 0, modelForRulesTesting.getRedPlayer());
    modelForRulesTesting.placeCard(2, 2, 1, modelForRulesTesting.getBluePlayer());

    assertEquals(PlayerColor.RED, modelForRulesTesting.getCardAt(0, 0).getPlayer());
    assertEquals(PlayerColor.RED, modelForRulesTesting.getCardAt(1, 1).getPlayer());
    assertEquals(PlayerColor.BLUE, modelForRulesTesting.getCardAt(2, 2).getPlayer());

  }

  @Test
  public void testModelCatchesErrorWithInvalidFileName() {
    try {
      IModel invalidModelName = new ModelImpl("playBoard.config", "simpleCard.database", players);
      invalidModelName.startGame();
      fail("PlayBoard is not a valid name");
    } catch (IllegalArgumentException e) {
      //successfully caught IllegalArgumentException
    }
  }

  @Test
  public void testModelCatchesErrorWithInvalidFileFormat() {
    try {
      IModel invalidModelConfig = new ModelImpl("invalidBoard.config", "simpleCard.database", players);
      invalidModelConfig.startGame();
      fail("The board config is not a valid format");
    } catch (IllegalArgumentException e) {
      //successfully caught IllegalArgumentException
    }
  }

  @Test
  public void testModelCatchesErrorWithDuplicateCard() {
    try {
      IModel dupCardModelConfig = new ModelImpl("simpleBoard.config", "dupCard.database", players);
      dupCardModelConfig.startGame();
      fail("There is a duplicate card in the card database");
    } catch (IllegalArgumentException e) {
      //successfully caught IllegalArgumentException
    }
  }

  @Test
  public void testModelCatchesErrorWithNotEnoughCards() {
    try {
      IModel dupCardModelConfig = new ModelImpl("simpleBoard.config", "notEnoughCards.database", players);
      dupCardModelConfig.startGame();
      fail("There is no enough cards in the card database");
    } catch (IllegalArgumentException e) {
      //successfully caught IllegalArgumentException
    }
  }

  @Test
  public void testValidGameConstruction() {
    simpleModel.startGame();
  }

  @Test
  public void testPlacingCardInEmptySpot() {
    simpleModel.startGame();
    simpleModel.placeCard(0, 0, 0, redPlayer);
    assertEquals("CorruptKing", simpleModel.getCardAt(0, 0).getName());
  }

  @Test
  public void testPlacingCardInNonEmptySpot() {
    try {
      simpleModel.startGame();
      simpleModel.placeCard(0, 0, 0, redPlayer);
      simpleModel.placeCard(0, 0, 0, bluePlayer);
      fail("Placed card in a full spot");
    } catch (IllegalArgumentException e) {
      //Successfully caught IllegalArgumentException
    }
  }

  @Test
  public void testPlacingCardInHole() {
    try {
      model.startGame();
      model.placeCard(1, 1, 0, redPlayer);
      fail("Placed card in a spot hole");
    } catch (IllegalArgumentException e) {
      //Successfully caught IllegalArgumentException
    }
  }

  @Test
  public void testPlacingCardOutOfBoundsRowPoss() {
    try {
      simpleModel.startGame();
      simpleModel.placeCard(3, 0, 0, redPlayer);
      fail("Out of bounds row");
    } catch (IllegalArgumentException e) {
      //Successfully caught IllegalArgumentException
    }
  }

  @Test
  public void testPlacingCardOutOfBoundsRowNeg() {
    try {
      simpleModel.startGame();
      simpleModel.placeCard(0, -1, 0, redPlayer);
      fail("Out of bounds row");
    } catch (IllegalArgumentException e) {
      //Successfully caught IllegalArgumentException
    }
  }

  @Test
  public void testPlacingCardOutOfBoundsColPoss() {
    try {
      simpleModel.startGame();
      simpleModel.placeCard(0, 3, 0, redPlayer);
      fail("Placed card in a spot hole");
    } catch (IllegalArgumentException e) {
      //Successfully caught IllegalArgumentException
    }
  }

  @Test
  public void testPlacingCardOutOfBoundsColNeg() {
    try {
      simpleModel.startGame();
      simpleModel.placeCard(0, -1, 0, redPlayer);
      fail("Placed card in a spot hole");
    } catch (IllegalArgumentException e) {
      //Successfully caught IllegalArgumentException
    }
  }

  @Test
  public void testPlacingCardOutOfBoundsHandIndexPoss() {
    try {
      simpleModel.startGame();
      simpleModel.placeCard(0, 0, 5, redPlayer);
      fail("Placed card in a spot hole");
    } catch (IllegalArgumentException e) {
      //Successfully caught IllegalArgumentException
    }
  }

  @Test
  public void testPlacingCardOutOfBoundsHandIndexNeg() {
    try {
      simpleModel.startGame();
      simpleModel.placeCard(0, 0, -1, redPlayer);
      fail("Placed card in a spot hole");
    } catch (IllegalArgumentException e) {
      //Successfully caught IllegalArgumentException
    }
  }

  @Test
  public void testPlacingCardNullPlayer() {
    try {
      simpleModel.startGame();
      simpleModel.placeCard(0, 0, 0, null);
      fail("Player cannot be null");
    } catch (IllegalArgumentException e) {
      //Successfully caught IllegalArgumentException
    }
  }

  @Test
  public void testGetCardAtValidPositionWithCard() {
    simpleModel.startGame();
    simpleModel.placeCard(0, 0, 0, redPlayer);
    Card card = simpleModel.getCardAt(0, 0);

    Assert.assertNotNull(card);
    assertEquals("CorruptKing", card.getName());
  }

  @Test
  public void testGetCardAtInvalidNegRow() {
    try {
      simpleModel.startGame();
      simpleModel.placeCard(0, 0, 0, redPlayer);
      simpleModel.getCardAt(-1, 0);
      fail("Invalid card position");
    } catch (IllegalArgumentException e) {
      //Successfully Caught IllegalArgumentException
    }
  }

  @Test
  public void testGetCardAtInvalidPossRow() {
    try {
      simpleModel.startGame();
      simpleModel.placeCard(0, 0, 0, redPlayer);
      simpleModel.getCardAt(10, 0);
      fail("Invalid card position");
    } catch (IllegalArgumentException e) {
      //Successfully Caught IllegalArgumentException
    }
  }

  @Test
  public void testGetCardAtInvalidNegCol() {
    try {
      simpleModel.startGame();
      simpleModel.placeCard(0, 0, 0, redPlayer);
      simpleModel.getCardAt(0, -1);
      fail("Invalid card position");
    } catch (IllegalArgumentException e) {
      //Successfully Caught IllegalArgumentException
    }
  }

  @Test
  public void testGetCardAtInvalidPossCol() {
    try {
      simpleModel.startGame();
      simpleModel.placeCard(0, 0, 0, redPlayer);
      simpleModel.getCardAt(0, 10);
      fail("Invalid card position");
    } catch (IllegalArgumentException e) {
      //Successfully Caught IllegalArgumentException
    }
  }

  @Test
  public void testGetCardAtINullCard() {
    try {
      simpleModel.startGame();
      simpleModel.placeCard(0, 0, 0, redPlayer);
      simpleModel.getCardAt(0, 1);
      fail("Cannot retrieve a null card");
    } catch (IllegalArgumentException e) {
      //Successfully Caught IllegalArgumentException
    }
  }

  @Test
  public void testGameOverBeforeGameStarted() {
    try {
      simpleModel.checkGameStarted();
      fail("The game is not started yet");
    } catch (IllegalStateException e) {
      // Successfully caught IllegalStateException
    }
  }

  @Test
  public void getRedPlayerReturnsACopyOfRedPlayerCorrectCardName() {
    simpleModel.startGame();
    assertEquals("CorruptKing",
        simpleModel.getRedPlayer().getHand().get(0).getName());
  }

  @Test
  public void getRedPlayerMutateCardDoesNotMutatePlayer() {
    simpleModel.startGame();
    simpleModel.getRedPlayer().getHand().remove(0);
    assertEquals("CorruptKing",
        simpleModel.getRedPlayer().getHand().get(0).getName());
  }

  @Test
  public void getBluePlayerReturnsACopyOfBluePlayerCorrectCardName() {
    simpleModel.startGame();
    assertEquals("AngryDragon",
        simpleModel.getBluePlayer().getHand().get(0).getName());
  }

  @Test
  public void getBluePlayerMutateCardDoesNotMutatePlayer() {
    simpleModel.startGame();
    simpleModel.getBluePlayer().getHand().remove(0);
    assertEquals("AngryDragon",
        simpleModel.getBluePlayer().getHand().get(0).getName());
  }

  @Test
  public void testGetBoardReturnsDeepCopy() {
    simpleModel.startGame();

    simpleModel.placeCard(0, 0, 0, redPlayer);
    Card originalCard = simpleModel.getCardAt(0, 0);

    Card[][] boardCopy = simpleModel.getBoard();
    assertEquals(originalCard.getName(), boardCopy[0][0].getName());

    boardCopy[0][0] = new Card(PlayerColor.RED, "MutantCard", DirectionValue.ONE, DirectionValue.TWO, DirectionValue.THREE, DirectionValue.FOUR);

    assertEquals("CorruptKing", simpleModel.getCardAt(0, 0).getName());
    assertEquals(originalCard.getName(), simpleModel.getCardAt(0, 0).getName());
    assertNotEquals("MutantCard", simpleModel.getCardAt(0, 0).getName());
  }

  @Test
  public void testGetBoardAvailabilityReturnsDeepCopy() {
    simpleModel.startGame();

    CellType[][] availabilityCopy = simpleModel.getBoardAvailability();

    CellType originalCellType = simpleModel.getBoardAvailability()[0][0];
    assertEquals(originalCellType, availabilityCopy[0][0]);

    availabilityCopy[0][0] = CellType.HOLE;

    assertEquals(originalCellType, simpleModel.getBoardAvailability()[0][0]);
    assertNotEquals(CellType.HOLE, simpleModel.getBoardAvailability()[0][0]);
  }

  @Test(expected = IllegalStateException.class)
  public void testCheckGameStartedThrowsExceptionIfGameNotStarted() {
    simpleModel.checkGameStarted();
  }

  @Test
  public void testCheckGameStartedDoesNotThrowIfGameStarted() {
    simpleModel.startGame();
    try {
      simpleModel.checkGameStarted();
    } catch (IllegalStateException e) {
      fail("Exception should not have been thrown, as the game has started.");
    }
  }

  @Test(expected = IllegalStateException.class)
  public void testCheckGameOverThrowsExceptionIfGameOver() {
    easyWinModel.startGame();
    easyWinModel.placeCard(0, 0, 0, redPlayer);
    easyWinModel.checkGameOver();
  }

  @Test
  public void testCheckGameOverDoesNotThrowIfGameNotOver() {
    simpleModel.startGame();
    try {
      simpleModel.checkGameOver();
    } catch (IllegalStateException e) {
      fail("Exception should not have been thrown, as the game is not over.");
    }
  }

  @Test(expected = IllegalStateException.class)
  public void testGetWinningPlayerThrowsExceptionIfGameNotStarted() {
    simpleModel.getWinningPlayer();
  }

  @Test(expected = IllegalStateException.class)
  public void testGetWinningPlayerThrowsExceptionIfGameIsInTieWithNoCards() {
    simpleModel.startGame();
    simpleModel.getWinningPlayer();
  }

  @Test(expected = IllegalStateException.class)
  public void testGetWinningPlayerThrowsExceptionIfGameIsInTieWithCards() {
    simpleModel.startGame();
    simpleModel.placeCard(0, 0, 0, redPlayer);
    simpleModel.placeCard(2, 2, 0, bluePlayer);
    simpleModel.getWinningPlayer();
  }

  @Test
  public void testGetWinningPlayerReturnsCorrectWinningPlayer() {
    simpleModel.startGame();
    simpleModel.placeCard(0, 0, 0, redPlayer);
    simpleModel.placeCard(1, 1, 0, redPlayer);
    simpleModel.placeCard(2, 2, 0, bluePlayer);
    assertEquals(PlayerColor.RED, simpleModel.getWinningPlayer().getPlayerColor());
  }

  @Test
  public void testGetWinningPlayerReturnsCorrectWinningPlayerAfterProp() {
    simpleModel.startGame();
    simpleModel.placeCard(0, 0, 0, redPlayer);
    simpleModel.placeCard(0, 1, 0, redPlayer);
    simpleModel.placeCard(0, 2, 0, bluePlayer);
    assertEquals(PlayerColor.BLUE, simpleModel.getWinningPlayer().getPlayerColor());
  }
}

