package ca.ece.ubc.cpen221.mp5;

import java.util.*;
import org.json.simple.JSONObject;

public class Review {
	final static String TYPE = "type";
	final static String BUSINESS_ID = "business_id";
	final static String VOTES = "votes";
	final static String COOL = "cool";
	final static String USEFUL = "useful";
	final static String FUNNY = "funny";
	final static String REVIEW_ID = "review_id";
	final static String TEXT = "text";
	final static String STARS = "stars";
	final static String USER_ID = "user_id";
	final static String DATE = "date";
	
	final private String businessID;
	final private Map<String, Long> votes = new HashMap<String, Long>();
	final private String reviewID; 
	final private String text;
	final private long stars; 
	final private String userID;
	final private String date; 
	final private String type;
	
	final private JSONObject jsonReview;
	
	/**
	 * Constructs new review from passed JSONObject.
	 * @param JSONObject representing review
	 *     rep invariant: JSONObject is not null, is properly formatted, 
	 *     and contains all related fields.
	 */
	public Review(JSONObject o){
	    // 
	    // Abstraction function: represents reviews that a user has given a restaurant (presumably after
	    //         having a meaningful interaction with restaurant).
	    //
	    // Rep invariant: 
	    //         - business ID is unique, represents correct restaurant, contains only ASCII characters
	    //         - no fields are null, all fields are accurate and valid
	    //         - 1 <= stars <= 5
	    //         - name is accurate and is written with only ASCII characters
	    //         - date is valid and newer than Yelp start date
	    
		jsonReview = (JSONObject) o.clone();
		type = (String) jsonReview.get(TYPE);
		businessID = (String) jsonReview.get(BUSINESS_ID);
		JSONObject json_votes = (JSONObject) jsonReview.get(VOTES);
		votes.put(COOL, (Long) json_votes.get(COOL));
		votes.put(USEFUL, (Long) json_votes.get(USEFUL));
		votes.put(FUNNY, (Long) json_votes.get(FUNNY));
	
		reviewID = (String) jsonReview.get(REVIEW_ID);
		text = (String) jsonReview.get(TEXT);
		userID = (String) jsonReview.get(USER_ID);
		date = (String) jsonReview.get(DATE);
		stars = (long) jsonReview.get(STARS);
	}
	
	/**
	 * Retrieves business ID of restaurant for which review was made.
	 * @return businessID
	 */
	public String getBusinessID(){
		return this.businessID;
	}
	
	/**
	 * Retrieves map of votes that review contains.
	 * @return votes
	 */
	public Map<String, Long> getVotes(){
		return Collections.unmodifiableMap(votes);
	}
	
	/**
	 * Retrieves ID of review.
	 * @return reviewID
	 */
	public String getReviewID(){
		return this.reviewID;
	}
	
	/**
	 * Retrieves main body text of review.
	 * @return body text
	 */
	public String getText(){
		return this.text;
	}
	
	/**
	 * Retrieves ID of user who posted review.
	 * @return userID
	 */
	public String getUserID(){
		return this.userID;
	}
	
	/**
	 * Retrieves the date review was updated.
	 * @return date
	 */
	public String getDate(){
		return this.date;
	}
	
	/**
	 * Retrieves num of stars that review has.
	 * @return num of stars
	 */
	public long getStars(){
		return this.stars;
	}
	
	/**
	 * Retrieves review type.
	 * @return type
	 */
	public String getType(){
		return this.type;
	}
	
	/**
	 * Retrieves Review in JSON format
	 * @return review in JSON format.
	 */
	public String getJSON(){
	    return this.jsonReview.toJSONString();
	}
	
	@Override
	public boolean equals(Object that){
		if (!(that instanceof Review)) return false;
		Review review = (Review) that;
		if(review.getReviewID().equals(this.getReviewID())) return true;
		return false;
	}
	
	@Override
	public int hashCode(){
		return reviewID.hashCode();
	}
}
