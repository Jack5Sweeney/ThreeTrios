package Mocks;

import model.ICard;
import model.IPlayer;
import model.PlayerColor;

import java.util.ArrayList;

// MockPlayer implementation for IPlayer interface
public class MockPlayer implements IPlayer {
  private final PlayerColor color;

  public MockPlayer(PlayerColor color) {
    this.color = color;
  }

  @Override
  public PlayerColor getPlayerColor() {
    return color;
  }

  @Override
  public ArrayList<ICard> getHand() {
    return new ArrayList<>();
  }

  @Override
  public void addToHand(ICard card) {
    // No action needed for addToHand in the mock
  }

  @Override
  public ICard removeFromHand(int index) {
    return null; // Stubbed response
  }
}