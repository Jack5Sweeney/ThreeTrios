import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ModelImpl implements IModel {
  private int numRows;
  private int numColumns;
  private File pathToConfig;
  private CellType[][] boardAvailability;
  private Card[][] boardWithCards;

  public ModelImpl(String board) {
    pathToConfig = new File("docs" + File.separator + board);
  }

  public void startGame() {
    configGame();
  }

  private void configGame() {
    if (pathToConfig.exists() && pathToConfig.isFile()) {
      try (BufferedReader reader = new BufferedReader(new FileReader(pathToConfig))) {
        String firstLine = reader.readLine();
        if (firstLine != null) {
          String[] parts = firstLine.split("\\s+");
          if (parts.length >= 2) {
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
        throw new IllegalArgumentException("Error reading config file.");
      }
    } else {
      throw new IllegalArgumentException("Config file not found.");
    }
  }

  private void configBoardAvailability(int numRows, int numColumns, BufferedReader reader) throws IOException {
    this.boardAvailability = new CellType[numRows][numColumns];
    this.boardWithCards = new Card[numRows][numColumns];
    for (int row = 0; row < numRows; row++) {
      String line = reader.readLine();
      char[] parts = line.toCharArray();
      for (int columns = 0; columns < numColumns; columns++) {
        if (parts[columns] == 'C') {
          boardAvailability[row][columns] = CellType.EMPTY;
        } else if (parts[columns] == 'X') {
          boardAvailability[row][columns] = CellType.EMPTY;
        }
        else throw new IOException();
      }
    }
  }


}
