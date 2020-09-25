package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Node {

	private char key;
	private HashMap<Character, Node> children;
	private int confidence;
	private String word;
	private boolean isWord;

	public Node(char key, String word) {
		this.key = key;
		this.children = new HashMap<Character, Node>();
		this.confidence = 0;
		this.word = word;
		this.isWord = false;
	}

	public void insert(String word, int index) {
		char firstLetter = word.charAt(index);

		// if letter is new, make a new Node for it
		if (!children.containsKey(firstLetter)) {
			children.put(firstLetter, new Node(firstLetter, word.substring(0, index + 1)));
		}

		if (word.length() == index + 1) {
			// if it is the last letter, increase the confidence
			Node child = children.get(firstLetter);
			child.increaseConfidence();
		} else {
			// else, continue insert
			children.get(firstLetter).insert(word, index + 1);
		}
	}

	public List<Candidate> children(String word) {
		Node prefixNode = getPrefixNode(word);

		// edge case on if there is no match
		if (prefixNode == null) {
			return null;
		}

		// make a list of candidate and return them
		ArrayList<Candidate> result = new ArrayList<Candidate>();
		getChildren(prefixNode, result);

		return result;
	}

	public void getChildren(Node root, List<Candidate> results) {
		// if it is a word, return it
		if (root.isWord) {
			results.add(new Candidate(root.word, root.confidence));
		}

		// return all instances of children
		for (Node child : root.children.values()) {
			getChildren(child, results);
		}
	}

	public Node getPrefixNode(String word) {
		char firstLetter = word.charAt(0);
		
		// if it does not exist, return null
		if (!children.containsKey(firstLetter)) {
			return null;
		}
		// it is the prefix, return this node
		if (word.length() == 1) {
			return children.get(word.charAt(0));
		}

		// travel the trie until the end
		while (children.get(firstLetter) != null) {
			return children.get(firstLetter).getPrefixNode(word.substring(1));
		}

		// return null if there is no match
		return null;
	}

	public int getConfidence() {
		return confidence;
	}

	// increase confidence
	public void increaseConfidence() {
		if (!isWord) {
			isWord = true;
		}

		confidence++;
	}

	public String getWord(String word) {
		return word;
	}

	public boolean isWord() {
		return isWord;
	}
}