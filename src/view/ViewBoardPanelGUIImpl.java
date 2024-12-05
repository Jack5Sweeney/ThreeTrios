package view;

import card.CellTypeContents;

import javax.swing.*;

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

  @Override
  public void showFlipCounts(int[][] flipCounts) {
    int rows = flipCounts.length;
    int cols = flipCounts[0].length;

    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        Component cell = getComponent(row * cols + col); // Retrieve the cell panel

        if (cell instanceof CellPanel) {
          CellPanel cellPanel = (CellPanel) cell;

          // Check the cell type
          CellTypeContents cellType = cellPanel.getCellType();
          if (cellType == CellTypeContents.EMPTY) {
            // Set flip count for empty cells
            cellPanel.setFlipCount(flipCounts[row][col]);
          } else {
            // Clear flip count for non-empty cells
            cellPanel.clearFlipCount();
          }
        }
      }
    }

    this.revalidate();
    this.repaint();
  }

  @Override
  public void clearHints() {
    // Iterate through all components in the board
    for (Component comp : getComponents()) {
      if (comp instanceof CellPanel) {
        CellPanel cellPanel = (CellPanel) comp;
        cellPanel.clearFlipCount(); // Clear the flip count for each cell
      }
    }

    this.revalidate(); // Ensure the layout updates after clearing
    this.repaint();    // Redraw the board
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
    private CellTypeContents cellType; // Store the cell type
    private JLabel flipCountLabel;

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

    // Getter for the cell type
    public CellTypeContents getCellType() {
      return cellType;
    }

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

    public void clearFlipCount() {
      if (flipCountLabel != null) {
        this.remove(flipCountLabel);
        flipCountLabel = null;
        this.revalidate();
        this.repaint();
      }
    }

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
