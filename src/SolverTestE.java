import java.util.*;
import java.io.*;

/**
 *
 * @author Nareg A. Megan
 *
 */
public class SolverTestE {

  public static boolean goalReached(NodeE node, HashMap<Pair, Pair> goalBlockMap) {
    HashMap<Pair, Pair> blockMap = node.getState().getBlockMap();
    for(Map.Entry<Pair, Pair> entry : goalBlockMap.entrySet()) {
      Pair goalLoc = entry.getKey();
      Pair goalDims = entry.getValue();
      Pair currDimsAtLoc = blockMap.get(goalLoc);
      if(goalDims.equals(currDimsAtLoc)) {
        continue;
      } else {
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
      File startFile = new File("../benchmark/benchmark_medium/" + start);
      File goalFile = new File("../benchmark/benchmark_medium/" + goal);
      BufferedReader startConfig = new BufferedReader(new FileReader(startFile));
      BufferedReader goalConfig = new BufferedReader(new FileReader(goalFile));
      String[] dims = startConfig.readLine().split(" ");
      ArrayList<String> startConfigLines = new ArrayList<String>();
      String line;
      while((line = startConfig.readLine()) != null) {
        startConfigLines.add(line);
      }

      Pair[][] blockRepr = new Pair[startConfigLines.size()][2];
      for(int i = 0;i < startConfigLines.size();i++) {
        String[] info = startConfigLines.get(i).split(" ");
        int x = Integer.parseInt(info[3]);
        int y = Integer.parseInt(info[2]);
        int width = Integer.parseInt(info[1]);
        int height = Integer.parseInt(info[0]);
        blockRepr[i][0] = new Pair(x, y);
        blockRepr[i][1] = new Pair(width, height);
      }

       BoardE startingBoard = new BoardE(blockRepr, Integer.parseInt(dims[1]), Integer.parseInt(dims[0]));
       System.out.println(startingBoard.toString());
       NodeE rootNode = new NodeE(startingBoard);

       HashMap<Pair, Pair> goalBlockMap = new HashMap<Pair, Pair>();
       while((line = goalConfig.readLine()) != null) {
         String[] info = line.split(" ");
         int Xi = Integer.parseInt(info[3]);
         int Yi = Integer.parseInt(info[2]);
         int Xf = Integer.parseInt(info[1]);
         int Yf = Integer.parseInt(info[0]);
         goalBlockMap.put(new Pair(Xi, Yi), new Pair(Xf, Yf));
       }
        ///////////////////////////////////////////////////////////
        //A*                        A*                       A*////
        ///////////////////////////////////////////////////////////
        double strt = System.currentTimeMillis();
        double totalTimeMoves = 0;
        double totalTimeGoal = 0;
        double totalTimeCheckClosed = 0;
        double totalTimeCheckOpen = 0;
        double totalTimeCheckFVal = 0;
        double totalTimeRemoveFromOpenList = 0;

        PriorityQueue<NodeE> openList = new PriorityQueue<NodeE>();
        HashMap<BoardE, Double> openListTracker = new HashMap<BoardE, Double>();
        HashMap<BoardE, Double> closedList = new HashMap<BoardE, Double>();

        openList.add(rootNode);
        openListTracker.put(rootNode.getState(), rootNode.getFVal());

        while(!openList.isEmpty()) {
          NodeE currNode = openList.poll();
          openListTracker.remove(currNode.getState());
          currNode.calcFVal(goalBlockMap);
          closedList.put(currNode.getState(), currNode.getFVal());

          double s = System.currentTimeMillis();
          boolean done = goalReached(currNode, goalBlockMap);
          double d = System.currentTimeMillis();
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
            System.out.println("Closed List Size: " + closedList.size());
            System.out.println("Open List Size: " + openList.size());
            System.out.println("Open List Tracker Size: " + openListTracker.size());
            //printMovesTo(currNode);
            System.out.println("============================");
            System.out.println(currNode.toString());
            return;
          }

          s = System.currentTimeMillis();
          ArrayList<NodeE> children = currNode.generateChildren();
          d = System.currentTimeMillis();
          totalTimeMoves += ((d - s)/1000.0);

          for(NodeE child : children) {

            s = System.currentTimeMillis();
            boolean inClosed = closedList.containsKey(child.getState());
            d = System.currentTimeMillis();
            totalTimeCheckClosed += ((d - s)/1000.0);

            if(inClosed) {
              continue;
            }
            child.calcFVal(goalBlockMap);

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
              openList.remove(child);
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
