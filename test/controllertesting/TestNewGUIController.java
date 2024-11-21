package controllertesting;

import org.junit.Test;

import java.util.List;

import card.CellType;
import card.ICard;
import controller.ControllerGUIImpl;

import static org.junit.Assert.assertEquals;

public class TestNewGUIController {

  @Test
  public void testControllerLogsCorrectCoordinates() {
    ICard[][] mockBoard = new ICard[3][3];
    CellType[][] mockAvailability = new CellType[3][3];
    MockModelForControllerGUI mockModel = new MockModelForControllerGUI(mockBoard, mockAvailability);
    // need to make a mock view
    // need to make mock players but we can do this by just creating players.

    ControllerGUIImpl controller = new ControllerGUIImpl(mockView, mockModel, mockPlayer);
    controller.handleCellClick(2, 3);

    // Verify coordinates were logged
    assertEquals(List.of(2, 3), mockModel.cordLog.get(0)); // Example: Controller accesses (1,1)
  }


}
