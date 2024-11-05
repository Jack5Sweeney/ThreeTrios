package model;

import java.util.Map;

/**
 * Represents a card in the game, associated with a player and directional values for four
 * cardinal directions (north, east, south, west).
 *
 * <p>Each card has a player color, name, and specific values associated with each direction.
 */
public interface ICard {

  /**
   * Gets a copy of the player color associated with this card.
   *
   * @return the {@link PlayerColor} indicating the owner of the card.
   */
  PlayerColor getPlayerColor();

  /**
   * Returns the name of this card.
   *
   * @return a {@link String} representing the card's name.
   */
  String getName();

  /**
   * Provides a map of the card's directional values.
   *
   * <p>Each direction (north, east, south, west) is mapped to its respective value.
   *
   * @return a new {@link Map} of {@link Direction} keys to {@link DirectionValue} values
   *         representing the card's directional values.
   */
  Map<Direction, DirectionValue> getDirectionsAndValues();

  /**
   * Returns a string representation of the card, displaying its name and values
   * in the order of north, east, south, and west directions.
   *
   * @return a formatted {@link String} representation of this card.
   */
  @Override
  String toString();
}
