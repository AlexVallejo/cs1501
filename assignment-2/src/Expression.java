/**
 * Created By: Alex Vallejo
 * Date: 9/14/13
 * Project: assignment-2
 * Email: amv49@pitt.edu
 * Peoplesoft: 357-8411
 */

public class Expression {

  public static boolean atoms[] = new boolean[26];

  public boolean value;
  private String line;
  private String rawLine;
  Node root;

  public Expression(String line) throws ParseError{
    this.rawLine = line;

    line = line.replaceAll(" ", "");
    this.line = line;

    validate();

    this.root = buildTree(line);
    TreeDisplay disp = new TreeDisplay(this.rawLine);
    disp.setRoot(this.root);
  }

  public Expression(String line, boolean input) throws ParseError{
    this.rawLine = line;

    line = line.replaceAll(" ", "");
    this.line = line;

    this.root = buildTree(line);
  }

  //=================================
  //Methods Required by the assignment
  //==================================

  /**
   * Set an atom for the entire Expression class. Used in the drivers.
   * @param atom the atom to be set (must be a letter)
   * @param value
   */
  public static void setAtom(String atom, String value) throws ParseError{

    int index = atom.charAt(0);

    if (!Character.isLetter(index))
      throw new ParseError("Invalid Atom: " + atom + " is not a recognized "
                           + "letter");

    index -= 65;

    if (value.contains("true"))
      atoms[index] = true;

    else
      atoms[index] = false;
  }

  public boolean evaluate(){
    this.value = evaluate(this.root);
    return this.value;
  }

  private boolean evaluate (Node subT){

    if (subT.isLeaf())
      return atoms[subT.symbol.charAt(0) - 65];

    if (subT.symbol.equals("^")){
      boolean left = evaluate(subT.left);
      boolean right = evaluate(subT.right);

      return left && right;
    }

    if (subT.symbol.equals("v")){
      boolean left = evaluate(subT.left);
      boolean right = evaluate(subT.right);

      return left || right;
    }

    //The only operator left is the negation
    return !evaluate(subT.right);
  }

  /**
   * Copies the expression be using the raw text originally assigned to the
   * expression as an argument to the constructor of the new expression,
   * effectively copying the other expression.
   * @return a copy of this expression
   * @throws ParseError if there is an error in building the copy
   */
  public Expression copy() throws ParseError{
    return new Expression(this.rawLine, true);
  }

  /**
   * Normalizes this tree.
   */
  public void normalize() {
    this.root = normalize(this.root);
  }

  private Node normalize(Node node){
    if (node.isLeaf())
      return node;

    if (node.symbol.equals("!") && node.right.isLeaf())
      return node;

    //Remove chained negations. Works for a negation as a root node!
    if (node.symbol.equals("!") && node.right.symbol.equals("!")){
        node = normalize(node.right.right);
    }

    else if (node.right.symbol.equals("!") && node.right.right.symbol.equals("!")){
          node.right = normalize(node.right.right.right);
    }

    //Remove a negation above an AND
    else if (node.symbol.equals("!") && node.right.symbol.equals("^")){
        node = node.right;
        node.symbol = "v";

        node = insertNegs(node);

        node.right = normalize(node.right);
        node.left = normalize(node.left);
      }

    //Remove a negation above an OR
    else if (node.symbol.equals("!") && node.right.symbol.equals("v")){
        node = node.right;
        node.symbol = "^";

        node = insertNegs(node);

        node.right = normalize(node.right);
        node.left = normalize(node.left);
      }

    else {
      node.right = normalize(node.right);
      node.left = normalize(node.left);
    }

    //Convert any AND above an OR to an OR and distribute inside the tree
    if (node.symbol.equals("^")){

      if (node.left.symbol.equals("v") && node.right.symbol.equals("v")){

        node.symbol = "v";

        Node ll, lr, rl, rr, iL, iR; //l -> left r-> right i -> right

        ll = new Node("^", node.left.left, node.right.left);
        lr = new Node("^", node.left.left, node.right.right);

        rl = new Node("^", node.left.right, node.right.left);
        rr = new Node("^", node.left.right, node.right.right);

        iL = new Node("^", ll, lr);
        iR = new Node("^", rl, rr);

        node.left = iL;
        node.right = iR;
      }

      else if (node.right.symbol.equals("v")){
        node.symbol = "v";
        Node subTL = new Node("^", node.left, node.right.left);
        Node subTR = new Node("^", node.left, node.right.right);

        node.right = subTR;
        node.left = subTL;

        normalize(node);

        //node.right = normalize(node.right);
        //node.left = normalize(node.left);
      }

      else if (node.left.symbol.equals("v")){
        node.symbol = "v";
        Node subTR = new Node("^", node.left.right, node.left);
        Node subTL = new Node("^", node.left.left, node.left);

        node.right = subTR;
        node.left = subTL;

        normalize(node);

        //node.right = normalize(node.right);
        //node.left = normalize(node.left);
      }
    }

    return node;
  }

