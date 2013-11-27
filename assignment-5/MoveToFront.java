/**
 * Created By: Alex Vallejo
 * Date: 11/14/13
 * Project: assignment-5
 * Email: amv49@pitt.edu
 * Peoplesoft: 357-8411
 */

import java.util.ArrayList;

public class MoveToFront {

// alphabet size
private static final int R = 256;

  // apply move-to-front encoding,
  // reading from standard input and writing to standard output
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

  private static ArrayList<Character> initList(){
    ArrayList<Character> list = new ArrayList<Character>(R);

    for(Character i = 0; i < R; i++)
      list.add(i);

    return list;
  }

  // apply move-to-front decoding,
  // reading from standard input and writing to standard output
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

  // if args[0] is '-', apply move-to-front encoding
  // if args[0] is '+', apply move-to-front decoding
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
      System.out.printf("Illegial argument \"%s\". Only \"+\" or \"-\" are " +
          "accepted");
      System.exit(1);
    }

  }
}
