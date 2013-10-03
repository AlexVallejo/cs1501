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

    Node right;
    Node left;
    Node above;
    Node below;
    int value;

    public Node(Node right, Node left, Node above, Node below, int value){
      this.right = right;
      this.left = left;
      this.above = above;
      this.below = below;

      this.value = value;
    }

    public int compareTo(Node other){
      return this.value - other.value;
    }
  }
}
