package linkedlist;

import java.util.HashSet;

public class LinkedList {
	
	public static void main(String[] args) {
		int[] arr = new int[]{1,2,2,3,4,5,6,7,8,9};
		LList a = createList(arr);
		
		
		LList removeDupes1 = createList(1000);
		LList removeDupes2 = createList(1000);
		
		System.out.println("Lists created.");
		
		System.out.println("\nCracking Code Interview: 2.1");
		long start1 = System.currentTimeMillis();
		removeDuplicates(removeDupes1.head);
		long end1 = System.currentTimeMillis();
		System.out.println("First Remove Duplicates took: " + (end1 - start1) + "ms");
		long start2 = System.currentTimeMillis();
		deleteDupes(removeDupes2.head);
		long end2 = System.currentTimeMillis();
		System.out.println("Second Remove Duplicates took: " + (end2 - start2) + "ms");
		
		System.out.println("\nCracking Code Interview: 2.2");
		LList kth = createList(5);
		kth.print();
		System.out.println(kthToLastRecursive(kth.head, 2).data);
		System.out.println(kthToLastInterval(kth.head, 4).data);
		
		System.out.println("\nCracking Code Interview: 2.3");
		LList deleteWithNoHead = createList(5);
		Node toDelete = deleteWithNoHead.head.next.next.next;
		deleteWithNoHead.print();
		deleteWithNoHead(toDelete);
		deleteWithNoHead.print();
		
		System.out.println("\nCracking Code Interview: 2.4");
		LList partitionList = createList(new int[]{3,5,8,5,10,2,1});
		partitionList.print();
		new LList(partition(partitionList.head, 5)).print();
		
		System.out.println("\nCracking Code Interview: 2.5");
		int[] arr1 = new int[]{7,1,6};
		int[] arr2 = new int[]{5,9,2};
		LList list1 = createList(arr1);
		LList list2 = createList(arr2);
		System.out.println(arrToString(arr1) + " + " + arrToString(arr2));
		new LList(sumLists(list1.head, list2.head)).print();
		
		// What if the values were put in forward instead of backwards?
		new LList(addLists(list1.head, list2.head)).print();
	}

	// Helper functions
	public static LList createList(int[] vals) {
		LList a = new LList(vals[0]);
		for (int i = 1; i < vals.length; i++) {
			a.head.insert(vals[i]);
		}
		return a;
	}
	
	public static LList createList(int vals) {
		int count = vals - 1;
		LList a = new LList((int)(Math.random() * 1000));
		for (int i = count; i >= 0; i--) {
			a.head.insert((int)(Math.random() * 1000));
		}
		return a;
	}

	public static String arrToString(int[] arr) {
		StringBuilder string = new StringBuilder();
		for (int i : arr) {
			string.append(i);
			string.append(", ");
		}
		// Remove the extra comma.
		string.setLength(string.length() - 2);
		return string.toString();
	}
	
	// O(n) time
	private static void removeDuplicates(Node h) {
		HashSet<Integer> set = new HashSet<Integer>();
		Node previous = null;
		while (h != null) {
			if (set.contains(h.data)) {
				previous.next = h.next;
			} else {
				set.add(h.data);
				previous = h;
			}
			h = h.next;
		}
	}

	// O(1) space, but O(n^2) time
	private static void deleteDupes(Node h) {
		Node current = h;
		while (current != null) {
			// Remove all future nodes that have the same value.
			Node runner = current;
			while (runner.next != null) {
				if (runner.next.data == current.data) {
					runner.next = runner.next.next;
				} else {
					runner = runner.next;
				}
			}
			current = current.next;
		}
	}
	
	// O(n) space
	private static Node kthToLastRecursive(Node h, int k) {
		Index index = new Index();
		return kthToLastRecursive(h, k, index);
	}
	
	private static Node kthToLastRecursive(Node h, int k, Index index) {
		if (h == null) {
			return null;
		}
		Node node = kthToLastRecursive(h.next, k, index);
		index.index += 1;
		if (index.index == k) {
			return h;
		}
		return node;
	}
	
