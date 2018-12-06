import java.util.*;

public class BoardE {

  private HashMap<Pair, Pair> blockMap = new HashMap<Pair, Pair>();
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
  public BoardE(Pair[][] blockPairs, int boardWidth, int boardHeight) {
    this.boardArray = new int[boardHeight][boardWidth];
    for(int i = 0;i < blockPairs.length;i++) {
      Pair loc = blockPairs[i][0];
      Pair dims = blockPairs[i][1];
      blockMap.put(loc, dims);
      for(int r = loc.getB();r < loc.getB()+dims.getB();r++) {
        for(int c = loc.getA();c < loc.getA()+dims.getA();c++) {
          this.boardArray[r][c] = i+1;
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
  * @param move
  *     Move to be made on board
  */
  public BoardE(BoardE board, MoveE move) {
    this.boardArray = board.copyBoardArray();
    this.blockMap = board.copyBlockMap();
    Pair oldLoc = move.getOldLoc();
    Pair newLoc = move.getNewLoc();
    Pair dims = this.blockMap.get(oldLoc);
    this.blockMap.remove(oldLoc);
    this.blockMap.put(newLoc, dims);
    int blockNumber = this.boardArray[oldLoc.getB()][oldLoc.getA()];
    int diff = newLoc.getA() - oldLoc.getA();
    int spaces;
    if(diff == 0) { //row-wise movment
      spaces = newLoc.getB() - oldLoc.getB();
      if(spaces < 0) {
        for(int r = oldLoc.getB();r < dims.getB()+oldLoc.getB();r++) {
          for(int c = oldLoc.getA();c < dims.getA()+oldLoc.getA();c++) {
            this.boardArray[r+spaces][c] = blockNumber;
            this.boardArray[r][c] = 0;
          }
        }
      } else {
        for(int r = dims.getB()+oldLoc.getB()-1;r >= oldLoc.getB();r--) {
          for(int c = oldLoc.getA();c < dims.getA()+oldLoc.getA();c++) {
            this.boardArray[r+spaces][c] = blockNumber;
            this.boardArray[r][c] = 0;
          }
        }
      }
    } else {       //column-wise movment
      spaces = diff;
      if(spaces < 0) {
        for(int c = oldLoc.getA();c < dims.getA()+oldLoc.getA();c++) {
          for(int r = oldLoc.getB();r < dims.getB()+oldLoc.getB();r++) {
            this.boardArray[r][c+spaces] = blockNumber;
            this.boardArray[r][c] = 0;
          }
        }
      } else {
        for(int c = dims.getA()+oldLoc.getA()-1;c >= oldLoc.getA();c--) {
          for(int r = oldLoc.getB();r < dims.getB()+oldLoc.getB();r++) {
            this.boardArray[r][c+spaces] = blockNumber;
            this.boardArray[r][c] = 0;
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
  public int[][] copyBoardArray() {
    int[][] newBoardArray = new int[this.boardArray.length][this.boardArray[0].length];
    for(int i = 0;i < this.boardArray.length;i++) {
      newBoardArray[i] = Arrays.copyOf(this.boardArray[i], this.boardArray[i].length);
    }
    return newBoardArray;
  }

  public HashMap<Pair, Pair> copyBlockMap() {
    HashMap<Pair, Pair> copy = new HashMap<Pair, Pair>();
    for(Map.Entry<Pair, Pair> entry : this.blockMap.entrySet()) {
      copy.put(entry.getKey().copy(), entry.getValue().copy());
    }
    return copy;
  }

  /**
  * Get heuristical value for A* algorithm; estimates
  * distance to goal node from current node by estimating the
  * number of moves to the solution.
  *
  * @return
  *     hVal
  */
  public double getHVal(HashMap<Pair, Pair> goalBlockMap) {
    return 0;
  }

  //Getters

  /**
  * Get blockList
  *
  * @return
  *     blockList
  */
  public HashMap<Pair, Pair> getBlockMap() {
    return this.blockMap;
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
  public ArrayList<MoveE> genPossibleMoves() {
    ArrayList<MoveE> moveList = new ArrayList<MoveE>();
    for(Map.Entry<Pair, Pair> entry : this.blockMap.entrySet()) {
      Pair loc = entry.getKey();
      Pair dims = entry.getValue();
      boolean blocked = false;
      int shift = 0;
      //look right
      while(!blocked) {
        for(int i = loc.getB();i < loc.getB()+dims.getB();i++) {
          if(loc.getA()+dims.getA()+shift >= this.boardArray[0].length ||
              this.boardArray[i][loc.getA()+dims.getA()+shift] != 0) {
            blocked = true;
          }
        }
        if(!blocked) {
          moveList.add(new MoveE(loc.copy(), new Pair(loc.getA()+shift+1, loc.getB())));
        }
        shift++;
      }
      //look left
      blocked = false;
      shift = -1;
      while(!blocked) {
        for(int i = loc.getB();i < loc.getB()+dims.getB();i++) {
          if(loc.getA()+shift < 0 ||
              this.boardArray[i][loc.getA()+shift] != 0) {
            blocked = true;
          }
        }
        if(!blocked) {
          moveList.add(new MoveE(loc.copy(), new Pair(loc.getA()+shift, loc.getB())));
        }
        shift--;
      }
      //look down
      blocked = false;
      shift = 0;
      while(!blocked) {
        for(int i = loc.getA();i < loc.getA()+dims.getA();i++) {
          if(loc.getB()+dims.getB()+shift >= this.boardArray.length ||
              this.boardArray[loc.getB()+dims.getB()+shift][i] != 0) {
            blocked = true;
          }
        }
        if(!blocked) {
          moveList.add(new MoveE(loc.copy(), new Pair(loc.getA(), loc.getB()+shift+1)));
        }
        shift++;
      }
      //look up
      blocked = false;
      shift = -1;
      while(!blocked) {
        for(int i = loc.getA();i < loc.getA()+dims.getA();i++) {
          if(loc.getB()+shift < 0 ||
              this.boardArray[loc.getB()+shift][i] != 0) {
            blocked = true;
          }
        }
        if(!blocked) {
          moveList.add(new MoveE(loc.copy(), new Pair(loc.getA(), loc.getB()+shift)));
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
  public boolean equals(Object board) {
    BoardE other = (BoardE) board;
    for(Pair loc : this.blockMap.keySet()) {
      Pair thisDim = this.blockMap.get(loc);
      Pair otherDim = other.getBlockMap().get(loc);
      if(!thisDim.equals(otherDim)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int hashCode() {
    return Arrays.deepHashCode(this.boardArray);
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

  public static void main(String[] args) {
    Pair[][] pairs = new Pair[5][2];
    pairs[0][0] = new Pair(0, 0);
    pairs[0][1] = new Pair(2, 2);
    pairs[1][0] = new Pair(2, 2);
    pairs[1][1] = new Pair(1, 1);
    pairs[2][0] = new Pair(3, 0);
    pairs[2][1] = new Pair(2, 1);
    pairs[3][0] = new Pair(0, 3);
    pairs[3][1] = new Pair(1, 2);
    pairs[4][0] = new Pair(4, 4);
    pairs[4][1] = new Pair(1, 1);
    BoardE board = new BoardE(pairs, 5, 5);
    ArrayList<MoveE> moves = board.genPossibleMoves();
    BoardE boardCopy = new BoardE(pairs, 5, 5);
    BoardE boardDiff = new BoardE(board, moves.get(0));
    System.out.println(board.equals(boardCopy));
    System.out.println(board.equals(boardDiff));
  }
}
