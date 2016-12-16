package stacks;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;

public class Stacks {

	// Last In First Out ordering.
	// In certain problems a stack can more favorable than an array.
	// Stacks are useful in recursive algorithms.  Sometimes you need
	// to add data as you recurse, but pop as you backtrack.
	public static void main(String[] args) {
		// Page 228
		System.out.println("\nCracking Code Interview: 3.1");
		/*
		 *  An array could be used to create three stacks by
		 *  separating it into three different lengths.
		 *  Ex. Say it was 9.  You could have three stacks of
		 *  3.  Where you always use the element closest to the
		 *  beginning of that section.
		 *  Ex. [ 0, 0, 0 ].  Insert 3 --> [ 0, 0, 3 ].
		 *  Insert 2 --> [ 0, 2, 3 ] --> Pop() --> [ 0, 0, 3 ]
		 */
		
		System.out.println("\nCracking Code Interview: 3.5");
		Stack<Integer> s = new Stack<Integer>();
		s.push(12);
		s.push(3);
		s.push(5);
		s.push(11);
		s.push(4);
		s.push(1);
		
		System.out.println(s);
		sort(s);
		System.out.println(s);
	}
	
	// O(n^2) time and O(n) space
	static void sort(Stack<Integer> s) {
		Stack<Integer> r = new Stack<Integer>();
		while (!s.isEmpty()) {
			int tmp = s.pop();
			while (!r.isEmpty() && r.peek() > tmp) {
				s.push(r.pop());
			}
			r.push(tmp);
		}
		
		while (!r.isEmpty()) {
			s.push(r.pop());
		}
	}
	
}

// 3.4
class MyQueue<T> {
	Stack<T> stackNewest, stackOldest;
	
	public MyQueue() {
		stackNewest = new Stack<T>();
		stackOldest = new Stack<T>();
	}
	
	public int size() {
		return stackNewest.size() + stackOldest.size();
	}
	
	public void add(T value) {
		// Push onto stackNewest, which always has the
		// newest elements on top.
		stackNewest.push(value);
	}
	
	private void shiftStacks() {
		if (stackOldest.isEmpty()) {
			while (!stackNewest.isEmpty()) {
				stackOldest.push(stackNewest.pop());
			}
		}
	}
	
	public T peek() {
		shiftStacks();
		return stackOldest.peek();
	}
	
	public T remove() {
		shiftStacks();
		return stackOldest.pop();
	}
}

// 3.3
class SetOfStacks {
	ArrayList<SetStack> stacks = new ArrayList<SetStack>();
	public int capacity;
	public SetOfStacks(int capacity) {
		this.capacity = capacity;
	}
	
	public void push(int v) {
		SetStack last = getLastStack();
		if (last != null && !last.isFull()) {
			last.push(v);
		} else {
			SetStack stack = new SetStack(capacity);
			stack.push(v);
			stacks.add(stack);
		}
	}
	
	public int pop() {
		SetStack last = getLastStack();
		if (last == null) throw new EmptyStackException();
		int v = last.pop();
		if (last.size == 0) {
			stacks.remove(stacks.size() - 1);
		}
		return v;
	}
	
	public SetStack getLastStack() {
		if (stacks.size() == 0) return null;
		return stacks.get(stacks.size() - 1);
	}
	
	public boolean isEmpty() {
		SetStack last = getLastStack();
		return last == null || last.isEmpty();
	}
	
	public int popAt(int index) {
		return leftShift(index, true);
	}
	
	public int leftShift(int index, boolean removeTop) {
		SetStack stack = stacks.get(index);
		int removed_item;
		if (removeTop) removed_item = stack.pop();
		else removed_item = stack.removeBottom();
		if (stack.isEmpty()) {
			stacks.remove(index);
		} else if (stack.size > index + 1) {
			int v = leftShift(index + 1, false);
			stack.push(v);
		}
		return removed_item;
	}
}

class SetStack {
	public int capacity;
	public SetNode top, bottom;
	public int size = 0;
	
	public SetStack(int capacity) {
		this.capacity = capacity;
	}
	
	public boolean isFull() {
		return this.size == this.capacity;
	}
	
	public void join(SetNode above, SetNode below) {
		if (below != null) below.above = above;
		if (above != null) above.below = below;
	}
	
	public boolean push(int v) {
		if (size >= capacity) return false;
		size++;
		SetNode n = new SetNode(v);
		if (size == 1) bottom = n;
		join(n, top);
		top = n;
		return true;
	}
	
	public int pop() {
		SetNode t = top;
		top = top.below;
		size--;
		return t.data;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	public int removeBottom() {
		SetNode b = bottom;
		bottom = bottom.above;
		if (bottom != null) bottom.below = null;
		size--;
		return b.data;
	}
	
}

class SetNode {
	public SetNode above, below;
	public int data;
	public SetNode(int d) {
		data = d;
	}
}

// 3.2
class StackWithMin extends Stack<Integer> {
	Stack<Integer> mins = new Stack<Integer>();
	public StackWithMin() {
		mins = new Stack<Integer>();
	}
	
	public void push(int data) {
		if (data <= mins.peek()) {
			mins.push(data);
		}
		
		super.push(data);
	}
	
	public Integer pop() {
		int value = super.pop();
		if (value == min()) {
			mins.pop();
		}
		return value;
	}
	
