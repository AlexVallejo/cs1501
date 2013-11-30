/*******************************************************************************
 * Name: Alex Vallejo
 * Email: amv49@pitt.edu, vallejo.alex@gmail.com
 * Date: 11/14/13
 * Project: assignment-5 (BZIP compress/decompress)
 * Peoplesoft: 357-8411
 *
 * Circular suffic array builds the data structure needed for burrows
 * wheeler transform during instantiation. The information needed by the
 * burrows wheeler transform can then be extracted from CSA.
 *
 * Usage: CircularSuffixArray csa = new CircularSuffixArray("Some string");
 *
 ******************************************************************************/

import java.util.Arrays;

public class CircularSuffixArray {

  private Suffix[] suffixes;

  /**
   * The constructor instantiates a new suffix for each character in standard
   * input and sorts the suffixes.
   */
  public CircularSuffixArray(String s){

    int length = s.length();
    this.suffixes = new Suffix[length];

    for (int i = 0; i < length; i++)
      suffixes[i] = new Suffix(s,i);

    Arrays.sort(suffixes);
  }

  /**
   * Returns the number of suffixes in the CSA. Equal to the number of
   * characters in the original text.
   *
   * @return number if suffixes in the CSA
   */
  public int length(){
    return suffixes.length;
  }

  /**
   * Returns the index of the ith sorted suffix
   *
   * @param i ith sorted suffix to find
   * @return index of the ith sorted suffix
   */
  public int index(int i){
    return suffixes[i].length();
  }

  /**
   * Inner class that holds the data for each suffix. This class is efficient
   * in the way that it deals with the suffixes because it does not actually
   * generate the suffix using substring. It simply stores the begining index
   * of the suffix it is representing and returns the begining character
   * only when asked.
   *
   * The begining character is also the character at the index in the orignal 
   * string
   */
  private static class Suffix implements Comparable<Suffix> {
    private final String text;
    private final int index;

    /**
     * Store the original string and the index into the string representing the
     * first character in the suffix
     */
    private Suffix(String text, int index) {
      this.text  = text;
      this.index = index;
    }

    /**
     * Returns the length of the suffix
     *
     * @return length of this suffix
     */
    private int length() {
      return text.length() - index;
    }

    /**
     * Find the character at the ith position in the suffix
     *
     * @return ith char of the suffix
     */
    private char charAt(int i) {
      return text.charAt(index + i);
    }

    /**
     * Implement the comparable interface
     *
     * @return -1 if the calling suffix appears lexographically before the
     *          argument suffix. 1 if the calling suffix appears lexographically
     *          after the argument suffix. 0 if the two suffixes are equal.
     */
    public int compareTo(Suffix that) {

      if (this == that) return 0;  // optimization

      int N = Math.min(this.length(), that.length());

      for (int i = 0; i < N; i++) {
        if (this.charAt(i) < that.charAt(i))
          return - 1;

        if (this.charAt(i) > that.charAt(i))
          return + 1;
      }

      return this.length() - that.length();
    }

    /**
     * object toString override
     */
    public String toString() {
      return text.substring(index);
    }// end Suffix.toString
  }//end inner class Suffix
}//end class CSA
