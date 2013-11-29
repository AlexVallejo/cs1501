/*******************************************************************************
 * Name: Alex Vallejo
 * Email: amv49@pitt.edu, vallejo.alex@gmail.com
 * Date: 11/14/13
 * Project: assignment-5 (BZIP compress/decompress)
 * Peoplesoft: 357-8411
 *
 * This class implements the Burrows Wheeler Transformation that performs pre-
 * compression optomization.
 *
 * Move common characters close to eachother which makes huffman encoding
 * more effective.
 *
 * Usage: java BurrowsWheeler { + | - } < somefile.txt
 *  - encodes & + decodes
 *
 ******************************************************************************/

public class BurrowsWheeler {
  private static final int R = 256; // Radix of a byte.

  /* Apply Burrows-Wheeler encoding, reading from standard input and
   * writing to standard output.
   */
  public static void encode() {
    String s = BinaryStdIn.readString();
    int n = s.length();
    CircularSuffixArray csa = new CircularSuffixArray(s);

    // find the first string in the sorted permutation table
    // the CSA is not actually kept insorted order (to decrease time the
    // requirement), so we have to find the first string
    int first;
    for (first = 0; first < csa.length(); first++)
      if (csa.index(first) == 0)
        break;

    BinaryStdOut.write(first);

    // print the last value in each of the CSA's column's
    for (int i = 0; i < csa.length(); i++){
      int indexOfLastChar = (csa.index(i) + n - 1) % n;
      BinaryStdOut.write(s.charAt(indexOfLastChar));
    }

    BinaryStdOut.close();
  }

  /* Apply Burrows-Wheeler decoding, reading from standard input and writing
   * to standard output.
   *
   * First, Build the next array that will decode the string. We discussed this
   * topic in class in week 9 and it is demonstrated in compressionIII.ppt.
   * However, I do not entirely know how it works. I more or less copied the
   * code out of the example powerpoint.
   *
   * The original string can be recovered by iterating through the next array
   * in such a way that the next character is the value at next[x], where
   * next = first to start.
   */
  public static void decode() {

  int first = BinaryStdIn.readInt();
  String t = BinaryStdIn.readString();
  int n = t.length();
  int count[] = new int[R + 1];
  int next[] = new int[n];

  for (int i = 0; i < n; i++)
    count[t.charAt(i) + 1]++;

  for (int i = 1; i < R + 1; i++)
    count[i] += count[i - 1];

  for (int i = 0; i < n; i++)
    next[count[t.charAt(i)]++] = i;

  int c = 0;
  for (int i = next[first]; c < n; i = next[i]){
    BinaryStdOut.write(t.charAt(i));
    c++;
  }

  BinaryStdOut.close();
  }

  /* Apply Burrows-Wheeler encoding or decoding to the standard input
   *
   * @param args enter '-' for encoding or '+' for decoding.
   */
  public static void main(String[] args) {
    if (args.length != 1){
      System.out.println("usage: java BurrowsWheeler - < somefile.txt");
      System.exit(1);
    }

    else if (args[0].equals("+"))
      decode();

    else if (args[0].equals("-"))
      encode();

    else {
      System.out.printf("\n\nIllegial argument \"%s\". Only \"+\" or \"-\"" +
                        " are accepted.");
      System.exit(0);
    }// end else for illegial argument
  }// end main
}//end class
