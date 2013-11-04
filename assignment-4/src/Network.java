/**
 * Created By: Alex Vallejo
 * Date: 10/22/13
 * Project: assignment-4
 * Email: amv49@pitt.edu
 * Peoplesoft: 357-8411
 */

public class Network extends EdgeWeightedGraph{



  public void up(int vertex1, int vertex2, int latency){
    Edge e = new Edge(vertex1,vertex1,latency);
    addEdge(e);
  }
}
