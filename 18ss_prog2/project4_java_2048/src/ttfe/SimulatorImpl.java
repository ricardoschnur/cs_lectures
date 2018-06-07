/**
 *
 */
package ttfe;

import java.util.Random;

/**
 * @author ricardo
 *
 */
public class SimulatorImpl implements SimulatorInterface {
	private int rows;
	private int cols;
	private int free; // number of free positions on the field
	private int score;
	private int moves;
	private Random rng;
	private int[][] field;
	private boolean[][] merged;

	// Constructor for new SimulatorImpl
	public SimulatorImpl(int rows, int cols, Random r) {
		this.rows = rows;
		this.cols = cols;
		free = rows * cols;
		score = 0;
		moves = 0;
		rng = r;

		int[][] field;
		boolean[][] merged;
		field = new int[rows][];
		merged = new boolean[rows][];
		for (int i = 0; i < rows; ++i) {
			field[i] = new int[cols];
			merged[i] = new boolean[cols];
			for (int j = 0; j < cols; ++j) {
				field[i][j] = 0;
				merged[i][j] = false;
			}
		}

		this.field = field;
		this.merged = merged;

		for (int i = 0; i < this.rows; ++i) {
			for (int j = 0; j < this.cols; ++j) {
				setPieceAt(i, j, 0);
			}
		}

		// Add two Tiles
		addPiece();
		addPiece();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ttfe.SimulatorInterface#addPiece()
	 */
	@Override
	public void addPiece() {
		assert free > 0;

		// Generate a random integer in [0, this.free)
		int n = rng.nextInt(free);

		// Find position (i,j) of the correct Tile
		int current = 0;
		int x = 0;
		int y = 0;
		for (int i = 0; i < rows && current <= n; ++i) {
			for (int j = 0; j < cols && current <= n; ++j) {
				// Increase counter if current Tile is empty
				if (!getFullAt(i, j)) {
					current += 1;
					x = i;
					y = j;
				}
			}
		}

		n = 2;
		if (rng.nextInt(10) == 0) {
			n = 4;
		}

		setPieceAt(x, y, n);
	}

	// Returns a copy of the SimulatorImpl
	public SimulatorImpl copySimulatorImpl() {
		SimulatorImpl field = new SimulatorImpl(rows, cols, rng);
		field.free = free;
		field.score = score;
		field.moves = moves;

		for (int i = 0; i < rows; ++i) {
			for (int j = 0; j < cols; ++j) {
				field.setPieceAt(i, j, getPieceAt(i, j));
			}
		}

		return field;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ttfe.SimulatorInterface#getBoardHeight()
	 */
	@Override
	public int getBoardHeight() {
		assert cols >= 2;
		return cols;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ttfe.SimulatorInterface#getBoardWidth()
	 */
	@Override
	public int getBoardWidth() {
		assert rows >= 2;
		return rows;
	}

	// Returns true if position (row,col) is full, false if empty
	private boolean getFullAt(int row, int col) {
		return field[row][col] != 0;
	}

	private boolean getMergedAt(int x, int y) {
		assert x >= 0 && x < rows && y >= 0 && y < cols;
		return merged[x][y];
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ttfe.SimulatorInterface#getNumMoves()
	 */
	@Override
	public int getNumMoves() {
		assert moves >= 0;
		return moves;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ttfe.SimulatorInterface#getNumPieces()
	 */
	@Override
	public int getNumPieces() {
		int n = rows * cols - free;
		assert n >= 0;
		return n;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ttfe.SimulatorInterface#getPieceAt(int, int)
	 */
	@Override
	public int getPieceAt(int x, int y) {
		assert x >= 0 && x < rows && y >= 0 && y < cols;
		return field[x][y];
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ttfe.SimulatorInterface#getPoints()
	 */
	@Override
	public int getPoints() {
		assert score >= 0;
		return score;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ttfe.SimulatorInterface#isMovePossible()
	 */
	@Override
	public boolean isMovePossible() {
		if (isSpaceLeft() && getNumPieces() > 0) {
			return true;
		}

		SimulatorImpl new_field = copySimulatorImpl();
		return new_field.moveNorth() == true || new_field.moveSouth() == true || new_field.moveEast() == true
				|| new_field.moveWest() == true;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ttfe.SimulatorInterface#isMovePossible(ttfe.MoveDirection)
	 */
	@Override
	public boolean isMovePossible(MoveDirection direction) {
		SimulatorImpl new_field = copySimulatorImpl();

		switch (direction) {
		case NORTH:
			return new_field.moveNorth() == true;
		case EAST:
			return new_field.moveEast() == true;
		case SOUTH:
			return new_field.moveSouth() == true;
		case WEST:
			return new_field.moveWest() == true;
		}

		return false;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ttfe.SimulatorInterface#isSpaceLeft()
	 */
	@Override
	public boolean isSpaceLeft() {
		return free > 0;
	}

	// Merges Tiles in position (x1, y1) and (x2, y2)
	// Produces merged Tile at (x1,y1) and empty Tile at (x2,y2)
	private void mergeTiles(int x1, int y1, int x2, int y2) {
		int val1 = getPieceAt(x1, y1);
		int val2 = getPieceAt(x2, y2);

		assert val1 == val2;

		int doubled_value = 2 * val1;
		setPieceAt(x1, y1, doubled_value);
		setMergedAt(x1, y1, true);
		setPieceAt(x2, y2, 0);

		score += doubled_value;
	}

	// Perform movement of tile t from (x,y) to (x+dx, y+dy) if possible
	// Handle merging, borders, no move possible
	// return 1if it moved tiles, 0 otherwise
	private int move(int x, int y, int dx, int dy) {
		// If Tile is empty, do nothing
		if (!getFullAt(x, y)) {
			return 0;
		}

		// Tile is at border of grid
		boolean b = dx == -1 && x == 0 || dx == 1 && x == rows - 1 || dy == -1 && y == 0
				|| dy == 1 && y == cols - 1;
		if (b) {
			return 0;
		}

		// Not at border
		if (!getFullAt(x + dx, y + dy)) {
			// recursively move Tile up as far as possible
			moveTile(x, y, x + dx, y + dy);
			move(x + dx, y + dy, dx, dy);
			return 1;
		}
		if (getPieceAt(x, y) == getPieceAt(x + dx, y + dy)) {
			// if Tiles were already merged this turn do nothing, else merge
			if (getMergedAt(x, y) == true || getMergedAt(x + dx, y + dy) == true) {
				return 0;
			}
			mergeTiles(x + dx, y + dy, x, y);
			return 1;
		}
		// Should not reach this point
		return 0;
	}

	private boolean moveEast() {
		int movecounter = 0;
		for (int i = rows - 1; i >= 0; --i) {
			for (int j = 0; j < cols; ++j) {
				movecounter += move(i, j, 1, 0);
			}
		}
		return movecounter > 0;
	}

	private boolean moveNorth() {
		int movecounter = 0;
		for (int j = 1; j < cols; ++j) {
			for (int i = 0; i < rows; ++i) {
				movecounter += move(i, j, 0, -1);
			}
		}
		return movecounter > 0;
	}

	private boolean moveSouth() {
		int movecounter = 0;
		for (int j = cols - 1; j >= 0; --j) {
			for (int i = 0; i < rows; ++i) {
				movecounter += move(i, j, 0, 1);
			}
		}
		return movecounter > 0;
	}

	// Moves the Tile in position (x1, y1) to position (x2, y2)
	private void moveTile(int x1, int y1, int x2, int y2) {
		setPieceAt(x2, y2, getPieceAt(x1, y1));
		setPieceAt(x1, y1, 0);
	}

	private boolean moveWest() {
		int movecounter = 0;
		for (int i = 1; i < rows; ++i) {
			for (int j = 0; j < cols; ++j) {
				movecounter += move(i, j, -1, 0);
			}
		}
		return movecounter > 0;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ttfe.SimulatorInterface#performMove(ttfe.MoveDirection)
	 */
	@Override
	public boolean performMove(MoveDirection direction) {
		boolean b = false;

		switch (direction) {
		case NORTH:
			b = moveNorth();
			break;

		case EAST:
			b = moveEast();
			break;

		case WEST:
			b = moveWest();
			break;

		case SOUTH:
			b = moveSouth();
			break;
		}

		if (b == true) {
			moves += 1;
			unsetMerged();
		}

		return b;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ttfe.SimulatorInterface#run(ttfe.PlayerInterface, ttfe.UserInterface)
	 */
	@Override
	public void run(PlayerInterface player, UserInterface ui) {
		MoveDirection direction;
		ui.updateScreen(this);
		while (isMovePossible()) {
			direction = player.getPlayerMove(this, ui);
			if (!performMove(direction)) {
				direction = player.getPlayerMove(this, ui);
			} else {
				addPiece();
				ui.updateScreen(this);
			}
		}
		ui.showGameOverScreen(this);
	}

	private void setMergedAt(int x, int y, boolean b) {
		assert x >= 0 && x < rows && y >= 0 && y < cols;

		merged[x][y] = b;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ttfe.SimulatorInterface#setPieceAt(int, int, int)
	 */
	@Override
	public void setPieceAt(int x, int y, int piece) {
		assert x >= 0 && x < rows && y >= 0 && y < cols;

		int current = getPieceAt(x, y);

		if (current == 0 && piece != 0) {
			free -= 1;
		}

		if (current != 0 && piece == 0) {
			free += 1;
		}

		field[x][y] = piece;
	}

	private void unsetMerged() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				setMergedAt(i, j, false);
			}
		}

	}
}
