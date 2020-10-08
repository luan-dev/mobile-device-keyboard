# Mobile Device Keyboard
Coding challenge for Asymmetrik by Luan Tran

I used a Trie structure as the data structure. The time complexity for training is `O(n*L)` where `n` is the number of words and `L` is the length of the longest word. The time complexity for lookups is only `O(L)`, where `L` is the length of the word.

The Trie remains intact as long as the program is running. You can train it, test it, train it, and test it as many times as you would like. I did not implement a save feature, but I can do that if needed.

## Installation
You will need to have Java 8 installed to run this app.

Make sure you also have a file called `test.txt` and any other training passages in the same directory as the class files. This is an easy way to train the code. You can input whatever you want in it. I preloaded the sample test case that was provided.

To make this easy to test, I have zipped up the required files into a `program.zip` file. You just need to unzip the folder and run `java AutocompleteProvider` (do not include the `.class`) to run the program. Testing can be done manually or by loading in `.txt` files. These files must be in the same directory as the `.class` files.

## Building
If you want to rebuild the files, I have added a `src.zip` file that contains all the `.java` code. You can modify the code and run `javac AutocompleteProvider.java` to create another program and then run `java AutocompleteProvider` to test it again.

## Usage
Run `java AutoCompleteProvider` to start the command line
You will be prompted with a command line interface. Here are the following commands:
```
- train		enters training mode to enter strings
- guess		attempts to guess your word based on trained strings
- load    allows you to train using files in the same directory as the jar

- test		loads the sample file from the project specs
- quit		quits program
```
You can access these commands if you type `help` at the main menu.

### Training
`train` will let you type in a string and it will build up the dictionary.

### Guessing
`guess` will return the list of strings that thinks is best for you. You can quit this by entering an empty string. I added a filter so that it doesn't show a bunch of clutter.  By default the value is 3 since most phones only show three suggestions. You can change the filter by changing the `THRESHOLD` value in the `Node.java` file.

### Loading
`load` will display all the files you can load. Put these in the same directory as the AutocompleteProvider.class file.

### Testing
`test` will load a file named test.txt. This has been provided, but it needs to be in the same directory as the other files.

### Quitting
`quit` will quit the program

## Testing
I have included the entire Great Gatsby text in the submission for you to easily test. You can load this by running `load` and then `gatsby.txt`. You can check the word count of the words to confirm that it is correct by using this site: https://www.verbalworkout.com/t/t1012a.htm.
