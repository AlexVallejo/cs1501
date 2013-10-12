import java.util.*;

public class CSolver {
  private final MinPQ<SearchNode> pqOriginal;
  private final MinPQ<SearchNode> pqTwin;
  private final Stack<CBoard> shortestCBoardSequence;
  private int totalMoves = 0;
  private int shortestNumOfMove = 0;
  private boolean isSolve = false;

  public CSolver(CBoard initial)            // find a solution to the initial CBoard (using the A* algorithm)
  {
    shortestCBoardSequence = new Stack<CBoard>();
    pqOriginal = new MinPQ<SearchNode>();
    pqTwin = new MinPQ<SearchNode>();
    pqOriginal.insert(new SearchNode(totalMoves, initial, null));
    pqTwin.insert(new SearchNode(totalMoves, initial.twin(), null));

    Queue<CBoard> neighborCBoards = new Queue<CBoard>();
    CBoard previousCBoard = initial;
    SearchNode originalNode;
    SearchNode twinNode;

    outer:
    while (!pqOriginal.isEmpty() && !pqTwin.isEmpty()) {
      originalNode = pqOriginal.delMin();
      neighborCBoards = (Queue<CBoard>) originalNode.CBoard.neighbors();
      if (originalNode.previousNode != null) {
        previousCBoard = originalNode.previousNode.CBoard;
      }
      for (CBoard neighborCBoard : neighborCBoards) {
        if (!previousCBoard.equals((CBoard) neighborCBoard)) {
          totalMoves = originalNode.numOfMoves + 1;
          pqOriginal.insert(new SearchNode(totalMoves, neighborCBoard, originalNode));
        }
      }

      if (originalNode.CBoard.isGoal()) {
        isSolve = true;
        shortestQueue(originalNode);
        shortestCBoardSequence.push(initial);
        break outer;
      }

      twinNode = pqTwin.delMin();
      neighborCBoards = (Queue<CBoard>) twinNode.CBoard.neighbors();
      if (twinNode.previousNode != null) {
        previousCBoard = twinNode.previousNode.CBoard;
      }
      for (CBoard neighborCBoard : neighborCBoards) {
        if (!previousCBoard.equals((CBoard) neighborCBoard)) {
          pqTwin.insert(new SearchNode(totalMoves, neighborCBoard, twinNode));
        }
      }
      if (twinNode.CBoard.isGoal()) {
        shortestNumOfMove = -1;
        isSolve = false;
        break outer;
      }

    }
  }

  private void shortestQueue(SearchNode original) {

    while (original.previousNode != null) {
      shortestCBoardSequence.push(original.CBoard);
      original = original.previousNode;
      shortestNumOfMove++;

    }
  }

  public boolean isSolvable()             // is the initial CBoard solvable?
  {
    return isSolve;
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

  private class SearchNode implements Comparable<SearchNode> {
    private int numOfMoves;
    private CBoard CBoard;
    private SearchNode previousNode;

    public SearchNode(int numOfMoves, CBoard CBoard, SearchNode previousNode) {
      this.numOfMoves = numOfMoves;
      this.CBoard = CBoard;
      this.previousNode = previousNode;

    }

    public int compareTo(SearchNode that) {
      if (this.CBoard.manhattan() + this.numOfMoves > that.CBoard.manhattan() + that.numOfMoves)
        return 1;
      else if (this.CBoard.manhattan() + this.numOfMoves < that.CBoard.manhattan() + that.numOfMoves)
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
