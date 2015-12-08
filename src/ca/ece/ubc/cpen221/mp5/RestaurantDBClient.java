package ca.ece.ubc.cpen221.mp5;

import java.io.*;
import java.net.*;

/**
 * RestaurantDBClient
 * @author Ryan Cotsakis and Daniel Chawla
 */
public class RestaurantDBClient{

    /**
     * Client that uses RestaurantDBServer
     * @param hostname
     * @param port
     */
	public RestaurantDBClient(String hostname, int port) {
		try{
			Socket socket = new Socket(hostname, port);
			PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
	
			String fromServer;
			String fromUser;
	
			do{
				fromServer = input.readLine();
				if (fromServer != null) {
					System.out.println("Server: " + fromServer);
					fromServer = null;
				}
				fromUser = stdIn.readLine();
				if (fromUser != null) {
					System.out.println("Client: " + fromUser);
					output.println(fromUser);
				}
			}while(!fromUser.equals("exit"));
			socket.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		new RestaurantDBClient("localhost", Main.PORT);
	}
}
