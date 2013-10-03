/**
 * Created By: Alex Vallejo
 * Date: 10/02/13
 * Project: assignment-2
 * Email: amv49@pitt.edu
 * Peoplesoft: 357-8411
 */
public class Solver {
  public Solver(Board initial){

  }

  public boolean isSolvable(){
    return false;
  }

  public int moves(){
    return 0;
  }

  public Iterable<Board> solution(){
    return new Iterable<Board>();
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
