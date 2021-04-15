package com.swopteam07.tablr.model.validator;

public interface Validator {

    /**
     * Check if an object is of the required type or can be converted to the required type.
     *
     * @param o Object that needs to be validated
     * @return True if the object is a valid object or can be parsed as one, false otherwise.
     */
    boolean isValid(Object o);
}
