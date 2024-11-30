package provider.src.threetrios.view;

import threetrios.model.Card;

/**
 * Interface for a cell implementation.
 */
public interface ICell {

  /**
   * Return whatever card a cell contains.
   * @return whatever card a cell contains.
   */
  public Card getCard();
}
