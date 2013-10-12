import java.util.*;

public class Solver {
  private final PriorityQueue<mNode> pq;
  private final PriorityQueue<mNode> clonepq;
  LinkedList<Board> goalSequence;
  private int totalMoves = 0;
  private int minMoves = 0;
  private boolean solvable = false;

  public Solver(Board initial)            // find a solution to the initial Board (using the A* algorithm)
  {
    goalSequence = new LinkedList<Board>();
    pq = new PriorityQueue<mNode>();
    clonepq = new PriorityQueue<mNode>();
    pq.add(new mNode(initial,totalMoves,  null));
    clonepq.add(new mNode(initial.cloneAndSwap(), totalMoves,  null));

    Queue<Board> neighborCBoards = new Queue<Board>();
    Board previousBoard = initial;
    mNode originalMNode;
    mNode twinMNode;

    outer:
    while (!pq.isEmpty() && !clonepq.isEmpty()) {
      originalMNode = pq.remove();
      neighborCBoards = (Queue<Board>) originalMNode.Board.neighbors();

      if (originalMNode.prev != null) {
        previousBoard = originalMNode.prev.Board;
      }

      for (Board neighborBoard : neighborCBoards) {
        if (!previousBoard.equals((Board) neighborBoard)) {
          totalMoves = originalMNode.numMoves + 1;
          pq.add(new mNode(neighborBoard, totalMoves, originalMNode));
        }
      }

      if (originalMNode.Board.isGoal()) {
        solvable = true;
        goalSequence(originalMNode);
        goalSequence.push(initial);
        break outer;
      }

      twinMNode = clonepq.remove();
      neighborCBoards = (Queue<Board>) twinMNode.Board.neighbors();
      if (twinMNode.prev != null) {
        previousBoard = twinMNode.prev.Board;
      }
      for (Board neighborBoard : neighborCBoards) {
        if (!previousBoard.equals((Board) neighborBoard)) {
          clonepq.add(new mNode(neighborBoard, totalMoves, twinMNode));
        }
      }
      if (twinMNode.Board.isGoal()) {
        minMoves = -1;
        solvable = false;
        break outer;
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
