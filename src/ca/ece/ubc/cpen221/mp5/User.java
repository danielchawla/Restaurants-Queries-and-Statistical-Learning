package ca.ece.ubc.cpen221.mp5;

import java.util.*;
import org.json.simple.JSONObject;

/**
 * A yelp user.
 * @author Ryan Cotsakis and Daniel Chawla
 */
public class User {
	
	final static String URL = "url";
	final static String REVIEW_COUNT = "review_count";
	final static String TYPE = "type";
	final static String USER_ID = "user_id";
	final static String NAME = "name";
	final static String AVG_STARS = "average_stars";
	final static String VOTES = "votes";
	final static String COOL = "cool";
	final static String USEFUL = "useful";
	final static String FUNNY = "funny";
	
	final private Map<String, Long> votes;
	final private String userID; 
	final private String name;
	final private double averageStars; 
	final private long reviewCount;
	
	final private JSONObject json_user;
	
	/**
	 * Creates a new user.
	 * @param JSONObject representing user 
	 *     rep invariant: must be properly formatted, must contain all fields, can't be null.
	 */
	public User(JSONObject o){
	    //
	    // Abstraction function: Represents a user who has signed up with a yelp account
	    //
	    // rep invariant: 
	    //         - ID contains only ASCII characters and is unique
	    //         - all fields are not null and accurate
	    //         - 0.0 <= averageStars <= 5.0
	    //         - reviewCount >= 0
		
		json_user = (JSONObject) o.clone();
		
		votes = new TreeMap<String, Long>();
		JSONObject json_votes = (JSONObject) json_user.get(VOTES);
		
		votes.put(FUNNY, (long) json_votes.get(FUNNY));
		votes.put(COOL, (long) json_votes.get(COOL));
		votes.put(USEFUL, (long) json_votes.get(USEFUL));
		reviewCount = (long) json_user.get(REVIEW_COUNT);
		userID = (String) json_user.get(USER_ID);
		name = (String) json_user.get(NAME);
		averageStars = (double) json_user.get(AVG_STARS);
	}
	
	/**
	 * Returns votes.
	 * @return an immutable map of votes
	 */
	public Map<String, Long> getVotes (){
		return Collections.unmodifiableMap(votes);
	}
	
	/**
	 * Returns user's name.
	 * @return name
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * Return's number of reviews a user has.
	 * @return reviewCount
	 */
	public long getReviewCount(){
		return this.reviewCount;
	}
	
	/**
	 * Retrieves the average number of stars a user rates a restaurant with.
	 * @return averageStars
	 */
	public double getAvgStars(){
		return this.averageStars;
	}
	
	/**
	 * Retrieves user's ID number.
	 * @return userID.
	 */
	public String getUserID (){
		return this.userID;
	}
	
	@Override
	public boolean equals(Object that){
		if (!(that instanceof User)) return false;
		User user = (User) that;
		if(user.getUserID().equals(this.getUserID())) return true;
		return false;
	}
	
	@Override
	public int hashCode(){
		return userID.hashCode();
	}
}
