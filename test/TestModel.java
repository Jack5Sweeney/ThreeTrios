import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.fail;

public class TestModel {

  private IModel simpleModel;
  private IModel model;

  @Before
  public void setup() {
    model = new ModelImpl("board.config", "card.database");
    simpleModel = new ModelImpl("simpleBoard.config", "simpleCard.database");
  }

  @Test
  public void testModelCatchesErrorWithInvalidFileName() {
    try {
      IModel invalidModelName = new ModelImpl("playBoard.config", "simpleCard.database");
      invalidModelName.startGame();
      fail("PlayBoard is not a valid name");
    } catch (IllegalArgumentException e) {
      //successfully caught IllegalArgumentException
    }
  }

  @Test
  public void testModelCatchesErrorWithInvalidFileFormat() {
    try {
      IModel invalidModelConfig = new ModelImpl("invalidBoard.config", "simpleCard.database");
      invalidModelConfig.startGame();
      fail("The board config is not a valid format");
    } catch (IllegalArgumentException e) {
      //successfully caught IllegalArgumentException
    }
  }

  @Test
  public void testVModelCatchesErrorWithDuplicateCard() {
    try {
      IModel dupCardModelConfig = new ModelImpl("simpleBoard.config", "dupCard.database");
      dupCardModelConfig.startGame();
      fail("There is a duplicate card in the card database");
    } catch (IllegalArgumentException e) {
      //successfully caught IllegalArgumentException
    }
  }
}

