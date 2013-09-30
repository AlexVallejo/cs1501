#8 Puzzle

Write a program to solve the 8-puzzle problem (and its natural generalizations) using the A* search algorithm.

###The problem. 
The 8-puzzle problem is a puzzle invented and popularized by Noyes Palmer Chapman in the 1870s. It is played on a 3-by-3 grid with 8 square blocks labeled 1 through 8 and a blank square. Your goal is to rearrange the blocks so that they are in order, using as few moves as possible. You are permitted to slide blocks horizontally or vertically into the blank square. The following shows a sequence of legal moves from an initial board (left) to the goal board (right).
 
###Best-first search. 
Now, we describe a solution to the problem that illustrates a general artificial intelligence methodology known as the A* search algorithm. We define a search node of the game to be a board, the number of moves made to reach the board, and the previous search node. First, insert the initial search node (the initial board, 0 moves, and a null previous search node) into an PQ. Define the Node class as a private inner-class of the Solver class (see below), where Node implements the Comparable interface. Note: Node is not generic! Then, delete from the PQ the search node with the minimum priority, and insert into the PQ of all neighboring search nodes (those that can be reached in one move from the removed search node). Repeat this procedure until the search node that has been removed corresponds to a goal board. The success of this approach hinges on the choice of priority function for a search node. We consider two priority functions:

 - Hamming priority function. The number of blocks in the wrong position, plus the number of moves made so far to get to the search node. Intuitively, a search node with a small number of blocks in the wrong position is close to the goal, and we prefer a search node that have been reached using a small number of moves.
 - Manhattan priority function. The sum of the Manhattan distances (sum of the vertical and horizontal distance) from the blocks to their goal positions, plus the number of moves made so far to get to the search node.

For example, the Hamming and Manhattan priorities of the initial search node below are 5 and 10, respectively.

 
We make a key observation: To solve the puzzle from a given search node on the PQ, the total number of moves we need to make (including those already made) is at least its priority, using either the Hamming or Manhattan priority function. (For Hamming priority, this is true because each block that is out of place must move at least once to reach its goal position. For Manhattan priority, this is true because each block must move its Manhattan distance from its goal position. Note that we do not count the blank square when computing the Hamming or Manhattan priorities.) Consequently, when the goal board is removed, we have discovered not only a sequence of moves from the initial board to the goal board, but one that makes the fewest number of moves. (Challenge for the mathematically inclined: prove this fact.)

A critical optimization. Best-first search has one annoying feature: search nodes corresponding to the same board are added to the PQ many times. To reduce unnecessary exploration of useless search nodes, when considering the neighbors of a search node, don't add a neighbor if its board is the same as the board of the previous search node.
   
###Detecting infeasible puzzles. 
Not all initial boards can lead to the goal board such as the one below.
                                    
To detect such situations, use the fact that boards are divided into two equivalence classes with respect to reachability:
 1. those that lead to the goal board
 2. those that cannot lead to the goal board. Moreover, we can identify in which equivalence class belongs by considering its parity.

###Odd board size. 
The parity of the number of inversions of the N2 - 1 blocks.
 
###Even board size. 
The parity of the number of inversions of the N2 - 1 blocks plus the row in which the blank square plus one.

###Board and Solver data types.
Organize your program by creating an immutable data type Board with the following API:
```
public class Board{
  public Board(int[][] blocks) //Construct a board from an NxN array of blocks
  
  public int dimension() //board dimension N
  
  public int hamming() //number of blocks out of place
  
  public int manhattan() //sum of manhattan blocks between blocks and goal
  
  public boolean isGoal()
  
  public boolean isSolvable()
  
  public boolean equals(Object y) //does this board equal y?
  
  public Iterable<Board> neighbors() //Place all boards onto your iterable Queue (Assignment 1)
  
  public String toString() //String representations of the board (in the output format specified below)
```

and a data type Solver with the following API:

```
public class Solver {
  public Solver(Board initial)
  
  public boolean isSolvable()
  
  public int moves()
  
  public Iterable<Board> solution()
  
  public static void main(String args[])
```
 
Throw an java.lang.IllegalArgumentException if the initial board is not solvable. To implement the A* algorithm, you must use the PQ<T> data type for the priority queues.
 
##Solver test client. 
Use the following test client to read a puzzle from a file (specified as a command-line argument) and print the solution to standard output.
```
public static void main(String[] args) {
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
    	System.out.println(board);
  }
}
```
                         
###Input and output formats. 
The input and output format for a board is the board dimension N followed by the N-by-N initial board, using 0 to represent the blank square. As an example,

![Example output](http://alex-vallejo.com/images/cs-1501-a3-ex-output.png)
                                                 
  Your program should work correctly for arbitrary N-by-N boards (for any 1 ≤ N < 128), even if it is too slow to solve some of them in a reasonable amount of time.
Deliverables
Submit the files Board.java and Solver.java (with the Hamming priority).  Your may not call any library functions other than those in java.lang or java.util. You must use the PQ<T> data type for the priority queue.
Here are the contents of the priority queue just before removing each node when using the Manhattan priority function on puzzle04.txt.
   