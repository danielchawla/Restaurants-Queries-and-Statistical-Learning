package ca.ece.ubc.cpen221.mp5;

import java.util.*;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

// TODO: Use this class to represent a restaurant.
// State the rep invariant and abs

public class Restaurant {
    
    final private JSONObject restaurantJSON;
    
    final static String LONGITUDE = "longitude";
    final static String LATITUDE = "latitude";
    final static String CITY = "city";
    final static String ADDRESS = "full_address";
    final static String STATE = "state";
    
    final static String NEIGHBORHOOD = "neighborhoods";
    final static String BUSINESSID = "business_id";
    final static String NAME = "name";
    final static String TYPE = "type";
    final static String CATEGORIES = "categories";
    final static String STARS = "stars";
    final static String REVIEWCOUNT = "review_count";
    final static String PRICE = "price";
    final static String PHOTO = "photo_url";
	final static String SCHOOLS = "schools";
	final private Location coordinates = new Location((Double) this.restaurantJSON.get(LATITUDE),
			(Double) this.restaurantJSON.get(LONGITUDE));

    final private String city;
    final private String address;
    final private String state;
    final private Set<String> neighbourhood = new HashSet<String>();
    final private String businessID;
    final private String name;
    final private String type;
    final private Set<String> categories = new HashSet<String>();
    
    final private double stars; // rep invariant: 0 <= stars <= 5.0
    private long reviewCount; // rep invariant: review >= 0
    final private int price; // rep invariant: 1 <= price <= 4
    final private String photo;
    final private Set<String> schools = new HashSet<String>();

    
    /**
     * Constructor for Restaurant class
     * @param object - the restaurant details in JSON format
     */
    public Restaurant(JSONObject object) {
        
        this.restaurantJSON = (JSONObject) object.clone();
        this.name = (String) this.restaurantJSON.get(NAME);
        this.businessID = (String) this.restaurantJSON.get(BUSINESSID);
        this.city = (String) this.restaurantJSON.get(CITY);
        this.address = (String) this.restaurantJSON.get(ADDRESS);
        this.state = (String) this.restaurantJSON.get(STATE);
        
        this.photo = (String) this.restaurantJSON.get(PHOTO);
        this.type = (String) this.restaurantJSON.get(TYPE);
        this.stars = (Double) this.restaurantJSON.get(STARS);
        this.reviewCount = (long) this.restaurantJSON.get(REVIEWCOUNT);
        this.price = (int) this.restaurantJSON.get(PRICE);

        JSONArray neighborhoods = (JSONArray) this.restaurantJSON.get(NEIGHBORHOOD);
        for (Object neighbor : neighborhoods) {
            this.neighbourhood.add((String) neighbor);
        }

        JSONArray categories = (JSONArray) this.restaurantJSON.get(CATEGORIES);
        for (Object category : categories) {
            this.categories.add((String) category);
        }

        JSONArray schools = (JSONArray) this.restaurantJSON.get(SCHOOLS);
        for (Object school : schools) {
            this.schools.add((String) school);
        }
    }
    
    public Restaurant clone() {
        return new Restaurant(this.restaurantJSON);
    }

    public String getName() {
        return this.name;
    }

    public String getBusinessID() {
        return this.businessID;
    }
    
    public String getType() {
        return this.type;
    }

    public String getCity() {
        return this.city;
    }

    public String getAddress() {
        return this.address;
    }

    public String getState() {
        return this.state;
    }
    
    public Location getLocation() {
        return coordinates.clone();
    }
    
    public String getJSON() {
        return this.restaurantJSON.toJSONString();
    }

    public Set<String> getSurroundingNeighbourhoods() {
        Set<String> neighbourhoodDuplicate = new HashSet<String>();
        neighbourhoodDuplicate.addAll(neighbourhood);
        return Collections.unmodifiableSet(neighbourhoodDuplicate);
    }

    public Set<String> getCategories() {
        Set<String> categoriesClone = new HashSet<String>();
        categoriesClone.addAll(categories);
        return Collections.unmodifiableSet(categoriesClone);
    }

    public double getStars() {
        return this.stars;
    }

    public long getReviewCount() {
        return this.reviewCount;
    }

    public int getPrice() {
        return this.price;
    }

    public String getPhoto() {
        return photo;
    }
    
    @Override
    public String toString() {
        return this.name;
    }

    public Set<String> getSchools() {
        Set<String> schoolsDuplicate = new HashSet<String>();
        schoolsDuplicate.addAll(schools);
        return Collections.unmodifiableSet(schoolsDuplicate);
    }

    @Override
    public boolean equals(Object obj) {
        Restaurant that = (Restaurant) obj;
        return ( this.businessID.equals(that.getBusinessID()) );
    }

    @Override
    public int hashCode() {
        return this.businessID.hashCode();
    }
    
}
