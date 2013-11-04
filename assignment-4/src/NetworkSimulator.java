/**
 * Created By: Alex Vallejo
 * Date: 10/02/13
 * Project: assignment-4
 * Email: amv49@pitt.edu
 * Peoplesoft: 357-8411
 */

import java.util.Scanner;

public class NetworkSimulator {

  public static void main(String args[]){
    Network network = new Network(args[0]);

    while (true){

      printInstructions();

      Scanner keyboard = new Scanner(System.in);
      System.out.println("Enter your choice: ");
      int input = keyboard.nextInt();

      switch (input){

        // Report display current active network and connected components
        case 1:
          break;

        // Print the MST for the network
        case 2:
          network.mst();
          break;

        // Calculate the shortest path between two vertices
        case 3:
          int one = keyboard.nextInt();
          int two = keyboard.nextInt();
          network.shortestPath(one, two);
          break;

        // Take down a certain edge
        case 4:
          break;

        // Bring a certain edge
        case 5:
          break;

        // Change weight of certain edge
        case 6:
          break;

        // Does the graph have a Eulerian tour or path?
        case 7:
          break;

        // Quit the program
        case 8:
          System.exit(0);
          break;
      }// end switch
    }// end while(true)
  }// end main

  private static void printInstructions(){
    System.out.println("\n1. R (eport)");
    System.out.println("2. M (inimum Spanning Tree)");
    System.out.println("3. S (hortest Path from) i j");
    System.out.println("4. D (own edge) i j");
    System.out.println("5. U (p edge) i j");
    System.out.println("6. C (hange weight of edge) i j x");
    System.out.println("7. E (ulerian)");
    System.out.println("8. Q (uit)\n");  }
}
