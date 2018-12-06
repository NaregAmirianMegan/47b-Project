import java.util.*;

/**
 *
 * @author Nareg A. Megan
 *
 */
public class NodeE implements Comparable<NodeE>{

  private NodeE parent = null;
  private BoardE state;
  private double gVal, hVal;
  private Double fVal;
  private MoveE moveToThis = null;

  /**
  * Construct initial root Node
  *
  * @param board
  *     initial board config
  */
  public NodeE(BoardE board) {
    this.state = board;
    this.gVal = 0;
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
  public NodeE(NodeE parent, MoveE move) {
    this.state = new BoardE(parent.getState(), move);
    this.gVal = parent.getGVal() + 1;
    this.parent = parent;
    this.moveToThis = move;
  }

  /**
  * check if two nodes are equal
  *
  * @param other
  *     other node with which to compare this node
  * @return
  *     true if equal and false if not
  */
  @Override
  public boolean equals(Object other) {
    NodeE node = (NodeE) other;
    return this.state.equals(node.getState());
  }

  /**
  * generate hash id for given node. Should equal for equal board states
  *
  * @return
  *     return int hashCode
  */
  @Override
  public int hashCode() {
    return this.state.hashCode();
  }

  /**
  * Allow automatic comparison of nodes with respect to fVal
  *
  * @param other
  *     Node being compared to current node
  * @return
  *     int evaluating comparison between objects
  */
  @Override
  public int compareTo(NodeE other) {
    return this.fVal.compareTo(other.getFVal());
  }

  /**
  * generate list of children nodes using Board's genPossibleMoves
  *
  * @return
  *     ArrayList of Nodes which have new board state as children and this node as parent
  */
  public ArrayList<NodeE> generateChildren() {
    ArrayList<NodeE> children = new ArrayList<NodeE>();
  //  double s = System.currentTimeMillis();
    for(MoveE move : this.state.genPossibleMoves()) {
      children.add(new NodeE(this, move));
    }
  //  double d = System.currentTimeMillis();
  //  System.out.println((d - s)/1000.0);
    return children;
  }

  //Getters and Setters

  /**
  * Get parent Node
  *
  * @return
  *     parent
  */
  public NodeE getParent() {
    return this.parent;
  }

  /**
  * Get state
  *
  * @return
  *     state
  */
  public BoardE getState() {
    return this.state;
  }

  public MoveE getMoveToThis() {
    return this.moveToThis;
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

  public double getGVal() {
    return this.gVal;
  }

  public double getHVal() {
    return this.hVal;
  }

  public void calcFVal(HashMap<Pair, Pair> goalBlockMap) {
    this.hVal = this.state.getHVal(goalBlockMap);
    this.fVal = this.gVal + this.hVal;
  }

  public String toString() {
    return this.state.toString();
  }

  public static void main(String[] args) {
    PriorityQueue<NodeE> pq = new PriorityQueue<NodeE>();

    Pair[][] pairs = new Pair[5][2];
    pairs[0][0] = new Pair(0, 0);
    pairs[0][1] = new Pair(2, 2);
    pairs[1][0] = new Pair(2, 2);
    pairs[1][1] = new Pair(1, 1);
    pairs[2][0] = new Pair(3, 0);
    pairs[2][1] = new Pair(2, 1);
    pairs[3][0] = new Pair(0, 3);
    pairs[3][1] = new Pair(1, 2);
    pairs[4][0] = new Pair(4, 4);
    pairs[4][1] = new Pair(1, 1);
    BoardE board = new BoardE(pairs, 5, 5);

    NodeE rootNode = new NodeE(board);
    ArrayList<NodeE> children = rootNode.generateChildren();

    System.out.println(rootNode.toString());
    System.out.println("===================");
    for(NodeE child : children) {
      System.out.println(child.toString());
    }
  }

}
