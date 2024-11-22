import card.CellType;
import card.ICard;
import controller.ControllerGUIImpl;
import gameconfig.ConfigGame;
import model.ModelImpl;
import player.IPlayer;
import player.PlayerColor;
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
    CellType[][] board = gameConfigurator.getBoard();
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
    IViewFrameGUI blueView = new ViewFrameGUIImpl(model);

    // Initialize controllers
    IControllerGUI redController = new ControllerGUIImpl(redView, model, redPlayer);
    IControllerGUI blueController = new ControllerGUIImpl(blueView, model, bluePlayer);

    redController.playGame();
    blueController.playGame();
  }
}