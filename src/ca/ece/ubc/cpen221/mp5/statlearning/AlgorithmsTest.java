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
	}

}
