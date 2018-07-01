package routing;

import java.util.Iterator;
import java.util.ArrayList;

public class NodeImpl implements Node {
	private final long ID;						// OSM ID, has to be non-negative
	private final Coordinate coordinate;		// Coordinates of the node
	private ArrayList<Edge> edges;				// List of outgoing edges
	

	public NodeImpl(long ID, double latitude, double longitude) {
		this.ID = ID;
		this.coordinate = new Coordinate(latitude, longitude);
		this.edges = new ArrayList<Edge>();
	}
	
	@Override
	public Coordinate getCoordinate() {
		return coordinate;
	}

	@Override
	public Edge getEdge(int idx) {
		if (idx < 1 && idx > edges.size()) {
			throw new IndexOutOfBoundsException();
		}
		
		return edges.get( idx );
	}

	@Override
	public long getId() {
		assert ID >= 0;
		return ID;
	}

	@Override
	public Iterator<Edge> iterator() {
		return edges.iterator();
	}

	@Override
	public int numEdges() {
		return edges.size();
	}

	@Override
	public void addEdge(Edge e) {
		edges.add(e);
	}

	@Override
	public void removeEdge(int i) {
		edges.remove(i);
	}

}
