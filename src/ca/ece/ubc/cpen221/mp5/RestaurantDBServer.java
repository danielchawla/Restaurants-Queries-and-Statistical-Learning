package ca.ece.ubc.cpen221.mp5;

// TODO: Implement a server that will instantiate a database, 
// process queries concurrently, etc.

public class RestaurantDBServer {
    
    private final int port;
    private final RestaurantDB restaurantDatabase;

	/**
	 * Constructor
	 * 
	 * @param port
	 * @param filename1
	 * @param filename2
	 * @param filename3
	 */
	public RestaurantDBServer(int port, String restaurants, String reviews, String users) {
		// TODO: See the problem statement for what the arguments are.
		// TODO: Rename the arguments suitably.
	    restaurantDatabase = new RestaurantDB(restaurants, reviews, users);
	    this.port = port;
	            
	}

}
