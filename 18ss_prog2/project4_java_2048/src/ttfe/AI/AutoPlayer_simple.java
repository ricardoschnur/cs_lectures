package ttfe.AI;

import ttfe.MoveDirection;
import ttfe.PlayerInterface;
import ttfe.SimulatorInterface;
import ttfe.UserInterface;

public class AutoPlayer_simple implements PlayerInterface {

	@Override
	public MoveDirection getPlayerMove(SimulatorInterface game, UserInterface ui) {
		if (game.isMovePossible(MoveDirection.NORTH)) {
			return MoveDirection.NORTH;
		}

		if (game.isMovePossible(MoveDirection.EAST)) {
			return MoveDirection.EAST;
		}

		if (game.isMovePossible(MoveDirection.WEST)) {
			return MoveDirection.WEST;
		}

		return MoveDirection.SOUTH;
	}
}
