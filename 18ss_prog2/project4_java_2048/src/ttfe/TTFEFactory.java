package ttfe;

import java.util.Random;

import ttfe.AI.AutoPlayer_MonteCarlo;

public class TTFEFactory {

	/**
	 * Create a 2048 player.
	 *
	 * @param human
	 *            Flag to indicate a human player will play the game.
	 *
	 * @return A 2048 player that will play automatically if human is false and
	 *         otherwise use the user interface to communicate with the human player
	 *         to select moves.
	 */
	public static PlayerInterface createPlayer(boolean human) {
		PlayerInterface player;
		if (human == true) {
			player = new HumanPlayer();
		} else {
			player = new AutoPlayer_MonteCarlo();
		}
		return player;
	}

	/**
	 * Create a 2048 simulator.
	 *
	 * @param width
	 *            The width of the simulated board, must be greater or equal than 2.
	 * @param height
	 *            The height of the simulated board, must be greater or equal than
	 *            2.
	 * @param r
	 *            The random object used to create random numbers.
	 *
	 * @return A 2048 simulator.
	 */
	public static SimulatorInterface createSimulator(int width, int height, Random r) {
		assert width > 1 && height > 1;
		SimulatorInterface game = new SimulatorImpl(width, height, r);
		return game;
	}

	/**
	 * Create a user interface for a 2048 simulator.
	 *
	 * @param game
	 *            The 2048 simulator.
	 *
	 * @return A graphical user interface for a 2048 simulator.
	 */
	public static UserInterface createUserInterface(SimulatorInterface game) {
		UserInterface ui = new GUI(game);
		return ui;
	}

}
