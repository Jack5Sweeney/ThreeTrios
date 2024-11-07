package view;

import controller.Features;
import model.ICard;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
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
        CardPanelGUIImpl CardPanelGUIImpl = (CardPanelGUIImpl) getComponent(i);
        CardPanelGUIImpl.setFeatures(features);
      }
    }
  }

  /**
   * Creates and adds individual card panels to represent each card in the player's hand.
   */
  private void createCardPanelGUIImpls() {
    for (int i = 0; i < cards.size(); i++) {
      ICard card = cards.get(i);
      CardPanelGUIImpl CardPanelGUIImpl = new CardPanelGUIImpl(card, i);
      CardPanelGUIImpl.setFeatures(features);  // Set the Features instance if already available
      this.add(CardPanelGUIImpl);
    }
  }
}
