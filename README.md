# **47b-Project**
Blocks project for Berkeley 47B

## **Representation:**

*   Each block will be a Block object
*   The board state is represented in two forms: a list of Block objects and a 2D array of integers.  
    This is contained in the Board object
*   The problem as a whole will be represented by Nodes each containing:
    *   Current Board state
    *   Reference to parent Node
    *   g-value and h-value to compute f-value for IDA* (or some other) algorithm held within each board object

## **Check Success:**

*   Use 2-D array representation in Board objects to compare Node state to goal state

## **Generate Possible Moves:**

*   Proceed block by block
*   First collect row available moves
*   Then collect column available moves

## **Choose Move:**

*   Choose the move with the lowest heuristic value (or highest depending on the search algorithm)

## **Change Game State:**

*   Moves are represented in the Move object; it contains a boolean indicating column or row movement  
    as well as which block to move and how many spaces to move it.
*   A Move instance can be passed into a Board constructor to create a new Board instance with the effect of  
    that move.

## **Store Previous Game States:**

*   Certain graph search algorithms like A* use a queue to remember previously visited nodes to  
    prevent infinite cycling; however, for larger sample cases a depth first search would be better if you  
    are remembering previous states (to conserve space complexity).

## **Algorithmic Improvements:**

*  I updated the hashing function so that it was only computed once and used the 2d array to compute the hash,
   making it far more efficient than before.
*  I implemented a block copy method that only copies the block that is changed. All other blocks in the list
   are kept as the same references as the previous node.
*  Lastly I implemented a new heuristic that, given a reasonable amount of blocks, calculates the number of moves it will
   take each block to reach its goal state by counting the number of blocks in its way both column-wise and row-wise.
   This allows the A* algorithm to make smarter decisions as to how it should proceed in searching the game graph.

## **Passed Hard Benchmarks:**

*  Tests 1 through 4; Tests 8, 9; Test 13; Tests 15-17.
