/**
 * Enum representing different direction values, each associated with an integer value.
 * The enum provides constants ONE through A, each mapped to a specific integer.
 */
public enum DirectionValue {

  ONE(1),
  TWO(2),
  THREE(3),
  FOUR(4),
  FIVE(5),
  SIX(6),
  SEVEN(7),
  EIGHT(8),
  NINE(9),
  A(10);

  /** The integer value associated with each direction constant. */
  private final int value;

  /**
   * Constructor to assign an integer value to each enum constant.
   *
   * @param value the integer value corresponding to the direction.
   */
  DirectionValue(int value) {
    this.value = value;
  }

  /**
   * Retrieves the integer value associated with the enum constant.
   *
   * @return the integer value.
   */
  public int getValue() {
    return value;
  }
}

