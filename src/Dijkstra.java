/* 
 * Graph shortest path with Dijkstra
 * Labb 5 Uppgift 10
 * For each node calculates the neighboring node value and mark neighboring nodes until target reached.
 *
 * Viktad riktad graf. Bestämma kortaste vägarna från ett hörn i grafen till alla andra hörn.
 *  
 */


import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 *Vertex nodes graph representation
 */
class Node implements Comparable<Node>
{
    public final String name;
    public Path[] neighbor;
    public double minDistance = Double.POSITIVE_INFINITY;
    public Node previous;	//a reference to the previous vertex to get a shortest path from the source vertex to this vertex.
    public Node(String argName) { name = argName; }
    public String toString() { return name; }
    public int compareTo(Node other)	//vertex comparator
    {
        return Double.compare(minDistance, other.minDistance);
    }
}

/**
 *Edges paths representing an edge that stores its weight and target vertex(the vertex it points to) directed
 */
class Path
{
    public final Node target;
    public final double distance;
    public Path(Node argTarget, double argDistance)
    { target = argTarget; distance = argDistance; }
}

public class Dijkstra
{
	/**
	 * Shortest path
	 */
    public static void computePaths(Node source)
    {
        source.minDistance = 0.;	//distance to startNode is 0
        
        /**
         * visit each vertex u, always visiting vertex with smallest minDistance first
         */
        PriorityQueue<Node> nodQueue = new PriorityQueue<Node>();	
      	nodQueue.add(source);

	while (!nodQueue.isEmpty()) {
	    Node u = nodQueue.poll();	//retrieves and removes head of queue

            // Visit each edge
            for (Path d : u.neighbor)	//path = d : u
            {
                Node n = d.target;
                double weight = d.distance;
                double distanceThroughU = u.minDistance + weight;
		if (distanceThroughU < n.minDistance) {		//remove n from queueu
		    nodQueue.remove(n);
		    n.minDistance = distanceThroughU ;
		    n.previous = u;
		    nodQueue.add(n);	//re-add n to queueue
		}
            }
        }
    }

    public static List<Node> getShortestPathTo(Node target)		//getting the shortest path
    {
        List<Node> path = new ArrayList<Node>();
        for (Node nod = target; nod != null; nod = nod.previous)
            path.add(nod);
        Collections.reverse(path);
        return path;
    }
    
    /**
     * main
     */
    public static void main(String[] args)
    {
    Node n0 = new Node("Stockholm");
	Node n1 = new Node("Göteborg");
	Node n2 = new Node("Malmö");
	Node n3 = new Node("Uppsala");
	Node n4 = new Node("Västerås");

	n0.neighbor = new Path[]{ new Path(n1, 5),
	                             new Path(n2, 10),
                               new Path(n3, 8) };
	n1.neighbor = new Path[]{ new Path(n0, 5),
	                             new Path(n2, 3),
	                             new Path(n4, 7) };
	n2.neighbor = new Path[]{ new Path(n0, 10),
                               new Path(n1, 3) };
	n3.neighbor = new Path[]{ new Path(n0, 8),
	                             new Path(n4, 2) };
	n4.neighbor = new Path[]{ new Path(n1, 7),
                               new Path(n3, 2) };
	Node[] nodes = { n0, n1, n2, n3, n4 };
        computePaths(n0);	//compute path Source node here
        for (Node n : nodes)
	{
	    System.out.println("Distance to " + n + ": " + n.minDistance);
	    List<Node> path = getShortestPathTo(n);
	    System.out.println("Path: " + path);
	}
    }
}
