package provider.src.threetrios.view;


import javax.swing.JFrame;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import provider.src.threetrios.controller.PlayerActionsListener;
import provider.src.threetrios.model.Card;
import provider.src.threetrios.model.ReadonlyThreeTriosModel;
import provider.src.threetrios.model.TeamColor;

/**
 * The guiview is the main frame that holds our board panel, and both hand panels. Its title
 * should also track the current player.
 */
public class GUIView extends JFrame implements IGUIView {

  // Tracker for the currently selected card
  private Card selectedCard;


  // View should not be able to mutate our model
  private ReadonlyThreeTriosModel model;
  private HandPanel redHandPanel;
  private HandPanel blueHandPanel;
  private BoardPanel boardPanel;
  private List<PlayerActionsListener> listeners;
  private TeamColor color;


  /**
   * Constructs the graphic user interface.
   *
   * @param model non-mutable model.
   */

  // takes in a features which has a model and view as fields
  public GUIView(ReadonlyThreeTriosModel model) {
    if (model == null) {
      throw new IllegalArgumentException("cannot instantiate a guiview with null argument");
    }

    this.color = null;
    this.listeners = new ArrayList<>();
    this.model = model;
    selectedCard = null;

    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setMinimumSize(new Dimension(550, 550));

    // We know we want to have Red hand on the left side, board in the middle, and blue on the right
    setLayout(new BorderLayout());

    // Create hand panels and add them to the sides
    HandPanel redHandLeftSide = new HandPanel(model.getRedHand(), this, model.getTurn(), color);
    redHandPanel = redHandLeftSide;
    HandPanel blueHandRightSide = new HandPanel(model.getBlueHand(), this, model.getTurn(), color);
    blueHandPanel = blueHandRightSide;

    // Create the board panel and add it to the center
    BoardPanel boardPanel = new BoardPanel(model.getBoardArray(), this, model.getTurn(), color);
    this.boardPanel = boardPanel;

    this.add(boardPanel, BorderLayout.CENTER);
    this.add(redHandLeftSide, BorderLayout.WEST);
    this.add(blueHandRightSide, BorderLayout.EAST);

    if (model.getTurn()) {
      this.setTitle("Current Player: RED");
    } else if (!model.getTurn()) {
      this.setTitle("Current Player: BLUE");
    }

    // Set window title based on current player, getTurn = true means reds turn, false means blue
    pack();
    setLocationRelativeTo(null);  // Center the window on the screen
  }

  @Override
  public void setSelectedCard(Card card) {
    this.selectedCard = card;
  }

  @Override
  public Card getSelectedCard() {
    return selectedCard;
  }

  @Override
  public void clearSelectedCard() {
    this.selectedCard = null;
  }

  @Override
  public void setVisible() {
    this.setVisible(true);
  }

  @Override
  public HandPanel getRedHandPanel() {
    return this.redHandPanel;
  }

  @Override
  public HandPanel getBlueHandPanel() {
    return this.blueHandPanel;
  }

  @Override
  public void addListener(PlayerActionsListener listener) {
    this.listeners.add(listener);
  }

  @Override
  public void updateListeners(int row, int col, Cell cell) {
    for (PlayerActionsListener listener : listeners) {
      listener.onPlaceCard(row, col, cell);
    }
  }

  @Override
  public void refresh() {

    this.remove(boardPanel);
    this.remove(redHandPanel);
    this.remove(blueHandPanel);

    redHandPanel = new HandPanel(model.getRedHand(), this, model.getTurn(), color);
    blueHandPanel = new HandPanel(model.getBlueHand(), this, model.getTurn(), color);
    boardPanel = new BoardPanel(model.getBoardArray(), this, model.getTurn(), color);

    this.add(boardPanel, BorderLayout.CENTER);
    this.add(redHandPanel, BorderLayout.WEST);
    this.add(blueHandPanel, BorderLayout.EAST);

    this.revalidate();
    this.repaint();

    // Update the window title based on the current player's turn
    if (model.getTurn()) {
      setTitle("Current Player: RED | I am player " + color);
    } else if (!model.getTurn()) {
      setTitle("Current Player: BLUE | I am player " + color);
    }
  }

  @Override
  public void setColor(TeamColor color) {
    this.color = color;
  }
}
