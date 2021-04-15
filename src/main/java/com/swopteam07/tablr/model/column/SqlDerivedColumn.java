package com.swopteam07.tablr.model.column;

import com.swopteam07.tablr.model.cell.Cell;
import com.swopteam07.tablr.model.validator.InvertableValidator;

@Deprecated
public class SqlDerivedColumn extends Column
{
	private InvertableValidator invertableValidator;
	private Column derivedColumn;

	/**
	 * Create a new column.
	 *
	 * @param id           The id of the column.
	 * @param name         The name of the column.
	 * @param derivedColumn Originating column
	 */
	public SqlDerivedColumn(int id, String name, Column derivedColumn)
	{
		super(id, name, derivedColumn.getDataType(), derivedColumn.allowsBlank(), derivedColumn.getDefaultValue());
		this.derivedColumn = derivedColumn;
	}

	/**
	 * Copy constructor.
	 *
	 * @param original The column to copy.
	 */
	SqlDerivedColumn(Column original)
	{
		super(original);
		if (original instanceof SqlDerivedColumn)
			this.derivedColumn = ((SqlDerivedColumn) original).derivedColumn;
	}

	/**
	 * Create a new column that allows blanks and has blank as the default value.
	 *
	 * @param id   The id of the column.
	 * @param name The name of the column.
	 * @param type The type of the column.
	 */
	public SqlDerivedColumn(int id, String name, DataType type)
	{
		super(id, name, type);
	}

	/**
	 * Create a new cell in this column.
	 *
	 * @return The new cell.
	 */
	@Override
	public Cell createCell()
	{
		return derivedColumn.createCell();
	}

	/**
	 * Try to parse a valid value for this column from a string.
	 *
	 * @param value The string to try to parse.
	 * @return A valid value for this column.
	 * @throws IllegalArgumentException Thrown when the value can't be parsed.
	 */
	@Override
	public Object parseValue(String value) throws IllegalArgumentException
	{
		return derivedColumn.parseValue(value);
	}

	/**
	 * @return A copy of this column.
	 */
	@Override
	public Column copy()
	{
		return new SqlDerivedColumn(this);
	}
}
