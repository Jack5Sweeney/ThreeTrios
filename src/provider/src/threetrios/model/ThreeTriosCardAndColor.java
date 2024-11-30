package provider.src.threetrios.model;

/**
 * Implementation for a three trios card.
 */
public class ThreeTriosCardAndColor implements Card {
  private final int northValue;
  private final int southValue;
  private final int westValue;
  private final int eastValue;
  private final String name;
  private TeamColor color;

  /**
   * Cards have an integer value for north-south-east-west. They also belong to a team,
   * and have a name. All values should be between 1-10, 10 will be represented by A in an
   * eventual GUI.
   *
   * @param northValue north int attack value
   * @param eastValue  east int attack value
   * @param southValue south int attack value
   * @param westValue  west int attack value
   * @param color      of the team the card belongs to, red or blue
   * @param name       string name of the card
   */
  public ThreeTriosCardAndColor(int northValue, int eastValue, int southValue, int westValue,
                                TeamColor color, String name) {
    if (color == null) {
      throw new IllegalArgumentException("Model.Card construction with null color not allowed !");
    }
    if (name == null) {
      throw new IllegalArgumentException("Model.Card construction with null name not allowed !");
    }
    if (northValue < 1 || northValue > 10 ||
            southValue < 1 || southValue > 10 ||
            westValue < 1 || westValue > 10 ||
            eastValue < 1 || eastValue > 10) {
      throw new IllegalArgumentException("Model.Card construction with illegal value ranges");
    }

    this.northValue = northValue;
    this.southValue = southValue;
    this.westValue = westValue;
    this.eastValue = eastValue;
    this.color = color;
    this.name = name;
  }

  @Override
  public int getNorthValue() {
    return this.northValue;
  }

  @Override
  public int getSouthValue() {
    return this.southValue;
  }

  @Override
  public int getEastValue() {
    return this.eastValue;
  }

  @Override
  public int getWestValue() {
    return this.westValue;
  }

  @Override
  public void flip() {
    if (color == TeamColor.RED) {
      color = TeamColor.BLUE;
    } else {
      color = TeamColor.RED;
    }
  }

  // a card named WHALE with 5 north, 4 east, 3 south, 2 west:
  // WHALE 5 4 3 2
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();

    String formattedNorth = String.valueOf(northValue);
    String formattedEast = String.valueOf(eastValue);
    String formattedSouth = String.valueOf(southValue);
    String formattedWest = String.valueOf(westValue);

    if (northValue == 10) {
      formattedNorth = "A";
    }
    if (eastValue == 10) {
      formattedEast = "A";
    }
    if (southValue == 10) {
      formattedSouth = "A";
    }
    if (westValue == 10) {
      formattedWest = "A";
    }

    builder.append(name).append(" ").append(formattedNorth).append(" ").append(formattedEast)
            .append(" ").append(formattedSouth).append(" ").append(formattedWest);
    return builder.toString();
  }

  @Override
  public TeamColor getColor() {
    return this.color;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public boolean equals(Object that) {
    if (that == null) {
      // comparing to a null object
      throw new IllegalArgumentException("Cannot compare card to a null object !");
    }

    if (this == that) {
      // same ref
      return true;
    }

    if (!(that instanceof ThreeTriosCardAndColor)) {
      // dif class
      return false;
    }

    // same color and name and number fields -> cards are equal
    return (this.color.equals(((ThreeTriosCardAndColor) that).color) &&
            this.northValue == ((ThreeTriosCardAndColor) that).northValue &&
            this.eastValue == ((ThreeTriosCardAndColor) that).eastValue &&
            this.southValue == ((ThreeTriosCardAndColor) that).southValue &&
            this.westValue == ((ThreeTriosCardAndColor) that).westValue) &&
            this.name.equals(((ThreeTriosCardAndColor) that).name);
  }

  @Override
  public int hashCode() {
    int colorHash = color.hashCode();
    int nameHash = name.hashCode();

    int hash = 31 * (colorHash + nameHash) + northValue + eastValue + southValue + westValue;
    // online said good idea to multiply by a prime number, 31, first
    // to spread out hash values!
    return hash;
  }
}