	public Integer min() {
		if (mins.isEmpty()) {
			return Integer.MAX_VALUE;
		} else {
			return mins.peek();
		}
	}
}

// 3.1 - Fixed multi stack

class FixedMultiStack {
	private int numberOfStacks = 3;
	private int stackCapacity;
	private int[] values;
	private int[] sizes;
	
	public FixedMultiStack(int stackSize) {
		stackCapacity = stackSize;
		values = new int[stackSize * numberOfStacks];
		sizes = new int[numberOfStacks];
	}
	
	// Push value onto stack (assuming ints)
	public void push(int stackNum, int value) throws Exception {
		if (isFull(stackNum)) {
			throw new Exception();
		}
		
		sizes[stackNum]++;
		values[indexOfTop(stackNum)] = value;
	}
	
	public int pop(int stackNum) throws EmptyStackException {
		if (isEmpty(stackNum)) {
			throw new EmptyStackException();
		}
		
		int offset = indexOfTop(stackNum);
		int data = values[offset];
		values[offset] = 0;
		sizes[stackNum]--;
		return data;
	}
	
	public boolean isEmpty(int stackNum) {
		return sizes[stackNum] == 0;
	}
	
	private boolean isFull(int stackNum) {
		return sizes[stackNum] == stackCapacity;
	}
	
	private int indexOfTop(int stackNum) {
		int offset = stackNum * stackCapacity;
		int size = sizes[stackNum];
		return offset + size - 1;
	}
}

// 3.1 - Advanced

class MultiStack {
	
	private class StackInfo {
		public int start, size, capacity;
		
		public StackInfo(int start, int capacity) {
			this. start = start;
			this.capacity = capacity; 
		}
		
		public boolean isWithinStackCapacity(int index) {
			if (index < 0 || index >= values.length) {
				return false;
			}
			
			int contiguousIndex = index < start ? index + values.length : index;
			int end = start + capacity;
			return start <= contiguousIndex && contiguousIndex < end;
		}
		
		public int lastCapacitylndex() {
			return adjustIndex(start + capacity - 1);
		}
		
		public int lastElementIndex() {
			return adjustIndex(start + size - 1);
		}
		
		public boolean isFull() {
			return size == capacity;
		}
		
		public boolean isEmpty() {
			return size == 0;
		} 
	}
	
	private StackInfo[] info;
	private int[] values;
	
	public MultiStack(int numberOfStacks, int defaultSize) {
		info = new StackInfo[numberOfStacks];
		for (int i = 0; i < numberOfStacks; i++) {
			info[i] = new StackInfo(defaultSize * i, defaultSize);
		}
		values = new int[numberOfStacks * defaultSize];
	}
	
	public void push(int stackNum, int value) throws Exception {
		if (allStacksAreFull()) {
			throw new Exception();
		}
		
		// If this stack is full, expand it.
		StackInfo stack = info[stackNum];
		if (stack.isFull()) {
			expand(stackNum);
		}
		
		stack.size++;
		values[stack.lastElementIndex()] = value;
	}
	
	public int pop(int stackNum) throws EmptyStackException {
		StackInfo stack = info[stackNum];
		if (stack.isEmpty()) {
			throw new EmptyStackException();
		}
		
		// Remove the last element.
		int value = values[stack.lastElementIndex()];
		values[stack.lastCapacitylndex()] = 0;
		stack.size--;
		return value;
	}
	
	public int peek(int stackNum) {
		StackInfo stack = info[stackNum];
		return values[stack.lastElementIndex()];
	}
	
	private void shift(int stackNum) {
		System.out.println("/// Shifting" + stackNum);
		StackInfo stack = info[stackNum];
		
		if (stack.size >= stack.capacity) {
			int nextStack = (stackNum + 1) % info.length;
			shift(nextStack);
			stack.capacity++;
		}
		
		// Shift all elements in stack over by one
		int index = stack.lastElementIndex();
		while (stack.isWithinStackCapacity(index)) {
			values[index] = values[previousIndex(index)];
			index = previousIndex(index);
		}
		
		values[stack.start] = 0;
		stack.start = nextIndex(stack.start);
		stack.capacity--;
	}
	
	private void expand(int stackNum) {
		shift((stackNum + 1) % info.length);
		info[stackNum].capacity++;
	}
	
	private int numberOfElements() {
		int size = 0;
		for (StackInfo stack : info) {
			size += stack.size;
		}
		return size;
	}
	
	public boolean allStacksAreFull() {
		return numberOfElements() == values.length;
	}
	
	private int adjustIndex(int index) {
		int max = values.length;
		return ((index % max) + max) % max;
	}
	
	private int nextIndex(int index) {
		return adjustIndex(index + 1);
	}
	
	private int previousIndex(int index) {
		return adjustIndex(index - 1);
	}
	
}

class MyStack<T> {
	
	private static class Node<T> {
		public T data;
		public Node<T> next;
		
		public Node(T data) {
			this.data = data;
		}
	}
	
	private Node<T> top;
	
	public T pop() {
		if (top == null) throw new EmptyStackException();
		T item = top.data;
		top = top.next;
		return item;
	}
	
	public void push(T data) {
		Node<T> node = new Node<T>(data);
		node.next = top;
		top = node;
	}
	
	public T peek() {
		if (top == null) throw new EmptyStackException();
		return top.data;
	}
	
	public boolean isEmpty() {
		return top == null;
	}
}