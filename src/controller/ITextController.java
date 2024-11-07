package controller;

import model.IModel;
import model.IPlayer;
import java.util.ArrayList;

/**
 * The {@code IController} interface defines the operations for a game controller in the MVC
 * (Model-View-Controller) pattern. It outlines the method for starting the game, allowing
 * the controller to coordinate interactions between the model and view based on
 * specified configurations.
 */
public interface ITextController {

  /**
   * Sets up and starts the game by configuring the board and deck of cards based on
   * provided file paths, initializing the model, and preparing the players.
   *
   * @param model   the game model that manages the game's state and logic
   */
  void playGame(IModel model);
}
