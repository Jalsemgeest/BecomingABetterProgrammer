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

	// 4.12

	// Approach 2 - Optimized
	static int countPathsWithSumOptimized(TreeNode root, int targetSum) {
		return countPathsWithSum(TreeNode root, targetSum, 0, new HashMap<Integer, Integer>());
	}

	static int countPathsWithSum(TreeNode node, int targetSum, int runningSum, HashMap<Integer, Integer> pathCount) {
		if (node == null) return 0; // Base case

		// Count paths with sum ending at the current node.
		runningSum += node.data;
		int sum = runningSum - targetSum;
		int totalPaths = pathCount.getOrDefault(sum, 0);

		// If runningSum equals targetSum, then one additional path starts at root.
		// Add in this path.
		if (runningSum == targetSum) {
			totalPaths++;
		}

		// Increment pathCount, recurse, then decrement pathCount.
		incrementHashTable(pathCount, runningSum, 1); // Increment pathcount.
		totalPaths += countPathsWithSum(node.left, targetSum, runningSum, pathCount);
		totalPaths += countPathsWithSum(node.right, targetSum, runningSum, pathCount);
		incrementHashTable(pathCount, runningSum, -1);

		return totalPaths;
	}

	static void incrementHashTable(HashMap<Integer, Integer> table, int key, int delta) {
		int newCount = table.getOrDefault(key, 0) + delta;
		if (newCount == 0) { // Remove when zero to reduce space usage
			table.remove(key);
		} else {
			table.put(key, newCount);
		}
	}

	// Approach 1 - brute force
	static int countPathsWithSum(TreeNode root, int targetSum) {
		if (root == null) return 0;

		// Count paths with sum starting from root.
		int pathsFromRoot = countPathsWithSumForNode(root, targetSum, 0);

		// Try the nodes on left and right
		int pathsOnLeft = countPathsWithSum(root.left, targetSum);
		int pathsOnRight = countPathsWithSum(root.right, targetSum);

		return pathsFromRoot + pathsOnLeft + pathsOnRight;
	}

	// Return the number of paths with this sum starting from this node.
	static int countPathsWithSumForNode(TreeNode node, int targetSum, int currentSum) {
		if (node == null) return 0;
		
		currentSum += node.data;

		int totalPaths = 0;
		if (currentSum == targetSum) {
			totalPaths++;
		}

		totalPaths += countPathsWithSumForNode(node.left, targetSum, currentSum);
		totalPaths += countPathsWithSumForNode(node.right, targetSum, currentSum);

		return totalPaths;
	}


	// 4.10 - See if one tree contains another tree.  Assuming one is larger than the other.
	// Approach 2 - average case runtime is better, but worst
	// case runtime is worse than approac 1.
	static boolean containsTree2(TreeNode t1, TreeNode t2) {
		if (t2 == null) return true;
		return subTree(t1, t2);
	}

	static boolean subTree(TreeNode t1, TreeNode t2) {
		if (t1 == null) {
			return false // big tree empty && subtree still not found.
		} else if (t1.data == t2.data && matchTree(t1, t2)) {
			return true;
		}
		return subTree(t1.left, t2) || subTree(t1.right, t2);
	}

	static boolean matchTree(TreeNode t1, TreeNode t2) {
		if (t1 == null && t2 == null) {
			return true;
		} else if (t1 == null || t2 == null) {
			return false;
		} else if (t1.data != t2.data) {
			return false;
		} else {
			return matchTree(t1.left, t2.left) && matchTree(t1.right, t2.right);
		}
	}

	// Approach 1 - takes O(n + m)
	static boolean containsTree(TreeNode t1, TreeNode t2) {
		StringBuilder string1 = new StringBuilder();
		StringBuilder string2 = new StringBuilder();

		getOrderString(t1, string1);
		getOrderString(t2, string2);

		return s1.indexOf(string2.toString()) != -1;
	}

	static void getOrderString(TreeNode node, StringBuilder sb) {
		if (node == null) {
			sb.append("X");
			return;
		}
		sp.append(node.data + " ");
		getOrderString(node.left, sb);
		getOrderString(node.right, sb);
	}

	// 4.9 - Print out all sequences of an array that could make a given tree.
	static ArrayList<LinkedList<Integer>> allSequences(TreeNode node) {
		ArrayList<LinkedList<Integer>> result = new ArrayList<LinkedList<Integer>>();

		if (node == null) {
			result.add(new LinkedList<Integer>());
			return result;
		}

		LinkedList<Integer> prefix = new LinkedList<Integer>();
		prefix.add(node.data);

		// Recurse on left and right subtrees
		ArrayList<LinkedList<Integer>> leftSeq = allSequences(node.left);
		ArrayList<LinkedList<Integer>> rightSeq = allSequences(node.right);

		// Weave together each list from the left and right sides.
		for (LinkedList<Integer> left : leftSeq) {
			for (LinkedList<Integer> right: rightSeq) {
				ArrayList<LinkedList<Integer>> weaved = new ArrayList<LinkedList<Integer>>();
				weaveLists(left, right, weaved, prefix);
				result.addAll(weaved);
			}
		}

		return result;
	}

	// Weaves lists together. Works by removing head of one list, recursing, and then doing it to the other list.
	static void weaveLists(ArrayList<Integer> first, ArrayList<Integer> second, ArrayList<LinkedList<Integer>> results, LinkedList<Integer> prefix) {
		// One list is empty, add remainder to a cloned prefix and store the result.
		if (first.size() == 0 || second.size() == 0) {
			LinkedList<Integer> result = (LinkedList<Integer>) prefix.clone();
			result.addAll(first);
			result.addAll(second);
			results.add(result);
			return;
		}

		// Recurse with head of first added to prefix.  Removing the head will
		// damage first, so we'll put it back where we found it afterwards.
		int headFirst = first.removeFirst();
		prefix.addLast(headFirst);
		weaveLists(first, second, results, prefix);
		prefix.removeLast();
		first.addFirst(headFirst);

		// Do the same thing with second.
		int headSecond = second.removeFirst();
		prefix.addLast(headSecond);
		weaveLists(first, second, results, prefix);
		prefix.removeLast();
		second.addFirst(headSecond);
	}

	// 4.8 - Find Common Ancestor of non-BST
	// Solution 4 - Optimized
	static TreeNode commonAncestor4(TreeNode root, TreeNode p, TreeNode q) {
		Result r = commonAncestorHelper(root, p, q);
		if (r.isAncestor) {
			return r.node;
		}
		return null;
	}

	static Result commonAncestorHelper(TreeNode root, TreeNode p, TreeNode q) {
		if (root == null) return new Result(null, false);

		if (root == p && root == q) {
			return new Result(root, true);
		}

		Result rx = commonAncestorHelper(root.left, p, q);
		if (rx.isAncestor) {
			return rx;
		}

		Result ry = commonAncestorHelper(root.right, p, q);
		if (ry.isAncestor) {
			return ry;
		}

		if (rx.node != null && ry.node != null) {
			return new Result(root, true);
		} else if (root == p || root == q) {
			boolean isAncestor = rx.node != null || ry.node != null;
			return new Result(root, isAncestor);
		} else {
			return new Result(rx.node != null ? rx.node : ry.node, false);
		}
	}

	// Solution 3 - without links to parents
	static TreeNode commonAncestor3(TreeNode root, TreeNode p, TreeNode q) {
		// Error check - one node is not in the tree.
		if (!covers(root, p) || !covers(root, q)) {
			return null;
		}
		return ancestorHelper(root, p, q);
	}

	static TreeNode ancestorHelper(TreeNode root, TreeNode p, TreeNode q) {
		if (root == null || root == p || root == q) {
			return root;
		}

		boolean pIsOnLeft = covers(root.left, p);
		boolean qIsOnLeft = covers(root.left, q);
		if (pIsOnLeft != qIsOnLeft) { // On different sides.
			return root;
		}
		TreeNode childSide = pIsOnLeft ? root.left : root.right;
		return ancestorHelper(childSide, p, q);
	}

	// Solution 2 - better worst case run time.
	// O(t) - where t is size of subtree of first common ancestor
	static TreeNode commonAncestor2(TreeNode root, TreeNode p, TreeNode q) {
		// Check if either is not in the tree, or if one covers the other.
		if (!covers(root, p) || !covers(root, q)) {
			return null;
		} else if (covers(p, q)) {
			return p;
		} else if (covers(q, p)) {
			return q;
		}

		TreeNode sibling = getSibling(p);
		TreeNode parent = p.parent;
		while (!covers(sibling, q)) {
			sibling = getSibling(parent);
			parent = parent.parent;
		}
		return parent;
	}

	static boolean covers(TreeNode root, TreeNode p) {
		if (root == null) return false;
		if (root == p) return true;
		return covers(root.left, p) || covers(root.right, p);
	}

	static TreeNode getSibling(TreeNode node) {
		if (node == null || node.parent == null) {
			return null;
		}

		TreeNode parent = node.parent;
		return parent.left == node ? parent.right : parent.left;
	}

	// Solution 1 - O(d) time, where d is the deeper node
	static TreeNode commonAncestor(TreeNode p, TreeNode q) {
		int delta = depth(p) - depth(q);
		TreeNode first = delta > 0 ? q : p; // get shallower node
		TreeNode second = delta > 0 ? p : q; // get deeper node
		second = goUpBy(second, Math.abs(delta));

		while (first != second && first != null && second != null) {
			first = first.parent;
			second = second.parent;
		}

		return first == null || second == null ? null : first;
	}

	static goUpBy(TreeNode node, int delta) {
		while (delta > 0 && node != null) {
			node = node.parent;
			delta--;
		}
		return node;
	}

	static int depth(TreeNode node) {
		int depth = 0;
		while (node != null) {
			node = node.parent;
			depth++;
		}
		return depth;
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

// 4.11 - Return Random Node - with equal probability for each node to be chosen.
class TreeRandom {
	TreeNodeRandom root = null;

	public int size() { return root == null ? 0 : root.size(); }

	public TreeNode getRandomNode() {
		if (root == null) return null;

		Random random = new Random();
		int i = random.nextInt(size());
	}

	public void insertInOrder(int value) {
		if (root == null) {
			root = new TreeNode(value);
		} else {
			root.insertInOrder(value);
		}
	}
}

class TreeNodeRandom {
	private int data;
	public TreeNodeRandom left;
	public TreeNodeRandom right;
	private int size = 0;

	public TreeNode(int d) {
		data = d;
		size = 1;
	}

	public TreeNode getIthNode(int i) {
		int leftSize = left == null ? 0 : left.size();
		if (i < leftSize) {
			return left.getIthNode(i);
		} else if (i == leftSize) {
			return this;
		} else {
			// Skipping over leftSize + 1 nodes so subtract them.
			return right.getIthNode(i - (leftSize + 1));
		}
	}

	public void insertInOrder(int d) {
		if (d <= data) {
			if (left == null) {
				left = new TreeNodeRandom(d);
			} else {
				left.insertInOrder(d);
			}
		} else {
			if (right == null) {
				right = new TreeNodeRandom(d);
			} else {
				right.insertInOrder(d);
			}
		}
		size++;
	}

	public int size() {
		return size;
	}

	public int data() {
		return data;
	}

	public TreeNode find(int d) {
		if (d == data) {
			return this;
		} else if (d <= data) {
			return left != null ? left.find(d) : null;
		} else if (d > data) {
			return right != null ? right.find(d) : null;
		}
		return null;
	}
}

class Result {
	public TreeNode node;
	public boolean isAncestor;
	public Result(TreeNode n, boolean isAncestor) {
		this.isAncestor = isAncestor;
		node = n;
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
