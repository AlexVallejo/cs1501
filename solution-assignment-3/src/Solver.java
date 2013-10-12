import java.util.*;

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
    pqOriginal.insert(new Node(totalMoves, initial, null));
    pqTwin.insert(new Node(totalMoves, initial.cloneAndSwap(), null));

    Queue<Board> neighborCBoards = new Queue<Board>();
    Board previousBoard = initial;
    Node originalNode;
    Node twinNode;

    outer:
    while (!pqOriginal.isEmpty() && !pqTwin.isEmpty()) {
      originalNode = pqOriginal.delMin();
      neighborCBoards = (Queue<Board>) originalNode.Board.neighbors();

      if (originalNode.prev != null) {
        previousBoard = originalNode.prev.Board;
      }

      for (Board neighborBoard : neighborCBoards) {
        if (!previousBoard.equals((Board) neighborBoard)) {
          totalMoves = originalNode.numMoves + 1;
          pqOriginal.insert(new Node(totalMoves, neighborBoard, originalNode));
        }
      }

      if (originalNode.Board.isGoal()) {
        solved = true;
        shortestQueue(originalNode);
        shortestBoardSequence.push(initial);
        break outer;
      }

      twinNode = pqTwin.delMin();
      neighborCBoards = (Queue<Board>) twinNode.Board.neighbors();
      if (twinNode.prev != null) {
        previousBoard = twinNode.prev.Board;
      }
      for (Board neighborBoard : neighborCBoards) {
        if (!previousBoard.equals((Board) neighborBoard)) {
          pqTwin.insert(new Node(totalMoves, neighborBoard, twinNode));
        }
      }
      if (twinNode.Board.isGoal()) {
        shortestNumOfMove = -1;
        solved = false;
        break outer;
      }
    }
  }

  private void shortestQueue(Node original) {

    while (original.prev != null) {
      shortestBoardSequence.push(original.Board);
      original = original.prev;
      shortestNumOfMove++;

    }
  }

  public boolean isSolvable()             // is the initial Board solvable?
  {
    return solved;
  }

  public int moves()                      // min number of moves to solve initial Board; -1 if no solution
  {
    return this.shortestNumOfMove;
  }

  public Iterable<Board> solution()       // sequence of CBoards in a shortest solution; null if no solution
  {
    if (shortestNumOfMove != -1)
      return shortestBoardSequence;
    else
      return null;
  }

  private class Node implements Comparable<Node> {
    private int numMoves;
    private Board Board;
    private Node prev;

    public Node(int numMoves, Board Board, Node prev) {
      this.numMoves = numMoves;
      this.Board = Board;
      this.prev = prev;

    }

    public int compareTo(Node that) {
      if (this.Board.manhattan() + this.numMoves > that.Board.manhattan() + that.numMoves)
        return 1;
      else if (this.Board.manhattan() + this.numMoves < that.Board.manhattan() + that.numMoves)
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
    int[][] blocks = new int[N][N];
    for (int i = 0; i < N; i++)
      for (int j = 0; j < N; j++)
        blocks[i][j] = in.readInt();
    Board initial = new Board(blocks);

    // solve the puzzle
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
