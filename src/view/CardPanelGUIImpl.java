package view;

import controller.Features;
import model.Direction;
import model.ICard;
import model.PlayerColor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * A JPanel representing an individual card. Displays the card's directional values
 * and player color, and handles click events if a {@link Features} instance is provided.
 */
public class CardPanelGUIImpl extends JPanel {
  private final ICard card;
  private  int index; // For hand placement, if needed
  private Features features;

  /**
   * Constructs a {@code CardPanel} to display an individual card's directional values
   * and color. Sets the background color based on the player's color and adds a mouse
   * listener for click events.
   *
   * @param card  the card to display
   * @param index the index of the card within the player's hand or position
   */
  public CardPanelGUIImpl(ICard card, int index) {
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
          features.handleCardClick(index, card.getPlayerColor()); // Notify Features about the card click
        }
      }
    });
  }

  /**
   * Sets the {@link Features} interface for handling interactions.
   *
   * @param features the Features instance to handle user actions
   */
  public void setFeatures(Features features) {
    this.features = features;
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
