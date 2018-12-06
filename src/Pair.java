import java.util.*;

/**
* Simple pair object to store two integers
*
* a = column and width
* b = row and height
* (Pair(column, row), Pair(width, height))
*/
public class Pair {
  private int[] pair = new int[2];

  /**
  * Construct Pair object
  *
  * @param a
  *     first int
  * @param b
  *     second int
  */
  public Pair(int a, int b) {
    this.pair[0] = a;
    this.pair[1] = b;
  }

  /**
  * Use Cantor function to create unique hashCode for any integer pair
  *
  * @return
  *     unique natural number to a natural number pair
  */
  @Override
  public int hashCode() {
    return Arrays.hashCode(this.pair);
  }

  /**
  * Get first int
  *
  * @return
  *     this.a
  */
  public int getA() {
    return this.pair[0];
  }

  /**
  * Get second int
  *
  * @return
  *     this.b
  */
  public int getB() {
    return this.pair[1];
  }

  @Override
  public boolean equals(Object otherPair) {
    if(otherPair == null) {
      return false;
    }
    Pair other = (Pair) otherPair;
    return (this.pair[0] == other.getA() && this.pair[1] == other.getB());
  }

  public Pair copy() {
    return new Pair(this.pair[0], this.pair[1]);
  }

  public String toString() {
    return "(" + this.pair[0] + ", " + this.pair[1] + ")";
  }

  public static void main(String[] args) {
    HashMap<Pair, Pair> map = new HashMap<Pair, Pair>();

  }
}
