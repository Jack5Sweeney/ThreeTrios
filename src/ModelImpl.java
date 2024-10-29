import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import static java.util.Arrays.asList;
import static java.util.Collections.frequency;

/**
 * Implementation of the {@link IModel} interface that represents the game model.
 * Handles board configuration and game setup.
 */
public class ModelImpl implements IModel {

  //test comment
  private int numRows;
  private int numColumns;
  private File pathToBoardConfig;
  private File pathToCardDB;
  private CellType[][] boardAvailability;
  private Card[][] boardWithCards;
  private ArrayList<Card> deck;
  private IPlayer redPlayer;
  private IPlayer bluePlayer;


  /**
   * Constructs the ModelImpl with the specified configuration files for the board and the card database.
   *
   * @param board  the name of the board configuration file
   * @param cardDB the name of the card database file
   */
  public ModelImpl(String board, String cardDB, ArrayList<IPlayer> players) {
    this.pathToBoardConfig = new File("docs" + File.separator + board);
    this.pathToCardDB = new File("docs" + File.separator + cardDB);
    this.deck = new ArrayList<>();
    this.redPlayer = players.get(0);
    this.bluePlayer = players.get(1);
  }

  /**
   * Starts the game by configuring the board and setting up its availability based on the config file,
   * adding all cards to a deck then distributing to the proper players.
   */
  @Override
  public void startGame() {
    configBoard();
    configCards();
    distributeCards();
  }

  /**
   * Reads the configuration file to set up the board dimensions and calls
   * the method to initialize board availability.
   *
   * @throws IllegalArgumentException if the file is not found, has an invalid format,
   *                                  or there is an issue reading the file
   */
  private void configBoard() {
    if (pathToBoardConfig.exists() && pathToBoardConfig.isFile()) {
      try (BufferedReader reader = new BufferedReader(new FileReader(pathToBoardConfig))) {
        String firstLine = reader.readLine();
        if (firstLine != null) {
          String[] parts = firstLine.split("\\s+");
          if (parts.length == 2) {
            this.numRows = Integer.parseInt(parts[0]);
            this.numColumns = Integer.parseInt(parts[1]);
            configBoardAvailability(this.numRows, this.numColumns, reader);
          } else {
            throw new IllegalArgumentException("Invalid config file format. Expected two integers.");
          }
        } else {
          throw new IllegalArgumentException("Config file is empty.");
        }
      } catch (IOException e) {
        throw new IllegalArgumentException("Error reading config file.", e);
      }
    } else {
      throw new IllegalArgumentException("Config file not found.");
    }
  }

  /**
   * Configures the deck of cards by reading the card database file.
   * Cards are assigned to players alternately.
   *
   * @throws IllegalArgumentException if the card database file is missing, has an invalid format,
   *                                  is a duplicate card, or there is an error reading the file.
   */
  private void configCards() {
    if (pathToCardDB.exists() && pathToCardDB.isFile()) {
      try (BufferedReader reader = new BufferedReader(new FileReader(pathToCardDB))) {
        String firstLine = reader.readLine();
        if (firstLine != null) {
          int playerToDealCardTo = 0;
          while (firstLine != null) {
            String[] parts = firstLine.split("\\s+");
            if (parts.length == 5) {
              Card possibleCardToAdd = new Card(determinePlayerColor(playerToDealCardTo), parts[0],
                  determineDirectionValue(parts[1]),
                  determineDirectionValue(parts[2]),
                  determineDirectionValue(parts[3]),
                  determineDirectionValue(parts[4]));
              if (confirmNonDupCard(possibleCardToAdd)) {
                this.deck.add(possibleCardToAdd);
              } else {
                throw new IllegalArgumentException("There are duplicate cards in the card database.");
              }
            } else {
              throw new IllegalArgumentException("Invalid card database file format. Expected two integers.");
            }
            playerToDealCardTo++;
            firstLine = reader.readLine();
          }
          ensureCorrectAmountOfCards();
        } else {
          throw new IllegalArgumentException("Card database file is empty.");
        }
      } catch (IOException e) {
        throw new IllegalArgumentException("Error reading card database file.", e);
      }
    } else {
      throw new IllegalArgumentException("Card database file not found.");
    }
  }

