package ca.ece.ubc.cpen221.mp5.statlearning;

import static org.junit.Assert.*;
import org.junit.Test;
import ca.ece.ubc.cpen221.mp5.*;

public class AlgorithmsTest{
	
	RestaurantDB db;

	@Test
	public void testK_Means( ){
		db = new RestaurantDB("data/restaurants.json", "data/reviews.json", "data/users.json");
		
	}

}
