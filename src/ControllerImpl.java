public class ControllerImpl implements IController {

  IView view;
  IModel model;

  public ControllerImpl(IView view, IModel model) {
    this.view = view;
    this.model = model;
  }
  @Override
  public void configFiles() {

  }

  @Override
  public void playGame() {

  }
}
