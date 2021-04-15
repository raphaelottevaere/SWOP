package com.swopteam07.tablr.model.validator;

public class IntValidator implements Validator {

	/**
	 * Check if an object is an integer or can be converted to an integer.
	 *
	 * @param o Object that needs to be validated
	 * @return True if specified object is an integer or can be parsed as one or is null.
	 */
	@Override
	public boolean isValid(Object o) {
		try {
			if (o == null || (o instanceof Integer))
				return true;
			if (((String) o).matches("^0$|^[+-]?[1-9]+[0-9]*$")) {
				Integer.parseInt(o.toString()); // Test for overflow errors.
				return true;
			}
		}
		catch(Exception e) {
			return false;
		}
		return false;
	}
}
