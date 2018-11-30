import java.util.*;
import java.io.*;

/**
 *
 * @author Nareg A. Megan
 *
 */
public class SolverInteractive {

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
    Move currMove = null;
    while((currMove = node.getMoveToThis()) != null) {
      System.out.println(currMove.toString());
      node = node.getParent();
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
      File startFile = new File("../benchmark/benchmark_easy/" + start);
      File goalFile = new File("../benchmark/benchmark_easy/" + goal);
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
       System.out.println(startingBoard.toString());
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
        double strt = System.currentTimeMillis();
        PriorityQueue<Node> openList = new PriorityQueue<Node>();
        HashMap<Board, Double> openListTracker = new HashMap<Board, Double>();
        HashMap<Board, Double> closedList = new HashMap<Board, Double>();
        openList.add(rootNode);
        openListTracker.put(rootNode.getState(), rootNode.getFVal());
        while(!openList.isEmpty()) {
          Node currNode = openList.poll();
          currNode.calcFVal(goalBlockList);
          closedList.put(currNode.getState(), currNode.getFVal());
          if(goalReached(currNode, goalBlockList)) {
            double end = System.currentTimeMillis();
            System.out.println("Goal Reached!!!");
            System.out.println("Solution Time: " + ((end - strt)/1000.0) + "s.");
            printMovesTo(currNode);
            System.out.println(currNode.toString());
            return;
          }
          for(Node child : currNode.generateChildren()) {
            if(closedList.get(child.getState()) != null) {
              continue;
            }
            child.calcFVal(goalBlockList);
            if(openListTracker.containsKey(child.getState())) { //if there is a node with the same board state as child in the open list
              if(openListTracker.get(child.getState()) <= child.getFVal()) { //if that node has a lower f then skip this node
                continue;
              }
              openList.remove(child);
            }
            openList.add(child);
            //gVal automatically replaced
            openListTracker.put(child.getState(), child.getFVal());
          }
        }

      }
  }

}
