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
    return curBoard.isSolvable();
  }

  public int moves(){
    return moves;
  }

  public Iterable<Board> solution(){
    return new ArrayList<Board>();
  }

  public static void main(String args[]){

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
