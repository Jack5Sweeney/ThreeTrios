package model;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a card associated with a player that holds directional values for four
 * cardinal directions (north, east, south, west), as well as a unique name for identification.
 */
public class CardImpl implements ICard {

  private final Map<Direction, DirectionValue> directionsAndValues;
  private PlayerColor playerColor;
  private final String name;

  /**
   * Constructs a {@code CardImpl} with a specified player color, name, and directional values
   * for each of the four cardinal directions.
   *
   * @param playerColor the color of the player to whom the card belongs (cannot be null)
   * @param name        the unique name associated with this card
   * @param northValue  the value assigned to the north direction (cannot be null)
   * @param eastValue   the value assigned to the east direction (cannot be null)
   * @param southValue  the value assigned to the south direction (cannot be null)
   * @param westValue   the value assigned to the west direction (cannot be null)
   * @throws IllegalArgumentException if any of the parameters are null
   */
  public CardImpl(PlayerColor playerColor, String name, DirectionValue northValue,
                  DirectionValue eastValue, DirectionValue southValue, DirectionValue westValue) {
    if (playerColor == null || northValue == null || eastValue == null || southValue == null
        || westValue == null) {
      throw new IllegalArgumentException("All parameters must be non-null to construct a card.");
    }
    this.playerColor = playerColor;
    this.name = name;
    this.directionsAndValues = new HashMap<>();
    directionsAndValues.put(Direction.NORTH, northValue);
    directionsAndValues.put(Direction.EAST, eastValue);
    directionsAndValues.put(Direction.SOUTH, southValue);
    directionsAndValues.put(Direction.WEST, westValue);
  }

  /**
   * Gets a copy of the color of the player associated with this card.
   *
   * @return the {@link PlayerColor} of the player who owns this card
   */
  @Override
  public PlayerColor getPlayerColor() {
    if (playerColor == PlayerColor.RED) {
      return PlayerColor.RED;
    }
    else {
      return PlayerColor.BLUE;
    }
  }

  /**
   * Retrieves a copy of the name associated with this card.
   *
   * @return a new {@code String} representing the name of this card
   */
  public String getName() {
    return new String(this.name);
  }

  /**
   * Provides a copy of the map containing directions and their corresponding values for this card.
   *
   * @return a new {@code Map} of {@link Direction} to {@link DirectionValue} representing
   *         the direction values of this card
   */
  public Map<Direction, DirectionValue> getDirectionsAndValues() {
    return new HashMap<>(directionsAndValues);
  }

  /**
   * Returns a string representation of the card, including its name and the values
   * of each cardinal direction.
   *
   * @return a formatted {@code String} representation of this card
   */
  @Override
  public String toString() {
    return this.name + " " +
        this.directionsAndValues.get(Direction.NORTH).toString() + " " +
        this.directionsAndValues.get(Direction.EAST).toString() + " " +
        this.directionsAndValues.get(Direction.SOUTH).toString() + " " +
        this.directionsAndValues.get(Direction.WEST).toString();
  }


  /**
   * Checks if this card is equal to another object. Two cards are considered equal if they
   * are both instances of {@link ICard} and have the same directional values and name.
   *
   * @param o the object to compare with this card
   * @return {@code true} if the specified object is an {@link ICard} with identical directional
   *         values and name, {@code false} otherwise
   */
  @Override
  public boolean equals(Object o) {
    if (!(o instanceof ICard)) {
      return false;
    } else {
      ICard otherCard = (ICard) o;
      return this.directionsAndValues.equals(otherCard.getDirectionsAndValues())
          && this.name.equals(otherCard.getName());
    }
  }

}
