package provider.src.threetrios.controller;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * Controller.Controller to be implemented in future assignment.
 */
public interface Controller {

  /**
   * Returns a string like, "NAME1 1 2 8 4\nNAME2 2 2 7 4\n..." which can be properly interpreted
   * by our models start game method.
   *
   * @return a string like, "NAME1 1 2 8 4\nNAME2 2 2 7 4\n..." which can be properly interpreted
   *         by our models start game method.
   * @throws FileNotFoundException if the file path you are looking for does not exist.
   */
  String readCardFile() throws FileNotFoundException;

  /**
   * Returns, in order, list.get(0) is the number of rows from the file, .get(1) is the number of
   * columns, and .get(2) is a string like "CCC" + "CCC" + "CCC" for a 3x3 game with all cardcells.
   *
   * @return in order, list.get(0) is the number of rows from the file, .get(1) is the number of
   *         columns, and .get(2) is a string like "CCC" + "CCC" + "CCC" for a 3x3 game with all
   *         cardcells.
   * @throws FileNotFoundException if the file path you are looking for does not exist.
   */
  List<Object> readBoardFile() throws FileNotFoundException;

  /**
   * A controller can add players to a game, this requires a player which means you add players
   * by giving them a strategy and giving them a team color.
   *
   * @param player holds the strategy and team color information.
   */
  public void addPlayer(PlayerType player);

  /**
   * Used to play and loop our game logic until a game is finished. Used ONLY for console games.
   * It also handles printing output to the console in that case.
   */
  public void play();

  public void addListener(ModelStatusListener oppController);
}
