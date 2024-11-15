package view;

import controller.Features;
import model.ICard;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;
import java.awt.Component;
import java.util.ArrayList;
import java.awt.Dimension;
import java.awt.Color;

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
    createCardPanelGUIImpls();
  }

  /**
   * Adds a click listener to handle user interactions with the cards in the panel.
   *
   * @param features the {@link Features} interface for handling card click events
   */
  @Override
  public void addClickListener(Features features) {
    this.features = features;

    // Update each CardPanelGUIImpl with the Features instance
    for (int i = 0; i < getComponentCount(); i++) {
      if (getComponent(i) instanceof CardPanelGUIImpl) {
        CardPanelGUIImpl cardPanelGUIImpl = (CardPanelGUIImpl) getComponent(i);
        cardPanelGUIImpl.setFeatures(features);
      }
    }
  }

  /**
   * Creates and adds individual card panels to represent each card in the player's hand.
   */
  private void createCardPanelGUIImpls() {
    for (int i = 0; i < cards.size(); i++) {
      ICard card = cards.get(i);
      if (card != null) {
        CardPanelGUIImpl cardPanel = new CardPanelGUIImpl(card, i);
        cardPanel.setFeatures(features);
        this.add(cardPanel);
      } else {
        // Add an empty panel to represent the missing card
        JPanel emptyPanel = new JPanel();
        emptyPanel.setPreferredSize(new Dimension(100, 20)); // Adjust size as needed
        emptyPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        this.add(emptyPanel);
      }
    }
  }

  /**
   * Removes the card at the specified index from the hand, setting the card reference
   * at that index to {@code null}. This method clears and rebuilds the panel to
   * reflect the change, including empty spots for removed cards.
   *
   * @param index the index of the card to remove in the hand
   */
  public void removeCardAtIndex(int index) {
    // Set the card at the specified index to null
    cards.set(index, null);

    // Remove all components from the panel
    this.removeAll();

    // Recreate the card panels, including empty spots for nulls
    createCardPanelGUIImpls();
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
}
