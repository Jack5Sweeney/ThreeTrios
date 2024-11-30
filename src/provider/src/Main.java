package provider.src;

import java.io.FileNotFoundException;
import java.util.List;

import provider.src.threetrios.controller.Controller;
import provider.src.threetrios.controller.ModelStatusListener;
import provider.src.threetrios.controller.PlayerImpl;
import provider.src.threetrios.controller.ThreeTriosController;
import provider.src.threetrios.controller.ThreeTriosGUIController;
import provider.src.threetrios.model.Model;
import provider.src.threetrios.model.TeamColor;
import provider.src.threetrios.model.ThreeTriosModel;
import provider.src.threetrios.strategies.FlipMaxImproved;
import provider.src.threetrios.strategies.GoForTheCorners;
import provider.src.threetrios.view.GUIView;
import provider.src.threetrios.view.IGUIView;

/**
 * Runner for a three trios game. Controller reads from a card file and board file.
 * In future will use full MVC.
 */
public class Main {
  /**
   * Run a Three Trios game interactively.
   */
  public static void main(String[] args) throws FileNotFoundException {

    // To run our program please read the last few lines of our readme, you must use the terminal
    // and our jar files ;)
    String redStrategy = args[0];
    String blueStrategy = args[1];

    Model model = new ThreeTriosModel();

    Controller controller = new ThreeTriosController(model, System.out);

    String readFileForCardString = controller.readCardFile();

    List<Object> readFileForRowsColsAndBoardString = controller.readBoardFile();

    model.startGame((int) readFileForRowsColsAndBoardString.get(0),
            (int) readFileForRowsColsAndBoardString.get(1),
            (String) readFileForRowsColsAndBoardString.get(2),
            readFileForCardString);

    IGUIView viewRed = new GUIView(model);
    IGUIView viewBlue = new GUIView(model);

    Controller playerRedController;
    Controller playerBlueController;

    switch (redStrategy) {
      case "goforcorners":
        playerRedController = new ThreeTriosGUIController(model, new PlayerImpl(TeamColor.RED,
                new GoForTheCorners()), viewRed);
        break;

      case "flipmax":
        playerRedController = new ThreeTriosGUIController(model, new PlayerImpl(TeamColor.RED,
                new FlipMaxImproved()), viewRed);
        break;

      default:
        playerRedController = new ThreeTriosGUIController(model, new PlayerImpl(TeamColor.RED,
                null), viewRed);
        break;
    }

    switch (blueStrategy) {
      case "goforcorners":
        playerBlueController = new ThreeTriosGUIController(model, new PlayerImpl(TeamColor.BLUE,
                new GoForTheCorners()), viewBlue);
        break;

      case "flipmax":
        playerBlueController = new ThreeTriosGUIController(model, new PlayerImpl(TeamColor.BLUE,
                new FlipMaxImproved()), viewBlue);
        break;

      default:
        playerBlueController = new ThreeTriosGUIController(model, new PlayerImpl(TeamColor.BLUE,
                null), viewBlue);
        break;
    }

    playerRedController.addListener((ModelStatusListener) playerBlueController);
    playerBlueController.addListener((ModelStatusListener) playerRedController);

    playerRedController.play();
  }
}