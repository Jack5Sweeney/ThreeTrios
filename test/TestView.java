import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

/**
 * Test class to test the ViewImpl Implementation.
 **/
public class TestView {
  private IModel model;
  private IModel simpleModel;
  private IModel plusModel;
  private IModel chessModel;
  private IModel oneByOneModel;
  private ViewImpl view;

  @Before
  public void setup() {
    IPlayer redPlayer = new PlayerImpl(PlayerColor.RED, new ArrayList<>());
    IPlayer bluePlayer = new PlayerImpl(PlayerColor.BLUE, new ArrayList<>());
    ArrayList<IPlayer> players = new ArrayList<>(List.of(redPlayer, bluePlayer));
    model = new ModelImpl("board.config", "card.database", players);
    simpleModel = new ModelImpl("simpleBoard.config", "simpleCard.database", players);
    plusModel = new ModelImpl("plusBoard.config", "card.database", players);
    chessModel = new ModelImpl("chessBoard.config", "card.database", players);
    oneByOneModel = new ModelImpl("1x1Board.config", "card.database", players);
  }

  @Test
  public void testRedPlayerView() {
    model.startGame();
    view = new ViewImpl(model);
    model.placeCard(0, 0, 0, model.getBluePlayer());
    model.placeCard(0, 1, 0, model.getBluePlayer());
    model.placeCard(1, 2, 0, model.getBluePlayer());
    model.placeCard(2, 3, 0, model.getRedPlayer());
    model.placeCard(4, 6, 0, model.getRedPlayer());
    String expectedOutput = "Player: RED\n"
        + "BB    _\n"
        + "_ B   _\n"
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
    view = new ViewImpl(model);

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

    view.switchPlayerView();

    String expectedOutputAfter = "Player: BLUE\n" +
        "__    _\n" +
        "_ _   _\n" +
        "_  _  _\n" +
        "_   _ _\n" +
        "_    __\n" +
        "Hand:\n" +
        "AngryDragon 2 8 9 9\n" +
        "HeroKnight A 2 4 4\n" +
        "SkyWhale 4 5 9 9\n" +
        "StormGiant 9 7 3 6\n" +
        "MysticPhoenix 5 6 7 A\n" +
        "ThunderWolf 8 6 3 5\n" +
        "SteelRaven 4 7 8 6\n" +
        "JJWKing 9 4 7 9\n";

    assertEquals(expectedOutputAfter, view.toString());
  }

  @Test
  public void testSimpleCardView() {
    simpleModel.startGame();
    view = new ViewImpl(simpleModel);

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
    view = new ViewImpl(simpleModel);
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

    simpleModel.placeCard(0, 0, 1, simpleModel.getRedPlayer());
    view.switchPlayerView();

    String expectedOutputAfter = "Player: BLUE\n" +
        "R__\n" +
        "___\n" +
        "___\n" +
        "Hand:\n" +
        "AngryDragon 2 8 9 9\n" +
        "HeroKnight A 2 4 4\n" +
        "SkyWhale 4 5 9 9\n" +
        "StormGiant 9 7 3 6\n" +
        "MysticPhoenix 5 6 7 A\n";

    assertEquals(expectedOutputAfter,  view.toString());
  }

  @Test
  public void testTextViewRespondsCardsAttacking() {
    simpleModel.startGame();
    view = new ViewImpl(simpleModel);
    simpleModel.placeCard(0, 0, 1, simpleModel.getRedPlayer());
    view.switchPlayerView();

    String expectedOutput = "Player: BLUE\n" +
        "BB_\n" +
        "___\n" +
        "___\n" +
        "Hand:\n" +
        "HeroKnight A 2 4 4\n" +
        "SkyWhale 4 5 9 9\n" +
        "StormGiant 9 7 3 6\n" +
        "MysticPhoenix 5 6 7 A\n";

    simpleModel.placeCard(0, 1, 0, simpleModel.getBluePlayer());

    assertEquals(expectedOutput,  view.toString());
  }

  @Test
  public void testTextViewRespondsCardsAttackingAndPropagation() {
    simpleModel.startGame();
    view = new ViewImpl(simpleModel);
    simpleModel.placeCard(0, 0, 1, simpleModel.getRedPlayer());
    view.switchPlayerView();
    simpleModel.placeCard(0, 1, 0, simpleModel.getBluePlayer());
    view.switchPlayerView();

    String expectedOutput = "Player: RED\n" +
        "RRR\n" +
        "___\n" +
        "___\n" +
        "Hand:\n" +
        "WorldDragon 8 3 5 7\n" +
        "FlameTiger 6 4 7 8\n" +
        "ShadowSerpent 3 9 6 8\n";
    simpleModel.placeCard(0, 2, 0, simpleModel.getRedPlayer());

    assertEquals(expectedOutput,  view.toString());
  }

  @Test
  public void testUniqueBoardConfig1ToString() {
    plusModel.startGame();
    view = new ViewImpl(plusModel);

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
    view = new ViewImpl(chessModel);

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
    view = new ViewImpl(oneByOneModel);

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