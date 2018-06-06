package ttfe;

import java.util.Random;

public class AutoPlayer_MonteCarlo implements PlayerInterface {
	private int TRIES = 100;

	@Override
	public MoveDirection getPlayerMove(SimulatorInterface game, UserInterface ui) {
		
		PlayerInterface player = new AutoPlayer_random();
		SimulatorInterface copy;
		MoveDirection first, direction;
		int moveNorth = 0;
		int moveEast = 0;
		int moveSouth = 0;
		int moveWest = 0;
		int max = 0;
		
		for (int i = 0; i < TRIES; i++) {
			copy = copyBoard(game);
			
			// First direction should be remembered
			while(true) {
				first = player.getPlayerMove(copy, ui);
				if(copy.performMove(first)) {
					copy.addPiece();
					break;
				}
			}
			
			while(copy.isMovePossible()) {
				direction = player.getPlayerMove(copy, ui);
				copy.performMove(direction);
				copy.addPiece();
			}
			
			switch (first) {
			case NORTH:
				moveNorth += copy.getPoints();
				break;
			case EAST:
				moveEast += copy.getPoints();
				break;
			case SOUTH:
				moveSouth += copy.getPoints();
				break;
			case WEST:
				moveWest += copy.getPoints();
				break;
			}
		}
		
		// Find maximum
		direction = MoveDirection.NORTH;
		max = moveNorth;
		
		if (moveEast > max) {
			max = moveEast;
			direction = MoveDirection.EAST;
		}
		
		if (moveSouth > max) {
			max = moveSouth;
			direction = MoveDirection.SOUTH;
		}
		
		if (moveWest > max) {
			max = moveWest;
			direction = MoveDirection.WEST;
		}
		
		return direction;
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
}
