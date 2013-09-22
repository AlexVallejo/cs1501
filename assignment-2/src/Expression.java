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
  private String rawLine;
  private boolean evaluated;
  Node root;

  public Expression(String line) throws ParseError{
    evaluated = false;
    this.rawLine = line;

    line = line.replaceAll(" ", "");
    this.line = line;

    this.root = buildTree(rmParenthesis(line));

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

  public Expression copy() throws ParseError{
    return new Expression(this.rawLine);
  }

  public void normalize() {
  }

  public void displayNormalized() {
    TreeDisplay display = new TreeDisplay(this.rawLine);
    display.setRoot(this.root);
  }

  public String toString() {
    if (!this.evaluated)
      evaluate();

    return this.rawLine + " = " + this.value;
  }

  //==================================
  //Custom helper methods!
  //==================================

  private Node buildTree(String line) throws ParseError{

    line = rmParenthesis(line);

    if (isAtom(line))
      return new Node(line);

    if(line.charAt(0) == '!')
      return new Node(stringFromChar('!'), null, buildTree(line.substring(1,
          line.length())));

    return new Node("Balls");
  }

  /**
   * Essentially, a missing constructor for string. Creates a string from a
   * single char
   * @param c the char to become the string
   * @return a string containing only c
   */
  private String stringFromChar(char c){
    String str = new String();
    str += c;
    return str;
  }

  /**
   * Determines if the input is an atom or not
   * @param line the expression that is to be evaluated
   * @return true if an atom, false otherwise!
   */
  private boolean isAtom(String line){
    if (line.length() > 1)
      return false;

    char firstChar = line.charAt(0);

    return Character.isLetter(firstChar);
  }

  /**
   * Removes the parenthesis around an expression. EX: (AvB) -> AvB
   * @param line the line to be stripped
   * @return the expression without the enclosing parenthesis
   * @throws ParseError Thrown if parenthesis do not match on the ends of the
   * expression
   */
  private String rmParenthesis(String line) throws ParseError{

    if (isAtom(line))
      return line;

    if (line.charAt(0) == '!')
      return line;

    if (line.charAt(0) == '(' && ')' == line.charAt(line.length() - 1))
      return line.substring(1,line.length() - 1);

    throw new ParseError("Parenthesis mismatch: " + line);
  }
}
