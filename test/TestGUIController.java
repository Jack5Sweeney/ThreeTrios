import controller.*;

import gameConfiguration.ConfigGame;
import model.*;
import org.junit.Before;
import org.junit.Test;
import view.ITextView;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.fail;

/**
 * Test class for the ControllerImpl class.
 */
public class TestGUIController {

  private ControllerGUIImpl controller;
  private ITextView view;
  private IModel model;
  private ReadOnlyIModel readOnlyModel;

  private PlayerImpl redPlayer;
  private PlayerImpl bluePlayer;
  private ArrayList<IPlayer> players;
  private ConfigGame gameConfigurator;


  @Before
  public void setUp() {
    redPlayer = new PlayerImpl(PlayerColor.RED, new ArrayList<>());
    bluePlayer = new PlayerImpl(PlayerColor.BLUE, new ArrayList<>());
    players = new ArrayList<>(List.of(redPlayer, bluePlayer));

    controller = new ControllerGUIImpl(readOnlyModel);
  }

}
