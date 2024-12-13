package model;

import card.CardImpl;
import card.CellTypeContents;
import card.Direction;
import card.ICard;
import cardcomparison.CardComparisonStrategy;
import controller.ModelObserver;
import flipcriteria.ICriteria;
import player.IPlayer;
import player.PlayerColor;
import player.PlayerImpl;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.frequency;

/**
 * The {@code ModelImpl} class implements the {@link IModel} interface, representing the game
 * model. It handles game setup, board configuration, and manages game state and player actions.
 *
 * <p><strong>Class Invariants:</strong></p>
 * <ul>
 *   <li>The board availability and board with cards arrays are consistent:
 *       <ul>
 *           <li>If <code>boardAvailability[i][j] == card.CellType.CARD</code>, then
 *               <code>boardWithCards[i][j] != null</code>.</li>
 *           <li>If <code>boardAvailability[i][j] != card.CellType.CARD</code>, then
 *               <code>boardWithCards[i][j] == null</code>.</li>
 *       </ul>
 *   </li>
 *   <li>The red player is always {@link PlayerColor#RED}, and the blue player is always
 *       {@link PlayerColor#BLUE}.</li>
 * </ul>
 */
public class ModelImpl implements IModel {

  private final CellTypeContents[][] boardAvailability;
  private final ICard[][] boardWithCards;
  private final ArrayList<ICard> deck;
  private final IPlayer redPlayer;
  private final IPlayer bluePlayer;
  private boolean gameStarted;
  private boolean gameOver;
  private IPlayer winningPlayer;
  private IPlayer currentPlayer;
  private IPlayer playerPlacing;
  private int cardIndexToPlace;

  private final List<ModelObserver> observers = new ArrayList<>();

  /**
   * Initializes the game model with a board configuration, deck of cards, and players.
   *
   * @param board   a 2D array of {@link CellTypeContents} representing the board layout
   * @param deck    a list of {@link ICard} representing the deck of cards
   * @param players a list of {@link IPlayer} representing the players in the game
   */
  public ModelImpl(CellTypeContents[][] board, ArrayList<ICard> deck, ArrayList<IPlayer> players) {
    this.boardAvailability = board;
    this.boardWithCards = new ICard[board.length][board[0].length];
    this.deck = deck;
    this.redPlayer = players.get(0);
    this.bluePlayer = players.get(1);
    this.gameStarted = false;
    this.gameOver = false;
    this.currentPlayer = players.get(0);
    distributeCards();
  }

  /**
   * Starts the game by validating the deck and distributing cards to the players.
   */
  @Override
  public void startGame() {
    gameStarted = true;
    ensureCorrectAmountOfCards();
    confirmNonDupCard();
  }

  @Override
  public boolean isCellEmpty(int row, int col) {
    return boardAvailability[row][col] == CellTypeContents.EMPTY;
  }

  @Override
  public void setVariantRule(CardComparisonStrategy variantRule) {
    // no implementation needed as it does not have the variants
  }

  @Override
  public void setCardComparisonStrategy(CardComparisonStrategy strategy) {
    // not needed
  }

  @Override
  public void setFlipCriteria(ICriteria criteria) {
    // not needed
  }

  /**
   * Adds an observer to the list of observers that will be notified of changes
   * in the model's state. Observers can listen for specific events or updates
   * in the model.
   *
   * @param observer the observer to be added; must not be null
   * @throws IllegalArgumentException if the observer is null
   */
  public void addObserver(ModelObserver observer) {
    if (observer == null) {
      throw new IllegalArgumentException("Observer cannot be null.");
    }
    observers.add(observer);
  }

  /**
   * Notifies all registered observers that the current player's turn has changed.
   * This method iterates through each observer and calls its {@code onTurnChanged}
   * method, passing the color of the current player.
   */
  private void notifyTurnChanged() {
    for (ModelObserver observer : observers) {
      if (observer != null) { // Ensure observer is not null before notifying
        observer.onTurnChanged(this.currentPlayer.getPlayerColor());
      }
    }
  }

