package provider.src.threetrios.controller;

import threetrios.model.Model;
import threetrios.model.TeamColor;
import threetrios.strategies.Move;
import threetrios.strategies.MoveStrategy;

/**
 * A simple Player implementation that delegates most of its
 * complexity to a MoveStrategy for choosing where to play next.
 */
public class PlayerImpl implements PlayerType {
  private final TeamColor color;
  private MoveStrategy moveStrategy;

  public PlayerImpl(TeamColor color, MoveStrategy strategy) {
    this.color = color;
    this.moveStrategy = strategy;
  }

  @Override
  public Move play(Model model) {
    return moveStrategy.chooseMove(model, this.color);
  }

  @Override
  public TeamColor getColor() {
    return this.color;
  }

  @Override
  public MoveStrategy getStrat() {
    return moveStrategy;
  }
}