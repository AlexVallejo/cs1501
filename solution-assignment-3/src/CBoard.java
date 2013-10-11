import java.util.*;

public class CBoard {

  private final int dimen;
  private final int[][] blocks;

  private int hammingDistance = 0;
  private int manhattanDistanceSum = 0;


  public CBoard(int[][] blocks)           // construct a CBoard from an N-by-N
  // array of blocks
  // (where blocks[i][j] = block in row i, column j)
  {
    this.dimen = blocks.length;
    this.blocks = new int[dimen][dimen];

    for (int i = 0; i < dimen; ++i) {
      System.arraycopy(blocks[i], 0, this.blocks[i], 0, blocks[i].length);
      //Arrays.copyOf(blocks[i], 2);
    }
  }

  public int dimen()                 // CBoard dimen N
  {
    return dimen;
  }

  public int hamming()                   // number of blocks out of place
  {
    hammingDistance = 0;
    for (int i = 0; i < dimen; ++i) {
      for (int j = 0; j < dimen; ++j) {
        if ((blocks[i][j] != 0) && !(blocks[i][j] == (dimen * i) + (j + 1)))
          hammingDistance++;
      }
    }
    //System.out.println (hammingDistance);
    return hammingDistance;
  }

  public int manhattan()                 // sum of Manhattan distances between blocks and goal
  {
    manhattanDistanceSum = 0;
    for (int x = 0; x < dimen; x++) // x-dimen, traversing rows (i)
      for (int y = 0; y < dimen; y++) { // y-dimen, traversing cols (j)
        int value = blocks[x][y]; // tiles array contains CBoard elements
        if (value != 0) { // we don't compute MD for element 0
          int targetX = (value - 1) / dimen; // expected x-coordinate (row)
          int targetY = (value - 1) % dimen; // expected y-coordinate (col)
          int dx = x - targetX; // x-distance to expected coordinate
          int dy = y - targetY; // y-distance to expected coordinate
          manhattanDistanceSum += Math.abs(dx) + Math.abs(dy);
        }
      }
    return manhattanDistanceSum;
  }

  public boolean isGoal()                // is this CBoard the goal CBoard?
  {
    for (int i = 0; i < dimen; ++i) {
      for (int j = 0; j < dimen; ++j) {
        if (i * j == Math.pow(dimen - 1, 2)) {
          if (!(this.blocks[i][j] == 0)) return false;
        } else if (this.blocks[i][j] != (i * dimen) + (j + 1)) return false;
      }
    }
    return true;
  }

  public CBoard twin()                    // a CBoard obtained by exchanging
  // two adjacent blocks in the same row
  {
    int val = 0;
    int twinBlocks[][] = new int[dimen][dimen];

    for (int i = 0; i < dimen; ++i) {
      for (int j = 0; j < dimen; ++j) {
        System.arraycopy(this.blocks[i], 0, twinBlocks[i], 0, this.blocks[i].length);
        //twinBlocks[i][j] = this.blocks[i][j];
      }
    }

    for (int i = 0; i < dimen; ++i) {
      if (twinBlocks[0][i] == 0) {
        val++;
        break;
      }
    }
    if (val == 0) {
      int tmp = twinBlocks[0][0];
      twinBlocks[0][0] = twinBlocks[0][1];
      twinBlocks[0][1] = tmp;
    } else {
      int tmp = twinBlocks[1][0];
      twinBlocks[1][0] = twinBlocks[1][1];
      twinBlocks[1][1] = tmp;
    }
    return new CBoard(twinBlocks);
  }

  public boolean equals(Object y) {
    if (this == y) return true;
    if (y == null) return false;
    if (y.getClass() != this.getClass()) return false;
    CBoard that = (CBoard) y;
    if (this.blocks.length != that.blocks.length) return false;
    for (int i = 0; i < dimen; ++i) {
      if (this.blocks[i].length != that.blocks[i].length) return false;
      for (int j = 0; j < dimen; ++j) {
        if (this.blocks[i][j] != that.blocks[i][j]) {
          return false;
        }


      }
    }
    return true;
  }

  private int[][] blocksCopy(){
    int[][] cpy = new int[dimen][dimen];

    for (int i = 0; i < dimen; i++)
      cpy[i] = Arrays.copyOf(blocks[i], blocks[i].length);

    return cpy;
  }
  