  /**
   * Ensures that the number of cards in the deck matches the expected minimum amount.
   * The expected number of cards should be equal to the number of playable spaces
   * (cells marked as {@link CellType#EMPTY}) plus one additional card.
   *
   * @throws IllegalArgumentException if the number of cards in the deck does not match the expected count
   */
  private void ensureCorrectAmountOfCards() {
    int playableSpacesCount = 0;
    for (CellType[] row : boardAvailability) {
      playableSpacesCount += frequency(asList(row), CellType.EMPTY);
    }
    if (this.deck.size() < playableSpacesCount + 1) {
      throw new IllegalArgumentException("The amount of cards in the deck should be equal to " +
          "(number of playable spaces) + 1.");
    }
  }

  /**
   * Confirms whether the card to be added is a duplicate.
   * Checks the deck to ensure the card with the same name does not already exist.
   *
   * @param possibleCardToAdd the card being checked for duplication
   * @return {@code true} if the card is not a duplicate, {@code false} otherwise
   */
  private boolean confirmNonDupCard(Card possibleCardToAdd) {
    if (!this.deck.isEmpty()) {
      for (Card card : this.deck) {
        if (card.getName().equals(possibleCardToAdd.getName())) {
          return false;
        }
      }
    }
    return true;
  }


  /**
   * Determines which player's color to assign to a card based on the index.
   * Alternates between RED and BLUE.
   *
   * @param playerToDealCardTo the index of the player to deal a card to
   * @return the PlayerColor (RED or BLUE) based on the index
   */
  private PlayerColor determinePlayerColor(int playerToDealCardTo) {
    if (playerToDealCardTo % 2 == 0) {
      return PlayerColor.RED;
    } else {
      return PlayerColor.BLUE;
    }
  }

  /**
   * Converts a string value from the card database into a corresponding {@link DirectionValue}.
   *
   * @param directionValue the string value representing the direction value
   * @return the corresponding DirectionValue
   * @throws IllegalArgumentException if the direction value is not valid
   */

  private DirectionValue determineDirectionValue(String directionValue) {
    switch (directionValue) {
      case "1":
        return DirectionValue.ONE;
      case "2":
        return DirectionValue.TWO;
      case "3":
        return DirectionValue.THREE;
      case "4":
        return DirectionValue.FOUR;
      case "5":
        return DirectionValue.FIVE;
      case "6":
        return DirectionValue.SIX;
      case "7":
        return DirectionValue.SEVEN;
      case "8":
        return DirectionValue.EIGHT;
      case "9":
        return DirectionValue.NINE;
      case "A":
        return DirectionValue.A;
      default:
        throw new IllegalArgumentException("Invalid direction value: " + directionValue);
    }
  }


  /**
   * Configures the board availability based on the given dimensions and file content.
   * Initializes the board with CellType and Card based on the config file's content.
   *
   * @param numRows    the number of rows in the board
   * @param numColumns the number of columns in the board
   * @param reader     the BufferedReader used to read the configuration file
   * @throws IOException if there is an error reading the file or invalid content is encountered
   */

  private void configBoardAvailability(int numRows, int numColumns, BufferedReader reader)
      throws IOException {
    this.boardAvailability = new CellType[numRows][numColumns];
    this.boardWithCards = new Card[numRows][numColumns];
    for (int row = 0; row < numRows; row++) {
      String line = reader.readLine();
      if (line != null) {
        char[] parts = line.toCharArray();
        for (int column = 0; column < numColumns; column++) {
          if (parts[column] == 'C') {
            boardAvailability[row][column] = CellType.EMPTY;
          } else if (parts[column] == 'X') {
            boardAvailability[row][column] = CellType.HOLE;
          } else {
            throw new IOException("Invalid character in config file.");
          }
        }
      } else {
        throw new IOException("Missing row in config file.");
      }
    }
  }

