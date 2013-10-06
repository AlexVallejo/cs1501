/**
 * Created By: Alex Vallejo
 * Date: 10/02/13
 * Project: assignment-2
 * Email: amv49@pitt.edu
 * Peoplesoft: 357-8411
 */

import java.util.ArrayList;

public class Board {

  public int squares[][];
  private int dimension;

  //Construct a board from an NxN array of blocks
  public Board(int[][] blocks){

    if (blocks.length != blocks[0].length){
      System.out.println("Sorry, the board must be square.");
      System.exit(0);
    }

    this.squares = blocks;
    this.dimension = dimension;

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
    int expectedValue = 1;

    for (int row = 0; row < dimension; row++)
      for (int col = 0; col < squares[0].length; col++){

        if (squares[row][col] == 0)
          continue;

        if (squares[row][col] != expectedValue)
          outOfPlace += 1;

        expectedValue += 1;
      }

    return outOfPlace;
  }

  //sum of manhattan blocks between blocks and goal
  public int manhattan(){

    int pos = 1;
    int priority = 0;

    for (int row = 0; row < dimension; row++)
      for (int col = 0; col < dimension; col++){

        if (squares[row][col] == 0 || squares[row][col] == pos)
          continue;

        int posDist = 0;

        for (int rowDist = 0; rowDist < dimension; rowDist++)
          for (int colDist = 0; colDist < dimension; colDist++){
            if (squares[row][col] == pos)
              priority += ( Math.abs(row - rowDist) + Math.abs(col - colDist) );
            posDist++;
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
      for (int col = 0; col < squares[0].length; col++){

        if (squares[row][col] == 0 && row != dimension && col !=
            dimension)
          return false;

        if (squares[row][col] != expectedValue)
          return false;

        expectedValue += 1;
      }

    return true;
  }

  public boolean isSolvable(){

    int boardParity = 0, expectedVal = 0;

    for (int row = 0; row < dimension; row++)
      for (int col = 0; col < dimension; col++){

        if (squares[row][col] == 0)
          if (row != dimension && col != dimension)
            boardParity += parity(row, col, dimension * dimension);

        if (expectedVal != squares[col][row])
          boardParity += parity(row, col, squares[row][col]);

        expectedVal++;
      }

    //todo actually make a decision on the parity

    return true;
  }

  private int parity(int row, int col, int val){
    int boardParity = 0;

    for (row = row; row < dimension; row++)
      for (col = col; col < dimension; col++)
        if (val <= squares[row][col] && squares[row][col] != 0)
          boardParity++;

    return boardParity;
  }

  //todo can I use board
  //does this board equal y?
  public boolean equals(Board board){

    for (int row = 0; row < dimension; row++)
      for (int col = 0; col < squares[0].length; col++)
        if (squares[row][col] != board.squares[row][col])
          return false;

    return true;
  }

  //Place all boards onto your iterable Queue
  public Iterable<Board> neighbors(){
    Queue<Board> neighbors = new Queue<Board>();

    int zeroRowLoc = -1;
    int zeroColLoc = -1;

    for (int row = 0; row < dimension; row++)
      for (int col = 0; col < dimension; col++)
        if (squares[row][col] == 0){
          zeroRowLoc = row;
          zeroColLoc = col;
          break;
        }

    //todo check all neighbors of the zero location
    if (squares[zeroRowLoc][zeroColLoc] != null)
      neighbors.enqueue();

    return neighbors;

  }

  //String representations of the board (in the output format specified below)
  public String toString(){
    return new String();
  }

  public Board copy(){
    int boardCopy[][] = new int[dimension][dimension];

    for (int i = 0; i < dimension; i++)
      for (int j = 0; j < dimension; j++)
        boardCopy[i][j] = squares[i][j];

    return new Board(boardCopy);
  }

}
