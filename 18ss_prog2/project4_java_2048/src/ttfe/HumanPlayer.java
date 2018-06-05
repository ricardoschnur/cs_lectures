/**
 * 
 */
package ttfe;

/**
 * @author ricardo
 *
 */
public class HumanPlayer implements PlayerInterface {

	/* (non-Javadoc)
	 * @see ttfe.PlayerInterface#getPlayerMove(ttfe.SimulatorInterface, ttfe.UserInterface)
	 */
	@Override
	public MoveDirection getPlayerMove(SimulatorInterface game, UserInterface ui) {
		return ui.getUserMove();
	}

}
