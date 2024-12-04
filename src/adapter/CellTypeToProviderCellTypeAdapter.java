package adapter;

import card.CellTypeContents;
import provider.src.threetrios.model.CellType;

public class CellTypeToProviderCellTypeAdapter {
  private final CellTypeContents myCellType;

  public CellTypeToProviderCellTypeAdapter(CellTypeContents cellType) {
    this.myCellType = cellType;
  }

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
