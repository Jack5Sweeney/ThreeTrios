package view;

import card.CellTypeContents;

import javax.swing.JLabel;
import javax.swing.OverlayLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import controller.Features;

/**
 * Implementation of the {@link IViewBoardPanelGUI} interface, providing a graphical panel
 * for displaying the game board within the GUI. Each cell is represented as its own panel
 * and is capable of handling user interactions.
 */
public class ViewBoardPanelGUIImpl extends JPanel implements IViewBoardPanelGUI {

  /**
   * The features interface used to handle user interactions with the game board.
   */
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
   * color, handles its own click events, and can display additional information such as
   * flip counts.
   */
  public class CellPanel extends JPanel {

    /**
     * The type of the cell, determining its appearance and behavior.
     */
    private CellTypeContents cellType;

    /**
     * Label to display the flip count within the cell.
     */
    private JLabel flipCountLabel;

    /**
     * Constructs a {@code CellPanel} for a specific cell in the game board.
     *
     * @param row      the row index of the cell
     * @param col      the column index of the cell
     * @param cellType the type of the cell (e.g., HOLE, EMPTY)
     */
    public CellPanel(int row, int col, CellTypeContents cellType) {
      this.cellType = cellType; // Initialize the cell type
      this.setLayout(new OverlayLayout(this));
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
     * Retrieves the type of the cell.
     *
     * @return the {@link CellTypeContents} of this cell
     */
    public CellTypeContents getCellType() {
      return cellType;
    }

    /**
     * Sets the flip count for this cell and updates its display.
     *
     * @param count the flip count to display
     */
    public void setFlipCount(int count) {
      if (flipCountLabel == null) {
        flipCountLabel = new JLabel();
        flipCountLabel.setForeground(Color.BLACK); // Set text color
        flipCountLabel.setAlignmentX(0.5f); // Center horizontally
        flipCountLabel.setAlignmentY(0.5f); // Center vertically
        this.add(flipCountLabel); // Add to the panel
      }

      flipCountLabel.setText(String.valueOf(count)); // Update the label text
      this.revalidate();
      this.repaint();
    }

    /**
     * Clears the flip count display from this cell.
     */
    public void clearFlipCount() {
      if (flipCountLabel != null) {
        this.remove(flipCountLabel);
        flipCountLabel = null;
        this.revalidate();
        this.repaint();
      }
    }

    /**
     * Sets the background color of this cell based on its type.
     *
     * @param cellType the {@link CellTypeContents} of the cell
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
