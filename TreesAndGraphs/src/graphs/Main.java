package graphs;

import java.util.ArrayList;
import java.util.LinkedList;

enum State {
	Unvisited,
	Visited,
	Visiting;
}

public class Main {

	public static void main(String[] args) {
		Graph g = createGraph(4);
		System.out.println("\nCracking Code Interview 4.1:");
		System.out.println(search(g, g.getNodes().get(0), g.getNodes().get(g.getNodes().size() - 1)));
	}

	
	// Really just makes a list.
	static Graph createGraph(int nodes) {
		ArrayList<Node> list = new ArrayList<Node>();
		
		// Create all nodes.
		for (int i = 0; i < nodes; i++) {
			list.add(new Node(i));
		}
		
		for (int i = 0; i < list.size() - 1; i++) {
			list.get(i).addAdjacent(list.get(i+1));
			list.get(i+1).addAdjacent(list.get(i));
		}
		
		return new Graph(list);
	}
	
	static boolean search(Graph g, Node start, Node end) {
		 if (start == end) return true;
		 
		 LinkedList<Node> q = new LinkedList<Node>();
		 
		 for (Node u : g.getNodes()) {
			 u.state = State.Unvisited;
		 }
		 start.state = State.Visited;
		 q.add(start);
		 Node u;
		 while (!q.isEmpty()) {
			 u = q.removeFirst();
			 if (u != null) {
				 for (Node v : u.getAdjacent()) {
					 if (v.state == State.Unvisited) {
						 if (v == end) {
							 return true;
						 } else {
							 v.state = State.Visiting;
							 q.push(v);
						 }
					 }
				 }
				 u.state = State.Visited;
			 }
		 }
		 
		 return false;
	}
	
}

class Graph {
	private ArrayList<Node> nodes;
	
	public Graph() {
		nodes = new ArrayList<Node>();
	}
	
	public Graph(ArrayList<Node> l) {
		nodes = l;
	}
	
	public ArrayList<Node> getNodes() {
		return nodes;
	}
}

class Node {
	public State state = State.Unvisited;
	private ArrayList<Node> adjacent;
	private int data;
	
	public Node() {
		adjacent = new ArrayList<Node>();
	}
	
	public Node(int i) {
		data = i;
		adjacent = new ArrayList<Node>();
	}
	
	public void addAdjacent(Node n) {
		if (!adjacent.contains(n)) {
			adjacent.add(n);
		}
	}
	
	public ArrayList<Node> getAdjacent() {
		return adjacent;
	}
}
