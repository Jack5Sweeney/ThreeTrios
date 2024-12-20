package model;

import cardcomparison.CardComparisonStrategy;
import controller.ModelObserver;
import flipcriteria.ICriteria;
import player.IPlayer;
import player.PlayerColor;

/**
 * Interface representing the full model for a game, extending the read-only model
 * and adding mutator methods to control and manage the game's state.
 */
public interface IModel extends ReadOnlyIModel {

  /**
   * Places a card on the board for a specified player at the given row and column
   * position, removing it from the player's hand.
   *
   * @param boardRow        the row on the board where the card will be placed
   * @param boardCol        the column on the board where the card will be placed
   * @param cardIndexInHand the index of the card in the player's hand
   * @param player          the player who is placing the card
   * @throws IllegalArgumentException if the player is null, if the card index is invalid,
   *                                  or if the placement on the board is invalid
   */
  void placeCard(int boardRow, int boardCol, int cardIndexInHand, IPlayer player);

  /**
   * Updates the card to be placed on the board based on the specified row index and
   * player color. This may involve changing the card's state or selection before placement.
   *
   * @param index the row on the board where the card will potentially be placed
   * @param color the color associated with the player or card being updated
   */
  void updateCardToPlace(int index, PlayerColor color);

  /**
   * Adds an observer to the list of observers that will be notified of changes
   * in the model's state. Observers can listen for specific events or updates
   * in the model.
   *
   * @param observer the observer to be added; must not be null
   * @throws IllegalArgumentException if the observer is null
   */
  void addObserver(ModelObserver observer);

  /**
   * Starts the game by configuring the board, setting up initial conditions, and preparing
   * the card deck.
   *
   * @throws IllegalArgumentException if there is an issue reading the configuration file
   *                                  or if the file has an invalid format
   */
  void startGame();

  boolean isCellEmpty(int row, int col);

  void setVariantRule(CardComparisonStrategy variantRule);

  void setCardComparisonStrategy(CardComparisonStrategy strategy);

  void setFlipCriteria(ICriteria criteria);

}
