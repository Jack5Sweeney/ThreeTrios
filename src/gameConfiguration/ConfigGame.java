package gameConfiguration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ConfigGame {
  
  private final File pathToBoardConfig;
  private final File pathToCardDB;
  private int numRows;
  private int numCols;

  public ConfigGame(String board, String cardDb) {
    this.pathToBoardConfig = new File("docs" + File.separator + board);
    this.pathToCardDB = new File("docs" + File.separator + cardDb);
  }

  public CellType[][] getBoard() {
    return configBoard();
  }

  public ArrayList<ICard> getDeck() {
    return configCards();
  }


  /**
   * Reads the configuration file to set up the board dimensions and calls
   * the method to initialize board availability.
   *
   * @throws IllegalArgumentException if the file is not found, has an invalid format,
   *                                  or there is an issue reading the file
   */
  private CellType[][] configBoard() {
    if (pathToBoardConfig.exists() && pathToBoardConfig.isFile()) {
      try (BufferedReader reader = new BufferedReader(new FileReader(pathToBoardConfig))) {
        String firstLine = reader.readLine();
        if (firstLine != null) {
          String[] parts = firstLine.split("\\s+");
          if (parts.length == 2) {
            this.numRows = Integer.parseInt(parts[0]);
            this.numCols = Integer.parseInt(parts[1]);
            return configBoardAvailability(this.numRows, this.numCols, reader);
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
   * Configures the deck of cards by reading the card database file.
   * Cards are assigned to players alternately.
   *
   * @throws IllegalArgumentException if the card database file is missing, has an invalid
   *                                  format, is a duplicate card, or there is an error reading
   *                                  the file.
   */
  private ArrayList<ICard> configCards() {
    ArrayList<ICard> deck = new ArrayList<ICard>();
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
              throw new IllegalArgumentException("Invalid card database file format. Expected " +
                  "two integers.");
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
   * Configures the board availability based on the given dimensions and file content.
   * Initializes the board with CellType and Card based on the config file's content.
   *
   * @param numRows    the number of rows in the board
   * @param numColumns the number of columns in the board
   * @param reader     the BufferedReader used to read the configuration file
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
}