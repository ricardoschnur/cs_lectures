package ttfe.AI;

import java.util.Random;

import ttfe.MoveDirection;
import ttfe.PlayerInterface;
import ttfe.SimulatorInterface;
import ttfe.TTFEFactory;
import ttfe.UserInterface;

public class AutoPlayer_MonteCarlo implements PlayerInterface {
	private int TRIES = 250;
	private Random r = new Random(6);
	private SimulatorInterface copy;
	private MoveDirection first, direction;
	private int cols;
	private int rows;
	private int moveNorth;
	private int moveEast;
	private int moveSouth;
	private int moveWest;
	private int max;
	private int isPossibleNorth;
	private int isPossibleEast;
	private int isPossibleSouth;
	private int isPossibleWest;
	private boolean movePossible;
	private boolean uninitialized = true;

	@Override
	public MoveDirection getPlayerMove(SimulatorInterface game, UserInterface ui) {
		if (uninitialized) {
			cols = game.getBoardHeight();
			rows = game.getBoardWidth();
			uninitialized = false;
		}

		// if only one move is possible, perform it
				isPossibleNorth = game.isMovePossible(MoveDirection.NORTH) ? 1: 0;
				isPossibleEast = game.isMovePossible(MoveDirection.EAST) ? 1: 0;
				isPossibleSouth = game.isMovePossible(MoveDirection.SOUTH) ? 1: 0;
				isPossibleWest = game.isMovePossible(MoveDirection.WEST) ? 1: 0;
				
				if ( isPossibleNorth + isPossibleEast +isPossibleSouth + isPossibleWest == 1 ) {
					if ( isPossibleNorth == 1 ) {
						return MoveDirection.NORTH;
					}
					if ( isPossibleEast == 1 ) {
						return MoveDirection.EAST;
					}
					if ( isPossibleSouth == 1 ) {
						return MoveDirection.SOUTH;
					}
					if ( isPossibleWest == 1 ) {
						return MoveDirection.WEST;
					}
				}

						
						
		// do the method
		moveNorth = 0;
		moveEast = 0;
		moveSouth = 0;
		moveWest = 0;
		max = 0;

		for (int i = 0; i < TRIES; i++) {
			copy = copyBoard(game);
			movePossible = true;
			isPossibleNorth = 1;
			isPossibleEast = 1;
			isPossibleSouth = 1;
			isPossibleWest = 1;

			// First direction should be remembered
			while (true) {
				first = getPossibleMove();
				if (copy.performMove(first)) {
					copy.addPiece();
					break;
				}
				setMoveImpossible(first);
			}

			while (movePossible) {
				direction = getPossibleMove();
				if (copy.performMove(direction)) {
					isPossibleNorth = 1;
					isPossibleEast = 1;
					isPossibleSouth = 1;
					isPossibleWest = 1;
					copy.addPiece();
				}
				setMoveImpossible(direction);
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

	private void setMoveImpossible(MoveDirection dir) {
		switch (dir) {
		case NORTH:
			isPossibleNorth = 0;
			break;
		case EAST:
			isPossibleEast = 0;
			break;
		case SOUTH:
			isPossibleSouth = 0;
			break;
		case WEST:
			isPossibleWest = 0;
			break;
		}
	}

	private MoveDirection getPossibleMove() {
		int n = isPossibleNorth + isPossibleEast + isPossibleSouth + isPossibleWest;

		// default case
		if (n == 0) {
			movePossible = false;
			return MoveDirection.NORTH;
		}

		int k = r.nextInt(n);

		if (isPossibleNorth == 1) {
			if (k == 0) {
				return MoveDirection.NORTH;
			}
			--k;
		}

		if (isPossibleEast == 1) {
			if (k == 0) {
				return MoveDirection.EAST;
			}
			--k;
		}

		if (isPossibleSouth == 1) {
			if (k == 0) {
				return MoveDirection.SOUTH;
			}
			--k;
		}

		if (isPossibleWest == 1) {
			if (k == 0) {
				return MoveDirection.WEST;
			}
			--k;
		}

		// Should not reach this point
		return MoveDirection.NORTH;
	}

	private SimulatorInterface copyBoard(SimulatorInterface game) {
		SimulatorInterface copy = TTFEFactory.createSimulator(rows, cols, r);

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				copy.setPieceAt(i, j, game.getPieceAt(i, j));
			}
		}

		return copy;
	}
}
