import java.util.HashMap;
import java.util.Map;

/**
 * Represents a Card that is associated with a player and holds directional values for four
 * cardinal directions (north, east, south, west).
 */
public class Card {

  private final Map<Direction, DirectionValue> directionsAndValues;
  private final PlayerColor player;

  /**
   * Constructs a Card with the given player and directional values.
   *
   * @param player     the color of the player to whom the card belongs (cannot be null)
   * @param northValue the value assigned to the north direction (cannot be null)
   * @param eastValue  the value assigned to the east direction (cannot be null)
   * @param southValue the value assigned to the south direction (cannot be null)
   * @param westValue  the value assigned to the west direction (cannot be null)
   * @throws IllegalArgumentException if any of the parameters are null
   */
  public Card(PlayerColor player, DirectionValue northValue, DirectionValue eastValue,
              DirectionValue southValue, DirectionValue westValue) {
    if (player == null || northValue == null || eastValue == null || southValue == null
        || westValue == null) {
      throw new IllegalArgumentException("You cannot construct a card without all of the proper parameters.");
    }
    this.player = player;
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
  public PlayerColor getPlayer() {
    return player;
  }

  /**
   * Gets the map of directions and their corresponding values.
   *
   * @return the map of directions and direction values
   */
  public Map<Direction, DirectionValue> getDirectionsAndValues() {
    return directionsAndValues;
  }
}
