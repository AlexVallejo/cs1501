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

  // circular suffix array of s   - 10 points
  public CircularSuffixArray(String s){

    int length = s.length();
    this.suffixes = new Suffix[length];

    for (int i = 0; i < length; i++)
      suffixes[i] = new Suffix(s,i);

    Arrays.sort(suffixes);

  }

  private static class Suffix implements Comparable<Suffix> {
    private final String text;
    private final int index;

    private Suffix(String text, int index) {
      this.text  = text;
      this.index = index;
    }
    private int length() {
      return text.length() - index;
    }
    private char charAt(int i) {
      return text.charAt(index + i);
    }

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

    public String toString() {
      return text.substring(index);
    }
  }

  // length of s
  public int length(){
    return suffixes.length;
  }

  // returns index of ith sorted suffix - 10 points
  public int index(int i){
    return suffixes[i].length();
  }
}
