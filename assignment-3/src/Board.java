/**
 * Created By: Alex Vallejo
 * Date: 10/02/13
 * Project: assignment-2
 * Email: amv49@pitt.edu
 * Peoplesoft: 357-8411
 */

import java.util.ArrayList;

public class Board {

  private int squares[][];

  //Construct a board from an NxN array of blocks
  public Board(int[][] blocks){

    if (blocks.length != blocks[0].length){
      System.out.println("Sorry, the dimensions of the board must be equal.");
      System.exit(0);
    }

    this.squares = blocks;

  }

  /**
   * The Dimension of the board.
   * @return The dimension of the board.
   */
  public int dimension(){
    return squares.length;
  }

  /**
   * Implements the hamming priority function for this board.
   * @return the number of blocks out of place
   */
  public int hamming(){

    int outOfPlace = 0;
    int expectedValue = 1;

    for (int row = 0; row < squares.length; row++)
      for (int col = 0; col < squares[0].length; col++){

        if (squares[row][col] != expectedValue)
          outOfPlace += 1;

        expectedValue += 1;
      }

    //Return outOfPlace - 1 because there will always be at least one value
    // out of place due to the 0 in the board which represents the blank space
    return outOfPlace - 1;
  }

  //sum of manhattan blocks between blocks and goal
  public int manhattan(){
    return 0;
  }

  /**
   * Determines if the board is solved or not.
   * @return true if the board is solved, false otherwise.
   */
  public boolean isGoal(){

    int expectedValue = 1;

    for (int row = 0; row < squares.length; row++)
      for (int col = 0; col < squares[0].length; col++){

        if (squares[row][col] != expectedValue)
          return false;

        expectedValue += 1;
      }

    return true;
  }

  public boolean isSolvable(){
    return false;
  }

  //does this board equal y?
  public boolean equals(Object y){
    return false;
  }

  //Place all boards onto your iterable Queue
  public Iterable<Board> neighbors(){
    return new ArrayList<Board>();
  }

  //String representations of the board (in the output format specified below)
  public String toString(){
    return new String();
  }

}
