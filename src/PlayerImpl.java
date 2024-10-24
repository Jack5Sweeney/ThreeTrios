import java.util.ArrayList;

/**
 * Represents a player in the game, holding a hand of cards and associated with a specific color.
 * This class implements the {@link IPlayer} interface and provides methods to access the player's
 * color and their hand of cards.
 */
public class PlayerImpl implements IPlayer {

  /** The color representing the player (e.g., RED or BLUE). */
  private final PlayerColor playerColor;

  /** The hand of cards that the player holds. */
  private ArrayList<Card> hand;

  /**
   * Constructs a {@code PlayerImpl} with the specified player color and initial hand of cards.
   *
   * @param playerColor the color representing the player (e.g., RED or BLUE)
   * @param hand        the initial hand of cards for the player
   */
  public PlayerImpl(PlayerColor playerColor, ArrayList<Card> hand) {
    this.playerColor = playerColor;
    this.hand = hand;
  }

  /**
   * Gets the color representing this player.
   *
   * @return the {@link PlayerColor} associated with this player
   */
  @Override
  public PlayerColor getPlayerColor() {
    return this.playerColor;
  }

  /**
   * Gets the player's hand of cards.
   *
   * @return an {@link ArrayList} containing the player's cards
   */
  @Override
  public ArrayList<Card> getHand() {
    return this.hand;
  }
}
