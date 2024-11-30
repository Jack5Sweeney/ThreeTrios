package provider.src.threetrios.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * A board is a 2-d array of objects, and is filled in initially by holes and card cells. Eventually
 * the board is filled in with cards as players place them.
 */
public class ThreeTriosBoard implements Board {
  private Object[][] board;
  private final String holesAndCells;
  private boolean boardSetup = false;

  /**
   * Create a board object to represent our playable board.
   *
   * @param rows          is the number of rows of the intended board.
   * @param columns       is the number of coluns of the intended board.
   * @param holesAndCells is a string, for example "XXX\nXCX\nCCX", that represents the layout of
   *                      cardcells and holes we want to start our game with.
   */
  public ThreeTriosBoard(int rows, int columns, String holesAndCells) {
    // check rows and columns big enough
    if (rows < 2 || columns < 2) {
      throw new IllegalArgumentException("rows and columns must be atleast 2x2 to play a game!");
    }
    for (char character : holesAndCells.toCharArray()) {
      if (character != 'C' && character != 'X') {
        throw new IllegalArgumentException("given board should be made up of only X and C.");
      }
    }

    board = new Object[rows][columns];
    this.holesAndCells = holesAndCells;
  }

  @Override
  public int setUp() {
    int rows = board.length;
    int columns = board[0].length;

    int counter = 0;
    int cardCellCounter = 0;

    for (int row = 0; row < rows; row++) {

      for (int column = 0; column < columns; column++) {

        if (holesAndCells.charAt(counter) == 'C') {
          board[row][column] = CellType.CARDCELL;
          cardCellCounter++;
        } else if (holesAndCells.charAt(counter) == 'X') {
          board[row][column] = CellType.HOLE;
        }

        counter++;
      }

    }

    if (cardCellCounter == 0) {
      throw new IllegalArgumentException("number of card cells on a board cannot be 0");
    }

    if (cardCellCounter % 2 == 0) {
      // number of card cells in a board must be odd!
      throw new IllegalArgumentException("number of card cells on a board must be odd");
    }

    boardSetup = true;
    return cardCellCounter;
  }

  @Override
  public Object getCardAt(int row, int col) {
    if (!boardSetup) {
      throw new IllegalStateException("after creating a board, " +
              "you must set it up before you can use its methods.");
    }
    if (board[row][col] == null) {
      throw new IllegalStateException("you are filling your board with null values");
    } else if (board[row][col] == CellType.CARDCELL) {
      return CellType.CARDCELL;
    } else if (board[row][col] == CellType.HOLE) {
      return CellType.HOLE;
    }

    Card intededCard = (Card) board[row][col];

    // avoid aliasing
    Card copy = new ThreeTriosCardAndColor(intededCard.getNorthValue(), intededCard.getEastValue(),
            intededCard.getSouthValue(), intededCard.getWestValue(), intededCard.getColor(),
            intededCard.getName());

    return copy;
  }

  @Override
  public List<Object> getAdjacentCards(Card card) {
    if (!boardSetup) {
      throw new IllegalStateException("after creating a board, " +
              "you must set it up before you can use its methods.");
    }
    if (card == null) {
      throw new IllegalArgumentException("you cannot call get adjacent card with a null card");
    }

    // logic: within same row, and one column either side OR
    //        within same column, and same row either side
    // check above, right, below, left

    List<Object> adjecentCards = new ArrayList<>();

    Point point = findCard(card);

    if (point == null) {
      return adjecentCards;
    }

    int row = (int) point.getX();
    int col = (int) point.getY();


    if (row == 0) { // No cell above
      adjecentCards.add(CellType.HOLE);
    } else {
      adjecentCards.add(board[row - 1][col]); // cell above
    }

    if (col >= board[0].length - 1) { // No cell to the right
      adjecentCards.add(CellType.HOLE);
    } else {
      adjecentCards.add(board[row][col + 1]); // cell to the right
    }

    if (row >= board.length - 1) { // No cell below
      adjecentCards.add(CellType.HOLE);
    } else {
      adjecentCards.add(board[row + 1][col]); // cell below
    }

    if (col == 0) { // No cell to the left
      adjecentCards.add(CellType.HOLE);
    } else {
      adjecentCards.add(board[row][col - 1]); // cell to the left
    }

    return adjecentCards;
  }

