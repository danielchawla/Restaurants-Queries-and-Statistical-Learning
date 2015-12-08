CPEN 221 / Fall 2015 / Machine Problem 5

Restaurants, Queries and Statistical Learning
===

This machine problem is designed to allow you to explore multiple aspects of software construction:
+ managing complex ADTs;
+ query parsing and execution;
+ multithreading and the client-server pattern.

In addition to these aspects, the problem also touches upon rudimentary methods for statistical inference and learning.

### Logistics

+ This assignment should be completed in teams of two. 
+ You should submit your work by **December 5, 12 noon.**
+ Details for Gradescope submission will be posted shortly.
+ A demo during the week of November 30 in the lab session is preferred. If this is not possible then you may need to schedule a demo after December 5 with a TA.

### Background

For this machine problem, you will work with an excerpt from the [Yelp Academic Dataset](https://www.yelp.com/academic_dataset). Specifically, you will work with data (in [JSON](https://en.wikipedia.org/wiki/JSON) format) on restaurants, and this data includes information about some restaurants, reviews of the restaurants, and user information (for those contributing reviews).

You will use the dataset to create and maintain a simple in-memory database with restaurants, users and reviews. (Since the Yelp Academic Dataset does not contain details of business near UBC we are using information for restaurants near UC Berkeley or UCB!)

For working with the JSON format, we recommend that you use the [json-simple](https://code.google.com/p/json-simple/) toolkit for decoding (reading) and encoding (writing) JSON data. The `json-simple-1.1.1` JAR file has been included in this repository and you can add it to the Java build path in Eclipse as an external JAR file.

### Part I: In-Memory Database Server and Queries

Create an abstract datatype `RestaurantDB` that represents the data from the given dataset. An important operation that we would like to perform on such a datatype is a search that retrieves a set of restaurants given a query.

A query can be based on a combination of restaurant names, neighbourhood, categories, rating and price level. 

For example:
```
in("Telegraph Ave") && (category("Chinese") || category("Italian")) && price(1..2)
```
represents the query that should return a list of all Chinese or Italian restaurants in the Telegraph Avenue neighbourhood that are in the price range [1,2].

Implement a **multithreaded server** that can be launched from the command line using four arguments in this order:
+ a port number;
+ the name of a file that contains the restaurant details in JSON format;
+ the name of a file that contains user reviews in JSON format;
+ and the name of a file with user details in JSON format.

This server should be capable of accepting queries from many clients and executing those queries concurrently. The clients communicate with the server by sending a query (a `String`) and they receive, in JSON format, a list of restaurants that satisfies the query.

> For parsing the queries, we recommend using ANTLR to save time and simplify the development effort. If you use ANTLR then you must add the appropriate JAR files and import statements. Commit the ANTLR JAR file to your repository so that evaluation is easier.

The server should also be capable of responding to five other types of client requests:
 
1. `randomReview("Restaurant Name")`. To this request, the server should respond by providing a random review (in JSON format) for the restaurant that matches the provided name. If more than one restaurant matches the name then any restaurant that satisfies the match can be selected.
2. `getRestaurant("businessId")`. To this request, the server should respond with the restaurant details in JSON format for the restaurant that has the provided business identifier.
3. `addRestaurant("Restaurant Details in JSON format")`. The server should add a new restaurant to the database with suitable checking (for example: does another restaurant with the same name exist at the same location?).
4. `addUser("User details in JSON format")`. 
5. `addReview("Review details in JSON format")`.

For all these requests, a suitable JSON formatted string should be returned if the request is illegal/invalid/incorrect. For the methods that update the database, also generate a suitable response.

The server should be implemented using a class named `RestaurantDBServer`.

#### Grammar

The grammar for the query language is:
```
<orExpr> ::= <andExpr>(<or><andExpr>)*
<andExpr> ::= <atom>(<and><atom>)*
<atom> ::= <in>|<category>|<rating>|<price>|<name>|<LParen><orExpr><RParen>
<or> ::= "||"
<and> ::= "&&"
<in> ::= "in" <LParen><string><RParen>
<category> ::= "category" <LParen><string><RParen>
<name> ::= "name" <LParen><string><RParen>
<rating> ::= "rating" <LParen><range><RParen>
<price> ::= "price" <LParen><range><RParen>
<range> ::= [1-5]..[1-5]
<LParen> ::= "("
<RParen> ::= ")"
```

> One of the limitations of this grammar is that if we want restaurants at the price level 2 then the query will look like `price(2..2)` and this can be improved upon but we will leave this as it is for this machine problem.

### Part II: Statistical Learning

> Look at the skeleton code in the package for `statlearning`.

In this part of the machine problem you will implement two approaches to statistical machine learning: one is an instance of unsupervised learning and the second is an instance of supervised learning. Statistical learning is an exciting area for computing today!

#### k-means Clustering

Suppose you are given a set of (x, y) coordinates, you may sometimes want to group the points into _k_ clusters such that no point is closer to a point in a cluster other than the one to which it is assigned. In the case of restaurants, this approach may allow us to group restaurants that are in the same neighbourhood even without knowing anything about the neighbourhoods in a city. _A similar approach is used to group similar products on online shopping services such as Amazon._

The k-means algorithm finds k centroids within a dataset that each correspond to a cluster of inputs. To do so, k-means clustering begins by choosing k centroids at random, then alternates between the following two steps:

1. Group the restaurants into clusters, where each cluster contains all restaurants that are closest to the same centroid.
2. Compute a new centroid (average position) for each non-empty cluster.

This [visualization](http://tech.nitoyon.com/en/blog/2013/11/07/k-means/) is a good way to understand how the algorithm works.

For the k-means clustering algorithm, you should implement a method that returns a `List` of `Set`s: each `Set` representing a cluster of restaurants. You should also implement a method that converts such a `List` to JSON format as illustrated by the JSON file `voronoi.json` in the directory `visualize`.

You can run the provided visualization method using `python` (Python 3) and the visualization is called a [Voronoi tesselation](https://en.wikipedia.org/wiki/Voronoi_diagram).

> One can visualize the tessalation produced by k-means clustering by writing the JSON formatted cluster information to `voronoi.json` in the `visualize` directory and then launch `visualize.py` as follows: `python3 visualize.py`
> For the curious, you can also see some Javascript in action here.

#### Least Squares Regression

As an instance of supervised learning, you will implement an algorithm for predicting the rating that a user may give to a restaurant.

By analyzing a user's past ratings, we can try to predict what rating the user might give to a new restaurant. 

To predict ratings, you will implement simple least-squares linear regression, a widely used statistical method that approximates a relationship between some input feature (such as price) and an output value (the rating) with a line. The algorithm takes a sequence of input-output pairs and computes the slope and intercept of the line that minimizes the mean of the squared difference between the line and the outputs.

Implement the `getPredictor` method, which takes a user and a feature function (as well as the `RestaurantDB`), and returns a _function_ that predicts the users ratings as well as a regression quality estimate (`r_squared`).

One method of computing these values is by calculating the sums of squares, S<sub>xx</sub>, S<sub>yy</sub>, and S<sub>xy</sub>:

+ S<sub>xx</sub> = Σ<sub>i</sub> (x<sub>i</sub> - mean(x))<sup>2</sup>
+ S<sub>yy</sub> = Σ<sub>i</sub> (y<sub>i</sub> - mean(y))<sup>2</sup>
+ S<sub>xy</sub> = Σ<sub>i</sub> (x<sub>i</sub> - mean(x))(y<sub>i</sub> - mean(y))

After calculating the sums of squares, the regression coefficients and R<sup>2</sup> (`r_squared`) are defined as follows:

+ b = S<sub>xy</sub> / S<sub>xx</sub>
+ a = mean(y) - b * mean(x)
+ R<sup>2</sup> = S<sub>xy</sub><sup>2</sup> / (S<sub>xx</sub> S<sub>yy</sub>)

Also implement the `getBestPredictor` method that takes a user and a list of feature functions and returns the _best_ predictor function (the one that results in the highest R<sup>2</sup> value). 

Consider the following feature functions for this machine problem:
+ restaurant price scale
+ restaurant mean rating
+ restaurant location: latitude
+ restaurant location: logitude
+ restaurant category (you will have to create a mapping from category name to integer)

In this machine problem, we will use interfaces to pass and return functions but we could have also considered using [lambdas that Java 8 supports](https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html).

> To pass and return functions in this machine problem, you can have classes that implement the interface `MP5Function` which contains a single method to be implemented `f`. Different implementations of the interface will allow for different functions `f`.

### Getting Started with JSON

Here is some sample code that could get you started with processing files in JSON format.
```java
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Test {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser
					.parse(new BufferedReader(new FileReader("data/restaurants.json")).readLine());

			JSONObject jsonObject = (JSONObject) obj; 

			String name = (String) jsonObject.get("name");
			String city = (String) jsonObject.get("city");
			JSONArray categories = (JSONArray) jsonObject.get("categories");

			System.out.println("Name: " + name);
			System.out.println("City: " + city);
			System.out.print("Categories: ");
			Iterator<String> iterator = categories.iterator();
			while (iterator.hasNext()) {
				System.out.print(iterator.next()+" ");
			}
			System.out.println();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
```

### Grading Rubric
We will use the following approximate rubric to grade your work:

| Task | Grade Contribution |
|:----|---:|
| Database Implementation: Multithreaded Client/Server + `randomReview` and `getRestaurant`  | 25% |
| Database Implementation: Rich Queries | 25% |
| Database Implementation: Update Operations | 10% |
| k-means Clustering | 20% |
| Least Squares Regression | 20% |

> Clear specifications, documentation and good programming style including suitable use of functions is expected. A submission could lose up to 30% of the grade for not following good practices.

### Suggestions
+ As usual, **start early!**
+ The processing of rich queries (we are using a language that is not as sophisticated as the structured query language - SQL - used by relational databases) is likely to be more time consuming than the other tasks. You may want to complete the other tasks first.
