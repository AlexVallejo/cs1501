import java.io.File;
import java.util.Scanner;

public class SpellCheck {

  public static void main(String args[]) throws Exception{

    TST<String> words = new TST<String>();

    if (args.length != 1){
      System.out.println("Usage: java SpellCheck dictionary.txt");
      System.exit(0);
    }

    String filename = args[0];
    Scanner scanner = new Scanner(new File(filename));

    while (scanner.hasNext()){
      String in = scanner.nextLine();

      words.put(in,in);
    }

    System.out.println("\nType -1 to exit.");
    Scanner keyboard = new Scanner(System.in);
    String in;

    do{
      System.out.println("\nEnter a word: ");
      in = keyboard.nextLine();

      if (in.equals("-1"))
        continue;

      if (!in.equals(words.get(in)))
        System.out.println(in + " is misspelled.");

    } while (!in.equals("-1"));

  }
}
