package ca.ece.ubc.cpen221.mp5;


public class Main{

	public static int PORT = 4444;
	
	public static void main (String[] args){
		//RestaurantDBServer(data/restaurants.json data/reviews.json data/users.json)
		new RestaurantDBServer(PORT, args[0], args[1], args[2]);
	}
}