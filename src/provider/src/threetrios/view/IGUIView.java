package provider.src.threetrios.view;


import threetrios.controller.PlayerActionsListener;
import threetrios.model.Card;
import threetrios.model.TeamColor;

/**
 * Interface for our GUI playable version of ThreeTrios.
 */
public interface IGUIView {

  /**
   * Make our board and players hands visible on the screen after we set them up.
   */
  void setVisible();

  /**
   * Update our selected card tracker for possible card placement.
   *
   * @param card is the card you want to mark as selected.
   */
  void setSelectedCard(Card card);

  /**
   * Get our currently selected card tracer for possible card placement.
   *
   * @return our currently selected card tracer for possible card placement.
   */
  Card getSelectedCard();

  /**
   * If you click on something thas is not a card(hole or cardcell), then you are not highlighting a
   * card, and your tracker should be set to null.
   */
  void clearSelectedCard();

  /**
   * Return a handpanel that represents reds current handpanel of cards as cells.
   *
   * @return a handpanel that represents reds current handpanel of cards as cells.
   */
  HandPanel getRedHandPanel();

  /**
   * Return a handpanel that represents blues current handpanel of cards as cells.
   *
   * @return a handpanel that represents reds current handpanel of cards as cells.
   */
  HandPanel getBlueHandPanel();

  void addListener(PlayerActionsListener listener);

  void updateListeners(int row, int col, Cell cell);

  void refresh();

  void setColor(TeamColor color);
}
