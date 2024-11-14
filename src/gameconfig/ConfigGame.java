package gameconfig;

import model.CellType;
import model.ICard;
import model.CardImpl;
import model.PlayerColor;
import model.DirectionValue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The {@code ConfigGame} class is responsible for configuring the game board and deck of cards
 * by reading and processing external configuration files. The board configuration file specifies
 * the layout of the board, including playable and non-playable cells, while the card database
 * file provides details for each card used in the game.
 *
 * <p>Usage:</p>
 * <pre>
 *   ConfigGame configGame = new ConfigGame("boardConfig.txt", "cardDB.txt");
 *   CellType[][] board = configGame.getBoard();
 *   ArrayList&lt;ICard&gt; deck = configGame.getDeck();
 * </pre>
 *
 * <p><strong>Class Invariants:</strong></p>
 * <ul>
 *   <li>Board dimensions are set based on the configuration file and remain constant after
 *   setup.</li>
 *   <li>The deck contains unique cards with assigned player colors based on an alternating
 *   pattern.</li>
 * </ul>
 */
public class ConfigGame {

  private final File pathToBoardConfig;
  private final File pathToCardDB;

  /**
   * Initializes the configuration for the game by setting up paths to the board configuration
   * file and the card database file.
   *
   * @param board  the file name of the board configuration file
   * @param cardDb the file name of the card database file
   */
  public ConfigGame(String board, String cardDb) {
    this.pathToBoardConfig = new File("docs" + File.separator + board);
    this.pathToCardDB = new File("docs" + File.separator + cardDb);
  }

  /**
   * Retrieves the board setup as specified in the configuration file.
   *
   * @return a 2D array of {@link CellType} representing the board layout
   * @throws IllegalArgumentException if the configuration file is missing, has invalid content,
   *                                  or there is an error while reading it
   */
  public CellType[][] getBoard() {
    return configBoard();
  }

  /**
   * Retrieves the deck of cards created from the card database file.
   *
   * @return an {@link ArrayList} of {@link ICard} objects representing the deck of cards
   * @throws IllegalArgumentException if the card database file is missing, has invalid content,
   *                                  or there is an error while reading it
   */
  public ArrayList<ICard> getDeck() {
    return configCards();
  }

  /**
   * Reads the board configuration file to set up the board dimensions and initializes
   * the availability of each cell based on the content of the file.
   *
   * @return a 2D array of {@link CellType} that represents the initial board configuration
   * @throws IllegalArgumentException if the configuration file is missing, has an invalid format,
   *                                  or cannot be read properly
   */
  private CellType[][] configBoard() {
    if (pathToBoardConfig.exists() && pathToBoardConfig.isFile()) {
      try (BufferedReader reader = new BufferedReader(new FileReader(pathToBoardConfig))) {
        String firstLine = reader.readLine();
        if (firstLine != null) {
          String[] parts = firstLine.split("\\s+");
          if (parts.length == 2) {
            int numRows = Integer.parseInt(parts[0]);
            int numCols = Integer.parseInt(parts[1]);
            return configBoardAvailability(numRows, numCols, reader);
          } else {
            throw new IllegalArgumentException("Invalid config file format. Expected two " +
                "integers.");
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
   * Sets up the deck of cards by reading entries from the card database file. Cards are
   * created and assigned alternately to players based on the index, which determines their
   * color.
   *
   * @return an {@link ArrayList} of {@link ICard} objects representing the deck
   * @throws IllegalArgumentException if the card database file is missing, has an invalid format,
   *                                  or cannot be read properly
   */
  private ArrayList<ICard> configCards() {
    ArrayList<ICard> deck = new ArrayList<>();
    if (pathToCardDB.exists() && pathToCardDB.isFile()) {
      try (BufferedReader reader = new BufferedReader(new FileReader(pathToCardDB))) {
        String firstLine = reader.readLine();
        if (firstLine != null) {
          int playerToDealCardTo = 0;
          while (firstLine != null) {
            String[] parts = firstLine.split("\\s+");
            if (parts.length == 5) {
              ICard possibleCardToAdd = new CardImpl(determinePlayerColor(playerToDealCardTo),
                  parts[0],
                  determineDirectionValue(parts[1]),
                  determineDirectionValue(parts[2]),
                  determineDirectionValue(parts[3]),
                  determineDirectionValue(parts[4]));
              deck.add(possibleCardToAdd);
            } else {
              throw new IllegalArgumentException("Invalid card database file format.");
            }
            playerToDealCardTo++;
            firstLine = reader.readLine();
          }
        } else {
          throw new IllegalArgumentException("Card database file is empty.");
        }
      } catch (IOException e) {
        throw new IllegalArgumentException("Error reading card database file.", e);
      }
    } else {
      throw new IllegalArgumentException("Card database file not found.");
    }
    return deck;
  }

  /**
   * Reads the cell availability information from the board configuration file and sets up
   * the initial layout of the board.
   *
   * @param numRows    the number of rows on the board
   * @param numColumns the number of columns on the board
   * @param reader     the {@link BufferedReader} used to read the board configuration file
   * @return a 2D array of {@link CellType} indicating the type of each cell on the board
   * @throws IOException if there is an error reading the file or invalid content is encountered
   */
  private CellType[][] configBoardAvailability(int numRows, int numColumns, BufferedReader reader)
      throws IOException {
    CellType[][] boardAvailability = new CellType[numRows][numColumns];
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
    return boardAvailability;
  }

  /**
   * Determines the color of the player to whom a card will be assigned. Alternates between
   * {@link PlayerColor#RED} and {@link PlayerColor#BLUE} based on the index.
   *
   * @param playerToDealCardTo the index of the player to whom the card is being dealt
   * @return the {@link PlayerColor} (RED or BLUE) based on the index
   */
  private PlayerColor determinePlayerColor(int playerToDealCardTo) {
    return (playerToDealCardTo % 2 == 0) ? PlayerColor.RED : PlayerColor.BLUE;
  }

  /**
   * Converts a string representation of a direction value from the card database to the
   * corresponding {@link DirectionValue}.
   *
   * @param directionValue the string value representing the direction on a card
   * @return the corresponding {@link DirectionValue} based on the input string
   * @throws IllegalArgumentException if the provided string does not map to a valid direction
   *         value
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
}
