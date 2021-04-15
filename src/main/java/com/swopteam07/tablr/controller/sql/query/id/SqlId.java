package com.swopteam07.tablr.controller.sql.query.id;

import com.swopteam07.tablr.controller.sql.SourceLocation;

import java.util.ArrayList;
import java.util.List;

public abstract class SqlId
{
	private final SourceLocation source;
	private final List<String> names;
	private final String alias;

	SqlId(SourceLocation sourceLocation, String alias, String... args)
	{
		source = sourceLocation;
		names = new ArrayList<>(args.length);
		for (String arg : args)
		{
			if (!arg.equals(""))
				names.add(arg);
		}
		if (alias != null && !alias.equals(""))
			this.alias = alias;
		else
			this.alias = null;
	}

	public List<String> getNames()
	{
		return names;
	}

	@Override
	public String toString()
	{
		return names.stream().reduce((s, s2) -> s + "/" + s2).get();
	}

	public String getAlias()
	{
		if (alias != null)
			return alias;
		else
			return this.getNames().get(0);
	}

	public String getResultColumnAlias()
	{
		if (alias != null)
			return alias;
		else
			return getNames().get(getNames().size() - 1);
		//return getNames().stream().reduce((s, s2) -> s + "." + s2).get();
	}

	public abstract String getColumnName();
	protected SourceLocation getSourceLocation()
	{
		return source;
	}

	public boolean matchAlias(String alias)
	{
		String[] s = this.getAlias().split("//");
		for (String s1 : s)
		{
			if (alias.equals(s1))
				return true;
		}
		return false;
	}
}
