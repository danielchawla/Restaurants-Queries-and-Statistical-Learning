package ca.ece.ubc.cpen221.mp5;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

// TODO: This class represents the Restaurant Database.
// Define the internal representation and 
// state the rep invariant and the abstraction function.

public class RestaurantDB {
    
    private final Set<Restaurant> restaurantDatabase = new CopyOnWriteArraySet<Restaurant>();
    private final Set<Review> reviewDatabase = new CopyOnWriteArraySet<Review>();
    private final Set<User> userDatabase = new CopyOnWriteArraySet<User>();

	/**
	 * Create a database from the Yelp dataset given the names of three files:
	 * <ul>
	 * <li>One that contains data about the restaurants;</li>
	 * <li>One that contains reviews of the restaurants;</li>
	 * <li>One that contains information about the users that submitted reviews.
	 * </li>
	 * </ul>
	 * The files contain data in JSON format.
	 * 
	 * @param restaurantJSONfilename
	 *            the filename for the restaurant data
	 * @param reviewsJSONfilename
	 *            the filename for the reviews
	 * @param usersJSONfilename
	 *            the filename for the users
	 */
	public RestaurantDB(String restaurantJSONfilename, String reviewsJSONfilename, String usersJSONfilename) {
	    
	    try {
	        
	        JSONParser parser = new JSONParser();
            String currentLine;
            
            // reads from restaurant file
	        FileReader restaurantFileReader = new FileReader(restaurantJSONfilename);
	        BufferedReader restaurantBufferedReader = new BufferedReader(restaurantFileReader);
	        
	        while( (currentLine = restaurantBufferedReader.readLine()) != null){
                // parses line in file to JSONObject
                JSONObject restaurant = (JSONObject) parser.parse(currentLine);
                Restaurant currentRestaurant = new Restaurant(restaurant);
                // adds JSONObject into restaurant database
                restaurantDatabase.add(currentRestaurant);
	        }
	        restaurantBufferedReader.close();
	        restaurantFileReader.close();
	        
	        // reads from review file
	        FileReader reviewFileReader = new FileReader(reviewsJSONfilename);
            BufferedReader reviewBufferedReader = new BufferedReader(reviewFileReader);
            
            while( (currentLine = reviewBufferedReader.readLine()) != null){
                // parses line in file to JSONObject
                JSONObject review = (JSONObject) parser.parse(currentLine);
                Review currentReview = new Review(review);
                // adds JSONObject into review database
                reviewDatabase.add(currentReview);
            }
            reviewBufferedReader.close();
            reviewFileReader.close();

            // reads from user file
            FileReader userFileReader = new FileReader(usersJSONfilename);
            BufferedReader userBufferedReader = new BufferedReader(userFileReader);
            
            while( (currentLine = userBufferedReader.readLine()) != null){
                // parses line in file to JSONObject
                JSONObject user = (JSONObject) parser.parse(currentLine);
                User currentUser = new User(user);
                // adds JSONObject into user database
                userDatabase.add(currentUser);
            }
            userBufferedReader.close();
            userFileReader.close();
	        
	    } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("File couldn't be found.");
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } 
	}
	
	/**
	 * Finds and returns all restaurants
	 * @return all restaurants in restaurant database.
	 */
	public Set<Restaurant> getRestaurants(){
		return this.restaurantDatabase;
		
	}

	/**
	 * 
	 * @param queryString
	 * @return
	 */
	public Set<Restaurant> query(String queryString) {
	  //TODO: implement this
		return null;
	}
	
	/**
	 * Outputs random review of a restaurant that matches restaurantName. If more than one name matches
	 * restaurantName, returns random review from first restaurant.
	 * @param restaurantName
	 * @return random review in JSONObject format 
	 */
	public JSONObject randomReview(String restaurantName){
	    // TODO: changes this
	    return null;
	}
	 
	/**
	 * Finds and returns restaurant details in JSON format for a restaurant given the
	 * ID number.
	 * @param businessID
	 * @return String with restaurant details in JSON format
	 */
	public String getRestaurant(String businessID){
	    // TODO: change this
	    return null;
	}
	

	
	/**
	 * Checks if restaurant exists and if not, adds new restaurant to restaurantDatabase. 
	 * @param restaurantDetails 
	 *     rep invariant: String must be JSON format. String must not be null.    
	 */
	public synchronized void addRestaurant(String restaurantDetails){
	    
	}
	
	/**
     * Checks if user exists and if not, adds new user to userDatabase. 
     * @param userDetails 
     *     rep invariant: String must be JSON format. String must not be null.    
     */
	public synchronized void addUser(String userDetails){
	    
	}
	
    /**
     * Checks if review exists and if not, adds new review to userDatabase. 
     * @param reviewDetails 
     *     rep invariant: String must be JSON format. String must not be null.    
     */
	public synchronized void addReview(String reviewDetails){
	    
	}
	
	/**
     * Gets all reviews given a userID
     * @param userID
     * @return set of reviews
     */
    public Set<Review> getReviews(String userID){
        //TODO: decided if we need this
        return null;
    }
}
