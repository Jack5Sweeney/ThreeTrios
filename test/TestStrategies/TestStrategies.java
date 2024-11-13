package TestStrategies;

import gameConfiguration.ConfigGame;
import model.*;
import strategies.CornerStrategy;
import strategies.FlipTheMostStrategy;
import strategies.Placement;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertTrue;

  /**
   * Test class to test the model.ModelImpl Implementation.
   **/
  public class TestStrategies {

    private IModel simpleModel;
    private IModel stratModel;
    private IModel modelWithHole;

    private IModel model;
    private IModel modelForRulesTesting;
    private IModel easyWinModel;


    private PlayerImpl redPlayer;
    private PlayerImpl bluePlayer;
    private ArrayList<IPlayer> players;
    private FlipTheMostStrategy strategy1;
    private CornerStrategy strategy2;
    private CellType[][] board1;

    @Before
    public void setup() {
      redPlayer = new PlayerImpl(PlayerColor.RED, new ArrayList<>());
      bluePlayer = new PlayerImpl(PlayerColor.BLUE, new ArrayList<>());
      players = new ArrayList<>(List.of(redPlayer, bluePlayer));

      ConfigGame gameConfig = new ConfigGame("board.config", "card.database");
      model = new ModelImpl(gameConfig.getBoard(), gameConfig.getDeck(), players);

      ConfigGame simpleGameConfig = new ConfigGame("simpleBoard.config", "simpleCard.database");
      simpleModel = new ModelImpl(simpleGameConfig.getBoard(), simpleGameConfig.getDeck(), players);

      ConfigGame stratGameConfig = new ConfigGame("simpleBoard.config", "strategy.database");
      stratModel = new ModelImpl(stratGameConfig.getBoard(), stratGameConfig.getDeck(), players);

      ConfigGame simpleGameWithHole = new ConfigGame("strategyTestingWithHole.config", "simpleCard.database");
      modelWithHole = new ModelImpl(simpleGameWithHole.getBoard(), simpleGameWithHole.getDeck(), players);


      // boardAvailibity for testing

      this.board1 = simpleGameConfig.getBoard();

      // Initialize the FlipTheMostStrategy
      strategy1 = new FlipTheMostStrategy();
      strategy2 = new CornerStrategy();
    }

    @Test
    public void testFlipTheMostStrategy0() {
      simpleModel.startGame();

      simpleModel.placeCard(2, 2, 0, redPlayer);
      simpleModel.placeCard(0, 0, 3, bluePlayer);
      simpleModel.placeCard(1, 2, 0, redPlayer);
      simpleModel.placeCard(2, 0, 0, bluePlayer);
      simpleModel.placeCard(0,2,0, redPlayer);
      simpleModel.placeCard(0, 1, 1, bluePlayer);

      Placement bestMove = strategy1.chooseMove(simpleModel, redPlayer);

      int expectedRow = 1;
      int expectedColumn = 0;

      assertEquals(expectedRow, bestMove.row);
      assertEquals(expectedColumn, bestMove.column);


    }

    // this test is with a new database that has 1 card can flip the 0th red card

    @Test
    public void testFlipTheMostStrategy1() {
      stratModel.startGame();

      stratModel.placeCard(2, 2, 0, redPlayer);

      Placement bestMove = strategy1.chooseMove(stratModel, bluePlayer);

      assertEquals(1, bestMove.row);
      assertEquals(2, bestMove.column);

      // to get this test to pass change the strategy database SkyWhale 4 5 9 9, to 4 5 6 9
      // the first two tests will fail, because 6 will not flip the North 7, but then the
      // last two tests would pass, interesting because the hand should update after each
      // placement of a card, but it doesn't.

      // placing the 1 card that can flip the red into a bad location

      stratModel.placeCard(1, 0, 2, bluePlayer);
      stratModel.placeCard(0, 2, 3, redPlayer);

      assertEquals("ShadowSerpent", stratModel.getCardAt(0, 2).getName());

      Placement bestMove2 = strategy1.chooseMove(stratModel, bluePlayer);

      // now theres no good moves for a blue to flip the red because there is no strong enough
      // blue cards and it should place at 0 0, no card is strong enough to flip that red card

      assertEquals(0, bestMove2.row);
      assertEquals(0, bestMove2.column);
    }

    @Test
    public void testFlipTheMostStrategyCannotFlipValidCardsAlreadyPlaced() {
      simpleModel.startGame();

      simpleModel.placeCard(2, 2, 0, redPlayer);


      Placement bestMove = strategy1.chooseMove(simpleModel, bluePlayer);

      int expectedRow = 1;
      int expectedColumn = 2;

      assertEquals(expectedRow, bestMove.row);
      assertEquals(expectedColumn, bestMove.column);

    }

    @Test
    public void testFlipTheMostStrategyWithNoBestMoveGoesUpperLeft() {
      simpleModel.startGame();

      simpleModel.placeCard(2, 2, 0, redPlayer);
      simpleModel.placeCard(0, 2, 2, bluePlayer);

      Placement bestMove = strategy1.chooseMove(simpleModel, redPlayer);

      int expectedRow = 0;
      int expectedColumn = 0;

      assertEquals(expectedRow, bestMove.row);
      assertEquals(expectedColumn, bestMove.column);

      simpleModel.placeCard(0, 1, 3, redPlayer);

      Placement bestMove2 = strategy1.chooseMove(simpleModel, bluePlayer);

      assertEquals(1, bestMove2.row);
      assertEquals(1, bestMove2.column);

      simpleModel.placeCard(1, 1, 1, bluePlayer);

      assertEquals("HeroKnight", simpleModel.getCardAt(1, 1).getName());

    }


    @Test
    public void testCornerStrategy() {
      simpleModel.startGame();

      // Simulate some moves to set up the board state
      simpleModel.placeCard(2, 2, 0, redPlayer);
      simpleModel.placeCard(0, 0, 3, bluePlayer);
      simpleModel.placeCard(1, 2, 0, redPlayer);

      // Use CornerStrategy to determine the best move for redPlayer
      Placement bestMove = strategy2.chooseMove(simpleModel, bluePlayer);

      // Assertions to verify the best corner placement
      assertEquals(0, bestMove.row);
      assertEquals(2, bestMove.column);
      simpleModel.placeCard(0, 2, 0, bluePlayer);
      assertEquals("AngryDragon", simpleModel.getCardAt(0, 2).getName());
    }

    @Test
    public void testCornerStrategyWithAllCornersTakenUpperLeftMost() {
      simpleModel.startGame();

      // Simulate some moves to set up the board state
      simpleModel.placeCard(2, 2, 0, redPlayer);
      simpleModel.placeCard(0, 0, 3, bluePlayer);
      simpleModel.placeCard(2, 0, 0, redPlayer);
      simpleModel.placeCard(0, 2, 0, bluePlayer);

      // Use CornerStrategy to determine the best move for redPlayer
      Placement bestMove = strategy2.chooseMove(simpleModel, redPlayer);

      // Assertions to verify the best corner placement
      assertEquals(0, bestMove.row);
      assertEquals(1, bestMove.column);
    }

    @Test
    public void testCornerStrategyWithOneCornerTaken() {
      simpleModel.startGame();

      // Simulate some moves to set up the board state
      simpleModel.placeCard(2, 2, 0, redPlayer);

      // Use CornerStrategy to determine the best move for redPlayer
      Placement bestMove = strategy2.chooseMove(simpleModel, bluePlayer);

      // Assertions to verify the best corner placement
      assertEquals(0, bestMove.row);
      assertEquals(0, bestMove.column);
    }

    @Test
    public void testCornerStrategyWithNoCardsPlaced() {
      simpleModel.startGame();

      // Use CornerStrategy to determine the best move for redPlayer
      Placement bestMove = strategy2.chooseMove(simpleModel, redPlayer);

      // Assertions to verify the best corner placement
      assertEquals(0, bestMove.row);
      assertEquals(0, bestMove.column);
    }

    @Test
    public void testLoggingCallsToGetCardAtWithStrategyOne() {
      ConfigGame simpleGameConfig = new ConfigGame("simpleBoard.config", "simpleCard.database");
      ICard[][] boardWithCards = new ICard[3][3];
      boardWithCards[2][2] = simpleGameConfig.getDeck().get(0);
      MockModelRecordCoordinatesChecked mockModel = new MockModelRecordCoordinatesChecked(boardWithCards, this.board1);

      strategy1.chooseMove(mockModel, bluePlayer);

      ArrayList<ArrayList<Integer>> expected = new ArrayList<ArrayList<Integer>>();
      expected.add(new ArrayList<>(Arrays.asList(0, 0)));
      expected.add(new ArrayList<>(Arrays.asList(0, 1)));
      expected.add(new ArrayList<>(Arrays.asList(0, 2)));
      expected.add(new ArrayList<>(Arrays.asList(1, 0)));
      expected.add(new ArrayList<>(Arrays.asList(1, 1)));
      expected.add(new ArrayList<>(Arrays.asList(1, 2)));
      expected.add(new ArrayList<>(Arrays.asList(2, 0)));
      expected.add(new ArrayList<>(Arrays.asList(2, 1)));
      expected.add(new ArrayList<>(Arrays.asList(2, 2)));

      assertEquals(expected, mockModel.cordLog);

      MockModelLiesAboutCalcCardsFlipValue mockModel2 = new MockModelLiesAboutCalcCardsFlipValue(boardWithCards, board1);
    }

    @Test
    public void testLoggingCallsToGetCardAtWithStrategyTwoAfterPlacingCard() {
      ConfigGame simpleGameConfig = new ConfigGame("simpleBoard.config", "simpleCard.database");
      ICard[][] boardWithCards = new ICard[3][3];
      boardWithCards[0][0] = simpleGameConfig.getDeck().get(0);
      MockModelRecordCoordinatesChecked mockModel = new MockModelRecordCoordinatesChecked(boardWithCards, board1);

      strategy2.chooseMove(mockModel, bluePlayer);

      ArrayList<ArrayList<Integer>> expected = new ArrayList<ArrayList<Integer>>();

      expected.add(new ArrayList<>(Arrays.asList(0, 0)));
      expected.add(new ArrayList<>(Arrays.asList(0, 2)));
      expected.add(new ArrayList<>(Arrays.asList(2, 0)));
      expected.add(new ArrayList<>(Arrays.asList(2, 2)));
      expected.add(new ArrayList<>(Arrays.asList(0, 0)));
      expected.add(new ArrayList<>(Arrays.asList(0, 1)));
      expected.add(new ArrayList<>(Arrays.asList(0, 2)));
      expected.add(new ArrayList<>(Arrays.asList(1, 0)));
      expected.add(new ArrayList<>(Arrays.asList(1, 1)));
      expected.add(new ArrayList<>(Arrays.asList(1, 2)));
      expected.add(new ArrayList<>(Arrays.asList(2, 0)));
      expected.add(new ArrayList<>(Arrays.asList(2, 1)));
      expected.add(new ArrayList<>(Arrays.asList(2, 2)));

      assertEquals(expected, mockModel.cordLog);
    }

    @Test
    public void testLyingAboutValueReturnsExpectedCoordinates() {
      ConfigGame simpleGameConfig = new ConfigGame("simpleBoard.config", "simpleCard.database");
      ICard[][] boardWithCards = new CardImpl[3][3];
      boardWithCards[0][0] = simpleGameConfig.getDeck().get(0);
      MockModelLiesAboutCalcCardsFlipValue mockModel = new MockModelLiesAboutCalcCardsFlipValue(boardWithCards, board1);

      assertEquals(0, strategy1.chooseMove(mockModel, redPlayer).row);
      assertEquals(1, strategy1.chooseMove(mockModel, redPlayer).column);
    }

    @Test
    public void testCornerStrategyWhenThereIsAHole() {
      modelWithHole.startGame();
      Placement bestMove = strategy2.chooseMove(modelWithHole, redPlayer);

      assertEquals(0, bestMove.row);
      assertEquals(2, bestMove.column);
    }

    @Test
    public void testCornerStrategyWhenThereIsAHoleAndNoOpenCorners() {
      modelWithHole.startGame();

      modelWithHole.placeCard(0, 2, 0, redPlayer);
      modelWithHole.placeCard(2, 2, 0, bluePlayer);
      modelWithHole.placeCard(2, 0, 0, redPlayer);
      Placement bestMove = strategy2.chooseMove(modelWithHole, bluePlayer);


      assertEquals(0, bestMove.row);
      assertEquals(1, bestMove.column);
    }

  }
