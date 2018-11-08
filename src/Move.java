public class Move {

  private boolean column;
  private int spaces, blockNum;

  /**
  * Construct represenation of a move through three variables
  *
  * @param column
  *     true if moving column-wise; false if moving row-wise
  * @param spaces
  *     how many spaces to move
  * @param blockNum
  *     which block to move
  */
  public Move(boolean column, int spaces, int blockNum) {
    this.column = column;
    this.spaces = spaces;
    this.blockNum = blockNum;
  }

  /**
  * get column
  *
  * @return
  *     column
  */
  public boolean getColumn() {
    return this.column;
  }

  /**
  * get spaces
  *
  * @return
  *     spaces
  */
  public int getSpaces() {
    return this.spaces;
  }

  /**
  * get blockNum
  *
  * @return
  *     blockNum
  */
  public int getBlockNum() {
    return this.blockNum;
  }

  /**
  * Check if two moves are the same move
  *
  * @return
  *     true if they are; false if they are not
  */
  public boolean equals(Move move) {
    return this.column == move.getColumn() && this.spaces == move.getSpaces() && this.blockNum == move.getBlockNum();
  }

  /**
  * Return String representation of a move
  *
  * @return
  *     String representation of move
  */
  public String toString() {
    String s = "Move block number " + this.blockNum;
    if(this.column) {
      return s + " column-wise " + this.spaces + " space(s).";
    }
    return s + " row-wise " + this.spaces + " space(s).";
  }

  public static void main(String[] args) {
    //Test construction, getters, and methods
    final boolean COLUMN = true;
    final int SPACES = 2, BLOCKNUM = 1;

    Move move = new Move(true, 2, 1);
    Move sameMove = new Move(true, 2, 1);
    Move diffMove = new Move(false, 1, 1);

    if(move.getColumn() != COLUMN) {
      System.out.println("Failed: move.getColumn() -> " + move.getColumn() + " != " + COLUMN);
    }
    if(move.getSpaces() != SPACES) {
      System.out.println("Failed: move.getSpaces() -> " + move.getSpaces() + " != " + SPACES);
    }
    if(move.getBlockNum() != BLOCKNUM) {
      System.out.println("Failed: move.getBlockNum() -> " + move.getBlockNum() + " != " + BLOCKNUM);
    }
    if(!move.equals(sameMove)) {
      System.out.println("Failed: move.equals(sameMove) should not be " + move.equals(sameMove));
    }
    if(move.equals(diffMove)) {
      System.out.println("Failed: move.equals(diffMove) should not be " + move.equals(diffMove));
    }
    System.out.println("-----------------------");
    System.out.println("Tests Finished.");
  }

}
