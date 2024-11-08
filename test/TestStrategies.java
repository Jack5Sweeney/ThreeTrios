import gameConfiguration.ConfigGame;
import model.*;
import strategies.FlipTheMostStrategy;
import strategies.Placement;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ObjectInputFilter;
import java.util.ArrayList;
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
    private IModel model;
    private IModel modelForRulesTesting;
    private IModel easyWinModel;

    private PlayerImpl redPlayer;
    private PlayerImpl bluePlayer;
    private ArrayList<IPlayer> players;
    private FlipTheMostStrategy strategy1;

    @Before
    public void setup() {
      redPlayer = new PlayerImpl(PlayerColor.RED, new ArrayList<>());
      bluePlayer = new PlayerImpl(PlayerColor.BLUE, new ArrayList<>());
      players = new ArrayList<>(List.of(redPlayer, bluePlayer));

      ConfigGame gameConfig = new ConfigGame("board.config", "card.database");
      model = new ModelImpl(gameConfig.getBoard(), gameConfig.getDeck(), players);

      ConfigGame simpleGameConfig = new ConfigGame("simpleBoard.config", "simpleCard.database");
      simpleModel = new ModelImpl(simpleGameConfig.getBoard(), simpleGameConfig.getDeck(), players);

      // Initialize the FlipTheMostStrategy
      strategy1 = new FlipTheMostStrategy();
    }

    @Test
    public void testFlipTheMostStrategy0() {
      simpleModel.startGame();

      simpleModel.placeCard(2, 2, 0, simpleModel.getRedPlayer());
      simpleModel.placeCard(0, 0, 3, simpleModel.getBluePlayer());
      simpleModel.placeCard(1, 2, 0, simpleModel.getRedPlayer());
      simpleModel.placeCard(2, 0, 0, simpleModel.getBluePlayer());
      simpleModel.placeCard(0,2,0, simpleModel.getRedPlayer());
      simpleModel.placeCard(0, 1, 1, simpleModel.getBluePlayer());

      Placement bestMove = strategy1.chooseMove(simpleModel, redPlayer);

      int expectedRow = 1;
      int expectedColumn = 0;

      assertEquals(expectedRow, bestMove.row);
      assertEquals(expectedColumn, bestMove.column);
    }
  }
