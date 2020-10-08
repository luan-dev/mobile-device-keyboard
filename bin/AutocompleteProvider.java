import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AutocompleteProvider {

	public static Node root;
	public static Scanner scan;
	
	public static void main(String[] args) {
		// create the base node
		root = new Node(' ', "");
		
		// creates input scanner and shows command line prompt
		System.out.println("---Mobile Device Keyboard---");
		scan = new Scanner(System.in);
		
		// this is the main gui of the system that handles the commands
		String input = "";
		while (!input.equalsIgnoreCase("!quit")) {
			// we want to remove any exccess materials and standardize the input
			System.out.println("Enter a command (type help for assistance)");
			System.out.print("> ");
			input = scan.nextLine().trim().toLowerCase();
			
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
					System.out.println("");
					break;
				case "quit":
					System.out.println("Quitting...");
					System.exit(0);
					break;
				default:
					System.out.println("Invalid command (type help for assistance)\n");
					break;
			}
		}
	}
	
	/**
	 * Enters the training mode
	 * You can quit by just entering a blank string
	 */
	public static void train(String passage) {
		// error check for if the passage is blank
		if (!passage.equals("")) {
			for (String word : passage.split(" ")) {
				// normalize the word by lower-casing it
				word = word.toLowerCase().trim();
				
				// remove any trailing special characters
				// added special cases for hyphen and apostrophes
				// (e.g. brother-in-law, they're)
				// NOTE: it won't capture trailing dashes,
				// 		 but this is rare enough in text to be passable
				// (e.g. they-)
				word = word.replaceAll("-*[^a-z-\\']-*", "");
				
				// make sure it is not a blank line
				if (word.length() != 0) {
					root.insert(word, 0);
				}
			}
		}
	}
	
	/**
	 * Returns the list of candidates for the word
	 */
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
			for (Candidate result : results) {
				System.out.println(result.toString());
			}
		}
		
		return results;
	}
	
	/**
	 * Prompts the user and takes in input to send to the train() method
	 */
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
	 * Prompts the user to enter a guess and sends to getWords() method
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
	
	/**
	 * Allows you to load your own .txt file
	 * Includes a file displayer too
	 */
	public static void load() {
		System.out.println("Choose a text file to load into trainer (please include .txt)");
		System.out.println("(enter blank string to quit)");
		listFiles();
		
		String input = " ";
		while (!input.equals("")) {
			System.out.print("> ");
			input = scan.nextLine();
			
			// need to add a double check
			if (!input.contentEquals("")) {
				try {
					File file = new File(input);
					Scanner fileReader = new Scanner(file);
					
					while (fileReader.hasNextLine()) {
						train(fileReader.nextLine());
					}
					fileReader.close();
					
					System.out.println("Text from file trained");
					input = "";
				} catch (FileNotFoundException e) {
					System.out.println("Invalid file... Please choose again\n");
					listFiles();
				}
			}
		}
		
		System.out.println("Returning to main menu...\n");
	}
	
	/**
	 * Overloaded method that accepts the file name
	 */
	public static void load(String input) {
		try {
			File file = new File(input);
			Scanner fileReader = new Scanner(file);
			
			while (fileReader.hasNextLine()) {
				train(fileReader.nextLine());
			}
			fileReader.close();
			
			System.out.println("Text from file trained");
			input = "";
		} catch (FileNotFoundException e) {
			System.out.println("Invalid file... Please choose again");
			load();
		}
	}
	
	/**
	 * Displays the files that are available to be opened
	 */
	public static void listFiles() {
		System.out.println("Getting list of files...");
		
		File currDirectory = new File(System.getProperty("user.dir"));
		File[] files = null;
		files = currDirectory.listFiles();
		// show all files in directory of the jar file
		if (files.length == 0) {
			System.out.println("No valid files in this directory");
		} else {
			for (File file : files) {
				if (file.getName().endsWith(".txt")) {
					System.out.println(" - " + file.getName());
				}
			}
		}
	}
	
	/**
	 * Displays the help prompt
	 */
	public static void helpPrompt() {
		System.out.println("	Made by: Luan Tran");
		System.out.println("	- train		enters training mode to enter strings");
		System.out.println("	- guess		enters the guessing mode to auto complete");
		System.out.println("	- load		enters the loading mode that lets you load any .txt file"
				+ "(make sure it is in the same directory)\n");
		
		System.out.println("	- test		loads the test file for quick testing");
		System.out.println("	- quit		quits program\n");
	}
}