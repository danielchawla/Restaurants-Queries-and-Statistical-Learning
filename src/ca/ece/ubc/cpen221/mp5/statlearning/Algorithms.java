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
	 * @return
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
	
	private static double getDistanceTo (Location centroid, Location restaurant){
		return Math.sqrt(Math.pow(centroid.getLat() - restaurant.getLat(), 2)
				+ Math.pow(centroid.getLong() - restaurant.getLong(), 2));
	}
	
	private static double getAverage(Set<Double> inputs){
		double sum = 0;
		for(double num : inputs){
			sum+=num;
		}
		return sum/inputs.size();
	}
	
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

	@SuppressWarnings("unchecked")
	public static String convertClustersToJSON(List<Set<Restaurant>> clusters) {
		JSONArray json_clusters = new JSONArray(); 
		int clusterNumber = 0;
		for (Set<Restaurant> set : clusters) {
			JSONObject json_cluster = new JSONObject();
			for (Restaurant rest : set) {
				json_cluster.put("latitude", rest.getLocation().getLat());
				json_cluster.put("longitude", rest.getLocation().getLong());
				json_cluster.put("name", rest.getName());
				json_cluster.put("cluster", clusterNumber);
				clusterNumber++;
				json_cluster.put("weight", 1);
			}
			json_clusters.add(json_cluster);
		}

		return json_clusters.toJSONString();
	}

	public static MP5Function getPredictor(User u, RestaurantDB db, MP5Function featureFunction) {
		Set<Review> reviews = db.getReviews(u.getUserID());
		Map<Double, Double> coordinates = new HashMap<>();
		Set<Restaurant> restaurants = db.getRestaurants();
		
		for(Review review : reviews)
			coordinates.put(review.getStars(), featureFunction.f(db.getRestaurant(review.getBusinessID()), db));
		
		return new Regression(coordinates, featureFunction);
	}

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