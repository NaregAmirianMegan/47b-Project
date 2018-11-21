import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Nareg A. Megan
 *
 */
public class Board {

  private Block[] blockList;
  private int[][] boardArray;

  /**
  * Construct Board representation from blockList and board width and height.
  * Used for initial Board construction from text input.
  *
  * @param blockList
  *     list of blocks from text input
  * @param boardWidth
  *     width of board
  * @param boardHeight
  *     height of board
  */
  public Board(Block[] blockList, int boardWidth, int boardHeight) {
    this.blockList = blockList;
    this.boardArray = new int[boardHeight][boardWidth];
    for(int k = 0;k < this.blockList.length;k++) {
      Block block = this.blockList[k];
      for(int r = block.getY();r < block.getY()+block.getHeight();r++) {
        for(int c = block.getX();c < block.getX()+block.getWidth();c++) {
          this.boardArray[r][c] = k+1;
        }
      }
    }
  }

  /**
  * Construct Board representation from a previous board and a move.
  * The move is determined by the blockNumber, column or row, and spaces
  *
  * @param board
  *     Previous board before the move
  * @param blockNumber
  *     In-order block to be moved
  * @param column
  *     boolean indicating true: column, false: row
  * @param spaces
  *     number of spaces to move along the column or row
  */
  public Board(Board board, Move move) {
    boolean column = move.getColumn();
    int blockNumber = move.getBlockNum();
    int spaces = move.getSpaces();
    this.boardArray = board.deepCopyBoardArray();
    this.blockList = board.deepCopyBlockList();
    Block block = board.getBlockList()[blockNumber-1];
    if(column) {
      this.blockList[blockNumber-1] = new Block(board.getBlockList()[blockNumber-1].getX()+spaces,
                                                board.getBlockList()[blockNumber-1].getY(),
                                                board.getBlockList()[blockNumber-1].getWidth(),
                                                board.getBlockList()[blockNumber-1].getHeight());
      if(spaces < 0) {
        for(int c = block.getX();c < block.getWidth()+block.getX();c++) {
          for(int r = block.getY();r < block.getHeight()+block.getY();r++) {
            this.boardArray[r][c+spaces] = blockNumber;
            this.boardArray[r][c] = 0;
          }
        }
      } else {
        for(int c = block.getWidth()+block.getX()-1;c >= block.getX();c--) {
          for(int r = block.getY();r < block.getHeight()+block.getY();r++) {
            this.boardArray[r][c+spaces] = blockNumber;
            this.boardArray[r][c] = 0;
          }
        }
      }
    } else {
      this.blockList[blockNumber-1] = new Block(board.getBlockList()[blockNumber-1].getX(),
                                                board.getBlockList()[blockNumber-1].getY()+spaces,
                                                board.getBlockList()[blockNumber-1].getWidth(),
                                                board.getBlockList()[blockNumber-1].getHeight());
      if(spaces < 0) {
        for(int r = block.getY();r < block.getHeight()+block.getY();r++) {
          for(int c = block.getX();c < block.getWidth()+block.getX();c++) {
            this.boardArray[r][c] = 0;
            this.boardArray[r+spaces][c] = blockNumber;
          }
        }
      } else {
        for(int r = block.getHeight()+block.getY()-1;r >= block.getY();r--) {
          for(int c = block.getX();c < block.getWidth()+block.getX();c++) {
            this.boardArray[r][c] = 0;
            this.boardArray[r+spaces][c] = blockNumber;
          }
        }
      }
    }
  }

  /**
  * Create a deep copied boardArray.
  *
  * @return
  *     copy of boardArray
  */
  public int[][] deepCopyBoardArray() {
    int[][] newBoardArray = new int[this.boardArray.length][this.boardArray[0].length];
    for(int i = 0;i < this.boardArray.length;i++) {
      newBoardArray[i] = Arrays.copyOf(this.boardArray[i], this.boardArray[i].length);
    }
    return newBoardArray;
  }

  /**
  * Create a deep copied blockList.
  *
  * @return
  *     copy of blockList
  */
  public Block[] deepCopyBlockList() {
    Block[] newBlockList = new Block[this.blockList.length];
    for(int i = 0;i < this.blockList.length;i++) {
      newBlockList[i] = this.blockList[i].copy();
    }
    return newBlockList;
  }

  //TODO: implement
  /**
  * Get heuristical value using A* algorithm. Which estimates
  * distance to goal node from current node using _________
  *
  * @return
  *     hVal
  */
  public double getHVal(Block[] goalBlockList) {
    return 0;
  }

  //Getters

  /**
  * Get blockList
  *
  * @return
  *     blockList
  */
  public Block[] getBlockList() {
    return this.blockList;
  }

  /**
  * Get boardArray
  *
  * @return
  *     boardArray
  */
  public int[][] getBoardArray() {
    return this.boardArray;
  }

