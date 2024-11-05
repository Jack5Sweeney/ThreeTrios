import controller.*;

import model.*;
import org.junit.Before;
import org.junit.Test;
import view.IView;
import view.ViewImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.fail;

/**
 * Test class for the ControllerImpl class.
 */
public class TestController {

  private IController controller;
  private IView view;
  private IModel model;

  private PlayerImpl redPlayer;
  private PlayerImpl bluePlayer;
  private ArrayList<IPlayer> players;


  @Before
  public void setUp() {
    redPlayer = new PlayerImpl(PlayerColor.RED, new ArrayList<>());
    bluePlayer = new PlayerImpl(PlayerColor.BLUE, new ArrayList<>());
    players = new ArrayList<>(List.of(redPlayer, bluePlayer));

    controller = new ControllerImpl();
  }

  @Test
  public void testModelCatchesErrorWithInvalidFBoardileName() {
    try {
      controller.playGame("playBoard.config", "simpleCard.database", model, players);
      fail("PlayBoard is not a valid name");
    } catch (IllegalArgumentException e) {
      //successfully caught IllegalArgumentException
    }
  }

  @Test
  public void testModelCatchesErrorWithInvalidCardFileName() {
    try {
      controller.playGame("board.config", "simpleCrd.database", model, players);
      fail("PlayBoard is not a valid name");
    } catch (IllegalArgumentException e) {
      //successfully caught IllegalArgumentException
    }
  }

  @Test
  public void testModelCatchesErrorWithInvalidFormat() {
    try {
      controller.playGame("invalidBoard.config", "simpleCard.database", model, players);
      fail("Invalid board format");
    } catch (IllegalArgumentException e) {
      //successfully caught IllegalArgumentException
    }
  }

  @Test
  public void testModelCatchesErrorWithEmptyFile() {
    try {
      controller.playGame("emptyFile.config", "simpleCard.database", model, players);
      fail("Invalid board format");
    } catch (IllegalArgumentException e) {
      //successfully caught IllegalArgumentException
    }
  }

  @Test
  public void testModelCatchesErrorInvalidCardFormat() {
    try {
      controller.playGame("board.config", "invalidFormatCard.database", model, players);
      fail("Invalid board format");
    } catch (IllegalArgumentException e) {
      //successfully caught IllegalArgumentException
    }
  }
}
