import java.util.*;

/**
 *
 * @author Nareg A. Megan
 *
 */
public class Block {

  private int x, y, width, height;

  /**
  * Construct Block object
  * @param x
  *     x-coordinate of upper-left corner of Block
  * @param y
  *     y-coordinate of upper-left corner of Block
  * @param width
  *     Width of block
  * @param height
  *     Height of block
  */
  public Block(int x, int y, int width, int height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }

  //Getters and Setters

  /**
  * Get x-coordinate
  *
  * @return
  *     x
  */
  public int getX() {
    return this.x;
  }

  /**
  * Get y-coordinate
  *
  * @return
  *     y
  */
  public int getY() {
    return this.y;
  }

  /**
  * Get width
  *
  * @return
  *     width
  */
  public int getWidth() {
    return this.width;
  }

  /**
  * Get height
  *
  * @return
  *     height
  */
  public int getHeight() {
    return this.height;
  }

  /**
  * Return a copied Block object.
  *
  * @return
  *     copy of this Block object
  */
  public Block copy() {
    Block newBlock = new Block(this.x, this.y, this.width, this.height);
    return newBlock;
  }

  /**
  * Check if two blocks are equal.
  *
  * @param other
  *     other block to check equality with
  * @return
  *     true if they are equal; false otherwise
  */
  public boolean equals(Block other) {
    if(this.x == other.getX() && this.y == other.getY() &&
          this.width == other.getWidth() && this.height == other.getHeight()) {
      return true;
    }
    return false;
  }

  /**
  * generate unique hash code for a block
  *
  * @return
  *     hash code
  */
  public int hashCode() {
    return Objects.hash((Integer) this.x, (Integer) this.y, (Integer) this.width, (Integer) this.height);
  }

  /**
  * Create string repr of Block object
  *
  * @return
  *     String representation
  */
  public String toString() {
    return "ROW: " + this.y + " COLUMN: " + this.x + " WIDTH: " + this.width + " HEIGHT: " + this.height;
  }

  //TEST FUNCTIONALITY

  public static void main(String[] args) {
    //Test construction, getters, setters
    final int X = 0, Y = 0, WIDTH = 1, HEIGHT = 1;
    Block block = new Block(X, Y, WIDTH, HEIGHT);
    if(block.getX() != X) {
      System.out.println("Failed: block.getX() -> " + block.getX() + " != " + X);
    }
    if(block.getY() != Y) {
      System.out.println("Failed: block.getY() -> " + block.getY() + " != " + Y);
    }
    if(block.getWidth() != WIDTH) {
      System.out.println("Failed: block.getWidth() -> " + block.getWidth() + " != " + WIDTH);
    }
    if(block.getHeight() != HEIGHT) {
      System.out.println("Failed: block.getHeight() -> " + block.getHeight() + " != " + HEIGHT);
    }
    Block other = new Block(X, Y, WIDTH, HEIGHT);
    if(!block.equals(other)) {
      System.out.println("Failed: block.equals(other) -> " + "false");
    }
    other = new Block(X+1, Y, WIDTH, HEIGHT+1);
    if(block.equals(other)) {
      System.out.println("Failed: block.equals(other) -> " + "true");
    }
    System.out.println("TEST HASHCODE FUNCTIONALITY");
    System.out.println("-----------------------");
    Block b1 = new Block(0, 0, 2, 2);
    Block b2 = new Block(0, 0, 2, 2);
    int h1 = b1.hashCode();
    int h2 = b2.hashCode();
    System.out.println(h1 + " == " + h2);
    b2 = new Block(1, 1, 2, 2);
    h2 = b2.hashCode();
    System.out.println(h1 + " != " + h2);
    System.out.println("-----------------------");
    System.out.println("Tests Finished.");
  }

}
