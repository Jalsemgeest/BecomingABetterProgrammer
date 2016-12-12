import java.util.ArrayList;

public class BTree {

	public static void main(String[] args) {

	}

	/*
	 * B-Tree is a form of binary search tree.
	 * B-tree of order m satisfies following properties:
	 * 1. Each node has at most m children
	 * 2. Each internal node has at least Ceiling(m/2) children
	 * 3. Root has at least 2 children if it is not a leaf
	 * 4. A non-leaf node with k children has (k-1) keys
	 * 5. All leaves appear in same level (keeping height minimal)
	 * 
	 * Example:
	 * 				[ 10 ]
	 * 				/
	 * 		   [ 5 | 9 ]
	 * 		  /    |    \
	 * 		 <5	 > 5 < 9   > 9
	 */
	
	// Number of possible children.
	private int m;
	private BTreeNode root;
	// Each node can only have m - 1 nodes.
	
	public BTree(int m) {
		m = m;
		root = new BTreeNode(m);
	}
	
	public void insert(int data) {
		if (root.valuesSize() == 0) {
			root.add(data);
			return;
		}
		root.insert(data);
	}
	
	
}


class BTreeNode {
	private int m;
	public ArrayList<Integer> values;
	public ArrayList<BTreeNode> children;
	
	public BTreeNode(int m) {
		m = m;
		values = new ArrayList<Integer>();
		children = new ArrayList<BTreeNode>();
	}
	
	public int valuesSize() {
		return values.size();
	}
	
	public void add(int data) {
		values.add(data);
	}
	
	public void insert(int data) {
		// If there are no children, then add it to values.
		if (childrenCount() == 0) {
			// If values length <= m - 1;
			if (values.size() < m) {
				addToValues(data);
			} else {
				split(data);
			}
		}
		int index = -1;
		for (int i = 0; i < values.size(); i++) {
			if (data < values.get(i)) {
				index = i;
			}
		}
	}
	
	private void addToValues(int data) {
		int index = 0;
		for (int i = 0; i < values.size(); i++) {
			if (values.get(i) > data) {
				index = i;
				break;
			}
		}
		values.add(index, data);
	}
	
	private void split(int data) {
		// Create left and right.
		BTreeNode left = new BTreeNode(m);
		BTreeNode right = new BTreeNode(m);
		
		int mid = (int) Math.ceil(values.size() / 2);
		
		for (int i = 0; i < values.size(); i++) {
			
		}
	}
	
	public int childrenCount() {
		return children.size();
	}
	
}
