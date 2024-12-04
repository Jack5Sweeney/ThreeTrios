package adapter;

import card.Direction;
import card.ICard;
import player.PlayerColor;
import provider.src.threetrios.model.Card;
import provider.src.threetrios.model.TeamColor;

/**
 * Adapter class to bridge {@link ICard} with {@link Card}.
 * This class allows seamless interaction between different representations of a card.
 */
public class CardToICardAdapter implements Card {

  private final ICard iCard;

  /**
   * Constructs an adapter for the given {@link ICard}.
   *
   * @param card the card to adapt
   */
  public CardToICardAdapter(ICard card) {
    this.iCard = card;
  }

  /**
   * Gets the north value of the card.
   *
   * @return the north value
   */
  @Override
  public int getNorthValue() {
    return this.iCard.getDirectionsAndValues().get(Direction.NORTH).getValue();
  }

  /**
   * Gets the south value of the card.
   *
   * @return the south value
   */
  @Override
  public int getSouthValue() {
    return this.iCard.getDirectionsAndValues().get(Direction.SOUTH).getValue();
  }

  /**
   * Gets the east value of the card.
   *
   * @return the east value
   */
  @Override
  public int getEastValue() {
    return this.iCard.getDirectionsAndValues().get(Direction.EAST).getValue();
  }

  /**
   * Gets the west value of the card.
   *
   * @return the west value
   */
  @Override
  public int getWestValue() {
    return this.iCard.getDirectionsAndValues().get(Direction.WEST).getValue();
  }

  /**
   * Flips the card.
   * <p>Note: This method is not used in this implementation and thus is left unadapted.</p>
   */
  @Override
  public void flip() {
    // Not implemented as flipping is not required in this context.
  }

  /**
   * Gets the color of the card's team.
   *
   * @return the team color
   */
  @Override
  public TeamColor getColor() {
    return this.iCard.getPlayerColor().equals(PlayerColor.BLUE) ? TeamColor.BLUE : TeamColor.RED;
  }

  /**
   * Gets the name of the card.
   *
   * @return the card name
   */
  @Override
  public String getName() {
    return this.iCard.getName();
  }
}
