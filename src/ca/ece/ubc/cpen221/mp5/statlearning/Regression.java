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
		this.feature = feature;
	}
	
	public double getA(){
		return a;
	}
	
	public double getB(){
		return b;
	}
	
	public double getR2(){
		return r2;
	}

	@Override
	public double f (Restaurant yelpRestaurant, RestaurantDB db){
		return feature.f(yelpRestaurant, db);
	}	
}
