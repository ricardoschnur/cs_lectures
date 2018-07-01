package routing;

import java.util.Iterator;
import java.util.List;

public class RouteImpl extends RouteBase implements Route {
	private final List<RouteLeg> legs;
	private final TravelType tt;
	
	public RouteImpl(List<RouteLeg> legs, TravelType tt) {
		this.legs = legs;
		this.tt = tt;
	}

	@Override
	public double distance() {
		double distance = 0;
		Iterator<RouteLeg> it = iterator();
		RouteLeg leg;
		
		while( it.hasNext() ) {
			leg = it.next();
			distance += leg.getDistance();
		}		
		
		return distance;
	}

	@Override
	public Node getEndNode() {
		return legs.get( size() - 1 ).getEndNode();
	}

	@Override
	public Node getStartNode() {
		return legs.get(0).getStartNode();
	}

	@Override
	public TravelType getTravelType() {
		return tt;
	}

	@Override
	public Iterator<RouteLeg> iterator() {
		return legs.iterator();
	}

	@Override
	public int size() {
		return legs.size();
	}

}
