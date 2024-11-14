
import gameconfig.ConfigGame;
import model.IModel;
import model.IPlayer;
import model.PlayerColor;
import model.PlayerImpl;
import model.ModelImpl;
import org.junit.Before;
import org.junit.Test;
import view.TextViewImpl;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

/**
 * Test class to test the view.ViewImpl Implementation.
 **/
public class TestTextView {
  private IModel model;
  private IModel simpleModel;
  private IModel plusModel;
  private IModel chessModel;
  private IModel oneByOneModel;
  private TextViewImpl view;
  private IPlayer redPlayer;
  private IPlayer bluePlayer;


  @Before
  public void setup() {
    redPlayer = new PlayerImpl(PlayerColor.RED, new ArrayList<>());
    bluePlayer = new PlayerImpl(PlayerColor.BLUE, new ArrayList<>());
    ArrayList<IPlayer> players = new ArrayList<>(List.of(redPlayer, bluePlayer));

    ConfigGame gameConfig = new ConfigGame("board.config", "card.database");
    model = new ModelImpl(gameConfig.getBoard(), gameConfig.getDeck(), players);

    ConfigGame simpleGameConfig = new ConfigGame("simpleBoard.config", "simpleCard.database");
    simpleModel = new ModelImpl(simpleGameConfig.getBoard(), simpleGameConfig.getDeck(), players);

    ConfigGame plusGameConfig = new ConfigGame("plusBoard.config", "card.database");
    plusModel = new ModelImpl(plusGameConfig.getBoard(), gameConfig.getDeck(), players);

    ConfigGame chessGameConfig = new ConfigGame("chessBoard.config", "card.database");
    chessModel = new ModelImpl(chessGameConfig.getBoard(), gameConfig.getDeck(), players);

    ConfigGame oneByOneGameConfig = new ConfigGame("1x1Board.config", "card.database");
    oneByOneModel = new ModelImpl(oneByOneGameConfig.getBoard(), gameConfig.getDeck(), players);
  }

  @Test
  public void testRedPlayerView() {
    model.startGame();
    view = new TextViewImpl(model);
    model.placeCard(2, 3, 0, redPlayer);
    model.placeCard(0, 0, 0, bluePlayer);
    model.placeCard(4, 6, 0, redPlayer);
    model.placeCard(0, 1, 0, bluePlayer);

    String expectedOutput = "Player: RED\n"
        + "BB    _\n"
        + "_ _   _\n"
        + "_  R  _\n"
        + "_   _ _\n"
        + "_    _R\n"
        + "Hand:\n"
        + "WorldDragon 8 3 5 7\n" +
        "FlameTiger 6 4 7 8\n" +
        "ShadowSerpent 3 9 6 8\n" +
        "IceGolem 5 4 6 7\n" +
        "LunarEagle 7 9 5 2\n" +
        "EarthBear 6 3 7 9\n";

    assertEquals(expectedOutput, view.toString());
  }

  @Test
  public void testBluePlayerView() {
    model.startGame();
    view = new TextViewImpl(model);

    String expectedOutputBefore = "Player: RED\n" +
        "__    _\n" +
        "_ _   _\n" +
        "_  _  _\n" +
        "_   _ _\n" +
        "_    __\n" +
        "Hand:\n" +
        "CorruptKing 7 3 9 A\n" +
        "WindBird 7 2 5 3\n" +
        "WorldDragon 8 3 5 7\n" +
        "FlameTiger 6 4 7 8\n" +
        "ShadowSerpent 3 9 6 8\n" +
        "IceGolem 5 4 6 7\n" +
        "LunarEagle 7 9 5 2\n" +
        "EarthBear 6 3 7 9\n";
    assertEquals(expectedOutputBefore, view.toString());

    String expectedOutputAfter = "Player: RED\n" +
        "__    _\n" +
        "_ _   _\n" +
        "_  _  _\n" +
        "_   _ _\n" +
        "_    __\n" +
        "Hand:\n" +
        "CorruptKing 7 3 9 A\n" +
        "WindBird 7 2 5 3\n" +
        "WorldDragon 8 3 5 7\n" +
        "FlameTiger 6 4 7 8\n" +
        "ShadowSerpent 3 9 6 8\n" +
        "IceGolem 5 4 6 7\n" +
        "LunarEagle 7 9 5 2\n" +
        "EarthBear 6 3 7 9\n";

    assertEquals(expectedOutputAfter, view.toString());
  }

  @Test
  public void testSimpleCardView() {
    simpleModel.startGame();
    view = new TextViewImpl(simpleModel);

    String expectedOutput = "Player: RED\n" +
        "___\n" +
        "___\n" +
        "___\n" +
        "Hand:\n" +
        "CorruptKing 7 3 9 A\n" +
        "WindBird 7 2 5 3\n" +
        "WorldDragon 8 3 5 7\n" +
        "FlameTiger 6 4 7 8\n" +
        "ShadowSerpent 3 9 6 8\n";
    view.toString();
    assertEquals(expectedOutput,  view.toString());
  }

