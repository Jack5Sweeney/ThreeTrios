package provider.src.threetrios.strategies;

import provider.src.threetrios.model.Model;
import provider.src.threetrios.model.TeamColor;

/**
 * A Strategy interface for choosing where to play next for the given player.
 */
public interface MoveStrategy {

  /**
   * All strategies can choose the best move based on their unique implementation.
   *
   * @param model is the model used by our strategies to check for best moves.
   * @param color is the team the strategy should act as if it were part of.
   * @return the best move based on their unique implementation.
   */
  IMove chooseMove(Model model, TeamColor color);
}