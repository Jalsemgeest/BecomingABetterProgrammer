package graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

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

	// 4.7 - Build Order for Projects
	// Option 2
	static Stack<Project> finalBuildOrderTwo(String[] projects, String[][] dependencies) {
		ProjectGraph graph = buildGraph(projects, dependencies);
		return orderProjectsTwo(graph.getNodes());
	}
	
	static Stack<Project> orderProjectsTwo(ArrayList<Project> projects) {
		Stack<Project> stack = new Stack<Project>();
		for (Project project : projects) {
			if (project.getState() == Project.State.BLANK) {
				if (!doDFS(project, stack)) {
					return null;
				}
			}
		}
		return stack;
	}
	
	static boolean doDFS(Project project, Stack<Project> stack) {
		if (project.getState() == Project.State.PARTIAL) {
			return false; // Cycle
		}
		
		if (project.getState() == Project.State.BLANK) {
			project.setState(Project.State.PARTIAL);
			ArrayList<Project> children = project.getChildren();
			
			for (Project child : children) {
				if (!doDFS(child, stack)) {
					return false;
				}
			}
			
			project.setState(Project.State.COMPLETE);
			stack.push(project);
		}
		
		return true;
	} 
	
	
	// Option 1
	static Project[] finalBuildOrder(String[] projects, String[][] dependencies) {
		ProjectGraph graph = buildGraph(projects, dependencies);
		return orderProjects(graph.getNodes());
	}
	
	static ProjectGraph buildGraph(String[] projects, String[][] dependencies) {
		ProjectGraph graph = new ProjectGraph();
		for (String project : projects) {
			graph.getOrCreateNode(project);
		}
		
		for (String[] dependency : dependencies) {
			String first = dependency[0];
			String second = dependency[1];
			graph.addEdge(first, second);
		}
		
		return graph;
	}
	
	static Project[] orderProjects(ArrayList<Project> projects) {
		Project[] order = new Project[projects.size()];
		
		int endOfList = addNonDependent(order, projects, 0);
		
		int toBeProcessed = 0;
		while (toBeProcessed < order.length) {
			Project current = order[toBeProcessed];
			
			if (current == null) {
				return null;
			}
			
			ArrayList<Project> children = current.getChildren();
			for (Project child : children) {
				child.decrementDependencies();
			}
			
			endOfList = addNonDependent(order, children, endOfList);
			toBeProcessed++;
		}
		
		return order;
	}
	
	static int addNonDependent(Project[] order, ArrayList<Project> projects, int offset) {
		for (Project project : projects) {
			if (project.getNumberOfDependencies() == 0) {
				order[offset] = project;
				offset++;
			}
		}
		return offset;
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

class ProjectGraph {
	private ArrayList<Project> nodes = new ArrayList<Project>();
	private HashMap<String, Project> map = new HashMap<String, Project>();

	public Project getOrCreateNode(String name) {
		if (!map.containsKey(name)) {
			Project node = new Project(name);
			nodes.add(node);
			map.put(name, node);
		}
		
		return map.get(name);
	}
	
	public void addEdge(String startName, String endName) {
		Project start = getOrCreateNode(startName);
		Project end = getOrCreateNode(endName);
		start.addNeighbor(end);
	}
	
	public ArrayList<Project> getNodes() { return nodes; }
}

class Project {
	private ArrayList<Project> children = new ArrayList<Project>();
	private HashMap<String, Project> map = new HashMap<String, Project>();
	private String name;
	private int dependencies = 0;
	public enum State { COMPLETE, PARTIAL, BLANK };
	
	private State state = State.BLANK;
	
	public Project(String n) { name = n; }
	
	public void addNeighbor(Project node) {
		if (!map.containsKey(node.getName())) {
			children.add(node);
			map.put(node.getName(), node);
			node.incrementDependencies();
		}
	}
	
	public void incrementDependencies() {
		dependencies++;
	}
	
	public void decrementDependencies() {
		dependencies--;
	}
	
	public State getState() {
		return state;
	}
	
	public void setState(State state) {
		this.state = state;
	}
	
	public String getName() { return name; }
	public ArrayList<Project> getChildren() { return children; }
	public int getNumberOfDependencies() { return dependencies; }
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
