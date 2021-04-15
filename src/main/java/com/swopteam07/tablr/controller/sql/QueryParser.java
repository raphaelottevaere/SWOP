package com.swopteam07.tablr.controller.sql;

import com.swopteam07.tablr.controller.sql.generated.SqlLexer;
import com.swopteam07.tablr.controller.sql.generated.SqlParser;
import org.antlr.v4.runtime.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class QueryParser<Q, Select, From, Expr, ID>
{

	private final IQueryFactory<Q, Select, From, Expr, ID> factory;

	private final List<String> errors = new ArrayList<>();

	public QueryParser(IQueryFactory<Q, Select, From, Expr, ID> factory)
	{
		this.factory = factory;
	}

	public static <Q, Select, From, Expr, ID> QueryParser<Q, Select, From, Expr, ID> create(
			IQueryFactory<Q, Select, From, Expr, ID> factory)
	{
		return new QueryParser<Q, Select, From, Expr, ID>(factory);
	}

	public IQueryFactory<Q, Select, From, Expr, ID> getFactory()
	{
		return factory;
	}

	/**
	 * Returns the Program that results from parsing the given string,
	 * or Optional.empty() if parsing has failed.
	 * <p>
	 * When parsing has failed, the error messages can be retrieved with the
	 * getErrors() method.
	 */
	public Optional<Q> parseString(String string)
	{
		return parse(new ANTLRInputStream(string));
	}

	/**
	 * Returns the Program that results from parsing the file with the given
	 * name,
	 * or Optional.empty() if parsing has failed.
	 * <p>
	 * When parsing has failed, the error messages can be retrieved with the
	 * getErrors() method.
	 */
	public Optional<Q> parseFile(String filename) throws IOException
	{
		return parse(new ANTLRInputStream(new FileReader(filename)));
	}

	/**
	 * Returns the Program that results from parsing the given CharStream,
	 * or Optional.empty() if parsing has failed.
	 * <p>
	 * When parsing has failed, the error messages can be retrieved with the
	 * getErrors() method.
	 */
	protected Optional<Q> parse(CharStream input)
	{
		reset();

		SqlLexer lexer = new SqlLexer(input);
		SqlParser parser = new SqlParser(
				new CommonTokenStream(lexer));
		parser.addErrorListener(new BaseErrorListener()
		{
			@Override
			public void syntaxError(Recognizer<?, ?> recognizer,
									Object offendingSymbol, int line, int charPositionInLine,
									String msg, RecognitionException e)
			{
				errors.add(msg + " (" + line + ", " + charPositionInLine + ")");
			}
		});
		lexer.removeErrorListeners();
		ParserVisitor<Q, Select, From, Expr, ID> visitor = new ParserVisitor<>(factory);
		try
		{
			visitor.visit(parser.query());
			if (errors.isEmpty())
			{
				return visitor.getQuery();
			}
		} catch (Exception e)
		{
			errors.add(e.toString());
		}
		return Optional.empty();
	}

	protected void reset()
	{
		this.errors.clear();
	}

	public List<String> getErrors()
	{
		return Collections.unmodifiableList(errors);
	}
}