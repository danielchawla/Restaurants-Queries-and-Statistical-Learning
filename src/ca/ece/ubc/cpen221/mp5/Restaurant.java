package ca.ece.ubc.cpen221.mp5;

import java.util.*;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

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
	
	final private Location coordinates;
	final private String city;
    final private String address;
    final private String state;
    final private Set<String> neighbourhood = new HashSet<String>();
    final private String businessID;
    final private String name;
    final private String type;
    final private Set<String> categories = new HashSet<String>();
    
    final private double stars; 
    private long reviewCount; 
    final private long price; 
    final private String photo;
    final private Set<String> schools = new HashSet<String>();

    
    /**
     * Constructor for Restaurant class
     * @param object - the restaurant details in JSON format
     */
    public Restaurant(JSONObject object) {
        // 
        // Abstraction function: represents physical restaurant that has chosen to be added to yelp database.     
        //
        // rep invariant:
        //      - no fields can be null
        //      - 1.0 <= price <= 4.0
        //      - review >= 0.0
        //      - 1.0 <= stars <= 5.0
        //      - businessID is unique
        //      - schools, city, address, and state are real and accurate
        //      - coordinates are real and represent actual location on earth
        
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
        this.price = (long) this.restaurantJSON.get(PRICE);
        this.coordinates = new Location((Double) this.restaurantJSON.get(LATITUDE),
        		(Double) this.restaurantJSON.get(LONGITUDE));

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
    
    /**
     * Clones restaurant.
     */
    public Restaurant clone() {
        return new Restaurant(this.restaurantJSON);
    }
    
    /**
     * Retrieves name of restaurant
     * @return name
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * Returns business ID of restaurant.
     * @return business ID
     */
    public String getBusinessID() {
        return this.businessID;
    }
    
    /**
     * Returns type of object. All these should be of the restaurant type.
     * @return type
     */
    public String getType() {
        return this.type;
    }
    
    /**
     * Retrieves city containing restaurant.
     * @return city
     */
    public String getCity() {
        return this.city;
    }

    /**
     * Retrieves address of restaurant
     * @return address
     */
    public String getAddress() {
        return this.address;
    }

    /**
     * Retrieves state restaurant is in.
     * @return state
     */
    public String getState() {
        return this.state;
    }
    
    /**
     * Retrieves location of restaurant
     * @return coordinates of restaurant.
     */
    public Location getLocation() {
        return coordinates.clone();
    }
    
    /**
     * Retrieves restaurant details in JSON format
     * @return string of restaurant details
     */
    public String getJSON() {
        return this.restaurantJSON.toJSONString();
    }

    /**
     * Retrieves neighborhood of restaurant.
     * @return neighborhood
     */
    public Set<String> getNeighbors() {
        Set<String> neighbourhoodDuplicate = new HashSet<String>();
        neighbourhoodDuplicate.addAll(neighbourhood);
        return Collections.unmodifiableSet(neighbourhoodDuplicate);
    }

    /**
     * Retrieves categories restaurant satisfies.
     * @return categories
     */
    public Set<String> getCategories() {
        Set<String> categoriesClone = new HashSet<String>();
        categoriesClone.addAll(categories);
        return Collections.unmodifiableSet(categoriesClone);
    }

    /**
     * Returns avg number of stars users have rated restaurant to have.
     * @return avg number of stars.
     */
    public double getStars() {
        return this.stars;
    }
    
    /**
     * Retrieves number of reviews restaurant has.
     * @return num of reviews
     */
    public long getReviewCount() {
        return this.reviewCount;
    }

    /**
     * Retrieves price range of restaurant.
     * @return price range
     */
    public long getPrice() {
        return this.price;
    }

    /**
     * Retrieves Restaurant photo
     * @return photo
     */
    public String getPhoto() {
        return this.photo;
    }
    
    /**
     * Retrieves set of all schools
     * @return all schools
     */
    public Set<String> getSchools() {
        Set<String> schoolsDuplicate = new HashSet<String>();
        schoolsDuplicate.addAll(schools);
        return Collections.unmodifiableSet(schoolsDuplicate);
    }
    
    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public boolean equals(Object obj) {
        Restaurant that = (Restaurant) obj;
        return ( this.coordinates.equals(that.getLocation()) && this.name.equals(that.getName()));
    }

    @Override
    public int hashCode() {
        return this.coordinates.hashCode() + 31 * this.name.hashCode();
    }
    
}
