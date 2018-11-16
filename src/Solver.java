import java.util.*;
import java.io.*;

/**
 *
 * @author Nareg A. Megan
 *
 */
public class Solver {

  // /**
  // * Return a Board object from a configuration file
  // *
  // * @param file
  // *     init config file
  // * @return
  // *     Board config board representation
  // */
  // public parseInput(File file) { //TODO: import File class and implement
  //
  // }

  public boolean goalReached(Node node, Block[] goalBlockList) {

  }

  public static void main(String[] args) throws IOException{
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
      System.out.println("Rows: " + dims[0] + " Columns: " + dims[1]);
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
      for(int i = 0;i < goalBlockList.length;i++) {
        System.out.println(goalBlockList[i].toString());
      }
    }
  }

}
