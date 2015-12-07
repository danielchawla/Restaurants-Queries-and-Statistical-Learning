package ca.ece.ubc.cpen221.mp5;

public class Main{

	private static final int PORT = 5555;

	public static void main (String[] args){
		new RestaurantDBServer(PORT, "restaurants.json", "reviews.json", "users.json");
	}
}