  /**
   * Notifies all registered observers that the game is over. This method determines
   * the color of the winning player, if there is one, and informs each observer
   * by calling its {@code onGameOver} method. If there is no winning player
   * (e.g., in case of a draw), {@code null} is passed as the winner color
   * to indicate that there is no specific winner.
   */
  private void notifyGameOver() {
    PlayerColor winnerColor = (this.winningPlayer != null)
        ? this.winningPlayer.getPlayerColor() : null;
    for (ModelObserver observer : observers) {
      if (observer != null) { // Ensure observer is not null before notifying
        observer.onGameOver(winnerColor);
      }
    }
  }

  /**
   * Method to get the current players color for the view to visualize the current player.
   *
   * @return the current players color
   */
  @Override
  public PlayerColor getCurrentPlayerColor() {
    return this.currentPlayer.getPlayerColor();
  }

  /**
   * Ensures the deck has the correct number of cards based on the playable spaces on the board.
   *
   * @throws IllegalArgumentException if the deck size does not match the expected count
   */
  private void ensureCorrectAmountOfCards() {
    int playableSpacesCount = 0;
    for (CellTypeContents[] row : boardAvailability) {
      playableSpacesCount += frequency(asList(row), CellTypeContents.EMPTY);
    }
    if (this.deck.size() < playableSpacesCount + 1) {
      throw new IllegalArgumentException("The deck should contain at least " +
          "(playable spaces + 1) cards.");
    }
  }

  /**
   * Confirms that the deck does not contain duplicate cards based on card names.
   *
   * @throws IllegalArgumentException if duplicate cards are found in the deck
   */
  private void confirmNonDupCard() {
    if (!this.deck.isEmpty()) {
      for (ICard outerCard : this.deck) {
        String name = outerCard.getName();
        for (ICard innerCard : this.deck) {
          if (innerCard.getName().equals(name) && !innerCard.equals(outerCard)) {
            throw new IllegalArgumentException("Cannot have duplicate cards");
          }
        }
      }
    }
  }

  /**
   * Distributes cards from the deck to the players based on the player's color.
   * Red player receives red cards, and blue player receives blue cards.
   */
  private void distributeCards() {
    for (ICard deckCard : this.deck) {
      if (deckCard.getPlayerColor() == PlayerColor.RED) {
        this.redPlayer.addToHand(deckCard);
      } else {
        this.bluePlayer.addToHand(deckCard);
      }
    }
  }

  /**
   * Places a card on the board at the specified location for a player, removing it from the
   * player's hand.
   *
   * @param boardRow        the row on the board for card placement
   * @param boardCol        the column on the board for card placement
   * @param cardIndexInHand the index of the card in the player's hand
   * @param player          the player placing the card
   * @throws IllegalArgumentException if the placement or player is invalid
   * @throws IllegalStateException    if the game is not started or already over
   */
  @Override
  public void placeCard(int boardRow, int boardCol, int cardIndexInHand, IPlayer player) {
    checkGameStarted();
    checkGameOver();

    if (player == null) {
      throw new IllegalArgumentException("Player cannot be null.");
    }
    if (cardIndexInHand < 0 || cardIndexInHand >= player.getHand().size()) {
      throw new IllegalArgumentException("Invalid card index in hand.");
    }
    if (currentPlayer.getPlayerColor() != player.getPlayerColor()) {
      throw new IllegalArgumentException("Player is not in turn.");
    }
    checkValidCardPlacement(boardRow, boardCol);
    ICard placedCard = player.removeFromHand(cardIndexInHand);
    this.boardWithCards[boardRow][boardCol] = placedCard;
    this.boardAvailability[boardRow][boardCol] = CellTypeContents.CARD;
    updateBoard(placedCard, boardRow, boardCol);
    updateCurrentPlayer(player);
    checkGameStatus();
  }

