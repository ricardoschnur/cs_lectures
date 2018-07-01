package routing;

import java.util.ArrayList;
import java.util.List;

public class NodeFinderImpl implements NodeFinder {
	private final static double RATIO = 100; 
	private final Coordinate min;
	private final Coordinate max;
	private final int gridWidth;
	private final int gridHeight;
	private final double width;
	private final double height;	
	private List<List<List<Node>>> grid;
	private final int numNodes;
	
	public NodeFinderImpl(Graph G) {		
		// Calculate appropriate size of grid
		// On average, there should be less than RATIO nodes per field 
		this.numNodes = G.numNodes();
		int gridWidth = 1;
		int gridHeight = 1;
				
		while( RATIO > this.numNodes / ( gridWidth * gridHeight ) ) {
			if (gridWidth > gridHeight) {
				++gridWidth;
			}
			else {
				++gridHeight;
			}
		}
		
		this.gridWidth = gridWidth;
		this.gridHeight = gridHeight;

		// Get length of the sides of a box in the grid
		this.min = G.getSECoordinate();
		this.max = G.getNWCoordinate();
		
		this.width = ( this.max.getLongitude() - this.min.getLongitude() ) / this.gridWidth;
		this.height = ( this.max.getLatitude() - this.min.getLatitude() ) / this.gridHeight;
		
		// Initialize grid
		grid = new ArrayList<List<List<Node>>>();
		for (int i = 0; i < this.gridHeight; i++) {
			grid.add( new ArrayList<List<Node>>() );
			for (int j = 0; j < this.gridWidth; j++) {
				grid.get(i).add( new ArrayList<Node>() );
			}
		}
		
		// Add nodes to grid
		int x, y;
		for (Node node : G) {
			// Get indices
			x = (int) (( node.getCoordinate().getLongitude() - this.min.getLongitude() ) % width);
			y = (int) (( node.getCoordinate().getLatitude() - this.min.getLatitude() ) % height);
			
			// Check bounds
			x = x < 0 ? 0 : x;
			x = x >= this.gridWidth ? this.gridWidth - 1 : x;
			y = y < 0 ? 0 : y;
			y = y >= this.gridHeight ? this.gridHeight - 1 : y;
			
			// Place node
			grid.get(x).get(y).add(node);
		}

	}

	@Override
	public Node getNodeForCoordinates(Coordinate c) {
		Node closeNode = findCloseNode(c);
		double radius = closeNode.getCoordinate().getDistance(c);
		CoordinateBox box = c.computeBoundingBox(radius);
		
		// Find range in grid that has to be searched		
		int xmin =  (int) ( (box.getLowerBound().getLatitude() - radius ) % width );
		int xmax =  (int) ( (box.getLowerBound().getLongitude() + radius ) % width );
		int ymin =  (int) ( (box.getUpperBound().getLatitude() - radius ) % height );
		int ymax =  (int) ( (box.getUpperBound().getLongitude() + radius ) % height );	
		
		
		// Check bounds
		xmin = xmin < 0 ? 0 : xmin;
		xmax = xmax >= gridWidth ? gridWidth - 1 : xmax;
		ymin = ymin < 0 ? 0 : ymin;
		ymax = ymax >= gridHeight ? gridHeight - 1 : ymax;
		
		
		// Find closest node
		Node closest = closeNode;
		double distance = closest.getCoordinate().getDistance(c);
		double current;
				
		for (int i = xmin; i <= xmax; i++) {
			for (int j = ymin; j <= ymax; j++) {
				for (Node node : grid.get(i).get(j)) {
					current = node.getCoordinate().getDistance(c);
					if ( current < distance ) {
						distance = current;
						closest = node;
					}
				}
			}
		}
		
		return closest;
	}

	private Node findCloseNode(Coordinate c) {
		// Get box in which coordinate c lies
		int x = (int) (( c.getLongitude() - min.getLongitude() ) % width);
		int y = (int) (( c.getLatitude() - min.getLatitude() ) % height);
		
		// Check bounds
		x = x < 0 ? 0 : x;
		x = x >= gridWidth ? gridWidth - 1 : x;
		y = y < 0 ? 0 : y;
		y = y >= gridHeight ? gridHeight - 1 : y;
		
		// Box non-empty?
		if ( grid.get(x).get(y).size() > 0 ) {
			return grid.get(x).get(y).get(0);
		}
		
		// Go along a spiral around the current grid to find non-empty box
		int dx = 1;
		int dy = 0;
		int length = 1;
		int passed = 0;
		int temp;
		
		for(int n = 0; n < numNodes; n++ ) {
			// Go a step
			x += dx;
			y += dy;
			++passed;
			
			// Check bounds
			x = x < 0 ? 0 : x;
			x = x >= gridWidth ? gridWidth - 1 : x;
			y = y < 0 ? 0 : y;
			y = y >= gridHeight ? gridHeight - 1 : y;
			
			// Box non-empty?
			if ( grid.get(x).get(y).size() > 0 ) {
				return grid.get(x).get(y).get(0);
			}
			
			// Change direction
			if ( passed == length ) {
				passed = 0;
				
				// Rotate
				temp = dx;
				dx = - dy;
				dy = temp;
				
				// Increase length
				if ( dy == 0 ) {
					++length;
				}
			}
		}
		
		// Did not find any node
		return null;
	}
}
