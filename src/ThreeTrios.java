import adapter.IGUIViewToIViewFrameGUIAdapter;
import adapter.ModelToReadOnlyThreeTriosModelAdapter;
import card.CellTypeContents;
import card.ICard;
import controller.ControllerGUIImpl;
import gameconfig.ConfigGame;
import model.ModelImpl;
import player.IPlayer;
import player.PlayerColor;
import provider.src.threetrios.view.GUIView;
import provider.src.threetrios.view.IGUIView;
import view.IViewFrameGUI;
import view.ViewFrameGUIImpl;
import controller.IControllerGUI;

import java.util.ArrayList;
import java.util.List;

/**
 * Run a Three Trios game interactively.
 */
public class ThreeTrios {
  /**
   * Run a Three Trios game interactively.
   */
  public static void main(String[] args) {
    ConfigGame gameConfigurator = new ConfigGame("board.config", "card.database");
    CellTypeContents[][] board = gameConfigurator.getBoard();
    ArrayList<ICard> deck = gameConfigurator.getDeck();
    PlayerFactory playerFactory = new PlayerFactory();

    if (args.length != 2) {
      throw new IllegalArgumentException("Player options: 'human', 'strategy1', 'strategy2'");
    }

    IPlayer redPlayer = playerFactory.createPlayer(args[0], PlayerColor.RED);
    IPlayer bluePlayer = playerFactory.createPlayer(args[1], PlayerColor.BLUE);

    ArrayList<IPlayer> players = new ArrayList<>(List.of(redPlayer, bluePlayer));

    ModelImpl model = new ModelImpl(board, deck, players);

    // Initialize views
    IViewFrameGUI redView = new ViewFrameGUIImpl(model);
    IGUIView blueView = new GUIView(new ModelToReadOnlyThreeTriosModelAdapter(model));

    // Initialize controllers
    IControllerGUI redController = new ControllerGUIImpl(redView, model, redPlayer);
    IControllerGUI blueController = new ControllerGUIImpl(
        new IGUIViewToIViewFrameGUIAdapter(blueView, model, bluePlayer), model, bluePlayer);

    redController.playGame();
    blueController.playGame();
  }
}