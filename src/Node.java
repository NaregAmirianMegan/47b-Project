import java.util.*;

/**
 *
 * @author Nareg A. Megan
 *
 */
public class Node implements Comparable<Node>{

  private Node parent = null;
  private Board state;
  private double gVal, hVal;
  private Double fVal;
  private Move moveToThis = null;

  /**
  * Construct initial root Node
  *
  * @param board
  *     initial board config
  */
  public Node(Board board) {
    this.state = board;
    this.gVal = 0;
    this.hVal = 0;
    this.fVal = 0.0;
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
  public Node(Node parent, Move move) {
    this.state = new Board(parent.state, move);
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
    Node node = (Node) other;
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
  public int compareTo(Node other) {
    return this.fVal.compareTo(other.getFVal());
  }

  /**
  * generate list of children nodes using Board's genPossibleMoves
  *
  * @return
  *     ArrayList of Nodes which have new board state as children and this node as parent
  */
  public ArrayList<Node> generateChildren() {
    ArrayList<Node> children = new ArrayList<Node>();
    for(Move move : this.state.genPossibleMoves()) {
      children.add(new Node(this, move));
    }
    return children;
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

  public Move getMoveToThis() {
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

  public void calcFVal(Block[] goalBlockList) {
    this.hVal = this.state.getHVal(goalBlockList);
    this.fVal = this.gVal + this.hVal;
  }

  public String toString() {
    return this.state.toString();
  }

  public static void main(String[] args) {
    PriorityQueue<Node> pq = new PriorityQueue<Node>();

    final int boardWidth = 5, boardHeight = 5;
    final Block[] blockList = new Block[5];

    Block block1 = new Block(0, 0, 2, 2);
    blockList[0] = block1;
    Block block2 = new Block(4, 4, 1, 1);
    blockList[1] = block2;
    Block block3 = new Block(2, 2, 2, 2);
    blockList[2] = block3;
    Block block4 = new Block(0, 4, 2, 1);
    blockList[3] = block4;
    Block block5 = new Block(4, 0, 1, 1);
    blockList[4] = block5;

    Board board = new Board(blockList, boardWidth, boardHeight);

    Block[] goalBlockList = {new Block(0, 0, 1, 1)};

    Node rootNode = new Node(board);
    rootNode.calcFVal(goalBlockList);
    ArrayList<Node> children = rootNode.generateChildren();

    Node copyNode = new Node(board);
    copyNode.calcFVal(goalBlockList);

    System.out.println(rootNode.toString());
    System.out.println("===================");
    for(Node child : children) {
      System.out.println(child.toString());
    }

    //System.out.println(copyNode.toString());
    //
    //
    // pq.add(rootNode);
    //
    // for(Node child : children) {
    //   child.calcFVal(goalBlockList);
    //   pq.add(child);
    // }
    //
    // pq.remove(copyNode);
    // System.out.println(pq.contains(copyNode));


    // HashMap<Board, Double> map = new HashMap<Board, Double>();
    // map.put(rootNode.getState(), rootNode.getFVal());
    // System.out.println(rootNode.toString());
    // for(Node child : children) {
    //   child.calcFVal(goalBlockList);
    //   map.put(child.getState(), child.getFVal());
    //   System.out.println(child.getState().toString());
    // }
    //
    // //map.put(copyNode.getState(), copyNode.getFVal());
    //
    // //System.out.println(map.get(children.get(0).getState()));
    //
    // System.out.println(map.containsKey(copyNode.getState()));

    // Node x = new Node(board);
    // pq.add(x);
    // Node y = new Node(3.0);
    // pq.add(y);
    // Node z = new Node(5.4);
    // pq.add(z);
    // Node a = new Node(4.2);
    // pq.add(a);
    // Node n = new Node(0.0);
    // pq.add(n);
    // System.out.println(pq.poll().getFVal());
    // System.out.println(pq.poll().getFVal());
    // System.out.println(pq.poll().getFVal());
    // System.out.println(pq.poll().getFVal());
  }

}
