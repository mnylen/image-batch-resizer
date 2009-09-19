package mnylen.imagebatchresizer.resizer.tests;

import java.util.Random;

import org.junit.*;
import static org.junit.Assert.*;

public class JunitEsimerkki {
	@Test
	public void testaaKonstruktori() {
		MasterGame game = new MasterGame();
		int[][] guess = game.getGuess();
		
		assertEquals(10, guess.length);
		assertEquals(4, guess[0].length);
		
		for (int i = 0; i < guess.length; i++) {
			for (int j = 0 ; j < guess[0].length; j++) {
				assertEquals(-1, guess[i][j]);
			}
		}
	}
	
	@Test
	public void testSetColor() {
		MasterGame game = new MasterGame();
		
		game.setColor(0, 4);
		assertEquals(4, game.getGuess()[0][0]);
		
		game.setColor(0, 1);
		assertEquals(1, game.getGuess()[0][0]);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSetColorInvalidPeg() {
		MasterGame game = new MasterGame();
		game.setColor(100, 4);
	}
}

class MasterGame {
	/*
	*enum Color tŠhŠn!
	*/
	
	private static final int MAX_GUESSES = 10;
	private static final int PEGS_PER_GUESS = 4;
	
	private int round;
	private int[][] guess = new int[10][4];
	
	private int[] code = new int[4];

	public MasterGame() {
		round = 0;
		createCode();

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 4; j++) {
				guess[i][j] = -1;
			}
		}
	}

	private void createCode() {
		for (int i = 0; i < 4; i++) {
			this.code[i] = (int) (Math.random()*6);
		}
	}
	
	public int getRound() {
		return round;
	}

	public int[][] getGuess() {
		return guess;
	}

	public void setColor(int peg, int color) {
		if (!(checkPeg(peg)))
			throw new IllegalArgumentException("invalid peg number");
		
		guess[round][peg] = color;
	}
	
	private boolean checkPeg(int peg) {
		if (peg < 0 || peg > 3)
			return false;
		
		return true;
	}

	public boolean guessReady() {
		for (int i = 0; i < PEGS_PER_GUESS; i++) {
			if (guess[round][i] == -1) return false;
		}
		return true;
	}

	public int[] checkGuess() {
		//if (!guessValid(guess)) throw new Exception("Guess not valid.");

		int black = 0;
		int white = 0;
		int[] done_guess = new int[4];
		int[] done_code = new int[4];

		// Check for "blacks" - pegs with correct color on matching location
		for (int i = 0; i < 4; i++) {
			if (guess[round][i] == this.code[i]) {
				black++;
				done_guess[i] = 1;
				done_code[i] = 1;
			}
		}

		// Check for "whites" - pegs with correct color, not matching locations
		for (int i = 0; i < 4; i++) {
			if (done_guess[i] != 1) {
				for (int j = 0; j < 4; j++) {
					if (guess[round][i] == this.code[j] && done_code[j] != 1) {
						white++;
						done_code[j] = 1;
						break;
					}
				}
			}
		}

		// Move on to the next round
		this.round++;

		// Wrap result in a int[2] and return it
		int[] result = {black, white};
		return result;
	}
}