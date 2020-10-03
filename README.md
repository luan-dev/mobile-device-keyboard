# Mobile Device Keyboard
Coding challenge for Asymmetrik by Luan Tran

I used a Trie structure as the data structure. The time complexity for training is `O(n*L)` where `n` is the number of words and `L` is the length of the longest word. The time complexity for lookups is only `O(L)`, where `L` is the length of the word.

The Trie remains intact as long as the program is running. You can train it, test it, train it, and test it as many times as you would like. I did not implement a save feature, but I can do that if needed.

A faster implementation could be to modify it to a Patricia Trie (also known as a Radix Tree). This would save on space, but is harder to implement. I can implement that if needed though.

## Installation
You will need to have Java 8 installed to run this app.

Make sure you also have a file called `test.txt` in the same directory. This is an easy way to train the code. You can input whatever you want in it. I preloaded the sample test case that was provided.

## Usage
Run `java AutoCompleteProvider` to start the command line
You will be prompted with a command line interface. Here are the following commands:
```
- train		enters training mode to enter strings
- guess		attempts to guess your word based on trained strings
- test		loads the test file
- quit		quits program
```
You can access these commands if you type `help` at the main menu.

### Training
`train` will let you type in a string and it will build up the dictionary

### Guessing
`guess` will return the list of strings that thinks is best for you
