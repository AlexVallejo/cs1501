import java.util.*;

public class Solver {
  private PriorityQueue<mNode> pq;
  private PriorityQueue<mNode> clonepq;
  private LinkedList<Board> goalSequence;
  private int totalMoves = 0;
  private int minMoves = 0;
  private boolean solvable = false;

  int moves = 0;


  public Solver(Board initial)            // find a solution to the initial Board (using the A* algorithm)
  {
    goalSequence = new LinkedList<Board>();
    pq = new PriorityQueue<mNode>();
    clonepq = new PriorityQueue<mNode>();

    pq.add(new mNode(initial,totalMoves,  null));
    clonepq.add(new mNode(initial.cloneAndSwap(), totalMoves,  null));

    Board prevBoard = initial;

    while (!pq.isEmpty() && !clonepq.isEmpty()) {
      mNode min = pq.remove();
      Iterable<Board> neighbors = min.Board.neighbors();

      if (min.prev != null)
        prevBoard = min.prev.Board;

      for (Board board : neighbors) {
        if (!prevBoard.equals(board)) { //fixme not adding to pq
        pq.add(new mNode(board, min.numMoves + 1, min));
        }
      }

      if (min.Board.isGoal()){
        this.moves = min.numMoves;
        solvable = true;
        goalSequence(min);
        break;
      }

      mNode cloneMin = clonepq.remove();
      Iterable<Board> cloneNeighbors = cloneMin.Board.neighbors();

      if (cloneMin.prev == null)
        prevBoard = cloneMin.Board;
      else
        prevBoard = cloneMin.prev.Board;

      for (Board board : cloneNeighbors)
        if (!prevBoard.equals(board))
        clonepq.add(new mNode(board, cloneMin.numMoves + 1, cloneMin));

      if (cloneMin.Board.isGoal()){
        solvable = false;
        this.moves = -1;
        break;
      }
    }
  }

  private void goalSequence(mNode step) {

    while (step.prev != null) {
      goalSequence.addFirst(step.Board);
      step = step.prev;
      minMoves++;
    }
  }

  public boolean isSolvable(){
    return this.solvable;
  }

  public int moves(){
    return this.minMoves;
  }

  public Iterable<Board> solution(){
    if (this.minMoves != -1)
      return goalSequence;

    else
      return null;
  }

  private class mNode implements Comparable<mNode> {
    private int numMoves;
    private Board Board;
    private mNode prev;

    public mNode(Board Board, int numMoves, mNode prev) {
      this.numMoves = numMoves;
      this.Board = Board;
      this.prev = prev;

    }

    public int compareTo(mNode that) {
      if (this.Board.manhattan() + this.numMoves > that.Board.manhattan() +
          that.numMoves)
        return 1;

      else if (this.Board.manhattan() + this.numMoves < that.Board.manhattan()
          + that.numMoves)
        return -1;

      else return 0;
    }

  }

  public static void main(String[] args)  // solve a slider puzzle (given below)
  {
    long startTime = System.currentTimeMillis();

    // create initial Board from file
    In in = new In(args[0]);
    int N = in.readInt();

    int blocks[][] = new int[N][N];

    for (int i = 0; i < N; i++)
      for (int j = 0; j < N; j++)
        blocks[i][j] = in.readInt();

    Board initial = new Board(blocks);
    Solver Solver = new Solver(initial);

    // print solution to standard output
    if (!Solver.isSolvable())
      StdOut.println("No solution possible");

    else {
      StdOut.println("Minimum number of moves = " + Solver.moves());

      for (Board Board : Solver.solution())
        StdOut.println(Board);
    }

    long endTime   = System.currentTimeMillis();
    double totalTime = (double)(endTime - startTime)/1000.0;
    System.out.println("Time elapsed => " + totalTime);
  }
}
