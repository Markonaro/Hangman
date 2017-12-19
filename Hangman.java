/*
 * File: Hangman.java
 * ------------------
 * This program will eventually play the Hangman game from
 * Assignment #4.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;
import acmx.export.java.io.FileReader;

import java.awt.*;
import java.io.*;


public class Hangman extends ConsoleProgram {
	/** Number of guesses */
	private static int guessesLeft = 8;
	
	/** The word to guess */
	private HangmanLexicon word;
	private static String myWord;
	
	/** Displayed String */
	private static String display = "";
	
	/** Length of entered word */
	private static int wordLength;
	
	/** Guessed letters */
	private static String guesses = "";
	
	private static int lettersLeft = 1;
	
	/** Hangman Canvas */
	private HangmanCanvas canvas;
	
	/** Random Integer */
	private static int randInt;
	
	/*private BufferedReader openFile(){
		BufferedReader rd = null;
		
		while(rd == null){
			try {
				rd = new BufferedReader(new FileReader("HangmanLexicon.java"));
			} catch (IOException ex) {
				println("File does not exist. Try new file name");
			}
		}
		return rd;
	}*/
	
	public void init() {
		/*BufferedReader rd = openFile();
		
		try {
			while (true){
				String line = rd.readLine();
				if (line == null){
					break;
				}
			}
			rd.close();
		} catch (IOException ex) {
			throw new ErrorException(ex);
		}
		*/
		
		word = new HangmanLexicon();
		randInt = rgen.nextInt(0, word.getWordCount()-1);
		myWord = word.getWord(randInt);
    	wordLength = myWord.length();
    	lettersLeft = wordLength;
    	
    	canvas = new HangmanCanvas();
		add(canvas);
    	
    	println("Number of guesses: "+ guessesLeft);
    	canvas.displayWord(display, guesses);
		/* Displaying the length with blanks for each letter */
    	String initDisplay = "";
    	for (int i = 0; i < wordLength; i++){
    		initDisplay += "-";
    	}
    	display = initDisplay;
    	println(display);

		canvas.reset(display);
		canvas.noteIncorrectGuess(guessesLeft);
	}
	
    public void run() {
		while(guessesLeft > 0 && lettersLeft > 0){
			check(guess(), display);
		}
	}
    
    private void check(char theGuess, String toDisplay){

		boolean alreadyGuessed = false; // Assume current letter hasn't been guessed
		boolean letterInSecretWord = false; // Assumes current letter isn't in the secret word
		char[] theDisplay = toDisplay.toCharArray(); // Creates an array of the displayed word
		
		
		/* Checks to see if the letter was already guessed */
		for (int j = 0; j < guesses.length(); j++){
			if (theGuess == guesses.charAt(j)){ // if current letter is already in guessed list
				alreadyGuessed = true;
			}
		}
		
		guesses += theGuess; // Mark it down
		
		/* If the current letter wasn't already guessed */
		if (alreadyGuessed == false){
			/* Check to see if it's in the secret word */
			for (int i = 0; i < wordLength; i++){
	    		if (theGuess == myWord.charAt(i)){
	    			theDisplay[i] = theGuess;
	    			letterInSecretWord = true;
	    			lettersLeft--;
	    		}
	    	}
		}
		
		display = new String(theDisplay);

		/* If the letter isn't in the secret word */
		if (letterInSecretWord == false) {
			guessesLeft--;
    		canvas.noteIncorrectGuess(guessesLeft);
    	} 
		
		/* End displays (win or lose) */
		if(guessesLeft == 0){
			println("You're a dead man");
			canvas.displayWord(display, guesses);
		} else if (lettersLeft == 0) {
			println("Congrats you've won!");
			println("Secret word: " + myWord);
    		canvas.displayWord(display, guesses);
		}
		
		/* Whenever the game is still going */
		if (lettersLeft != 0 && guessesLeft != 0){
			canvas.displayWord(display, guesses);
	    	println("Result: " + display);
	    	println("Guesses left: " + guessesLeft);
		}
    }
   
    private char guess(){
    	String myGuess = readLine("Guess a letter: ");
	    myGuess = myGuess.toUpperCase();
	    char letter;
	    if (myGuess.length() == 1){
	    	letter = myGuess.charAt(0);
	    } else {
	    	letter = '?';
	    }
	    return letter;
      }
   
    private RandomGenerator rgen = RandomGenerator.getInstance();

}
