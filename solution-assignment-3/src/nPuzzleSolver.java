/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 10/12/13
 * Time: 2:47 PM
 * To change this template use File | Settings | File Templates.
 */
public interface nPuzzleSolver {

  public boolean isSolvable();

  public int moves();

  public Iterable<Board> solution();
}
