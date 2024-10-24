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
  public ModelImpl(String board, String cardDB) {
    pathToBoardConfig = new File("docs" + File.separator + board);
    pathToCardDB = new File("docs" + File.separator + cardDB);
    deck = new ArrayList<>();
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
    ArrayList<Card> redHand = new ArrayList<>();
    ArrayList<Card> blueHand = new ArrayList<>();
    for(Card deckCard : this.deck) {
      if(deckCard.getPlayer() == PlayerColor.RED) {
        redHand.add(deckCard);
      }
      else { blueHand.add(deckCard); }
    }
    this.redPlayer = new PlayerImpl(PlayerColor.RED, redHand);
    this.bluePlayer = new PlayerImpl(PlayerColor.BLUE, blueHand);
  }

  @Override
  public void placeCard(int boardRow, int boardCol, int cardIndex, PlayerImpl player) {

  }
}