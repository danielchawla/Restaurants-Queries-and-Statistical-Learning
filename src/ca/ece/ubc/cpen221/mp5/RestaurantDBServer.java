package ca.ece.ubc.cpen221.mp5;

import java.io.*;
import java.net.*;

// TODO: Implement a server that will instantiate a database, 
// process queries concurrently, etc.

public class RestaurantDBServer {
    
    private ServerSocket socket;
    private final RestaurantDB db;

	/**
	 * Constructor
	 * 
	 * @param port
	 * @param filename1
	 * @param filename2
	 * @param filename3
	 */
	public RestaurantDBServer(int port, String restaurants, String reviews, String users) {
	    db = new RestaurantDB(restaurants, reviews, users);
	    
	    try{
			socket = new ServerSocket(port);
		}catch (IOException e){
			e.printStackTrace();
			return;
		}
	    
	    while(true){
	    	Socket client;
    		try{
				client = socket.accept();
			
		    	new Thread(new Runnable(){
		
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
		    	
    		}catch (IOException e){
				e.printStackTrace();
			}
	    }
	}
	
	private void handle(Socket socket) throws IOException {
		BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintWriter output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
		
		String line;
		do{
			line = input.readLine();
			String restaurants = "";
			for(Restaurant restaurant : db.query(line)){
				restaurants.concat("Name: " + restaurant.getName() + " // Location: (" + restaurant.getAddress()
						+ " // Rating: " + restaurant.getStars());
			}
			output.println(restaurants);
		}while(line != null);
		input.close();
		output.close();
	}


}
