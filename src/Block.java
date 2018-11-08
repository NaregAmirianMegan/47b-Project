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
    System.out.println("-----------------------");
    System.out.println("Tests Finished.");
  }

}