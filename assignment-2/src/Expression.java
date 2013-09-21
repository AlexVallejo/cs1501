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

    this.root = buildTree(stripParenthesis(line));

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
    this.value = false;
  }

  public void displayNormalized() {
    TreeDisplay display = new TreeDisplay(this.line);
    display.setRoot(this.root);
  }

  public String toString() {
    if (!this.evaluated)
      evaluate();

    return line + " = " + this.value;
  }

  //==================================
  //Custom helper methods!
  //==================================

  private String operator(String line) throws ParseError{

    int position = 0, open = 0, closed = 0;
    char c;

    //Handles the operator in a standalone expression "A v B"
    c = line.charAt(0);
    if (Character.isLetter(c)){

      if (line.length() != 3)
        throw new ParseError(line);

      else
        return line.substring(1,2);
    }

    //Handles nested expression (A v B) ^ (B ^ A)
    do {
      c = line.charAt(position);

      if ('(' == c)
        open++;

      if (')' == c)
        closed++;

      if (open == closed && open > 0){
        if (line.length() <= position + 2)
          return null;
        return line.substring(position + 1, position + 2);

      }

      position++;

    } while (position < line.length());

    throw new ParseError(line);
  }

  /**
   * Identify and return the left sub-expression from a larger expression
   * @param line the large expression the left sub-expression is to be
   *             extracted from
   * @return the left expression from the large expression
   * @throws ParseError Thrown when the parenthesis do not match up
   */
  private Node leftExp(String line) throws ParseError{

    int open = 0, closed = 0, position = 1;

    char firstChar = line.charAt(0);
    if (Character.isLetter(firstChar)){
      assert (line.length() == 3) : "Expected length of 3: " + line;

      Node rightAtom = new Node(line.substring(0,1));
      String operator = line.substring(1,2);
      Node leftAtom = new Node(line.substring(2,3));

      return new Node(operator, rightAtom, leftAtom);
    }

    if (isAtom(line))
      return new Node(line);

    do {
      char c = line.charAt(position);

      if ('(' == c)
        open++;

      else if (')' == c)
        closed++;

      if (open == closed && open > 0)
        buildTree(line.substring(1,position));

      position++;

    } while (position < line.length());

    throw new ParseError(line);
  }

  /**
   * Identify and return the right sub-expression from a larger expression
   * @param line the expression the right sub-expression is to be extracted
   *             from
   * @return the right expression from the large expression
   * @throws ParseError Thrown when the parenthesis do not match up
   */
  private Node rightExp(String line) throws ParseError{
    int open = 0, closed = 0;
    int position = line.length() - 2; // -1 for array index offset and -1 to
                                      // skip last closing parenthesis

    if (isAtom(line))
      return new Node(line);

    char firstChar = line.charAt(0);

    if (Character.isLetter(firstChar)){ //ToDo Assume proper format

      assert (line.length() == 3) : "Expected length of 3: " + line;

      Node rightAtom = new Node(line.substring(0,1));
      String operator = line.substring(1,2);
      Node leftAtom = new Node(line.substring(2,3));

      return new Node(operator, rightAtom, leftAtom);
    }

    do {
      char c = line.charAt(position);

      if ('(' == c)
        open++;

      if (')' == c)
        closed++;

      if (open == closed && open > 0)
        buildTree(line.substring(position, line.length()));

      position--;

    } while (position >= 0);

    throw new ParseError(line);
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

    if (Character.isLetter(firstChar))
      return true;

    else
      return false;


  }

  private Node buildTree(String line) throws ParseError{

    if (line.charAt(0) == '!'){
      String operator = line.substring(0,1);
      String internalExp = stripParenthesis(line.substring(1,line.length()));


      return new Node(operator, null, buildTree(internalExp));
    }

    else if (isAtom(line))
      return new Node(line);

    else
      return new Node(operator(line), leftExp(line), rightExp(line));
  }

  private String stripParenthesis(String line) throws ParseError{
    char first, last;

    first = line.charAt(0);
    last = line.charAt(line.length() - 1);

    if (isAtom(line))
      return line;

    if (first != '(' || last != ')')
      throw new ParseError("Parenthesis mismatch: " + line);

    else
      return line.substring(1,line.length() - 1);
  }
}
