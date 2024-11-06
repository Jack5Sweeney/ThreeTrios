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
   * @param board   the file name of the board configuration file
   * @param cardDB  the file name of the card database file
   * @param model   the game model used to represent the game's logic and state
   * @param players the list of players participating in the game
   */
  @Override
  public void playGame(String board, String cardDB, IModel model, ArrayList<IPlayer> players) {
    ConfigGame gameConfiguration = new ConfigGame(board, cardDB);
    model.startGame();
  }
}
