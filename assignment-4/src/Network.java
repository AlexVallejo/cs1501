/**
 * Created By: Alex Vallejo
 * Date: 10/22/13
 * Project: assignment-4
 * Email: amv49@pitt.edu
 * Peoplesoft: 357-8411
 */

public class Network extends EdgeWeightedGraph {

  /**
   * Construct a new network from the given filename
   * @param filename the name of the input file
   */
  public Network(String filename){
    super(filename);
  }

  public void mst(){

    KruskalMST mst = new KruskalMST(this);
    for (Edge e : mst.edges()) {
      StdOut.println(e);
    }

    StdOut.printf("%.5f\n", mst.weight());
  }

  public void shortestPath(int one, int two){
    DijkstraSP s = new DijkstraSP(this, one);

    if(s.hasPathTo(two)){
      System.out.println("The shortest path from "+ one +" to "+ two +" is:");
      System.out.println(s.pathTo(two));
    }

    else
      System.out.println("There is no path from " + one + " to " + two);

  }

  public void changeWeightOfEdge(Edge edge, double newWeight){

    // b/c edges are immutable, remove the old one and add a new one
    // with the updated weight

    Edge newEdge = new Edge(edge.either(), edge.other(edge.either()),
        newWeight);

    if (removeEdge(edge))
      addEdge(newEdge);
  }

  public void up(int vertex1, int vertex2, int latency){
    Edge e = new Edge(vertex1, vertex2 ,latency);
    addEdge(e);
  }
}
