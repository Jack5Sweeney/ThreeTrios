import java.util.HashMap;
import java.util.Map;

/**
 * Represents a Card that is associated with a player and holds directional values for four
 * cardinal directions (north, east, south, west).
 */
public class Card {

  private final Map<Direction, DirectionValue> directionsAndValues;
  private final PlayerColor playerColor;
  private final String name;

  /**
   * Constructs a Card with the given player and directional values.
   *
   * @param playerColor     the color of the player to whom the card belongs (cannot be null)
   * @param northValue the value assigned to the north direction (cannot be null)
   * @param eastValue  the value assigned to the east direction (cannot be null)
   * @param southValue the value assigned to the south direction (cannot be null)
   * @param westValue  the value assigned to the west direction (cannot be null)
   * @throws IllegalArgumentException if any of the parameters are null
   */
  public Card(PlayerColor playerColor, String name,  DirectionValue northValue,
              DirectionValue eastValue, DirectionValue southValue, DirectionValue westValue) {
    if (playerColor == null || northValue == null || eastValue == null || southValue == null
        || westValue == null) {
      throw new IllegalArgumentException("You cannot construct a card without all of the proper " +
          "parameters.");
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
   * Gets the player color associated with this card.
   *
   * @return the player color
   */
  public PlayerColor getPlayerColor() {
    return playerColor;
  }

  /**
   * Returns a copy of the name associated with this object.
   *
   * @return a new String representing the name of this object
   */
  public String getName() {
    return new String(this.name);
  }


  /**
   * Gets a copy of directions and their corresponding values.
   *
   * @return a copy map of directions and direction values
   */
  public Map<Direction, DirectionValue> getDirectionsAndValues() {
    return new HashMap<>(directionsAndValues);
  }

  @Override
  public String toString() {
    return  this.name + " " +
            this.directionsAndValues.get(Direction.NORTH).toString()
            + " " +
            this.directionsAndValues.get(Direction.EAST).toString()
            + " " +
            this.directionsAndValues.get(Direction.SOUTH).toString()
            + " " +
            this.directionsAndValues.get(Direction.WEST).toString();
  }

}
