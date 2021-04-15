// Generated from /home/covert/code/tablr/src/main/java/com/swopteam07/tablr/controller/sql/generated/Sql.g4 by ANTLR 4.7.2
package com.swopteam07.tablr.controller.sql.generated;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.LexerATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class SqlLexer extends Lexer
{
	public static final int
			T__0 = 1, T__1 = 2, T__2 = 3, T__3 = 4, T__4 = 5, T__5 = 6, WS = 7, K_EQUAL = 8, K_ST = 9,
			K_LT = 10, K_PLUS = 11, K_MINUS = 12, K_SELECT = 13, K_FROM = 14, K_WHERE = 15, K_AS = 16,
			K_INNER = 17, K_JOIN = 18, K_ON = 19, K_USING = 20, K_TRUE = 21, K_FALSE = 22, K_OR = 23,
			K_AND = 24, NUMERIC_LITERAL = 25, STRING_LITERAL = 26;
	public static final String[] ruleNames = makeRuleNames();
	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	public static final String _serializedATN =
			"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\34\u011a\b\1\4\2" +
					"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4" +
					"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22" +
					"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31" +
					"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t" +
					" \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t" +
					"+\4,\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64" +
					"\t\64\4\65\t\65\4\66\t\66\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7" +
					"\3\7\3\b\3\b\3\b\3\b\6\b~\n\b\r\b\16\b\177\3\b\3\b\3\t\3\t\3\n\3\n\3\13" +
					"\3\13\3\f\3\f\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\17\3\17\3\17" +
					"\3\17\3\17\3\20\3\20\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\22\3\22\3\22" +
					"\3\22\3\22\3\22\3\23\3\23\3\23\3\23\3\23\3\24\3\24\3\24\3\25\3\25\3\25" +
					"\3\25\3\25\3\25\3\26\3\26\3\26\3\26\3\26\3\27\3\27\3\27\3\27\3\27\3\27" +
					"\3\30\3\30\3\30\3\31\3\31\3\31\3\31\3\32\3\32\3\33\3\33\3\34\3\34\3\35" +
					"\3\35\3\36\3\36\3\37\3\37\3 \3 \3!\3!\3\"\3\"\3#\3#\3$\3$\3%\3%\3&\3&" +
					"\3\'\3\'\3(\3(\3)\3)\3*\3*\3+\3+\3,\3,\3-\3-\3.\3.\3/\3/\3\60\3\60\3\61" +
					"\3\61\3\62\3\62\3\63\3\63\3\64\3\64\3\65\5\65\u0100\n\65\3\65\3\65\7\65" +
					"\u0104\n\65\f\65\16\65\u0107\13\65\3\65\5\65\u010a\n\65\3\65\3\65\3\65" +
					"\7\65\u010f\n\65\f\65\16\65\u0112\13\65\5\65\u0114\n\65\3\66\6\66\u0117" +
					"\n\66\r\66\16\66\u0118\2\2\67\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13" +
					"\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61" +
					"\32\63\2\65\2\67\29\2;\2=\2?\2A\2C\2E\2G\2I\2K\2M\2O\2Q\2S\2U\2W\2Y\2" +
					"[\2]\2_\2a\2c\2e\2g\2i\33k\34\3\2 \4\2\13\13\"\"\3\2\62;\4\2CCcc\4\2D" +
					"Ddd\4\2EEee\4\2FFff\4\2GGgg\4\2HHhh\4\2IIii\4\2JJjj\4\2KKkk\4\2LLll\4" +
					"\2MMmm\4\2NNnn\4\2OOoo\4\2PPpp\4\2QQqq\4\2RRrr\4\2SSss\4\2TTtt\4\2UUu" +
					"u\4\2VVvv\4\2WWww\4\2XXxx\4\2YYyy\4\2ZZzz\4\2[[{{\4\2\\\\||\4\2--//\6" +
					"\2//C\\aac|\2\u0107\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2" +
					"\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3" +
					"\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2" +
					"\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2" +
					"\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2i\3\2\2\2\2k\3\2\2\2\3m\3\2\2\2\5" +
					"o\3\2\2\2\7q\3\2\2\2\ts\3\2\2\2\13u\3\2\2\2\rw\3\2\2\2\17}\3\2\2\2\21" +
					"\u0083\3\2\2\2\23\u0085\3\2\2\2\25\u0087\3\2\2\2\27\u0089\3\2\2\2\31\u008b" +
					"\3\2\2\2\33\u008d\3\2\2\2\35\u0094\3\2\2\2\37\u0099\3\2\2\2!\u009f\3\2" +
					"\2\2#\u00a2\3\2\2\2%\u00a8\3\2\2\2\'\u00ad\3\2\2\2)\u00b0\3\2\2\2+\u00b6" +
					"\3\2\2\2-\u00bb\3\2\2\2/\u00c1\3\2\2\2\61\u00c4\3\2\2\2\63\u00c8\3\2\2" +
					"\2\65\u00ca\3\2\2\2\67\u00cc\3\2\2\29\u00ce\3\2\2\2;\u00d0\3\2\2\2=\u00d2" +
					"\3\2\2\2?\u00d4\3\2\2\2A\u00d6\3\2\2\2C\u00d8\3\2\2\2E\u00da\3\2\2\2G" +
					"\u00dc\3\2\2\2I\u00de\3\2\2\2K\u00e0\3\2\2\2M\u00e2\3\2\2\2O\u00e4\3\2" +
					"\2\2Q\u00e6\3\2\2\2S\u00e8\3\2\2\2U\u00ea\3\2\2\2W\u00ec\3\2\2\2Y\u00ee" +
					"\3\2\2\2[\u00f0\3\2\2\2]\u00f2\3\2\2\2_\u00f4\3\2\2\2a\u00f6\3\2\2\2c" +
					"\u00f8\3\2\2\2e\u00fa\3\2\2\2g\u00fc\3\2\2\2i\u00ff\3\2\2\2k\u0116\3\2" +
					"\2\2mn\7.\2\2n\4\3\2\2\2op\7,\2\2p\6\3\2\2\2qr\7\60\2\2r\b\3\2\2\2st\7" +
					"*\2\2t\n\3\2\2\2uv\7+\2\2v\f\3\2\2\2wx\7$\2\2x\16\3\2\2\2y~\t\2\2\2z{" +
					"\7\17\2\2{~\7\f\2\2|~\7\f\2\2}y\3\2\2\2}z\3\2\2\2}|\3\2\2\2~\177\3\2\2" +
					"\2\177}\3\2\2\2\177\u0080\3\2\2\2\u0080\u0081\3\2\2\2\u0081\u0082\b\b" +
					"\2\2\u0082\20\3\2\2\2\u0083\u0084\7?\2\2\u0084\22\3\2\2\2\u0085\u0086" +
					"\7>\2\2\u0086\24\3\2\2\2\u0087\u0088\7@\2\2\u0088\26\3\2\2\2\u0089\u008a" +
					"\7-\2\2\u008a\30\3\2\2\2\u008b\u008c\7/\2\2\u008c\32\3\2\2\2\u008d\u008e" +
					"\5Y-\2\u008e\u008f\5=\37\2\u008f\u0090\5K&\2\u0090\u0091\5=\37\2\u0091" +
					"\u0092\59\35\2\u0092\u0093\5[.\2\u0093\34\3\2\2\2\u0094\u0095\5? \2\u0095" +
					"\u0096\5W,\2\u0096\u0097\5Q)\2\u0097\u0098\5M\'\2\u0098\36\3\2\2\2\u0099" +
					"\u009a\5a\61\2\u009a\u009b\5C\"\2\u009b\u009c\5=\37\2\u009c\u009d\5W," +
					"\2\u009d\u009e\5=\37\2\u009e \3\2\2\2\u009f\u00a0\5\65\33\2\u00a0\u00a1" +
					"\5Y-\2\u00a1\"\3\2\2\2\u00a2\u00a3\5E#\2\u00a3\u00a4\5O(\2\u00a4\u00a5" +
					"\5O(\2\u00a5\u00a6\5=\37\2\u00a6\u00a7\5W,\2\u00a7$\3\2\2\2\u00a8\u00a9" +
					"\5G$\2\u00a9\u00aa\5Q)\2\u00aa\u00ab\5E#\2\u00ab\u00ac\5O(\2\u00ac&\3" +
					"\2\2\2\u00ad\u00ae\5Q)\2\u00ae\u00af\5O(\2\u00af(\3\2\2\2\u00b0\u00b1" +
					"\5]/\2\u00b1\u00b2\5Y-\2\u00b2\u00b3\5E#\2\u00b3\u00b4\5O(\2\u00b4\u00b5" +
					"\5A!\2\u00b5*\3\2\2\2\u00b6\u00b7\5[.\2\u00b7\u00b8\5W,\2\u00b8\u00b9" +
					"\5]/\2\u00b9\u00ba\5=\37\2\u00ba,\3\2\2\2\u00bb\u00bc\5? \2\u00bc\u00bd" +
					"\5\65\33\2\u00bd\u00be\5K&\2\u00be\u00bf\5Y-\2\u00bf\u00c0\5=\37\2\u00c0" +
					".\3\2\2\2\u00c1\u00c2\5Q)\2\u00c2\u00c3\5W,\2\u00c3\60\3\2\2\2\u00c4\u00c5" +
					"\5\65\33\2\u00c5\u00c6\5O(\2\u00c6\u00c7\5;\36\2\u00c7\62\3\2\2\2\u00c8" +
					"\u00c9\t\3\2\2\u00c9\64\3\2\2\2\u00ca\u00cb\t\4\2\2\u00cb\66\3\2\2\2\u00cc" +
					"\u00cd\t\5\2\2\u00cd8\3\2\2\2\u00ce\u00cf\t\6\2\2\u00cf:\3\2\2\2\u00d0" +
					"\u00d1\t\7\2\2\u00d1<\3\2\2\2\u00d2\u00d3\t\b\2\2\u00d3>\3\2\2\2\u00d4" +
					"\u00d5\t\t\2\2\u00d5@\3\2\2\2\u00d6\u00d7\t\n\2\2\u00d7B\3\2\2\2\u00d8" +
					"\u00d9\t\13\2\2\u00d9D\3\2\2\2\u00da\u00db\t\f\2\2\u00dbF\3\2\2\2\u00dc" +
					"\u00dd\t\r\2\2\u00ddH\3\2\2\2\u00de\u00df\t\16\2\2\u00dfJ\3\2\2\2\u00e0" +
					"\u00e1\t\17\2\2\u00e1L\3\2\2\2\u00e2\u00e3\t\20\2\2\u00e3N\3\2\2\2\u00e4" +
					"\u00e5\t\21\2\2\u00e5P\3\2\2\2\u00e6\u00e7\t\22\2\2\u00e7R\3\2\2\2\u00e8" +
					"\u00e9\t\23\2\2\u00e9T\3\2\2\2\u00ea\u00eb\t\24\2\2\u00ebV\3\2\2\2\u00ec" +
					"\u00ed\t\25\2\2\u00edX\3\2\2\2\u00ee\u00ef\t\26\2\2\u00efZ\3\2\2\2\u00f0" +
					"\u00f1\t\27\2\2\u00f1\\\3\2\2\2\u00f2\u00f3\t\30\2\2\u00f3^\3\2\2\2\u00f4" +
					"\u00f5\t\31\2\2\u00f5`\3\2\2\2\u00f6\u00f7\t\32\2\2\u00f7b\3\2\2\2\u00f8" +
					"\u00f9\t\33\2\2\u00f9d\3\2\2\2\u00fa\u00fb\t\34\2\2\u00fbf\3\2\2\2\u00fc" +
					"\u00fd\t\35\2\2\u00fdh\3\2\2\2\u00fe\u0100\t\36\2\2\u00ff\u00fe\3\2\2" +
					"\2\u00ff\u0100\3\2\2\2\u0100\u0113\3\2\2\2\u0101\u0105\5\63\32\2\u0102" +
					"\u0104\5\63\32\2\u0103\u0102\3\2\2\2\u0104\u0107\3\2\2\2\u0105\u0103\3" +
					"\2\2\2\u0105\u0106\3\2\2\2\u0106\u0114\3\2\2\2\u0107\u0105\3\2\2\2\u0108" +
					"\u010a\5\63\32\2\u0109\u0108\3\2\2\2\u0109\u010a\3\2\2\2\u010a\u010b\3" +
					"\2\2\2\u010b\u010c\7\60\2\2\u010c\u0110\5\63\32\2\u010d\u010f\5\63\32" +
					"\2\u010e\u010d\3\2\2\2\u010f\u0112\3\2\2\2\u0110\u010e\3\2\2\2\u0110\u0111" +
					"\3\2\2\2\u0111\u0114\3\2\2\2\u0112\u0110\3\2\2\2\u0113\u0101\3\2\2\2\u0113" +
					"\u0109\3\2\2\2\u0114j\3\2\2\2\u0115\u0117\t\37\2\2\u0116\u0115\3\2\2\2" +
					"\u0117\u0118\3\2\2\2\u0118\u0116\3\2\2\2\u0118\u0119\3\2\2\2\u0119l\3" +
					"\2\2\2\f\2}\177\u00ff\u0105\u0109\u0110\u0113\u0116\u0118\3\2\3\2";
	public static final ATN _ATN =
			new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
			new PredictionContextCache();
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);
	public static String[] channelNames = {
			"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};
	public static String[] modeNames = {
			"DEFAULT_MODE"
	};

	static
	{
		RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION);
	}

	static
	{
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++)
		{
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null)
			{
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null)
			{
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	static
	{
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++)
		{
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}

	public SqlLexer(CharStream input)
	{
		super(input);
		_interp = new LexerATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
	}

	private static String[] makeRuleNames()
	{
		return new String[]{
				"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "WS", "K_EQUAL", "K_ST",
				"K_LT", "K_PLUS", "K_MINUS", "K_SELECT", "K_FROM", "K_WHERE", "K_AS",
				"K_INNER", "K_JOIN", "K_ON", "K_USING", "K_TRUE", "K_FALSE", "K_OR",
				"K_AND", "NUMMER", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
				"K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X",
				"Y", "Z", "NUMERIC_LITERAL", "STRING_LITERAL"
		};
	}

	private static String[] makeLiteralNames()
	{
		return new String[]{
				null, "','", "'*'", "'.'", "'('", "')'", "'\"'", null, "'='", "'<'",
				"'>'", "'+'", "'-'"
		};
	}

	private static String[] makeSymbolicNames()
	{
		return new String[]{
				null, null, null, null, null, null, null, "WS", "K_EQUAL", "K_ST", "K_LT",
				"K_PLUS", "K_MINUS", "K_SELECT", "K_FROM", "K_WHERE", "K_AS", "K_INNER",
				"K_JOIN", "K_ON", "K_USING", "K_TRUE", "K_FALSE", "K_OR", "K_AND", "NUMERIC_LITERAL",
				"STRING_LITERAL"
		};
	}

	@Override
	@Deprecated
	public String[] getTokenNames()
	{
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary()
	{
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName()
	{
		return "Sql.g4";
	}

	@Override
	public String[] getRuleNames()
	{
		return ruleNames;
	}

	@Override
	public String getSerializedATN()
	{
		return _serializedATN;
	}

	@Override
	public String[] getChannelNames()
	{
		return channelNames;
	}

	@Override
	public String[] getModeNames()
	{
		return modeNames;
	}

	@Override
	public ATN getATN()
	{
		return _ATN;
	}
}