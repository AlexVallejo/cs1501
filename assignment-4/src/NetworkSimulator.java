/**
 * Created By: Alex Vallejo
 * Date: 10/02/13
 * Project: assignment-4
 * Email: amv49@pitt.edu
 * Peoplesoft: 357-8411
 */

import java.util.Scanner;

public class NetworkSimulator {

  static Network network;
  static Scanner keyboard;

  public static void main(String args[]){
    network = new Network(args[0]);

    while (true){

      printInstructions();

      keyboard = new Scanner(System.in);
      System.out.print("Enter your choice: ");
      int input = keyboard.nextInt();
      System.out.println();

      switch (input){

        // Report display current active network and connected components
        case 1:
          report();
          break;

        // Print the MST for the network
        case 2:
          network.mst();
          break;

        // Calculate the shortest path between two vertices
        case 3:
          shortestPath();
          break;

        // Take down a certain edge
        case 4:
          System.out.println("Enter from vertex: ");
          int from = keyboard.nextInt();
          System.out.println("Enter to vertex: ");
          int to = keyboard.nextInt();

          if (network.removeEdge(new Edge(from, to, 10))){
            System.out.println("");
            break;
          }
          else {
            System.out.println("This edge is not in the graph!");
            break;
          }

        // Bring up a certain edge
        case 5:
          System.out.println("Enter from vertex: ");
          System.out.println("Enter to vertex: ");
          break;

        // Change weight of certain edge
        case 6:
          changeWeight();
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

  private static void report(){
    System.out.println(network);

    UF cc = new UF(network);
    cc.printComponents();

  }

  private static void changeWeight(){
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
      return;
    }

    // Else the edge is within the graph
    network.changeWeightOfEdge(oldEdge, weight);

    // Inform the user their requested edge was changed
    System.out.println("\nChange edge " + from + " -> " + to + " from " +
        oldEdge.weight() + " to " + newEdge.weight());
  }

  private static void shortestPath(){
    System.out.print("Enter the from vertex: ");
    int one = keyboard.nextInt();
    System.out.print("Enter the to vertex: ");
    int two = keyboard.nextInt();

    network.shortestPath(one, two);
  }

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
