/**
 * Created By: Alex Vallejo
 * Date: 11/14/13
 * Project: assignment-5
 * Email: amv49@pitt.edu
 * Peoplesoft: 357-8411
 */

import java.util.ArrayList;

public class MoveToFront {

private static final int alphabitSize = 256;

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
    ArrayList<Character> list = new ArrayList<Character>(256);

    for(Character i = 0; i < 256; i++)
      list.add(i);

    return list;
  }

  // apply move-to-front decoding,
  // reading from standard input and writing to standard output
  public static void decode(){

  }

  // if args[0] is '-', apply move-to-front encoding
  // if args[0] is '+', apply move-to-front decoding
  public static void main(String[] args){

    if (args.length != 1){
      System.out.printf("\n\nError: provide encode/decode specifier");
      System.out.printf("\nex: java MoveToFront - < some_file.txt\n");
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
