package ttfe.AI;

import java.util.Random;

import ttfe.PlayerInterface;
import ttfe.SimulatorInterface;
import ttfe.TTFEFactory;

public abstract class AI implements PlayerInterface {
	public Random r = new Random(0);
	private int cols;
	private int rows;
	private boolean uninitialized = true;
	private int i, j, p1, p2, weight, max;
	
	/*
	 * Returns a copy of the current game.
	 * However, score of the copy is zero.
	 */
	public SimulatorInterface copyBoard(SimulatorInterface game) {
		if (uninitialized) {
			cols = game.getBoardHeight();
			rows = game.getBoardWidth();
			uninitialized = false;
		}
		
		SimulatorInterface copy = TTFEFactory.createSimulator(rows, cols, r);

		for (i = 0; i < rows; i++) {
			for (j = 0; j < cols; j++) {
				copy.setPieceAt(i, j, game.getPieceAt(i, j));
			}
		}

		return copy;
	}
	
	
	public int getMaxPiece(SimulatorInterface game) {
		max = Integer.MIN_VALUE;

		for (i = 0; i < rows; i++) {
			for (j = 0; j < cols; j++) {
				p1 = game.getPieceAt(i, j);
				max = p1 > max ? p1 : max;
			}
		}

		return max;
	}
	
	
	public int getEdgeWeight(SimulatorInterface game) {
		weight = 0;
		
		// Horizontal
		for (i = 0; i < rows; i++) {
			for (j = 0; j < cols - 1; j++) {
				p1 = game.getPieceAt(i, j);
				p2 = game.getPieceAt(i, j + 1);
				weight += java.lang.Math.abs(p1 - p2);
			}
		}
		
		// Vertical
		for (i = 0; i < rows - 1; i++) {
			for (j = 0; j < cols; j++) {
				p1 = game.getPieceAt(i, j);
				p2 = game.getPieceAt(i + 1, j);
				weight += java.lang.Math.abs(p1 - p2);
			}
		}
		
		return weight;
	}
}