	// O(n) time, O(1) space
	private static Node kthToLastInterval(Node h, int k) {
		int counter = 0;
		Node pointer = null;
		Node runner = h;
		
		while (runner.next != null) {
			counter++;
			if (counter == k - 1) {
				pointer = h;
			}
			else if (counter >= k) {
				pointer = pointer.next;
			}
			runner = runner.next;
		}
		
		return pointer;
	}
	
	// O(1) time - cannot delete final node in linked list
	private static boolean deleteWithNoHead(Node h) {
		if (h == null || h.next == null) return false;
		Node next = h.next;
		h.data = next.data;
		h.next = next.next;
		return true;
	}

	
	private static Node partition(Node h, int x) {
		Node head = h;
		Node tail = h;
		
		while (h != null) {
			Node next = h.next;
			// Insert at the beginning of the linked list.
			if (h.data < x) {
				h.next = head;
				head = h;
			} else { // Insert at the tail
				tail.next = h;
				tail = h;
			}
			h = next;
		}
		tail.next = null;
		
		return head;
	}

	private static Node sumLists(Node x, Node y) {
		return sumListsHelper(x, y, 0);
	}
	
	private static Node sumListsHelper(Node x, Node y, int carry) {
		if (x == null && y == null && carry == 0) {
			return null;
		}
		
		Node result;
		int value = carry;
		if (x != null) {
			value += x.data;
		}
		
		if (y != null) {
			value += y.data;
		}
		
		result = new Node(value % 10); // Get 1's number
		
		if (x != null || y != null) {
			Node more = sumListsHelper(x == null ? null : x.next,
									   y == null ? null : y.next,
								       value >= 10 ? 1 : 0);
			result.next = more;
		}
		
		return result;
	}
	
	private static Node addLists(Node x, Node y) {
		int lengthX = length(x);
		int lengthY = length(y);
		
		// Pad shorter list with zeroes
		if (lengthX < lengthY) {
			x = padList(x, lengthY - lengthX);
		} else {
			y = padList(y, lengthX - lengthY);
		}
		
		// Add lists
		PartialSum sum = addListHelper(x, y);
		
		
		// If there was a carry value left over
		// Insert this at the front of the list
		if (sum.carry == 0) {
			return sum.sum;
		} else {
			Node result = insertBefore(sum.sum, sum.carry);
			return result;
		}
	}
	
	private static PartialSum addListHelper(Node x, Node y) {
		if (x == null && y == null) {
			PartialSum sum = new PartialSum();
			return sum;
		}
		
		// Add smaller digits recursively.
		PartialSum sum = addListHelper(x.next, y.next);
		
		// Add carry to current data
		int val = sum.carry + x.data + y.data;
		
		// Insert sum of current digits
		Node full_reset = insertBefore(sum.sum, val % 10);
		
		// Return sum so far and carry value
		sum.carry = val >= 10 ? 1 : 0;
		sum.sum = full_reset;
		return sum;
	}
	
	private static Node padList(Node h, int length) {
		Node head = h;
		for (int i = 0; i < length; i++) {
			head = insertBefore(head, 0);
		}
		return head;
	}
	
	private static Node insertBefore(Node h, int val) {
		Node node = new Node(val);
		if (h != null) {
			node.next = h;
		}
		return node;
	}
	
	private static int length(Node h) {
		int counter = 1;
		while (h.next != null) {
			counter++;
			h = h.next;
		}
		return counter;
	}
}

// Helper class - Recursive solution 2.2
class Index {
	public int index = 0;
	public Index() {}
}

// Helper class - 2.5
class PartialSum {
	public int carry = 0;
	public Node sum = null;
}

class LList {
	public Node head;
	
	public LList() {}
	
	public LList(int v) {
		head = new Node(v);
	}
	
	public LList(Node h) {
		head = h;
	}
	
	public void setHead(Node h) {
		head = h;
	}
	
	public void print() {
		Node n = head;
		while (n.next != null) {
			System.out.print(n.data + ", ");
			n = n.next;
		}
		System.out.print(n.data);
		System.out.println();
	}
}

class Node {
	int data;
	Node next;
	
	public Node(int d) {
		data = d;
	}
	
	public void insert(int d) {
		Node end = new Node(d);
		Node n = this;
		while (n.next != null) {
			n = n.next;
		}
		n.next = end;
	}
}