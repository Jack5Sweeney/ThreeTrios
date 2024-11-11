package controller;

import model.IModel;
import model.IPlayer;
import model.PlayerColor;
import model.ReadOnlyIModel;
import view.IViewFrameGUI;
import view.ViewFrameGUIImpl;

/**
 * Implementation of the {@link IControllerGUI} interface, responsible for managing the game
 * logic and interactions between the model and the GUI view. It also implements the {@link Features}
 * interface to handle user interactions within the GUI.
 */
public class ControllerGUIImpl implements IControllerGUI, Features {

  private IViewFrameGUI view;
  private ReadOnlyIModel readOnlyModel;
  private IModel model;

  /**
   * Default constructor for {@code ControllerGUIImpl}.
   * Initializes an instance without setting the model or view, which are configured later
   * during the game setup process.
   *
   * @param view  a view constructed with a read-only-model
   * @throws IllegalArgumentException if the constructor has a null field
   */
  public ControllerGUIImpl(IViewFrameGUI view, ReadOnlyIModel readOnlyModel) {
    if (view == null || readOnlyModel == null) {
      throw new IllegalArgumentException("readOnlyModel cannot be null");
    }
    this.readOnlyModel = readOnlyModel;
    this.view = view;

  }

  /**
   * Starts the game by setting up the game board and card deck, initializing the model with
   * specified configurations, and establishing the connection with the view. The view is set
   * to visible and linked to this controller to handle user interactions.
   *
   * @param model the game model that encapsulates game logic and state
   * @throws IllegalArgumentException if the model is null
   */
  @Override
  public void playGame(IModel model) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    this.model = model;
    this.model.startGame();
    this.view = new ViewFrameGUIImpl(this.readOnlyModel);
    view.addFeatures(this);
    view.makeVisible();
  }

  /**
   * Handles cell click events in the game grid. Triggered when a cell is clicked, typically
   * for board-based interactions.
   *
   * @param row the row index of the clicked cell
   * @param col the column index of the clicked cell
   */
  @Override
  public void handleCellClick(int row, int col) {
    int cardIndexToPlace = model.getCardIndexToPlace();
    IPlayer playerPlacing = model.getPlayerToPlace();
    model.placeCard(row, col, cardIndexToPlace, playerPlacing);
    view.updateBoard(model.getBoard());
    view.updateHand(cardIndexToPlace, playerPlacing);
    System.out.println("Cell clicked: " + row + ", " + col);
  }

  /**
   * Handles click events on a card in the player's hand. Triggered when a card is selected
   * by the player during gameplay.
   *
   * @param row the row index representing the card's position in the player's hand
   */
  @Override
  public void handleCardClick(int row, PlayerColor color) {
    view.highlightCard(row, color);
    model.updateCardToPlace(row, color);
    System.out.println("Card clicked: " + row + " Player color: " + color.toString());
  }
}
