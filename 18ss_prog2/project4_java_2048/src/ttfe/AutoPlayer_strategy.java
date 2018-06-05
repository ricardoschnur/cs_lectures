package ttfe;

public class AutoPlayer_strategy implements PlayerInterface {

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
