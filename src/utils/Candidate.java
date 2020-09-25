package utils;

import java.util.Comparator;

public class Candidate implements Comparable<Candidate>{
	
	private String word;
	private int confidence;
	
	public Candidate(String word, int confidence) {
		this.word = word;
		this.confidence = confidence;
	}
	
	public String getWord() {
		return word;
	}
	
	public int getConfidence() {
		return confidence;
	}
	
	public String toString() {
		return "\"" + word + "\" (" + confidence + ")";
	}

	@Override
	public int compareTo(Candidate other) {
		int comp = other.confidence - confidence;
		
		return comp == 0 ? word.compareTo(other.word) : comp;
	}
}