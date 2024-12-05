package controller;

import card.ICard;
import gameconsole.IGameConsole;
import player.IPlayer;
import model.IModel;
import player.PlayerColor;
import provider.src.threetrios.controller.PlayerActionsListener;
import provider.src.threetrios.view.Cell;
import strategies.Placement;
import view.IViewFrameGUI;

/**
 * Implementation of the {@link IControllerGUI} interface, responsible for managing the game
 * logic and interactions between the model and the GUI view. It also implements the
 * {@link Features} interface to handle user interactions within the GUI.
 */
public class ControllerGUIImpl implements IControllerGUI, Features, ModelObserver, PlayerActions, PlayerActionsListener {

  private IViewFrameGUI view;
  private final IPlayer player;
  private final IModel model;
  private boolean isMyTurn = false;
  private int selectedCardIndex = -1;
  private int[][] flipCount;
  private boolean hintsEnabled;

  /**
   * Constructs a {@code ControllerGUIImpl} with the specified view, model, and player.
   * Initializes the controller, sets up the model and view, and registers this controller
   * as an observer.
   *
   * @param view   the view interface connected to a read-only model for rendering the GUI
   * @param model  the game model containing the game state and logic
   * @param player the player using this controller
   * @throws IllegalArgumentException if any parameter is null
   */
  public ControllerGUIImpl(IViewFrameGUI view, IModel model, IPlayer player, IGameConsole gameConsole) {
    if (view == null || model == null || player == null) {
      throw new IllegalArgumentException("Arguments cannot be null");
    }
    this.view = view;
    this.model = model;
    this.player = player;
    this.hintsEnabled = false;
    // Register the controller as an observer of the model
    this.model.addObserver(this);

    int rows = model.getBoard().length;
    int cols = model.getBoard()[0].length;
     this.flipCount = new int[rows][cols];

    // Add features to the view
    view.addFeatures(this);
    gameConsole.addFeatures(this);
  }

  /**
   * Starts the game by setting up the game board and card deck, initializing the model with
   * specified configurations, and making the view visible to handle user interactions.
   */
  @Override
  public void playGame() {
    this.model.startGame();
    this.model.addObserver(this);
    //this.view = new ViewFrameGUIImpl(this.model);
    view.addFeatures(this);
    view.makeVisible();

    if (this.model.getCurrentPlayerColor().equals(player.getPlayerColor())) {
      this.isMyTurn = true;
      view.bringToFront();
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
  }

  /**
   * Handles click events on a card in the player's hand. Triggered when a card is selected
   * by the player during gameplay.
   *
   * @param row the index representing the card's position in the player's hand
   * @param color the color of the player selecting the card
   */
  @Override
  public void handleCardClick(int row, PlayerColor color) {
    if (!isMyTurn || color != player.getPlayerColor()) {
      view.showErrorMessage("It's not your turn or invalid card selection.");
      return;
    }

    selectedCardIndex = row;
    view.highlightCard(row, color);

    // Get the selected card from the player's hand
    IPlayer currentPlayer = (color == PlayerColor.RED) ? model.getRedPlayer() : model.getBluePlayer();
    ICard selectedCard = currentPlayer.getHand().get(row);

    // Initialize a 2D array to store flip counts
    int rows = model.getBoard().length;
    int cols = model.getBoard()[0].length;
    int[][] flipCounts = new int[rows][cols];

    // Use calculateFlips to evaluate each empty cell
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        if (model.isCellEmpty(i, j)) { // Check if the cell is empty
          flipCounts[i][j] = model.calculateFlips(i, j, selectedCard);
        }
      }
    }

    this.flipCount = flipCounts;
    if(this.hintsEnabled) {
      this.enableHints();
    }
  }

  @Override
  public void enableHints() {
    this.hintsEnabled = true;
    this.view.enableHints(this.flipCount);
  }

  @Override
  public void disableHints() {
    this.hintsEnabled = false;
    this.view.disableHints();
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
    view.refreshHands(model.getRedPlayer().getHand(), model.getBluePlayer().getHand());
    isMyTurn = (currentPlayer == player.getPlayerColor());
    if (isMyTurn) {
      view.enableInteractions();
      view.setTitle("Your turn, " + player.getPlayerColor().toString());
      choosePlayerMove();
      view.bringToFront();
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
    view.updateBoard(model.getBoard());
    view.refreshHands(model.getRedPlayer().getHand(), model.getBluePlayer().getHand());
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

  @Override
  public void choosePlayerMove() {
    Placement playerPlacement = player.chooseMove(model);
    if (playerPlacement != null) {
      int row = playerPlacement.row;
      int col = playerPlacement.column;
      int index = playerPlacement.cardIndex;
      model.placeCard(row, col, index, player);
    }
  }

  @Override
  public void onPlaceCard(int row, int col, Cell cell) {
    this.handleCellClick(row, col);
  }
}
