package ttfe;

import java.util.Random;
import static org.junit.Assert.assertTrue;

public class AutoPlayer_DeepSearch implements PlayerInterface {
	private int DEPTH = 5;			// has to be at least 1
	private int NUM_DIR = 4;
	private int MAX_PENALTY = 1 << 10;

	@Override
	public MoveDirection getPlayerMove(SimulatorInterface game, UserInterface ui) {
		/*
		 * Order moves as follows (example for DEPTH = 3 and NUM_DIR = 4):
		 *	  0: (0,0,0)
		 *	  1: (0,0,1)
		 *	  2: (0,0,2)
		 *	  3: (0,0,3)
		 *	  4: (0,1,0)
		 *	 		...
		 *	 15: (0,3,3)
		 *	 16: (1,0,0)
		 *	 		...
		 *	 63: (3,3,3)
		 *
		 * Here the i-th element of the tuple stands for the direction in this turn, with
		 * 0 <> NORTH
		 * 1 <> EAST
		 * 2 <> SOUTH
		 * 3 <> WEST
		 * 
		 * Note that in general the pair (i_0, ..., i_{DEPTH-1}) appears 
		 * at position 0 <= p < NUM_DIR^DEPTH, where
		 * 		p = i_0 * NUM_DIR^{DEPTH-1} + i_1 * NUM_DIR^{DEPTH-2} 
		 * 							  + ... + i_{DEPTH-1} * NUM_DIR^0
		 * 
		 * Conversely, given a position p, you can get back the direction i_0 
		 * of the first move via integer division
		 * 		i_0 = p / NUM_DIR^{DEPTH-1}
		 */
		
		
		// powers[i] = NUM_DIR^i
		int[] powers = new int[DEPTH];
		powers[0] = 1;
		for (int i = 1; i < DEPTH; i++) {
			powers[i] = powers[i-1] * NUM_DIR;
		}
		
		int possibilities = powers[DEPTH-1] * NUM_DIR;	
		
		// build fields
		int[] scores = new int[possibilities];
		boolean[] isPossible = new boolean[possibilities];
		
		for (int i = 0; i< possibilities; i++) {
			scores[i] = 0;
			isPossible[i] = true;
		}
				
		// Search in each direction
		for (int i = 0; i < NUM_DIR; i++) {
			SimulatorInterface currentGame = copyBoard(game);
			int pos = i * powers[DEPTH-1];
			assertTrue("pos: " + pos, pos >= 0 && pos < possibilities);
			searchDepth(currentGame, scores, isPossible, 0, pos, powers);
		}
		
		// Get direction
		int position = getMaxPosition(scores);
		int dir = position / powers[DEPTH-1];
		
		MoveDirection direction = MoveDirection.NORTH;
		switch(dir) {
		case 0:
			break;
		case 1:
			direction = MoveDirection.EAST;
			break;
		case 2:
			direction = MoveDirection.SOUTH;
			break;
		case 3:
			direction = MoveDirection.WEST;
			break;
		}
		
		return direction;
	}
	
	
	private int getMaxPosition(int[] scores) {
		assert scores.length > 0;
		int maxVal = scores[0];
		int maxPos = 0;
		
		for (int i = 1; i < scores.length; i++) {
			if (scores[i] > maxVal) {
				maxVal = scores[i];
				maxPos = i;
			}
		}
		
		return maxPos;
	}


