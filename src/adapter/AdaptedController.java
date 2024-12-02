package adapter;

import controller.ControllerGUIImpl;
import model.IModel;
import player.IPlayer;
import provider.src.threetrios.controller.PlayerActionsListener;
import provider.src.threetrios.view.Cell;
import view.IViewFrameGUI;

/**
 * Adapter controller that bridges your Features interface and the provider's PlayerActionsListener.
 */
public class AdaptedController extends ControllerGUIImpl implements PlayerActionsListener {

  public AdaptedController(IModel model, IViewFrameGUI view, IPlayer player) {
    super(view, model, player);
  }

  @Override
  public void onPlaceCard(int row, int col, Cell cell) {
    // Delegate the action to the appropriate Features method
    this.handleCellClick(row, col);
  }
}
