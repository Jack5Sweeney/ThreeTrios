import cardcomparison.CardComparisonStrategy;
import cardcomparison.FallenAce;
import cardcomparison.Reverse;
import gameconfig.ConfigGame;
import card.CardImpl;
import card.CellTypeContents;
import card.DirectionValue;
import card.ICard;
import model.IModel;
import model.ModelVarientImpl;
import player.IPlayer;
import player.PlayerColor;
import player.PlayerImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertTrue;

/**
 * Test class to test the model.ModelImpl Implementation.
 **/
public class TestModel {

  private IModel simpleModel;
  private IModel newSimpleModel;
  private IModel model;
  private IModel modelForRulesTesting;
  private IModel easyWinModel;

  private PlayerImpl redPlayer;
  private PlayerImpl bluePlayer;
  private ArrayList<IPlayer> players;

  @Before
  public void setup() {
    redPlayer = new PlayerImpl(PlayerColor.RED, new ArrayList<>());
    bluePlayer = new PlayerImpl(PlayerColor.BLUE, new ArrayList<>());
    players = new ArrayList<>(List.of(redPlayer, bluePlayer));

    /*

    ConfigGame gameConfig = new ConfigGame("board.config", "card.database");
    model = new ModelVarientImpl(gameConfig.getBoard(), gameConfig.getDeck(), players);

    ConfigGame simpleGameConfig = new ConfigGame("simpleBoard.config", "simpleCard.database");
    simpleModel = new ModelVarientImpl(simpleGameConfig.getBoard(), simpleGameConfig.getDeck(), players);

    ConfigGame easyWinGameConfig = new ConfigGame("1x1Board.config", "simpleCard.database");
    easyWinModel = new ModelVarientImpl(easyWinGameConfig.getBoard(), easyWinGameConfig.getDeck(),
        players);

     */

    ConfigGame newSimpleGameConfig = new ConfigGame("board.config", "card.database");
    newSimpleModel = new ModelVarientImpl(newSimpleGameConfig.getBoard(), newSimpleGameConfig.getDeck(), players);


    PlayerImpl redPlayer1 = new PlayerImpl(PlayerColor.RED, new ArrayList<>());
    PlayerImpl bluePlayer1 = new PlayerImpl(PlayerColor.BLUE, new ArrayList<>());
    ArrayList<IPlayer> players1 = new ArrayList<>(List.of(redPlayer1, bluePlayer1));
    /*
    modelForRulesTesting = new ModelVarientImpl(simpleGameConfig.getBoard(), simpleGameConfig.getDeck(),
        players1);

     */


  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlaceCardOutOfBoundsColNeg() {
    simpleModel.startGame();
    simpleModel.placeCard(0, -1, 0, redPlayer);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlaceCardOutOfBoundsColPos() {
    simpleModel.startGame();
    simpleModel.placeCard(0, 10, 0, redPlayer);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlaceCardOutOfBoundsRowNeg() {
    simpleModel.startGame();
    simpleModel.placeCard(-1, 0, 0, redPlayer);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlaceCardOutOfBoundsRowPos() {
    simpleModel.startGame();
    simpleModel.placeCard(10, 0, 0, redPlayer);
  }

  @Test
  public void testBattleWithoutFlipDueToLowerValue() {
    modelForRulesTesting.startGame();

    modelForRulesTesting.placeCard(2, 2, 0,
            modelForRulesTesting.getRedPlayer());
    modelForRulesTesting.placeCard(1, 1, 0,
            modelForRulesTesting.getBluePlayer());
    modelForRulesTesting.placeCard(1, 2, 0,
            modelForRulesTesting.getRedPlayer());
    assertEquals(
            PlayerColor.RED, modelForRulesTesting.getCardAt(1, 2).getPlayerColor());
  }

  @Test
  public void testPlaceAndFlipAdjacentCard() {
    modelForRulesTesting.startGame();

    CardImpl redCard0 = new CardImpl(PlayerColor.RED, "RedCard",
            DirectionValue.FIVE, DirectionValue.FIVE, DirectionValue.FIVE, DirectionValue.FIVE);
    (modelForRulesTesting.getRedPlayer()).getHand().add(redCard0);
    modelForRulesTesting.placeCard(2, 2, 0,
        modelForRulesTesting.getRedPlayer());

    CardImpl blueCard = new CardImpl(PlayerColor.BLUE, "BlueCard",
            DirectionValue.TWO, DirectionValue.TWO, DirectionValue.TWO, DirectionValue.TWO);
    (modelForRulesTesting.getBluePlayer()).getHand().add(blueCard);
    modelForRulesTesting.placeCard(1, 1, 0,
        modelForRulesTesting.getBluePlayer());

    assertEquals(PlayerColor.BLUE, modelForRulesTesting.getCardAt(1, 1).getPlayerColor());

    CardImpl redCard = new CardImpl(PlayerColor.RED, "RedCard",
            DirectionValue.FIVE, DirectionValue.FIVE, DirectionValue.FIVE, DirectionValue.FIVE);
    (modelForRulesTesting.getRedPlayer()).getHand().add(redCard);
    modelForRulesTesting.placeCard(1, 2, 0,
        modelForRulesTesting.getRedPlayer());

    assertEquals(PlayerColor.RED, modelForRulesTesting.getCardAt(1, 1).getPlayerColor());
  }

  @Test
  public void testPlaceAndFlipAdjacentCardWithProp() {
    modelForRulesTesting.startGame();

    CardImpl redCard0 = new CardImpl(PlayerColor.RED, "RedCard",
            DirectionValue.FIVE, DirectionValue.FIVE, DirectionValue.FIVE, DirectionValue.FIVE);
    (modelForRulesTesting.getRedPlayer()).getHand().add(redCard0);
    modelForRulesTesting.placeCard(2, 2, 0,
        modelForRulesTesting.getRedPlayer());

    CardImpl blueCard = new CardImpl(PlayerColor.BLUE, "BlueCard",
            DirectionValue.TWO, DirectionValue.TWO, DirectionValue.TWO, DirectionValue.TWO);
    (modelForRulesTesting.getBluePlayer()).getHand().add(blueCard);
    modelForRulesTesting.placeCard(1, 1, 0,
        modelForRulesTesting.getBluePlayer());

    CardImpl redCard1 = new CardImpl(PlayerColor.RED, "RedCard",
            DirectionValue.FIVE, DirectionValue.FIVE, DirectionValue.FIVE, DirectionValue.FIVE);
    (modelForRulesTesting.getRedPlayer()).getHand().add(redCard1);
    modelForRulesTesting.placeCard(0, 2, 0,
        modelForRulesTesting.getRedPlayer());

    CardImpl blueCard1 = new CardImpl(PlayerColor.BLUE, "BlueCard",
            DirectionValue.ONE, DirectionValue.ONE, DirectionValue.ONE, DirectionValue.ONE);
    (modelForRulesTesting.getBluePlayer()).getHand().add(blueCard1);
    modelForRulesTesting.placeCard(1, 0, 0,
        modelForRulesTesting.getBluePlayer());

    assertEquals(PlayerColor.BLUE, modelForRulesTesting.getCardAt(1, 1).getPlayerColor());
    assertEquals(PlayerColor.BLUE, modelForRulesTesting.getCardAt(1, 0).getPlayerColor());

    CardImpl redCard2 = new CardImpl(PlayerColor.RED, "RedCard",
            DirectionValue.FIVE, DirectionValue.FIVE, DirectionValue.FIVE, DirectionValue.FIVE);
    (modelForRulesTesting.getRedPlayer()).getHand().add(redCard2);
    modelForRulesTesting.placeCard(1, 2, 0,
        modelForRulesTesting.getRedPlayer());

    assertEquals(PlayerColor.RED, modelForRulesTesting.getCardAt(1, 1).getPlayerColor());
    assertEquals(PlayerColor.RED, modelForRulesTesting.getCardAt(1, 0).getPlayerColor());
  }

  @Test
  public void testPlaceAndFlipAdjacentCardWithNoPropAvailable() {
    simpleModel.startGame();
    simpleModel.placeCard(2, 2, 0, redPlayer);
    simpleModel.placeCard(0, 0, 0, bluePlayer);
    simpleModel.placeCard(1, 2, 0, redPlayer);
    simpleModel.placeCard(0, 1, 0, bluePlayer);

    assertEquals(PlayerColor.BLUE, simpleModel.getCardAt(0, 0).getPlayerColor());
    assertEquals(PlayerColor.BLUE, simpleModel.getCardAt(0, 1).getPlayerColor());

    simpleModel.placeCard(0, 2, 0, redPlayer);
    assertEquals(PlayerColor.RED, simpleModel.getCardAt(0, 1).getPlayerColor());
    assertEquals(PlayerColor.BLUE, simpleModel.getCardAt(0, 0).getPlayerColor());

  }

  @Test
  public void testPlaceAndFlipAdjacentCardWithPropAvailableButTie() {
    simpleModel.startGame();

    simpleModel.placeCard(2, 2, 0, redPlayer);
    simpleModel.placeCard(0, 0, 1, bluePlayer);
    simpleModel.placeCard(1, 2, 0, redPlayer);
    simpleModel.placeCard(1, 0, 1, bluePlayer);
    simpleModel.placeCard(1, 1, 1, redPlayer);

    assertEquals(PlayerColor.BLUE, simpleModel.getCardAt(0, 0).getPlayerColor());
    assertEquals(PlayerColor.RED, simpleModel.getCardAt(1, 0).getPlayerColor());
  }

  @Test
  public void testPlaceCardDoesNotFlipSameColor() {
    simpleModel.startGame();

    simpleModel.placeCard(2, 2, 0, redPlayer);
    simpleModel.placeCard(0, 0, 1, bluePlayer);
    simpleModel.placeCard(2, 1, 1, redPlayer);
    simpleModel.placeCard(1, 0, 2, bluePlayer);

    assertEquals(PlayerColor.BLUE, simpleModel.getCardAt(0, 0).getPlayerColor());
  }

  @Test
  public void testPropegationOfMultipleCardsWithDifferentDirections() {
    modelForRulesTesting.startGame();

    modelForRulesTesting.placeCard(0, 0, 4,
        modelForRulesTesting.getRedPlayer());
    modelForRulesTesting.placeCard(0, 2, 1,
        modelForRulesTesting.getBluePlayer());
    modelForRulesTesting.placeCard(1, 0, 2,
        modelForRulesTesting.getRedPlayer());
    modelForRulesTesting.placeCard(1, 2, 1,
        modelForRulesTesting.getBluePlayer());
    modelForRulesTesting.placeCard(2, 0, 1,
        modelForRulesTesting.getRedPlayer());
    modelForRulesTesting.placeCard(1, 1, 1,
        modelForRulesTesting.getBluePlayer());
    modelForRulesTesting.placeCard(2, 1, 1,
        modelForRulesTesting.getRedPlayer());
    modelForRulesTesting.placeCard(2, 2, 0,
        modelForRulesTesting.getBluePlayer());

    assertEquals(PlayerColor.BLUE, modelForRulesTesting.getCardAt(0, 0).getPlayerColor());
    assertEquals(PlayerColor.BLUE, modelForRulesTesting.getCardAt(1, 0).getPlayerColor());
    assertEquals(PlayerColor.BLUE, modelForRulesTesting.getCardAt(2, 0).getPlayerColor());
    assertEquals(PlayerColor.BLUE, modelForRulesTesting.getCardAt(2, 1).getPlayerColor());
    assertEquals(PlayerColor.BLUE, modelForRulesTesting.getCardAt(2, 2).getPlayerColor());
  }

  @Test
  public void testTryingToPropWithDiag() {
    modelForRulesTesting.startGame();

    modelForRulesTesting.placeCard(0, 0, 0, modelForRulesTesting.getRedPlayer());
    modelForRulesTesting.placeCard(0, 2, 0, modelForRulesTesting.getBluePlayer());
    modelForRulesTesting.placeCard(1, 1, 0, modelForRulesTesting.getRedPlayer());
    modelForRulesTesting.placeCard(2, 2, 0, modelForRulesTesting.getBluePlayer());

    assertEquals(PlayerColor.RED, modelForRulesTesting.getCardAt(0, 0).getPlayerColor());
    assertEquals(PlayerColor.RED, modelForRulesTesting.getCardAt(1, 1).getPlayerColor());
    assertEquals(PlayerColor.BLUE, modelForRulesTesting.getCardAt(2, 2).getPlayerColor());

  }

  @Test
  public void testPlaceCardFlipsCardsAroundIt() {
    simpleModel.startGame();

    simpleModel.placeCard(2, 2, 0, redPlayer);
    simpleModel.placeCard(1, 2, 1, bluePlayer);
    simpleModel.placeCard(0, 0, 0, redPlayer);
    simpleModel.placeCard(1, 0, 1, bluePlayer);

    assertEquals(PlayerColor.BLUE, simpleModel.getCardAt(1, 2).getPlayerColor());
    assertEquals(PlayerColor.BLUE, simpleModel.getCardAt(1, 0).getPlayerColor());

    simpleModel.placeCard(1, 1, 2, redPlayer);

    assertEquals(PlayerColor.RED, simpleModel.getCardAt(1, 2).getPlayerColor());
    assertEquals(PlayerColor.RED, simpleModel.getCardAt(1, 0).getPlayerColor());
  }

  @Test
  public void testModelCatchesErrorWithDuplicateCard() {
    try {
      ConfigGame configGame = new ConfigGame("simpleBoard.config",
          "dupCard.database");
      IModel dupCardModelConfig = new ModelVarientImpl(configGame.getBoard(), configGame.getDeck(),
          players);
      dupCardModelConfig.startGame();
      fail("There is a duplicate card in the card database");
    } catch (IllegalArgumentException e) {
      //successfully caught IllegalArgumentException
    }
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
    CardImpl card = simpleModel.getCardAt(0, 0);

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
    CardImpl originalCard = simpleModel.getCardAt(0, 0);

    ICard[][] boardCopy = simpleModel.getBoard();
    assertEquals(originalCard.getName(), boardCopy[0][0].getName());

    boardCopy[0][0] = new CardImpl(PlayerColor.RED, "MutantCard", DirectionValue.ONE,
            DirectionValue.TWO, DirectionValue.THREE, DirectionValue.FOUR);

    assertEquals("CorruptKing", simpleModel.getCardAt(0, 0).getName());
    assertEquals(originalCard.getName(), simpleModel.getCardAt(0, 0).getName());
    assertNotEquals("MutantCard", simpleModel.getCardAt(0, 0).getName());
  }

  @Test
  public void testGetBoardAvailabilityReturnsDeepCopy() {
    simpleModel.startGame();

    CellTypeContents[][] availabilityCopy = simpleModel.getBoardAvailability();

    CellTypeContents originalCellType = simpleModel.getBoardAvailability()[0][0];
    assertEquals(originalCellType, availabilityCopy[0][0]);

    availabilityCopy[0][0] = CellTypeContents.HOLE;

    assertEquals(originalCellType, simpleModel.getBoardAvailability()[0][0]);
    assertNotEquals(CellTypeContents.HOLE, simpleModel.getBoardAvailability()[0][0]);
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

  @Test
  public void testCheckGameOverThrowsExceptionIfGameOver() {
    easyWinModel.startGame();
    easyWinModel.placeCard(0, 0, 0, redPlayer);
    assertTrue(easyWinModel.checkGameOver());
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

  @Test
  public void testCheckOverWhenGameIsOver() {
    easyWinModel.startGame();
    easyWinModel.placeCard(0, 0, 0, redPlayer);
    assertTrue(easyWinModel.checkGameOver());
  }

  @Test
  public void testGetPlayerColorReturnsCorrectPlayerColorRED() {
    simpleModel.startGame();
    assertEquals(PlayerColor.RED, simpleModel.getRedPlayer().getPlayerColor());
  }

  @Test
  public void testGetPlayerColorReturnsCorrectPlayerColorBLUE() {
    simpleModel.startGame();
    assertEquals(PlayerColor.BLUE, simpleModel.getBluePlayer().getPlayerColor());
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
    simpleModel.placeCard(0, 2, 1, bluePlayer);
    simpleModel.placeCard(1, 1, 0, redPlayer);
    simpleModel.placeCard(2, 2, 0, bluePlayer);
    simpleModel.placeCard(0, 1, 0, redPlayer);
    assertEquals(PlayerColor.RED, simpleModel.getWinningPlayer().getPlayerColor());
  }

  @Test
  public void testGetWinningPlayerReturnsCorrectWinningPlayerAfterProp() {
    simpleModel.startGame();
    simpleModel.placeCard(0, 0, 0, redPlayer);
    simpleModel.placeCard(2, 2, 1, bluePlayer);
    simpleModel.placeCard(0, 1, 0, redPlayer);
    simpleModel.placeCard(0, 2, 0, bluePlayer);
    assertEquals(PlayerColor.BLUE, simpleModel.getWinningPlayer().getPlayerColor());
  }

  @Test
  public void testGetPlayerScoreAtGameStart() {
    simpleModel.startGame();
    assertEquals(0, simpleModel.getPlayerScore(PlayerColor.RED));
    assertEquals(0, simpleModel.getPlayerScore(PlayerColor.BLUE));
  }

  @Test
  public void testGetPlayerScoreAfterPlacingCards() {
    simpleModel.startGame();
    simpleModel.placeCard(0, 0, 0, redPlayer);
    assertEquals(1, simpleModel.getPlayerScore(PlayerColor.RED));
    assertEquals(0, simpleModel.getPlayerScore(PlayerColor.BLUE));
    simpleModel.placeCard(1, 1, 0, bluePlayer);
    assertEquals(1, simpleModel.getPlayerScore(PlayerColor.RED));
    assertEquals(1, simpleModel.getPlayerScore(PlayerColor.BLUE));
  }

  @Test
  public void testGetPlayerScoreAfterFlippingCards() {
    modelForRulesTesting.startGame();

    CardImpl redCard0 = new CardImpl(PlayerColor.RED, "RedCard",
            DirectionValue.ONE, DirectionValue.ONE, DirectionValue.ONE, DirectionValue.ONE);
    modelForRulesTesting.getRedPlayer().getHand().add(redCard0);
    modelForRulesTesting.placeCard(0, 0, 0, modelForRulesTesting.getRedPlayer());

    CardImpl blueCard = new CardImpl(PlayerColor.BLUE, "BlueCard",
            DirectionValue.ONE, DirectionValue.ONE, DirectionValue.ONE, DirectionValue.ONE);
    modelForRulesTesting.getBluePlayer().getHand().add(blueCard);
    modelForRulesTesting.placeCard(1, 1, 0, modelForRulesTesting.getBluePlayer());

    CardImpl redCard = new CardImpl(PlayerColor.RED, "RedCard",
            DirectionValue.NINE, DirectionValue.NINE, DirectionValue.NINE, DirectionValue.NINE);
    modelForRulesTesting.getRedPlayer().getHand().add(redCard);
    modelForRulesTesting.placeCard(1, 2, 0, modelForRulesTesting.getRedPlayer());

    assertEquals(3, modelForRulesTesting.getPlayerScore(PlayerColor.RED));
    assertEquals(0, modelForRulesTesting.getPlayerScore(PlayerColor.BLUE));
  }

  @Test
  public void testCalculateFlipsNoAdjacentOpponentCards() {
    simpleModel.startGame();
    ICard redCard = redPlayer.getHand().get(0);

    int flips = simpleModel.calculateFlips(0, 0, redCard);
    assertEquals(0, flips);
  }

  @Test
  public void testCalculateFlipsWithOneAdjacentOpponentCard() {
    simpleModel.startGame();

    simpleModel.placeCard(0, 0, 0, redPlayer);
    ICard blueCard = bluePlayer.getHand().get(0);
    int flips = simpleModel.calculateFlips(0, 1, blueCard);
    assertEquals(1, flips);
  }

  @Test
  public void testCalculateFlipsWithTwoAdjacentOpponentCard() {
    simpleModel.startGame();

    simpleModel.placeCard(2, 2, 0, redPlayer);
    simpleModel.placeCard(1, 2, 1, bluePlayer);
    simpleModel.placeCard(0, 2, 1, redPlayer);
    simpleModel.placeCard(1, 0, 1, bluePlayer);

    assertEquals(PlayerColor.BLUE, simpleModel.getCardAt(1, 2).getPlayerColor());
    assertEquals(PlayerColor.BLUE, simpleModel.getCardAt(1, 0).getPlayerColor());

    ICard redCard = redPlayer.getHand().get(2);
    int flips = simpleModel.calculateFlips(1, 1, redCard);
    assertEquals(2, flips);
  }

  @Test
  public void testCalculateFlipsWithTwoOpponentCardProp() {
    simpleModel.startGame();

    simpleModel.placeCard(0, 2, 4, redPlayer);
    simpleModel.placeCard(1, 0, 1, bluePlayer);
    simpleModel.placeCard(2, 2, 3, redPlayer);
    simpleModel.placeCard(1, 1, 1, bluePlayer);

    ICard redCard = redPlayer.getHand().get(2);
    int flips = simpleModel.calculateFlips(1, 2, redCard);
    assertEquals(2, flips);
  }

  @Test
  public void testCurrentGetPlayerColor() {
    simpleModel.startGame();
    assertEquals(simpleModel.getCurrentPlayerColor(), PlayerColor.RED);
  }

  @Test
  public void testCurrentGetPlayerColorAfterSwitchedPlayerColor() {
    simpleModel.startGame();
    assertEquals(simpleModel.getCurrentPlayerColor(), PlayerColor.RED);
    simpleModel.placeCard(0, 0, 0, redPlayer);
    assertEquals(simpleModel.getCurrentPlayerColor(), PlayerColor.BLUE);
  }

  // tests for the new rules (strategies we created such as reverse and fallen ace)

  @Test
  public void testPropWithReverseRules() {
    newSimpleModel.startGame();
    newSimpleModel.setVariantRule(new Reverse());

    newSimpleModel.placeCard(0, 0, 0, redPlayer);
    newSimpleModel.placeCard(4, 0, 0, bluePlayer);
    newSimpleModel.placeCard(1, 0, 0, redPlayer);
    newSimpleModel.placeCard(2, 0, 1, bluePlayer);

    assertEquals(PlayerColor.BLUE, newSimpleModel.getCardAt(0, 0).getPlayerColor());
    assertEquals(PlayerColor.BLUE, newSimpleModel.getCardAt(1, 0).getPlayerColor());

  }


}




