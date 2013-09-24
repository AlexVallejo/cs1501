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
import java.util.Scanner;

public class ExpressionDisplayer {
  public static void main(String args[]) {

    //Verify the program was used correctly
    if (args.length != 1) {
      System.out.println("Correct Usage: java ExpressionDisplayer exp.txt");
      System.out.println("Try again....");
      System.exit(0);
    }

    //Read all of the atoms!
    try {
      BufferedReader reader = new BufferedReader(new FileReader(args[0]));
      Scanner keyboard = new Scanner(System.in);

      String line;

      //Process the expressions!
      do {
        line = reader.readLine();

        if (line == null)
          continue;

        try {
          Expression exp = new Expression(line);
          exp.displayNormalized();
          System.out.println("\n" + exp);
        }

        catch (ParseError pe){
          System.out.println("\n" + pe + "\n");
        }

        String input;
        char answer;
        do {
          System.out.print("Do you want to continue (y/n):");
          input = keyboard.nextLine();

          if (input.contains("y"))
            answer = 'y';

          else
            answer = 'n';

          if (answer == 'n')
            System.exit(0);

        } while (answer != 'y'); //input verification loop
      } while (line != null); //while there are more expressions

      System.out.println("\nEnd of file reached");
      System.exit(0);
    }//End buffered reader exception

    catch (IOException ex) {
      System.out.println(ex.toString());
      System.out.println("Sorry, but there was a problem with your " +
          "input file");
      System.exit(1);
    }
  }//end main
}