  /**
   * Updates the card to be placed on the board based on the specified row and player color.
   * This may involve changing the card's state or selection before placement.
   *
   * @param index the row on the board where the card will potentially be placed
   * @param color the color associated with the player or card being updated
   */
  @Override
  public void updateCardToPlace(int index, PlayerColor color) {
    IPlayer player;
    if (color == PlayerColor.RED) {
      player = this.getRedPlayer();
    } else {
      player = this.getBluePlayer();
    }
    this.playerPlacing = player;
    this.cardIndexToPlace = index;
  }

  @Override
  public IPlayer getPlayerToPlace() {
    return this.playerPlacing;
  }

  @Override
  public int getCardIndexToPlace() {
    return Integer.valueOf(this.cardIndexToPlace);
  }


  /**
   * Updates the current player after a turn.
   *
   * @param player the player who completed their turn
   */
  private void updateCurrentPlayer(IPlayer player) {
    if (player.getPlayerColor() == PlayerColor.RED) {
      this.currentPlayer = this.bluePlayer;
    } else {
      this.currentPlayer = this.redPlayer;
    }
    notifyTurnChanged();
  }

  /**
   * Validates if the card placement is within board boundaries and if the cell is empty.
   *
   * @param boardRow the row index
   * @param boardCol the column index
   * @throws IllegalArgumentException if the placement is out of bounds or cell is occupied
   */
  private void checkValidCardPlacement(int boardRow, int boardCol) {
    checkValidIndex(boardRow, boardCol);
    if (this.boardAvailability[boardRow][boardCol] != CellTypeContents.EMPTY) {
      throw new IllegalArgumentException("The card placement is not valid for the board.");
    }
  }

  /**
   * Checks if a row and column index are within the board's valid bounds.
   *
   * @param boardRow the row index
   * @param boardCol the column index
   * @throws IllegalArgumentException if the indices are out of bounds
   */
  private void checkValidIndex(int boardRow, int boardCol) {
    if (boardRow < 0 || boardCol < 0 || boardRow >= this.boardAvailability.length
        || boardCol >= this.boardAvailability[boardRow].length) {
      throw new IllegalArgumentException("Invalid card placement for row " + boardRow +
          " and column " + boardCol);
    }
  }

  /**
   * Retrieves a copy of the card at the specified board position, if one exists.
   *
   * @param boardRow the row index
   * @param boardCol the column index
   * @return a new instance of the {@link CardImpl} at the specified position
   * @throws IllegalArgumentException if there is no card at the specified position
   */
  public CardImpl getCardAt(int boardRow, int boardCol) {
    checkGameStarted();
    checkGameOver();
    checkValidIndex(boardRow, boardCol);
    if (this.boardWithCards[boardRow][boardCol] != null) {
      ICard card = this.boardWithCards[boardRow][boardCol];
      return new CardImpl(
          card.getPlayerColor(),
          card.getName(),
          card.getDirectionsAndValues().get(Direction.NORTH),
          card.getDirectionsAndValues().get(Direction.EAST),
          card.getDirectionsAndValues().get(Direction.SOUTH),
          card.getDirectionsAndValues().get(Direction.WEST)
      );
    } else {
      throw new IllegalArgumentException("No such card.");
    }
  }

