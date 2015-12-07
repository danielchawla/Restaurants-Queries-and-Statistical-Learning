package ca.ece.ubc.cpen221.mp5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import ca.ece.ubc.cpen221.mp5.queries.QueryGrammerParser;

// TODO: This class represents the Restaurant Database.
// Define the internal representation and 
// state the rep invariant and the abstraction function.

public class RestaurantDB {
    
    private final Set<Restaurant> restaurantDatabase = new HashSet<Restaurant>();
    private final Set<Review> reviewDatabase = new HashSet<Review>();
    private final Set<User> userDatabase = new HashSet<User>();

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
		return Collections.unmodifiableSet(this.restaurantDatabase);		
	}
	
	/**
	 * Finds a restaurant with given id.
	 * @param business_id
	 *            the ID of the restaurant that is to be returned
	 * @return the restaurant with ID the same as business_id.
	 */
	public Restaurant findRestaurant (String business_id){
		for (Restaurant restaurant : restaurantDatabase){
			if (restaurant.getBusinessID().equals(business_id))
				return restaurant.clone();
		}
		throw new IllegalArgumentException("Restaurant not found.");
	}

	
	/**
	 * Outputs random review of a restaurant that matches restaurantName. If more than one name matches
	 * restaurantName, returns random review from first restaurant.
	 * @param restaurantName
	 * @return random review in JSONObject format 
	 */
	public String randomReview(String restaurantName){
	    
	    String restaurantID = null;
	    
	    // finds first restaurant with given name
	    for (Restaurant restaurant: restaurantDatabase){
	       if (restaurant.getName().equals(restaurantName)){
	           restaurantID = restaurant.getBusinessID();
	           break;
	       }
	    }
	    // throws Exception if no restaurant found with given name
	    if (restaurantID == null) throw new IllegalArgumentException("Can't find restaurant with given name.");

	    List<Review> reviews = new ArrayList<Review>();
	    for(Review review: reviewDatabase){
	        if (review.getBusinessID().equals(restaurantID)){
	            reviews.add(review);
	        }
	    }
	    int index = Math.round( (float) (Math.random() * reviews.size())); // finds random index
	    return reviews.get(index).getJSON();
	}
	 
	/**
	 * Finds and returns restaurant details in JSON format for a restaurant given the
	 * ID number.
	 * @param businessID
	 * @return String with restaurant details in JSON format
	 */
	public String getRestaurant(String businessID){
	    
        for (Restaurant restaurant : restaurantDatabase) {
            if (restaurant.getBusinessID().equals(businessID)) {
                return restaurant.getJSON();
            }
        }
	    throw new IllegalArgumentException("No restaurant found with given ID");
	}
	
	/**
     * Gets all reviews given a userID
     * @param userID
     * @return set of reviews
     */
    public Set<Review> getReviews(String userID){
        
        Set<Review> reviews = new HashSet<Review>();
        
        for (Review review : reviewDatabase) {
            if (review.getUserID().equals(userID)){
                reviews.add(review);
            }
        }
        return reviews;
    }
    
    /**
     * Checks if restaurant exists and if not, adds new restaurant to restaurantDatabase. 
     * @param restaurantDetails 
     *     rep invariant: String must be JSON format. String must not be null.    
     */
    public synchronized void addRestaurant(String restaurantDetails){
        
        boolean alreadyExists = false;
        JSONParser parser = new JSONParser();
        
        try {
            Restaurant toAdd;
            toAdd = new Restaurant((JSONObject) parser.parse((restaurantDetails)));
            
            for (Restaurant restaurant : restaurantDatabase) {    
                if ( restaurant.equals(toAdd) ) {
                        alreadyExists = true; 
                }
            }
            
            if (alreadyExists == true) throw new IllegalArgumentException("Restaurant already exists.");
            else restaurantDatabase.add(toAdd);
        } catch (ParseException e) { e.printStackTrace(); }
    }
    
    /**
     * Checks if user exists and if not, adds new user to userDatabase. 
     * @param userDetails 
     *     rep invariant: String must be JSON format. String must not be null.    
     */
    public synchronized void addUser(String userDetails){
        
        boolean alreadyExists = false;
        JSONParser parser = new JSONParser();
        
        try {
            User toAdd;
            toAdd = new User((JSONObject) parser.parse((userDetails)));
            
            for (User user : userDatabase) {    
                if ( user.equals(toAdd) ) {
                        alreadyExists = true; 
                }
            }
            
            if (alreadyExists == true) 
                throw new IllegalArgumentException("Error. User with given ID already exists.");
            else userDatabase.add(toAdd);
        } catch (ParseException e) { e.printStackTrace(); }
    }
    
    /**
     * Checks if review exists and if not, adds new review to userDatabase. 
     * @param reviewDetails 
     *     rep invariant: String must be JSON format. String must not be null.    
     */
    public synchronized void addReview(String reviewDetails){
        
        boolean alreadyExists = false;
        JSONParser parser = new JSONParser();
        
        try {
            Review toAdd;
            toAdd = new Review((JSONObject) parser.parse((reviewDetails)));
            
            for (Review review : reviewDatabase) {    
                if ( review.equals(toAdd) ) {
                        alreadyExists = true; 
                }
            }
            
            if (alreadyExists == true) 
                throw new IllegalArgumentException("Error. Review already exists.");
            else reviewDatabase.add(toAdd);
        } catch (ParseException e) { e.printStackTrace(); } 
    }
    
    /**
     * 
     * @param queryString
     * @return
     */
    public Set<Restaurant> query(String queryString) {
//        QueryGrammerParser queryParser = new QueryGrammerParser();
        
        return null;
    }
}

