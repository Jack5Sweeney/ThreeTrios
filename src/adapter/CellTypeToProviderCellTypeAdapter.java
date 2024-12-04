package adapter;

import card.CellTypeContents;
import provider.src.threetrios.model.CellType;

/**
 * Adapter class to convert {@link CellTypeContents} to {@link CellType}.
 * This allows mapping between different representations of cell types.
 */
public class CellTypeToProviderCellTypeAdapter {

  private final CellTypeContents myCellType;

  /**
   * Constructs an adapter for the given {@link CellTypeContents}.
   *
   * @param cellType the cell type to adapt
   */
  public CellTypeToProviderCellTypeAdapter(CellTypeContents cellType) {
    this.myCellType = cellType;
  }

  /**
   * Converts the {@link CellTypeContents} to its corresponding {@link CellType}.
   *
   * @return the adapted {@link CellType}
   */
  public CellType convert() {
    switch (myCellType) {
      case CARD:
        return CellType.CARD;
      case EMPTY:
        return CellType.CARDCELL;
      default:
        return CellType.HOLE;
    }
  }
}
