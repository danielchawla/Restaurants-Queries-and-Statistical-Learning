package ca.ece.ubc.cpen221.mp5;

public class Location{
	
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
	
	public Location clone(){
		return new Location(latitude,longitude);
	}
}
