package ca.ece.ubc.cpen221.mp5;

/**
 * Represents a location parameterized in latitude and longitude.
 * @author Ryan Cotsakis and Daniel Chawla
 */
public class Location{
    
	private static final double CLOSE = 0.001;
	private final double latitude;
	private final double longitude;

	/**
	 * Location constructor.
	 * @param latitude
	 * @param longitude
	 */
	public Location (double latitude, double longitude){
	    // Rep invariant: latitude and longitude represent locations on earth.
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	/**
	 * Retrieves location latitude.
	 * @return latitude.
	 */
	public double getLat(){
		return latitude;
	}
	
	/**
	 * Retrieves location longitude.
	 * @return longitude
	 */
	public double getLong(){
		return longitude;
	}
	
	/**
	 * Determines if location is "close" to another location
	 * @param other location
	 * @return true if location is close, false otherwise
	 */
	public boolean isCloseTo(Location there){
		if(Math.abs(there.getLong()-longitude)<CLOSE && Math.abs(there.getLat()-latitude)<CLOSE) return true;
		return false;
	}

	@Override
	public Location clone(){
		return new Location(latitude,longitude);
	}
	
	@Override
	public boolean equals(Object that){
		if (!(that instanceof Location)) return false;
		Location location = (Location) that;
		if(latitude == location.getLat() && longitude == location.getLong()) return true;
		return false;
	}
	
	@Override
	public int hashCode(){
		return (int) (latitude + 271 * longitude);
	}
}
