package linkedlist;

import java.util.HashSet;
import java.util.Stack;

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
	
		System.out.println("\nCracking Code Interview: 2.6");
		LList palindrome = createList(new int[]{1,2,3,2,1});
		System.out.println(isPalindrome(palindrome.head));
		System.out.println(isPalindromeIterative(palindrome.head));
		System.out.println(isPalindromeRecursive(palindrome.head));
		
		System.out.println("\nCracking Code Interview: 2.7");
		LList intersect1 = createList(5);
		LList intersect2 = createList(1); // 0 indexed, length 2.
		intersect2.head.next.next = intersect1.head.next.next.next;
		intersect1.print();
		intersect2.print();
		System.out.println(intersects(intersect1.head, intersect2.head).data);
		
		
		System.out.println("\nCracking Code Interview: 2.8");
		LList loop = createList(5);
		loop.print();
		System.out.println("Looping at: " + loop.head.next.next.data);
		getEnd(loop.head).next = loop.head.next.next;
		System.out.println(collision(loop.head).data);
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

	// Reverse approach
	private static boolean isPalindrome(Node h) {
		Node reversed = reverseAndClone(h);
		return isEqual(h, reversed);
	}
	
	private static boolean isPalindromeIterative(Node h) {
		Node fast = h;
		Node slow = h;
		
		Stack<Integer> stack = new Stack<Integer>();
		
		// Push elements from first half of linked list onto stack.
		// When fast runner (2x speed) reaches end of linked list,
		// we know we're in the middle.
		while (fast != null && fast.next != null) {
			stack.push(slow.data);
			slow = slow.next;
			fast = fast.next.next;
		}
		
		// Has odd number of elements, so skip the middle elements
		if (fast != null) {
			slow = slow.next;
		}
		
		while (slow != null) {
			int top = stack.pop().intValue();
			
			// If values are different, not a palindrome.
			if (top != slow.data) {
				return false;
			}
			slow = slow.next;
		}
		
		return true;
	}
	
	private static Node reverseAndClone(Node h) {
		Node head = null;
		while (h != null) {
			Node n = new Node(h.data);
			n.next = head;
			head = n;
			h = h.next;
		}
		return head;
	}
	
	private static boolean isEqual(Node x, Node y) {
		while (x != null && y != null) {
			if (x.data != y.data) {
				return false;
			}
			x = x.next;
			y = y.next;
		}
		return x == null && y == null;
	}

	private static boolean isPalindromeRecursive(Node h) {
		int length = length(h);
		Result p = isPalindromeRecursiveHelper(h, length);
		return p.result;
	}
	
	private static Result isPalindromeRecursiveHelper(Node head, int length) {
		// If we have reached the end, return true.
		if (head == null || length <= 0) {
			return new Result(head, true);
		} else if (length == 1) { // Odd number of nodes
			return new Result(head.next, true);
		}
		
		// Recurse on sublist, moving pointer along.
		// We are trying to get the head pointer to be the middle of the linked list.
		// This will be returned to us in the Result object.
		Result res = isPalindromeRecursiveHelper(head.next, length - 2);
		
		// If child calls are not a palindrome, pass back up a failure.
		if (!res.result || res.node == null) {
			return res;
		}
		
		// Check if matches corresponding node on either side.
		res.result = (head.data == res.node.data);
		
		// Return corresponding node.
		// Move the node to pointer at the next node to
		// correspond with the unwrapping recursion.
		res.node = res.node.next;
		
		return res;
	}

	
	// How do we determine if two linked lists intersect?
	/*
	 * Note: Singly Linked List.
	 * 
	 * We could hash map each node by memory address and see the first that matches.
	 * Although, each linked list has the same last node.
	 * So we could go to the end of the list and work backwards and compare last nodes.
	 * 
	 * So:
	 * 1. Run through each linked list to get the lengths and the tails.
	 * 2. Compare the tails.  If they are different (by reference or value), return false.
	 * 3. Set two pointers to the start of each linked list.
	 * 4. On the longer list, advance its pointer by the difference in lengths.
	 * 5. Now, traverse on each linked list until the pointers are the same.
	 */
	// O(A + B) where A and B are lengths of the two lists.
	private static Node intersects(Node x, Node y) {
		if (x == null || y == null) return null;
		
		IntersectResult rx = getTailAndSize(x);
		IntersectResult ry = getTailAndSize(y);
		
		int difference = Math.abs(rx.size - ry.size);
		
		Node shorter = rx.size < ry.size ? x : y;
		Node longer = rx.size < ry.size ? y : x;
		
		longer = getKthNode(longer, difference);
		
		while (shorter != longer) {
			shorter = shorter.next;
			longer = longer.next;
		}
		
		return longer;
	}
	
	private static Node getKthNode(Node h, int k) {
		Node current = h;
		while (k > 0 && current != null) {
			k--;
			current = current.next;
		}
		return current;
	}
	
	private static IntersectResult getTailAndSize(Node h) {
		if (h == null) return null;
		int counter = 0;
		while (h.next != null) {
			counter++;
			h = h.next;
		}
		return new IntersectResult(h, counter);
	}
		
	private static Node getEnd(Node h) {
		if (h == null) return h;
		while (h.next != null) {
			h = h.next;
		}
		return h;
	}

	// Determine if a linked list has a loop.
	/*
	 * Easy way is through the fast runner / slow runner approach.
	 * FastRunner moves two steps at a time, while SlowRunner moves on step at a time.
	 * When do they collides?
	 * We know for everyone p steps taken by SlowRunner, 2p steps have been taken by FastRunner.
	 * When SlowRunner enters the loop, FastRunner would be 2k steps total and must be
	 * 2k - k steps, or k steps, into the loop portion.  Since k might be much larger than the
	 * loop length, we should actually write mod(k, LOOP_SIZE), which we can denote at k.
	 * So we know:
	 * 1. SlowRunner is 0 steps into the loop.
	 * 2. FastRunner is k steps into the loop.
	 * 3. SlowRunner is k steps behind FastRunner.
	 * 4. FastRunner is LOOP_SIZE - k steps behind SlowRunner.
	 * 5. FastRunner catches up to SlowRunner at a rate of 1 step per unit of time.
	 * They will meet after LOOP_SIZE - k steps.
	 * 
	 * How do we find the start of the loop?
	 * We know the collision spot is k before start of loop.
	 * Since K = %(k, LOOP_SIZE) from the loop start.
	 * Ex. If node N is 2 nodes into a 5 node loop.  It is safe to say it's also 7, 12 in as well.
	 * 
	 * Therefore, both the collision spot and the linked list head are k nodes from the start of the loop.
	 * 
	 * Putting it all together.
	 * Once SlowPointer enters the loop, SlowPointer and FastPointer are LOOP_SIZE - k nodes away from each other.
	 * They will meet after LOOP_SIZE - k turns.  Both will be k nodes from the front of the loop.
	 * So... The head of the linked list is also k nodes from the front of the loop.  So if we keep one pointer where it is,
	 * and move the other pointer to the head of the linked list, then they will meet at the front of the loop.
	 * 
	 * Steps:
	 * 1. Create two pointers, FastPointer and SlowPointer.
	 * 2. Move FastPointer at a rate of 2 steps and SlowPointer at a rate of 1 step.
	 * 3. When they collide, move SlowPointer to Head.  Keep FastPointer where it is.
	 * 4. Move SlowPointer and FastPointer at a rate of one step.  Return collision point.
	 */
	private static Node collision(Node h) {
		if (h == null) return null;
		
		Node slow = h;
		Node fast = h;
		
		// While there are nodes to keep going and slow does not equal fast
		while (fast != null && fast.next != null) {
			slow = slow.next;
			fast = fast.next.next;
			if (slow == fast) {
				break;
			}
		}
		
		if (fast == null || fast.next == null) {
			return null;
		}
		
		slow = h;
		
		while (slow != fast) {
			slow = slow.next;
			fast = fast.next;
		}
		
		return fast;
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

// Helper class - 2.6
class Result {
	public Node node;
	public boolean result;
	
	public Result(Node n, boolean r) {
		node = n;
		result = r;
	}
}

// Helper class - 2.7
class IntersectResult {
	public int size;
	public Node node;
	
	public IntersectResult(Node n, int s) {
		node = n;
		size = s;
	}
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