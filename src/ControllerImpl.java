import gameConfiguration.ConfigGame;

public class ControllerImpl implements IController {

  IView view;
  IModel model;

  public ControllerImpl(IView view, IModel model) {
    this.view = view;
    this.model = model;
  }

  @Override
  public void playGame(String board, String cardDB) {
    ConfigGame gameConfiguration = new ConfigGame(board, cardDB);
    model.startGame(gameConfiguration.getBoard(), gameConfiguration.getDeck());
  }
}
