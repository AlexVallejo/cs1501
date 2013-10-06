import java.util.Scanner;

public class UniqueSubstrings {

  public static void main(String args[]){

    TST<String> words = new TST<String>();
    Scanner keyboard = new Scanner(System.in);
    String in;


    System.out.println("\nSubstring length: ");
    int l = keyboard.nextInt();

    //Consume remaining new line from scanner buffer
    keyboard.nextLine();

    System.out.println("Enter a string: ");
    in = keyboard.nextLine();

    for (int i = 0; i + l <= in.length(); i++){
      String sub = in.substring(i, i + l);
      words.put(sub, sub);
    }

    System.out.println("There were " + words.size() + " unique substrings.");
  }
}
