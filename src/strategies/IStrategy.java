package strategies;

import model.ModelImpl;
import model.IModel;
import model.PlayerImpl;

public interface IStrategy {

  Placement chooseMove(IModel model, PlayerImpl player);

}
