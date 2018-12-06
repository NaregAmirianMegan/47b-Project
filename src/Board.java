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
  private int code;

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
    this.code = Arrays.deepHashCode(this.boardArray);
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
    this.blockList = board.copyBlockList(blockNumber);
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
    this.code = Arrays.deepHashCode(this.boardArray);
  }


  private Block[] copyBlockList(int blockNumber) {
    Block[] newBlockList = new Block[this.blockList.length];
    for(int i = 0;i < this.blockList.length;i++) {
      if(i == blockNumber-1) {
        newBlockList[i] = this.blockList[i].copy();
      }
      newBlockList[i] = this.blockList[i];
    }
    return newBlockList;
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

  private int moveCalculator(Block block, Block goalBlock) {
    int xDis = block.getX() - goalBlock.getX();
    int yDis = block.getY() - goalBlock.getY();
    if(xDis != 0 && yDis != 0) {
      int columnCount = 0;
      int ghostColumnCount = 0;
      int rowCount = 0;
      int ghostRowCount = 0;
      for(Block b : this.blockList) {
        if(b != block) {
          if((b.getX() >= block.getX() && b.getX()+b.getWidth()-1 <= goalBlock.getX()+goalBlock.getWidth()-1) ||
              (b.getX() >= goalBlock.getX() && b.getX()+b.getWidth()-1 <= block.getX()+block.getWidth()-1)) {
                if(!(b.getY()+b.getHeight()-1 < block.getY() || b.getY() > block.getY()+block.getHeight()-1)) {
                  columnCount++;
                }
                if(!(b.getY()+b.getHeight()-1 < goalBlock.getY() || b.getY() > goalBlock.getY()+goalBlock.getHeight()-1)) {
                  ghostColumnCount++;
                }
            }
          if((b.getY() >= block.getY() && b.getY()+b.getHeight()-1 <= goalBlock.getY()+goalBlock.getHeight()-1) ||
              (b.getY() >= goalBlock.getY() && b.getY()+b.getHeight()-1 <= block.getY()+block.getHeight()-1)) {
                if(!(b.getX()+b.getWidth()-1 < block.getX() || b.getX() > block.getX()+block.getWidth()-1)) {
                  rowCount++;
                }
                if(!(b.getX()+b.getWidth()-1 < goalBlock.getX() || b.getX() > goalBlock.getX()+goalBlock.getWidth()-1)) {
                  ghostRowCount++;
                }
            }
        }
      }
      // System.out.println(columnCount);
      // System.out.println(ghostColumnCount);
      // System.out.println(rowCount);
      // System.out.println(ghostRowCount);
      int totalCount1 = columnCount + ghostRowCount;
      int totalCount2 = rowCount + ghostColumnCount;
      // System.out.println(totalCount1);
      // System.out.println(totalCount2);
      if(totalCount1 < totalCount2) {
        return 2 + totalCount2;
      }
      return 2 + totalCount1;
    } else if(xDis != 0) {
      int columnCount = 0;
      for(Block b : this.blockList) {
        if(b != block) {
          if((b.getX() > goalBlock.getX() && b.getX() < block.getX()) ||
              (b.getX() < goalBlock.getX() && b.getX() > block.getX())) {
                if(!(b.getY()+b.getHeight() < block.getY() || b.getY() > block.getY()+block.getHeight())) {
                  columnCount++;
                }
            }
          }
      }
      return 1 + columnCount;
    } else {
      int rowCount = 0;
      for(Block b : this.blockList) {
        if(b != block) {
          if((b.getY() > goalBlock.getY() && b.getY() < block.getY()) ||
              (b.getY() < goalBlock.getY() && b.getY() > block.getY())) {
                if(!(b.getX()+b.getWidth() < block.getX() || b.getX() > block.getX()+block.getWidth())) {
                  rowCount++;
                }
            }
        }
      }
      return 1 + rowCount;
    }
  }

  /**
  * Get heuristical value for A* algorithm; estimates
  * distance to goal node from current node by estimating the
  * number of moves to the solution.
  *
  * @return
  *     hVal
  */
  public double getHVal(Block[] goalBlockList) {
    if(this.blockList.length > 100) {
      return 0;
    }
    ArrayList<Block> usedBlocks = new ArrayList<Block>();
    int moves = 0;
    int distance;
    int minDistance;
    Block curr = null;
    for(Block goalBlock : goalBlockList) {
      minDistance = 0;
      for(Block potBlock : this.blockList) {
        if(goalBlock.equalsDims(potBlock) && !potBlock.getUsed()) {
          distance = this.moveCalculator(potBlock, goalBlock);
          if(minDistance == 0 || distance < minDistance) {
            minDistance = distance;
            curr = potBlock;
          }
        }
      }
      curr.setUsed(true);
      usedBlocks.add(curr);
      moves += minDistance;
    }
    for(Block block : usedBlocks) {
      block.setUsed(false);
    }
    return moves;
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

  // public ArrayList<Move> genPossibleMoves() {
  //   ArrayList<Move> moveList = new ArrayList<Move>();
  //   int spaceLeft;
  //   int spaceRight;
  //   int spaceUp;
  //   int spaceDown;
  //   int leftMargin;
  //   int rightMargin;
  //   int upMargin;
  //   int downMargin;
  //   boolean leftClear;
  //   boolean rightClear;
  //   boolean upClear;
  //   boolean downClear;
  //   for(int i = 0;i < this.blockList.length;i++) {
  //     Block currBlock = this.blockList[i];
  //     spaceLeft = Integer.MAX_VALUE;
  //     spaceRight = Integer.MAX_VALUE;
  //     spaceUp = Integer.MAX_VALUE;
  //     spaceDown = Integer.MAX_VALUE;
  //     leftClear = true;
  //     rightClear = true;
  //     upClear = true;
  //     downClear = true;
  //     for(int j = 0;j < this.blockList.length;j++) {
  //       Block block = this.blockList[j];
  //       if(currBlock == block) {
  //         continue;
  //       }
  //       if(block.getX()+block.getWidth()-1 < currBlock.getX()) {
  //         if(!(block.getY()+block.getHeight()-1 < currBlock.getY() || block.getY() > currBlock.getY()+currBlock.getHeight()-1)) {
  //           leftMargin = currBlock.getX()-(block.getX()+block.getWidth());
  //           leftClear = false;
  //           if(leftMargin < spaceLeft) {
  //             spaceLeft = leftMargin;
  //           }
  //         }
  //       } else if(block.getX() > currBlock.getX()+currBlock.getWidth()-1) {
  //         if(!(block.getY()+block.getHeight()-1 < currBlock.getY() || block.getY() > currBlock.getY()+currBlock.getHeight()-1)) {
  //           rightMargin = block.getX()-(currBlock.getX()+currBlock.getWidth());
  //           rightClear = false;
  //           if(rightMargin < spaceRight) {
  //             spaceRight = rightMargin;
  //           }
  //         }
  //       }
  //       if(block.getY()+block.getHeight()-1 < currBlock.getY()) {
  //         if(!(block.getX()+block.getWidth()-1 < currBlock.getX() || block.getX() > currBlock.getX()+currBlock.getWidth()-1)) {
  //           upMargin = currBlock.getY()-(block.getY()+block.getHeight());
  //           upClear = false;
  //           if(upMargin < spaceUp) {
  //             spaceUp = upMargin;
  //           }
  //         }
  //       } else if(block.getY() > currBlock.getY()+currBlock.getHeight()-1) {
  //         if(!(block.getX()+block.getWidth()-1 < currBlock.getX() || block.getX() > currBlock.getX()+currBlock.getWidth()-1)) {
  //           downMargin = block.getY()-(currBlock.getY()+currBlock.getHeight());
  //           downClear = false;
  //           if(downMargin < spaceDown) {
  //             spaceDown = downMargin;
  //           }
  //         }
  //       }
  //     }
  //     if(leftClear) {
  //       spaceLeft = currBlock.getX();
  //     }
  //     if(rightClear) {
  //       spaceRight = this.boardArray[0].length-(currBlock.getX()+currBlock.getWidth());
  //     }
  //     if(upClear) {
  //       spaceUp = currBlock.getY();
  //     }
  //     if(downClear) {
  //       spaceDown = this.boardArray.length-(currBlock.getY()+currBlock.getHeight());
  //     }
  //     int max = Math.max(Math.max(spaceLeft, spaceRight), Math.max(spaceUp, spaceDown));
  //     for(int s = max;s > 0;s--) {
  //       if(spaceLeft >= s) {
  //         moveList.add(new Move(true, -s, i+1));
  //       }
  //       if(spaceRight >= s) {
  //         moveList.add(new Move(true, s, i+1));
  //       }
  //       if(spaceUp >= s) {
  //         moveList.add(new Move(false, -s, i+1));
  //       }
  //       if(spaceDown >= s) {
  //         moveList.add(new Move(false, s, i+1));
  //       }
  //     }
  //   }
  //   return moveList;
  // }

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

  // /**
  // * Check if this board is equal to that board.
  // *
  // * @return
  // *     true if they are equal; false otherwise
  // */
  // @Override
  // public boolean equals(Object board) {
  //   Board other = (Board) board;
  //   Block[] otherBlockList = other.getBlockList();
  //   for(int i = 0;i < otherBlockList.length;i++) {
  //     boolean okay = false;
  //     for(int j = 0;j < this.blockList.length;j++) {
  //       if(otherBlockList[i].getWidth() == this.blockList[j].getWidth()
  //             && otherBlockList[i].getHeight() == this.blockList[j].getHeight()) {
  //         if(otherBlockList[i].getX() == this.blockList[j].getX()
  //               && otherBlockList[i].getY() == this.blockList[j].getY()) {
  //             okay = true;
  //             break;
  //         }
  //       }
  //     }
  //     if(!okay) {
  //       return false;
  //     }
  //   }
  //   return true;
  // }

  @Override
  public boolean equals(Object board) {
    Board other = (Board) board;
    for(int i = 0;i < this.boardArray.length;i++) {
      for(int j = 0;j < this.boardArray[0].length;j++) {
        if((this.boardArray[i][j] != 0 && other.getBoardArray()[i][j] == 0) ||
            (this.boardArray[i][j] == 0 && other.getBoardArray()[i][j] != 0)) {
          return false;
        }
      }
    }
    return true;
  }

  private static int code(int[] list) {
    return Arrays.hashCode(list);
  }

  @Override
  public int hashCode() {
    // int[] list = new int[this.blockList.length];
    // for(int i = 0;i < list.length;i++) {
    //   list[i] = this.blockList[i].hashCode();
    // }
    // return this.code(list);
    return this.code;
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
    final int boardWidth = 4, boardHeight = 6;
    final Block[] blockList = new Block[11];
    //x, y, width height

    Block block1 = new Block(0, 1, 2, 2);
    blockList[0] = block1;
    Block block2 = new Block(2, 0, 2, 1);
    blockList[1] = block2;
    Block block3 = new Block(2, 1, 2, 1);
    blockList[2] = block3;
    Block block4 = new Block(2, 2, 2, 1);
    blockList[3] = block4;
    Block block5 = new Block(0, 3, 1, 2);
    blockList[4] = block5;
    Block block6 = new Block(1, 3, 1, 2);
    blockList[5] = block6;
    Block block7 = new Block(2, 3, 2, 1);
    blockList[6] = block7;
    Block block8 = new Block(2, 4, 2, 1);
    blockList[7] = block8;
    Block block9 = new Block(0, 5, 1, 1);
    blockList[8] = block9;
    Block block10 = new Block(1, 5, 1, 1);
    blockList[9] = block10;
    Block block11 = new Block(2, 5, 2, 1);
    blockList[10] = block11;

    // Block gBlock1 = new Block(0, 4, 2, 2);
    // //Block gBlock2 = new Block(3, 4, 1, 1);
    // Block[] goalBlocks = new Block[1];
    // goalBlocks[0] = gBlock1;
    //goalBlocks[1] = gBlock2;

    Board board = new Board(blockList, boardWidth, boardHeight);

    System.out.println(board.toString());

    ArrayList<Move> moves = board.genPossibleMoves();
    for(Move move : moves) {
      System.out.println(move.toString());
      System.out.println("-----------------------");
      Board newBoard = new Board(board, move);
      System.out.println(newBoard.toString());
    }
//    System.out.println(board.toString());

  //  System.out.println(board.getHVal(goalBlocks));

    //
    // Block block1c = new Block(0, 0, 2, 2);
    // blockList[0] = block1c;
    // Block block2c = new Block(4, 0, 1, 1);
    // blockList[1] = block2c;
    // Block block3c = new Block(2, 2, 2, 2);
    // blockList[2] = block3c;
    // Block block4c = new Block(0, 4, 2, 1);
    // blockList[3] = block4c;
    // Block block5c = new Block(4, 4, 1, 1);
    // blockList[4] = block5c;
    //
    // Board boardCopy = new Board(blockList, boardWidth, boardHeight);
    //boardCopy = new Board(boardCopy, new Move(true, 2, 1));

    // System.out.println(board.toString());
    // System.out.println(boardCopy.toString());
    //
    // System.out.println(board.hashCode());
    // System.out.println(boardCopy.hashCode());

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

    // ArrayList<Move> moves = board.genPossibleMovesSingleSpace();
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
