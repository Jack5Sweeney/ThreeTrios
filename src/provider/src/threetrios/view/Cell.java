package provider.src.threetrios.view;

import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputAdapter;

import java.awt.Color;

import provider.src.threetrios.model.Card;
import provider.src.threetrios.model.CellType;
import provider.src.threetrios.model.TeamColor;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.BasicStroke;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;


/**
 * A cell is what makes up players hands as well as the board of the GUI.
 */
public class Cell extends JPanel implements ICell {

  private final int row;
  private final int col;
  private static Cell highLightedCell = null;
  private Card card;
  boolean isRedsTurn;
  CellType cellType;
  TeamColor color;

  /**
   * Make a cell that you can place on your board.
   *
   * @param row      is the intended row of a cell.
   * @param col      is the intended col of a cell.
   * @param cellType is the intended type of cell.
   * @param card     is the card at the cell, will be null when game first starts.
   */
  public Cell(int row, int col, CellType cellType, Card card, boolean isRedsTurn, TeamColor color) {
    this.card = card;
    this.row = row;
    this.col = col;
    this.cellType = cellType;
    this.isRedsTurn = isRedsTurn;
    this.color = color;

    // Set background color based on cell type
    if (cellType == CellType.CARDCELL) {
      // cardcells should be yellow
      setBackground(new Color(253, 253, 150));

    } else if (cellType == CellType.HOLE) {
      // holes should be dark grey
      setBackground(new Color(59, 59, 59));

    } else if (cellType == CellType.CARD) {
      // cards should be either pastel blue or pastel red, depending on what team they belong to
      if (card.getColor().equals(TeamColor.RED)) {
        setBackground(new Color(255, 202, 202));
      } else if (card.getColor().equals(TeamColor.BLUE)) {
        setBackground(new Color(174, 198, 225));
      }

      // use border layout so attack values can easily be placed on the top/bottom and left/right
      this.setLayout(new BorderLayout());

      JLabel northLabel = new JLabel(Integer.toString(card.getNorthValue()), SwingConstants.CENTER);
      northLabel.setFont(new Font("Arial", Font.PLAIN, 27));
      add(northLabel, BorderLayout.NORTH);

      JLabel southLabel = new JLabel(Integer.toString(card.getSouthValue()), SwingConstants.CENTER);
      southLabel.setFont(new Font("Arial", Font.PLAIN, 27));
      add(southLabel, BorderLayout.SOUTH);

      JLabel eastLabel = new JLabel(Integer.toString(card.getEastValue()));
      eastLabel.setFont(new Font("Arial", Font.PLAIN, 27));
      add(eastLabel, BorderLayout.EAST);

      JLabel westLabel = new JLabel(Integer.toString(card.getWestValue()));
      westLabel.setFont(new Font("Arial", Font.PLAIN, 27));
      add(westLabel, BorderLayout.WEST);
    }

    // Set consistent cell size, for hand
    setPreferredSize(new Dimension(100, 100));

    // Add a border for better visual separation
    // https://stackoverflow.com/questions/29778456/set-border-thickness-on-jpanels
    setBorder(BorderFactory.createLineBorder(Color.BLACK));

    // Add listeners
    MouseEventListener listener = new MouseEventListener();
    this.addMouseListener(listener);
  }

  @Override
  public Card getCard() {
    return this.card;
  }

  private class MouseEventListener extends MouseInputAdapter {

    @Override
    public void mousePressed(MouseEvent e) {
      if (cellType == CellType.CARDCELL || cellType == CellType.HOLE ||
              (((card.getColor() == TeamColor.RED && isRedsTurn) ||
                      (card.getColor() == TeamColor.BLUE && !isRedsTurn))
                      && color == card.getColor())) {

        // if you are a cardcell or a hole or it is your teams turn, then you can be highlighted

        if (highLightedCell == null) {
          // if there was no highlighted cell before, mark this as the highlighted(Selected) cell
          highLightedCell = Cell.this;
          highLightedCell.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(5.0f)));

        } else if (highLightedCell.equals(Cell.this)) {
          // if you reclicked on an already highlighted cell, unhighlight it
          setBorder(BorderFactory.createLineBorder(Color.BLACK));
          highLightedCell = null;

        } else {

          // you click on a new cell to highlight, after clicking on an old one
          highLightedCell.setBorder(BorderFactory.createLineBorder(Color.BLACK));

          highLightedCell = Cell.this;

          // https://stackoverflow.com/questions/29778456/set-border-thickness-on-jpanels
          highLightedCell.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(5.0f)));

        }

        System.out.println("Cords: (" + row + ", " + col + ")");

        if (card != null) {
          System.out.println("Team of card clicked on: " + card.getColor());
        }
      } else {
        JOptionPane.showMessageDialog(null, "Invalid selection attempt!");
      }
    }
  }
}
