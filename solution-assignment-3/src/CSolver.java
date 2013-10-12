import java.util.*;

public class CSolver {
  private final MinPQ<Node> pqOriginal;
  private final MinPQ<Node> pqTwin;
  private final Stack<CBoard> shortestCBoardSequence;
  private int totalMoves = 0;
  private int shortestNumOfMove = 0;
  private boolean solved = false;

  public CSolver(CBoard initial)            // find a solution to the initial CBoard (using the A* algorithm)
  {
    shortestCBoardSequence = new Stack<CBoard>();
    pqOriginal = new MinPQ<Node>();
    pqTwin = new MinPQ<Node>();
    pqOriginal.insert(new Node(totalMoves, initial, null));
    pqTwin.insert(new Node(totalMoves, initial.twin(), null));

    Queue<CBoard> neighborCBoards = new Queue<CBoard>();
    CBoard previousCBoard = initial;
    Node originalNode;
    Node twinNode;

    outer:
    while (!pqOriginal.isEmpty() && !pqTwin.isEmpty()) {
      originalNode = pqOriginal.delMin();
      neighborCBoards = (Queue<CBoard>) originalNode.CBoard.neighbors();
      if (originalNode.prev != null) {
        previousCBoard = originalNode.prev.CBoard;
      }
      for (CBoard neighborCBoard : neighborCBoards) {
        if (!previousCBoard.equals((CBoard) neighborCBoard)) {
          totalMoves = originalNode.numMoves + 1;
          pqOriginal.insert(new Node(totalMoves, neighborCBoard, originalNode));
        }
      }

      if (originalNode.CBoard.isGoal()) {
        solved = true;
        shortestQueue(originalNode);
        shortestCBoardSequence.push(initial);
        break outer;
      }

      twinNode = pqTwin.delMin();
      neighborCBoards = (Queue<CBoard>) twinNode.CBoard.neighbors();
      if (twinNode.prev != null) {
        previousCBoard = twinNode.prev.CBoard;
      }
      for (CBoard neighborCBoard : neighborCBoards) {
        if (!previousCBoard.equals((CBoard) neighborCBoard)) {
          pqTwin.insert(new Node(totalMoves, neighborCBoard, twinNode));
        }
      }
      if (twinNode.CBoard.isGoal()) {
        shortestNumOfMove = -1;
        solved = false;
        break outer;
      }

    }
  }

  private void shortestQueue(Node original) {

    while (original.prev != null) {
      shortestCBoardSequence.push(original.CBoard);
      original = original.prev;
      shortestNumOfMove++;

    }
  }

  public boolean isSolvable()             // is the initial CBoard solvable?
  {
    return solved;
  }

  public int moves()                      // min number of moves to solve initial CBoard; -1 if no solution
  {
    return this.shortestNumOfMove;
  }

  public Iterable<CBoard> solution()       // sequence of CBoards in a shortest solution; null if no solution
  {
    if (shortestNumOfMove != -1)
      return shortestCBoardSequence;
    else
      return null;
  }

  private class Node implements Comparable<Node> {
    private int numMoves;
    private CBoard CBoard;
    private Node prev;

    public Node(int numMoves, CBoard CBoard, Node prev) {
      this.numMoves = numMoves;
      this.CBoard = CBoard;
      this.prev = prev;

    }

    public int compareTo(Node that) {
      if (this.CBoard.manhattan() + this.numMoves > that.CBoard.manhattan() + that.numMoves)
        return 1;
      else if (this.CBoard.manhattan() + this.numMoves < that.CBoard.manhattan() + that.numMoves)
        return -1;
      else return 0;
    }

  }

  public static void main(String[] args)  // solve a slider puzzle (given below)
  {
    long startTime = System.currentTimeMillis();

    // create initial CBoard from file
    In in = new In(args[0]);
    int N = in.readInt();
    int[][] blocks = new int[N][N];
    for (int i = 0; i < N; i++)
      for (int j = 0; j < N; j++)
        blocks[i][j] = in.readInt();
    CBoard initial = new CBoard(blocks);

    // solve the puzzle
    CSolver CSolver = new CSolver(initial);

    // print solution to standard output
    if (!CSolver.isSolvable())
      StdOut.println("No solution possible");
    else {
      StdOut.println("Minimum number of moves = " + CSolver.moves());
      for (CBoard CBoard : CSolver.solution())
        StdOut.println(CBoard);
    }

    long endTime   = System.currentTimeMillis();
    double totalTime = (double)(endTime - startTime)/1000.0;
    System.out.println("Time elapsed => " + totalTime);
  }
}
