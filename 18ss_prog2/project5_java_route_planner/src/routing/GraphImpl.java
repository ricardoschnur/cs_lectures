package routing;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class GraphImpl implements Graph {
	private Map<Long, Node> nodes = new HashMap<Long, Node>();
	private double minLat;
	private double maxLat;
	private double minLon;
	private double maxLon;

	public GraphImpl() {
		this.minLat = Coordinate.MAX_LAT;
		this.maxLon = Coordinate.MIN_LAT;
		this.minLat = Coordinate.MAX_LON;
		this.maxLon = Coordinate.MIN_LON;
	}
	
	@Override
	public Node getNode(long id) {
		return nodes.get(id);
	}

	@Override
	public Coordinate getNWCoordinate() {
		Coordinate coord = new Coordinate(maxLat, maxLon);
		return coord;
	}

	@Override
	public Coordinate getSECoordinate() {
		Coordinate coord = new Coordinate(minLat, minLon);
		return coord;
	}

	@Override
	public Iterator<Node> iterator() {
		return nodes.values().iterator();
	}

	@Override
	public int numEdges() {
		int i = 0;
		
		for (Node n : nodes.values()) {
			for (@SuppressWarnings("unused") Edge e : n) {
				i++;
			}
		}
		
		return i;
	}

	@Override
	public int numNodes() {
		return nodes.size();
	}

	@Override
	public int removeIsolatedNodes() {
		int removed = 0;
		long ID;
		
		for (Node node: this) {
			ID = node.getId();
			if ( node.numEdges() == 0 ) {
				nodes.remove(ID);
				++removed;
			}
		}
		
		return removed;
	}

	@Override
	public int removeUntraversableEdges(RoutingAlgorithm ra, TravelType tt) {
		int removed = 0;
		Direction  dir = ra.isBidirectional() ? Direction.ANY : Direction.FORWARD;
		
		for (Node node : this) {
			for(int i = 0; i < node.numEdges(); i++) {
				if( !node.getEdge(i).allowsTravelType(tt, dir) ) {
					node.removeEdge(i);
					++removed;
				}
			}
		}
		
		return removed;	
	}

	@Override
	public boolean isOverlayGraph() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Node getNodeInUnderlyingGraph(long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static Graph fromFile(String fileName) throws IOException {		
		try {
			Scanner sc = new Scanner(new File(fileName));
			GraphImpl G = new GraphImpl();
			String s;
			
			while ( sc.hasNext() ) {
				s = sc.next();
				
				if ( Objects.equals("N", s) ) {
					G.readNode(sc);
				}
				
				if ( Objects.equals("E", s) ) {
					G.readEdge(sc);
				}	
				
				s = " ";
			}
			
			sc.close();
			return (Graph)G;			
		} catch (FileNotFoundException e) {
			throw new IOException();
		}
	}

	private void readNode(Scanner sc) {
		long ID = sc.nextLong();
		double latitude = sc.nextDouble();
		double longitude = sc.nextDouble();
		
		// Add node
		Node node = new NodeImpl(ID, latitude, longitude);
		nodes.put(ID, node);
		
		// Change min / max values if necessary
		minLat = latitude < minLat ? latitude : minLat;
		maxLat = latitude > maxLat ? latitude : maxLat;
		minLon = longitude < minLon ? longitude : minLon;
		maxLon = longitude > maxLon ? longitude : maxLon;		
	}

	private void readEdge(Scanner sc) {
		long idStart = sc.nextLong();
		long idEnd = sc.nextLong();
		int goCar = sc.nextInt();
		int comeCar = sc.nextInt();
		int goBike = sc.nextInt();
		int comeBike = sc.nextInt();
		int goFoot = sc.nextInt();
		int comeFoot = sc.nextInt();
		
		// Find nodes
		Node start = nodes.get(idStart);
		Node end = nodes.get(idEnd);
		
		// Calculate length of the edge
		double length = start.getCoordinate().getDistance( end.getCoordinate() );
		
		// Add two edges, one for each direction
		Edge e1 = new EdgeImpl(start, end, length, goCar, comeCar, goBike, comeBike, goFoot, comeFoot);
		Edge e2 = new EdgeImpl(end, start, length, comeCar, goCar, comeBike, goBike, comeFoot, goFoot);
		
		// Add edges to respective nodes
		start.addEdge(e1);
		end.addEdge(e2);
	}
}
