package adapter;

import model.IModel;
import player.IPlayer;
import provider.src.threetrios.controller.PlayerActionsListener;
import view.IViewFrameGUI;
import provider.src.threetrios.view.IGUIView;
import controller.Features;
import card.ICard;
import player.PlayerColor;

import java.util.List;

/**
 * Object Adapter to bridge between IViewFrameGUI and IGUIView.
 */
public class IGUIViewToIViewFrameGUIAdapter implements IViewFrameGUI {
  private final IGUIView providedView;
  private final IModel model;
  private final IPlayer player;


  /**
   * Constructor for the adapter.
   *
   * @param providedView the provided IGUIView instance.
   */
  public IGUIViewToIViewFrameGUIAdapter(IGUIView providedView, IModel model, IPlayer player ) {
    this.providedView = providedView;
    this.model = model;
    this.player = player;
  }

  @Override
  public void refresh() {
    providedView.refresh();
  }

  @Override
  public void makeVisible() {
    providedView.setVisible();
  }

  @Override
  public void addFeatures(Features features) {
    // Create an adapted controller to bridge Features to PlayerActionsListener
    PlayerActionsListener adaptedListener = new ControllerToPlayerActionListenerAdapter(model, this, player, features);
    providedView.addListener(adaptedListener);
  }

  @Override
  public void highlightCard(int row, PlayerColor color) {
    // Highlighting is handled in providers implementation
  }

  @Override
  public void updateBoard(ICard[][] boardWithCard) {
    providedView.refresh();
  }

  @Override
  public void enableInteractions() {
  }

  @Override
  public void disableInteractions() {
  }

  @Override
  public void showErrorMessage(String message) {
  }

  @Override
  public void showGameOver(String message) {
  }

  @Override
  public void setTitle(String title) {
  }

  @Override
  public void bringToFront() {
  }

  @Override
  public void refreshHands(List<ICard> redHand, List<ICard> blueHand) {
    providedView.refresh();
  }
}
