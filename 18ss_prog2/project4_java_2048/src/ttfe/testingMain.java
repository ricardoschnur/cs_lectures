package ttfe;

import java.util.Random;

//import ttfe.AI.AutoPlayer_MonteCarlo;
//import ttfe.AI.AutoPlayer_MonteCarlo2;
//import ttfe.AI.AutoPlayer_DeepSearch;
import ttfe.AI.AutoPlayer_DeepSearch2;
//import ttfe.AI.AutoPlayer_random;
//import ttfe.AI.AutoPlayer_simple;

public class testingMain {
	private static PlayerInterface player = new AutoPlayer_DeepSearch2();
	private static boolean SHOW_GUI = true;
	private static int RUNS = 1;
	private static int DEFAULT_WIDTH = 4;
	private static int DEFAULT_HEIGHT = 4;
//	private static long DEFAULT_RANDOM_SEED = 0L;
//	private static long DEFAULT_RANDOM_SEED = -8390354061705970578L;
//	private static long DEFAULT_RANDOM_SEED = 1818607536662613108L;
//	private static Random r = new Random(DEFAULT_RANDOM_SEED);
	private static Random r = new Random();

	public static void main(String[] args) {
		SimulatorInterface game = createSimulator(DEFAULT_WIDTH, DEFAULT_HEIGHT, r);
		UserInterface ui = createUserInterface(game);

		for (int i = 0; i < RUNS; i++) {
			game.run(player, ui);
			game = createSimulator(DEFAULT_WIDTH, DEFAULT_HEIGHT, r);
		}	
	}

	
	
	private static SimulatorInterface createSimulator(int width, int height, Random r) {
		SimulatorInterface game = new SimulatorImpl(width, height, r);
		return game;
	}

	private static UserInterface createUserInterface(SimulatorInterface game) {
		UserInterface ui;
		if(SHOW_GUI) {
			ui = new GUI(game);
		}
		else {
			ui = new noGUI();			
		}
		return ui;
	}
	
}

