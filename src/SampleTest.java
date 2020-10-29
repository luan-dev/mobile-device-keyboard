package test.com.luan.mdk;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.luan.mdk.Candidate;
import com.luan.mdk.Node;

public class SampleTest {
	
	Node root;
	String error = "Failure: Expected %s, but got %s";
	String sample = "The third thing that I need to tell you is that this thing does not think thoroughly.";
	
	@Before
	public void setUp() {
		root = new Node(' ', "");
		
		// have to do the initial word processing
		for (String word : sample.split(" ")) {
			word = word.toLowerCase().trim();
			word = word.replaceAll("-*[^a-z-\\']-*", "");
			
			root.insert(word, 0);
		}
	}
	
	
	@Test
	public void sanity() {
		assertTrue(true);
	}
	
	@Test
	/**
	 * Using the sample we will test "thi"
	 */
	public void testThi() {
		// create the testing variables
		List<Candidate> results = root.children("thi");
		
		List<Candidate> expected = new ArrayList<>();
		expected.add(new Candidate("thing", 2));
		expected.add(new Candidate("think", 1));
		expected.add(new Candidate("third", 1));
		expected.add(new Candidate("this", 1));
	
		// loop through and compare them
		assertEquals(4, results.size());
		for (int i = 0; i < expected.size(); i++) {
			Candidate a = results.get(i);
			Candidate b = expected.get(i);
			assertTrue(String.format(error, a, b), a.compareTo(b) == 0);
		}
	}
	
	@Test
	/**
	 * Using the sample we will test "nee"
	 */
	public void testNee() {
		// create the testing variables
		List<Candidate> results = root.children("nee");
		
		List<Candidate> expected = new ArrayList<>();
		expected.add(new Candidate("need", 1));
		
		// compare them
		assertEquals(1, results.size());
		Candidate a = results.get(0);
		Candidate b = expected.get(0);
		assertTrue(String.format(error, a, b), a.compareTo(b) == 0);
	}
	
	@Test
	/**
	 * Using the sample we will test "th"
	 */
	public void testTh() {
		// create the testing variables
		List<Candidate> results = root.children("th");
		
		List<Candidate> expected = new ArrayList<>();
		expected.add(new Candidate("that", 2));
		expected.add(new Candidate("thing", 2));
		expected.add(new Candidate("the", 1));
		expected.add(new Candidate("think", 1));
		expected.add(new Candidate("third", 1));
		expected.add(new Candidate("this", 1));
		expected.add(new Candidate("thoroughly", 1));
	
		// loop through and compare them
		assertEquals(7, results.size());
		for (int i = 0; i < expected.size(); i++) {
			Candidate a = results.get(i);
			Candidate b = expected.get(i);
			assertTrue(String.format(error, a, b), a.compareTo(b) == 0);
		}
	}
}
