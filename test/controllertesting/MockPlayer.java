package controllertesting;

import java.util.ArrayList;

import card.ICard;
import player.IPlayer;
import player.PlayerColor;
import strategies.Placement;

/**
 * Mock implementation of the IPlayer interface for testing purposes.
 */
public class MockPlayer implements IPlayer {

  private final PlayerColor color;
  private Placement mockPlacement; // Simulates the result of chooseMove

  public MockPlayer(PlayerColor color) {
    this.color = color;
  }

  public void setMockPlacement(Placement placement) {
    this.mockPlacement = placement;
  }

  @Override
  public PlayerColor getPlayerColor() {
    return color;
  }

  @Override
  public ArrayList<ICard> getHand() {
    return null;
  }

  @Override
  public void addToHand(ICard card) {

  }

  @Override
  public ICard removeFromHand(int index) {
    return null;
  }

  @Override
  public Placement chooseMove(model.IModel model) {
    return mockPlacement; // Return the pre-set placement
  }
}
