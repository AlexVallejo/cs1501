/**
 * Created By: Alex Vallejo
 * Date: 9/14/13
 * Project: assignment-2
 * Email: amv49@pitt.edu
 * Peoplesoft: 357-8411
 */

public class Node {
  public String symbol;
  public Node right;
  public Node left;

  public Node(char c) {
    this(new String(new char[] {c}));
  }

  public Node(char c, Node left){
    this(new String(new char[] {c}), left);
  }

  public Node(char c, Node left, Node right){
    this(new String(new char[] {c}), left, right);
  }

  public Node(String value) {
    this(value, null);
  }

  public Node(String value, Node left) {
    this(value, left, null);
  }

  public Node(String value, Node left, Node right) {
    this.symbol = value;
    this.left = left;
    this.right = right;
  }
}
