package ca.ece.ubc.cpen221.mp5;

public class Main{

	public static void main (String[] args){

		//RestaurantDBServer(4444 data/restaurants.json data/reviews.json data/users.json)
		new RestaurantDBServer(Integer.parseInt(args[0]), args[1], args[2], args[3]);
	}
}