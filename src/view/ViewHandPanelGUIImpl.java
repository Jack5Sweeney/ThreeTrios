package view;

import controller.Features;
import model.ICard;
import model.PlayerColor;
import model.Direction;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Implementation of the {@link IViewHandsPanelGUI} interface, representing the GUI panel
 * displaying a player's hand of cards. Each card is represented as a separate panel within
 * this container, with interaction capabilities for each card.
 */
public class ViewHandPanelGUIImpl extends JPanel implements IViewHandsPanelGUI {
  private Features features;
  private final ArrayList<ICard> cards;

  /**
   * Constructs a {@code ViewHandPanelGUIImpl} with the given list of cards, organizing
   * them in a vertical layout within individual panels.
   *
   * @param cards the list of cards to be displayed in this panel
   */
  public ViewHandPanelGUIImpl(ArrayList<ICard> cards) {
    this.cards = cards;
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    createCardPanels();
  }

  /**
   * Adds a click listener to handle user interactions with the cards in the panel.
   *
   * @param features the {@link Features} interface for handling card click events
   */
  @Override
  public void addClickListener(Features features) {
    this.features = features;
  }

  /**
   * Creates and adds individual card panels to represent each card in the player's hand.
   */
  private void createCardPanels() {
    for (int i = 0; i < cards.size(); i++) {
      ICard card = cards.get(i);
      CardPanel cardPanel = new CardPanel(card, i);
      this.add(cardPanel);
    }
  }

  /**
   * A JPanel representing an individual card in the hand, displaying its directional
   * values and player color. This panel is clickable, triggering events via the
   * {@link Features} interface when selected.
   */
  private class CardPanel extends JPanel {
    private final ICard card;
    private final int index;

    /**
     * Constructs a {@code CardPanel} to display an individual card's directional values
     * and color. Sets the background color based on the player's color and adds a mouse
     * listener for click events.
     *
     * @param card  the card to display
     * @param index the index of the card within the player's hand
     */
    public CardPanel(ICard card, int index) {
      this.card = card;
      this.index = index;

      // Set background color based on player color
      PlayerColor playerColor = card.getPlayerColor();
      this.setBackground(playerColor == PlayerColor.RED ? Color.PINK : Color.CYAN);

      // Set preferred size and add a border
      this.setBorder(BorderFactory.createLineBorder(Color.BLACK));

      // Add a mouse listener to detect clicks
      this.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
          if (features != null) {
            features.handleCardClick(index); // Notify Features about the card click
          }
        }
      });
    }

    /**
     * Paints the card panel, displaying directional values around the center
     * and adjusting text placement to account for resizing.
     *
     * @param g the {@code Graphics} object used for painting
     */
    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      Graphics2D g2d = (Graphics2D) g;
      g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      g2d.setFont(new Font("SansSerif", Font.BOLD, 14));  // Set font for text display
      g2d.setColor(Color.BLACK);

      // Get directional values
      String northValue = card.getDirectionsAndValues().get(Direction.NORTH).toString();
      String eastValue = card.getDirectionsAndValues().get(Direction.EAST).toString();
      String southValue = card.getDirectionsAndValues().get(Direction.SOUTH).toString();
      String westValue = card.getDirectionsAndValues().get(Direction.WEST).toString();

      // Calculate the center of the panel
      int centerX = getWidth() / 2;
      int centerY = getHeight() / 2;

      // Calculate relative offsets for each direction
      int verticalOffset = getHeight() / 4;  // Offset for north/south
      int horizontalOffset = getWidth() / 4; // Offset for east/west

      // Draw each directional value around the center
      g2d.drawString(northValue, centerX - g2d.getFontMetrics().stringWidth(northValue) / 2, centerY - verticalOffset);
      g2d.drawString(eastValue, centerX + horizontalOffset - g2d.getFontMetrics().stringWidth(eastValue) / 2, centerY);
      g2d.drawString(southValue, centerX - g2d.getFontMetrics().stringWidth(southValue) / 2, centerY + verticalOffset);
      g2d.drawString(westValue, centerX - horizontalOffset - g2d.getFontMetrics().stringWidth(westValue) / 2, centerY);
    }
  }
}
