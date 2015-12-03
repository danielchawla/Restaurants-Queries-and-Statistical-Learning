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
	
	final private String type;
	final private String business_id;
	final private Map<String, Integer> votes = new HashMap<String, Integer>();
	final private String review_id;
	final private String text;
	final private int stars;
	final private String user_id;
	final private String date;
	final private JSONObject json_review;
	
	public Review(JSONObject o){
		this.json_review = (JSONObject) o.clone();
		type = (String) json_review.get(TYPE);
		business_id = (String) json_review.get(BUSINESS_ID);
		JSONObject json_votes = (JSONObject) json_review.get(VOTES);
		votes.put(COOL, (Integer) json_votes.get(COOL));
		votes.put(USEFUL, (Integer) json_votes.get(USEFUL));
		votes.put(FUNNY, (Integer) json_votes.get(FUNNY));
	
		review_id = (String) json_review.get(REVIEW_ID);
		text = (String) json_review.get(TEXT);
		user_id = (String) json_review.get(USER_ID);
		date = (String) json_review.get(DATE);
		stars = (int) json_review.get(STARS);
	}
	
	//TODO: Implement other methods!!
}
