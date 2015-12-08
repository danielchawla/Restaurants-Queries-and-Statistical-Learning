// Generated from QueryGrammer.g4 by ANTLR 4.4

package ca.ece.ubc.cpen221.mp5.queries;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class QueryGrammerLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.4", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		OR=1, AND=2, IN=3, CATEGORY=4, NAME=5, RATING=6, PRICE=7, RANGE=8, LPAREN=9, 
		RPAREN=10, WHITESPACE=11, STRING=12;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"'\\u0000'", "'\\u0001'", "'\\u0002'", "'\\u0003'", "'\\u0004'", "'\\u0005'", 
		"'\\u0006'", "'\\u0007'", "'\b'", "'\t'", "'\n'", "'\\u000B'", "'\f'"
	};
	public static final String[] ruleNames = {
		"OR", "AND", "IN", "CATEGORY", "NAME", "RATING", "PRICE", "RANGE", "LPAREN", 
		"RPAREN", "WHITESPACE", "STRING"
	};


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


	public QueryGrammerLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "QueryGrammer.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\16|\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\3\2\3\2\3\2\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3"+
		"\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6"+
		"\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\b\3"+
		"\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\13"+
		"\3\13\3\f\6\f_\n\f\r\f\16\f`\3\f\3\f\3\r\3\r\5\rg\n\r\3\r\6\rj\n\r\r\r"+
		"\16\rk\3\r\7\ro\n\r\f\r\16\rr\13\r\3\r\5\ru\n\r\6\rw\n\r\r\r\16\rx\3\r"+
		"\3\r\2\2\16\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\3"+
		"\2\5\3\2\63\67\5\2\13\f\17\17\"\"\b\2().\60\62;C\\c|\u00eb\u00eb\u0081"+
		"\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2"+
		"\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2"+
		"\2\31\3\2\2\2\3\33\3\2\2\2\5\36\3\2\2\2\7!\3\2\2\2\t(\3\2\2\2\13\65\3"+
		"\2\2\2\r>\3\2\2\2\17I\3\2\2\2\21S\3\2\2\2\23Y\3\2\2\2\25[\3\2\2\2\27^"+
		"\3\2\2\2\31d\3\2\2\2\33\34\7~\2\2\34\35\7~\2\2\35\4\3\2\2\2\36\37\7(\2"+
		"\2\37 \7(\2\2 \6\3\2\2\2!\"\7k\2\2\"#\7p\2\2#$\3\2\2\2$%\5\23\n\2%&\5"+
		"\31\r\2&\'\5\25\13\2\'\b\3\2\2\2()\7e\2\2)*\7c\2\2*+\7v\2\2+,\7g\2\2,"+
		"-\7i\2\2-.\7q\2\2./\7t\2\2/\60\7{\2\2\60\61\3\2\2\2\61\62\5\23\n\2\62"+
		"\63\5\31\r\2\63\64\5\25\13\2\64\n\3\2\2\2\65\66\7p\2\2\66\67\7c\2\2\67"+
		"8\7o\2\289\7g\2\29:\3\2\2\2:;\5\23\n\2;<\5\31\r\2<=\5\25\13\2=\f\3\2\2"+
		"\2>?\7t\2\2?@\7c\2\2@A\7v\2\2AB\7k\2\2BC\7p\2\2CD\7i\2\2DE\3\2\2\2EF\5"+
		"\23\n\2FG\5\21\t\2GH\5\25\13\2H\16\3\2\2\2IJ\7r\2\2JK\7t\2\2KL\7k\2\2"+
		"LM\7e\2\2MN\7g\2\2NO\3\2\2\2OP\5\23\n\2PQ\5\21\t\2QR\5\25\13\2R\20\3\2"+
		"\2\2ST\t\2\2\2TU\7\60\2\2UV\7\60\2\2VW\3\2\2\2WX\t\2\2\2X\22\3\2\2\2Y"+
		"Z\7*\2\2Z\24\3\2\2\2[\\\7+\2\2\\\26\3\2\2\2]_\t\3\2\2^]\3\2\2\2_`\3\2"+
		"\2\2`^\3\2\2\2`a\3\2\2\2ab\3\2\2\2bc\b\f\2\2c\30\3\2\2\2dv\7$\2\2eg\5"+
		"\23\n\2fe\3\2\2\2fg\3\2\2\2gi\3\2\2\2hj\t\4\2\2ih\3\2\2\2jk\3\2\2\2ki"+
		"\3\2\2\2kl\3\2\2\2lp\3\2\2\2mo\5\27\f\2nm\3\2\2\2or\3\2\2\2pn\3\2\2\2"+
		"pq\3\2\2\2qt\3\2\2\2rp\3\2\2\2su\5\25\13\2ts\3\2\2\2tu\3\2\2\2uw\3\2\2"+
		"\2vf\3\2\2\2wx\3\2\2\2xv\3\2\2\2xy\3\2\2\2yz\3\2\2\2z{\7$\2\2{\32\3\2"+
		"\2\2\n\2`fikptx\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}