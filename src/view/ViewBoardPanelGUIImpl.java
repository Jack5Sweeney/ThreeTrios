package view;

import model.CellType;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import controller.Features;

/**
 * Implementation of the {@link IViewBoardPanelGUI} interface, providing a graphical panel
 * for displaying the game board within the GUI. This panel handles rendering of cells and
 * grid lines and supports user interactions through mouse clicks.
 */
public class ViewBoardPanelGUIImpl extends JPanel implements IViewBoardPanelGUI {
  private Features features;
  private final int numRows;
  private final int numCols;
  private final CellType[][] boardAvailability;

  /**
   * Constructs a {@code ViewBoardPanelGUIImpl} with specified rows, columns, and board availability.
   *
   * @param numRows           the number of rows in the game board
   * @param numCols           the number of columns in the game board
   * @param boardAvailability a 2D array representing the availability status of each cell
   */
  public ViewBoardPanelGUIImpl(int numRows, int numCols, CellType[][] boardAvailability) {
    this.numRows = numRows;
    this.numCols = numCols;
    this.boardAvailability = boardAvailability;
    this.addMouseListener(new BoardClickListener());
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
   * Paints the component by drawing the cells with colors based on their availability,
   * as well as the grid lines.
   *
   * @param g the {@code Graphics} object used for drawing
   */
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g.create();
    g2d.transform(getModelToPhysical());
    drawCells(g2d);
    drawGrid(g2d);
  }

  /**
   * Draws the cells on the board based on their type, using different colors for each cell type.
   *
   * @param g2d the {@code Graphics2D} object used for drawing
   */
  private void drawCells(Graphics2D g2d) {
    for (int row = 0; row < numRows; row++) {
      for (int col = 0; col < numCols; col++) {
        CellType cellType = boardAvailability[row][col];
        Color color;
        switch (cellType) {
          case HOLE:
            color = Color.GRAY;
            break;
          case EMPTY:
            color = Color.YELLOW;
            break;
          default:
            color = Color.WHITE;
            break;
        }
        g2d.setColor(color);
        g2d.fillRect(col, row, 1, 1);
      }
    }
  }

  /**
   * Draws the grid lines to visually separate cells on the game board.
   *
   * @param g2d the {@code Graphics2D} object used for drawing
   */
  private void drawGrid(Graphics2D g2d) {
    g2d.setColor(Color.BLACK);
    g2d.setStroke(new BasicStroke(0.01f));

    // Draw horizontal grid lines
    for (int row = 0; row <= numRows; row++) {
      g2d.drawLine(0, row, numCols, row);
    }
    // Draw vertical grid lines
    for (int col = 0; col <= numCols; col++) {
      g2d.drawLine(col, 0, col, numRows);
    }
  }

  /**
   * Generates an {@code AffineTransform} for scaling model coordinates to physical screen
   * coordinates, enabling a coordinate system that matches the number of rows and columns.
   *
   * @return the {@code AffineTransform} for scaling coordinates
   */
  private AffineTransform getModelToPhysical() {
    AffineTransform xForm = new AffineTransform();
    xForm.scale((double) this.getWidth() / numCols, (double) this.getHeight() / numRows);
    return xForm;
  }

  /**
   * Private inner class implementing {@code MouseListener} to handle click events on
   * the game board. Transforms the click position to model coordinates and calls
   * {@code handleCellClick} in {@link Features}.
   */
  private class BoardClickListener implements MouseListener {

    @Override
    public void mouseClicked(MouseEvent e) {
      try {
        AffineTransform physicalToModel = getModelToPhysical();
        physicalToModel.invert();

        Point2D evtPt = e.getPoint();
        Point2D modelPt = physicalToModel.transform(evtPt, null);
        int row = (int) modelPt.getY();
        int col = (int) modelPt.getX();

        features.handleCellClick(row, col);
      } catch (NoninvertibleTransformException ex) {
        throw new RuntimeException("Transform could not be inverted", ex);
      }
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
  }
}
