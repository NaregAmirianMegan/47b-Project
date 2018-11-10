import java.util.PriorityQueue;

/**
 *
 * @author Nareg A. Megan
 *
 */
public class Node implements Comparable<Node>{

  private Node parent = null;
  private Board state;
  //A* vals
  private double gVal, hVal;
  private Double fVal;

//TESTING PURPOSES
  public Node(Double x) {
    this.fVal = x;
  }

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
    this.fVal = this.hVal;
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
  public Node(Board board, double gVal, Node parent) {
    this.state = board;
    this.gVal = gVal;
    this.hVal = board.getHVal();
    this.fVal = this.gVal + this.hVal;
    this.parent = parent;
  }

  /**
  * Allow automatic comparison of nodes with respect to fVal
  *
  * @param other
  *     Node being compared to current node
  * @return
  *     int evaluating comparison between objects
  */
  //@override
  public int compareTo(Node other) {
    return this.fVal.compareTo(other.getFVal());
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
  public Double getFVal() {
    return this.fVal;
  }

  public static void main(String[] args) {
    PriorityQueue<Node> pq = new PriorityQueue<Node>();
    Node x = new Node(1.5);
    pq.add(x);
    Node y = new Node(3.0);
    pq.add(y);
    Node z = new Node(5.4);
    pq.add(z);
    Node a = new Node(4.2);
    pq.add(a);
    Node n = new Node(0.0);
    pq.add(n);
    System.out.println(pq.poll().getFVal());
    System.out.println(pq.poll().getFVal());
    System.out.println(pq.poll().getFVal());
    System.out.println(pq.poll().getFVal());
  }

}