  /**
   * Updates the game board based on card interactions, flipping adjacent cards as necessary.
   *
   * @param cardPlaced the card recently placed on the board
   * @throws IllegalStateException if the game has not started or is over
   */
  private void updateBoard(ICard cardPlaced, int row, int col) {
    checkGameStarted();
    checkGameOver();
    int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    Direction[] dirEnums = {Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST};

    for (int directionIndex = 0; directionIndex < directions.length; directionIndex++) {
      int adjRow = row + directions[directionIndex][0];
      int adjCol = col + directions[directionIndex][1];

      if (isValidPosition(adjRow, adjCol) && boardWithCards[adjRow][adjCol] != null) {
        ICard adjacentCard = boardWithCards[adjRow][adjCol];

        if (adjacentCard.getPlayerColor() != cardPlaced.getPlayerColor()) {
          Direction placedDir = dirEnums[directionIndex];
          Direction adjOppositeDir = getOppositeDirection(placedDir);

          if (cardPlaced.getDirectionsAndValues().get(placedDir).getValue() >
              adjacentCard.getDirectionsAndValues().get(adjOppositeDir).getValue()) {

            flipCardOwnership(adjacentCard, adjRow, adjCol, cardPlaced.getPlayerColor());
            comboStep(adjacentCard, adjRow, adjCol, cardPlaced.getPlayerColor());
          }
        }
      }
    }
  }

  /**
   * Handles the combo step after flipping an opponent's card. Newly flipped cards may battle
   * adjacent cards recursively until no more cards can be flipped.
   *
   * @param flippedCard the card that has been flipped
   * @param row         the row index of the flipped card
   * @param col         the column index of the flipped card
   * @param newOwner    the new owner of the flipped card
   */

  private void comboStep(ICard flippedCard, int row, int col, PlayerColor newOwner) {

    int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};  // North, South, West, East
    Direction[] dirEnums = {Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST};

