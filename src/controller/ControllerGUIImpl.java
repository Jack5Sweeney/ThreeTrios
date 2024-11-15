package controller;

import model.IPlayer;
import model.IModel;
import model.PlayerColor;
import view.IViewFrameGUI;
import view.ViewFrameGUIImpl;

/**
 * Implementation of the {@link IControllerGUI} interface, responsible for managing the game
 * logic and interactions between the model and the GUI view. It also implements the
 * {@link Features} interface to handle user interactions within the GUI.
 */
public class ControllerGUIImpl implements IControllerGUI, Features, ModelObserver {

  private IViewFrameGUI view;
  private final IPlayer player;
  private final IModel model;
  private boolean isMyTurn = false;
  private int selectedCardIndex = -1;

  /**
   * Constructs a {@code ControllerGUIImpl} with the specified view, model, and player.
   * Initializes the controller, sets up the model and view, and registers this controller
   * as an observer.
   *
   * @param view  the view interface connected to a read-only model for rendering the GUI
   * @param model the game model containing the game state and logic
   * @param player the player using this controller
   * @throws IllegalArgumentException if any parameter is null
   */
  public ControllerGUIImpl(IViewFrameGUI view, IModel model, IPlayer player) {
    if (view == null || model == null || player == null) {
      throw new IllegalArgumentException("Arguments cannot be null");
    }
    this.view = view;
    this.model = model;
    this.player = player;

    // Register the controller as an observer of the model
    this.model.addObserver(this);

    // Add features to the view
    view.addFeatures(this);

    // Disable interactions until it's this player's turn
    view.disableInteractions();
  }

  /**
   * Starts the game by setting up the game board and card deck, initializing the model with
   * specified configurations, and making the view visible to handle user interactions.
   */
  @Override
  public void playGame() {
    this.model.startGame();
    this.model.addObserver(this);
    this.view = new ViewFrameGUIImpl(this.model);
    view.addFeatures(this);
    view.makeVisible();

    if(this.model.getCurrentPlayerColor().equals(player.getPlayerColor())) {
      this.isMyTurn = true;
    }
  }

  /**
   * Handles cell click events in the game grid. Triggered when a cell is clicked, typically
   * for placing a card on the board.
   *
   * @param row the row index of the clicked cell
   * @param col the column index of the clicked cell
   */
  @Override
  public void handleCellClick(int row, int col) {
    if (!isMyTurn) {
      view.showErrorMessage("It's not your turn.");
      return;
    }
    if (selectedCardIndex == -1) {
      view.showErrorMessage("Please select a card first.");
      return;
    }
    try {
      model.placeCard(row, col, selectedCardIndex, player);
      selectedCardIndex = -1; // Reset selected card
    } catch (IllegalArgumentException | IllegalStateException e) {
      view.showErrorMessage(e.getMessage());
    }
    view.updateHand(model.getCardIndexToPlace(), player);
  }

  /**
   * Handles click events on a card in the player's hand. Triggered when a card is selected
   * by the player during gameplay.
   *
   * @param index the index representing the card's position in the player's hand
   * @param color the color of the player selecting the card
   */
  @Override
  public void handleCardClick(int index, PlayerColor color) {
    if (!isMyTurn || color != player.getPlayerColor()) {
      view.showErrorMessage("It's not your turn or invalid card selection.");
      return;
    }
    selectedCardIndex = index;
    view.highlightCard(index, color);
  }

  /**
   * Called when the current turn changes in the game. Updates the view and interaction
   * state based on whether it is this player's turn.
   *
   * @param currentPlayer the color of the current player
   */
  @Override
  public void onTurnChanged(PlayerColor currentPlayer) {
    view.updateBoard(model.getBoard());
    isMyTurn = (currentPlayer == player.getPlayerColor());
    if (isMyTurn) {
      view.enableInteractions();
      view.setTitle("Your turn, " + player.getPlayerColor().toString());
    } else {
      view.disableInteractions();
      view.setTitle("Waiting for opponent...");
    }
  }

  /**
   * Called when the game is over. Displays the appropriate end-game message in the view,
   * indicating whether the player won, lost, or the game ended in a tie.
   *
   * @param winningPlayerColor the color of the winning player, or {@code null} if the game
   *                           ended in a tie
   */
  @Override
  public void onGameOver(PlayerColor winningPlayerColor) {
    String message;
    if (winningPlayerColor == null) {
      message = "The game is a tie!";
    } else if (winningPlayerColor == player.getPlayerColor()) {
      message = "You win!";
    } else {
      message = "You lose.";
    }
    view.showGameOver(message);
    view.disableInteractions();
  }

}
