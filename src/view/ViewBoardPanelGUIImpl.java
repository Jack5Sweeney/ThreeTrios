package view;

import model.CellType;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import controller.Features;

/**
 * Implementation of the {@link IViewBoardPanelGUI} interface, providing a graphical panel
 * for displaying the game board within the GUI. Each cell is represented as its own panel.
 */
public class ViewBoardPanelGUIImpl extends JPanel implements IViewBoardPanelGUI {
  private Features features;
  private final int numRows;
  private final int numCols;
  private final CellType[][] boardAvailability;
  private final CellPanel[][] cellPanels;

  /**
   * Constructs a {@code ViewBoardPanelGUIImpl} with specified rows, columns, and board
   * availability.
   *
   * @param numRows           the number of rows in the game board
   * @param numCols           the number of columns in the game board
   * @param boardAvailability a 2D array representing the availability status of each cell
   */
  public ViewBoardPanelGUIImpl(int numRows, int numCols, CellType[][] boardAvailability) {
    this.numRows = numRows;
    this.numCols = numCols;
    this.boardAvailability = boardAvailability;
    this.cellPanels = new CellPanel[numRows][numCols];

    setLayout(new GridLayout(numRows, numCols)); // Set grid layout for the board

    // Initialize each cell panel and add it to the board
    for (int row = 0; row < numRows; row++) {
      for (int col = 0; col < numCols; col++) {
        CellPanel cellPanel = new CellPanel(row, col, boardAvailability[row][col]);
        cellPanels[row][col] = cellPanel;
        this.add(cellPanel);
      }
    }
  }

  /**
   * Sets up the click listener to handle user interactions on the game board.
   *
   * @param features the {@link Features} interface for handling click events
   */
  @Override
  public void addClickListener(Features features) {
    this.features = features;
  }

  /**
   * Updates the visual state of each cell based on the board data. Each cell is updated
   * individually.
   *
   * @param updatedBoard a 2D array representing the updated board state
   */
  public void updateBoard(CellType[][] updatedBoard) {
    for (int row = 0; row < numRows; row++) {
      for (int col = 0; col < numCols; col++) {
        cellPanels[row][col].updateCell(updatedBoard[row][col]);
      }
    }
  }

  /**
   * Inner class representing each cell in the board as a JPanel. Each cell has its own
   * color and
   * handles its own click events.
   */
  private class CellPanel extends JPanel {

    public CellPanel(int row, int col, CellType cellType) {
      setCellColor(cellType); // Set the initial color based on the cell type

      setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

      // Add a mouse listener to handle click events
      addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
          if (features != null) {
            features.handleCellClick(row, col); // Notify the controller of the click
          }
        }
      });
    }

    /**
     * Updates the cell color based on the new cell type.
     *
     * @param cellType the new cell type for this cell
     */
    public void updateCell(CellType cellType) {
      setCellColor(cellType);
      repaint(); // Repaint the cell to reflect the updated color
    }

    /**
     * Sets the color of the cell based on its type.
     *
     * @param cellType the type of cell (e.g., HOLE, EMPTY)
     */
    private void setCellColor(CellType cellType) {
      switch (cellType) {
        case HOLE:
          setBackground(Color.GRAY);
          break;
        case EMPTY:
          setBackground(Color.YELLOW);
          break;
        default:
          setBackground(Color.WHITE);
          break;
      }
    }
  }
}
