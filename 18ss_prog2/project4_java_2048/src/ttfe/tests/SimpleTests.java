package ttfe.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import ttfe.MoveDirection;
import ttfe.SimulatorInterface;
import ttfe.TTFEFactory;

/**
 * This class provides a very simple example of how to write tests for this project.
 * You can implement your own tests within this class or any other class within this package.
 * Tests in other packages will not be run and considered for completion of the project.
 */
public class SimpleTests {

	private SimulatorInterface game1;
	private SimulatorInterface game2;
	private SimulatorInterface game3;
	private SimulatorInterface game4;
	private SimulatorInterface game5;

	@Before
	public void setUp() {
		game1 = TTFEFactory.createSimulator(4, 4, new Random(0));
		game2 = TTFEFactory.createSimulator(2, 8, new Random(0));
		game3 = TTFEFactory.createSimulator(5, 3, new Random(0));
		game4 = TTFEFactory.createSimulator(3, 9, new Random(0));
		game5 = TTFEFactory.createSimulator(2, 2, new Random(0));
	}
	
	
	// Tests for public int getBoardHeight();
	@Test
	public void testGetBoardHeight() {
		assertTrue("The initial game board did not have correct height",
				4 == game1.getBoardHeight());
		assertTrue("The initial game board did not have correct height",
				8 == game2.getBoardHeight());
		assertTrue("The initial game board did not have correct height",
				3 == game3.getBoardHeight());
		assertTrue("The initial game board did not have correct height",
				9 == game4.getBoardHeight());
		assertTrue("The initial game board did not have correct height",
				2 == game5.getBoardHeight());
	}
		
	
	// Tests for public int getBoardWidth();
	@Test
	public void testGetBoardWidth() {
		assertTrue("The initial game board did not have correct width",
				4 == game1.getBoardWidth());
		assertTrue("The initial game board did not have correct width",
				2 == game2.getBoardWidth());
		assertTrue("The initial game board did not have correct width",
				5 == game3.getBoardWidth());
		assertTrue("The initial game board did not have correct width",
				3 == game4.getBoardWidth());
		assertTrue("The initial game board did not have correct width",
				2 == game5.getBoardWidth());
	}

	

	// Tests for public int getNumMoves();
	@Test
	public void testGetNumMovesInitial() {
		assertTrue("The initial game board did not have correct number of moves",
				0 == game1.getNumMoves());
		assertTrue("The initial game board did not have correct number of moves",
				0 == game2.getNumMoves());
		assertTrue("The initial game board did not have correct number of moves",
				0 == game3.getNumMoves());
		assertTrue("The initial game board did not have correct number of moves",
				0 == game4.getNumMoves());
		assertTrue("The initial game board did not have correct number of moves",
				0 == game5.getNumMoves());
	}
	
	@Test
	public void testGetNumMovesFirst() {
		if ( game1.isMovePossible(MoveDirection.NORTH) ) {
			game1.performMove(MoveDirection.NORTH);
			assertTrue("The game board did not have correct number of moves",
					1 == game1.getNumMoves());
		}
		else {
			assertTrue("The game board did not have correct number of moves",
					0 == game1.getNumMoves());
		}
		
		
		
		if ( game2.isMovePossible(MoveDirection.EAST) ) {
			game2.performMove(MoveDirection.EAST);
			assertTrue("The game board did not have correct number of moves",
					1 == game2.getNumMoves());
		}
		else {
			assertTrue("The game board did not have correct number of moves",
					0 == game2.getNumMoves());
		}
		
		
		if ( game3.isMovePossible(MoveDirection.SOUTH) ) {
			game3.performMove(MoveDirection.SOUTH);
			assertTrue("The game board did not have correct number of moves",
					1 == game3.getNumMoves());
		}
		else {
			assertTrue("The game board did not have correct number of moves",
					0 == game3.getNumMoves());
		}
		
		
		if ( game4.isMovePossible(MoveDirection.WEST) ) {
			game4.performMove(MoveDirection.WEST);
			assertTrue("The game board did not have correct number of moves",
					1 == game4.getNumMoves());
		}
		else {
			assertTrue("The game board did not have correct number of moves",
					0 == game4.getNumMoves());
		}
		
		
		if ( game5.isMovePossible(MoveDirection.NORTH) ) {
			game5.performMove(MoveDirection.NORTH);
			assertTrue("The game board did not have correct number of moves",
					1 == game5.getNumMoves());
		}
		else {
			assertTrue("The game board did not have correct number of moves",
					0 == game5.getNumMoves());
		}
	}
	
		

