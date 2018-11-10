import java.util.PriorityQueue;
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

  public boolean goalReached(Node node, int[] goalRepr) {
    
  }

  public static void main(String[] args) throws IOException{
    if(args.length < 2 || args.length > 3) {
      throw new IOException("Invalid number of arguments.");
    }
    if(args[0].substring(0, 2).equals("-o")) {
      System.out.println("Debug");
    } else {
      System.out.println(args[0].substring(0, 2));
    }
  }

}
