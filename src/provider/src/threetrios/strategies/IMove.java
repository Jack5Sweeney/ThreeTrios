package provider.src.threetrios.strategies;

import provider.src.threetrios.model.Card;

/**
 * Interface for a move implementation.
 */
public interface IMove {

  /**
   * Every move can calculate its distance from the top left corner (0,0). This is used often
   * for tie breaker rules with strategies.
   *
   * @return a moves distance from the top left corner (0,0).
   */
  double calcDistanceFromTopLeftCorner();

  /**
   * Return this moves row value.
   * @return this moves row value.
   */
  int getRow();

  /**
   * Return this moves col value.
   * @return this moves col value.
   */
  int getCol();

  /**
   * Return this moves card value.
   * @return this moves card value.
   */
  Card getCard();
}
