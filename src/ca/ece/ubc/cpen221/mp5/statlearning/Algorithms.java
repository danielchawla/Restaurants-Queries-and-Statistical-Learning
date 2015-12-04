package ca.ece.ubc.cpen221.mp5.statlearning;

import java.util.*;
import ca.ece.ubc.cpen221.mp5.*;

public class Algorithms {
	
	private static final int NUMOFCYCLES = 30;
	
	/**
	 * Use k-means clustering to compute k clusters for the restaurants in the
	 * database.
	 * 
	 * @param db
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
		
		for(int itter = 0; itter < NUMOFCYCLES; itter++){
			//recreate centroids
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
				centroids.remove(index);
				centroids.add(index, new Location(getAverage(lats), getAverage(longs)));
			}
			
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
		double average = 0;
		int i = 0;
		for(double num : inputs){
			average*=i/(i+1);
			average+=num/(i+1);
			i++;
		}
		return average;
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

	public static String convertClustersToJSON(List<Set<Restaurant>> clusters) {
		// TODO: Implement this method
		return null;
	}

	public static MP5Function getPredictor(User u, RestaurantDB db, MP5Function featureFunction) {
		// TODO: Implement this method
		return null;
	}

	public static MP5Function getBestPredictor(User u, RestaurantDB db, List<MP5Function> featureFunctionList) {
		// TODO: Implement this method
		return null;
	}
}