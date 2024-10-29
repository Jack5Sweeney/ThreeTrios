public class GameTextView implements GameView {

  private final IModel model;
  private final IPlayer currentPlayer;  // Set this to either redPlayer or bluePlayer

  /**
   * Constructs a GameTextView with the given game model and current player.
   *
   * @param model the game model that provides the state of the game
   * @param currentPlayer the player whose turn it is (either redPlayer or bluePlayer)
   * @throws IllegalArgumentException if model or currentPlayer is null
   */
  public GameTextView(IModel model, IPlayer currentPlayer) {
    if (model == null || currentPlayer == null) {
      throw new IllegalArgumentException("Model and current player cannot be null");
    }
    this.model = model;
    this.currentPlayer = currentPlayer;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();

    // Display the current player
    String currentPlayerColor = currentPlayer == model.getRedPlayer() ? "RED" : "BLUE";
    sb.append("Player: ").append(currentPlayerColor).append("\n");

    // Display the board, representing R, B, _ and spaces
    // Assuming model has getters for board availability and cards as arrays
    Card[][] boardWithCards = model.getBoard();  // Assuming `getBoard` returns Card[][]
    CellType[][] boardAvailability = model.getBoardAvailability();  // Assuming `getBoardAvailability` returns CellType[][]

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
        sb.append(" ");
      }
      sb.append("\n");
    }

    // Display the hand for the current player
    sb.append("Hand:\n");
    for (Card card : currentPlayer.getHand()) {
      sb.append(card.toString()).append("\n");
    }

    return sb.toString();
  }
}
