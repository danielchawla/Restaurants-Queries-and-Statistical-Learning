package ca.ece.ubc.cpen221.mp5;

import java.io.IOException;
import java.net.*;

// TODO: Implement a server that will instantiate a database, 
// process queries concurrently, etc.

public class RestaurantDBServer {
    
    private final int port;
    private ServerSocket socket;
    private final RestaurantDB db;
    private boolean online;

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
	    db = new RestaurantDB(restaurants, reviews, users);
	    this.port = port;
	    
	    try{
			socket = new ServerSocket(port);
			online = true;
		}catch (IOException e){
			e.printStackTrace();
			return;
		}
	    
	    while(online){
	    	Socket client;
	    	try{
	    		client = socket.accept();
	    	}catch(IOException e ){
	    		if(!online) break;
	    		else e.printStackTrace();
	    	}
	    	new Thread(new RestaurantDBWorker(socket, db)).start();
	    }
	    
	    try{
	    	socket.close();
	    }catch(IOException e){
	    	e.printStackTrace();
	    }
	}

}
