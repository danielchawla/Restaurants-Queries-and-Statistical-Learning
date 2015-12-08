package ca.ece.ubc.cpen221.mp5.queries;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import ca.ece.ubc.cpen221.mp5.Restaurant;
import ca.ece.ubc.cpen221.mp5.RestaurantDB;

/**
 * 
 * 
 * 
 * Note: Some of this code has been reused from FormulaFactory in lab 9.
 */
public class QueryParser {
    
    private static RestaurantDB database;
    
    /**
     * @param string must contain a well-formed formula string of boolean literals and operators..
     * @return Formula corresponding to the string
     */
    public Set<Restaurant> parse(RestaurantDB restaurantDatabase, String query) {
        
        QueryParser.database = restaurantDatabase;
        
        try {
            // Create a stream of tokens using the lexer.
            CharStream stream = new ANTLRInputStream(query);
            QueryGrammerLexer lexer = new QueryGrammerLexer(stream);
            lexer.reportErrorsAsExceptions();
            TokenStream tokens = new CommonTokenStream(lexer);
            
            // Feed the tokens into the parser.
            QueryGrammerParser parser = new QueryGrammerParser(tokens);
            parser.reportErrorsAsExceptions();
            
            // Generate the parse tree using the starter rule.
            ParseTree tree = parser.orExpr(); // "root" is the starter rule.
            
            // debugging option #1: print the tree to the console
            System.err.println(tree.toStringTree(parser));

            // debugging option #2: show the tree in a window
            // ((RuleContext)tree).inspect(parser);

            // debugging option #3: walk the tree with a listener
            // new ParseTreeWalker().walk(new FormulaListener_PrintEverything(), tree);
            
            // Finally, construct a Document value by walking over the parse tree.
            ParseTreeWalker walker = new ParseTreeWalker();
            QueryListener_QueryCreator listener = new QueryListener_QueryCreator();
            walker.walk(listener, tree);
            
            // return the Document value that the listener created
            return listener.answer();
        } catch (RuntimeException e){ }
        throw new RuntimeException("Error parsing. Check syntax.");
    }
    
    /**
     * QueryListener_QueryCreator
     * Note: Some of this code is reused from Lab 9.
     */
    private static class QueryListener_QueryCreator extends QueryGrammerBaseListener {
        
        private static final int NAME_INDEX = 6;
        private static final int CATEGORY_INDEX = 10;
        private static final int PRICE_INDEX = 6;
        private static final int RATING_INDEX = 7;
        private static final int IN_INDEX = 4;
        
        private final Set<Restaurant> restaurants = Collections.synchronizedSet(new HashSet<Restaurant>());
        private final Stack<Set<Restaurant>> stack = new Stack<Set<Restaurant>>();
        
        @Override
        public void exitOrExpr(QueryGrammerParser.OrExprContext ctx) {
            if (ctx.OR() != null) {

                for (int i = 0; i < (ctx.getChildCount() - 1) / 2; i++) {
                    if (stack.size() <= 1) break;
                    Set<Restaurant> first = stack.pop();
                    Set<Restaurant> second = stack.pop();
                    first.addAll(second);
                    stack.push(first);
                }
            }
        }
        
        @Override
        public void exitAndExpr(QueryGrammerParser.AndExprContext ctx) {
            if (ctx.AND() != null) {

                for (int i = 0; i < (ctx.getChildCount() - 1) / 2; i++) {
                    if (stack.size() <= 1) break;
                    Set<Restaurant> first = stack.pop();
                    Set<Restaurant> second = stack.pop();
                    first.addAll(second);

                    stack.push(first);
                }
            }
        }
        
        @Override
        public void exitAtom(QueryGrammerParser.AtomContext ctx) {
            
            Set<Restaurant> results;
            
            String s = ctx.start.getText();
            String requestType;
            String toFind;

            switch (ctx.start.getType()) {
            
                case QueryGrammerParser.NAME:
                    requestType = s.substring(0, NAME_INDEX - 2);
                    toFind = s.substring(NAME_INDEX, s.length() - 2);
                    results = Collections.synchronizedSet(database.answerQuery(requestType, toFind));
                    stack.push(results);
                    break;
    
                case QueryGrammerParser.CATEGORY:
                    requestType = s.substring(0, CATEGORY_INDEX - 2);
                    toFind = s.substring(CATEGORY_INDEX, s.length() - 2);
                    results = Collections.synchronizedSet(database.answerQuery(requestType, toFind));
                    stack.push(results);
                    break;
    
                case QueryGrammerParser.PRICE:
                    requestType = s.substring(0, PRICE_INDEX - 1);
                    toFind = s.substring(PRICE_INDEX, s.length() - 1);
                    results = Collections.synchronizedSet(database.answerQuery(requestType, toFind));
                    stack.push(results);
                    break;
    
                case QueryGrammerParser.RATING:
                    requestType = s.substring(0, RATING_INDEX - 1);
                    toFind = s.substring(RATING_INDEX, s.length() - 1);
                    results = Collections.synchronizedSet(database.answerQuery(requestType, toFind));
                    stack.push(results);
                    break;
    
                case QueryGrammerParser.IN:
                    requestType = s.substring(0, IN_INDEX - 2);
                    toFind = s.substring(IN_INDEX, s.length() - 2);
                    results = Collections.synchronizedSet(database.answerQuery(requestType, toFind));
                    stack.push(results);
                    break;
                default:
                    break;
            }
        }
        
        /**
         * @return threadsafe immutable set of restaurants
         */
        public Set<Restaurant> answer() {
            restaurants.addAll(stack.get(0));
            return Collections.synchronizedSet(Collections.unmodifiableSet(restaurants));
        }
    }
}  

    
/**
 * QueryGrammerListener_PrintEverything
 * Note: Most of this is reused code from Lab 9.
 */
class QueryGrammerListener_PrintEverything extends QueryGrammerBaseListener {
    
    public void enterOrExpr(QueryGrammerParser.OrExprContext ctx) { 
        System.err.println("entering OR expression " + ctx.getText()); }
    public void exitOrExpr(QueryGrammerParser.OrExprContext ctx) { 
        System.err.println("exiting OR expression " + ctx.getText()); }

    public void enterAndExpr(QueryGrammerParser.AndExprContext ctx) { 
        System.err.println("entering AND expression " + ctx.getText()); }
    public void exitAndExpr(QueryGrammerParser.AndExprContext ctx) { 
        System.err.println("exiting AND expression " + ctx.getText()); }

    public void enterAtom(QueryGrammerParser.AtomContext ctx) { 
        System.err.println("entering atom " + ctx.getText()); }
    public void exitAtom(QueryGrammerParser.AtomContext ctx) { 
        System.err.println("exiting atom " + ctx.getText()); }
}
