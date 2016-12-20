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
	
	public TreeNode() {
		
	}
	
	public TreeNode(int d) {
		data = d;
	}
}
