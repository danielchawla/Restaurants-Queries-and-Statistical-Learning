package ca.ece.ubc.cpen221.mp5.statlearning;

import java.util.*;
import ca.ece.ubc.cpen221.mp5.*;
import org.json.simple.*;

public class Algorithms {
	
	private static final int MAX_NUM_OF_CYCLES = 100;
	
	/**
	 * Use k-means clustering to compute k clusters for the restaurants in the
	 * database.
	 * 
	 * @param db must contain at least k restaurants
	 * @param k the number of clusters
	 * @return list of set of restaurants where each set is a whole cluster of restaurants
	 */
	public static List<Set<Restaurant>> kMeansClustering(int k, RestaurantDB db) {
		Set<Restaurant> restaurants = new HashSet<Restaurant>();
		List<Set<Restaurant>> clusters = new ArrayList<Set<Restaurant>>();
		List<Location> centroids = new ArrayList<Location>();
		
		restaurants = db.getRestaurants();
		int numOfCentroids = 0;
		
		for(Restaurant rest : restaurants){
			if(numOfCentroids < k){
				Set<Restaurant> newSet = new HashSet<Restaurant>();
				newSet.add(rest);
				clusters.add(newSet);
				centroids.add(rest.getLocation());
				numOfCentroids++;
			}
			else{
				Location closestCentroid = getClosest(rest.getLocation(),centroids);
				int index = centroids.indexOf(closestCentroid);
				clusters.get(index).add(rest);
			}
		}
		if(numOfCentroids != k) throw new IllegalArgumentException();
		
		for(int itter = 0; itter < MAX_NUM_OF_CYCLES; itter++){
			//recreate centroids
			boolean centroidsAreTheSame = true;
			for(Set<Restaurant> cluster: clusters){
				//set latitude & longitude Sets
				Set<Double> lats = new HashSet<Double>();
				Set<Double> longs = new HashSet<Double>();
				for(Restaurant rest : cluster){
					Location loc = rest.getLocation();
					lats.add(loc.getLat());
					longs.add(loc.getLong());
				}
				
				int index = clusters.indexOf(cluster);
				Location lastLoc = centroids.remove(index);
				Location newLoc = new Location(getAverage(lats), getAverage(longs));
				centroids.add(index, newLoc);
				
				if(!newLoc.equals(lastLoc)) centroidsAreTheSame = false;
			}
			
			if(centroidsAreTheSame){ System.out.println("Number of itterations: " + itter); break;}
			
			//recreate clusters
			clusters.clear();
			for(int i = 0; i < k; i ++){
				clusters.add(new HashSet<Restaurant>());
			}
			
			for(Restaurant rest : restaurants){
				Location closestCentroid = getClosest(rest.getLocation(),centroids);
				int index = centroids.indexOf(closestCentroid);
				clusters.get(index).add(rest);
			}
		}
		
		return clusters;
	}
	
	/**
	 * finds euclidian distance between two locations
	 * @param centroid first location
	 * @param restaurant second location
	 * @return magnitude of straight line distance
	 */
	private static double getDistanceTo (Location centroid, Location restaurant){
		return Math.sqrt(Math.pow(centroid.getLat() - restaurant.getLat(), 2)
				+ Math.pow(centroid.getLong() - restaurant.getLong(), 2));
	}
	
	/**
	 * 
	 * @param inputs any doubles
	 * @return average of inputs (mean)
	 */
	private static double getAverage(Set<Double> inputs){
		double sum = 0;
		for(double num : inputs){
			sum+=num;
		}
		return sum/inputs.size();
	}
	
	/**
	 * finds the closest centroid to a given location
	 * 
	 * @param myLocation location of restaurant
	 * @param centroids the set of all centroids
	 * @return closest centroid to myLocation
	 */
	private static Location getClosest(Location myLocation, List<Location> centroids){
		Location closestLocation = null;
		double distance = 0;
		boolean isFirstLocation = true;
		for(Location loc : centroids){
			if(isFirstLocation){
				closestLocation = loc;
				distance = getDistanceTo(loc,myLocation);
				isFirstLocation = false;
			}
			else if(getDistanceTo(loc,myLocation) < distance){
				distance = getDistanceTo(loc,myLocation);
				closestLocation = loc;
			}
		}
		return closestLocation;
	}

	/**
	 * Converts a List of clusters, each cluster is a set of
	 * restaurants. converts to JSON formatted string representation of the list.
	 * 
	 * @param clusters all clusters
	 * @return the JSON formatted string representation of the list
	 */
	@SuppressWarnings("unchecked")
	public static String convertClustersToJSON(List<Set<Restaurant>> clusters) {
		JSONArray json_clusters = new JSONArray();
		for (Set<Restaurant> set : clusters) {
			JSONArray json_cluster = new JSONArray();
			for (Restaurant rest : set) {
				json_cluster.add(rest.getJSON());
			}
			json_clusters.add(json_cluster);
		}

		return json_clusters.toJSONString();
	}
	
	/**
	 * Produces a predictor function that predicts a given user's rating of any restaurant in
	 * a given RestaurantDB, based on a certain aspect of all the restaurants.
	 *  The predictor function is linear with respect to the given feature function.
	 * 
	 * @param u the given user
	 * @param db the given restaurant database
	 * @param featureFunction an MP5Function that returns a specific feature of the restaurants in the database
	 * @return an MP5Function that predicts the given user's next review
	 */
	public static MP5Function getPredictor(User u, RestaurantDB db, MP5Function featureFunction) {
		Set<Review> reviews = db.getReviews(u.getUserID());
		Map<Double, Double> coordinates = new HashMap<>();
		
		for(Review review : reviews)
			coordinates.put(featureFunction.f( db.findRestaurant(review.getBusinessID()) , db), (double) review.getStars());
		
		return new Regression(coordinates, featureFunction);
	}
	
	/**
	 * Finds the best Regression that best predicts a given users next review based on their previous reviews.
	 * 
	 * @param u
	 *            the user
	 * @param db
	 *            the restaurant database
	 * @param featureFunctionList
	 *            the list of MP5Functions
	 * @return the best MP5Function that best predicts the user's ratings.
	 */
	public static MP5Function getBestPredictor(User u, RestaurantDB db, List<MP5Function> featureFunctionList) {
		boolean firstFunction = true;
		Regression bestPredictor = null;
		for(MP5Function feature : featureFunctionList){
			if(firstFunction){
				bestPredictor = (Regression) getPredictor(u, db, feature);
				firstFunction = false;
			}
			else{
				Regression currentPredictor = (Regression) getPredictor(u, db, feature);
				if(currentPredictor.getR2() > bestPredictor.getR2()){
					bestPredictor = currentPredictor;
				}
			}
		}
		return bestPredictor;
	}
}