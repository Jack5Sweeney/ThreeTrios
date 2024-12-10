package controller;

import cardcomparison.CardComparisonStrategy;
import gameconsole.IGameConsole;
import player.IPlayer;
import model.IModel;
import player.PlayerColor;
import provider.src.threetrios.controller.PlayerActionsListener;
import provider.src.threetrios.view.Cell;
import strategies.Placement;
import view.IViewFrameGUI;
import view.HintDecorator;

/**
 * Implementation of the {@link IControllerGUI} interface, responsible for managing the game
 * logic and interactions between the model and the GUI view. It also implements the
 * {@link Features} interface to handle user interactions within the GUI.
 */
public class ControllerGUIImpl implements IControllerGUI, Features, ModelObserver, PlayerActions, PlayerActionsListener {

  /**
   * The main GUI view, decorated to include hint functionality.
   */
  private final IViewFrameGUI view;

  /**
   * The player associated with this controller.
   */
  private final IPlayer player;

  /**
   * The game model, representing the state and logic of the game.
   */
  private final IModel model;

  /**
   * Indicates whether it is the player's turn.
   */
  private boolean isMyTurn = false;

  /**
   * Index of the card selected by the player, or -1 if no card is selected.
   */
  private int selectedCardIndex = -1;

  /**
   * Tracks whether hints are currently enabled.
   */
  private boolean hintsEnabled;

  /**
   * Constructs a {@code ControllerGUIImpl} with the specified view, model, and player.
   * Initializes the controller, sets up the model and view, and registers this controller
   * as an observer.
   *
   * @param view        the decorated view interface for rendering the GUI
   * @param model       the game model containing the game state and logic
   * @param player      the player using this controller
   * @param gameConsole the console interface for additional game interactions
   * @throws IllegalArgumentException if any of the arguments are {@code null}
   */
  public ControllerGUIImpl(IViewFrameGUI view, IModel model, IPlayer player, IGameConsole gameConsole) {
    if (view == null || model == null || player == null) {
      throw new IllegalArgumentException("Arguments cannot be null");
    }
    this.view = new HintDecorator(view, model, player.getPlayerColor());
    this.model = model;
    this.player = player;
    this.hintsEnabled = false;

    // Register the controller as an observer of the model
    this.model.addObserver(this);

    // Add features to the view and game console
    view.addFeatures(this);
    gameConsole.addFeatures(this);
  }

  /**
   * Starts the game by initializing the model, setting up the view, and determining
   * if it is the player's turn.
   */
  @Override
  public void playGame() {
    this.model.startGame();
    this.model.addObserver(this);
    view.addFeatures(this);
    view.makeVisible();

    if (this.model.getCurrentPlayerColor().equals(player.getPlayerColor())) {
      this.isMyTurn = true;
      view.bringToFront();
    }
  }

  /**
   * Handles a click on a board cell. If it is the player's turn and a card is selected,
   * attempts to place the card at the specified cell.
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
   * Handles a click on a card in the player's hand. Highlights the selected card
   * and recalculates hints if they are enabled.
   *
   * @param row   the row index of the card in the player's hand
   * @param color the color of the player who clicked the card
   */
  @Override
  public void handleCardClick(int row, PlayerColor color) {
    if (!isMyTurn || color != player.getPlayerColor()) {
      view.showErrorMessage("It's not your turn or invalid card selection.");
      return;
    }

    selectedCardIndex = row;
    view.highlightCard(row, color);

    // Recalculate hints for the newly highlighted card
    if (hintsEnabled) {
      view.enableHints(null); // Trigger the decorator to recalculate hints
    }
  }

  /**
   * Enables hint functionality in the view. Recalculates and displays hints for
   * the currently highlighted card.
   */
  @Override
  public void enableHints() {
    if (!hintsEnabled) {
      hintsEnabled = true;
      view.enableHints(null); // Delegates to the decorator for implementation
    }
  }

  /**
   * Disables hint functionality in the view. Removes any displayed hints.
   */
  @Override
  public void disableHints() {
    if (hintsEnabled) {
      hintsEnabled = false;
      view.disableHints(); // Delegates to the decorator for implementation
    }
  }

  /**
   * Sets the variant rule for card comparison in the model.
   *
   * @param variantRule the card comparison strategy to use
   */
  @Override
  public void setVariantRule(CardComparisonStrategy variantRule) {
    this.model.setVariantRule(variantRule);
  }

  /**
   * Handles changes in the current player's turn. Updates the view and enables
   * or disables interactions based on the current player.
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
   * Handles the game-over event. Displays the outcome of the game and disables
   * further interactions in the view.
   *
   * @param winningPlayerColor the color of the winning player, or {@code null} if the game is a tie
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

  /**
   * Allows the player to choose a move. If a valid move is chosen, places the card
   * at the selected cell.
   */
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

  /**
   * Handles card placement events. Invokes {@link #handleCellClick(int, int)} to process
   * the placement.
   *
   * @param row  the row index of the placed card
   * @param col  the column index of the placed card
   * @param cell the cell where the card was placed
   */
  @Override
  public void onPlaceCard(int row, int col, Cell cell) {
    this.handleCellClick(row, col);
  }
}
