package ca.ece.ubc.cpen221.mp5;

import java.io.*;
import java.net.*;

/**
 * Server for RestaurantDB. 
 * @author Ryan Cotsakis and Daniel Chawla
 */
public class RestaurantDBServer {
    
    private ServerSocket socket;
    private final RestaurantDB db;
    
    private static final String RANDOMREVIEW = "randomReview";
    private static final String GETRESTAURANT = "getRestaurant";
    private static final String ADDRESTAURANT = "addRestaurant";
    private static final String ADDUSER = "addUser";
    private static final String ADDREVIEW = "addReview";
    private static final int INDEX = 2;

	/**
	 * Constructor for server that will instantiate database and process queries concurrently.
	 * 
	 * @param port
	 * @param filename1
	 * @param filename2
	 * @param filename3
	 */
	public RestaurantDBServer(int port, String restaurants, String reviews, String users) {
	    //
	    // Abstraction function: represents server to that connects to restaurantDB.
	    // 
	    // Rep invariant: 
	    //         - No fields are null
	    //         - Strings represent valid files.
	    //         - Files are properly formatted for RestaurantDB.
	    
	    db = new RestaurantDB(restaurants, reviews, users);
	    
	    try{
	    	System.out.println("Booting server...");
			socket = new ServerSocket(port);
			System.out.println("Server is ready.");
		}catch (IOException e){
			e.printStackTrace();
			return;
		}
	    
	    while(true){
	    	Socket client;
    		try{
    			System.out.println("Waiting for client...");
				client = socket.accept();
				System.out.println("Found client.");
			
		    	Thread handler = new Thread(new Runnable(){
		
					@Override
					public void run ( ){
						try{
							handle(client);
							client.close();
						}catch (IOException e){
							e.printStackTrace();
						}
					}
					
		    	});
		    	handler.start();
    		}catch (IOException e){
				e.printStackTrace();
			}
	    }
	}
	
	/**
	 * 
	 * @param socket
	 * @throws IOException
	 */
	private void handle(Socket socket) throws IOException {
		BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintWriter output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

		String line;
		
		do{
		    output.println("Please enter a query.");
			line = input.readLine();
			StringBuilder restaurants = new StringBuilder();
			
			if (line.contains(RANDOMREVIEW)){
			    output.println(db.randomReview(line.substring(RANDOMREVIEW.length() + INDEX, line.length() - INDEX)));
			} else if (line.contains(GETRESTAURANT)) {
			    output.println(db.getRestaurant(line.substring(GETRESTAURANT.length() + INDEX, line.length() - INDEX)));
			} else if (line.contains(ADDRESTAURANT)) {
			    output.println(db.addRestaurant(line.substring(ADDRESTAURANT.length() + INDEX, line.length() - INDEX)));
            } else if (line.contains(ADDUSER)) {
                output.println(db.addUser(line.substring(ADDUSER.length() + INDEX, line.length() - INDEX)));
            } else if (line.contains(ADDREVIEW)) {
                output.println(db.addReview(line.substring(ADDREVIEW.length() + INDEX, line.length() - INDEX)));
            } else {
			
                for (Restaurant restaurant : db.query(line)) {
                    restaurants.append(restaurant.getJSON());
                    //restaurants.append('\n');
                }
                restaurants.append("Please enter another query");
            }
			output.println(restaurants);
			output.flush();
		} while(line != null);
		input.close();
		output.close();
	}
}
