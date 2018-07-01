package routing;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class RoutingAlgorithmBase implements RoutingAlgorithm {

	@Override
	public Route computeRoute(Graph g, List<Node> nodes, TravelType tt) throws NoSuchRouteException {
		List<RouteLeg> legs = new ArrayList<RouteLeg>();		
		
		Iterator<Node> it = nodes.iterator();
		Node start = it.next();
		Node end;
		
		while( it.hasNext() ) {
			end = it.next();
			legs.add( computeRouteLeg(g, start, end, tt) );
			start = end;
		}		
		
		Route route = new RouteImpl(legs, tt);
		return route ;
	}

	@Override
	public RouteLeg computeRouteLeg(Graph g, long startId, long endId, TravelType tt) throws NoSuchRouteException {
		return computeRouteLeg(g, g.getNode(startId), g.getNode(endId), tt);
	}
}