  /**
   * Distributes the cards from the deck to the players' hands based on the player's color.
   * Cards belonging to the red player are added to the {@code redHand}, and cards belonging
   * to the blue player are added to the {@code blueHand}.
   */
  private void distributeCards() {
    for (Card deckCard : this.deck) {
      if (deckCard.getPlayer() == PlayerColor.RED) {
        this.redPlayer.getHand().add(deckCard);
      } else {
        this.bluePlayer.getHand().add(deckCard);
      }
    }
  }

  /**
   * Places a card on the board for a specified player at the given row and column
   * position, removing it from the player's hand.
   *
   * @param boardRow        the row on the board where the card will be placed
   * @param boardCol        the column on the board where the card will be placed
   * @param cardIndexInHand the index of the card in the player's hand
   * @param player          the player who is placing the card
   * @throws IllegalArgumentException if the player is null, if the card index is invalid,
   *                                  or if the placement on the board is invalid
   */
  @Override
  public void placeCard(int boardRow, int boardCol, int cardIndexInHand, PlayerImpl player) {
    if (player == null) {
      throw new IllegalArgumentException("Player cannot be null.");
    }
    if (cardIndexInHand < 0 || cardIndexInHand >= player.getHand().size()) {
      throw new IllegalArgumentException("Invalid card index in hand.");
    }
    checkValidCardPlacement(boardRow, boardCol);
    Card placedCard = player.getHand().remove(cardIndexInHand);
    this.boardWithCards[boardRow][boardCol] = placedCard;
    this.boardAvailability[boardRow][boardCol] = CellType.CARD;
    updateBoard(placedCard, boardRow, boardCol);
  }


  /**
   * Verifies if a card placement is valid by checking the board boundaries
   * and ensuring the target cell is empty.
   *
   * @param boardRow the row of the placement
   * @param boardCol the column of the placement
   * @throws IllegalArgumentException if the placement is outside the board boundaries
   *                                  or if the target cell is not empty
   */
  private void checkValidCardPlacement(int boardRow, int boardCol) {
    checkValidIndex(boardRow, boardCol);
    if (this.boardAvailability[boardRow][boardCol] != CellType.EMPTY) {
      throw new IllegalArgumentException("The card placement is not valid for the board.");
    }
  }

  private void checkValidIndex(int boardRow, int boardCol) {
    if (boardRow < 0
        || boardCol < 0
        || boardRow >= this.boardAvailability.length
        || boardCol >= this.boardAvailability[boardRow].length) {
      throw new IllegalArgumentException("Invalid card placement for row "
          + boardRow + " and column " + boardCol);
    }
  }

