package ca.ece.ubc.cpen221.mp5.statlearning;

import java.util.Map;
import java.util.Map.Entry;
import ca.ece.ubc.cpen221.mp5.Restaurant;
import ca.ece.ubc.cpen221.mp5.RestaurantDB;

public class Regression implements MP5Function{

	private final double a;
	private final double b;
	private final double r2;
	private final MP5Function feature;
	
	/**
	 * construct a linear regression predictor that predicts a user's future ratings based on a feature of all restaurants.
	 * 
	 * @param coordinates the xy coordinates, where x is the restaurant feature and y is the user's rating.
	 * @param feature the aspect of the restauraunt on which the predictions are based
	 */
	public Regression(Map<Double, Double> coordinates, MP5Function feature){
		double sxy = 0;
		double sxx = 0;
		double syy = 0;
		double xbar = 0;
		double ybar = 0;
		int n = 0;
		
		for(Entry<Double, Double> coordinate : coordinates.entrySet()){
			n++;
			xbar+=coordinate.getKey();
			ybar+=coordinate.getValue();
		}
		if(n==1){
			a = 0;
			b = ybar;
			r2 = 1;
			
		}
		else{
			xbar/=n;
			ybar/=n;
			for(Entry<Double, Double> coordinate : coordinates.entrySet()){
				sxx+=Math.pow(coordinate.getKey() - xbar, 2.0);
				syy+=Math.pow(coordinate.getValue() - ybar, 2.0);
				sxy+=(coordinate.getKey() - xbar)*(coordinate.getValue() - ybar);
			}
			b = sxy/sxx;
			a = ybar - b * xbar;
			r2 = sxy*sxy/sxx/syy;
		}
		this.feature = feature;
	}
	
	/**
	 * 
	 * @return get slope of the line
	 */
	public double getA(){
		return a;
	}
	
	/**
	 * 
	 * @return get y intercept of the line
	 */
	public double getB(){
		return b;
	}
	
	/**
	 * 
	 * @return get the R^2 value for this prediction
	 */
	public double getR2(){
		return r2;
	}

	@Override
	public double f (Restaurant yelpRestaurant, RestaurantDB db){
		double result = a * feature.f(yelpRestaurant, db) + b;
		if (result >= 0.0 && result <= 5.0) return result;
		else if (result > 5.0) return 5.0;
		else return 0.0;
				
	}	
}
