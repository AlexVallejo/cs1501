/**
 * @author Alex Vallejo <amv49@pitt.edu>
 * @since 11/14/13
 * Project: assignment-5
 * Peoplesoft: 357-8411
 */

public class BurrowsWheeler {
  // apply Burrows-Wheeler encoding, reading from standard input and writing to standard output
  public static void encode(){

  }

  // apply Burrows-Wheeler decoding, reading from standard input and writing to standard output
  public static void decode(){

  }

  // if args[0] is '-', apply Burrows-Wheeler encoding   - 5 points
  // if args[0] is '+', apply Burrows-Wheeler decoding   - 5 points
  /**
   * Move to front encoding helps to increase likelyhood that the same
   * characters will appear next to each other. This class supports both
   * encoding and decoding. Redirect the output of the class to a file to
   * store the output.
   *
   * @param args enter '-' for encoding or '+' for decoding.
   */
  public static void main(String[] args){

    if (args.length != 1){
      System.out.printf("\nError: provide encode/decode specifier");
      System.out.printf("\nex: java BurrowsWheeler - < some_file.txt");
      System.exit(1);
    }

    if (args[0].equals("-"))
      encode();

    else if (args[0].equals("+"))
      decode();

    else {
      System.out.printf("Illegial argument \"%s\". Only \"+\" or \"-\" are accepted");
    }// end else
  }// end main
} // end BurrowsWheeler
