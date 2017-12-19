/*
 * File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display.
 */

import acm.graphics.*;

public class HangmanCanvas extends GCanvas {

	private static GLabel displayedWord = new GLabel("");
	
	private static GLabel guessedLetters = new GLabel("");
	
/** Resets the display so that only the scaffold appears */
	public void reset(String word) {
		/* Display the Hangman scaffolding */
    	add(new GLine(X_OFFSET, Y_OFFSET, X_OFFSET, Y_OFFSET+SCAFFOLD_HEIGHT)); // Main scaffold
    	add(new GLine(X_OFFSET, Y_OFFSET, X_OFFSET+BEAM_LENGTH, Y_OFFSET)); // Horizontal scaffold
    	add(new GLine(X_OFFSET+BEAM_LENGTH, Y_OFFSET, X_OFFSET+BEAM_LENGTH, Y_OFFSET+ROPE_LENGTH)); // Rope
	
    	displayedWord.setFont("Courier-16");
    	displayedWord.setLabel("Secret word: " + word);
		add(displayedWord, X_OFFSET/2, Y_OFFSET+SCAFFOLD_HEIGHT+14);
		
		guessedLetters.setFont("Courier-16");
		guessedLetters.setLabel("Past guesses: ");
		add(guessedLetters, displayedWord.getX(), 
				displayedWord.getY()+displayedWord.getHeight());
	}

/**
 * Updates the word on the screen to correspond to the current
 * state of the game.  The argument string shows what letters have
 * been guessed so far; unguessed letters are indicated by hyphens.
 */
	public void displayWord(String word, String guesses) {
		remove(displayedWord);
		displayedWord.setLabel("Secret word: " + word);
		add(displayedWord, X_OFFSET/2, Y_OFFSET+SCAFFOLD_HEIGHT+14);
		
		remove(guessedLetters);
		guessedLetters.setLabel("Past guesses: " + guesses);
		add(guessedLetters, displayedWord.getX(), displayedWord.getY()+displayedWord.getHeight());
	}

/**
 * Updates the display to correspond to an incorrect guess by the
 * user.  Calling this method causes the next body part to appear
 * on the scaffold and adds the letter to the list of incorrect
 * guesses that appears at the bottom of the window.
 */
	public void noteIncorrectGuess(int Lives/*, char letter*/) {
		/* The Hanging Man */
    	// Head
		GOval head = new GOval(X_OFFSET+BEAM_LENGTH-HEAD_RADIUS, Y_OFFSET+ROPE_LENGTH, 
    			2*HEAD_RADIUS, 2*HEAD_RADIUS);
    	// Body
		GLine body = new GLine(head.getX()+HEAD_RADIUS, head.getY()+2*HEAD_RADIUS,
    			head.getX()+HEAD_RADIUS, head.getY()+2*HEAD_RADIUS+BODY_LENGTH);
    	// Left arm
		GLine lBicep = new GLine(body.getX()-UPPER_ARM_LENGTH, body.getY()+ARM_OFFSET_FROM_HEAD,
				body.getX(), body.getY()+ARM_OFFSET_FROM_HEAD);
		GLine lForearm = new GLine(lBicep.getX(), lBicep.getY(), lBicep.getX(), lBicep.getY()+LOWER_ARM_LENGTH);
		// Right arm
		GLine rBicep = new GLine(body.getX()+UPPER_ARM_LENGTH, body.getY()+ARM_OFFSET_FROM_HEAD,
				body.getX()-UPPER_ARM_LENGTH, body.getY()+ARM_OFFSET_FROM_HEAD);
		GLine rForearm = new GLine(rBicep.getX(), rBicep.getY(), rBicep.getX(), rBicep.getY()+LOWER_ARM_LENGTH);
		// Left leg
		GLine lFemur = new GLine(body.getX()-HIP_WIDTH, body.getY()+BODY_LENGTH,
				body.getX(), body.getY()+BODY_LENGTH);
		GLine lShin = new GLine(lFemur.getX(), lFemur.getY()+LEG_LENGTH, lFemur.getX(), lFemur.getY());
		// Right leg
		GLine rFemur = new GLine(body.getX()+HIP_WIDTH, body.getY()+BODY_LENGTH,
				body.getX(), body.getY()+BODY_LENGTH);
		GLine rShin = new GLine(rFemur.getX(), rFemur.getY()+LEG_LENGTH, rFemur.getX(), rFemur.getY());
		// Left foot
		GLine lFoot = new GLine(lShin.getX()-FOOT_LENGTH, lShin.getY(), lShin.getX(), lShin.getY());
		// Right foot
		GLine rFoot = new GLine(rShin.getX()+FOOT_LENGTH, rShin.getY(), rShin.getX(), rShin.getY());
    	
		if (Lives == 7){
			add(head);
		} else if (Lives == 6){    	
			add(body);
		} else if (Lives == 5){
			add(lBicep);
    		add(lForearm);
		} else if (Lives == 4){
			add(rBicep);
			add(rForearm);
		} else if (Lives == 3){
			add(lFemur);
			add(lShin);
		} else if (Lives == 2){
			add(rFemur);
    		add(rShin);
		} else if (Lives == 1){
			add(lFoot);
		} else if (Lives == 0){
			add(rFoot);
		}
	}

/* Constants for the simple version of the picture (in pixels) */
	private static final int SCAFFOLD_HEIGHT = 360;
	private static final int BEAM_LENGTH = 144;
	private static final int ROPE_LENGTH = 18;
	private static final int HEAD_RADIUS = 36;
	private static final int BODY_LENGTH = 144;
	private static final int ARM_OFFSET_FROM_HEAD = 28;
	private static final int UPPER_ARM_LENGTH = 72;
	private static final int LOWER_ARM_LENGTH = 44;
	private static final int HIP_WIDTH = 36;
	private static final int LEG_LENGTH = 108;
	private static final int FOOT_LENGTH = 28;
	private static final int Y_OFFSET = 50;
	private static final int X_OFFSET = 100;

}
