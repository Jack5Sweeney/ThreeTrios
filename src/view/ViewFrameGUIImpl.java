package view;

import controller.Features;
import model.ICard;
import model.IPlayer;
import model.PlayerColor;
import model.ReadOnlyIModel;

import javax.swing.*;
import java.awt.*;

/**
 * Implementation of the {@link IViewFrameGUI} interface, representing the main game window for the GUI.
 * This frame displays the game board in the center, with panels for each player's hand on the sides.
 * It also facilitates user interactions by connecting feature listeners to each panel.
 */
public class ViewFrameGUIImpl extends JFrame implements IViewFrameGUI {

  private final ViewHandPanelGUIImpl redHandPanel;
  private final ViewHandPanelGUIImpl blueHandPanel;
  private final ViewBoardPanelGUIImpl boardPanel;

  private JPanel highlightedCardPanelGUIImpl;

  /**
   * Constructs a {@code ViewFrameGUIImpl} with the specified readOnlyModel, initializing the panels for
   * the game board and player hands and setting up the layout of the main window.
   *
   * @param readOnlyModel the game readOnlyModel providing data for the board and players' hands
   */
  public ViewFrameGUIImpl(ReadOnlyIModel readOnlyModel) {
    this.redHandPanel = new ViewHandPanelGUIImpl(readOnlyModel.getRedPlayer().getHand());
    this.blueHandPanel = new ViewHandPanelGUIImpl(readOnlyModel.getBluePlayer().getHand());
    this.boardPanel = new ViewBoardPanelGUIImpl(
        readOnlyModel.getBoard().length,
        readOnlyModel.getBoard()[0].length,
        readOnlyModel.getBoardAvailability()
    );

    this.setLayout(new BorderLayout());
    this.setSize(800, 600);
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);

    this.add(redHandPanel, BorderLayout.WEST);
    this.add(boardPanel, BorderLayout.CENTER);
    this.add(blueHandPanel, BorderLayout.EAST);

    redHandPanel.setPreferredSize(new Dimension(75, 600));
    blueHandPanel.setPreferredSize(new Dimension(75, 600));
  }

  /**
   * Refreshes the view by repainting the frame to reflect any changes in the game state.
   */
  @Override
  public void refresh() {
    this.repaint();
  }

  /**
   * Makes the view visible, allowing the game session to start and be displayed to the user.
   */
  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  /**
   * Adds feature listeners to the red and blue hand panels as well as the board panel, enabling
   * interaction handling by the controller. Also requests focus to capture keyboard events.
   *
   * @param features the {@link Features} interface for handling user actions
   */
  @Override
  public void addFeatures(Features features) {
    redHandPanel.addClickListener(features);
    blueHandPanel.addClickListener(features);
    boardPanel.addClickListener(features);

    // Request focus for keyboard events
    this.setFocusable(true);
    this.requestFocus();
  }

  /**
   * Highlights a card belonging to a given player at a specified index (row in the hand).
   * If another card is already highlighted, it will be de-highlighted first by removing
   * its border.
   *
   * @param row   the row (index) of the card in the player's hand (0-based index)
   * @param color the {@link PlayerColor} indicating which player's hand contains the card
   */
  @Override
  public void highlightCard(int row, PlayerColor color) {
    // Determine the correct hand panel based on the player's color
    ViewHandPanelGUIImpl handPanel = (color == PlayerColor.RED) ? redHandPanel : blueHandPanel;

    // Ensure the index is within bounds
    if (row >= 0 && row < handPanel.getComponentCount()) {
      JPanel CardPanelGUIImpl = (JPanel) handPanel.getComponent(row);

      // Remove the border from the previously highlighted card, if any
      if (highlightedCardPanelGUIImpl != null && highlightedCardPanelGUIImpl != CardPanelGUIImpl) {
        highlightedCardPanelGUIImpl.setBorder(null);  // Remove the highlight border
      }

      // Add a yellow border to the new card
      CardPanelGUIImpl.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));

      // Update the reference to the currently highlighted card
      highlightedCardPanelGUIImpl = CardPanelGUIImpl;

      // Refresh the panel to apply changes
      CardPanelGUIImpl.repaint();
    }
  }

  @Override
  public void updateBoard(ICard[][] boardWithCard) {
    int rows = boardWithCard.length;
    int cols = boardWithCard[0].length;

    // Iterate over each cell in the boardWithCard array
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        ICard card = boardWithCard[row][col];

        // Find the component at this location in the grid
        int componentIndex = row * cols + col;
        Component currentComponent = boardPanel.getComponent(componentIndex);

        if (card != null) {
          // If there's a card, replace the current component with a CardPanelGUIImpl
          CardPanelGUIImpl CardPanelGUIImpl = new CardPanelGUIImpl(card, -1); // Use -1 if index is irrelevant for the board
          CardPanelGUIImpl.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

          // Replace the current component with the new CardPanelGUIImpl
          boardPanel.remove(componentIndex);
          boardPanel.add(CardPanelGUIImpl, componentIndex);
        } else {
          // If there's no card, ensure the cell is a blank JPanel
          if (currentComponent instanceof CardPanelGUIImpl) {
            JPanel emptyCell = new JPanel();
            emptyCell.setBackground(Color.LIGHT_GRAY);
            emptyCell.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

            // Replace the current component with an empty cell
            boardPanel.remove(componentIndex);
            boardPanel.add(emptyCell, componentIndex);
          }
        }
      }
    }

    // Revalidate and repaint the board panel to reflect the updates
    boardPanel.revalidate();
    boardPanel.repaint();
  }

  @Override
  public void updateHand(int cardIndexToPlace, IPlayer playerPlacing) {
  }
}

