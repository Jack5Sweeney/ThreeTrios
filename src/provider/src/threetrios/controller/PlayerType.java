package provider.src.threetrios.controller;

import threetrios.model.Model;
import threetrios.model.TeamColor;
import threetrios.strategies.Move;
import threetrios.strategies.MoveStrategy;

/**
 * A simple Player interface for a game of three trios.
 */
public interface PlayerType {

  /**
   * Play calls a strategies choseMove(), which then returns a card and cords of where the player
   * should move based on the given strategy.
   *
   * @param model is the model the game is being played one.
   * @return the move selected by a strategy.
   */
  Move play(Model model);

  /**
   * Returns a players team color, this can be either red or blue.
   *
   * @return a players team color, this can be either red or blue.
   */
  TeamColor getColor();

  /**
   * Return the strategy that a player is using, null if not using a strategy.
   * @return the strategy that a player is using, null if not using a strategy.
   */
  MoveStrategy getStrat();
}
