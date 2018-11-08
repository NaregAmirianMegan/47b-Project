/**
 *
 * @author Nareg A. Megan
 *
 */
public class Node {

  private Node parent = null;
  private Board state;
  //A* vals
  private float gVal, hVal;

  /**
  * Construct initial root Node
  *
  * @param board
  *     initial board config
  */
  public Node(Board board) {
    this.state = board;
    this.gVal = 0;
    this.hVal = board.getHVal();
  }

  /**
  * Construct child Node
  *
  * @param board
  *     new board state
  * @param gVal
  *     new gVal
  * @param parent
  *     parent Node
  */
  public Node(Board board, int gVal, Node parent) {
    this.state = board;
    this.gVal = gVal;
    this.hVal = board.getHVal();
    this.parent = parent;
  }

  //Getters and Setters

  /**
  * Get parent Node
  *
  * @return
  *     parent
  */
  public Node getParent() {
    return this.parent;
  }

  /**
  * Get state
  *
  * @return
  *     state
  */
  public Board getState() {
    return this.state;
  }

  /**
  * Get fVal of board state
  *
  * @return
  *     fVal
  */
  public float getFVal() {
    return this.gVal + this.hVal;
  }

}
