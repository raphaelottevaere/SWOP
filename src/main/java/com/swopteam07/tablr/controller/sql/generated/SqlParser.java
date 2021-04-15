// Generated from /home/covert/code/tablr/src/main/java/com/swopteam07/tablr/controller/sql/generated/Sql.g4 by ANTLR 4.7.2
package com.swopteam07.tablr.controller.sql.generated;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class SqlParser extends Parser
{
	public static final int
			T__0 = 1, T__1 = 2, T__2 = 3, T__3 = 4, T__4 = 5, T__5 = 6, WS = 7, K_EQUAL = 8, K_ST = 9,
			K_LT = 10, K_PLUS = 11, K_MINUS = 12, K_SELECT = 13, K_FROM = 14, K_WHERE = 15, K_AS = 16,
			K_INNER = 17, K_JOIN = 18, K_ON = 19, K_USING = 20, K_TRUE = 21, K_FALSE = 22, K_OR = 23,
			K_AND = 24, NUMERIC_LITERAL = 25, STRING_LITERAL = 26;
	public static final int
			RULE_query = 0, RULE_select_stmnt = 1, RULE_from_stmnt = 2, RULE_where_stmnt = 3,
			RULE_result_column = 4, RULE_table = 5, RULE_join_constraint = 6, RULE_join_operator = 7,
			RULE_join_clause = 8, RULE_value_expr = 9, RULE_literal_value = 10, RULE_boolean_value = 11,
			RULE_alias = 12, RULE_expr_operator = 13, RULE_cell_name = 14, RULE_column_name = 15,
			RULE_table_name = 16, RULE_database_name = 17, RULE_any_name = 18;
	public static final String[] ruleNames = makeRuleNames();
	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	public static final String _serializedATN =
			"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\34\u00bb\4\2\t\2" +
					"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13" +
					"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22" +
					"\4\23\t\23\4\24\t\24\3\2\3\2\3\2\5\2,\n\2\3\2\3\2\3\3\3\3\3\3\3\3\7\3" +
					"\64\n\3\f\3\16\3\67\13\3\3\4\3\4\3\4\5\4<\n\4\3\5\3\5\3\5\3\6\3\6\3\6" +
					"\3\6\3\6\3\6\3\6\3\6\3\6\5\6J\n\6\3\6\3\6\3\6\5\6O\n\6\3\7\3\7\3\7\5\7" +
					"T\n\7\3\7\3\7\5\7X\n\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\7\bd\n" +
					"\b\f\b\16\bg\13\b\3\b\3\b\5\bk\n\b\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n" +
					"\3\n\3\n\3\n\3\n\3\n\7\n{\n\n\f\n\16\n~\13\n\3\13\3\13\3\13\3\13\5\13" +
					"\u0084\n\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\5\13\u008d\n\13\7\13\u008f" +
					"\n\13\f\13\16\13\u0092\13\13\3\f\3\f\3\f\3\f\3\f\5\f\u0099\n\f\3\r\3\r" +
					"\3\16\3\16\3\16\3\17\3\17\3\20\3\20\3\20\5\20\u00a5\n\20\3\20\3\20\3\20" +
					"\5\20\u00aa\n\20\3\20\3\20\3\21\3\21\3\22\3\22\3\23\3\23\3\24\3\24\3\24" +
					"\3\24\3\24\5\24\u00b9\n\24\3\24\2\4\22\24\25\2\4\6\b\n\f\16\20\22\24\26" +
					"\30\32\34\36 \"$&\2\4\3\2\27\30\4\2\n\16\31\32\2\u00bc\2(\3\2\2\2\4/\3" +
					"\2\2\2\68\3\2\2\2\b=\3\2\2\2\nN\3\2\2\2\fS\3\2\2\2\16j\3\2\2\2\20l\3\2" +
					"\2\2\22o\3\2\2\2\24\u0083\3\2\2\2\26\u0098\3\2\2\2\30\u009a\3\2\2\2\32" +
					"\u009c\3\2\2\2\34\u009f\3\2\2\2\36\u00a9\3\2\2\2 \u00ad\3\2\2\2\"\u00af" +
					"\3\2\2\2$\u00b1\3\2\2\2&\u00b8\3\2\2\2()\5\4\3\2)+\5\6\4\2*,\5\b\5\2+" +
					"*\3\2\2\2+,\3\2\2\2,-\3\2\2\2-.\7\2\2\3.\3\3\2\2\2/\60\7\17\2\2\60\65" +
					"\5\n\6\2\61\62\7\3\2\2\62\64\5\n\6\2\63\61\3\2\2\2\64\67\3\2\2\2\65\63" +
					"\3\2\2\2\65\66\3\2\2\2\66\5\3\2\2\2\67\65\3\2\2\28;\7\20\2\29<\5\f\7\2" +
					":<\5\22\n\2;9\3\2\2\2;:\3\2\2\2<\7\3\2\2\2=>\7\21\2\2>?\5\24\13\2?\t\3" +
					"\2\2\2@O\7\4\2\2AB\5\"\22\2BC\7\5\2\2CD\7\4\2\2DO\3\2\2\2EF\5\"\22\2F" +
					"G\7\5\2\2GI\5 \21\2HJ\5\32\16\2IH\3\2\2\2IJ\3\2\2\2JO\3\2\2\2KL\5\"\22" +
					"\2LM\5\32\16\2MO\3\2\2\2N@\3\2\2\2NA\3\2\2\2NE\3\2\2\2NK\3\2\2\2O\13\3" +
					"\2\2\2PQ\5$\23\2QR\7\5\2\2RT\3\2\2\2SP\3\2\2\2ST\3\2\2\2TU\3\2\2\2UW\5" +
					"\"\22\2VX\5\32\16\2WV\3\2\2\2WX\3\2\2\2X\r\3\2\2\2YZ\7\25\2\2Z[\5\36\20" +
					"\2[\\\7\n\2\2\\]\5\36\20\2]k\3\2\2\2^_\7\26\2\2_`\7\6\2\2`e\5 \21\2ab" +
					"\7\3\2\2bd\5 \21\2ca\3\2\2\2dg\3\2\2\2ec\3\2\2\2ef\3\2\2\2fh\3\2\2\2g" +
					"e\3\2\2\2hi\7\7\2\2ik\3\2\2\2jY\3\2\2\2j^\3\2\2\2jk\3\2\2\2k\17\3\2\2" +
					"\2lm\7\23\2\2mn\7\24\2\2n\21\3\2\2\2op\b\n\1\2pq\5\f\7\2qr\5\20\t\2rs" +
					"\5\f\7\2st\5\16\b\2t|\3\2\2\2uv\f\3\2\2vw\5\20\t\2wx\5\f\7\2xy\5\16\b" +
					"\2y{\3\2\2\2zu\3\2\2\2{~\3\2\2\2|z\3\2\2\2|}\3\2\2\2}\23\3\2\2\2~|\3\2" +
					"\2\2\177\u0080\b\13\1\2\u0080\u0084\5\26\f\2\u0081\u0084\5\30\r\2\u0082" +
					"\u0084\5\36\20\2\u0083\177\3\2\2\2\u0083\u0081\3\2\2\2\u0083\u0082\3\2" +
					"\2\2\u0084\u0090\3\2\2\2\u0085\u0086\f\6\2\2\u0086\u008c\5\34\17\2\u0087" +
					"\u0088\7\6\2\2\u0088\u0089\5\24\13\2\u0089\u008a\7\7\2\2\u008a\u008d\3" +
					"\2\2\2\u008b\u008d\5\24\13\2\u008c\u0087\3\2\2\2\u008c\u008b\3\2\2\2\u008d" +
					"\u008f\3\2\2\2\u008e\u0085\3\2\2\2\u008f\u0092\3\2\2\2\u0090\u008e\3\2" +
					"\2\2\u0090\u0091\3\2\2\2\u0091\25\3\2\2\2\u0092\u0090\3\2\2\2\u0093\u0099" +
					"\7\33\2\2\u0094\u0095\7\b\2\2\u0095\u0096\5&\24\2\u0096\u0097\7\b\2\2" +
					"\u0097\u0099\3\2\2\2\u0098\u0093\3\2\2\2\u0098\u0094\3\2\2\2\u0099\27" +
					"\3\2\2\2\u009a\u009b\t\2\2\2\u009b\31\3\2\2\2\u009c\u009d\7\22\2\2\u009d" +
					"\u009e\5&\24\2\u009e\33\3\2\2\2\u009f\u00a0\t\3\2\2\u00a0\35\3\2\2\2\u00a1" +
					"\u00a2\5$\23\2\u00a2\u00a3\7\5\2\2\u00a3\u00a5\3\2\2\2\u00a4\u00a1\3\2" +
					"\2\2\u00a4\u00a5\3\2\2\2\u00a5\u00a6\3\2\2\2\u00a6\u00a7\5\"\22\2\u00a7" +
					"\u00a8\7\5\2\2\u00a8\u00aa\3\2\2\2\u00a9\u00a4\3\2\2\2\u00a9\u00aa\3\2" +
					"\2\2\u00aa\u00ab\3\2\2\2\u00ab\u00ac\5 \21\2\u00ac\37\3\2\2\2\u00ad\u00ae" +
					"\5&\24\2\u00ae!\3\2\2\2\u00af\u00b0\5&\24\2\u00b0#\3\2\2\2\u00b1\u00b2" +
					"\5&\24\2\u00b2%\3\2\2\2\u00b3\u00b9\7\34\2\2\u00b4\u00b5\7\6\2\2\u00b5" +
					"\u00b6\5&\24\2\u00b6\u00b7\7\7\2\2\u00b7\u00b9\3\2\2\2\u00b8\u00b3\3\2" +
					"\2\2\u00b8\u00b4\3\2\2\2\u00b9\'\3\2\2\2\23+\65;INSWej|\u0083\u008c\u0090" +
					"\u0098\u00a4\u00a9\u00b8";
	public static final ATN _ATN =
			new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
			new PredictionContextCache();
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

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

	public SqlParser(TokenStream input)
	{
		super(input);
		_interp = new ParserATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
	}

	private static String[] makeRuleNames()
	{
		return new String[]{
				"query", "select_stmnt", "from_stmnt", "where_stmnt", "result_column",
				"table", "join_constraint", "join_operator", "join_clause", "value_expr",
				"literal_value", "boolean_value", "alias", "expr_operator", "cell_name",
				"column_name", "table_name", "database_name", "any_name"
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
	public ATN getATN()
	{
		return _ATN;
	}

	public final QueryContext query() throws RecognitionException
	{
		QueryContext _localctx = new QueryContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_query);
		int _la;
		try
		{
			enterOuterAlt(_localctx, 1);
			{
				setState(38);
				((QueryContext) _localctx).select = select_stmnt();
				setState(39);
				((QueryContext) _localctx).from = from_stmnt();
				setState(41);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la == K_WHERE)
				{
					{
						setState(40);
						((QueryContext) _localctx).where = where_stmnt();
					}
				}

				setState(43);
				match(EOF);
			}
		} catch (RecognitionException re)
		{
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally
		{
			exitRule();
		}
		return _localctx;
	}

	public final Select_stmntContext select_stmnt() throws RecognitionException
	{
		Select_stmntContext _localctx = new Select_stmntContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_select_stmnt);
		int _la;
		try
		{
			enterOuterAlt(_localctx, 1);
			{
				setState(45);
				match(K_SELECT);
				setState(46);
				result_column();
				setState(51);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la == T__0)
				{
					{
						{
							setState(47);
							match(T__0);
							setState(48);
							result_column();
						}
					}
					setState(53);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
			}
		} catch (RecognitionException re)
		{
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally
		{
			exitRule();
		}
		return _localctx;
	}

	public final From_stmntContext from_stmnt() throws RecognitionException
	{
		From_stmntContext _localctx = new From_stmntContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_from_stmnt);
		try
		{
			enterOuterAlt(_localctx, 1);
			{
				setState(54);
				match(K_FROM);
				setState(57);
				_errHandler.sync(this);
				switch (getInterpreter().adaptivePredict(_input, 2, _ctx))
				{
					case 1:
					{
						setState(55);
						table();
					}
					break;
					case 2:
					{
						setState(56);
						join_clause(0);
					}
					break;
				}
			}
		} catch (RecognitionException re)
		{
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally
		{
			exitRule();
		}
		return _localctx;
	}

	public final Where_stmntContext where_stmnt() throws RecognitionException
	{
		Where_stmntContext _localctx = new Where_stmntContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_where_stmnt);
		try
		{
			enterOuterAlt(_localctx, 1);
			{
				setState(59);
				match(K_WHERE);
				setState(60);
				value_expr(0);
			}
		} catch (RecognitionException re)
		{
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally
		{
			exitRule();
		}
		return _localctx;
	}

	public final Result_columnContext result_column() throws RecognitionException
	{
		Result_columnContext _localctx = new Result_columnContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_result_column);
		int _la;
		try
		{
			setState(76);
			_errHandler.sync(this);
			switch (getInterpreter().adaptivePredict(_input, 4, _ctx))
			{
				case 1:
					enterOuterAlt(_localctx, 1);
				{
					setState(62);
					match(T__1);
				}
				break;
				case 2:
					enterOuterAlt(_localctx, 2);
				{
					setState(63);
					table_name();
					setState(64);
					match(T__2);
					setState(65);
					match(T__1);
				}
				break;
				case 3:
					enterOuterAlt(_localctx, 3);
				{
					setState(67);
					table_name();
					setState(68);
					match(T__2);
					setState(69);
					column_name();
					setState(71);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la == K_AS)
					{
						{
							setState(70);
							alias();
						}
					}

				}
				break;
				case 4:
					enterOuterAlt(_localctx, 4);
				{
					setState(73);
					table_name();
					setState(74);
					alias();
				}
				break;
			}
		} catch (RecognitionException re)
		{
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally
		{
			exitRule();
		}
		return _localctx;
	}

	public final TableContext table() throws RecognitionException
	{
		TableContext _localctx = new TableContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_table);
		try
		{
			enterOuterAlt(_localctx, 1);
			{
				setState(81);
				_errHandler.sync(this);
				switch (getInterpreter().adaptivePredict(_input, 5, _ctx))
				{
					case 1:
					{
						setState(78);
						database_name();
						setState(79);
						match(T__2);
					}
					break;
				}
				setState(83);
				table_name();
				setState(85);
				_errHandler.sync(this);
				switch (getInterpreter().adaptivePredict(_input, 6, _ctx))
				{
					case 1:
					{
						setState(84);
						alias();
					}
					break;
				}
			}
		} catch (RecognitionException re)
		{
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally
		{
			exitRule();
		}
		return _localctx;
	}

	public final Join_constraintContext join_constraint() throws RecognitionException
	{
		Join_constraintContext _localctx = new Join_constraintContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_join_constraint);
		int _la;
		try
		{
			enterOuterAlt(_localctx, 1);
			{
				setState(104);
				_errHandler.sync(this);
				switch (getInterpreter().adaptivePredict(_input, 8, _ctx))
				{
					case 1:
					{
						setState(87);
						match(K_ON);
						setState(88);
						cell_name();
						setState(89);
						match(K_EQUAL);
						setState(90);
						cell_name();
					}
					break;
					case 2:
					{
						setState(92);
						match(K_USING);
						setState(93);
						match(T__3);
						setState(94);
						column_name();
						setState(99);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la == T__0)
						{
							{
								{
									setState(95);
									match(T__0);
									setState(96);
									column_name();
								}
							}
							setState(101);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						setState(102);
						match(T__4);
					}
					break;
				}
			}
		} catch (RecognitionException re)
		{
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally
		{
			exitRule();
		}
		return _localctx;
	}

	public final Join_operatorContext join_operator() throws RecognitionException
	{
		Join_operatorContext _localctx = new Join_operatorContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_join_operator);
		try
		{
			enterOuterAlt(_localctx, 1);
			{
				setState(106);
				match(K_INNER);
				setState(107);
				match(K_JOIN);
			}
		} catch (RecognitionException re)
		{
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally
		{
			exitRule();
		}
		return _localctx;
	}

	public final Join_clauseContext join_clause() throws RecognitionException
	{
		return join_clause(0);
	}

	private Join_clauseContext join_clause(int _p) throws RecognitionException
	{
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Join_clauseContext _localctx = new Join_clauseContext(_ctx, _parentState);
		Join_clauseContext _prevctx = _localctx;
		int _startState = 16;
		enterRecursionRule(_localctx, 16, RULE_join_clause, _p);
		try
		{
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
				{
					setState(110);
					table();
					setState(111);
					join_operator();
					setState(112);
					table();
					setState(113);
					join_constraint();
				}
				_ctx.stop = _input.LT(-1);
				setState(122);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input, 9, _ctx);
				while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER)
				{
					if (_alt == 1)
					{
						if (_parseListeners != null) triggerExitRuleEvent();
						_prevctx = _localctx;
						{
							{
								_localctx = new Join_clauseContext(_parentctx, _parentState);
								pushNewRecursionContext(_localctx, _startState, RULE_join_clause);
								setState(115);
								if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
								setState(116);
								join_operator();
								setState(117);
								table();
								setState(118);
								join_constraint();
							}
						}
					}
					setState(124);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input, 9, _ctx);
				}
			}
		} catch (RecognitionException re)
		{
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally
		{
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public final Value_exprContext value_expr() throws RecognitionException
	{
		return value_expr(0);
	}

	private Value_exprContext value_expr(int _p) throws RecognitionException
	{
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Value_exprContext _localctx = new Value_exprContext(_ctx, _parentState);
		Value_exprContext _prevctx = _localctx;
		int _startState = 18;
		enterRecursionRule(_localctx, 18, RULE_value_expr, _p);
		try
		{
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
				setState(129);
				_errHandler.sync(this);
				switch (_input.LA(1))
				{
					case T__5:
					case NUMERIC_LITERAL:
					{
						setState(126);
						literal_value();
					}
					break;
					case K_TRUE:
					case K_FALSE:
					{
						setState(127);
						boolean_value();
					}
					break;
					case T__3:
					case STRING_LITERAL:
					{
						setState(128);
						cell_name();
					}
					break;
					default:
						throw new NoViableAltException(this);
				}
				_ctx.stop = _input.LT(-1);
				setState(142);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input, 12, _ctx);
				while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER)
				{
					if (_alt == 1)
					{
						if (_parseListeners != null) triggerExitRuleEvent();
						_prevctx = _localctx;
						{
							{
								_localctx = new Value_exprContext(_parentctx, _parentState);
								_localctx.left = _prevctx;
								_localctx.left = _prevctx;
								pushNewRecursionContext(_localctx, _startState, RULE_value_expr);
								setState(131);
								if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
								setState(132);
								((Value_exprContext) _localctx).op = expr_operator();
								setState(138);
								_errHandler.sync(this);
								switch (getInterpreter().adaptivePredict(_input, 11, _ctx))
								{
									case 1:
									{
										setState(133);
										match(T__3);
										setState(134);
										((Value_exprContext) _localctx).right = value_expr(0);
										setState(135);
										match(T__4);
									}
									break;
									case 2:
									{
										setState(137);
										((Value_exprContext) _localctx).right = value_expr(0);
									}
									break;
								}
							}
						}
					}
					setState(144);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input, 12, _ctx);
				}
			}
		} catch (RecognitionException re)
		{
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally
		{
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public final Literal_valueContext literal_value() throws RecognitionException
	{
		Literal_valueContext _localctx = new Literal_valueContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_literal_value);
		try
		{
			setState(150);
			_errHandler.sync(this);
			switch (_input.LA(1))
			{
				case NUMERIC_LITERAL:
					enterOuterAlt(_localctx, 1);
				{
					setState(145);
					match(NUMERIC_LITERAL);
				}
				break;
				case T__5:
					enterOuterAlt(_localctx, 2);
				{
					setState(146);
					match(T__5);
					setState(147);
					any_name();
					setState(148);
					match(T__5);
				}
				break;
				default:
					throw new NoViableAltException(this);
			}
		} catch (RecognitionException re)
		{
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally
		{
			exitRule();
		}
		return _localctx;
	}

	public final Boolean_valueContext boolean_value() throws RecognitionException
	{
		Boolean_valueContext _localctx = new Boolean_valueContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_boolean_value);
		int _la;
		try
		{
			enterOuterAlt(_localctx, 1);
			{
				setState(152);
				_la = _input.LA(1);
				if (!(_la == K_TRUE || _la == K_FALSE))
				{
					_errHandler.recoverInline(this);
				} else
				{
					if (_input.LA(1) == Token.EOF) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
			}
		} catch (RecognitionException re)
		{
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally
		{
			exitRule();
		}
		return _localctx;
	}

	public final AliasContext alias() throws RecognitionException
	{
		AliasContext _localctx = new AliasContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_alias);
		try
		{
			enterOuterAlt(_localctx, 1);
			{
				setState(154);
				match(K_AS);
				setState(155);
				any_name();
			}
		} catch (RecognitionException re)
		{
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally
		{
			exitRule();
		}
		return _localctx;
	}

	public final Expr_operatorContext expr_operator() throws RecognitionException
	{
		Expr_operatorContext _localctx = new Expr_operatorContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_expr_operator);
		int _la;
		try
		{
			enterOuterAlt(_localctx, 1);
			{
				setState(157);
				_la = _input.LA(1);
				if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << K_EQUAL) | (1L << K_ST) | (1L << K_LT) | (1L << K_PLUS) | (1L << K_MINUS) | (1L << K_OR) | (1L << K_AND))) != 0)))
				{
					_errHandler.recoverInline(this);
				} else
				{
					if (_input.LA(1) == Token.EOF) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
			}
		} catch (RecognitionException re)
		{
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally
		{
			exitRule();
		}
		return _localctx;
	}

	public final Cell_nameContext cell_name() throws RecognitionException
	{
		Cell_nameContext _localctx = new Cell_nameContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_cell_name);
		try
		{
			enterOuterAlt(_localctx, 1);
			{
				setState(167);
				_errHandler.sync(this);
				switch (getInterpreter().adaptivePredict(_input, 15, _ctx))
				{
					case 1:
					{
						setState(162);
						_errHandler.sync(this);
						switch (getInterpreter().adaptivePredict(_input, 14, _ctx))
						{
							case 1:
							{
								setState(159);
								database_name();
								setState(160);
								match(T__2);
							}
							break;
						}
						setState(164);
						table_name();
						setState(165);
						match(T__2);
					}
					break;
				}
				setState(169);
				column_name();
			}
		} catch (RecognitionException re)
		{
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally
		{
			exitRule();
		}
		return _localctx;
	}

	public final Column_nameContext column_name() throws RecognitionException
	{
		Column_nameContext _localctx = new Column_nameContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_column_name);
		try
		{
			enterOuterAlt(_localctx, 1);
			{
				setState(171);
				any_name();
			}
		} catch (RecognitionException re)
		{
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally
		{
			exitRule();
		}
		return _localctx;
	}

	public final Table_nameContext table_name() throws RecognitionException
	{
		Table_nameContext _localctx = new Table_nameContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_table_name);
		try
		{
			enterOuterAlt(_localctx, 1);
			{
				setState(173);
				any_name();
			}
		} catch (RecognitionException re)
		{
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally
		{
			exitRule();
		}
		return _localctx;
	}

	public final Database_nameContext database_name() throws RecognitionException
	{
		Database_nameContext _localctx = new Database_nameContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_database_name);
		try
		{
			enterOuterAlt(_localctx, 1);
			{
				setState(175);
				any_name();
			}
		} catch (RecognitionException re)
		{
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally
		{
			exitRule();
		}
		return _localctx;
	}

	public final Any_nameContext any_name() throws RecognitionException
	{
		Any_nameContext _localctx = new Any_nameContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_any_name);
		try
		{
			setState(182);
			_errHandler.sync(this);
			switch (_input.LA(1))
			{
				case STRING_LITERAL:
					enterOuterAlt(_localctx, 1);
				{
					setState(177);
					match(STRING_LITERAL);
				}
				break;
				case T__3:
					enterOuterAlt(_localctx, 2);
				{
					setState(178);
					match(T__3);
					setState(179);
					any_name();
					setState(180);
					match(T__4);
				}
				break;
				default:
					throw new NoViableAltException(this);
			}
		} catch (RecognitionException re)
		{
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally
		{
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex)
	{
		switch (ruleIndex)
		{
			case 8:
				return join_clause_sempred((Join_clauseContext) _localctx, predIndex);
			case 9:
				return value_expr_sempred((Value_exprContext) _localctx, predIndex);
		}
		return true;
	}

	private boolean join_clause_sempred(Join_clauseContext _localctx, int predIndex)
	{
		switch (predIndex)
		{
			case 0:
				return precpred(_ctx, 1);
		}
		return true;
	}

	private boolean value_expr_sempred(Value_exprContext _localctx, int predIndex)
	{
		switch (predIndex)
		{
			case 1:
				return precpred(_ctx, 4);
		}
		return true;
	}

	public static class QueryContext extends ParserRuleContext
	{
		public Select_stmntContext select;
		public From_stmntContext from;
		public Where_stmntContext where;

		public QueryContext(ParserRuleContext parent, int invokingState)
		{
			super(parent, invokingState);
		}

		public TerminalNode EOF()
		{
			return getToken(SqlParser.EOF, 0);
		}

		public Select_stmntContext select_stmnt()
		{
			return getRuleContext(Select_stmntContext.class, 0);
		}

		public From_stmntContext from_stmnt()
		{
			return getRuleContext(From_stmntContext.class, 0);
		}

		public Where_stmntContext where_stmnt()
		{
			return getRuleContext(Where_stmntContext.class, 0);
		}

		@Override
		public int getRuleIndex()
		{
			return RULE_query;
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor)
		{
			if (visitor instanceof SqlVisitor) return ((SqlVisitor<? extends T>) visitor).visitQuery(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class Select_stmntContext extends ParserRuleContext
	{
		public Select_stmntContext(ParserRuleContext parent, int invokingState)
		{
			super(parent, invokingState);
		}

		public TerminalNode K_SELECT()
		{
			return getToken(SqlParser.K_SELECT, 0);
		}

		public List<Result_columnContext> result_column()
		{
			return getRuleContexts(Result_columnContext.class);
		}

		public Result_columnContext result_column(int i)
		{
			return getRuleContext(Result_columnContext.class, i);
		}

		@Override
		public int getRuleIndex()
		{
			return RULE_select_stmnt;
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor)
		{
			if (visitor instanceof SqlVisitor) return ((SqlVisitor<? extends T>) visitor).visitSelect_stmnt(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class From_stmntContext extends ParserRuleContext
	{
		public From_stmntContext(ParserRuleContext parent, int invokingState)
		{
			super(parent, invokingState);
		}

		public TerminalNode K_FROM()
		{
			return getToken(SqlParser.K_FROM, 0);
		}

		public TableContext table()
		{
			return getRuleContext(TableContext.class, 0);
		}

		public Join_clauseContext join_clause()
		{
			return getRuleContext(Join_clauseContext.class, 0);
		}

		@Override
		public int getRuleIndex()
		{
			return RULE_from_stmnt;
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor)
		{
			if (visitor instanceof SqlVisitor) return ((SqlVisitor<? extends T>) visitor).visitFrom_stmnt(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class Where_stmntContext extends ParserRuleContext
	{
		public Where_stmntContext(ParserRuleContext parent, int invokingState)
		{
			super(parent, invokingState);
		}

		public TerminalNode K_WHERE()
		{
			return getToken(SqlParser.K_WHERE, 0);
		}

		public Value_exprContext value_expr()
		{
			return getRuleContext(Value_exprContext.class, 0);
		}

		@Override
		public int getRuleIndex()
		{
			return RULE_where_stmnt;
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor)
		{
			if (visitor instanceof SqlVisitor) return ((SqlVisitor<? extends T>) visitor).visitWhere_stmnt(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class Result_columnContext extends ParserRuleContext
	{
		public Result_columnContext(ParserRuleContext parent, int invokingState)
		{
			super(parent, invokingState);
		}

		public Table_nameContext table_name()
		{
			return getRuleContext(Table_nameContext.class, 0);
		}

		public Column_nameContext column_name()
		{
			return getRuleContext(Column_nameContext.class, 0);
		}

		public AliasContext alias()
		{
			return getRuleContext(AliasContext.class, 0);
		}

		@Override
		public int getRuleIndex()
		{
			return RULE_result_column;
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor)
		{
			if (visitor instanceof SqlVisitor) return ((SqlVisitor<? extends T>) visitor).visitResult_column(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class TableContext extends ParserRuleContext
	{
		public TableContext(ParserRuleContext parent, int invokingState)
		{
			super(parent, invokingState);
		}

		public Table_nameContext table_name()
		{
			return getRuleContext(Table_nameContext.class, 0);
		}

		public Database_nameContext database_name()
		{
			return getRuleContext(Database_nameContext.class, 0);
		}

		public AliasContext alias()
		{
			return getRuleContext(AliasContext.class, 0);
		}

		@Override
		public int getRuleIndex()
		{
			return RULE_table;
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor)
		{
			if (visitor instanceof SqlVisitor) return ((SqlVisitor<? extends T>) visitor).visitTable(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class Join_constraintContext extends ParserRuleContext
	{
		public Join_constraintContext(ParserRuleContext parent, int invokingState)
		{
			super(parent, invokingState);
		}

		public TerminalNode K_ON()
		{
			return getToken(SqlParser.K_ON, 0);
		}

		public List<Cell_nameContext> cell_name()
		{
			return getRuleContexts(Cell_nameContext.class);
		}

		public Cell_nameContext cell_name(int i)
		{
			return getRuleContext(Cell_nameContext.class, i);
		}

		public TerminalNode K_EQUAL()
		{
			return getToken(SqlParser.K_EQUAL, 0);
		}

		public TerminalNode K_USING()
		{
			return getToken(SqlParser.K_USING, 0);
		}

		public List<Column_nameContext> column_name()
		{
			return getRuleContexts(Column_nameContext.class);
		}

		public Column_nameContext column_name(int i)
		{
			return getRuleContext(Column_nameContext.class, i);
		}

		@Override
		public int getRuleIndex()
		{
			return RULE_join_constraint;
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor)
		{
			if (visitor instanceof SqlVisitor) return ((SqlVisitor<? extends T>) visitor).visitJoin_constraint(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class Join_operatorContext extends ParserRuleContext
	{
		public Join_operatorContext(ParserRuleContext parent, int invokingState)
		{
			super(parent, invokingState);
		}

		public TerminalNode K_INNER()
		{
			return getToken(SqlParser.K_INNER, 0);
		}

		public TerminalNode K_JOIN()
		{
			return getToken(SqlParser.K_JOIN, 0);
		}

		@Override
		public int getRuleIndex()
		{
			return RULE_join_operator;
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor)
		{
			if (visitor instanceof SqlVisitor) return ((SqlVisitor<? extends T>) visitor).visitJoin_operator(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class Join_clauseContext extends ParserRuleContext
	{
		public Join_clauseContext(ParserRuleContext parent, int invokingState)
		{
			super(parent, invokingState);
		}

		public List<TableContext> table()
		{
			return getRuleContexts(TableContext.class);
		}

		public TableContext table(int i)
		{
			return getRuleContext(TableContext.class, i);
		}

		public Join_operatorContext join_operator()
		{
			return getRuleContext(Join_operatorContext.class, 0);
		}

		public Join_constraintContext join_constraint()
		{
			return getRuleContext(Join_constraintContext.class, 0);
		}

		public Join_clauseContext join_clause()
		{
			return getRuleContext(Join_clauseContext.class, 0);
		}

		@Override
		public int getRuleIndex()
		{
			return RULE_join_clause;
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor)
		{
			if (visitor instanceof SqlVisitor) return ((SqlVisitor<? extends T>) visitor).visitJoin_clause(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class Value_exprContext extends ParserRuleContext
	{
		public Value_exprContext left;
		public Expr_operatorContext op;
		public Value_exprContext right;

		public Value_exprContext(ParserRuleContext parent, int invokingState)
		{
			super(parent, invokingState);
		}

		public Literal_valueContext literal_value()
		{
			return getRuleContext(Literal_valueContext.class, 0);
		}

		public Boolean_valueContext boolean_value()
		{
			return getRuleContext(Boolean_valueContext.class, 0);
		}

		public Cell_nameContext cell_name()
		{
			return getRuleContext(Cell_nameContext.class, 0);
		}

		public List<Value_exprContext> value_expr()
		{
			return getRuleContexts(Value_exprContext.class);
		}

		public Value_exprContext value_expr(int i)
		{
			return getRuleContext(Value_exprContext.class, i);
		}

		public Expr_operatorContext expr_operator()
		{
			return getRuleContext(Expr_operatorContext.class, 0);
		}

		@Override
		public int getRuleIndex()
		{
			return RULE_value_expr;
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor)
		{
			if (visitor instanceof SqlVisitor) return ((SqlVisitor<? extends T>) visitor).visitValue_expr(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class Literal_valueContext extends ParserRuleContext
	{
		public Literal_valueContext(ParserRuleContext parent, int invokingState)
		{
			super(parent, invokingState);
		}

		public TerminalNode NUMERIC_LITERAL()
		{
			return getToken(SqlParser.NUMERIC_LITERAL, 0);
		}

		public Any_nameContext any_name()
		{
			return getRuleContext(Any_nameContext.class, 0);
		}

		@Override
		public int getRuleIndex()
		{
			return RULE_literal_value;
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor)
		{
			if (visitor instanceof SqlVisitor) return ((SqlVisitor<? extends T>) visitor).visitLiteral_value(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class Boolean_valueContext extends ParserRuleContext
	{
		public Boolean_valueContext(ParserRuleContext parent, int invokingState)
		{
			super(parent, invokingState);
		}

		public TerminalNode K_TRUE()
		{
			return getToken(SqlParser.K_TRUE, 0);
		}

		public TerminalNode K_FALSE()
		{
			return getToken(SqlParser.K_FALSE, 0);
		}

		@Override
		public int getRuleIndex()
		{
			return RULE_boolean_value;
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor)
		{
			if (visitor instanceof SqlVisitor) return ((SqlVisitor<? extends T>) visitor).visitBoolean_value(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class AliasContext extends ParserRuleContext
	{
		public AliasContext(ParserRuleContext parent, int invokingState)
		{
			super(parent, invokingState);
		}

		public TerminalNode K_AS()
		{
			return getToken(SqlParser.K_AS, 0);
		}

		public Any_nameContext any_name()
		{
			return getRuleContext(Any_nameContext.class, 0);
		}

		@Override
		public int getRuleIndex()
		{
			return RULE_alias;
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor)
		{
			if (visitor instanceof SqlVisitor) return ((SqlVisitor<? extends T>) visitor).visitAlias(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class Expr_operatorContext extends ParserRuleContext
	{
		public Expr_operatorContext(ParserRuleContext parent, int invokingState)
		{
			super(parent, invokingState);
		}

		public TerminalNode K_OR()
		{
			return getToken(SqlParser.K_OR, 0);
		}

		public TerminalNode K_AND()
		{
			return getToken(SqlParser.K_AND, 0);
		}

		public TerminalNode K_EQUAL()
		{
			return getToken(SqlParser.K_EQUAL, 0);
		}

		public TerminalNode K_ST()
		{
			return getToken(SqlParser.K_ST, 0);
		}

		public TerminalNode K_LT()
		{
			return getToken(SqlParser.K_LT, 0);
		}

		public TerminalNode K_PLUS()
		{
			return getToken(SqlParser.K_PLUS, 0);
		}

		public TerminalNode K_MINUS()
		{
			return getToken(SqlParser.K_MINUS, 0);
		}

		@Override
		public int getRuleIndex()
		{
			return RULE_expr_operator;
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor)
		{
			if (visitor instanceof SqlVisitor) return ((SqlVisitor<? extends T>) visitor).visitExpr_operator(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class Cell_nameContext extends ParserRuleContext
	{
		public Cell_nameContext(ParserRuleContext parent, int invokingState)
		{
			super(parent, invokingState);
		}

		public Column_nameContext column_name()
		{
			return getRuleContext(Column_nameContext.class, 0);
		}

		public Table_nameContext table_name()
		{
			return getRuleContext(Table_nameContext.class, 0);
		}

		public Database_nameContext database_name()
		{
			return getRuleContext(Database_nameContext.class, 0);
		}

		@Override
		public int getRuleIndex()
		{
			return RULE_cell_name;
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor)
		{
			if (visitor instanceof SqlVisitor) return ((SqlVisitor<? extends T>) visitor).visitCell_name(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class Column_nameContext extends ParserRuleContext
	{
		public Column_nameContext(ParserRuleContext parent, int invokingState)
		{
			super(parent, invokingState);
		}

		public Any_nameContext any_name()
		{
			return getRuleContext(Any_nameContext.class, 0);
		}

		@Override
		public int getRuleIndex()
		{
			return RULE_column_name;
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor)
		{
			if (visitor instanceof SqlVisitor) return ((SqlVisitor<? extends T>) visitor).visitColumn_name(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class Table_nameContext extends ParserRuleContext
	{
		public Table_nameContext(ParserRuleContext parent, int invokingState)
		{
			super(parent, invokingState);
		}

		public Any_nameContext any_name()
		{
			return getRuleContext(Any_nameContext.class, 0);
		}

		@Override
		public int getRuleIndex()
		{
			return RULE_table_name;
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor)
		{
			if (visitor instanceof SqlVisitor) return ((SqlVisitor<? extends T>) visitor).visitTable_name(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class Database_nameContext extends ParserRuleContext
	{
		public Database_nameContext(ParserRuleContext parent, int invokingState)
		{
			super(parent, invokingState);
		}

		public Any_nameContext any_name()
		{
			return getRuleContext(Any_nameContext.class, 0);
		}

		@Override
		public int getRuleIndex()
		{
			return RULE_database_name;
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor)
		{
			if (visitor instanceof SqlVisitor) return ((SqlVisitor<? extends T>) visitor).visitDatabase_name(this);
			else return visitor.visitChildren(this);
		}
	}

	public static class Any_nameContext extends ParserRuleContext
	{
		public Any_nameContext(ParserRuleContext parent, int invokingState)
		{
			super(parent, invokingState);
		}

		public TerminalNode STRING_LITERAL()
		{
			return getToken(SqlParser.STRING_LITERAL, 0);
		}

		public Any_nameContext any_name()
		{
			return getRuleContext(Any_nameContext.class, 0);
		}

		@Override
		public int getRuleIndex()
		{
			return RULE_any_name;
		}

		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor)
		{
			if (visitor instanceof SqlVisitor) return ((SqlVisitor<? extends T>) visitor).visitAny_name(this);
			else return visitor.visitChildren(this);
		}
	}
}