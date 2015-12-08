package ca.ece.ubc.cpen221.mp5.statlearning;

import static org.junit.Assert.*;
import org.junit.Test;
import ca.ece.ubc.cpen221.mp5.Restaurant;
import ca.ece.ubc.cpen221.mp5.RestaurantDB;
import ca.ece.ubc.cpen221.mp5.User;

public class AlgorithmsTest{

	@Test
	public void testGetPredictor() {
		RestaurantDB db = new RestaurantDB("data/restaurants.json", "data/reviews.json","data/users.json");
	
		Restaurant yelpRestaurant = null;
		for(Restaurant rest : db.getRestaurants()){
			yelpRestaurant = rest;
		}

		User z = null;
		for(User u : db.getUsers()){
			z = u;
		}
				
		Regression predictor = (Regression) Algorithms.getPredictor(z, db, new Price());
		System.out.println("R^2: "+predictor.f(yelpRestaurant, db));
	}
	
	@Test
	public void test ( ){
		
	}

}