	// Tests for public void addPiece();
	@Test
	public void testaddPiece() {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				game1.setPieceAt(i, j, 0);
			}
		}
		
		assertTrue("Should have no pieces, but have " + game1.getNumPieces(),
				0 == game1.getNumPieces());		
		
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				game1.addPiece();
			}
		}
		
		
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				assertTrue("Should have value 2 or 4, but is " + game1.getPieceAt(i,j),
						2 == game1.getPieceAt(i,j) || 4 == game1.getPieceAt(i,j));
			}
		}
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				game4.setPieceAt(i, j, 0);
			}
		}
		
		assertTrue("Should have no pieces, but have " + game4.getNumPieces(),
				0 == game4.getNumPieces());		
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				game4.addPiece();
			}
		}
		
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				assertTrue("Should have value 2 or 4, but is " + game4.getPieceAt(i,j),
						2 == game4.getPieceAt(i,j) || 4 == game4.getPieceAt(i,j));
			}
		}
	}
	
	
	@Test(expected = AssertionError.class)
	public void testaddPieceFull1() {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				game1.setPieceAt(i, j, 2);
			}
		}
		
		assertTrue("Should have no free space",
				false == game1.isSpaceLeft());		
		
		game1.addPiece();
	}
	
	@Test(expected = AssertionError.class)
	public void testaddPieceFull2() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				game4.setPieceAt(i, j, 2);
			}
		}
		
		assertTrue("Should have no free space",
				false == game4.isSpaceLeft());		
		
		game4.addPiece();
	}

	
	// Tests for public int getNumPieces();
	@Test
	public void testgetNumPieces() {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				game1.setPieceAt(i, j, 0);
			}
		}
		
		assertTrue("Should have no pieces, but have " + game1.getNumPieces(),
				0 == game1.getNumPieces());		
		
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				game1.addPiece();
				assertTrue("Wrong number of pieces",
						1 + i * 4 + j == game1.getNumPieces());	
			}
		}
		
		
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				game4.setPieceAt(i, j, 0);
			}
		}
		
		assertTrue("Should have no pieces, but have " + game4.getNumPieces(),
				0 == game4.getNumPieces());		
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				game4.addPiece();
				assertTrue("Wrong number of pieces",
						1 + i * 9 + j == game4.getNumPieces());	
			}
		}
	}
		

	// Tests for public int getPieceAt(int x, int y);
	@Test
	public void testgetPieceAt() {	
		
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 8; j++) {
				game2.setPieceAt(i, j, 0);
				assertTrue("Should be 0, but is" + game2.getPieceAt(i, j),
						0 == game2.getPieceAt(i, j));	
			}
		}
				
		
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 8; j++) {
				game2.setPieceAt(i, j, 1 + 8 * i + j);
				assertTrue("Should be " + (1 + 8 * i + j) + ", but is " + game2.getPieceAt(i, j),
						1 + 8 * i + j == game2.getPieceAt(i, j));	
			}
		}
	}

	// Tests for public void setPieceAt(int x, int y, int piece);
	@Test
	public void testsetPieceAt() {	
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 3; j++) {
				game3.setPieceAt(i, j, 0);
				assertTrue("Should be 0, but is" + game3.getPieceAt(i, j),
						0 == game3.getPieceAt(i, j));	
			}
		}
				
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 3; j++) {
				game3.setPieceAt(i, j, 1 + 3 * i + j);
				assertTrue("Should be " + (1 + 3 * i + j) + ", but is " + game3.getPieceAt(i, j),
						1 + 3 * i + j == game3.getPieceAt(i, j));	
			}
		}
	}
	

	// Tests for public boolean isMovePossible();
	@Test
	public void testIsMovePossibleInitial() {
		assertTrue("Initially a move should be possible",
				true == game1.isMovePossible());
		assertTrue("Initially a move should be possible",
				true == game2.isMovePossible());
		assertTrue("Initially a move should be possible",
				true == game3.isMovePossible());
		assertTrue("Initially a move should be possible",
				true == game4.isMovePossible());
		assertTrue("Initially a move should be possible",
				true == game5.isMovePossible());
	}

	@Test
	public void testIsMovePossibleEmpty() {	
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 3; j++) {
				game3.setPieceAt(i, j, 0);
			}
		}
				
		assertTrue("Move on empty board should be impossible",
				false == game3.isMovePossible());
	}

	@Test
	public void testIsMovePossibleFull() {	
		
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				game5.setPieceAt(i, j, 1 + 2 * i + j);
			}
		}
				
		assertTrue("Move on full board with pairwise distinc tiles should be impossible",
				false == game5.isMovePossible());
	}
	

	// Tests for public boolean isSpaceLeft();
	@Test
	public void testIsSpaceLeftInitial() {
		assertTrue("Initially there should be space left",
				true == game1.isSpaceLeft());
		assertTrue("Initially there should be space left",
				true == game2.isSpaceLeft());
		assertTrue("Initially there should be space left",
				true == game3.isSpaceLeft());
		assertTrue("Initially there should be space left",
				true == game4.isSpaceLeft());
		assertTrue("Initially there should be space left",
				true == game5.isSpaceLeft());
	}

	@Test
	public void testIsSpaceLeftEmpty() {	
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 3; j++) {
				game3.setPieceAt(i, j, 0);
				assertTrue("There should be space left",
						true == game3.isSpaceLeft());
			}
		}
				
		
	}

	@Test
	public void testIsSpaceLeftFull() {	
		
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				assertTrue("There should be space left",
						true == game5.isSpaceLeft());
				game5.setPieceAt(i, j, 1 + 2 * i + j);
			}
		}
				
		assertTrue("There should be no more space left",
				false == game5.isSpaceLeft());
	}


	// Tests for public int getPoints();
	@Test
	public void testGetPointsInitial() {
		assertEquals("The initial game did not have zero points", 0,
				game1.getPoints());
		assertEquals("The initial game did not have zero points", 0,
				game2.getPoints());
		assertEquals("The initial game did not have zero points", 0,
				game3.getPoints());
		assertEquals("The initial game did not have zero points", 0,
				game4.getPoints());
		assertEquals("The initial game did not have zero points", 0,
				game5.getPoints());
	}

	
	@Test
	public void testGetPointsSingleMerge() {
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 3; j++) {
				game3.setPieceAt(i, j, 0);
			}
		}
		game3.setPieceAt(0, 0, 2048);
		game3.setPieceAt(0, 1, 2048);
		game3.performMove(MoveDirection.NORTH);
		
		assertEquals("Expected 4096 points", 4096, game3.getPoints());
	}
	
	@Test
	public void testGetPointsNotPowerOfTwo() {
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 3; j++) {
				game3.setPieceAt(i, j, 0);
			}
		}
		game3.setPieceAt(0, 0, 7);
		game3.setPieceAt(0, 1, 7);
		game3.performMove(MoveDirection.NORTH);
		
		assertEquals("Expected 14 points", 14, game3.getPoints());
	}
	
	@Test
	public void testGetPointsMultipleMerges() {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				game1.setPieceAt(i, j, 2);
			}
		}
		game1.performMove(MoveDirection.NORTH);
		
		assertEquals("Expected 32 points", 32, game1.getPoints());
	}
	

	// Tests for public boolean performMove(MoveDirection direction);
	@Test
	public void testPerformMoveMoving() {
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 8; j++) {
				game2.setPieceAt(i, j, 0);
			}
		}

		assertTrue("Empty board, no move should be possible", false == game2.performMove(MoveDirection.NORTH));
		assertTrue("Empty board, no move should be possible", false == game2.performMove(MoveDirection.EAST));
		assertTrue("Empty board, no move should be possible", false == game2.performMove(MoveDirection.SOUTH));
		assertTrue("Empty board, no move should be possible", false == game2.performMove(MoveDirection.WEST));
				
		game2.setPieceAt(0, 0, 7);
		// Go in a circle around the board
		assertTrue("Only piece at upper left corner", false == game2.performMove(MoveDirection.NORTH));
		assertTrue("Only piece at upper left corner", false == game2.performMove(MoveDirection.WEST));
		assertTrue("Only piece at upper left corner", true == game2.performMove(MoveDirection.EAST));

		assertTrue("Only piece at upper left corner", false == game2.performMove(MoveDirection.NORTH));
		assertTrue("Only piece at upper left corner", false == game2.performMove(MoveDirection.EAST));
		assertTrue("Only piece at upper left corner", true == game2.performMove(MoveDirection.SOUTH));	

		assertTrue("Only piece at upper left corner", false == game2.performMove(MoveDirection.SOUTH));
		assertTrue("Only piece at upper left corner", false == game2.performMove(MoveDirection.EAST));
		assertTrue("Only piece at upper left corner", true == game2.performMove(MoveDirection.WEST));
		
		assertTrue("Only piece at upper left corner", false == game2.performMove(MoveDirection.SOUTH));
		assertTrue("Only piece at upper left corner", false == game2.performMove(MoveDirection.WEST));
		assertTrue("Only piece at upper left corner", true == game2.performMove(MoveDirection.NORTH));		
	}
	
	
	@Test
	public void testPerformMoveFull() {
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				game5.setPieceAt(i, j, 1 + i * 2 + j);
			}
		}
		
		assertTrue("Full board, no move should be possible", false == game5.performMove(MoveDirection.NORTH));
		assertTrue("Full board, no move should be possible", false == game5.performMove(MoveDirection.EAST));
		assertTrue("Full board, no move should be possible", false == game5.performMove(MoveDirection.SOUTH));
		assertTrue("Full board, no move should be possible", false == game5.performMove(MoveDirection.WEST));
	}
	
	
	@Test
	public void testPerformMoveMergeBehaviour1() {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				game1.setPieceAt(i,j,0);
			}
		}

		game1.setPieceAt(0,0,2);
		game1.setPieceAt(1,0,2);
		game1.setPieceAt(2,0,4);
		game1.performMove(MoveDirection.WEST);
		
		assertTrue("Only one merge should have happened!" + game1.getPieceAt(0,0) + "," + game1.getPieceAt(1,0), 
				game1.getPieceAt(0,0) == 4 && game1.getPieceAt(1,0) == 4 );
	}

	
	@Test
	public void testPerformMoveMergeBehaviour2() {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				game1.setPieceAt(i,j,0);
			}
		}

		game1.setPieceAt(0,0,4);
		game1.setPieceAt(1,0,2);
		game1.setPieceAt(2,0,2);
		game1.performMove(MoveDirection.WEST);
		
		assertTrue("Only one merge should have happened!" + game1.getPieceAt(0,0) + "," + game1.getPieceAt(1,0), 
				game1.getPieceAt(0,0) == 4 && game1.getPieceAt(1,0) == 4 );
	}
	
	
	@Test
	public void testPerformMoveMergeNORTH() {
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				game5.setPieceAt(i, j, 2);
			}
		}
		
		assertTrue("Merging should be possible", true == game5.performMove(MoveDirection.NORTH));
	}
	
	
	@Test
	public void testPerformMoveMergeEAST() {
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				game5.setPieceAt(i, j, -2);
			}
		}
		
		assertTrue("Merging should be possible", true == game5.performMove(MoveDirection.EAST));
	}
	
	
	@Test
	public void testPerformMoveMergeSOUTH() {
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				game5.setPieceAt(i, j, 4096);
			}
		}
		
		assertTrue("Merging should be possible", true == game5.performMove(MoveDirection.SOUTH));
	}

	
	@Test
	public void testPerformMoveMergeWEST() {
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				game5.setPieceAt(i, j, 5000);
			}
		}
		
		assertTrue("Merging should be possible", true == game5.performMove(MoveDirection.WEST));
	}
	
	
	@Test
	public void testPerformMoveMergeCornerNORTH() {
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 3; j++) {
				game3.setPieceAt(i, j, 1 + i * 3 + j);
			}
		}
		
		assertTrue("Merging should not be possible", false == game3.performMove(MoveDirection.NORTH));
		
		game3.setPieceAt(0, 1, 1);
		assertTrue("Merging should be possible", true == game3.performMove(MoveDirection.NORTH));
	}
	
	
	@Test
	public void testPerformMoveMergeCornerEAST() {
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 3; j++) {
				game3.setPieceAt(i, j, 1 + i * 3 + j);
			}
		}
		
		assertTrue("Merging should not be possible", false == game3.performMove(MoveDirection.EAST));
		
		game3.setPieceAt(3, 0, 13);
		assertTrue("Merging should be possible", true == game3.performMove(MoveDirection.EAST));
	}
	
	
	@Test
	public void testPerformMoveMergeCornerSOUTH() {
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 3; j++) {
				game3.setPieceAt(i, j, 1 + i * 3 + j);
			}
		}
		
		assertTrue("Merging should not be possible", false == game3.performMove(MoveDirection.SOUTH));
		
		game3.setPieceAt(4, 1, 15);
		assertTrue("Merging should be possible", true == game3.performMove(MoveDirection.SOUTH));
	}
	
	
	@Test
	public void testPerformMoveMergeCornerWEST() {
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 3; j++) {
				game3.setPieceAt(i, j, 1 + i * 3 + j);
			}
		}
		
		assertTrue("Merging should not be possible", false == game3.performMove(MoveDirection.WEST));
		
		game3.setPieceAt(1, 2, 3);
		assertTrue("Merging should be possible", true == game3.performMove(MoveDirection.WEST));
	}
	
	
	// Tests for public boolean isMovePossible(MoveDirection direction);
		@Test
		public void testIsMovePossibleDirection() {
			for (int i = 0; i < 2; i++) {
				for (int j = 0; j < 8; j++) {
					game2.setPieceAt(i, j, 0);
				}
			}

			assertTrue("Empty board, no move should be possible", false == game2.isMovePossible(MoveDirection.NORTH));
			assertTrue("Empty board, no move should be possible", false == game2.isMovePossible(MoveDirection.EAST));
			assertTrue("Empty board, no move should be possible", false == game2.isMovePossible(MoveDirection.SOUTH));
			assertTrue("Empty board, no move should be possible", false == game2.isMovePossible(MoveDirection.WEST));
					
			game2.setPieceAt(0, 0, 7);
			assertTrue("Only piece at upper left corner", false == game2.isMovePossible(MoveDirection.NORTH));
			assertTrue("Only piece at upper left corner", true == game2.isMovePossible(MoveDirection.EAST));
			assertTrue("Only piece at upper left corner", true == game2.isMovePossible(MoveDirection.SOUTH));
			assertTrue("Only piece at upper left corner", false == game2.isMovePossible(MoveDirection.WEST));
			
			game2.setPieceAt(0, 0, 0);
			game2.setPieceAt(1, 7, -3);
			assertTrue("Only piece at lower right corner", true == game2.isMovePossible(MoveDirection.NORTH));
			assertTrue("Only piece at lower right corner", false == game2.isMovePossible(MoveDirection.EAST));
			assertTrue("Only piece at lower right corner", false == game2.isMovePossible(MoveDirection.SOUTH));
			assertTrue("Only piece at lower right corner", true == game2.isMovePossible(MoveDirection.WEST));
			
			game2.setPieceAt(0, 0, 7);
			assertTrue("All directions should be possible", true == game2.isMovePossible(MoveDirection.NORTH));
			assertTrue("All directions should be possible", true == game2.isMovePossible(MoveDirection.EAST));
			assertTrue("All directions should be possible", true == game2.isMovePossible(MoveDirection.SOUTH));
			assertTrue("All directions should be possible", true == game2.isMovePossible(MoveDirection.WEST));
		}
		
		
		@Test
		public void testIsMovePossibleDirectionFull() {
			for (int i = 0; i < 2; i++) {
				for (int j = 0; j < 8; j++) {
					game2.setPieceAt(i, j, 1 + i * 8 + j);
				}
			}
			
			assertTrue("Full board, no move should be possible", false == game2.isMovePossible(MoveDirection.NORTH));
			assertTrue("Full board, no move should be possible", false == game2.isMovePossible(MoveDirection.EAST));
			assertTrue("Full board, no move should be possible", false == game2.isMovePossible(MoveDirection.SOUTH));
			assertTrue("Full board, no move should be possible", false == game2.isMovePossible(MoveDirection.WEST));
		}
		
		
		
		public void testisMovePossibleMoving() {
			for (int i = 0; i < 2; i++) {
				for (int j = 0; j < 8; j++) {
					game2.setPieceAt(i, j, 0);
				}
			}

			assertTrue("Empty board, no move should be possible", false == game2.isMovePossible(MoveDirection.NORTH));
			assertTrue("Empty board, no move should be possible", false == game2.isMovePossible(MoveDirection.EAST));
			assertTrue("Empty board, no move should be possible", false == game2.isMovePossible(MoveDirection.SOUTH));
			assertTrue("Empty board, no move should be possible", false == game2.isMovePossible(MoveDirection.WEST));
			assertTrue("Empty board, no move should be possible", false == game2.isMovePossible());
					
			game2.setPieceAt(0, 0, 7);
			// Go in a circle around the board
			assertTrue("Only piece at upper left corner", false == game2.isMovePossible(MoveDirection.NORTH));
			assertTrue("Only piece at upper left corner", false == game2.isMovePossible(MoveDirection.WEST));
			assertTrue("Only piece at upper left corner", true == game2.isMovePossible(MoveDirection.EAST));
			assertTrue("Only piece at upper left corner", true == game2.isMovePossible());
			game2.performMove(MoveDirection.EAST);

			assertTrue("Only piece at upper left corner", false == game2.isMovePossible(MoveDirection.NORTH));
			assertTrue("Only piece at upper left corner", false == game2.isMovePossible(MoveDirection.EAST));
			assertTrue("Only piece at upper left corner", true == game2.isMovePossible(MoveDirection.SOUTH));
			assertTrue("Only piece at upper left corner", true == game2.isMovePossible());
			game2.performMove(MoveDirection.SOUTH);	

			assertTrue("Only piece at upper left corner", false == game2.isMovePossible(MoveDirection.SOUTH));
			assertTrue("Only piece at upper left corner", false == game2.isMovePossible(MoveDirection.EAST));
			assertTrue("Only piece at upper left corner", true == game2.isMovePossible(MoveDirection.WEST));
			assertTrue("Only piece at upper left corner", true == game2.isMovePossible());
			game2.performMove(MoveDirection.WEST);
			
			assertTrue("Only piece at upper left corner", false == game2.isMovePossible(MoveDirection.SOUTH));
			assertTrue("Only piece at upper left corner", false == game2.isMovePossible(MoveDirection.WEST));
			assertTrue("Only piece at upper left corner", true == game2.isMovePossible(MoveDirection.NORTH));
			assertTrue("Only piece at upper left corner", true == game2.isMovePossible());		
		}
		
		
		@Test
		public void testisMovePossibleMergeNORTH() {
			for (int i = 0; i < 2; i++) {
				for (int j = 0; j < 2; j++) {
					game5.setPieceAt(i, j, 2);
				}
			}

			assertTrue("Merging should be possible", true == game5.isMovePossible(MoveDirection.NORTH));
			assertTrue("Merging should be possible", true == game5.isMovePossible());
		}
		
		
		@Test
		public void testisMovePossibleMergeEAST() {
			for (int i = 0; i < 2; i++) {
				for (int j = 0; j < 2; j++) {
					game5.setPieceAt(i, j, -2);
				}
			}

			assertTrue("Merging should be possible", true == game5.isMovePossible(MoveDirection.EAST));
			assertTrue("Merging should be possible", true == game5.isMovePossible());
		}
		
		
		@Test
		public void testisMovePossibleMergeSOUTH() {
			for (int i = 0; i < 2; i++) {
				for (int j = 0; j < 2; j++) {
					game5.setPieceAt(i, j, 4096);
				}
			}

			assertTrue("Merging should be possible", true == game5.isMovePossible(MoveDirection.SOUTH));
			assertTrue("Merging should be possible", true == game5.isMovePossible());
		}

		
		@Test
		public void testisMovePossibleMergeWEST() {
			for (int i = 0; i < 2; i++) {
				for (int j = 0; j < 2; j++) {
					game5.setPieceAt(i, j, 5000);
				}
			}

			assertTrue("Merging should be possible", true == game5.isMovePossible(MoveDirection.WEST));
			assertTrue("Merging should be possible", true == game5.isMovePossible());
		}
		
		
		@Test
		public void testisMovePossibleMergeCornerNORTH() {
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 3; j++) {
					game3.setPieceAt(i, j, 1 + i * 3 + j);
				}
			}

			assertTrue("Merging should not be possible", false == game3.isMovePossible(MoveDirection.NORTH));
			assertTrue("Merging should not be possible", false == game3.isMovePossible());
			
			game3.setPieceAt(0, 1, 1);
			assertTrue("Merging should be possible", true == game3.isMovePossible(MoveDirection.NORTH));
			assertTrue("Merging should be possible", true == game3.isMovePossible());
		}
		
		
		@Test
		public void testisMovePossibleMergeCornerEAST() {
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 3; j++) {
					game3.setPieceAt(i, j, 1 + i * 3 + j);
				}
			}

			assertTrue("Merging should not be possible", false == game3.isMovePossible(MoveDirection.EAST));
			assertTrue("Merging should not be possible", false == game3.isMovePossible());
			
			game3.setPieceAt(3, 0, 13);
			assertTrue("Merging should be possible", true == game3.isMovePossible(MoveDirection.EAST));
			assertTrue("Merging should be possible", true == game3.isMovePossible());
		}
		
		
		@Test
		public void testisMovePossibleMergeCornerSOUTH() {
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 3; j++) {
					game3.setPieceAt(i, j, 1 + i * 3 + j);
				}
			}

			assertTrue("Merging should not be possible", false == game3.isMovePossible(MoveDirection.SOUTH));
			assertTrue("Merging should not be possible", false == game3.isMovePossible());
			
			game3.setPieceAt(4, 1, 15);
			assertTrue("Merging should be possible", true == game3.isMovePossible(MoveDirection.SOUTH));
			assertTrue("Merging should be possible", true == game3.isMovePossible());
		}
		
		
		@Test
		public void testisMovePossibleMergeCornerWEST() {
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 3; j++) {
					game3.setPieceAt(i, j, 1 + i * 3 + j);
				}
			}

			assertTrue("Merging should not be possible", false == game3.isMovePossible(MoveDirection.WEST));
			assertTrue("Merging should not be possible", false == game3.isMovePossible());
			
			game3.setPieceAt(1, 2, 3);
			assertTrue("Merging should be possible", true == game3.isMovePossible(MoveDirection.WEST));
			assertTrue("Merging should be possible", true == game3.isMovePossible());
		}
}