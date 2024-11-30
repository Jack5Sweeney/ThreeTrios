package provider.src.threetrios.view;

import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.event.MouseInputAdapter;
import javax.swing.JPanel;
import threetrios.model.Card;
import threetrios.model.CellType;
import threetrios.model.TeamColor;

import javax.swing.BoxLayout;

/**
 * A hand panel is used to hold all the cells that make up a players hand in the gui.
 */
public class HandPanel extends JPanel implements IHandPanel {

  private GUIView guiView;
  boolean isRedsTurn;
  TeamColor color;

  /**
   * Construct a hand panel with a list of cards and a tie to the guiview.
   * @param hand is the hand to make the panel from.
   * @param guiView is a tie to the guiview.
   */
  public HandPanel(List<Card> hand, GUIView guiView, boolean isRedsTurn, TeamColor color) {
    this.guiView = guiView;
    this.isRedsTurn = isRedsTurn;
    this.color = color;

    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    int counter = 0;

    for (Card card : hand) {

      // the hand is in a single col (0), and they are gaurunteed to be a card
      Cell cardCell = new Cell(counter, 0, CellType.CARD, card, isRedsTurn, color);

      CellMouseListener listener = new CellMouseListener(card);

      cardCell.addMouseListener(listener);
      this.add(cardCell);

      counter++;
    }
  }

  // Inner class for handling mouse events
  private class CellMouseListener extends MouseInputAdapter {
    Card card;

    public CellMouseListener(Card card) {
      this.card = card;
    }

    @Override
    public void mousePressed(MouseEvent e) {
      if ( ((card.getColor() == TeamColor.RED && isRedsTurn) ||
              (card.getColor() == TeamColor.BLUE && !isRedsTurn)) ) {

        // this is what does the selecting of a card to be placed
        guiView.setSelectedCard(card);
        System.out.println("Selected card: " + card);
      }
    }

  }
}
