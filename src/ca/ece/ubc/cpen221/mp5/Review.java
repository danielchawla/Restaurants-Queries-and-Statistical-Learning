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
	final private Map<String, Integer> votes = new HashMap<String, Integer>();
	final private String reviewID;
	final private String text;
	final private int stars;
	final private String userID;
	final private String date;
	final private String type;
	
	final private JSONObject jsonReview;
	
	public Review(JSONObject o){
		jsonReview = (JSONObject) o.clone();
		type = (String) jsonReview.get(TYPE);
		businessID = (String) jsonReview.get(BUSINESS_ID);
		JSONObject json_votes = (JSONObject) jsonReview.get(VOTES);
		votes.put(COOL, (Integer) json_votes.get(COOL));
		votes.put(USEFUL, (Integer) json_votes.get(USEFUL));
		votes.put(FUNNY, (Integer) json_votes.get(FUNNY));
	
		reviewID = (String) jsonReview.get(REVIEW_ID);
		text = (String) jsonReview.get(TEXT);
		userID = (String) jsonReview.get(USER_ID);
		date = (String) jsonReview.get(DATE);
		stars = (int) jsonReview.get(STARS);
	}
	
	public String getBusinessID(){
		return this.businessID;
	}
	
	public Map<String, Integer> getVotes(){
		return Collections.unmodifiableMap(votes);
	}
	
	public String getReviewID(){
		return this.reviewID;
	}
	
	public String getText(){
		return this.text;
	}
	
	public String getUserID(){
		return this.userID;
	}
	
	public String getDate(){
		return this.date;
	}
	
	public int getStars(){
		return this.stars;
	}
	
	public String getType(){
		return this.type;
	}
	
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