    for (int directionIndex = 0; directionIndex < directions.length; directionIndex++) {
      int adjRow = row + directions[directionIndex][0];
      int adjCol = col + directions[directionIndex][1];

      if (isValidPosition(adjRow, adjCol) && boardWithCards[adjRow][adjCol] != null) {
        ICard adjacentCard = boardWithCards[adjRow][adjCol];

        if (adjacentCard.getPlayerColor() != newOwner) {
          // Battle with the adjacent card
          Direction flippedDir = dirEnums[directionIndex];
          Direction adjOppositeDir = getOppositeDirection(flippedDir);

          if (flippedCard.getDirectionsAndValues().get(flippedDir).getValue() >
              adjacentCard.getDirectionsAndValues().get(adjOppositeDir).getValue()) {

            // Flip the opponent's card and continue the combo step
            flipCardOwnership(adjacentCard, adjRow, adjCol, newOwner);
            comboStep(adjacentCard, adjRow, adjCol, newOwner);
          }
        }
      }
    }
  }

  /**
   * Flips the ownership of a card to a new player.
   *
   * @param card     the card to flip
   * @param row      the row index of the card on the board
   * @param col      the column index of the card on the board
   * @param newOwner the new owner of the card
   */

  private void flipCardOwnership(ICard card, int row, int col, PlayerColor newOwner) {
    CardImpl flippedCard = new CardImpl(newOwner, card.getName(),
        card.getDirectionsAndValues().get(Direction.NORTH),
        card.getDirectionsAndValues().get(Direction.EAST),
        card.getDirectionsAndValues().get(Direction.SOUTH),
        card.getDirectionsAndValues().get(Direction.WEST));

    // Update the board with the flipped card
    boardWithCards[row][col] = flippedCard;
  }

  /**
   * Gets the opposite direction of a given direction.
   *
   * @param direction the direction for which to find the opposite
   * @return the opposite direction
   */

  public Direction getOppositeDirection(Direction direction) {
    switch (direction) {
      case NORTH:
        return Direction.SOUTH;
      case SOUTH:
        return Direction.NORTH;
      case EAST:
        return Direction.WEST;
      case WEST:
        return Direction.EAST;
      default:
        throw new IllegalArgumentException("Invalid direction: " + direction);
    }
  }

  /**
   * Checks if the specified position on the board is within valid bounds.
   *
   * @param row the row index to check
   * @param col the column index to check
   * @return {@code true} if the position is valid; {@code false} otherwise
   */
  public boolean isValidPosition(int row, int col) {
    return row >= 0 && row < boardWithCards.length && col >= 0 && col < boardWithCards[0].length;
  }

  /**
   * Retrieves a new instance of the red player with the same color and hand.
   *
   * @return a new {@link IPlayer} instance for the red player
   */
  public IPlayer getRedPlayer() {
    return new PlayerImpl(PlayerColor.RED, redPlayer.getHand());
  }

  /**
   * Retrieves a new instance of the blue player with the same color and hand.
   *
   * @return a new {@link IPlayer} instance for the blue player
   */
  public IPlayer getBluePlayer() {
    return new PlayerImpl(PlayerColor.BLUE, bluePlayer.getHand());
  }


  /**
   * Provides a deep copy of the current board with all cards.
   *
   * @return a 2D array of {@link CardImpl} objects representing the board, with all attributes
   *         copied
   */
  public ICard[][] getBoard() {
    int numRows = boardWithCards.length;
    int numCols = boardWithCards[0].length;
    ICard[][] boardCopy = new CardImpl[numRows][numCols];

    for (int rows = 0; rows < numRows; rows++) {
      for (int cols = 0; cols < numCols; cols++) {
        if (boardWithCards[rows][cols] != null) {
          ICard card = boardWithCards[rows][cols];
          boardCopy[rows][cols] = new CardImpl(
              card.getPlayerColor(),
              card.getName(),
              card.getDirectionsAndValues().get(Direction.NORTH),
              card.getDirectionsAndValues().get(Direction.EAST),
              card.getDirectionsAndValues().get(Direction.SOUTH),
              card.getDirectionsAndValues().get(Direction.WEST)
          );
        } else {
          boardCopy[rows][cols] = null;
        }
      }
    }
    return boardCopy;
  }

  /**
   * Provides a copy of the board availability array, showing each cell's availability type.
   *
   * @return a 2D array of {@link CellTypeContents} objects representing the availability status of
   *         each cell
   */
  public CellTypeContents[][] getBoardAvailability() {
    int numRows = boardAvailability.length;
    CellTypeContents[][] availabilityCopy = new CellTypeContents[numRows][];

    for (int rowIndx = 0; rowIndx < numRows; rowIndx++) {
      availabilityCopy[rowIndx] = boardAvailability[rowIndx].clone();
    }
    return availabilityCopy;
  }

  /**
   * Checks the current game status, updating game state and determining the winning player.
   * This method counts the number of cards each player has on the board and checks if
   * the board is full, updating the gameOver and winningPlayer fields as necessary.
   */

  private void checkGameStatus() {
    int redCount = 0;
    int blueCount = 0;
    int totalPlayableCells = 0;
    int totalOccupiedCells = 0;

    for (int row = 0; row < boardWithCards.length; row++) {
      for (int col = 0; col < boardWithCards[row].length; col++) {
        if (boardAvailability[row][col] == CellTypeContents.EMPTY ||
            boardAvailability[row][col] == CellTypeContents.CARD) {
          totalPlayableCells++;
          if (boardWithCards[row][col] != null) {
            totalOccupiedCells++;
            ICard card = boardWithCards[row][col];
            if (card.getPlayerColor() == PlayerColor.RED) {
              redCount++;
            } else if (card.getPlayerColor() == PlayerColor.BLUE) {
              blueCount++;
            }
          }
        }
      }
    }

    if (totalOccupiedCells == totalPlayableCells) {
      this.gameOver = true;
    }
    if (redCount > blueCount) {
      this.winningPlayer = this.redPlayer;
    } else if (blueCount > redCount) {
      this.winningPlayer = this.bluePlayer;
    } else {
      this.winningPlayer = null;
    }

    if (this.gameOver) {
      notifyGameOver();
    }
  }

  /**
   * Validates if the game has started. Throws an exception if the game is not yet started.
   *
   * @throws IllegalStateException if the game has not started
   */
  public boolean checkGameStarted() {
    if (!this.gameStarted) {
      throw new IllegalStateException("The game is not started!");
    }
    return true;
  }

  /**
   * Checks if the game is over and throws an exception if it is.
   *
   * @throws IllegalStateException if the game is not started
   */
  public boolean checkGameOver() {
    if (!this.gameStarted) {
      throw new IllegalStateException("The game is over!");
    }
    return this.gameOver;
  }

  /**
   * Retrieves the winning player of the game.
   * If the game ended in a draw, throws an exception as there is no winning player.
   *
   * @return a new {@link IPlayer} instance representing the winning player
   * @throws IllegalStateException if the game ended in a draw with no winning player
   *                               or if the game has not started.
   */
  public IPlayer getWinningPlayer() {
    checkGameStarted();
    if (this.winningPlayer == null) {
      throw new IllegalStateException("There is a tie, no winning player yet");
    }
    return new PlayerImpl(winningPlayer.getPlayerColor(), winningPlayer.getHand());
  }


  /**
   * Calculates the number of flips that would occur if a card were placed at the specified
   * board position, including propagation of flips. A flip occurs when the placed card's value
   * in a given direction is greater than the adjacent card's value in the opposite direction.
   *
   * @param row  the row index where the card is to be placed
   * @param col  the column index where the card is to be placed
   * @param card the card being placed on the board
   * @return the number of opponent cards that would be flipped by this placement
   */
  public int calculateFlips(int row, int col, ICard card) {
    int flipCount = 0;
    boolean[][] visited = new boolean[boardWithCards.length][boardWithCards[0].length];
    return calculateFlipsRecursive(row, col, card, card.getPlayerColor(), visited);
  }

  /**
   * A recursive helper method to count flips, propagating through the board by following the
   * flipping rules in each direction.
   *
   * @param row        the row index of the current card
   * @param col        the column index of the current card
   * @param card       the card used to initiate the flip
   * @param ownerColor the color of the player placing the card
   * @param visited    a boolean 2D array to track visited positions and avoid re-counting flips
   * @return the number of opponent cards that would be flipped by this placement
   */
  private int calculateFlipsRecursive(int row, int col, ICard card, PlayerColor ownerColor,
                                      boolean[][] visited) {
    int flipCount = 0;
    int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    Direction[] dirEnums = {Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST};

    for (int directionIndex = 0; directionIndex < directions.length; directionIndex++) {
      int adjRow = row + directions[directionIndex][0];
      int adjCol = col + directions[directionIndex][1];

      if (isValidPosition(adjRow, adjCol) && boardWithCards[adjRow][adjCol] != null &&
          !visited[adjRow][adjCol]) {

        ICard adjacentCard = boardWithCards[adjRow][adjCol];

        if (adjacentCard.getPlayerColor() != ownerColor) {
          Direction placedDir = dirEnums[directionIndex];
          Direction adjOppositeDir = getOppositeDirection(placedDir);

          if (card.getDirectionsAndValues().get(placedDir).getValue() >
              adjacentCard.getDirectionsAndValues().get(adjOppositeDir).getValue()) {

            // Mark as visited and count this flip
            visited[adjRow][adjCol] = true;
            flipCount++;

            // Recursively calculate flips from this newly "flipped" card
            flipCount += calculateFlipsRecursive(adjRow, adjCol, adjacentCard, ownerColor,
                visited);
          }
        }
      }
    }
    return flipCount;
  }


  /**
   * Calculates the score for the specified player color by counting the cards on the board
   * that belong to the player.
   *
   * @param playerColor the color of the player whose score is being calculated
   * @return the number of cards on the board that belong to the specified player
   */
  public int getPlayerScore(PlayerColor playerColor) {
    int score = 0;
    for (ICard[] row : boardWithCards) {
      for (ICard card : row) {
        if (card != null && card.getPlayerColor() == playerColor) {
          score++;
        }
      }
    }
    return score;
  }
}