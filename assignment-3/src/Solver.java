/**
 * Created By: Alex Vallejo
 * Date: 10/02/13
 * Project: assignment-2
 * Email: amv49@pitt.edu
 * Peoplesoft: 357-8411
 */

import java.util.ArrayList;

public class Solver {

  private Board initial;
  private Board curBoard;
  private int moves;

  public Solver(Board initial){
    this.initial = initial;
    this.moves = 0;
  }

  public boolean isSolvable(){
    //copy board
    //create threads
    //execute threads
    //if one finishes, board solvable, else not
    return curBoard.isSolvable();
  }

  public int moves(){
    return moves;
  }

  public Iterable<Board> solution(){
    return new ArrayList<Board>();
  }

  public static void main(String args[]){

    // create initial board from file
    In in = new In(args[0]);
    int N = in.readInt();
    int[][] blocks = new int[N][N];

    for (int i = 0; i < N; i++)
      for (int j = 0; j < N; j++)
        blocks[i][j] = in.readInt();

    Board initial = new Board(blocks);      // solve the puzzle
    Solver solver = new Solver(initial);    // print solution to standard output

    if (!initial.isSolvable())
      System.out.println("No solution possible");

    else {
      System.out.println("Minimum number of moves = " + solver.moves());

      for (Board board : solver.solution())
        System.out.println(board);
    }
  }

  private class Node implements Comparable<Node>{

    Node prev;
    int numMoves;
    Board board;

    public Node(Board board, int numMoves, Node prev){
      this.board = board;
      this.numMoves = numMoves;
      this.prev = prev;
    }

    public int compareTo(Node other){
      return this.numMoves - other.numMoves;
    }
  }
}
