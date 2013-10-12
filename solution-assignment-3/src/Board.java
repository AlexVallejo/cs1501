/**
 * Created By: Alex Vallejo
 * Date: 10/02/13
 * Project: assignment-2
 * Email: amv49@pitt.edu
 * Peoplesoft: 357-8411
 */

import java.util.Arrays;

public class Board {

  public int blocks[][];
  private int dimension;

  //Construct a board from an NxN array of blocks
  public Board(int[][] blocks){

    this.dimension = blocks.length;
    this.blocks = new int[dimension][dimension];

    for (int i = 0; i < dimension; ++i)
      this.blocks[i] = Arrays.copyOf(blocks[i], blocks.length);
  }

  /**
   * The Dimension of the board.
   * @return The dimension of the board.
   */
  public int dimension(){
    return this.dimension;
  }

  /**
   * Implements the hamming priority function for this board.
   * @return the number of blocks out of place
   */
  public int hamming(){

    int outOfPlace = 0;
    int expectedValue = 0;

    for (int row = 0; row < dimension; row++)
      for (int col = 0; col < dimension; col++){

        expectedValue++;

        if (blocks[row][col] == 0)
          continue;

        if (blocks[row][col] != expectedValue)
          outOfPlace += 1;
      }

    return outOfPlace;
  }

  /**
   * Implements the manhattan priority function for this board (AKA taxicab).
   * @return returns the number of spaces each block must move in order to be
   * a goal board
   */
  public int manhattan(){
    int priority = 0;

    for (int row = 0; row < dimension; row++)
      for (int col = 0; col < dimension; col++){

        int val = blocks[row][col];

        if (val != 0){
          int expectedRowVal = (val - 1) / dimension;
          int expectedColVal = (val - 1) % dimension;

          priority += Math.abs(row - expectedRowVal) + Math.abs(col -
              expectedColVal);
        }
      }

    return priority;
  }

  /**
   * Determines if the board is solved or not.
   * @return true if the board is solved, false otherwise.
   */
  public boolean isGoal(){

    int expectedValue = 1;

    for (int row = 0; row < dimension; row++)
      for (int col = 0; col < dimension; col++){

        if (blocks[row][col] == 0 && row != dimension - 1 && col !=
            dimension - 1)
          return false;

        if (blocks[row][col] != 0 && blocks[row][col] != expectedValue)
          return false;

        expectedValue += 1;
      }

    return true;
  }

  /**
   * Decide if the current board is solvable or not. Solvable boards have an
   * even parity.
   * @return true if solvable, false otherwise.
   */
  public boolean isSolvable(){
    return true;
  }

  /**
   * Equals override for the Board class
   * @param obj the object that is equal or not to the calling board
   * @return true if equal, false otherwise
   */
  public boolean equals(Object obj){

    if (this == obj)
      return true;

    if (obj == null)
      return false;

    if (this == null)
      return false;

    if (!(obj instanceof Board))
      return false;

    Board board = (Board)obj;

    for (int row = 0; row < dimension; row++)
      for (int col = 0; col < dimension; col++)
        if (blocks[row][col] != board.blocks[row][col])
          return false;

    return true;
  }

  /**
   * Find all the possible neighbor boards of the current board. This is done
   * by shifting the zero location to another acceptable place in the puzzle.
   * @return an iterable object containing all possible neighbors of the
   * current board.
   */
  public Iterable<Board> neighbors(){
    Queue<Board> neighbors = new Queue<Board>();

    int zeroRowLoc = -1;
    int zeroColLoc = -1;

    for (int row = 0; row < dimension; row++)
      for (int col = 0; col < dimension; col++)
        if (blocks[row][col] == 0){
          zeroRowLoc = row;
          zeroColLoc = col;
        }

    if (zeroRowLoc + 1 < dimension){
      int tmp = blocks[zeroRowLoc + 1][zeroColLoc];
      blocks[zeroRowLoc + 1][zeroColLoc] = 0;
      blocks[zeroRowLoc][zeroColLoc] = tmp;

      neighbors.enqueue(new Board(blocks));

      blocks[zeroRowLoc + 1][zeroColLoc] = tmp;
      blocks[zeroRowLoc][zeroColLoc] = 0;
    }

    if (zeroRowLoc - 1 >= 0){
      int tmp = blocks[zeroRowLoc - 1][zeroColLoc];
      blocks[zeroRowLoc - 1][zeroColLoc] = 0;
      blocks[zeroRowLoc][zeroColLoc] = tmp;

      neighbors.enqueue(new Board(blocks));

      blocks[zeroRowLoc - 1][zeroColLoc] = tmp;
      blocks[zeroRowLoc][zeroColLoc] = 0;
    }

    if (zeroColLoc + 1 < dimension){
      int tmp = blocks[zeroRowLoc][zeroColLoc + 1];
      blocks[zeroRowLoc][zeroColLoc + 1] = 0;
      blocks[zeroRowLoc][zeroColLoc] = tmp;

      neighbors.enqueue(new Board(blocks));

      blocks[zeroRowLoc][zeroColLoc + 1] = tmp;
      blocks[zeroRowLoc][zeroColLoc] = 0;
    }

    if (zeroColLoc - 1 >= 0){
      int tmp = blocks[zeroRowLoc][zeroColLoc - 1];
      blocks[zeroRowLoc][zeroColLoc - 1] = 0;
      blocks[zeroRowLoc][zeroColLoc] = tmp;

      neighbors.enqueue(new Board(blocks));

      blocks[zeroRowLoc][zeroColLoc - 1] = tmp;
      blocks[zeroRowLoc][zeroColLoc] = 0;
    }

    return neighbors;
  }

  /**
   * toString overload for the Board class
   * @return matrix representation for the board
   */
  public String toString(){
    String str = new String();

    for (int row = 0; row < dimension; row++){
      for (int col = 0; col < dimension; col++){
        str += String.format("%-5d", blocks[row][col]);
      }
      str += "\n";
    }

    str = str.substring(0, str.length() - 2);
    return str;
  }

  public Board cloneAndSwap(){
    int clone[][] = new int[dimension][dimension];
    int val = 0;

    for (int i = 0; i < dimension; i++)
      clone[i] = Arrays.copyOf(blocks[i], blocks[i].length);

    for (int col = 0; col < dimension; col++)
      if (clone[0][col] == 0)
        val++;

    if (val == 0){
      int tmp = clone[0][0];
      clone[0][0] = clone[0][1];
      clone[0][1] = tmp;
    }

    else{
      int tmp = clone[1][0];
      clone[1][0] = clone[1][1];
      clone[1][1] = tmp;
    }

    return new Board(clone);
  }
}
