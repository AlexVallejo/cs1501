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

  private String operator(String line) throws ParseError{

    int position = 0, open = 0, closed = 0;

    //Handles exressions of the form {atom} {op} {exp}
    //{exp} can also be an atom
    char c = line.charAt(0);

    if (Character.isLetter(c)){
      char op = line.charAt(1);
      op = Character.toLowerCase(op);

      if (op != 'v' && op != '!' && op != '^')
         throw new ParseError("Expected an operator as the second character: " +
                              line);
      String opString = new String();
      opString += op;

      return opString;
    }

    //Handles nested expression ex: (A v B) ^ (B ^ A)
    do {
      c = line.charAt(position);

      if ('(' == c)
        open++;

      if (')' == c)
        closed++;

      //We have identified the location of the operator
      if (open == closed && open > 0){

        if (position + 1 >= line.length())
          throw new ParseError("Unexpected end of expression: " + line);

        String op = new String();
        op += line.charAt(position + 1);
        return op;
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

    int open = 0, closed = 0, position = 0;

    // Base Cases
    char firstChar = line.charAt(0);
    if (Character.isLetter(firstChar)){

      String op = new String();
      op += line.charAt(1); //fixme assume operator is 2nd char

      String leftAtom = new String();
      leftAtom += firstChar; //fixme assume left atom is first char

      String thirdChar = new String();
      thirdChar += line.charAt(2);

      //The base case where the expression is {atom} {op} {atom}
      if (Character.isLetter(line.charAt(2))){
        return new Node(op,new Node(leftAtom),new Node(thirdChar));
      }

      //The base case where the expression is {atom} {op} {exp}
      else {
        do {

          char c = line.charAt(position);

          if ('(' == c)
            open++;

          else if (')' == c)
            closed++;

          if (open == closed && open > 0){
            String rightExp = line.substring(2, line.length());
            return new Node(op, new Node(leftAtom), buildTree(rightExp));
          }

        } while (position < line.length());

        throw new ParseError("Error in right half of expression: " + line);
      }
    }

    //a single atom
    if (isAtom(line))
      return new Node(line);

    //multiple expressions!
    do {
      char c = line.charAt(position);

      if ('(' == c)
        open++;

      else if (')' == c)
        closed++;

      if (open == closed && open > 0){
        String trimmedExp = line.substring(1,position);
        return buildTree(trimmedExp);
      }

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
    int position = line.length() - 1; // -1 for array index offset

    if (isAtom(line))
      return new Node(line);

    char firstChar = line.charAt(position);

    if (Character.isLetter(firstChar)){

      String op = new String();
      op += line.charAt(position - 1); //fixme assume operator is 2nd to last
      String rightAtom = new String();
      rightAtom += line.charAt(position); //fixme assume right atom is last char

      do {
        char c = line.charAt(position);

        if ('(' == c)
          open++;

        else if (')' == c)
          closed++;

        if (closed == open && open > 0){
          int end = line.length() - 2; // -1 to exclude last atom
                                       // -1 to exclude last operator
                                       // ex: (A^B)^Z

          String leftExp = line.substring(position, end);

          return new Node(op, buildTree(leftExp), new Node(rightAtom));
        }

      } while (position >= 0);

      throw new ParseError("Invalid expression in the left half: " + line);
    }

    do {
      char c = line.charAt(position);

      if ('(' == c)
        open++;

      else if (')' == c)
        closed++;

      if (open == closed && open > 0){

        String op = new String();
        op += line.substring(position + 1, line.length() - 1);
        return buildTree(op);
      }

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

    return Character.isLetter(firstChar);
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

    if (isAtom(line))
      return line;

    char first, last;

    first = line.charAt(0);
    last = line.charAt(line.length() - 1);

    if (first != '(' || last != ')')
      throw new ParseError("Parenthesis mismatch: " + line);

    else
      return line.substring(1,line.length() - 1);
  }
}
