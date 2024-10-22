import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ModelImpl implements IModel {
  private int numRows;
  private int numColumns;
  private File pathToConfig;
  private ArrayList<ArrayList<CellType>> boardAvailibilty;
  private ArrayList<ArrayList<Card>> boardWithCards;

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
}
