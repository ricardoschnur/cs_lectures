package ttfe.AI;

import ttfe.MoveDirection;
import ttfe.SimulatorInterface;
import ttfe.UserInterface;

public class AutoPlayer_DeepSearch2 extends AI {
	private int DEPTH = 7; // has to be at least 1
	private MoveDirection NEXT;
	private int PENALTY = 1 << 10;
	private long EVALUATION;
	private long CURRENT;
	
	private class Node {
		public long score;
		public SimulatorInterface game;
		
		public Node(long score, SimulatorInterface game) {
			this.score = score;
			this.game = game;
		}
	}

	@Override
	public MoveDirection getPlayerMove(SimulatorInterface game, UserInterface ui) {
		EVALUATION = -1;
		
		for (MoveDirection first : MoveDirection.values()) {
			if (game.isMovePossible(first)) {
				SimulatorInterface copy = copyBoard(game);
				Node node = new Node( eval( 0, 1, copy ), copy );
				Tree<Node> tree = new Tree<Node>(node);
				buildTree(tree, 1, first);
			}		
		}
		
		return NEXT;
	}

	private void buildTree(Tree<Node> tree, int depth, MoveDirection first) {
		// Reached maximal depth?
		if (depth == DEPTH) {
			evaluate(tree.getData(), first, 0);
			return;
		}	
				
		// Add children for all possiblke moves
		for (MoveDirection direction : MoveDirection.values()) {
			SimulatorInterface copy = copyBoard(tree.getData().game);
			if ( copy.performMove(direction) ) {		
				Node node = new Node( eval( tree.getData().score, depth, copy ), copy );
				tree.addLeaf(node);
			}
		}	
		
		// No Move was possible
		if ( tree.getSubtrees().size() == 0 ) {
			evaluate(tree.getData(), first, DEPTH - depth);
			return;
		}

		
		// Iterate over subtrees
		for (Tree<Node> subtree : tree.getSubtrees()) {
			buildTree(subtree, depth + 1, first);
		}	
	}
	
	private long eval(long score, int depth, SimulatorInterface game) {
		CURRENT = score + game.getPoints();
		System.out.println("CURRENT: " + CURRENT);
		return CURRENT > 0 ? CURRENT : 0;
	}

	private void evaluate(Node data, MoveDirection first, int remainingSteps) {
		CURRENT = data.score;
		CURRENT /= (remainingSteps + 1);
		
		if (CURRENT > EVALUATION) {
			EVALUATION = CURRENT;
			NEXT = first;
		}
	}

}

