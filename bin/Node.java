import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Node {

	// the different amount of confidences you want
	// by default it will show 3 different confidences
	// increasing the number will show more candidates,
	// but will also add more clutter
	int THRESHOLD = 3;

	private HashMap<Character, Node> children;
	private int confidence;
	private String word;
	private boolean isWord;

	public Node(char key, String word) {
		this.children = new HashMap<Character, Node>();
		this.confidence = 0;
		this.word = word;
		this.isWord = false;
	}

	/**
	 * Inserts the word into the trie
	 * 
	 * @param word  to be entered at the trie
	 * @param index used to allow recursive entry into the trie
	 */
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

	/**
	 * Returns a list of Candidates
	 * 
	 * @param word the word you want the children of
	 * @return a list of children for specified word
	 */
	public List<Candidate> children(String word) {
		Node prefixNode = getPrefixNode(word);

		// edge case on if there is no match
		if (prefixNode == null) {
			return null;
		}

		// we need to make an ArrayList with to pass to the getChildren
		ArrayList<Candidate> result = new ArrayList<Candidate>();
		getChildren(prefixNode, result);
		Collections.sort(result);

		return filter(THRESHOLD, result);
	}

	/**
	 * Filter the list of Candidates down
	 * 
	 * @param threshold the number of different candidates you want
	 * @param list the list you want to filter
	 * @return the filtered list with candidates
	 */
	public ArrayList<Candidate> filter(int threshold, ArrayList<Candidate> list) {
		// edge case
		if (list.isEmpty()) {
			return list;
		}
		
		// iterate through and filter out results
		ArrayList<Candidate> result = new ArrayList<Candidate>();
		int currConfidence = list.get(0).getConfidence();
		
		for (Candidate entry : list) {
			// adjust the current confidence as it changes
			if (entry.getConfidence() < currConfidence) {
				currConfidence = entry.getConfidence();
				threshold--;
			}
			
			// add the entry if the threshold allows
			if (threshold > 0) {
				result.add(entry);
			} else {
				return result;
			}
		}
		
		
		return result;
	}

	/**
	 * Gets all the children for the provided prefix and places it in the results
	 * 
	 * @param root    the root node that you want the children of
	 * @param results the recursively passed list
	 */
	public void getChildren(Node root, List<Candidate> results) {
		// if it is a word, return it
		if (root.isWord) {
			results.add(new Candidate(root.word, root.confidence));
		}

		// return all instances of children recursively
		for (Node child : root.children.values()) {
			getChildren(child, results);
		}
	}

	/**
	 * Returns the prefix node of the word
	 * 
	 * @param word that you want the prefix node of
	 * @return node that is the prefix for the word
	 */
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

	/**
	 * @return confidence level (the amount of times the word was used)
	 */
	public int getConfidence() {
		return confidence;
	}

	/**
	 * increases the confidence
	 */
	public void increaseConfidence() {
		if (!isWord) {
			isWord = true;
		}

		confidence++;
	}

	/**
	 * @return if the word is a word or not
	 */
	public boolean isWord() {
		return isWord;
	}
}