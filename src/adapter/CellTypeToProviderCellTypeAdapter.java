package adapter;

import card.CellTypeContents;
import provider.src.threetrios.model.CellType;

public class CellTypeToProviderCellTypeAdapter {
  CellTypeContents myCellType;

  public CellTypeToProviderCellTypeAdapter(CellTypeContents cellType) {
    this.myCellType = cellType;
  }

  public CellType convert() {
    if(myCellType == CellTypeContents.CARD) {
      return CellType.CARD;
    }
    else if(myCellType == CellTypeContents.EMPTY) {
      return CellType.CARDCELL;
    }
    else {
      return CellType.HOLE;
    }
  }
}
