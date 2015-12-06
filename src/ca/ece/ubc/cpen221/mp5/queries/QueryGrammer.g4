grammar QueryGrammer;

@header {
package ca.ece.ubc.cpen221.mp5.queries;
}

@members {
    public void reportErrorsAsExceptions() {        
        addErrorListener(new ExceptionThrowingErrorListener());
    }
    
    private static class ExceptionThrowingErrorListener extends BaseErrorListener {
        @Override
        public void syntaxError(Recognizer<?, ?> recognizer,
                Object offendingSymbol, int line, int charPositionInLine,
                String msg, RecognitionException e) {
            throw new RuntimeException(msg);
        }
    }
}

/*
 * These are the lexical rules. They define the tokens used by the lexer.
 *   *** Antlr requires tokens to be CAPITALIZED, like START_ITALIC, END_ITALIC, and TEXT.
 */
OR : '||';
AND : '&&';
IN : 'in' LPAREN STRING RPAREN;
CATEGORY : 'category' LPAREN STRING RPAREN;
NAME : 'name' LPAREN STRING RPAREN;
RATING : 'rating' LPAREN RANGE RPAREN;
PRICE : 'price' LPAREN RANGE RPAREN;
RANGE : [1-5]'..'[1-5];
LPAREN : '(' ;
RPAREN : ')' ;
WHITESPACE : [ \t\r\n]+ -> skip ;
STRING : '"'(LPAREN?([a-z]|[A-Z]|[0-9]|'&'|','|'\''|'-'|'.')+ WHITESPACE* RPAREN?)+ '"';

/*
 * These are the parser rules. They define the structures used by the parser.
 *    *** Antlr requires grammar nonterminals to be lowercase, like html, normal, and italic.
 */


orExpr : andExpr (OR andExpr)*;
andExpr : atom (AND atom)*;
atom : IN | CATEGORY | RATING | PRICE | NAME | (LPAREN orExpr RPAREN);