package com.swopteam07.tablr.model.validator;

public class MailValidator implements Validator {

    /**
     * Check if an objects string representation is a valid email address.
     *
     * @param o Object that needs to be validated
     * @return True if the objects string representation is a valid email address.
     */
    @Override
    public boolean isValid(Object o) {
        try {
            return o == null || ((String) o).matches("^[^@]*@[^@]*$");
            //return ((String) o).matches("[_a-zA-Z1-9]+(\\.[A-Za-z0-9]*)*@[A-Za-z0-9]+\\.[A-Za-z0-9]+(\\.[A-Za-z0-9]*)*");
        } catch (Exception e) {
            return false;
        }
    }

}
