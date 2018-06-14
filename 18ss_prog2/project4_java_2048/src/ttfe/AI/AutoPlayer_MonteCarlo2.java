package ttfe.AI;

import ttfe.MoveDirection;
import ttfe.SimulatorInterface;
import ttfe.UserInterface;

public class AutoPlayer_MonteCarlo2 extends AI {
	private int TRIES = 50;
	private SimulatorInterface copy;
	private MoveDirection first, direction;
	private int moveNorth;
	private int moveEast;
	private int moveSouth;
	private int moveWest;
	private int isPossibleNorth;
	private int isPossibleEast;
	private int isPossibleSouth;
	private int isPossibleWest;
	private int isFirstPossibleNorth;
	private int isFirstPossibleEast;
	private int isFirstPossibleSouth;
	private int isFirstPossibleWest;
	private int max;
	private boolean movePossible;
	private int i, j, n, k;

	@Override
	public MoveDirection getPlayerMove(SimulatorInterface game, UserInterface ui) {
		// if only one move is possible, perform it
		isFirstPossibleNorth = game.isMovePossible(MoveDirection.NORTH) ? 1: 0;
		isFirstPossibleEast = game.isMovePossible(MoveDirection.EAST) ? 1: 0;
		isFirstPossibleSouth = game.isMovePossible(MoveDirection.SOUTH) ? 1: 0;
		isFirstPossibleWest = game.isMovePossible(MoveDirection.WEST) ? 1: 0;
		
		n = isFirstPossibleNorth + isFirstPossibleEast + isFirstPossibleSouth + isFirstPossibleWest;
		if ( n == 1) {
			if ( isFirstPossibleNorth == 1 ) {
				return MoveDirection.NORTH;
			}
			if ( isFirstPossibleEast == 1 ) {
				return MoveDirection.EAST;
			}
			if ( isFirstPossibleSouth == 1 ) {
				return MoveDirection.SOUTH;
			}
			if ( isFirstPossibleWest == 1 ) {
				return MoveDirection.WEST;
			}
		}			

								
		// do the method
		moveNorth = 0;
		moveEast = 0;
		moveSouth = 0;
		moveWest = 0;
		setAllPossible();

		for (j = 0; j < 4; j++) {
			switch(j) {
			case 0:
				if(isFirstPossibleNorth == 1) {
					first = MoveDirection.NORTH;
					break;
				}
				continue;
			case 1:
				if(isFirstPossibleEast == 1) {
					first = MoveDirection.EAST;
					break;
				}
				continue;
			case 2:
				if(isFirstPossibleSouth == 1) {
					first = MoveDirection.SOUTH;
					break;
				}
				continue;
			case 3:
				if(isFirstPossibleWest == 1) {
					first = MoveDirection.WEST;
					break;
				}
				continue;
			}
			
			for (i = 0; i < TRIES; i++) {
				copy = copyBoard(game);
				movePossible = true;

				copy.performMove(first);
				copy.addPiece();				
				
				while (movePossible) {
					direction = getPossibleMove();
					if (copy.performMove(direction)) {
						setAllPossible();
						copy.addPiece();
						continue;
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
	
	private void setAllPossible() {
		isPossibleNorth = 1;
		isPossibleEast = 1;
		isPossibleSouth = 1;
		isPossibleWest = 1;
	}

	private MoveDirection getPossibleMove() {
		n = isPossibleNorth + isPossibleEast + isPossibleSouth + isPossibleWest;

		// default case
		if (n == 0) {
			movePossible = false;
			return MoveDirection.NORTH;
		}

		k = r.nextInt(n);

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
}
