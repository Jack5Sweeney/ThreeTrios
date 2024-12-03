package view;

import card.CellTypeContents;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import controller.Features;

/**
 * Implementation of the {@link IViewBoardPanelGUI} interface, providing a graphical panel
 * for displaying the game board within the GUI. Each cell is represented as its own panel.
 */
public class ViewBoardPanelGUIImpl extends JPanel implements IViewBoardPanelGUI {
  private Features features;

  /**
   * Constructs a {@code ViewBoardPanelGUIImpl} with specified rows, columns, and board
   * availability.
   *
   * @param numRows           the number of rows in the game board
   * @param numCols           the number of columns in the game board
   * @param boardAvailability a 2D array representing the availability status of each cell
   */
  public ViewBoardPanelGUIImpl(int numRows, int numCols, CellTypeContents[][] boardAvailability) {
    setLayout(new GridLayout(numRows, numCols)); // Set grid layout for the board

    // Initialize each cell panel and add it to the board
    for (int row = 0; row < numRows; row++) {
      for (int col = 0; col < numCols; col++) {
        CellPanel cellPanel = new CellPanel(row, col, boardAvailability[row][col]);
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
   * Enables interaction with all components within this container. This method
   * iterates through each component and enables it, allowing the user to interact
   * with the view.
   */
  @Override
  public void enableInteractions() {
    for (Component comp : getComponents()) {
      comp.setEnabled(true);
    }
  }

  /**
   * Disables interaction with all components within this container. This method
   * iterates through each component and disables it, preventing any user interaction
   * with the view.
   */
  @Override
  public void disableInteractions() {
    for (Component comp : getComponents()) {
      comp.setEnabled(false);
    }
  }

  /**
   * Enables interaction with all components within this container. This method
   * iterates through each component and enables it, allowing the user to interact
   * with the view.
   */
  public void enableInteraction() {
    for (Component comp : getComponents()) {
      comp.setEnabled(true);
    }
  }

  /**
   * Disables interaction with all components within this container. This method
   * iterates through each component and disables it, preventing any user interaction
   * with the view.
   */
  public void disableInteraction() {
    for (Component comp : getComponents()) {
      comp.setEnabled(false);
    }
  }


  /**
   * Inner class representing each cell in the board as a JPanel. Each cell has its own
   * color and
   * handles its own click events.
   */
  private class CellPanel extends JPanel {

    public CellPanel(int row, int col, CellTypeContents cellType) {
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
    public void updateCell(CellTypeContents cellType) {
      setCellColor(cellType);
      repaint(); // Repaint the cell to reflect the updated color
    }

    /**
     * Sets the color of the cell based on its type.
     *
     * @param cellType the type of cell (e.g., HOLE, EMPTY)
     */
    private void setCellColor(CellTypeContents cellType) {
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
