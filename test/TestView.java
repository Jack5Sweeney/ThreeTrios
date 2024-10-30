import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;

public class TestView {
  private IModel model;
  private ViewImpl view;

  @Before
  public void setup() {
    // Create players and a predefined deck
    IPlayer redPlayer = new PlayerImpl(PlayerColor.RED, new ArrayList<>());
    IPlayer bluePlayer = new PlayerImpl(PlayerColor.BLUE, new ArrayList<>());

    ArrayList<Card> deck = new ArrayList<>(List.of(
        new Card(PlayerColor.RED, "R1", DirectionValue.TWO, DirectionValue.THREE, DirectionValue.FOUR, DirectionValue.FIVE),
        new Card(PlayerColor.BLUE, "B2", DirectionValue.ONE, DirectionValue.TWO, DirectionValue.THREE, DirectionValue.FOUR),
        new Card(PlayerColor.RED, "R3", DirectionValue.SIX, DirectionValue.SEVEN, DirectionValue.EIGHT, DirectionValue.NINE)
        // Add more cards as needed
    ));

    // Set up a simple board with predefined availability
    CellType[][] boardAvailability = {
        { CellType.EMPTY, CellType.HOLE, CellType.EMPTY },
        { CellType.EMPTY, CellType.EMPTY, CellType.EMPTY },
        { CellType.EMPTY, CellType.EMPTY, CellType.HOLE }
    };

    // Place initial cards on the board
    Card[][] boardWithCards = new Card[3][3];
    boardWithCards[0][0] = new Card(PlayerColor.RED, "R1", DirectionValue.TWO, DirectionValue.THREE, DirectionValue.FOUR, DirectionValue.FIVE);
    boardWithCards[1][1] = new Card(PlayerColor.BLUE, "B2", DirectionValue.ONE, DirectionValue.TWO, DirectionValue.THREE, DirectionValue.FOUR);

    // Initialize the model with the test constructor
    model = new ModelImpl(boardAvailability, boardWithCards, deck, redPlayer, bluePlayer);

    // Initialize the view with redPlayer as the current player
    view = new ViewImpl(model);
  }

  @Test
  public void testRedPlayerView() {
    String expectedOutput = "Player: RED\n"
        + "R _ _ \n"
        + "_ B _ \n"
        + "_ _ _ \n"
        + "Hand:\n"
        + "R3 6 7 8 9\n";

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