	private SimulatorInterface copyBoard(SimulatorInterface game) {
		int cols = game.getBoardHeight();
		int rows = game.getBoardWidth();
		Random r = new Random();
		SimulatorInterface copy = TTFEFactory.createSimulator(rows, cols, r);
		
		
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				copy.setPieceAt(i, j, game.getPieceAt(i, j));
			}
		}	
		
		return copy;
	}
	
	
	private void searchDepth(SimulatorInterface currentGame, int[] scores, boolean[] isPossible, 
			int currentDepth, int currentPos, int[] powers) {
		assertTrue("currentPos: " + currentPos, currentPos >= 0 && currentPos < isPossible.length);
		// End recursion
		if (currentDepth == DEPTH || isPossible[currentPos] == false) {
			return;
		}
		
				
		for (int i = 0; i <4; i++) {			
			SimulatorInterface game = copyBoard(currentGame);
			
			MoveDirection direction = MoveDirection.NORTH;
			switch(i) {
			case 1:
				direction = MoveDirection.EAST;
				break;
			case 2:
				direction = MoveDirection.SOUTH;
				break;
			case 3:
				direction = MoveDirection.WEST;
				break;
			default:
				break;
			}
			
			// Direction possible?
			int newPos = currentPos + i * powers[DEPTH-1-currentDepth];
			if (newPos >= scores.length) {
				return;
			}
			assertTrue("newPos: " + newPos, newPos >= 0 && newPos < scores.length);
			
			if (game.performMove(direction)) {
				game.addPiece();
				addScore(scores, currentDepth, currentPos,  powers,i, game.getPoints());
					
				
				// If no other move is possible after the next move, incur heavy penalty
				SimulatorInterface copy1 = copyBoard(currentGame);
				SimulatorInterface copy2 = copyBoard(currentGame);
				SimulatorInterface copy3 = copyBoard(currentGame);
				SimulatorInterface copy4 = copyBoard(currentGame);
				
				int pos = 0;
				int depth = currentDepth + 1;
				
				if ( copy1.performMove(MoveDirection.NORTH) ) {
					if ( !copy1.isMovePossible() ) {
						pos = currentPos + 0 * powers[DEPTH-1-currentDepth];		// 0 for NORTH
						for (int j = 0; j < 4; j++) {
							addScore(scores, depth, pos, powers, j, -1 * java.lang.Math.max(MAX_PENALTY, game.getPoints()) );
						}
					} 
				} 
				
				if ( copy2.performMove(MoveDirection.EAST) ) {
					if ( !copy2.isMovePossible() ) {
						pos = currentPos + 1 * powers[DEPTH-1-currentDepth];		// 1 for EAST
						for (int j = 0; j < 4; j++) {
							addScore(scores, depth, pos, powers, j, -1 * java.lang.Math.max(MAX_PENALTY, game.getPoints()) );
						}
					} 
				} 
				
				if ( copy3.performMove(MoveDirection.SOUTH) ) {
					if ( !copy3.isMovePossible() ) {
						pos = currentPos + 2 * powers[DEPTH-1-currentDepth];		// 2 for SOUTH
						for (int j = 0; j < 4; j++) {
							addScore(scores, depth, pos, powers, j, -1 * java.lang.Math.max(MAX_PENALTY, game.getPoints()) );
						}
					} 
				} 
				
				if ( copy4.performMove(MoveDirection.WEST) ) {
					if ( !copy4.isMovePossible() ) {
						pos = currentPos + 3 * powers[DEPTH-1-currentDepth];		// 3 for WEST
						for (int j = 0; j < 4; j++) {
							addScore(scores, depth, pos, powers, j, -1 * java.lang.Math.max(MAX_PENALTY, game.getPoints()) );
						}
					} 
				} 

				
				
				searchDepth(game, scores,isPossible, currentDepth + 1, newPos, powers);		
				
			} else {
				setImpossible(isPossible, currentDepth, currentPos, powers, i);
			}
		}
	}

	private void addScore(int[] scores, int currentDepth, int currentPos, int[] powers, int dir, int points) {
		int pos = currentPos + dir * powers[DEPTH-1-currentDepth];
		int range = pos + powers[DEPTH-1-currentDepth];
		for (; pos < range; pos++) {
			scores[pos] += points;
		}		
	}


	private void setImpossible(boolean[] isPossible, int currentDepth, int currentPos, int[] powers,
			int dir) {
		int pos = currentPos + dir * powers[DEPTH-1-currentDepth];
		int range = pos + powers[DEPTH-1-currentDepth];
		for (; pos < range; pos++) {
			isPossible[pos] = false;
		}
	}

	


	

}
