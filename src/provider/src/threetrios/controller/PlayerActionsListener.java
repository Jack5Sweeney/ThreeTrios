package provider.src.threetrios.controller;

import threetrios.view.Cell;

/**
 * Player actions listeners are used to communicate from view to controller that a visual
 * change has taken place.
 */
public interface PlayerActionsListener {

  /**
   * If a card is visually placed, report it to the controller so the model can update.
   */
  void onPlaceCard(int row, int col, Cell cell);

}