  public Iterable<CBoard> neighbors()     // all neighboring CBoards
  {
    Queue<CBoard> neighbors = new Queue<CBoard>();

    int zeroRowLoc = -1;
    int zeroColLoc = -1;

    for (int row = 0; row < dimen; row++)
      for (int col = 0; col < dimen; col++)
        if (blocks[row][col] == 0){
          zeroRowLoc = row;
          zeroColLoc = col;
        }

    if (zeroRowLoc + 1 < dimen){
      int tmp = blocks[zeroRowLoc + 1][zeroColLoc];
      blocks[zeroRowLoc + 1][zeroColLoc] = 0;
      blocks[zeroRowLoc][zeroColLoc] = tmp;

      neighbors.enqueue(new CBoard(cpy));
    }

    if (zeroRowLoc - 1 >= 0){
      cpy = blocksCopy();

      tmp = cpy[zeroRowLoc - 1][zeroColLoc];
      cpy[zeroRowLoc - 1][zeroColLoc] = 0;
      cpy[zeroRowLoc][zeroColLoc] = tmp;

      neighbors.enqueue(new CBoard(cpy));
    }

    if (zeroColLoc + 1 < dimen){
      cpy = blocksCopy();

      tmp = cpy[zeroRowLoc][zeroColLoc + 1];
      cpy[zeroRowLoc][zeroColLoc + 1] = 0;
      cpy[zeroRowLoc][zeroColLoc] = tmp;

      neighbors.enqueue(new CBoard(cpy));
    }

    if (zeroColLoc - 1 >= 0){
      cpy = blocksCopy();

      tmp = cpy[zeroRowLoc][zeroColLoc -1];
      cpy[zeroRowLoc][zeroColLoc - 1] = 0;
      cpy[zeroRowLoc][zeroColLoc] = tmp;

      neighbors.enqueue(new CBoard(cpy));
    }

    return neighbors;

    /*
    Queue<CBoard> neighborQueue = new Queue<CBoard>();
    int x = 0, y = 0;
    int tmpBlocks[][];
    CBoard b;

    outer:
    for (int i = 0; i < dimen; ++i) {
      for (int j = 0; j < dimen; ++j) {
        if (this.blocks[i][j] == 0) {
          x = i;
          y = j;
          break outer;
        }
      }
    }

    tmpBlocks = new int[dimen][dimen];
    int tmpVar = 0;
    for (int j = 0; j < dimen; ++j) {
      System.arraycopy(blocks[j], 0, tmpBlocks[j], 0, blocks[j].length);
    }
    if (x > 0 && y > 0 && x < (dimen - 1) && y < (dimen - 1)) {

      for (int i = 0; i < 4; i++) {
        if (i == 0) {
          tmpBlocks[x][y] = tmpBlocks[x - 1][y];
          tmpBlocks[x - 1][y] = 0;
          b = new CBoard(tmpBlocks);
          neighborQueue.enqueue(b);
          tmpBlocks[x - 1][y] = tmpBlocks[x][y];
          tmpBlocks[x][y] = 0;
        }

        if (i == 1) {
          tmpBlocks[x][y] = tmpBlocks[x + 1][y];
          tmpBlocks[x + 1][y] = 0;
          b = new CBoard(tmpBlocks);
          neighborQueue.enqueue(b);
          tmpBlocks[x + 1][y] = tmpBlocks[x][y];
          tmpBlocks[x][y] = 0;
        }
        if (i == 2) {
          tmpBlocks[x][y] = tmpBlocks[x][y - 1];
          tmpBlocks[x][y - 1] = 0;
          b = new CBoard(tmpBlocks);
          neighborQueue.enqueue(b);
          tmpBlocks[x][y - 1] = tmpBlocks[x][y];
          tmpBlocks[x][y] = 0;
        }
        if (i == 3) {
          tmpBlocks[x][y] = tmpBlocks[x][y + 1];
          tmpBlocks[x][y + 1] = 0;
          b = new CBoard(tmpBlocks);
          neighborQueue.enqueue(b);
          tmpBlocks[x][y + 1] = tmpBlocks[x][y];
          tmpBlocks[x][y] = 0;
        }
      }
    } else if (x == 0 && y == 0) {
      for (int i = 0; i < 2; i++) {
        if (i == 0) {
          tmpBlocks[x][y] = tmpBlocks[x + 1][y];
          tmpBlocks[x + 1][y] = 0;
          b = new CBoard(tmpBlocks);
          neighborQueue.enqueue(b);
          tmpBlocks[x + 1][y] = tmpBlocks[x][y];
          tmpBlocks[x][y] = 0;
        }
        if (i == 1) {
          tmpBlocks[x][y] = tmpBlocks[x][y + 1];
          tmpBlocks[x][y + 1] = 0;
          b = new CBoard(tmpBlocks);
          neighborQueue.enqueue(b);
          tmpBlocks[x][y + 1] = tmpBlocks[x][y];
          tmpBlocks[x][y] = 0;
        }

      }
    } else if (x == 0 && y == dimen - 1) {

      for (int i = 0; i < 2; i++) {
        if (i == 0) {
          tmpBlocks[x][y] = tmpBlocks[x + 1][y];
          tmpBlocks[x + 1][y] = 0;
          b = new CBoard(tmpBlocks);
          neighborQueue.enqueue(b);
          tmpBlocks[x + 1][y] = tmpBlocks[x][y];
          tmpBlocks[x][y] = 0;
        }
        if (i == 1) {
          tmpBlocks[x][y] = tmpBlocks[x][y - 1];
          tmpBlocks[x][y - 1] = 0;
          b = new CBoard(tmpBlocks);
          neighborQueue.enqueue(b);
          tmpBlocks[x][y - 1] = tmpBlocks[x][y];
          tmpBlocks[x][y] = 0;
        }

      }

    } else if (x == dimen - 1 && y == 0) {
      for (int i = 0; i < 2; i++) {
        if (i == 0) {
          tmpBlocks[x][y] = tmpBlocks[x - 1][y];
          tmpBlocks[x - 1][y] = 0;
          b = new CBoard(tmpBlocks);
          neighborQueue.enqueue(b);
          tmpBlocks[x - 1][y] = tmpBlocks[x][y];
          tmpBlocks[x][y] = 0;
        }
        if (i == 1) {
          tmpBlocks[x][y] = tmpBlocks[x][y + 1];
          tmpBlocks[x][y + 1] = 0;
          b = new CBoard(tmpBlocks);
          neighborQueue.enqueue(b);
          tmpBlocks[x][y + 1] = tmpBlocks[x][y];
          tmpBlocks[x][y] = 0;
        }
      }
    } else if (x == dimen - 1 && y == dimen - 1) {
      for (int i = 0; i < 2; i++) {
        if (i == 0) {
          tmpBlocks[x][y] = tmpBlocks[x - 1][y];
          tmpBlocks[x - 1][y] = 0;
          b = new CBoard(tmpBlocks);
          neighborQueue.enqueue(b);
          tmpBlocks[x - 1][y] = tmpBlocks[x][y];
          tmpBlocks[x][y] = 0;
        }
        if (i == 1) {
          tmpBlocks[x][y] = tmpBlocks[x][y - 1];
          tmpBlocks[x][y - 1] = 0;
          b = new CBoard(tmpBlocks);
          neighborQueue.enqueue(b);
          tmpBlocks[x][y - 1] = tmpBlocks[x][y];
          tmpBlocks[x][y] = 0;
        }
      }
    } else if (x == 0 && y > 0 && y < dimen - 1) {
      for (int i = 0; i < 3; i++) {
        if (i == 0) {
          tmpBlocks[x][y] = tmpBlocks[x + 1][y];
          tmpBlocks[x + 1][y] = 0;
          b = new CBoard(tmpBlocks);
          neighborQueue.enqueue(b);
          tmpBlocks[x + 1][y] = tmpBlocks[x][y];
          tmpBlocks[x][y] = 0;
        }
        if (i == 1) {
          tmpBlocks[x][y] = tmpBlocks[x][y - 1];
          tmpBlocks[x][y - 1] = 0;
          b = new CBoard(tmpBlocks);
          neighborQueue.enqueue(b);
          tmpBlocks[x][y - 1] = tmpBlocks[x][y];
          tmpBlocks[x][y] = 0;
        }
        if (i == 2) {
          tmpBlocks[x][y] = tmpBlocks[x][y + 1];
          tmpBlocks[x][y + 1] = 0;
          b = new CBoard(tmpBlocks);
          neighborQueue.enqueue(b);
          tmpBlocks[x][y + 1] = tmpBlocks[x][y];
          tmpBlocks[x][y] = 0;
        }
      }
    } else if (y == 0 && x > 0 && x < dimen - 1) {
      for (int i = 0; i < 3; i++) {
        if (i == 0) {
          tmpBlocks[x][y] = tmpBlocks[x + 1][y];
          tmpBlocks[x + 1][y] = 0;
          b = new CBoard(tmpBlocks);
          neighborQueue.enqueue(b);
          tmpBlocks[x + 1][y] = tmpBlocks[x][y];
          tmpBlocks[x][y] = 0;
        }
        if (i == 1) {
          tmpBlocks[x][y] = tmpBlocks[x - 1][y];
          tmpBlocks[x - 1][y] = 0;
          b = new CBoard(tmpBlocks);
          neighborQueue.enqueue(b);
          tmpBlocks[x - 1][y] = tmpBlocks[x][y];
          tmpBlocks[x][y] = 0;
        }
        if (i == 2) {
          tmpBlocks[x][y] = tmpBlocks[x][y + 1];
          tmpBlocks[x][y + 1] = 0;
          b = new CBoard(tmpBlocks);
          neighborQueue.enqueue(b);
          tmpBlocks[x][y + 1] = tmpBlocks[x][y];
          tmpBlocks[x][y] = 0;
        }
      }
    } else if (x == dimen - 1 && y > 0 && y < dimen - 1) {
      for (int i = 0; i < 3; i++) {
        if (i == 0) {
          tmpBlocks[x][y] = tmpBlocks[x - 1][y];
          tmpBlocks[x - 1][y] = 0;
          b = new CBoard(tmpBlocks);
          neighborQueue.enqueue(b);
          tmpBlocks[x - 1][y] = tmpBlocks[x][y];
          tmpBlocks[x][y] = 0;
        }
        if (i == 1) {
          tmpBlocks[x][y] = tmpBlocks[x][y - 1];
          tmpBlocks[x][y - 1] = 0;
          b = new CBoard(tmpBlocks);
          neighborQueue.enqueue(b);
          tmpBlocks[x][y - 1] = tmpBlocks[x][y];
          tmpBlocks[x][y] = 0;
        }
        if (i == 2) {
          tmpBlocks[x][y] = tmpBlocks[x][y + 1];
          tmpBlocks[x][y + 1] = 0;
          b = new CBoard(tmpBlocks);
          neighborQueue.enqueue(b);
          tmpBlocks[x][y + 1] = tmpBlocks[x][y];
          tmpBlocks[x][y] = 0;
        }
      }
    } else if (y == dimen - 1 && x > 0 && x < dimen - 1) {
      for (int i = 0; i < 3; i++) {
        if (i == 0) {
          tmpBlocks[x][y] = tmpBlocks[x + 1][y];
          tmpBlocks[x + 1][y] = 0;
          b = new CBoard(tmpBlocks);
          neighborQueue.enqueue(b);
          tmpBlocks[x + 1][y] = tmpBlocks[x][y];
          tmpBlocks[x][y] = 0;
        }
        if (i == 1) {
          tmpBlocks[x][y] = tmpBlocks[x - 1][y];
          tmpBlocks[x - 1][y] = 0;
          b = new CBoard(tmpBlocks);
          neighborQueue.enqueue(b);
          tmpBlocks[x - 1][y] = tmpBlocks[x][y];
          tmpBlocks[x][y] = 0;
        }
        if (i == 2) {
          tmpBlocks[x][y] = tmpBlocks[x][y - 1];
          tmpBlocks[x][y - 1] = 0;
          b = new CBoard(tmpBlocks);
          neighborQueue.enqueue(b);
          tmpBlocks[x][y - 1] = tmpBlocks[x][y];
          tmpBlocks[x][y] = 0;
        }
      }
    }
    return neighborQueue;    */
  }

  public String toString()               // string representation of the CBoard (in the output format specified below)
  {
    StringBuilder s = new StringBuilder();
    s.append(dimen + "\n");
    for (int i = 0; i < dimen; i++) {
      for (int j = 0; j < dimen; j++) {
        s.append(String.format("%2d ", blocks[i][j]));
      }
      s.append("\n");
    }
    return s.toString();
  }
}
