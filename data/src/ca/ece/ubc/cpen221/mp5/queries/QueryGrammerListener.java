// Generated from QueryGrammer.g4 by ANTLR 4.4

package ca.ece.ubc.cpen221.mp5.queries;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link QueryGrammerParser}.
 */
public interface QueryGrammerListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link QueryGrammerParser#orExpr}.
	 * @param ctx the parse tree
	 */
	void enterOrExpr(@NotNull QueryGrammerParser.OrExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryGrammerParser#orExpr}.
	 * @param ctx the parse tree
	 */
	void exitOrExpr(@NotNull QueryGrammerParser.OrExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link QueryGrammerParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterAtom(@NotNull QueryGrammerParser.AtomContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryGrammerParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitAtom(@NotNull QueryGrammerParser.AtomContext ctx);
	/**
	 * Enter a parse tree produced by {@link QueryGrammerParser#andExpr}.
	 * @param ctx the parse tree
	 */
	void enterAndExpr(@NotNull QueryGrammerParser.AndExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryGrammerParser#andExpr}.
	 * @param ctx the parse tree
	 */
	void exitAndExpr(@NotNull QueryGrammerParser.AndExprContext ctx);
}