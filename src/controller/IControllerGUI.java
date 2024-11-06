package controller;

import model.IModel;

/**
 * Interface for the GUI-based game controller. Defines the primary methods that
 * control the game flow and interactions between the model and the view.
 */
public interface IControllerGUI {

  /**
   * Initializes and starts the game. This includes configuring the board and deck,
   * setting up the model with necessary parameters, and preparing players for gameplay.
   *
   * @param model the game model that manages the game’s state and logic
   * @throws IllegalArgumentException if the model is null
   */
  void playGame(IModel model);
}
