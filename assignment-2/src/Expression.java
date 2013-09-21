/**
 * Created By: Alex Vallejo
 * Date: 9/14/13
 * Project: assignment-2
 * Email: amv49@pitt.edu
 * Peoplesoft: 357-8411
 */

public class Expression {

  private boolean value;
  private String line;
  Node root;

  public Expression(String line) {

    line = line.toUpperCase();
    line = line.replaceAll(" ", "");
    this.line = line;

    if (isExpr(line))
      this.root = buildTree(line);

  }

  //=================================
  //Methods Required by the assignment
  //==================================

  public static void setAtom(String atom, String value) {
  }

  public boolean evaluate() {

    //ToDo set value to evulation
    return false;
  }

  public Expression copy() {
    return new Expression(line);
  }

  public void normalize() {
  }

  public void displayNormalized() {
  }

  public String toString() {
    return line + " = " + this.value;
  }


  //=================================
  //Custom helper methods!
  //==================================

  private boolean isExpr(String line) {
    //ToDo validate the expression and print a helpful error message if the expression is not valid

    return true;
  }

  private Node buildTree(String line) {

    return new Node();
  }
}
