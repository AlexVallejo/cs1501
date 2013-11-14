public class SpecialDriver {

  public static void main(String[] args){
    long startTime = System.currentTimeMillis();

    if (!args[0].equals("-h") && !args[0].equals("-m")){
      System.out.println("You must use a flag (-h or -m)");
      System.exit(0);
    }

    // create initial board from file
    In in = new In(args[1]);
    int N = in.readInt();

    int blocks[][] = new int[N][N];

    for (int i = 0; i < N; i++)
      for (int j = 0; j < N; j++)
        blocks[i][j] = in.readInt();

    Board initial = new Board(blocks);
    nPuzzleSolver puzzle;

    if (args[0].equals("-h")){
      System.out.println("\nUsing the hamming function...\n");
      puzzle = new HammingSolver(initial);
    }

    else{
      System.out.println("\nUsing the manhattan function...\n");
      puzzle = new Solver(initial);
    }

    // print solution to standard output
    if (!puzzle.isSolvable())
      StdOut.println("No solution possible");

    else {
      StdOut.println("Minimum number of moves = " + puzzle.moves());

      for (Board Board : puzzle.solution())
        StdOut.println(Board + "\n");
    }

    long endTime   = System.currentTimeMillis();
    double totalTime = (double)(endTime - startTime)/1000.0;
    System.out.println("Time elapsed: " + totalTime + "s");
  }
}
