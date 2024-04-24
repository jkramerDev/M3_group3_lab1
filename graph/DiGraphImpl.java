package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

 
//test comment another and another
public class DiGraphImpl implements DiGraph{

	private List<GraphNode> nodeList = new ArrayList<>();

	@Override
	public Boolean addNode(GraphNode node) {
		return nodeList.add(node);
	}

	@Override
	public Boolean removeNode(GraphNode node) {
		for(GraphNode n : this.nodeList) {
			if(n.getNeighbors().contains(node)) {
				if(!n.removeNeighbor(node))
					return false;
			}
		}
		
		return this.nodeList.remove(node);
	}

	@Override
	public Boolean setNodeValue(GraphNode node, String newNodeValue) {
		node.setValue(newNodeValue);
		return node.getValue().equals(newNodeValue);
	}

	@Override
	public String getNodeValue(GraphNode node) {
		return node.getValue();
	}

	@Override
	public Boolean addEdge(GraphNode fromNode, GraphNode toNode, Integer weight) {
		return fromNode.addNeighbor(toNode, weight);
	}

	@Override
	public Boolean removeEdge(GraphNode fromNode, GraphNode toNode) {
		return fromNode.removeNeighbor(toNode);
	}

	@Override
	public Boolean setEdgeValue(GraphNode fromNode, GraphNode toNode, Integer newWeight) {
		if (!this.removeEdge(fromNode, toNode))
			return false;
		return this.addEdge(fromNode, toNode, newWeight);
	}

	@Override
	public Integer getEdgeValue(GraphNode fromNode, GraphNode toNode) {
		List<GraphNode> fromNeighbors = fromNode.getNeighbors();
		if(!fromNeighbors.contains(toNode))
			return -1;
		else
			return fromNode.getDistanceToNeighbor(toNode);
	}

	@Override
	public List<GraphNode> getAdjacentNodes(GraphNode node) {
		return node.getNeighbors();
	}

	@Override
	public Boolean nodesAreAdjacent(GraphNode fromNode, GraphNode toNode) {
		List<GraphNode> fromAdjacencies = fromNode.getNeighbors();
		return fromAdjacencies.contains(toNode);
	}

	@Override
	public Boolean nodeIsReachable(GraphNode fromNode, GraphNode toNode) {
		LinkedList<GraphNode> queue = new LinkedList<GraphNode>();
		
		Set<GraphNode> visitedGraphNodes = new HashSet<GraphNode>();
		
		queue.add(fromNode);
		visitedGraphNodes.add(toNode);
		while (!queue.isEmpty()) {

			GraphNode element = queue.remove();
			List<GraphNode> neighbours = element.getNeighbors();

			for (int i = 0; i < neighbours.size(); i++) {
				GraphNode n = neighbours.get(i);
				if (n != null && !visitedGraphNodes.contains(n)) {
					queue.add(n);
					visitedGraphNodes.add(n);

				}
				if (n.getValue().equals(toNode.getValue())) {
					return true;
				}		
			}	
		}
		
		return false;
	}

	@Override
	public Boolean hasCycles() {
		for(GraphNode n : this.nodeList) {
			if(this.nodeIsReachable(n, n)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public List<GraphNode> getNodes() {
		return this.nodeList;
	}

	@Override
	public GraphNode getNode(String nodeValue) {
		for(GraphNode n : this.nodeList) {
			if(n.getValue().equals(nodeValue)) {
				return n;
			}
		}
		
		throw new NoSuchElementException();
	}

	@Override
	public int fewestHops(GraphNode fromNode, GraphNode toNode) {
		GraphNode targetFromNode = getNode(fromNode.getValue());
		GraphNode targetToNode = getNode(toNode.getValue());
		
		HashMap<GraphNode, Integer> visited = new HashMap<>();

		LinkedList<GraphNode> toVisit = new LinkedList<>();
		LinkedList<Integer> toVisitWeights = new LinkedList<>();
		toVisit.add(targetFromNode);
		toVisitWeights.add(0);

		while(!visited.keySet().contains(targetToNode) && !toVisit.isEmpty()) {
			GraphNode activeNode = toVisit.poll();
			Integer activeHops = toVisitWeights.poll();

			// if i have been visited
			if(visited.containsKey(activeNode)) {
				// if i have a lower hop count now than before
				if (visited.get(activeNode) > activeHops) {
					visited.put(activeNode, activeHops);
				}
			} else {
				visited.put(activeNode, activeHops);
			}

			for(GraphNode neighbor : activeNode.getNeighbors()) {
				// for each neighbor
				if(!visited.containsKey(neighbor)) {
					// if it hasn't been visited
					toVisit.add(neighbor);
					toVisitWeights.add(activeHops + 1);
				}
			}
		}
		
		try {
			return visited.get(targetToNode);
		} catch(Exception e) {
			return -1;
		}
	}

	@Override
	public int shortestPath(GraphNode fromNode, GraphNode toNode) {
		
		// this would be better if it were changed to dijkstra's algorithm instead of this
		
		if(this.hasCycles()) {
			return -1;
		}
		
		GraphNode targetFromNode = getNode(fromNode.getValue());
		GraphNode targetToNode = getNode(toNode.getValue());
		
		HashMap<GraphNode, Integer> visited = new HashMap<>();

		LinkedList<GraphNode> toVisit = new LinkedList<>();
		LinkedList<Integer> toVisitWeights = new LinkedList<>();
		toVisit.add(targetFromNode);
		toVisitWeights.add(0);

		while(!toVisit.isEmpty()) {
			GraphNode activeNode = toVisit.poll();
			Integer activeWeight = toVisitWeights.poll();

			// if i have been visited
			if(visited.containsKey(activeNode)) {
				// if i have a lower hop count now than before
				if (visited.get(activeNode) > activeWeight) {
					visited.put(activeNode, activeWeight);
				}
			} else {
				visited.put(activeNode, activeWeight);
			}

			for(GraphNode neighbor : activeNode.getNeighbors()) {
				// for each neighbor
				toVisit.add(neighbor);
				toVisitWeights.add(activeWeight + activeNode.getDistanceToNeighbor(neighbor));
			}
		}
		
		try {
			return visited.get(targetToNode);
		} catch(Exception e) {
			return -1;
		}
	}
	
	
	
}
