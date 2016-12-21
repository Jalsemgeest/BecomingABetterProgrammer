package trees;

import java.util.ArrayList;
import java.util.LinkedList;

public class Main {
	// Chapter 4 (pg. 112)
	// Answers on pg. 253
	public static void main(String[] args) {
		System.out.println("\nCracking Code Interview 4.2:");
		System.out.println("\nCracking Code Interview 4.3:");
	}

	// 4.6 Find the successor of a BST Node
	static TreeNode inOrderSuccessor(TreeNode node) {
		if (node == null) {
			return null;
		}
		
		// Found a right child, return the left most child of that subtree.
		if (node.right != null) {
			return leftMostChild(node.right);
		} else {
			// The node is a right child
			TreeNode q = node;
			TreeNode x = q.parent;
			// Go up until we're on left instead of right.
			while (x != null && x.left != q) {
				q = x;
				x = x.parent;
			}
			return x;
		}
	}
	
	static TreeNode leftMostChild(TreeNode n) {
		if (n == null) {
			return null;
		}
		while (n.left != null) {
			n = n.left;
		}
		return n;
	}
	
	
	static Integer lastPrinted = null;
	// 4.5 - Check if BST is valid
	// In-Order Traversal
	static boolean checkBSTInOrder(TreeNode root) {
		if (root == null) return true;
		
		// Check and recurse left
		if (!checkBSTInOrder(root.left)) return false;
		
		// Check current
		if (lastPrinted != null && root.data <= lastPrinted) {
			return false;
		}
		lastPrinted = root.data;
		
		// Check and recurse right
		if (!checkBSTInOrder(root.right)) return false;
		
		return true;
	}
	
	// Min/Max Solution - awesome solution
	static boolean checkBST(TreeNode node) {
		return checkBST(node, null, null);
	}
	
	static boolean checkBST(TreeNode node, Integer min, Integer max) {
		if (node == null) {
			return true;
		}
		
		if ((min != null && node.data <= min) || (max != null && node.data > max)) {
			return false;
		}
		
		if (!checkBST(node.left, min, node.data) || !checkBST(node.right, node.data, max)) {
			return false;
		}
		
		return true;
	}
	
	
	static int checkHeight(TreeNode root) {
		if (root == null) return -1;
		
		int leftHeight = checkHeight(root.left);
		if (leftHeight == Integer.MIN_VALUE) return Integer.MIN_VALUE;
		
		int rightHeight = checkHeight(root.right);
		if (rightHeight == Integer.MIN_VALUE) return Integer.MIN_VALUE;
		
		int heightDiff = leftHeight - rightHeight;
		if (Math.abs(heightDiff) > 1) {
			return Integer.MIN_VALUE;
		} else {
			return Math.max(leftHeight, rightHeight) + 1;
		}
	}
	
	static boolean isBalanced(TreeNode root) {
		return checkHeight(root) != Integer.MIN_VALUE;
	}
	
	// Breadth First Search Implementation 4.3.
	static ArrayList<LinkedList<TreeNode>> createLevelLinkedListBFS(TreeNode root) {
		ArrayList<LinkedList<TreeNode>> result = new ArrayList<LinkedList<TreeNode>>();
		LinkedList<TreeNode> list = new LinkedList<TreeNode>();
		if (root != null) {
			list.add(root);
		}
		
		while (list.size() > 0) {
			result.add(list);
			LinkedList<TreeNode> parents = list;
			list = new LinkedList<TreeNode>();
			for (TreeNode parent : parents) {
				if (parent.left != null) {
					list.add(parent.left);
				}
				if (parent.right != null) {
					list.add(parent.right);
				}
			}
		}
		return result;
	}
	
	// Depth First Search Implementation 4.3.
	static void createLevelLinkedListDFS(TreeNode root, ArrayList<LinkedList<TreeNode>> lists, int level) {
		if (root == null) return; // Base case
		
		LinkedList<TreeNode> list = null;
		if (lists.size() == level) {
			// We do not have this level yet.
			list = new LinkedList<TreeNode>();
			lists.add(list);
		} else {
			list = lists.get(level);
		}
		
		list.add(root);
		createLevelLinkedListDFS(root.left, lists, level + 1);
		createLevelLinkedListDFS(root.right, lists, level + 1);
	}
	
	static ArrayList<LinkedList<TreeNode>> createLevelLinkedListDFS(TreeNode root) {
		ArrayList<LinkedList<TreeNode>> list = new ArrayList<LinkedList<TreeNode>>();
		createLevelLinkedListDFS(root, list, 0);
		return list;
	}
	
	static TreeNode createMinimalBST(int[] array) {
		return  createMinimalBST(array, 0, array.length - 1);
	}

	private static TreeNode createMinimalBST(int[] array, int start, int end) {
		if (start < end) return null;
		int mid = (start + end) / 2;
		TreeNode n = new TreeNode(array[mid]);
		n.left = createMinimalBST(array, start, mid - 1);
		n.right = createMinimalBST(array, mid + 1, end);
		return n;
	}
	
}

class TreeNode {
	public int data;
	public TreeNode left = null;
	public TreeNode right = null;
	public TreeNode parent = null;
	
	public TreeNode() {
		
	}
	
	public TreeNode(int d) {
		data = d;
	}
}
