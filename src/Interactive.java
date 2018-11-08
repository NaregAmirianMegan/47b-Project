import java.util.ArrayList;
import java.util.Scanner;

public class Interactive {

  /**
  * Check the edges of the board and if there are other blocks
  * in the way of where this block will end up
  *
  * @param blockNumber
  *     number in blocklist of block to be moved
  * @param column
  *     whether to move column-wise or row-wise
  * @param spaces
  *     number of spaces to move
  * @return
  *     true if legal move; false if not
  */
  public static boolean isLegalMove(Move move, ArrayList<Move> moves) {
    boolean legal = false;
    for(Move m : moves) {
      if(move.equals(m)) {
        legal = true;
        break;
      }
    }
    return legal;
  }

  public static void loop(Board init, Board fin) {
    Board curr = init;
    Scanner scnr = new Scanner(System.in);
    while(true) {
      System.out.println("================================");
      System.out.println("CURRENT BOARD:");
      System.out.println(curr.toString());
      System.out.println("================================");
      System.out.println("GOAL STATE:");
      System.out.println(fin.toString());
      System.out.println("--------------------------------");
      ArrayList<Move> moves = curr.genPossibleMoves();
      System.out.print("Which block do you wish to move (0 to quit)? ");
      int blockNum = scnr.nextInt();
      if(blockNum == 0) {
        System.out.println("YOU QUIT :(");
        break;
      }
      System.out.print("(0) for row-wise (1) for column-wise: ");
      int input = scnr.nextInt();
      boolean column = false;
      if(input == 1) {
        column = true;
      }
      System.out.print("How many spaces? ");
      int spaces = scnr.nextInt();
      Move move = new Move(column, spaces, blockNum);
      if(isLegalMove(move, moves)) {
        System.out.println("Okay.");
        curr = new Board(curr, move);
        if(curr.equals(fin)) {
          System.out.println("SOLVED!!!");
          break;
        }
      } else {
        System.out.println("Illegal Move! Try again.");
      }
      System.out.println("\n\n");
    }
  }

  public static void main(String[] args) {

    final int boardWidth = 3, boardHeight = 3;
    final Block[] blockList = new Block[8];

    Block block1 = new Block(0, 0, 1, 1);
    blockList[0] = block1;
    Block block2 = new Block(1, 0, 1, 1);
    blockList[1] = block2;
    Block block3 = new Block(2, 0, 1, 1);
    blockList[2] = block3;
    Block block4 = new Block(0, 1, 1, 1);
    blockList[3] = block4;
    Block block5 = new Block(1, 1, 1, 1);
    blockList[4] = block5;
    Block block6 = new Block(2, 1, 1, 1);
    blockList[5] = block6;
    Block block7 = new Block(0, 2, 1, 1);
    blockList[6] = block7;
    Block block8 = new Block(2, 2, 1, 1);
    blockList[7] = block8;

    final Block[] blockListFinal = new Block[8];

    Block block1f = new Block(0, 0, 1, 1);
    blockListFinal[0] = block1f;
    Block block2f = new Block(1, 0, 1, 1);
    blockListFinal[1] = block2f;
    Block block3f = new Block(2, 0, 1, 1);
    blockListFinal[2] = block3f;
    Block block4f = new Block(0, 1, 1, 1);
    blockListFinal[3] = block4f;
    Block block5f = new Block(1, 1, 1, 1);
    blockListFinal[4] = block5f;
    Block block6f = new Block(2, 1, 1, 1);
    blockListFinal[5] = block6f;
    Block block7f = new Block(0, 2, 1, 1);
    blockListFinal[6] = block7f;
    Block block8f = new Block(1, 2, 1, 1);
    blockListFinal[7] = block8f;

    Board init = new Board(blockList, boardWidth, boardHeight);
    Board fin = new Board(blockListFinal, boardWidth, boardHeight);

    loop(init, fin);

  }

}