  @Override
  public List<Object> getAdjacentCards(int row, int col) {
    if (!boardSetup) {
      throw new IllegalStateException("after creating a board, " +
              "you must set it up before you can use its methods.");
    }

    // logic: within same row, and one column either side OR
    //        within same column, and same row either side
    // check above, right, below, left

    List<Object> adjecentCards = new ArrayList<>();

    if (row == 0) { // No cell above
      adjecentCards.add(CellType.HOLE);
    } else {
      adjecentCards.add(board[row - 1][col]); // cell above
    }

    if (col >= board[0].length - 1) { // No cell to the right
      adjecentCards.add(CellType.HOLE);
    } else {
      adjecentCards.add(board[row][col + 1]); // cell to the right
    }

    if (row >= board.length - 1) { // No cell below
      adjecentCards.add(CellType.HOLE);
    } else {
      adjecentCards.add(board[row + 1][col]); // cell below
    }

    if (col == 0) { // No cell to the left
      adjecentCards.add(CellType.HOLE);
    } else {
      adjecentCards.add(board[row][col - 1]); // cell to the left
    }

    return adjecentCards;
  }

  private Point findCard(Card card) {
    // return a new point representing the coords of a given card.
    // returns null if no matching card is found.
    for (int row = 0; row < board.length; row++) {

      for (int col = 0; col < board[0].length; col++) {

        if (board[row][col].equals(card)) {
          return new Point(row, col);

        }
      }
    }

    return null;
  }

  @Override
  public boolean redPlayerWins() {
    if (!boardSetup) {
      throw new IllegalStateException("after creating a board, " +
              "you must set it up before you can use its methods.");
    }

    int redCounter = 0;
    int blueCounter = 0;

    for (int rows = 0; rows < board.length; rows++) {

      for (int cols = 0; cols < board[0].length; cols++) {

        if (board[rows][cols] instanceof Card) {

          if (((Card) board[rows][cols]).getColor() == TeamColor.RED) {
            redCounter++;
          }
          if (((Card) board[rows][cols]).getColor() == TeamColor.BLUE) {
            blueCounter++;
          }
        }
      }
    }

    return redCounter > blueCounter;
  }

  @Override
  public int getPlayerScore(TeamColor teamColor) {
    int scoreCounter = 0;

    for (int rows = 0; rows < board.length; rows++) {

      for (int cols = 0; cols < board[0].length; cols++) {

        if (board[rows][cols] instanceof Card) {

          if (((Card) board[rows][cols]).getColor() == teamColor) {
            scoreCounter++;
          }
        }
      }
    }

    return scoreCounter;
  }

  @Override
  public void setCardAt(int row, int col, Card card) {
    if (!boardSetup) {
      throw new IllegalStateException("after creating a board, " +
              "you must set it up before you can use its methods.");
    }
    if (card == null) {
      throw new IllegalArgumentException("you cannot call get adjacent card with a null card");
    }
    if (row < 0 || row > board.length) {
      throw new IllegalArgumentException("cannot set a card, invalid given row");
    }
    if (col < 0 || col > board[0].length) {
      throw new IllegalArgumentException("cannot set a card, invalid given col");
    }

    board[row][col] = card;
  }

  @Override
  public String boardToString() {
    if (!boardSetup) {
      throw new IllegalStateException("after creating a board, " +
              "you must set it up before you can use its methods.");
    }
    StringBuilder builder = new StringBuilder();

    for (int row = 0; row < board.length; row++) {
      for (int col = 0; col < board[0].length; col++) {
        builder.append(board[row][col]).append(" ");

        if (col == board[0].length - 1) {
          builder.append("\n");
        }

      }
    }

    return builder.toString();
  }

  @Override
  public Object[][] getBoardArray() {
    Object[][] copy = new Object[board.length][board[0].length];
    for (int row = 0; row < board.length; row++) {
      for (int col = 0; col < board[0].length; col++) {
        copy[row][col] = board[row][col];
      }
    }

    return copy;
  }

  @Override
  public int getRow() {
    return board.length;
  }

  @Override
  public int getCol() {
    return board[0].length;
  }

  @Override
  public void setBoard(Object[][] board) {
    this.board = board;
  }
}
