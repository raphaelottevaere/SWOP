package com.swopteam07.tablr.model.cell;

import com.swopteam07.tablr.model.column.DataType;

public class SqlDerivedCell extends Cell
{

	private final Cell derivedCell;
	private final CellUpdater cellUpdater;

	public SqlDerivedCell(Object initialValue, Cell derivedCell, CellUpdater cellUpdater)
	{
		super(initialValue, derivedCell.validator, derivedCell.allowsBlanks());
		this.derivedCell = derivedCell;
		this.cellUpdater = cellUpdater;
	}

	public SqlDerivedCell(SqlDerivedCell sqlDerivedCell)
	{
		super(sqlDerivedCell);
		this.derivedCell = sqlDerivedCell.derivedCell;
		this.cellUpdater = sqlDerivedCell.cellUpdater;
	}

	/**
	 * Gets the DataType from this object
	 *
	 * @return The datatype of this object.
	 */
	@Override
	public DataType getDataType()
	{
		return derivedCell.getDataType();
	}

	/**
	 * @return A copy of this cell.
	 */
	@Override
	public Cell copy()
	{
		return new SqlDerivedCell(this);
	}

	/**
	 * Sets the value to this value if this.isValid(o)
	 *
	 * @param value The new value for this cell.
	 */
	@Override
	public void setValue(Object value)
	{
		super.setValue(value);
		if (this.cellUpdater != null)//SHOULD ONLY BE NULL IN CONSTRUCTOR
			this.cellUpdater.setValue(value);
	}

	public interface CellUpdater
	{
		void setValue(Object value);
	}

}
