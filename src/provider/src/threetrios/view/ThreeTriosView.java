package provider.src.threetrios.view;

import java.io.IOException;
import java.util.List;

import threetrios.model.Card;
import threetrios.model.Model;

/**
 * A three trios view highlights the current player as well as a snapshot of the board. As well
 * as the current players hand. This is exclusively for console games.
 */
public class ThreeTriosView implements View {
  private final Model model;
  private Appendable appendable;


  /**
   * View.ThreeTriosView constructor sets the model and appendable fields.
   * This constructor is not utilized yet, but will be important for our full MVC.
   *
   * @param model      is the model which functionality we wish to play by.
   * @param appendable is the appendable object we are going to use to build user output.
   */
  public ThreeTriosView(Model model, Appendable appendable) {
    if (appendable == null) {
      throw new IllegalArgumentException("your appendable is null in View.ThreeTriosView");
    }
    this.model = model;
    this.appendable = appendable;
  }

  /**
   * This constructor is useful for testing before full MVC is working.
   *
   * @param model is the model which functionality we wish to play by.
   */
  public ThreeTriosView(Model model) {
    this.model = model;
    this.appendable = null;
  }


  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();

    builder.append("Player: ");

    if (model.getTurn()) {
      builder.append("RED\n");
    } else {
      builder.append("BLUE\n");
    }

    builder.append(model.getBoard());

    builder.append("Hand:\n");

    List<Card> hand;

    if (model.getTurn()) {
      hand = model.getRedHand();
    } else {
      hand = model.getBlueHand();
    }

    for (Card card : hand) {
      builder.append(card).append("\n");
    }

    return builder.toString().trim();
  }

  @Override
  public void render() throws IOException {
    if (appendable == null) {
      throw new IllegalArgumentException("cant render, appendable is null; view class");
    }

    appendable.append(this.toString());
  }
}
