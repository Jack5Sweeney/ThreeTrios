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


  /**
   * Constructor for the adapter.
   *
   * @param providedView the provided IGUIView instance.
   */
  public IGUIViewToIViewFrameGUIAdapter(IGUIView providedView) {
    this.providedView = providedView;
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
    providedView.addListener(new FeaturesToPlayerActionListener(features));
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
    // Provider does not enable/disable interactions
  }

  @Override
  public void disableInteractions() {
    // Provider does not enable/disable interactions
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
