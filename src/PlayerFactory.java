import player.AIPlayerImpl;
import player.IPlayer;
import player.PlayerColor;
import player.PlayerImpl;
import strategies.CornerStrategy;
import strategies.FlipTheMostStrategy;

import java.util.ArrayList;

public class PlayerFactory {
  /**
   * Creates a player based on the type and color.
   *
   * @param type  the player type ('human', 'strategy1', etc.)
   * @param color the color of the player (RED or BLUE)
   * @return the configured player, or null if the type is invalid
   */
  public IPlayer createPlayer(String type, PlayerColor color) {
    switch (type.toLowerCase()) {
      case "human":
        return new PlayerImpl(color, new ArrayList<>());
      case "strategy1":
        return new AIPlayerImpl(new FlipTheMostStrategy(), color, new ArrayList<>());
      case "strategy2":
        return new AIPlayerImpl(new CornerStrategy(), color, new ArrayList<>());
      default:
        throw new IllegalArgumentException("Invalid player type: " + type);
    }
  }
}
