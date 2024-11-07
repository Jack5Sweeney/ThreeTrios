package controller;

import gameConfiguration.ConfigGame;
import model.IModel;
import model.IPlayer;
import view.ITextView;

import java.util.ArrayList;

/**
 * The {@code ControllerImpl} class implements the {@link ITextController} interface, serving as the
 * controller in the MVC (Model-View-Controller) pattern for the game application.
 * It coordinates interactions between the model and the view, facilitating the setup and
 * starting of the game using specified configurations for the board and card deck.
 *
 * <p><strong>Usage:</strong></p>
 * <pre>
 *   ControllerImpl controller = new ControllerImpl();
 *   controller.playGame("boardConfig.txt", "cardDB.txt", model, players);
 * </pre>
 */
public class TextControllerImpl implements ITextController {

  private ITextView view;
  private IModel model;

  /**
   * Default constructor for {@code ControllerImpl}.
   * Initializes a controller instance without setting the model or view, which will be
   * configured during game setup.
   */
  public TextControllerImpl() { }

  /**
   * Starts the game by configuring the board and card deck based on specified file paths,
   * initializing the model with these configurations and the provided players.
   *
   * @param model   the game model used to represent the game's logic and state
   */
  @Override
  public void playGame(IModel model) {
    model.startGame();
  }
}
