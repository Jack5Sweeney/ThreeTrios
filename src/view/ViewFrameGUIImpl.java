package view;

import controller.Features;
import model.IModel;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Dimension;

/**
 * Implementation of the {@link IViewFrameGUI} interface, representing the main game window for the GUI.
 * This frame displays the game board in the center, with panels for each player's hand on the sides.
 * It also facilitates user interactions by connecting feature listeners to each panel.
 */
public class ViewFrameGUIImpl extends JFrame implements IViewFrameGUI {

  private final ViewHandPanelGUIImpl redHandPanel;
  private final ViewHandPanelGUIImpl blueHandPanel;
  private final ViewBoardPanelGUIImpl boardPanel;

  /**
   * Constructs a {@code ViewFrameGUIImpl} with the specified model, initializing the panels for
   * the game board and player hands and setting up the layout of the main window.
   *
   * @param model the game model providing data for the board and players' hands
   */
  public ViewFrameGUIImpl(IModel model) {
    this.redHandPanel = new ViewHandPanelGUIImpl(model.getRedPlayer().getHand());
    this.blueHandPanel = new ViewHandPanelGUIImpl(model.getBluePlayer().getHand());
    this.boardPanel = new ViewBoardPanelGUIImpl(
        model.getBoard().length,
        model.getBoard()[0].length,
        model.getBoardAvailability()
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
}
