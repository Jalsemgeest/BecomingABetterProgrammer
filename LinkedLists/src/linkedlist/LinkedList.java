package linkedlist;

import java.util.HashSet;

public class LinkedList {
	
	public static void main(String[] args) {
		int[] arr = new int[]{1,2,2,3,4,5,6,7,8,9};
		LList a = createList(arr);
		
		
		LList removeDupes1 = createList(100000);
		LList removeDupes2 = createList(100000);
		
		System.out.println("Lists created.");
		
		long start1 = System.currentTimeMillis();
		removeDuplicates(removeDupes1.head);
		long end1 = System.currentTimeMillis();
		System.out.println("First Remove Duplicates took: " + (end1 - start1) + "ms");
		long start2 = System.currentTimeMillis();
		deleteDupes(removeDupes2.head);
		long end2 = System.currentTimeMillis();
		System.out.println("Second Remove Duplicates took: " + (end2 - start2) + "ms");
	}

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
	
}

class LList {
	public Node head;
	
	public LList() {}
	
	public LList(int v) {
		head = new Node(v);
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