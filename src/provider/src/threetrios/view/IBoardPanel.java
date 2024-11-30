package provider.src.threetrios.view;


/**
 * Interface for a board panel implementation.
 */
public interface IBoardPanel {

  /**
   * Places a cell at a given location.
   * @param row is the row the user wants to place at. Follows readme cordinate rules.
   * @param col is the column the user wants to place at. Follows readme cordinate rules.
   * @param cell is the cell the user wants to place.
   */
  public void placeCard(int row, int col, Cell cell);

}
