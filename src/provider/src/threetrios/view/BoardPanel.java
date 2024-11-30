package provider.src.threetrios.view;

import java.awt.event.MouseEvent;

import java.awt.GridLayout;

import javax.swing.event.MouseInputAdapter;
import javax.swing.JPanel;

import threetrios.model.Card;
import threetrios.model.CellType;
import threetrios.model.TeamColor;

/**
 * A board panel is the master panel that holds all the cells of the playable board. Initially
 * is filled with all cardcells and holes.
 */
public class BoardPanel extends JPanel implements IBoardPanel {
  private final int rows;
  private final int cols;
  private Object[][] boardCopy;
  private final GUIView guiView;
  private boolean redsTurn;
  private TeamColor color;



  /**
   * Make a board panel with a 2d array copy of your board and a guiview.
   * @param boardCopy is the inteded board to show.
   * @param guiView is the guiview to update.
   */
  public BoardPanel(Object[][] boardCopy, GUIView guiView, Boolean redsTurn, TeamColor color) {
    this.rows = boardCopy.length;
    this.cols = boardCopy[0].length;
    this.guiView = guiView;
    this.redsTurn = redsTurn;
    this.color = color;

    this.boardCopy = boardCopy;
    setLayout(new GridLayout(rows, cols));

    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {

        Cell cell;

        if (boardCopy[row][col] instanceof Card) {
          // if you are a card, turn into a cell
          CellMouseListener listener = new CellMouseListener(row, col, CellType.CARD);

          cell = new Cell(row, col, CellType.CARD, (Card) boardCopy[row][col], redsTurn, color);

          cell.addMouseListener(listener);
          boardCopy[row][col] = cell;
          this.add(cell);
        }
        else if (boardCopy[row][col] instanceof Cell) {
          // if you are already a cell do not do anything

        }
        else {
          // if you are a hole / cardcell
          CellType cellType = (CellType) boardCopy[row][col];

          CellMouseListener listener = new CellMouseListener(row, col, cellType);

          cell = new Cell(row, col, cellType, null, redsTurn, color);

          cell.addMouseListener(listener);
          boardCopy[row][col] = cell;
          this.add(cell);
        }
      }
    }
  }

  @Override
  public void placeCard(int row, int col, Cell cell) {

    // Update data structure, gui is till not updated
    boardCopy[row][col] = cell;

    // clear the panel, so we can update it with new value
    removeAll();

    // Re-add cells, with the new cell placed
    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        add((Cell) boardCopy[r][c]);
      }
    }



    guiView.updateListeners(row, col, cell);
    guiView.clearSelectedCard();  // Clear the selection in GUIView
  }

  // Inner class for handling mouse events
  private class CellMouseListener extends MouseInputAdapter {
    private final int row;
    private final int col;
    private final CellType cellType;

    public CellMouseListener(int row, int col, CellType cellType) {
      this.row = row;
      this.col = col;
      this.cellType = cellType;
    }

    @Override
    public void mousePressed(MouseEvent e) {
      Card selectedCard = guiView.getSelectedCard();

      if (cellType == CellType.CARDCELL && selectedCard != null) {
        // Place the selected card in the clicked cell, stops from placing on other cards/holes
        placeCard(row, col, new Cell(row, col, CellType.CARD, selectedCard, redsTurn, color));

        System.out.println("Placed card at (" + row + ", " + col + ")");
      }

    }

  }
}

