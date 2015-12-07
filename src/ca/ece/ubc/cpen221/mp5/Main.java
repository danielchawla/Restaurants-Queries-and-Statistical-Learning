package ca.ece.ubc.cpen221.mp5;

public class Main{

	public static void main (String[] args){

		//RestaurantDBServer(int port, String restaurants, String reviews, String users)
		new RestaurantDBServer(Integer.parseInt(args[0]), args[1], args[2], args[3]);
	}
}