  @Test
  public void testTextViewRespondsToPlacingCard() {
    simpleModel.startGame();
    view = new TextViewImpl(simpleModel);
    String expectedOutputBefore = "Player: RED\n" +
        "___\n" +
        "___\n" +
        "___\n" +
        "Hand:\n" +
        "CorruptKing 7 3 9 A\n" +
        "WindBird 7 2 5 3\n" +
        "WorldDragon 8 3 5 7\n" +
        "FlameTiger 6 4 7 8\n" +
        "ShadowSerpent 3 9 6 8\n";
    assertEquals(expectedOutputBefore,  view.toString());

    simpleModel.placeCard(0, 0, 1, redPlayer);

    String expectedOutputAfter = "Player: RED\n" +
        "R__\n" +
        "___\n" +
        "___\n" +
        "Hand:\n" +
        "CorruptKing 7 3 9 A\n" +
        "WorldDragon 8 3 5 7\n" +
        "FlameTiger 6 4 7 8\n" +
        "ShadowSerpent 3 9 6 8\n";

    assertEquals(expectedOutputAfter,  view.toString());
  }

  @Test
  public void testTextViewRespondsCardsAttacking() {
    simpleModel.startGame();
    view = new TextViewImpl(simpleModel);
    simpleModel.placeCard(0, 0, 1, redPlayer);

    String expectedOutputRed = "Player: RED\n" +
        "R__\n" +
        "___\n" +
        "___\n" +
        "Hand:\n" +
        "CorruptKing 7 3 9 A\n" +
        "WorldDragon 8 3 5 7\n" +
        "FlameTiger 6 4 7 8\n" +
        "ShadowSerpent 3 9 6 8\n";

    assertEquals(expectedOutputRed, view.toString());

    simpleModel.placeCard(0, 1, 0, bluePlayer);

    String expectedOutputBlue = "Player: RED\n" +
        "BB_\n" +
        "___\n" +
        "___\n" +
        "Hand:\n" +
        "CorruptKing 7 3 9 A\n" +
        "WorldDragon 8 3 5 7\n" +
        "FlameTiger 6 4 7 8\n" +
        "ShadowSerpent 3 9 6 8\n";

    assertEquals(expectedOutputBlue,  view.toString());
  }

  @Test
  public void testTextViewRespondsCardsAttackingAndPropagation() {
    simpleModel.startGame();
    view = new TextViewImpl(simpleModel);
    simpleModel.placeCard(0, 0, 1, redPlayer);
    simpleModel.placeCard(0, 1, 0, bluePlayer);

    String expectedOutput = "Player: RED\n" +
        "RRR\n" +
        "___\n" +
        "___\n" +
        "Hand:\n" +
        "WorldDragon 8 3 5 7\n" +
        "FlameTiger 6 4 7 8\n" +
        "ShadowSerpent 3 9 6 8\n";
    simpleModel.placeCard(0, 2, 0, redPlayer);

    assertEquals(expectedOutput,  view.toString());
  }

  @Test
  public void testUniqueBoardConfig1ToString() {
    plusModel.startGame();
    view = new TextViewImpl(plusModel);

    String expectedOutput = "Player: RED\n" +
        " _ \n" +
        "___\n" +
        " _ \n" +
        "Hand:\n" +
        "CorruptKing 7 3 9 A\n" +
        "WindBird 7 2 5 3\n" +
        "WorldDragon 8 3 5 7\n" +
        "FlameTiger 6 4 7 8\n" +
        "ShadowSerpent 3 9 6 8\n" +
        "IceGolem 5 4 6 7\n" +
        "LunarEagle 7 9 5 2\n" +
        "EarthBear 6 3 7 9\n";

    assertEquals(expectedOutput, view.toString());
  }

  @Test
  public void testUniqueBoardConfig2ToString() {
    chessModel.startGame();
    view = new TextViewImpl(chessModel);

    String expectedOutput = "Player: RED\n" +
        "_ _ _\n" +
        " _ _ \n" +
        "_ _ _\n" +
        " _ _ \n" +
        "_ _ _\n" +
        "Hand:\n" +
        "CorruptKing 7 3 9 A\n" +
        "WindBird 7 2 5 3\n" +
        "WorldDragon 8 3 5 7\n" +
        "FlameTiger 6 4 7 8\n" +
        "ShadowSerpent 3 9 6 8\n" +
        "IceGolem 5 4 6 7\n" +
        "LunarEagle 7 9 5 2\n" +
        "EarthBear 6 3 7 9\n";

    assertEquals(expectedOutput, view.toString());
  }

  @Test
  public void testUniqueBoardConfig3ToString() {
    oneByOneModel.startGame();
    view = new TextViewImpl(oneByOneModel);

    String expectedOutput = "Player: RED\n" +
        "_\n" +
        "Hand:\n" +
        "CorruptKing 7 3 9 A\n" +
        "WindBird 7 2 5 3\n" +
        "WorldDragon 8 3 5 7\n" +
        "FlameTiger 6 4 7 8\n" +
        "ShadowSerpent 3 9 6 8\n" +
        "IceGolem 5 4 6 7\n" +
        "LunarEagle 7 9 5 2\n" +
        "EarthBear 6 3 7 9\n";

    assertEquals(expectedOutput, view.toString());
  }
}