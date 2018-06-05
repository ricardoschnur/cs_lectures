/**
 * 
 */
package ttfe;

/**
 * @author ricardo
 *
 */
public class Tile {
	private int val;
	  private boolean full;

	  // Constructor for empty Tile
	  public Tile() {
	    this.val = 0;
	    this.full = false;
	  }

	  // Constructor for non-empty Tile
	  public Tile(int val) {
	    this.val = val;
	    this.full = true;
	  }

	  // Constructor for arbitrary Tile
	  public Tile(int val, boolean full) {
	    this.val = val;
	    this.full = full;
	  }

	  // Returns vale of the Tile
	  public int getPiece() {
	    return this.val;
	  }

	  // Returns true if Tile is full, false otherwise
	  public boolean getFull() {
	    return this.full;
	  }

	  // Sets vale of the Tile
	  public void setPiece(int val) {
	    this.val = val;
	  }

	  // Sets boolean full of the Tile to bool
	  public void setFull(boolean bool) {
	    this.full = bool;
	  }
}
