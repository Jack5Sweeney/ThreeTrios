package adapter;

import controller.Features;
import provider.src.threetrios.controller.PlayerActionsListener;
import provider.src.threetrios.view.Cell;

public class FeaturesToPlayerActionListener implements PlayerActionsListener {
  private final Features features;
  public FeaturesToPlayerActionListener(Features features) {
    this.features = features;
  }
  @Override
  public void onPlaceCard(int row, int col, Cell cell) {
    features.handleCellClick(row,col);
  }
}
