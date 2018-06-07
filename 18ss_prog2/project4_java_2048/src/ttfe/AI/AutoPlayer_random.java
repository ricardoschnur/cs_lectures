/**
 * 
 */
package ttfe.AI;

import java.util.Random;
import ttfe.MoveDirection;
import ttfe.PlayerInterface;
import ttfe.SimulatorInterface;
import ttfe.UserInterface;

/**
 * @author ricardo
 *
 */
public class AutoPlayer_random implements PlayerInterface {

	/**
	 * Start with a simple player that chooses moves at random
	 */
	@Override
	public MoveDirection getPlayerMove(SimulatorInterface game, UserInterface ui) {
		Random r = new Random();
		int n = r.nextInt(4);

		if (n == 0 && game.isMovePossible(MoveDirection.NORTH)) {
			return MoveDirection.NORTH;
		}

		if (n == 1 && game.isMovePossible(MoveDirection.EAST)) {
			return MoveDirection.EAST;
		}

		if (n == 2 && game.isMovePossible(MoveDirection.WEST)) {
			return MoveDirection.WEST;
		}

		if (n == 3 && game.isMovePossible(MoveDirection.SOUTH)) {
			return MoveDirection.SOUTH;
		}

		// If drawn move was impossible, try again
		return getPlayerMove(game, ui);
	}

}
