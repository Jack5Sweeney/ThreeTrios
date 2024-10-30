/**
 * A textual representation of the game view for a player, displaying the current player, game board,
 * and the player's hand.
 *
 * <p>The {@code GameTextView} class implements the {@code GameView} interface and provides a
 * formatted string representation of the game state, including the current player's identity, the
 * board layout, and the cards in the current player's hand.
 */
public class ViewImpl implements IView {

  private final IModel model;
  private IPlayer currentPlayer;

  /**
   * Constructs a {@code GameTextView} with the specified game model and current player.
   *
   * @param model the game model that provides the state of the game
   * @throws IllegalArgumentException if {@code model} or {@code currentPlayer} is null
   */
  public ViewImpl(IModel model) {
    if (model == null) {
      throw new IllegalArgumentException("Model and current player cannot be null");
    }
    this.model = model;
    this.currentPlayer = model.getRedPlayer();
  }

  public void switchPlayerView() {
    if(this.currentPlayer.getPlayerColor() == PlayerColor.RED) {
      this.currentPlayer = model.getBluePlayer();
    } else if (this.currentPlayer.getPlayerColor() == PlayerColor.BLUE) {
      this.currentPlayer = model.getRedPlayer();
    }
  }

  /**
   * Returns a string representation of the game state, including the current player, board layout,
   * and hand.
   *
   * @return a formatted string displaying the game state for the current player
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    appendCurrentPlayer(sb);
    appendBoard(sb);
    appendHand(sb);
    return sb.toString();
  }

  /**
   * Appends the current player's identity to the provided {@code StringBuilder}, indicating
   * whether the player is the red or blue player.
   *
   * @param sb the {@code StringBuilder} to append the current player information to
   */
  private void appendCurrentPlayer(StringBuilder sb) {
    String currentPlayerColor = currentPlayer == model.getRedPlayer() ? "RED" : "BLUE";
    sb.append("Player: ").append(currentPlayerColor).append("\n");
  }

  /**
   * Appends the current game board layout to the provided {@code StringBuilder}, displaying
   * each cell as either red ('R'), blue ('B'), empty ('_'), or a hole (' ').
   *
   * @param sb the {@code StringBuilder} to append the board layout to
   */
  private void appendBoard(StringBuilder sb) {
    Card[][] boardWithCards = model.getBoard();
    CellType[][] boardAvailability = model.getBoardAvailability();

    for (int row = 0; row < boardWithCards.length; row++) {
      for (int col = 0; col < boardWithCards[row].length; col++) {
        if (boardWithCards[row][col] != null) {
          // Display 'R' or 'B' based on the card’s player color
          sb.append(boardWithCards[row][col].getPlayer() == PlayerColor.RED ? "R" : "B");
        } else if (boardAvailability[row][col] == CellType.HOLE) {
          sb.append(" ");  // Hole
        } else {
          sb.append("_");  // Empty cell
        }
      }
      sb.append("\n");
    }
  }

  /**
   * Appends the current player's hand of cards to the provided {@code StringBuilder}.
   *
   * @param sb the {@code StringBuilder} to append the hand information to
   */
  private void appendHand(StringBuilder sb) {
    sb.append("Hand:\n");
    for (Card card : currentPlayer.getHand()) {
      sb.append(card.toString()).append("\n");
    }
  }
}
