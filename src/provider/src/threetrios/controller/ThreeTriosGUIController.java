package provider.src.threetrios.controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import provider.src.threetrios.model.CellType;
import provider.src.threetrios.model.Model;
import provider.src.threetrios.model.TeamColor;
import provider.src.threetrios.strategies.Move;
import provider.src.threetrios.view.Cell;
import provider.src.threetrios.view.IGUIView;
import javax.swing.JOptionPane;

/**
 * Controller class implementation specifically for a gui version of the game of three trios.
 */
public class ThreeTriosGUIController implements Controller, PlayerActionsListener,
        ModelStatusListener {

  private final Model model;
  private final PlayerType player;
  private final IGUIView view;
  private List<ModelStatusListener> listeners;

  /**
   * A gui controller is made with a model, a player, and a view. The model is shared between
   * controllers.
   * @param model is the model the controller should play with.
   * @param player is the player using that model.
   * @param view is the view of this controller.
   */
  public ThreeTriosGUIController(Model model, PlayerType player, IGUIView view) {
    this.model = model;
    this.player = player;
    this.view = view;
    this.listeners = new ArrayList<>();

    view.addListener(this);

    model.addListener(this);

    this.view.setVisible();
    this.view.setColor(player.getColor());
  }

  @Override
  public void addPlayer(PlayerType player) {
    //
  }

  @Override
  public void play() {

    if (player.getStrat() != null) {
      // you have a strategy, so you are a computer

      Move move = player.play(model);

      onPlaceCard(move.getRow(), move.getCol(), new Cell(move.getRow(), move.getCol(),
              CellType.CARD, move.getCard(), model.getTurn(), player.getColor()));
    } else {
      JOptionPane.showMessageDialog(null, "Click a card to make your move...");
    }
  }

  @Override
  public void addListener(ModelStatusListener oppController) {

    this.listeners.add(oppController);
    this.view.refresh();

  }

  @Override
  public String readCardFile() throws FileNotFoundException {
    // if you want jar to work uncomment this line instead
    // Scanner cardEnoughForConnecting = new Scanner(new FileReader("gameFiles/CardEnoughForAll"));

    Scanner cardEnoughForConnecting = new
            Scanner(new FileReader("src/threetrios/gameFiles/CardEnoughForAll"));

    StringBuilder cardString = new StringBuilder();

    // for every line in a card file...
    while (cardEnoughForConnecting.hasNextLine()) {
      cardString.append(cardEnoughForConnecting.nextLine()).append("\n");
    }

    return cardString.toString();
  }

  @Override
  public List<Object> readBoardFile() throws FileNotFoundException {
    // if you want jar to work uncomment this line instead
    // Scanner boardConnecting = new Scanner(new FileReader("gameFiles/BoardConnecting"));

    Scanner boardConnecting = new
            Scanner(new FileReader("src/threetrios/gameFiles/BoardConnecting"));

    List<Object> rowsColsBoard = new ArrayList<>();

    int rows = boardConnecting.nextInt();
    int col = boardConnecting.nextInt();

    rowsColsBoard.add(rows);
    rowsColsBoard.add(col);

    StringBuilder holesAndCells = new StringBuilder();

    // for every line in a board file...
    while (boardConnecting.hasNextLine()) {
      holesAndCells.append(boardConnecting.nextLine().trim());
    }

    rowsColsBoard.add(holesAndCells.toString());

    return rowsColsBoard;
  }

  @Override
  public void onPlaceCard(int row, int col, Cell cell) {
    // handles removing card from data structure, and removing from hands

    model.placeCard(row, col, cell.getCard());
    model.adjacentBattle(cell.getCard(), row, col, true, 0);
    model.changeTurn();

    view.refresh();

    if (model.isGameOver()) {
      if (model.redPlayerWinsModel()) {
        JOptionPane.showMessageDialog(null, "Game Over: Red won with a" +
                "score of " + model.getPlayerScore(TeamColor.RED));
      } else {
        JOptionPane.showMessageDialog(null, "Game Over: Blue won with a" +
                "score of " + model.getPlayerScore(TeamColor.BLUE));
      }
    } else {
      ModelStatusListener opponentsController = listeners.get(0);

      opponentsController.refreshAndPlay();
    }
  }

  @Override
  public void refreshAndPlay() {
    System.out.println(player.getColor());
    view.refresh();

    play();
  }

}
