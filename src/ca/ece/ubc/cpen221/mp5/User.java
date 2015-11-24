package ca.ece.ubc.cpen221.mp5;

import java.util.*;

// TODO: Use this class to represent a Yelp user.

public class User {
	private final Map<String, Integer> votes;
	private int review_count;
	private final String user_id;
	private final String name;
	private double average_stars;
	
	public User(int funnyVotes, int usefulVotes, int coolVotes, int review_count,
			String user_id, String name, double average_stars){
		
		votes = new TreeMap<String, Integer>();
		votes.put("funny", funnyVotes);
		votes.put("useful", usefulVotes);
		votes.put("cool", coolVotes);
		this.review_count = review_count;
		this.user_id = user_id;
		this.name = name;
		this.average_stars = average_stars;
	}
	
	public Map<String, Integer> getVotes (){
		return Collections.unmodifiableMap(votes);
	}
	
	public void addVote(String type){
		//TODO: make this maybe
	}
	
	public String getUserID (){
		return user_id;
	}
	
	public void addReview (int numOfStars){
		long totalStars = Math.round(average_stars * review_count);
		totalStars+=numOfStars;
		review_count ++;
		average_stars = ((double) totalStars) / reviewCount;
		//TODO: finish this method
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