  /**
   * Retrieves the card at the specified board position. If a card exists at the
   * given location, a new instance of the card is returned to prevent unintended
   * modifications. If no card is present, an exception is thrown.
   *
   * @param boardRow the row index on the board where the card is located
   * @param boardCol the column index on the board where the card is located
   * @return a new instance of the {@link Card} at the specified position
   * @throws IllegalArgumentException if there is no card at the specified position
   */
  public Card getCardAt(int boardRow, int boardCol) {
    checkValidIndex(boardRow, boardCol);
    if (this.boardWithCards[boardRow][boardCol] != null) {
      Card card = this.boardWithCards[boardRow][boardCol];
      return new Card(
              card.getPlayer(),
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

// Card placement implementation
// - board availability, two arrays representing an array of rows and array of columns
// - board with cards, two arrays representing an array of rows and array of CARDS null and card
// how should we represent the neighbors of the cards to call the implementation of the rules
// hashmap? array?

// function called placeCard where we place the card onto the board
// we have to access the first index of the hand, then place the card onto the
// board with cards, and this changes the according index of the board availability.
// in the GUI (future use) the boardWithCards is the representation and if a card is placed
// that card is shown, if it is null which means that there isn't a card, it checks the
// boardAvailability and sees if it is a hole or a empty.

  /**
   * Updates the game board to reflect any changes due to the placement of a card,
   * including resolving interactions with adjacent cards.
   *
   * @param cardPlaced the card that was recently placed on the board
   */

  public void updateBoard(Card cardPlaced, int row, int col) {
    int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};  // North, South, West, East
    Direction[] dirEnums = {Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST};

    // Iterate over each direction to check for adjacent cards
    for (int i = 0; i < directions.length; i++) {
      int adjRow = row + directions[i][0];
      int adjCol = col + directions[i][1];

      if (isValidPosition(adjRow, adjCol) && boardWithCards[adjRow][adjCol] != null) {
        Card adjacentCard = boardWithCards[adjRow][adjCol];

        // Check if the adjacent card belongs to the opponent
        if (adjacentCard.getPlayer() != cardPlaced.getPlayer()) {
          // Battle: compare card values in the respective direction
          Direction placedDir = dirEnums[i];
          Direction adjOppositeDir = getOppositeDirection(placedDir);

          if (cardPlaced.getDirectionsAndValues().get(placedDir).getValue() >
                  adjacentCard.getDirectionsAndValues().get(adjOppositeDir).getValue()) {

            // Flip the opponent's card and start a combo check
            flipCardOwnership(adjacentCard, adjRow, adjCol, cardPlaced.getPlayer());
            comboStep(adjacentCard, adjRow, adjCol, cardPlaced.getPlayer());
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
   * @param row the row index of the flipped card
   * @param col the column index of the flipped card
   * @param newOwner the new owner of the flipped card
   */

  private void comboStep(Card flippedCard, int row, int col, PlayerColor newOwner) {

    int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};  // North, South, West, East
    Direction[] dirEnums = {Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST};

    for (int i = 0; i < directions.length; i++) {
      int adjRow = row + directions[i][0];
      int adjCol = col + directions[i][1];

      if (isValidPosition(adjRow, adjCol) && boardWithCards[adjRow][adjCol] != null) {
        Card adjacentCard = boardWithCards[adjRow][adjCol];

        if (adjacentCard.getPlayer() != newOwner) {
          // Battle with the adjacent card
          Direction flippedDir = dirEnums[i];
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
   * @param card the card to flip
   * @param row the row index of the card on the board
   * @param col the column index of the card on the board
   * @param newOwner the new owner of the card
   */

  public void flipCardOwnership(Card card, int row, int col, PlayerColor newOwner) {
    Card flippedCard = new Card(newOwner, card.getName(),
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

  private Direction getOppositeDirection(Direction direction) {
    switch (direction) {
      case NORTH: return Direction.SOUTH;
      case SOUTH: return Direction.NORTH;
      case EAST:  return Direction.WEST;
      case WEST:  return Direction.EAST;
      default: throw new IllegalArgumentException("Invalid direction: " + direction);
    }
  }

  /**
   * Checks if the given row and column are within the bounds of the board.
   *
   * @param row the row index
   * @param col the column index
   * @return true if the position is valid; false otherwise
   */

  private boolean isValidPosition(int row, int col) {
    return row >= 0 && row < boardWithCards.length && col >= 0 && col < boardWithCards[0].length;
  }

  // getter for testing

  public IPlayer getRedPlayer() {
    return redPlayer;
  }

  // getter for testing

  public IPlayer getBluePlayer() {
    return bluePlayer;
  }

  // this implements the rules helper function that will be implemented
  // when the board is being updated.
  // updates the board, and then calls the functions to implement rules when the board
  // is being update so then it can change the cards colors if nessaracy.
}