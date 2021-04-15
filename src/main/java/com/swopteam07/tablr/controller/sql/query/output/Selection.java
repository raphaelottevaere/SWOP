package com.swopteam07.tablr.controller.sql.query.output;

import com.swopteam07.tablr.controller.sql.SourceLocation;
import com.swopteam07.tablr.controller.sql.query.id.SqlId;

import java.util.List;

public class Selection
{

	private final List<SqlId> resultColumns;

	public Selection(SourceLocation ctx, List<SqlId> resultColumns)
	{
		this.resultColumns = resultColumns;
	}

	public List<SqlId> getResultColumns()
	{
		return List.copyOf(resultColumns);
	}
}
