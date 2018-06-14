package routing;

/**
 * The type of travel needed for routing process.
 */
public enum TravelType {
	ANY, // < Routing for all of the below
	BIKE, // < Routing for bikes
	CAR, // < Routing for cars
	FOOT // < Routing for pedestrians
}