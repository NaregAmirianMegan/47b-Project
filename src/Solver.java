import java.util.*;
import java.io.*;

/**
 *
 * @author Nareg A. Megan
 *
 */
public class Solver {

  public static boolean goalReached(Node node, Block[] goalBlockList) {
    Block[] currBlocks = node.getState().getBlockList();
    for(int i = 0;i < goalBlockList.length;i++) {
      boolean okay = false;
      for(int j = 0;j < currBlocks.length;j++) {
        if(goalBlockList[i].getWidth() == currBlocks[j].getWidth()
              && goalBlockList[i].getHeight() == currBlocks[j].getHeight()) {
          if(goalBlockList[i].getX() == currBlocks[j].getX()
                && goalBlockList[i].getY() == currBlocks[j].getY()) {
              okay = true;
              break;
          }
        }
      }
      if(!okay) {
        return false;
      }
    }
    return true;
  }

  public static void printMovesTo(Node node) {
    Stack<Node> path = new Stack<Node>();
    Node currNode = node;
    while(currNode != null) {
      path.push(currNode);
      currNode = currNode.getParent();
    }
    Node prevNode = path.pop();
    while(!path.empty()) {
      currNode = path.pop();
      int blockNum = currNode.getMoveToThis().getBlockNum();
      Block prevBlock = prevNode.getState().getBlockList()[blockNum-1];
      Block newBlock = currNode.getState().getBlockList()[blockNum-1];
      int iRow = prevBlock.getY();
      int iColumn = prevBlock.getX();
      int fRow = newBlock.getY();
      int fColumn = newBlock.getX();
      System.out.println(iRow + " " + iColumn + " " + fRow + " " + fColumn);
      prevNode = currNode;
    }
  }

  public static void main(String[] args) throws IOException {
    if(args.length < 2 || args.length > 3) {
      throw new IOException("Invalid number of arguments.");
    }
    if(args[0].substring(0, 2).equals("-o")) {
      System.out.println("Debug");
    } else {
      String start = args[0];
      String goal = args[1];
      File startFile = new File(start);
      File goalFile = new File(goal);
      BufferedReader startConfig = new BufferedReader(new FileReader(startFile));
      BufferedReader goalConfig = new BufferedReader(new FileReader(goalFile));
      String[] dims = startConfig.readLine().split(" ");
      //System.out.println("Rows: " + dims[0] + " Columns: " + dims[1]);
      String blockRepr;
      LinkedList<Block> linkedBlocks = new LinkedList<Block>();
      int length = 0;
      while((blockRepr = startConfig.readLine()) != null) {
        String[] info = blockRepr.split(" ");
        int x = Integer.parseInt(info[3]);
        int y = Integer.parseInt(info[2]);
        int width = Integer.parseInt(info[1]);
        int height = Integer.parseInt(info[0]);
        linkedBlocks.add(new Block(x, y, width, height));
        length++;
     }
       Block[] blocks = linkedBlocks.toArray(new Block[length]);
       Board startingBoard = new Board(blocks, Integer.parseInt(dims[1]), Integer.parseInt(dims[0]));
       //System.out.println(startingBoard.toString());
       Node rootNode = new Node(startingBoard);

       linkedBlocks = new LinkedList<Block>();
       length = 0;
       while((blockRepr = goalConfig.readLine()) != null) {
         String[] info = blockRepr.split(" ");
         int x = Integer.parseInt(info[3]);
         int y = Integer.parseInt(info[2]);
         int width = Integer.parseInt(info[1]);
         int height = Integer.parseInt(info[0]);
         linkedBlocks.add(new Block(x, y, width, height));
         length++;
        }
        Block[] goalBlockList = linkedBlocks.toArray(new Block[length]);
        ///////////////////////////////////////////////////////////
        //A*                        A*                       A*////
        ///////////////////////////////////////////////////////////

        PriorityQueue<Node> openList = new PriorityQueue<Node>();
        HashMap<Board, Double> openListTracker = new HashMap<Board, Double>();
        HashMap<Board, Double> closedList = new HashMap<Board, Double>();

        openList.add(rootNode);
        openListTracker.put(rootNode.getState(), rootNode.getFVal());
        
        while(!openList.isEmpty()) {
          Node currNode = openList.poll();
          openListTracker.remove(currNode.getState());
          closedList.put(currNode.getState(), currNode.getFVal());
          if(goalReached(currNode, goalBlockList)) {
            printMovesTo(currNode);
            return;
          }
          for(Node child : currNode.generateChildren()) {
            if(closedList.containsKey(child.getState())) {
              continue;
            }
            child.calcFVal(goalBlockList);
            if(openListTracker.containsKey(child.getState())) { //if there is a node with the same board state as child in the open list
              if(openListTracker.get(child.getState()) <= child.getFVal()) { //if that node has a lower f then skip this node
                continue;
              }
            }
            openList.add(child);
            openListTracker.put(child.getState(), child.getFVal()); //fVal automatically replaced
          }
        }
        System.exit(1);
      }
  }

}
