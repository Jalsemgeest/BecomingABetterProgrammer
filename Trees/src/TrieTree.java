import java.util.ArrayList;
import java.util.HashMap;

// A Trie Tree (Prefix Tree) is often used to hold the English language of possible words.
// Each node is a letter, where a child is a terminating node, stating that the path taken
// to reach the child is a valid word.
// https://en.wikipedia.org/wiki/Trie
public class TrieTree {

	public static void main(String[] args) {
		ArrayList<String> list = new ArrayList<String>();
		list.add("Test");
		list.add("Coolio");
		list.add("Fun");
		list.add("Jake");
		
		Trie test = new Trie(list);
		
		System.out.println(test.contains("Cool"));
		System.out.println(test.contains("Cooly"));
		System.out.println(test.contains("Jake", true));
	}
	
}

class Trie {
	// This is the root of this trie.
	private TrieNode root;
	
	/*
	 * Takes a list of strings as an argument, and constructs a trie that stores these strings.
	 */
	public Trie(ArrayList<String> list) {
		root = new TrieNode();
		for (String word : list) {
			root.addWord(word);
		}
	}
	
	/*
	 * Checks whether this trie contains a string with the prefix passed in as an argument.
	 */
	public boolean contains(String prefix, boolean exact) {
		TrieNode lastNode = root;
		int i = 0;
		// Looping through the word and seeing if the node exists.
		for (i = 0; i < prefix.length(); i++) {
			lastNode = lastNode.getChild(prefix.charAt(i));
			if (lastNode == null) {
				return false;
			}
		}
		// If we don't care about exact, return !false (true)
		// Otherwise, we do care so return !true || --> lastNode.terminates()
		return !exact || lastNode.terminates();
	}
	
	public boolean contains(String prefix) {
		return contains(prefix, false);
	}
	
	public TrieNode getRoot() {
		return root;
	}
}

class TrieNode {
	// Children of this node in the trie.
	private HashMap<Character, TrieNode> children;
	private boolean terminates = false;
	
	// The character stored in this node as data.
	private char character;
	
	/*
	 * Constructs an empty trie node and initializes the list of its children to an empty
	 * hash map.  Used only to construct the root node of the trie.
	 */
	public TrieNode() {
		children = new HashMap<Character, TrieNode>();
	}
	
	/*
	 * Constructs a trie node and stores this character as the node's value.
	 * Initializes the lsit of child nodes of this node to an empty hash map.
	 */
	public TrieNode(char character) {
		this();
		this.character = character;
	}
	
	// Returns the character data stores in this node.
	public char getChar() {
		return this.character;
	}
	
	// Add this word to the Trie and recursively create the child nodes.
	public void addWord(String word) {
		if (word == null || word.isEmpty()) {
			return;
		}
		
		char firstChar = word.charAt(0);
		
		// Check if we already have a child that uses that character.
		TrieNode child = getChild(firstChar);
		if (child == null) {
			child = new TrieNode(firstChar);
			children.put(firstChar, child);
		}
		
		if (word.length() > 1) {
			child.addWord(word.substring(1));
		} else {
			child.setTerminates(true);
		}
	}
	
	public TrieNode getChild(char c) {
		return children.get(c);
	}
	
	public boolean terminates() {
		return this.terminates;
	}
	
	public void setTerminates(boolean t) {
		this.terminates = t;
	}
	
	
}