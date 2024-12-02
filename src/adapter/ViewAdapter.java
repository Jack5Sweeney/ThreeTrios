package adapter;

import view.IViewFrameGUI;
import provider.src.threetrios.view.IGUIView;
import controller.Features;
import card.ICard;
import player.PlayerColor;
import provider.src.threetrios.model.Card;
import provider.src.threetrios.model.TeamColor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Adapter class to bridge between IViewFrameGUI and IGUIView.
 */
public class ViewAdapter implements IViewFrameGUI {
  private final IGUIView providedView;

  /**
   * Constructor for the adapter.
   *
   * @param providedView the provided IGUIView instance.
   */
  public ViewAdapter(IGUIView providedView) {
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
    // Translate Features to the equivalent PlayerActionsListener
    providedView.addListener(features); // Adjust method mapping if needed.
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
