package routing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class RoutingAlgorithmDijkstraHeuristic extends RoutingAlgorithmBase implements RoutingAlgorithm {
	private Node current = null;
	private Map<Node,Node> predecessor = new HashMap<Node,Node>();
    private Map<Node,Double> dist = new HashMap<Node,Double>();
	
    
    private class NodeComparator implements Comparator<Node> {				
    	@Override
    	public int compare(Node n1, Node n2) {
    		if ( !dist.containsKey(n1) ) {
    			dist.put(n1, Double.POSITIVE_INFINITY);
    		}
    		if ( !dist.containsKey(n2) ) {
    			dist.put(n2, Double.POSITIVE_INFINITY);
    		}
    		
    		double w1 = dist.get(n1);
    		double w2 = dist.get(n2);
    		
    		if ( w1 < w2 ) {
    			return -1;
    		}
    		if ( w1 > w2 ) {
    			return 1;
    		}
    		
    		return 0;
    	}    		       
    }
	

	@Override
	public RouteLeg computeRouteLeg(Graph G, Node start_node, Node end_node, TravelType TT)
			throws NoSuchRouteException {
		Comparator<Node> comparator = new NodeComparator();
        Queue<Node> queue = new PriorityQueue<Node>(comparator);
        
                
        current = start_node; 
        assert current != null;
        
        predecessor.put(start_node, null);
        dist.put(start_node,  0.0);
		queue.offer(start_node);

		while ( !queue.isEmpty() ) {
			current = queue.poll();
			
			// Desitnation?
			if ( current.equals(end_node) ) {
				List<Node> nodes = new ArrayList<Node>();
				while ( current != null ) {
					nodes.add(current);
					current = predecessor.get(current);
				}
				
				Collections.reverse(nodes);
				
				RouteLeg leg = new RouteLegImpl( nodes );
				return leg;
			}
			
			for (Edge edge : current) {
				Node next = edge.getEnd();
				if ( !dist.containsKey(next) ) {
	    			dist.put(next, Double.POSITIVE_INFINITY);
	    		}
				
				double currDist = dist.get(next);
				double newDist = dist.get(current) + edge.getLength();
				
				if ( newDist < currDist ) {
					if ( currDist < Double.POSITIVE_INFINITY ) {
						queue.remove(next);
					}
					
					predecessor.put(next, current);
					dist.put(next, newDist);
					queue.add(next);
				}
			}
		}
		
		return null;
	}

	@Override
	public boolean isBidirectional() {
		return false;
	}

}
