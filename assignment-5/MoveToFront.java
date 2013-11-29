/*******************************************************************************
 * Name: Alex Vallejo
 * Email: amv49@pitt.edu, vallejo.alex@gmail.com
 * Date: 11/14/13
 * Project: assignment-5 (BZIP compress/decompress)
 * Peoplesoft: 357-8411
 *
 * This class implements the move to front algorithm for pre-compression
 * optomization. It produces a higher frequency of characters when characters
 * are close to eachther.
 *
 * Move to front plays a fundemental role in the BZIP compression algorithm by
 * generating a high frequency of characters from the output of the Burrows-
 * Wheeler transform.
 *
 * Usage: java MoveToFront { - | + } < somefile.txt
 *  - encodes & + decodes
 *
 ******************************************************************************/

import java.util.ArrayList;

public class MoveToFront {

// alphabet size
private static final int R = 256;

  /**
   *  Applies move to front encoding to the standard input. The result is
   *  written to the standard output.
   */
  public static void encode(){

    //Initialize the list
    ArrayList<Character> list = initList();

    while(!BinaryStdIn.isEmpty()){
      Character b = BinaryStdIn.readChar();

      // write the position of the read character to the standard out
      char pos = (char)list.indexOf(b);

      //System.out.printf("\npos => %d char => %c", (int)pos, b);
      BinaryStdOut.write(pos);

      // remove the character from the list
      list.remove(pos);

      // add the character to the front of the list
      list.add(0,b);
    }

    BinaryStdOut.close();
  }

  /**
   * Initializes an array list that contains the first R character points.
   * @return list the initialized list of the first R unicode code points
   */
  private static ArrayList<Character> initList(){
    ArrayList<Character> list = new ArrayList<Character>(R);

    for(Character i = 0; i < R; i++)
      list.add(i);

    return list;
  }

  /**
   * Applies move to front decoding to the standard input. The result is written
   * to the standard output.
   */
  public static void decode(){
    ArrayList<Character> list = initList();

    while(!BinaryStdIn.isEmpty()){
      // read the next encoded character
      char indexOfEncodedChar = BinaryStdIn.readChar();

      // find the position of the encoded character
      char charOut = list.get(indexOfEncodedChar);

      // write the decoded value of the character
      BinaryStdOut.write(charOut);

      // add the read character to the begining of the list
      list.remove(indexOfEncodedChar);
      list.add(0,charOut);
    }

    BinaryStdOut.close();
  }

  /**
   * Apply Move To Front encoding or decoding to standard input
   *
   * @param args enter '-' for encoding or '+' for decoding.
   */
  public static void main(String[] args){

    if (args.length != 1){
      System.out.printf("\nError: provide encode/decode specifier");
      System.out.printf("\nex: java MoveToFront - < some_file.txt");
      System.exit(1);
    }

    if (args[0].equals("-"))
      encode();

    else if (args[0].equals("+"))
      decode();

    else {
      System.out.printf("\n\nIllegial argument \"%s\". Only \"+\" or \"-\" are"
          + " accepted");
      System.exit(1);
    }// end else for illegial argument
  }// end main
}// end class