  /**
   * Copy this Expression, normalize it then display the normalized tree
   * @throws ParseError if there is an error in building the copy
   */
  public void displayNormalized() throws ParseError{

    Expression copy = this.copy();
    copy.normalize();

    TreeDisplay disp = new TreeDisplay("normalized " + copy.rawLine);
    disp.setRoot(copy.root);
  }

  /**
   * Evaluates the expression if it's not been evaluated already.
   * @return The original expression with it's value. EX: (A v B) = false
   */
  public String toString(){
    return this.rawLine;
  }

  //==================================
  // Custom methods!
  //==================================

  //==================================
  // Normalize methods
  //==================================

  /** //ToDo change this to reflect new implementation
   * Insert a negation node between the node passed as an argument and it's
   * right or left child as specified by the half parameter
   * @param subT the node that will have a negative inserted as it's child
   * @param half the half of the node to insert the negative on. Accepts 'l'
   *             or 'r'
   */
  private Node insertNegs(Node subT){
    Node iR, iL;

    iR = new Node("!", null, subT.right);
    iL = new Node("!", null, subT.left);

    subT.right = iR;
    subT.left = iL;

    return subT;
  }

  //==================================
  // Tree builder methods
  //==================================

  private Node buildTree(String line) throws ParseError{

    line = rmParenthesis(line);

    //Base case: line => {atom}
    if (isAtom(line))
      return new Node(line);

    //line => !{exp}
    if(line.charAt(0) == '!')
      return new Node(strFrmChar('!'), null, buildTree(line.substring(1,
          line.length())));

    //At this point, the expression must be at least {atom/exp} {op} {atom/exp}
    if (line.length() < 3)
      throw new ParseError("Expression incomplete: " + this.rawLine + "\nThe " +
                           "problem: " + line);

    char first, op, last;
    first = line.charAt(0);
    op = line.charAt(1);
    last = line.charAt(2);

    //Base case: line => {exp} {op} {exp}
    if (isAtom(first) && isOp(op) && isAtom(last))
      return new Node(strFrmChar(op), new Node(first), new Node(last));

    return new Node(op(line),buildTree(leftExp(line)),buildTree(rightExp(line)));
  }

  /**
   * Returns the operator of an expression
   * @param line the expression to be examined for an operator
   * @return the operator found within line
   * @throws ParseError thrown if the operator is not found
   */
  private String op(String line) throws ParseError{

    int open = 0, closed = 0, pos = 0;

    //ToDo throws ParseError if line.length < 2
    char first, op, last, endOp;
    first = line.charAt(0);
    op = line.charAt(1);
    last = line.charAt(line.length() - 1);
    endOp = line.charAt(line.length() - 2);

    //Error case: {atom} ( {exp/atom}
    if (isAtom(first) && !isOp(op))
      throw new ParseError("Expression not fully parenthesized: " + this
        .rawLine);

    //Base case: => {atom} {op} {exp}
    if (isAtom(first) && isOp(op))
      return strFrmChar(op);

    //Base case: => {exp} {op} {atom}
    if (isAtom(last) && isOp(endOp))
      return strFrmChar(endOp);

    //Handles fully parenthesized expressions
    do {
      char c = line.charAt(pos);

      if ('(' == c)
        open++;

      else if (')' == c)
        closed++;

      if (open == closed && open > 0)
        return strFrmChar(line.charAt(pos + 1));

      pos++;
    } while (pos < line.length());

    if (!isOp(op))
      throw new ParseError("Invalid operator: " + op + " in " + this.rawLine);

    throw new ParseError("Parenthesis mismatch: " + this.rawLine + "\nThe " +
                         "problem: " + line);
  }

