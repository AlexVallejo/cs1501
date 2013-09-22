/**
 * Created By: Alex Vallejo
 * Date: 9/14/13
 * Project: assignment-2
 * Email: amv49@pitt.edu
 * Peoplesoft: 357-8411
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ExpressionEvaluator {
  public static void main(String args[]) {

    boolean atoms[];
    ArrayList<Expression> exps = new ArrayList<Expression>();

    if (args.length != 1) {
      System.out.println("Correct Usage: java ExpressionEvaluator exp" +
          ".txt");
      System.out.println("Try again....");
      System.exit(0);
    }

    try {
      BufferedReader reader = new BufferedReader(new FileReader(args[0]));
      Scanner keyboard = new Scanner(System.in);

      String line;
      for (int i = 0; i < 26; i++) {
        line = reader.readLine();
        line = line.trim();

        String str = new String();
        str += line.charAt(0);

        if (line.contains("true"))
          Expression.setAtom(str,"true");

        else if (line.contains("false"))
          Expression.setAtom(str,"false");

        else {
          System.out.println("There must be 26 atoms declared.");
          System.out.println("Error reading line #" + i);
          System.out.printf("line %d: %s", i, line);
          continue;
        }
      }

      printAtoms();
      System.out.println("\n");

      do {
        line = reader.readLine();

        if (line == null)
          continue;

        try {
          Expression exp = new Expression(line);
          exp.displayNormalized();
          exps.add(exp);
          System.out.println(exp);
        }

        catch (ParseError pe){
          System.out.println("\n" + pe);

          char input;

          do {
            System.out.print("\n" + "Do you want to continue (y/n):");
            input = keyboard.nextLine().charAt(0);

            if (input == 'n')
              System.exit(0);

          } while (input != 'y');

          System.out.println();
        }
      } while (line != null);

      System.out.println("\nEnd of file reached");
    }//End buffered reader exception

    catch (IOException ex) {
      System.out.println(ex.toString());
      System.out.println("Sorry, but there was a problem with your " +
          "input file");
      System.exit(1);
    }
  }//end main

  public static void printAtoms() {
    for (int i = 0; i < Expression.atoms.length; i++)
      System.out.printf("%c %-5s ", i + 65, Expression.atoms[i]);
  }
}
