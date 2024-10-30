import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;

public class TestView {
  private IModel model;
  private ViewImpl view;
  private ArrayList<IPlayer> players;

  @Before
  public void setup() {
    // Create players and a predefined deck
    IPlayer redPlayer = new PlayerImpl(PlayerColor.RED, new ArrayList<>());
    IPlayer bluePlayer = new PlayerImpl(PlayerColor.BLUE, new ArrayList<>());
    players = new ArrayList<>(List.of(redPlayer, bluePlayer));
    // Initialize the model with the files given
    model = new ModelImpl("board.config", "card.database", players);
    model.startGame();
    view = new ViewImpl(model);
  }

  @Test
  public void testRedPlayerView() {
    model.placeCard(0, 0, 0, model.getBluePlayer());
    model.placeCard(0, 1, 0, model.getBluePlayer());
    model.placeCard(1, 2, 0, model.getBluePlayer());
    model.placeCard(2, 3, 0, model.getRedPlayer());
    model.placeCard(4, 6, 0, model.getRedPlayer());
    String expectedOutput = "Player: BLUE\n"
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
    // Switch the view to the blue player
    view.switchPlayerView();
    String expectedOutput = "Player: BLUE\n"
        + "R _ _ \n"
        + "_ B _ \n"
        + "_ _ _ \n"
        + "Hand:\n"
        + "B2 1 2 3 4\n";

    assertEquals(expectedOutput, view.toString());
  }


}
