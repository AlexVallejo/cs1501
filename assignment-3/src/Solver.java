/**
 * Created By: Alex Vallejo
 * Date: 10/02/13
 * Project: assignment-2
 * Email: amv49@pitt.edu
 * Peoplesoft: 357-8411
 */

import java.util.LinkedList;
import java.util.Stack;

public class Solver {

  private final MinPQ<Node> pqOriginal;
  private final MinPQ<Node> pqTwin;
  private final Stack<Board> shortestBoardSequence;
  private int totalMoves = 0;
  private int shortestNumOfMove = 0;
  private boolean solved = false;

  public Solver(Board initial)            // find a solution to the initial Board (using the A* algorithm)
  {
    shortestBoardSequence = new Stack<Board>();
    pqOriginal = new MinPQ<Node>();
    pqTwin = new MinPQ<Node>();
    pqOriginal.insert(new Node(initial, totalMoves, null));
    pqTwin.insert(new Node(initial.cloneAndSwap(), totalMoves,  null));

    Queue<Board> neighborCBoards = new Queue<Board>();
    Board previousBoard = initial;
    Node originalNode;
    Node twinNode;

    outer:
    while (!pqOriginal.isEmpty() && !pqTwin.isEmpty()) {
      originalNode = pqOriginal.delMin();
      neighborCBoards = (Queue<Board>) originalNode.board.neighbors();

      if (originalNode.prev != null) {
        previousBoard = originalNode.prev.board;
      }

      for (Board neighborBoard : neighborCBoards) {
        if (!previousBoard.equals((Board) neighborBoard)) {
          totalMoves = originalNode.numMoves + 1;
          pqOriginal.insert(new Node(neighborBoard, totalMoves, originalNode));
        }
      }

      if (originalNode.board.isGoal()) {
        solved = true;
        goalSequence(originalNode);
        shortestBoardSequence.push(initial);
        break outer;
      }

      twinNode = pqTwin.delMin();
      neighborCBoards = (Queue<Board>) twinNode.board.neighbors();
      if (twinNode.prev != null) {
        previousBoard = twinNode.prev.board;
      }
      for (Board neighborBoard : neighborCBoards) {
        if (!previousBoard.equals((Board) neighborBoard)) {
          pqTwin.insert(new Node(neighborBoard, totalMoves, twinNode));
        }
      }
      if (twinNode.board.isGoal()) {
        shortestNumOfMove = -1;
        solved = false;
        break outer;
      }
    }
  }

  private void goalSequence(Node step){
    while (step != null){
      shortestBoardSequence.push(step.board);
      step = step.prev;
      shortestNumOfMove++;
    }
  }

  public boolean isSolvable(){
    return solved;
  }

  public int moves(){
    return totalMoves;
  }

  public Iterable<Board> solution(){
    return shortestBoardSequence;
  }

  public static void main(String args[]){

    if (args.length < 1 || args.length > 1){
      System.out.println("Usage: java Solver puzzles.txt");
      System.exit(0);
    }

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
        System.out.println(board + "\n");
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

    public Node(){ }

    public int compareTo(Node other){
      return (this.board.hamming() + this.numMoves) - (other.board.hamming()
          + other.numMoves);
    }
  }
}
