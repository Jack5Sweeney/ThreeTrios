package provider.src.threetrios.controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import threetrios.model.Model;
import threetrios.strategies.Move;

/**
 * Controller class for three trios. This controller has the full functionality for a console game
 * and only bare functionality for a GUI based game. In the future** when this controller is fully
 * fleshed out in assignment 7 it should handle the views System.out.println's with possible pop up
 * windows.
 */
public class ThreeTriosController implements Controller {

  private final Model model;
  private final List<PlayerType> players;
  private final Appendable output;

  /**
   * Constructor for a three trios controller, will be fully implemented in coming assignments.
   * @param model is the model our controler will use.
   * @param appendable is the appendable our controler will use for text actions.
   */
  public ThreeTriosController(Model model, Appendable appendable) {
    if (model == null || appendable == null) {
      throw new IllegalArgumentException("Cannot instantiate controller with null parameters");
    }

    this.model = model;
    this.players = new ArrayList<PlayerType>();
    this.output = Objects.requireNonNull(appendable);
  }

  @Override
  public void addPlayer(PlayerType player) {
    this.players.add(Objects.requireNonNull(player));
  }

  @Override
  public void play() {
    int playerIndex = 0;
    while (!this.model.isGameOver()) {
      System.out.println("Is it reds turn: " + model.getTurn());

      Move temp = this.players.get(playerIndex).play(this.model);

      if (temp == null) {
        break;
      }

      System.out.println("temp row: " + temp.getRow() + " temp col: "
              + temp.getCol() + " temp card: " + temp.getCard());
      System.out.println("---------------------------------------------------");

      Move move = new Move(temp.getRow(), temp.getCol(), temp.getCard());

      this.model.placeCard(move.getRow(), move.getCol(), move.getCard());
      this.model.adjacentBattle(move.getCard(), move.getRow(), move.getCol(), true,
              0);
      this.model.changeTurn();

      System.out.println(model.getBoard());

      playerIndex = (playerIndex + 1) % this.players.size();
    }

    try {
      System.out.println("final game board:");
      System.out.println(model.getBoard());

      if (this.model.isGameOver())
        if (this.model.redPlayerWinsModel()) {
          output.append("Player red won");
        } else if (!this.model.redPlayerWinsModel()) {
          output.append("Player blue won");
        } else {
          output.append("Tie game");
        }
    } catch (IOException e) {
        // should never get here
    }
  }

  @Override
  public void addListener(ModelStatusListener oppController) {
    //
  }

  @Override
  public String readCardFile() throws FileNotFoundException {
    // if you want jar to work uncomment this line instead
     Scanner cardEnoughForConnecting = new Scanner(new FileReader("gameFiles/CardEnoughForAll"));

    // if you want to run through main use this line, if you want to use the jar
    // comment our this line and uncomment out the one above.
    //Scanner cardEnoughForConnecting = new
            //Scanner(new FileReader("src/threetrios/gameFiles/CardEnoughForAll"));

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
     Scanner boardConnecting = new Scanner(new FileReader("gameFiles/BoardConnecting"));

    // if you want to run through main use this line, if you want to use the jar
    // comment our this line and uncomment out the one above.
    //Scanner boardConnecting = new
            //Scanner(new FileReader("src/threetrios/gameFiles/BoardConnecting"));

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

}
