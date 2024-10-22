import org.junit.Before;
import org.junit.Test;

public class TestModel {

  private IModel model;

  @Before
  public void setup() {
    model = new ModelImpl("board.config1");
  }

  @Test
  public void testModelSetupCorrectRowsAndColumns() {
    model.startGame();
  }
}

