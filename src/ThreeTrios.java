import card.CellTypeContents;
import card.ICard;
import controller.ControllerGUIImpl;
import gameconfig.ConfigGame;
import gameconsole.GameConsole;
import model.ModelVarientImpl;
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
    CellTypeContents[][] board = gameConfigurator.getBoard();
    ArrayList<ICard> deck = gameConfigurator.getDeck();
    PlayerFactory playerFactory = new PlayerFactory();
    GameConsole gameConsole = new GameConsole();

    if (args.length != 2) {
      throw new IllegalArgumentException("Player options: 'human', 'strategy1', 'strategy2'");
    }

    IPlayer redPlayer = playerFactory.createPlayer(args[0], PlayerColor.RED);
    IPlayer bluePlayer = playerFactory.createPlayer(args[1], PlayerColor.BLUE);

    ArrayList<IPlayer> players = new ArrayList<>(List.of(redPlayer, bluePlayer));

    ModelVarientImpl model = new ModelVarientImpl(board, deck, players);

    // Initialize views
    IViewFrameGUI redView = new ViewFrameGUIImpl(model);
    IViewFrameGUI blueView = new ViewFrameGUIImpl(model);

    // Initialize controllers
    IControllerGUI redController = new ControllerGUIImpl(redView, model, redPlayer, gameConsole);
    IControllerGUI blueController = new ControllerGUIImpl(blueView, model, bluePlayer, gameConsole);

    redController.playGame();
    blueController.playGame();
    gameConsole.runGame();
  }
}