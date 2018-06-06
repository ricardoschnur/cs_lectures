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
	  private int free;           // number of free positions on the field
	  private int score;
	  private int moves;
	  private Random rng;
	  private Tile[][] field;
	
	  
	  // Constructor for new SimulatorImpl
	  public SimulatorImpl(int rows, int cols, Random r) {
	    this.rows = rows;
	    this.cols = cols;
	    this.free = rows * cols;
	    this.score = 0;
	    this.moves = 0;
	    this.rng = r;

	    Tile[][] field;
	    field = new Tile[rows][];
	    for (int i = 0; i < rows; ++i) {
	      field[i] = new Tile[cols];
	      for (int j = 0; j < cols; ++j) {
	        field[i][j] = new Tile();
	      }
	    }

	    this.field = field;

	    for (int i = 0; i < this.rows; ++i) {
	      for (int j = 0; j < this.cols; ++j) {
	        this.setPieceAt(i, j, 0);
	        this.setFullAt(i, j, false);
	      }
	    }

	    // Add two Tiles
	    this.addPiece();
	    this.addPiece();
	  }
	  	  
	  
	  // Marks the position (row,col) as full (true) or empty (false)
	  private void setFullAt(int row, int col, boolean bool){
	    this.field[row][col].setFull(bool);
	  }
	  
	  
	  // Returns true if position (row,col) is full, false if empty
	  private boolean getFullAt(int row, int col) {
	    return this.field[row][col].getFull();
	  }
	  	  

	  // Returns a copy of the SimulatorImpl
	  public SimulatorImpl copySimulatorImpl(){
	    SimulatorImpl field = new SimulatorImpl(this.rows, this.cols, this.rng);
	    field.free = this.free;
	    field.score = this.score;
	    field.moves = this.moves;

	    int val;
	    boolean full;

	    for (int i = 0; i < this.rows; ++i) {
	      for (int j = 0; j < cols; ++j) {
	        val = this.getPieceAt(i, j);
	        full = this.getFullAt(i, j);

	        field.setPieceAt(i, j, val);
	        field.setFullAt(i, j, full);
	      }
	    }

	    return field;
	  }
	  

	/* (non-Javadoc)
	 * @see ttfe.SimulatorInterface#addPiece()
	 */
	@Override
	public void addPiece() {
			assert this.free > 0;
		
		    // Generate a random integer in [0, this.free)
		    int n = this.rng.nextInt(this.free);

		    // Find position (i,j) of the correct Tile
		    int current = 0;
		    int x = 0;
		    int y = 0;
		    for (int i = 0; i < this.rows && current <= n; ++i) {
		      for (int j = 0; j < this.cols && current <= n; ++j) {
		        // Increase counter if current Tile is empty
		        if ( !(this.getFullAt(i, j)) ) {
		          current += 1;
		          x = i;
		          y = j;
		        }
		      }
		    }

		    n = 2;
		    if ( this.rng.nextInt(10) == 0 ) {
		    	n = 4;
		    }

		    this.setPieceAt(x, y, n);
	}

	
	/* (non-Javadoc)
	 * @see ttfe.SimulatorInterface#getBoardHeight()
	 */
	@Override
	public int getBoardHeight() {
		assert this.cols >= 2;
		return this.cols;
	}

	/* (non-Javadoc)
	 * @see ttfe.SimulatorInterface#getBoardWidth()
	 */
	@Override
	public int getBoardWidth() {
		assert this.rows >= 2;
		return this.rows;
	}

	/* (non-Javadoc)
	 * @see ttfe.SimulatorInterface#getNumMoves()
	 */
	@Override
	public int getNumMoves() {
		assert this.moves >= 0;
		return this.moves;
	}

	/* (non-Javadoc)
	 * @see ttfe.SimulatorInterface#getNumPieces()
	 */
	@Override
	public int getNumPieces() {
		int n = this.rows * this.cols - this.free;
		assert n >= 0;
		return n;
	}

	/* (non-Javadoc)
	 * @see ttfe.SimulatorInterface#getPieceAt(int, int)
	 */
	@Override
	public int getPieceAt(int x, int y) {
		assert x >= 0 && x < this.rows && y >= 0 && y < this.cols;
		return this.field[x][y].getPiece();
	}

	/* (non-Javadoc)
	 * @see ttfe.SimulatorInterface#getPoints()
	 */
	@Override
	public int getPoints() {
		assert this.score >= 0;
		return this.score;
	}

	/* (non-Javadoc)
	 * @see ttfe.SimulatorInterface#isMovePossible()
	 */
	@Override
	public boolean isMovePossible() {
		if ( isSpaceLeft() && getNumPieces() > 0) {
			return true;
		}
		
		SimulatorImpl new_field = copySimulatorImpl();
	    return (new_field.moveNorth() == true)
	                    || (new_field.moveSouth() == true)
	                    || (new_field.moveEast() == true)
	                    || (new_field.moveWest() == true);
	}

	/* (non-Javadoc)
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

	/* (non-Javadoc)
	 * @see ttfe.SimulatorInterface#isSpaceLeft()
	 */
	@Override
	public boolean isSpaceLeft() {
		return this.free > 0;
	}

	/* (non-Javadoc)
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
	    	this.moves += 1;
	    }
	    
	    return b;
	}

	/* (non-Javadoc)
	 * @see ttfe.SimulatorInterface#run(ttfe.PlayerInterface, ttfe.UserInterface)
	 */
	@Override
	public void run(PlayerInterface player, UserInterface ui) {
		ui.updateScreen(this);
		while (isMovePossible()) {
			MoveDirection direction = player.getPlayerMove(this, ui);
			if (performMove(direction)) {
				addPiece();
			}
			ui.updateScreen(this);
		}
		ui.showGameOverScreen(this);
	}

	/* (non-Javadoc)
	 * @see ttfe.SimulatorInterface#setPieceAt(int, int, int)
	 */
	@Override
	public void setPieceAt(int x, int y, int piece) {
		assert x >= 0 && x < this.rows && y >= 0 && y < this.cols;
		
		int current = getPieceAt(x, y);
		
		if (current == 0 && piece != 0) {
			this.free -= 1;
			setFullAt(x, y, true);
		}		
		
		if (current != 0 && piece == 0) {
			this.free += 1;
			setFullAt(x, y, false);
		}
		
		this.field[x][y].setPiece(piece);
	}
	
	
	// Moves the Tile in position (x1, y1) to position (x2, y2)
	private void moveTile(int x1, int y1, int x2, int y2) {
	  int val = getPieceAt(x1, y1);
	  boolean full = getFullAt(x1, y1);

	  setPieceAt(x2, y2, val);
	  setFullAt(x2, y2, full);

	  setPieceAt(x1, y1, 0);
	  setFullAt(x1, y1, false);
	}

	  // Merges Tiles in position (x1, y1) and (x2, y2)
	  // Produces merged Tile at (x1,y1) and empty Tile at (x2,y2)
	  private void mergeTiles(int x1, int y1, int x2, int y2) {
	    int val1 = getPieceAt(x1, y1);
	    boolean full1 = getFullAt(x1, y1);

	    int val2 = getPieceAt(x2, y2);
	    boolean full2 = getFullAt(x2, y2);


	    assert( val1 == val2 && full1 == true && full2 == true );

	    int doubled_value = 2*val1;

	    setPieceAt(x1, y1, doubled_value);

	    setPieceAt(x2, y2, 0);

	    this.score += doubled_value;
	  }
	  
	  
	// Perform movement of tile t from (x,y) to (x+dx, y+dy) if possible
	  // Handle merging, borders, no move possible
	  // return true if it moved tiles, false otherwise
	  private int move(int x, int y, int dx, int dy) {
	    // If Tile is empty, do nothing
	    if (!getFullAt(x, y)) {
	      return 0;
	    }

	    // Tile is at border of grid
	    boolean b = (dx == -1 && x == 0) || (dx == 1 && x == this.rows - 1)
	    || (dy == -1 && y == 0) || (dy == 1 && y == this.cols - 1);
	    if (b) {
	      return 0;
	    }

	    // Not at border
	    if (!getFullAt(x + dx, y + dy)) {
	      // recursively move Tile up as far as possible
	      moveTile(x, y, x + dx, y +dy);
	      move(x + dx, y + dy, dx, dy);
	      return 1;
	    }
	    if ( getPieceAt(x, y) == getPieceAt(x + dx, y +dy) ) {
	      mergeTiles(x + dx, y + dy, x, y);
	      return 1;
	    }
	    // Should not reach this point
	    return 0;
	  }
	  
	  private boolean moveWest(){
		    int movecounter = 0;
		    for (int i = 1; i < this.rows; ++i) {
		      for (int j = 0; j < this.cols; ++j) {
		        movecounter += move(i, j, -1, 0);
		      }
		    }
		    return movecounter > 0;
		  }


		  private boolean moveEast(){
		    int movecounter = 0;
		    for (int i = this.rows - 1; i >= 0; --i) {
		      for (int j = 0; j < this.cols; ++j) {
		        movecounter += move(i, j, 1, 0);
		      }
		    }
		    return movecounter > 0;
		  }


		  private boolean moveNorth(){
		    int movecounter = 0;
		    for (int j = 1; j < this.cols; ++j) {
		      for (int i = 0; i < this.rows; ++i) {
		        movecounter += move(i, j, 0, -1);
		      }
		    }
		    return movecounter > 0;
		  }


		  private boolean moveSouth(){
		    int movecounter = 0;
		    for (int j = this.cols - 1; j >= 0; --j) {
		      for (int i = 0; i < this.rows; ++i) {
		        movecounter += move(i, j, 0, 1);
		      }
		    }
		    return movecounter > 0;
		  }
}



