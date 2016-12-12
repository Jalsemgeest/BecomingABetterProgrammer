
public class Binary {

	public static void main(String[] args) {

	}
	
	// In-order Traversal
	// Visits nodes in ascending order.
	void inOrderTraversal(Node node) {
		if (node != null) {
			inOrderTraversal(node.left);
			visit(node);
			inOrderTraversal(node.right);
		}
	}
	
	// Pre-order Traversal
	// Visit current node, then children.  Root is alway visited first.
	void preOrderTraversal(Node node) {
		if (node != null) {
			visit(node);
			preOrderTraversal(node.left);
			preOrderTraversal(node.right);
		}
	}
	
	// Post-order Traversal
	// Visit the first node last.  Root is always last.
	void postOrderTraversal(Node node) {
		if (node != null) {
			postOrderTraversal(node.left);
			preOrderTraversal(node.right);
			visit(node);
		}
	}
	
	void visit(Node node) {
		System.out.println(node.data);
	}

}

class Node {
	public int data;
	public Node left, right, parent;
	private int size = 0;
	
	public Node(int data) {
		data = data;
		size = 1;
	}
	
	public void insertInOrder(int d) {
		if (d <= data) {
			if (left == null) {
				setLeftChild(new Node(d));
			} else {
				left.insertInOrder(d);
			}
		} else {
			if (right == null) {
				setRightChild(new Node(d));
			} else {
				right.insertInOrder(d);
			}
		}
		size++;
	}
	
	public int size() {
		return size;
	}
	
	public Node find(int d) {
		if (d == data) {
			return this;
		} else if (d <= data) {
			return left != null ? left.find(d) : null;
		} else if (d > data) {
			return right != null ? right.find(d) : null;
		}
		return null;
	}
	
	public void setLeftChild(Node left) {
		this.left = left;
		if (left != null) {
			left.parent = this;
		}
	}
	
	public void setRightChild(Node right) {
		this.right = right;
		if (right != null) {
			right.parent = this;
		}
	}
}

class Tree {
	public Node root;
}
