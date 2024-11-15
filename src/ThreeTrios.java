import controller.ControllerGUIImpl;
import gameconfig.ConfigGame;
import model.ICard;
import model.CellType;
import model.PlayerColor;
import model.PlayerImpl;
import model.IPlayer;
import model.ModelImpl;
import model.ReadOnlyIModel;
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
    IPlayer redPlayer = new PlayerImpl(PlayerColor.RED, new ArrayList<>());
    IPlayer bluePlayer = new PlayerImpl(PlayerColor.BLUE, new ArrayList<>());

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