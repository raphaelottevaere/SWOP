package com.swopteam07.tablr.model.validator;

public class StringValidator implements Validator {


	/**
	 * Check if an objects string representation is not an empty or whitespace string.
	 * @param o Object that needs to be validated
	 * @return True if the string is valid, false otherwise.
	 */
	@Override
	public boolean isValid(Object o) {
		try {
			return o == null || !(((String) o).isBlank() || ((String) o).isEmpty());
		}
		catch(Exception e){
			return false;
		}
	}

}
