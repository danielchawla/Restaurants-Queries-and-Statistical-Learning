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
	    	System.out.println("Booting server.");
			socket = new ServerSocket(port);
			System.out.println("Success booting server.");
		}catch (IOException e){
			e.printStackTrace();
			return;
		}
	    
	    while(true){
	    	Socket client;
    		try{
    			System.out.println("Waiting for client");
				client = socket.accept();
				System.out.println("Found client");
			
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
		output.println("Please enter a query:");
		do{
			line = input.readLine();
			StringBuilder restaurants = new StringBuilder();
			for(Restaurant restaurant : db.query(line)){
				restaurants.append(restaurant.getJSON()); // it looks like this only prints out one restaurant
			}
			output.println(restaurants);
			output.flush();
		} while(line != null);
		input.close();
		output.close();
	}
}
