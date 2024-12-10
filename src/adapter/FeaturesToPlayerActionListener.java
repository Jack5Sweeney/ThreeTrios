package adapter;

import controller.Features;
import provider.src.threetrios.controller.PlayerActionsListener;
import provider.src.threetrios.view.Cell;

/**
 * Adapter class to bridge {@link Features} with {@link PlayerActionsListener}.
 * This allows interaction between the Features controller and the PlayerActionsListener interface.
 */
public class FeaturesToPlayerActionListener implements PlayerActionsListener {

  private final Features features;

  /**
   * Constructs an adapter with the given features implementation.
   *
   * @param features the features controller to adapt
   */
  public FeaturesToPlayerActionListener(Features features) {
    this.features = features;
  }

  /**
   * Handles the action of placing a card by delegating the row and column to
   * the {@link Features} instance.
   *
   * @param row   the row where the card is placed
   * @param col   the column where the card is placed
   * @param cell  the cell being interacted with (not used directly in this implementation)
   */
  @Override
  public void onPlaceCard(int row, int col, Cell cell) {
    features.handleCellClick(row, col);
  }
}
