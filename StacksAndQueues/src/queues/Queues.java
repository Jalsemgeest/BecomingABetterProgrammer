package queues;

import java.util.LinkedList;
import java.util.NoSuchElementException;

import stacks.Dog;

public class Queues {

	// Queues are FIFO (first in first out)
	// Often used in BFS or using a cache.
	public static void main(String[] args) {
		
	}

}

//3.6
abstract class Animal {
	private int order;
	protected String name;
	public Animal(String n) { name = n; }
	public void setOrder(int order) { this.order = order; }
	public int getOrder() { return order; }
	
	public boolean isOlderThan(Animal a) {
		return this.order < a.getOrder();
	}
}

class AnimalQueue {
	LinkedList<Dog> dogs = new LinkedList<Dog>();
	LinkedList<Cat> cats = new LinkedList<Cat>();
	private int order = 0;
	
	public void enqueue(Animal a) {
		a.setOrder(order);
		order++;
		if (a instanceof Dog) dogs.addLast((Dog) a);
		else if (a instanceof Cat) cats.addLast((Cat) a);
	}
	
	public Animal dequeueAny() {
		if (dogs.size() == 0) {
			return dequeueCat();
		} else if (cats.size() == 0) {
			return dequeueDog();
		}
		Dog dog = dogs.peek();
		Cat cat = cats.peek();
		if (dog.isOlderThan(cat)) {
			return dequeueDog();
		} else {
			return dequeueCat();
		}
	}
	
	public Cat dequeueCat() {
		return cats.poll();
	}
	
	public Dog dequeueDog() {
		return dogs.poll();
	}
}

class Dog extends Animal {
	public Dog(String n) { super(n); }
}

class Cat extends Animal {
	public Cat(String n) { super(n); }
}

class MyQueue<T> {
	
	private static class Node<T> {
		private T data;
		private Node<T> next;
		
		public Node(T data) {
			this.data = data;
		}
	}
	
	private Node<T> first;
	private Node<T> last;
	
	public void add(T data) {
		Node<T> t = new Node<T>(data);
		if (last != null) {
			last.next = t;
		}
		last = t;
		if (first == null) {
			first = last;
		}
	}
	
	public T remove() {
		if (first == null) throw new NoSuchElementException();
		T data = first.data;
		first = first.next;
		if (first == null) {
			last = null;
		}
		return data;
	}
	
	public T peek() {
		if (first == null) throw new NoSuchElementException();
		return first.data;
	}
	
	public boolean isEmpty() {
		return first == null;
	}
}
