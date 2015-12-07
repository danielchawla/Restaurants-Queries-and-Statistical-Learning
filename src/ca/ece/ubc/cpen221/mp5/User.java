package ca.ece.ubc.cpen221.mp5;

import java.util.*;
import org.json.simple.JSONObject;

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
	final private long review_count;
	final private String user_id;
	final private String name;
	final private double average_stars;
	final private JSONObject json_user;
	
	public User(JSONObject o){
		
		json_user = (JSONObject) o.clone();
		
		votes = new TreeMap<String, Long>();
		JSONObject json_votes = (JSONObject) json_user.get(VOTES);
		
		votes.put(FUNNY, (long) json_votes.get(FUNNY));
		votes.put(COOL, (long) json_votes.get(COOL));
		votes.put(USEFUL, (long) json_votes.get(USEFUL));
		review_count = (long) json_user.get(REVIEW_COUNT);
		user_id = (String) json_user.get(USER_ID);
		name = (String) json_user.get(NAME);
		average_stars = (double) json_user.get(AVG_STARS);
	}
	
	public Map<String, Long> getVotes (){
		return Collections.unmodifiableMap(votes);
	}
	
	public String getName(){
		return name;
	}
	
	public long getReviewCount(){
		return review_count;
	}
	
	public double getAvgStars(){
		return average_stars;
	}
	
	public String getUserID (){
		return user_id;
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
		return user_id.hashCode();
	}
}
