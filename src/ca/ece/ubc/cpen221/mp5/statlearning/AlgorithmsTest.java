package ca.ece.ubc.cpen221.mp5.statlearning;

import static org.junit.Assert.*;
import java.util.*;
import org.junit.Test;
import ca.ece.ubc.cpen221.mp5.*;

public class AlgorithmsTest{
	
	RestaurantDB db;

	@Test
	public void testK_Means( ){
		db = new RestaurantDB("data/restaurants.json", "data/reviews.json", "data/users.json");
		List<Set<Restaurant>> out = Algorithms.kMeansClustering(5, db);
		assertEquals(out.size(), 5);
		
		List<Set<Restaurant>> out2 = Algorithms.kMeansClustering(1, db);
		assertEquals(out2.size(), 1);
		
		List<Set<Restaurant>> out3 = Algorithms.kMeansClustering(12, db);
		assertEquals(out3.size(), 12);
		
		System.out.println(Algorithms.convertClustersToJSON(out));
	}
	
	@Test
	public void testRegression(){
		db = new RestaurantDB("data/restaurants.json", "data/reviews.json", "data/users.json");
		User w = null;
		for(User u : db.getUsers()){
			w = u;
			break;
		}
		Regression functionPrice = (Regression) Algorithms.getPredictor(w, db, new Price());
		Regression functionRating = (Regression) Algorithms.getPredictor(w, db, new Rating());
		Regression functionLongitude = (Regression) Algorithms.getPredictor(w, db, new Longitude());
		System.out.println(functionRating.getR2() + " " + functionPrice.getR2()+ " " + functionLongitude.getR2());
		
	}

}
