import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import utils.Candidate;
import utils.Node;

public class AutocompleteProvider {

	public static Node root;
	public static Scanner scan;
	
	public static void main(String[] args) {
		// create the base node
		root = new Node(' ', "");
		
		// creates input scanner and shows command line prompt
		System.out.println("---Mobile Device Keyboard---");
		System.out.println("Enter a command (type help for assistance)");
		scan = new Scanner(System.in);
		String input = "";
		while (!input.equalsIgnoreCase("!quit")) {
			System.out.print("> ");
			input = scan.nextLine().trim();
			
			switch(input) {
				case "train":
					trainPrompt();
					break;
				case "guess":
					guessPrompt();
					break;
				case "help":
					helpPrompt();
					break;
				case "load":
					load();
					break;
				case "test":
					load("test.txt");
				case "!quit":
					break;
				default:
					System.out.println("Invalid command (type help for assistance)");
					break;
			}
		}
	}
	
	public static void train(String passage) {
		// error check for if the passage is blank
		if (!passage.equals("")) {
			for (String word : passage.split(" ")) {
				// normalize the word by lower-casing it
				word = word.toLowerCase();
				
				// remove any trailing special characters
				// added special cases for hyphen and apostrophes
				// (e.g. brother-in-law, they're)
				// NOTE: it won't capture trailing dashes,
				// 		 but this is rare enough in text to be passable
				// (e.g. they-)
				word = word.replaceAll("-*[^a-z-\\']-*", "");
				
				root.insert(word, 0);
			}
		}
	}
	
	public static List<Candidate> getWords(String fragment) {
		// error check for if the fragment is blank
		if (fragment.equals("")) {
			return null;
		}
		
		List<Candidate> results = new ArrayList<Candidate>();
		results = root.children(fragment);
		
		if (results == null) {
			System.out.println("No matches found");
		} else {
			Collections.sort(results);
			for (Candidate result : results) {
				System.out.println(result.toString());
			}
		}
		
		return results;
	}
	
	public static void trainPrompt() {
		System.out.println("Starting trainer...");
		System.out.println("Type any string to train the program (enter blank string to quit)\n");
		
		// continues until user enters blank string
		String input = " ";
		while (!input.equals("")) {
			System.out.print("> ");
			input = scan.nextLine();
			train(input);
		}
		
		System.out.println("Quitting trainer...\n");
	}
	
	/**
	 * Prompts the user to enter a guess 
	 */
	public static void guessPrompt() {
		System.out.println("Starting autocomplete...");
		System.out.println("Type any string fragment for guesses (enter blank string to quit)\n");
		
		// continues until user enters blank string
		String input = " ";
		while (!input.equals("")) {
			System.out.print("> ");
			input = scan.nextLine();
			getWords(input);
		}
		
		System.out.println("Quitting autocomplete...\n");
	}
	
	public static void load() {
		System.out.println("Choose a text file to load into trainer (please include .txt)");
		listFiles();
		
		String input = " ";
		while (!input.equals("")) {
			System.out.print("> ");
			input = scan.nextLine();
			
			try {
				File file = new File(input);
				Scanner fileReader = new Scanner(file);
				
				while (fileReader.hasNextLine()) {
					train(fileReader.nextLine());
				}
				
				System.out.println("Text from file trained");
				input = "";
			} catch (FileNotFoundException e) {
				System.out.println("Invalid file... Please choose again");
				listFiles();
			}
		}
	}
	
	public static void load(String input) {
		try {
			File file = new File(input);
			Scanner fileReader = new Scanner(file);
			
			while (fileReader.hasNextLine()) {
				train(fileReader.nextLine());
			}
			
			System.out.println("Text from file trained");
			input = "";
		} catch (FileNotFoundException e) {
			System.out.println("Invalid file... Please choose again");
			load();
		}
	}
	
	public static void listFiles() {
		System.out.println("Getting list of files...");
		
		File currDirectory = new File(System.getProperty("user.dir"));
		File[] files = null;
		files = currDirectory.listFiles();
		// show all files in directory of the jar file
		for (File file : files) {
			if (file.getName().endsWith(".txt")) {
				System.out.println(" - " + file.getName());
			}
		}
	}
	
	public static void helpPrompt() {
		System.out.println("	Made by: Luan Tran");
		System.out.println("	- train		enters training mode to enter strings");
		System.out.println("	- guess		attempts to guess your word based on trained strings\n");
		System.out.println("	- !quit		quits program\n");
	}
}