/**
 * Created By: Alex Vallejo
 * Date: 10/02/13
 * Project: assignment-4
 * Email: amv49@pitt.edu
 * Peoplesoft: 357-8411
 */

import java.util.Iterator;
import java.util.Scanner;

public class NetworkSimulator {

  public static void main(String args[]){
    Network network = new Network(args[0]);

    while (true){

      printInstructions();

      Scanner keyboard = new Scanner(System.in);
      System.out.print("Enter your choice: ");
      int input = keyboard.nextInt();
      System.out.println();

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
          System.out.print("Enter the from vertex: ");
          int one = keyboard.nextInt();
          System.out.print("Enter the to vertex: ");
          int two = keyboard.nextInt();

          network.shortestPath(one, two);

          break;

        // Take down a certain edge
        case 4:
          break;

        // Bring up a certain edge
        case 5:
          break;

        // Change weight of certain edge
        case 6:
          // Collect the required input from the user
          System.out.print("Enter from vertex: ");
          int from = keyboard.nextInt();
          System.out.print("Enter to vertex: ");
          int to = keyboard.nextInt();
          System.out.print("Enter the new weight: ");
          double weight = keyboard.nextDouble();

          Edge newEdge = new Edge(from, to, weight);

          // Find the edge to be modified and cache it for display purposes
          Edge oldEdge = null;
          for (Edge edge : network.edges())
            if (newEdge.equals(edge))
              oldEdge = edge;

          // If the edge does not exist within the network: EXIT
          if (oldEdge == null){
            System.out.println("This edge was not found in the graph. Try " +
                "again.");
            break;
          }

          // Else the edge is within the graph
          network.changeWeightOfEdge(oldEdge, weight);

          // Inform the user their requested edge was changed
          System.out.println("\nChange edge " + from + " -> " + to + " from " +
              oldEdge.weight() + " to " + newEdge.weight());
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
