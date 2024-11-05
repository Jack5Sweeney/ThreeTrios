package model;

import java.util.ArrayList;

/**
 * Interface representing a player for a representation of a Triple Triad game.
 */
public interface IPlayer {

  /**
   * Gets the player's color.
   *
   * @return the color representing the player (e.g., RED or BLUE)
   */
  PlayerColor getPlayerColor();

  /**
   * Gets the player's hand of cards.
   *
   * @return the hand of cards
   */
  ArrayList<ICard> getHand();
}
