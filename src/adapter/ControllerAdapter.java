package adapter;

import controller.ControllerGUIImpl;
import controller.Features;
import model.IModel;
import player.IPlayer;
import provider.src.threetrios.controller.PlayerActionsListener;
import provider.src.threetrios.view.Cell;
import view.IViewFrameGUI;

/**
 * Object adapter controller that bridges Features and PlayerActionsListener.
 */
public class ControllerAdapter extends ControllerGUIImpl implements PlayerActionsListener {
  private final Features features;

  public ControllerAdapter(IModel model, IViewFrameGUI view, IPlayer player, Features features) {
    super(view, model, player);
    this.features = features;
  }

  @Override
  public void onPlaceCard(int row, int col, Cell cell) {
    // Delegate to Features interface methods
    features.handleCellClick(row, col);
  }
}
