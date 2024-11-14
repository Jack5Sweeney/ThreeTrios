package ControllerTesting;

import model.ICard;
import model.IPlayer;
import model.PlayerColor;

import java.util.ArrayList;

/**
 * A mock implementation of the {@link IPlayer} interface used for testing purposes.
 * It provides a stubbed player with a specified color and a minimal hand management system.
 */
public class MockPlayer implements IPlayer {
  private final PlayerColor color;

  /**
   * Constructs a {@code MockPlayer} with the specified color.
   *
   * @param color the color associated with this player
   */
  public MockPlayer(PlayerColor color) {
    this.color = color;
  }

  /**
   * Retrieves the color of this player.
   *
   * @return the {@link PlayerColor} of this player
   */
  @Override
  public PlayerColor getPlayerColor() {
    return color;
  }

  /**
   * Retrieves the player's hand. Always returns an empty hand in this mock implementation.
   *
   * @return an empty {@link ArrayList} of {@link ICard}
   */
  @Override
  public ArrayList<ICard> getHand() {
    return new ArrayList<>();
  }

  /**
   * Adds a card to the player's hand. No action is performed in this mock implementation.
   *
   * @param card the {@link ICard} to be added
   */
  @Override
  public void addToHand(ICard card) {
    // No action needed for addToHand in the mock
  }

  /**
   * Removes a card from the player's hand at the specified index. Always returns null in
   * this mock.
   *
   * @param index the index of the card to remove
   * @return null, as no actual hand management is implemented
   */
  @Override
  public ICard removeFromHand(int index) {
    return null; // Stubbed response
  }
}
