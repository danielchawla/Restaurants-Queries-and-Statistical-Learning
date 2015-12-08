package ca.ece.ubc.cpen221.mp5;


public class Main{

	public static int PORT;
	
	public static void main (String[] args){

		PORT = Integer.parseInt(args[0]);
		//RestaurantDBServer(4444 data/restaurants.json data/reviews.json data/users.json)
		new RestaurantDBServer(PORT, args[1], args[2], args[3]);
	}
}