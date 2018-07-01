package routing;

public class EdgeImpl implements Edge {
	private final Node start;
	private final Node end;
	private final double length;
	private final long traversable;
	private static final long goByCar = 1 << 5;
	private static final long comeByCar = 1 << 4;
	private static final long goByBike = 1 << 3;
	private static final long comeByBike = 1 << 2;
	private static final long goByFoot = 1 << 1;
	private static final long comeByFoot = 1;
	/*
	 * The 6 bits of traversable are 1 if the respective 
	 * travel method is possible, 0 otherwise
	 * 		go by car
	 *		come by car
	 *		go by bike
	 *		come by bike
	 *		go by foot
	 *		come by foot
	 */
	
	// The values of goX and comeX have to be 1 or 0
	public EdgeImpl(Node start, Node end, double length, 
			long goCar, long comeCar, long goBike, long comeBike, long goFoot, long comeFoot) {
		this.start = start;
		this.end = end;
		this.length = length;

		long temp = 0;
		temp |= (goCar << 5);
		temp |= (comeCar << 4);
		temp |= (goBike << 3);
		temp |= (comeBike << 2);
		temp |= (goFoot << 1);
		temp |= (comeFoot);
		
		this.traversable = temp;
	}
	
	
	
	// @return True, iff the travel type is allowed on this edge in direction D
	@Override
	public boolean allowsTravelType(TravelType tt, Direction dir) {
		switch (tt) {
		case CAR:
			return allowsCar(dir);
		case BIKE:
			return allowsBike(dir);
		case FOOT:
			return allowsFoot(dir);
		default:
			// this is the ANY case
			return allowsCar(dir) || allowsBike(dir) || allowsFoot(dir);
		}
	}

	@Override
	public Node getEnd() {
		return end;
	}

	@Override
	public double getLength() {
		return length;
	}

	@Override
	public Node getStart() {
		return start;
	}

	// Return true iff travel by car is allows in direction dir
	private boolean allowsCar(Direction dir) {
		switch(dir) {
		case FORWARD:
			return (traversable & goByCar) == goByCar;
		case BACKWARD:
			return (traversable & comeByCar) == comeByCar;
		default:
			// this is the ANY case
			return (traversable & goByCar) == goByCar  || (traversable & comeByCar) == comeByCar;
		}
	}

	// Return true iff travel by Bike is allows in direction dir
	private boolean allowsBike(Direction dir) {
		switch(dir) {
		case FORWARD:
			return (traversable & goByBike) == goByBike;
		case BACKWARD:
			return (traversable & comeByBike) == comeByBike;
		default:
			// this is the ANY case
			return (traversable & goByBike) == goByBike  || (traversable & comeByBike) == comeByBike;
		}
	}

	// Return true iff travel by Foot is allows in direction dir
	private boolean allowsFoot(Direction dir) {
		switch(dir) {
		case FORWARD:
			return (traversable & goByFoot) == goByFoot;
		case BACKWARD:
			return (traversable & comeByFoot) == comeByFoot;
		default:
			// this is the ANY case
			return (traversable & goByFoot) == goByFoot  || (traversable & comeByFoot) == comeByFoot;
		}
	}

}
