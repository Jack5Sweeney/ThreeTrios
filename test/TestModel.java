import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TestModel {

  private IModel simpleModel;
  private IModel model;
  private IModel modelForRulesTesting;

  private PlayerImpl redPlayer;
  private PlayerImpl bluePlayer;
  private PlayerImpl redPlayer1;
  private PlayerImpl bluePlayer1;
  private ArrayList<IPlayer> players;
  private ArrayList<IPlayer> players1;
  private Card redCard;
  private Card blueCard;
  private Card strongRedCard;
  private Card weakBlueCard;

  @Before
  public void setup() {
    redPlayer = new PlayerImpl(PlayerColor.RED, new ArrayList<>());
    bluePlayer = new PlayerImpl(PlayerColor.BLUE, new ArrayList<>());
    players = new ArrayList<>(List.of(redPlayer, bluePlayer));
    model = new ModelImpl("board.config", "card.database", players);
    simpleModel = new ModelImpl("simpleBoard.config", "simpleCard.database", players);

    // Initialize the model with a 3x3 board for simple testing
    redPlayer1 = new PlayerImpl(PlayerColor.RED, new ArrayList<>());
    bluePlayer1 = new PlayerImpl(PlayerColor.BLUE, new ArrayList<>());
    players1 = new ArrayList<>(List.of(redPlayer1, bluePlayer1));
    modelForRulesTesting = new ModelImpl("simpleBoard.config", "simpleCard.database", players1);

    // Initialize the board with a 3x3 grid for simplicity
    modelForRulesTesting.startGame();

    // Directly initialize cards with attack values for testing
    /*
    redCard = new Card(PlayerColor.RED, "RedCard",
            DirectionValue.NINE, DirectionValue.SIX, DirectionValue.THREE, DirectionValue.FOUR);

    blueCard = new Card(PlayerColor.BLUE, "BlueCard",
            DirectionValue.TWO, DirectionValue.SEVEN, DirectionValue.FIVE, DirectionValue.EIGHT);


     */
    strongRedCard = new Card(PlayerColor.RED, "StrongRedCard",
            DirectionValue.NINE, DirectionValue.NINE, DirectionValue.NINE, DirectionValue.NINE);

    weakBlueCard = new Card(PlayerColor.BLUE, "WeakBlueCard",
            DirectionValue.TWO, DirectionValue.TWO, DirectionValue.TWO, DirectionValue.TWO);

    redPlayer1.getHand().add(strongRedCard);
    bluePlayer1.getHand().add(weakBlueCard);
  }

  @Test
  public void testBattleWithoutFlipDueToLowerValue() {
    // Place a weak Blue card in the center and a strong Red card to the east
    modelForRulesTesting.placeCard(1, 1, 0,
             modelForRulesTesting.getBluePlayer());  // Weak Blue card in the center
    modelForRulesTesting.placeCard(1, 2, 0,
              modelForRulesTesting.getRedPlayer());   // Strong Red card to the east

    // Trigger the battle rules by updating the board with the weak Blue card
    modelForRulesTesting.updateBoard(modelForRulesTesting.getCardAt(
            1, 1), 1, 1);

    // The strong Red card should not flip as it has a higher attack value
    assertEquals(
            PlayerColor.RED, modelForRulesTesting.getCardAt(1, 2).getPlayer());
  }

  @Test
  public void testPlaceAndFlipAdjacentCard() {
    // Place a weaker blue card on the board at (1, 1)
    Card blueCard = new Card(PlayerColor.BLUE, "BlueCard",
            DirectionValue.TWO, DirectionValue.TWO, DirectionValue.TWO, DirectionValue.TWO);
    (  modelForRulesTesting.getBluePlayer()).getHand().add(blueCard);
    modelForRulesTesting.placeCard(1, 1, 0,   modelForRulesTesting.getBluePlayer());

    // Ensure the initial card is blue
    assertEquals(PlayerColor.BLUE, modelForRulesTesting.getCardAt(1, 1).getPlayer());

    // Place a stronger red card adjacent to the blue card at (1, 2)
    Card redCard = new Card(PlayerColor.RED, "RedCard",
            DirectionValue.FIVE, DirectionValue.FIVE, DirectionValue.FIVE, DirectionValue.FIVE);
    (  modelForRulesTesting.getRedPlayer()).getHand().add(redCard);
    modelForRulesTesting.placeCard(1, 2, 0,   modelForRulesTesting.getRedPlayer());

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
    modelForRulesTesting.placeCard(1, 1, 0,   modelForRulesTesting.getBluePlayer());

    // Place a weaker blue card on the board at (1, 0)
    Card blueCard1 = new Card(PlayerColor.BLUE, "BlueCard",
            DirectionValue.ONE, DirectionValue.ONE, DirectionValue.ONE, DirectionValue.ONE);
    (modelForRulesTesting.getBluePlayer()).getHand().add(blueCard);
    modelForRulesTesting.placeCard(1, 0, 0,   modelForRulesTesting.getBluePlayer());

    // Ensure the initial card is blue
    assertEquals(PlayerColor.BLUE, modelForRulesTesting.getCardAt(1, 1).getPlayer());
    assertEquals(PlayerColor.BLUE, modelForRulesTesting.getCardAt(1, 0).getPlayer());

    // Place a stronger red card adjacent to the blue card at (1, 2)
    Card redCard = new Card(PlayerColor.RED, "RedCard",
            DirectionValue.FIVE, DirectionValue.FIVE, DirectionValue.FIVE, DirectionValue.FIVE);
    (modelForRulesTesting.getRedPlayer()).getHand().add(redCard);
    modelForRulesTesting.placeCard(1, 2, 0,   modelForRulesTesting.getRedPlayer());

    // Verify that the blue card at (1, 1) has flipped to red
    assertEquals(PlayerColor.RED, modelForRulesTesting.getCardAt(1, 1).getPlayer());
    assertEquals(PlayerColor.RED, modelForRulesTesting.getCardAt(1, 0).getPlayer());
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
    simpleModel.placeCard(0,0,0, redPlayer);
    assertEquals("CorruptKing", simpleModel.getCardAt(0,0).getName());
  }

  @Test
  public void testPlacingCardInNonEmptySpot() {
    try {
      simpleModel.startGame();
      simpleModel.placeCard(0, 0, 0, redPlayer);
      simpleModel.placeCard(0, 0, 0, bluePlayer);
      fail("Placed card in a full spot");
    }
    catch (IllegalArgumentException e) {
      //Successfully caught IllegalArgumentException
    }
  }

  @Test
  public void testPlacingCardInHole() {
    try {
      model.startGame();
      model.placeCard(1, 1, 0, redPlayer);
      fail("Placed card in a spot hole");
    }
    catch (IllegalArgumentException e) {
      //Successfully caught IllegalArgumentException
    }
  }

  @Test
  public void testPlacingCardOutOfBoundsRowPoss() {
    try {
      simpleModel.startGame();
      simpleModel.placeCard(3,0,0, redPlayer);
      fail("Out of bounds row");
    }
    catch (IllegalArgumentException e) {
      //Successfully caught IllegalArgumentException
    }
  }

  @Test
  public void testPlacingCardOutOfBoundsRowNeg() {
    try {
      simpleModel.startGame();
      simpleModel.placeCard(0,-1,0, redPlayer);
      fail("Out of bounds row");
    }
    catch (IllegalArgumentException e) {
      //Successfully caught IllegalArgumentException
    }
  }

  @Test
  public void testPlacingCardOutOfBoundsColPoss() {
    try {
      simpleModel.startGame();
      simpleModel.placeCard(0,3,0, redPlayer);
      fail("Placed card in a spot hole");
    }
    catch (IllegalArgumentException e) {
      //Successfully caught IllegalArgumentException
    }
  }

  @Test
  public void testPlacingCardOutOfBoundsColNeg() {
    try {
      simpleModel.startGame();
      simpleModel.placeCard(0,-1,0, redPlayer);
      fail("Placed card in a spot hole");
    }
    catch (IllegalArgumentException e) {
      //Successfully caught IllegalArgumentException
    }
  }

  @Test
  public void testPlacingCardOutOfBoundsHandIndexPoss() {
    try {
      simpleModel.startGame();
      simpleModel.placeCard(0,0,5, redPlayer);
      fail("Placed card in a spot hole");
    }
    catch (IllegalArgumentException e) {
      //Successfully caught IllegalArgumentException
    }
  }

  @Test
  public void testPlacingCardOutOfBoundsHandIndexNeg() {
    try {
      simpleModel.startGame();
      simpleModel.placeCard(0,0,-1, redPlayer);
      fail("Placed card in a spot hole");
    }
    catch (IllegalArgumentException e) {
      //Successfully caught IllegalArgumentException
    }
  }

  @Test
  public void testPlacingCardNullPlayer() {
    try {
      simpleModel.startGame();
      simpleModel.placeCard(0,0,0, null);
      fail("Player cannot be null");
    }
    catch (IllegalArgumentException e) {
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

}

