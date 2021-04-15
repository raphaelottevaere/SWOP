package com.swopteam07.tablr.model.validator;

import com.swopteam07.tablr.model.cell.Cell;

public class InvertableValidator implements Validator
{

	private Cell derivedCell;
	private boolean isInvertalbe = true;

	public InvertableValidator(Cell derivedCell)
	{
		this.derivedCell = derivedCell;
	}

	public InvertableValidator(boolean isInvertalbe)
	{
		assert !isInvertalbe;
		this.isInvertalbe = false;
	}

	public InvertableValidator(Cell derivedCell, boolean isInvertable)
	{
		this.derivedCell = derivedCell;
		this.isInvertalbe = isInvertable;
	}

	/**
	 * Check if an object is of the required type or can be converted to the required type.
	 *
	 * @param o Object that needs to be validated
	 * @return True if the object is a valid object or can be parsed as one, false otherwise.
	 */
	@Override
	public boolean isValid(Object o)
	{
		if (isInvertalbe)
			return derivedCell.isValid(o);
		else
			return false;
	}
}
