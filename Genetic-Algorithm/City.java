package TSP_GA;

// Models a city
public class City {
	double x, y;
	int ref;

	// Constructs a city at chosen x, y location
	public City(int ref, double x, double y) {
		this.ref = ref;
		this.x = x;
		this.y = y;
	}

	public int getRef() {
		return this.ref;
	}

	// Get city's x coordinate
	public double getX() {
		return this.x;
	}

	// Get city's y coordinate
	public double getY() {
		return this.y;
	}

	// Get the distance to given city
	public double distanceTo(City city) {
		return haversine(getX(), getY(), city.getX(), city.getY());
	}

	@Override
	public String toString() {
		return getRef() + ".";
	}

	public static double haversine(double lat1, double lon1, double lat2,
			double lon2) {
		double dLat = Math.toRadians(lat2 - lat1) / 2;
		double dLon = Math.toRadians(lon2 - lon1) / 2;
		double a = Math.sin(dLat) * Math.sin(dLat)
				+ Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2)) * Math.sin(dLon)
				* Math.sin(dLon);

		return 12742 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	}
}