  /**
   * Returns the left expression of the larger expression
   * @param line the line the left expression wil lbe extracted from
   * @return the left expression of the larger expression
   * @throws ParseError thrown if a left expression is not found
   */
  private String leftExp(String line) throws ParseError{
    int open = 0, closed = 0, pos = 0;

    char first = line.charAt(pos);

    //Base case: => {atom} {op} {exp}
    if (isAtom(first))
      return strFrmChar(first);

    //Fully parenthesized expression ex: ({exp} {op} {exp})
    do {
      char c = line.charAt(pos);

      if ('(' == c)
        open++;

      else if (')' == c)
        closed++;

      if (open == closed && open > 0){
        String str = line.substring(0, pos + 1);
        return str;
      }

      pos++;

    } while (pos < line.length());

    throw new ParseError("Parenthesis Mismatch: " + this.rawLine + "\nThe " +
        "Problem: " + line);
  }

  /**
   * Extracts the right expression from a larger expression
   * @param line the line the right expression will be extracted from
   * @return the right expression in line
   * @throws ParseError thrown if no right expression is found
   */
  private String rightExp(String line) throws ParseError{
    int open = 0, closed = 0, pos = line.length() - 1;

    char last = line.charAt(pos);

    //Base case: => {exp} {op} {atom}
    if (isAtom(last))
      return strFrmChar(last);

    //Fully parenthesized expression ex: ({exp} {op} {exp})
    do {
      char c = line.charAt(pos);

      if ('(' == c)
        open++;

      else if (')' == c)
        closed++;

      if (open == closed && open > 0){
        String str = line.substring(pos, line.length());
        return str;
      }

      pos--;
    } while (pos >= 0);

    throw new ParseError("Parenthesis mismatch: " + line);
  }

  //=================
  // Utility Methods
  //=================

  private void validate() throws ParseError{
    if (line.contains("!v") || line.contains("!^") || line.contains("v!") ||
        line.contains("^!"))
      throw new ParseError("Expression not fully parenthesized: " + this.rawLine);

/*
    char[] chars = line.toCharArray();

    char prevChar, curChar, nxtChar;

    for (int prev = -1, cur = 0, nxt = 1; nxt < line.length();prev++,  cur++,
        nxt++ ){

      curChar = line.charAt(cur);
      nxtChar = line.charAt(nxt);

      if (prev >= 0){
        prevChar = line.charAt(prev);

        if (isAtom(curChar) && prevChar == ')')
          throw new ParseError("Expression not fully parenthesized: " + this
              .rawLine);
      }

      if (isAtom(curChar) && nxtChar == '(')
          throw new ParseError("Expression not fully parenthesized: " + this
              .rawLine);
    }
*/
  }

  /**
   * Essentially, a missing constructor for string. Creates a string from a
   * single char
   * @param c the char to become the string
   * @return a string containing only c
   */
  private String strFrmChar(char c){
    String str = new String();
    str += c;
    return str;
  }

  /**
   * Determines if a character is an operator or not according to the grammar
   * defined by the assignment
   * @param c the character to be assessed
   * @return true if c is an operator, false otherwise
   */
  private boolean isOp(char c){
    if (c == 'v' || c == '^')
      return true;

    else
      return false;
  }

  /**
   * Decide if a character is an atom or not
   * @param c the character to be assessed
   * @return true if c is an atom, false otherwise
   */
  private boolean isAtom(char c){
    return Character.isLetter(c);
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

    //Base case: => {atom}
    if (isAtom(line))
      return line;

    //Base case: => !{exp}
    if (line.charAt(0) == '!')
      return line;

    //At this point, the expression MUST be at least {atom/exp} {op} {atom/exp}
    if (line.length() < 3)
      throw new ParseError("Expression incomplete: " + line);

    //Get the required chars to decide if the line should not have
    // parenthesis removed
    char first, op, second, last, endOp;
    first = line.charAt(0);
    op = line.charAt(1);
    second = line.charAt(2);
    last = line.charAt(line.length() - 1);
    endOp = line.charAt(line.length() - 2); //op could be second to last
                                            //{exp} {op} {atom}
                                            //ex: (AvB)^x

    //Base case: => {atom} {exp} {atom}
    if (isAtom(first) && isOp(op) && isAtom(second))
      return line;

    //Base case: => {atom} {op} {exp}
    if (isAtom(first) && isOp(op))
      return line;

    //Base case: => {exp} {op} {atom}
    if (isAtom(last) && isOp(endOp))
      return line;

    //General case
    //If valid, line MUST be of this form: => ({exp} {op} {exp})
    if ('(' == first && ')' == last)
      return line.substring(1,line.length() - 1);

    throw new ParseError("Parenthesis mismatch: " + this.rawLine + "\nThe " +
                         "problem:" + line);
  }
}
