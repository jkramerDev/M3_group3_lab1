package graph;

 

public class GraphTester {

	private static DiGraph graph;
	
	public static void main(String[] args) {
	
		graph = new DiGraphImpl();
		
		//add nodes
		
		GraphNode bos = new GraphNode("Boston");
		GraphNode nyc = new GraphNode("New York City");
		GraphNode phi = new GraphNode("Philadelphia");

		graph.addNode(bos);
		graph.addNode(nyc);
		graph.addNode(phi);
		
		//add edges
		
		graph.addEdge(bos, nyc, 2);
		graph.addEdge(nyc, phi, 2);
		graph.addEdge(bos, phi, 5);

		
		//test reachablity
		
		System.out.println("phi is reachable from nyc: " + graph.nodeIsReachable(nyc, phi));
		
		//test hasCycles

		graph.addEdge(phi, bos, 5);
		
		System.out.println("has cycles: " + graph.hasCycles());

		graph.removeEdge(phi, bos);
		
		
		//test fewest hops

		System.out.println("fewest hops from bos to phi: " + graph.fewestHops(bos, phi));
		
		//test shortest path
		
		System.out.println("shortest path from bos to phi: " + graph.shortestPath(bos, phi));
		
		
	}
	
}