  /**
    * Generate all possible moves from board state.
    *
    * @return
    *     List of possible next boards from current board
    */
  public ArrayList<Move> genPossibleMoves() {
    ArrayList<Move> moveList = new ArrayList<Move>();
    for(int b = 0;b < this.blockList.length;b++) {
      Block block = this.blockList[b];
      boolean blocked = false;
      int shift = 0;
      while(!blocked) {
        for(int i = block.getY();i < block.getY()+block.getHeight();i++) {
          if(block.getX()+block.getWidth()+shift >= this.boardArray[0].length ||
              this.boardArray[i][block.getX()+block.getWidth()+shift] != 0) {
            blocked = true;
          }
        }
        if(!blocked) {
          moveList.add(new Move(true, shift+1, b+1));
        }
        shift++;
      }
      blocked = false;
      shift = -1;
      while(!blocked) {
        for(int i = block.getY();i < block.getY()+block.getHeight();i++) {
          if(block.getX()+shift < 0 ||
              this.boardArray[i][block.getX()+shift] != 0) {
            blocked = true;
          }
        }
        if(!blocked) {
          moveList.add(new Move(true, shift, b+1));
        }
        shift--;
      }
      blocked = false;
      shift = 0;
      while(!blocked) {
        for(int i = block.getX();i < block.getX()+block.getWidth();i++) {
          if(block.getY()+block.getHeight()+shift >= this.boardArray.length ||
              this.boardArray[block.getY()+block.getHeight()+shift][i] != 0) {
            blocked = true;
          }
        }
        if(!blocked) {
          moveList.add(new Move(false, shift+1, b+1));
        }
        shift++;
      }
      blocked = false;
      shift = -1;
      while(!blocked) {
        for(int i = block.getX();i < block.getX()+block.getWidth();i++) {
          if(block.getY()+shift < 0 ||
              this.boardArray[block.getY()+shift][i] != 0) {
            blocked = true;
          }
        }
        if(!blocked) {
          moveList.add(new Move(false, shift, b+1));
        }
        shift--;
      }
    }
    return moveList;
  }

  /**
  * Check if this board is equal to that board.
  *
  * @return
  *     true if they are equal; false otherwise
  */
  @Override
  public boolean equals(Object block) {
    Board other = (Board) block;
    Block[] otherBlockList = other.getBlockList();
    for(int i = 0;i < otherBlockList.length;i++) {
      boolean okay = false;
      for(int j = 0;j < this.blockList.length;j++) {
        if(otherBlockList[i].getWidth() == this.blockList[j].getWidth()
              && otherBlockList[i].getHeight() == this.blockList[j].getHeight()) {
          if(otherBlockList[i].getX() == this.blockList[j].getX()
                && otherBlockList[i].getY() == this.blockList[j].getY()) {
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

  /**
  * generate unique hashCode for board state
  *
  * @return
  *     hashCode
  */
  @Override
  public int hashCode() {
    //create hash out of hashCode of each block
    int[] blockHashes = new int[this.blockList.length];
    for(int i = 0;i < blockHashes.length;i++) {
      blockHashes[i] = this.blockList[i].hashCode();
    }
    //int code = Arrays.hashCode(blockHashes);
    //System.out.println(code);
    return Arrays.hashCode(blockHashes);
  }

  /**
  * Return a string that allows the board to be interpreted
  * by the user
  *
  * @return
  *     string representation
  */
  public String toString() {
    String s = "";
    for(int r = 0;r < this.boardArray.length;r++) {
      for(int c = 0;c < this.boardArray[0].length;c++) {
        s += this.boardArray[r][c] + " ";
      }
      s += "\n";
    }
    return s;
  }

  //TEST FUNCTIONALITY

  public static void main(String[] args) {
    //Test construction, getters, setters
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

    Block block1c = new Block(0, 0, 2, 2);
    blockList[0] = block1c;
    Block block2c = new Block(4, 0, 1, 1);
    blockList[1] = block2c;
    Block block3c = new Block(2, 2, 2, 2);
    blockList[2] = block3c;
    Block block4c = new Block(0, 4, 2, 1);
    blockList[3] = block4c;
    Block block5c = new Block(4, 4, 1, 1);
    blockList[4] = block5c;

    Board boardCopy = new Board(blockList, boardWidth, boardHeight);
    //boardCopy = new Board(boardCopy, new Move(true, 2, 1));

    System.out.println(board.toString());
    System.out.println(boardCopy.toString());

    System.out.println(board.hashCode());
    System.out.println(boardCopy.hashCode());

    //Test 2D representation

    // System.out.println(board.toString());
    // System.out.println("=========================");



    //
    // Board board2 = new Board(board, new Move(true, 1, 1));
    //
    // System.out.println("-----------------------");
    // System.out.println(board2.toString());

    // Move move = new Move(true, 2, 1);
    // System.out.println(move.toString());
    // System.out.println((new Board(board, move)).toString());

    // ArrayList<Move> moves = board.genPossibleMoves();
    // for(Move move : moves) {
    //   System.out.println(move.toString());
    //   System.out.println("-----------------------");
    //   System.out.println(board.toString());
    //   System.out.println("+++++++++++++++++++++++");
    //   Board newBoard = new Board(board, move);
    //   System.out.println(newBoard.toString());
    // }

  //   System.out.println("-----------------------");
  //   System.out.println("Tests Finished.");
  }

}
