package ca.ece.ubc.cpen221.mp5;

public class Location{
	private static final double CLOSE = 0.001;
	
	private final double latitude;
	private final double longitude;

	public Location (double latitude, double longitude){
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public double getLat(){
		return latitude;
	}
	
	public double getLong(){
		return longitude;
	}
	
	
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
