import controller.ControllerGUIImpl;
import gameConfiguration.ConfigGame;
import model.*;
import view.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Run a Three Trios game interactively
 */
public class Main {
  /**
   * Run a Three Trios game interactively
   */
  public static void main(String[] args) {

    // New Hotness: Graphical User Interface:
    // 1. Create an instance of the model.
    // 2. Create an instance of the view.
    // 3. Create an instance of the controller, passing the view to its constructor.
    // 4. Call playGame() on the controller.

    PlayerImpl redPlayer = new PlayerImpl(PlayerColor.RED, new ArrayList<>());
    PlayerImpl bluePlayer = new PlayerImpl(PlayerColor.BLUE, new ArrayList<>());
    ArrayList<IPlayer> players = new ArrayList<>(List.of(redPlayer, bluePlayer));

    ConfigGame gameConfigurator = new ConfigGame("board.config", "card.database");
    ModelImpl model = new ModelImpl(gameConfigurator.getBoard(), gameConfigurator.getDeck(), players);
    ReadOnlyIModel readOnlyModel = new ModelImpl(gameConfigurator.getBoard(), gameConfigurator.getDeck(), players);

    ControllerGUIImpl controller = new ControllerGUIImpl(readOnlyModel);
    controller.playGame(model);
  }
}