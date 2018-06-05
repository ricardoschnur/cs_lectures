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
	// TODO IMPLEMENT ME
	

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
	

	// Tests for public boolean isMovePossible(MoveDirection direction);
	// TODO IMPLEMENT ME
	

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
	

	// Tests for public boolean performMove(MoveDirection direction);
	// TODO IMPLEMENT ME
}