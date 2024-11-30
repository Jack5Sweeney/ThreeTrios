package provider.src;

import java.io.FileNotFoundException;
import java.util.List;

import threetrios.controller.Controller;
import threetrios.controller.ModelStatusListener;
import threetrios.controller.PlayerImpl;
import threetrios.controller.ThreeTriosController;
import threetrios.controller.ThreeTriosGUIController;
import threetrios.model.Model;
import threetrios.model.TeamColor;
import threetrios.model.ThreeTriosModel;
import threetrios.strategies.FlipMaxImproved;
import threetrios.strategies.GoForTheCorners;
import threetrios.view.GUIView;
import threetrios.view.IGUIView;

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