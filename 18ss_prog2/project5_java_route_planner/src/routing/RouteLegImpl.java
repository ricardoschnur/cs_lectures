package routing;

import java.util.Iterator;
import java.util.List;

public class RouteLegImpl extends RouteLegBase implements RouteLeg {
	private final List<Node> nodes;

	
	public RouteLegImpl(List<Node> nodes) {
		this.nodes = nodes;
	}
	
	
	@Override
	public double getDistance() {
		double distance = 0;
		Iterator<Node> it = iterator();
		Node previous = it.next();
		Node next;
		
		while( it.hasNext() ) {
			next = it.next();
			distance += previous.getCoordinate().getDistance( next.getCoordinate() );
			previous = next;
		}		
		
		return distance;
	}

	@Override
	public Node getEndNode() {
		return nodes.get( size() - 1 );
	}

	@Override
	public Node getStartNode() {
		return nodes.get(0);
	}

	@Override
	public Iterator<Node> iterator() {
		return nodes.iterator();
	}

	@Override
	public int size() {
		return nodes.size();
	}

}
