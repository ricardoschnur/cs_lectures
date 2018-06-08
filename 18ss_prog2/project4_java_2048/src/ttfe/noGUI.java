package ttfe;

public class noGUI implements UserInterface {

	@Override
	public String getUserInput(String question, String[] possibleAnswers) {
		return "";
	}

	@Override
	public MoveDirection getUserMove() {
		return MoveDirection.NORTH;
	}

	@Override
	public void showGameOverScreen(SimulatorInterface game) {
		int max = game.getPieceAt(0, 0);
		int current;
		for (int i = 0; i < game.getBoardHeight(); i++) {
			for (int j = 0; j < game.getBoardWidth(); j++) {
				current = game.getPieceAt(i, j);
				max = current > max ? current : max;
			}
		}
		
		System.out.println("Achieved " + game.getPoints() + " points in " + game.getNumMoves() + " moves. Highest piece was " + max + ".");
		//System.out.println("" + max);
	}

	@Override
	public void showMessage(String msg) {}

	@Override
	public void updateScreen(SimulatorInterface game) {}
}
