import java.util.*;
import java.io.*;

/**
 *
 * @author Nareg A. Megan
 *
 */
public class SolverTest {

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
      File startFile = new File("../benchmark/benchmark_hard/" + start);
      File goalFile = new File("../benchmark/benchmark_hard/" + goal);
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

        double totalTimeMoves = 0;
        double totalTimeGoal = 0;
        double totalTimeCheckClosed = 0;
        double totalTimeCheckOpen = 0;
        double totalTimeCheckFVal = 0;
        double totalTimeRemoveFromOpenList = 0;
        double totalTimeCalcFVal = 0;
        double totalPollTime = 0;
        double totalOpenListTrackerRemovalTime = 0;
        double totalTimeClosedListPut = 0;

        PriorityQueue<Node> openList = new PriorityQueue<Node>(100000);
        HashMap<Board, Double> openListTracker = new HashMap<Board, Double>(1000000);
        HashMap<Board, Double> closedList = new HashMap<Board, Double>(1000000);

        openList.add(rootNode);
        openListTracker.put(rootNode.getState(), rootNode.getFVal());

        double s;
        double d;

        double strt = System.currentTimeMillis();
        while(!openList.isEmpty()) {
          s = System.currentTimeMillis();
          Node currNode = openList.poll();
          d = System.currentTimeMillis();
          totalPollTime += ((d - s)/1000.0);

          s = System.currentTimeMillis();
          openListTracker.remove(currNode.getState());
          d = System.currentTimeMillis();
          totalOpenListTrackerRemovalTime += ((d - s)/1000.0);

          s = System.currentTimeMillis();
          closedList.put(currNode.getState(), currNode.getFVal());
          d = System.currentTimeMillis();
          totalTimeClosedListPut += ((d - s)/1000.0);

          s = System.currentTimeMillis();
          boolean done = goalReached(currNode, goalBlockList);
          d = System.currentTimeMillis();
          totalTimeGoal += ((d - s)/1000.0);

          if(done) {
            double end = System.currentTimeMillis();
            System.out.println("Goal Reached!!!");
            System.out.println("Solution Time: " + ((end - strt)/1000.0) + "s.");
            System.out.println("Move Generation Time: " + totalTimeMoves);
            System.out.println("Goal Check Time: " + totalTimeGoal);
            System.out.println("Check Closed List Time: " + totalTimeCheckClosed);
            System.out.println("Check Open List Time: " + totalTimeCheckOpen);
            System.out.println("Check fVal Time: " + totalTimeCheckFVal);
            System.out.println("Open List Removal Time: " + totalTimeRemoveFromOpenList);
            System.out.println("Calc FVal Time: " + totalTimeCalcFVal);
            System.out.println("Poll Time: " + totalPollTime);
            System.out.println("Open List Tracker Removal time: " + totalOpenListTrackerRemovalTime);
            System.out.println("Closed List Put Time: " + totalTimeClosedListPut);
            System.out.println("Closed List Size: " + closedList.size());
            System.out.println("Open List Size: " + openList.size());
            System.out.println("Open List Tracker Size: " + openListTracker.size());
            double totalTime = totalTimeMoves+totalTimeGoal+totalTimeCheckClosed+totalTimeCheckOpen+totalTimeCheckFVal+totalTimeRemoveFromOpenList
                                +totalTimeCalcFVal+totalPollTime+totalOpenListTrackerRemovalTime+totalTimeClosedListPut;
            System.out.println("Total Time: " + totalTime);
            printMovesTo(currNode);
            System.out.println("============================");
            System.out.println(currNode.toString());
            return;
          }

          s = System.currentTimeMillis();
          ArrayList<Node> children = currNode.generateChildren();
          d = System.currentTimeMillis();
          totalTimeMoves += ((d - s)/1000.0);

          for(Node child : children) {

            s = System.currentTimeMillis();
            boolean inClosed = closedList.containsKey(child.getState());
            d = System.currentTimeMillis();
            totalTimeCheckClosed += ((d - s)/1000.0);

            if(inClosed) {
              continue;
            }
            s = System.currentTimeMillis();
            child.calcFVal(goalBlockList);
            d = System.currentTimeMillis();
            totalTimeCalcFVal += ((d - s)/1000.0);

            s = System.currentTimeMillis();
            boolean inOpen = openListTracker.containsKey(child.getState());
            d = System.currentTimeMillis();
            totalTimeCheckOpen += ((d - s)/1000.0);

            if(inOpen) { //if there is a node with the same board state as child in the open list

              s = System.currentTimeMillis();
              boolean checkF = (openListTracker.get(child.getState()) <= child.getFVal());
              d = System.currentTimeMillis();
              totalTimeCheckFVal += ((d - s)/1000.0);

              if(checkF) { //if that node has a lower f then skip this node
                continue;
              }
              s = System.currentTimeMillis();
            //  openList.remove(child);
              d = System.currentTimeMillis();
              totalTimeRemoveFromOpenList += ((d - s)/1000.0);
            }
            openList.add(child);
            //fVal automatically replaced
            openListTracker.put(child.getState(), child.getFVal());
          }
        }
      }
  }

}
