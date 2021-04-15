package com.swopteam07.tablr.model.column;

import com.swopteam07.tablr.model.cell.Cell;
import com.swopteam07.tablr.model.cell.MailCell;
import com.swopteam07.tablr.model.validator.MailValidator;

/**
 * A mail column in a table in the database.
 */
public class MailColumn extends Column<String> {

    /**
     * Validator to check if a string is a vlid email address.
     */
    private final MailValidator validator = new MailValidator();

    /**
     * Create a new mail column.
     *
     * @param id           The id of the column.
     * @param name         Teh name of the column.
     * @param allowsBlanks Whether or not this column allows blank values.
     * @param defaultValue The default value for this column.
     */
    public MailColumn(int id, String name, boolean allowsBlanks, String defaultValue) {
        super(id, name, DataType.EMAIL, allowsBlanks, defaultValue);

        if (!isValid(defaultValue))
            throw new IllegalArgumentException("The string: " + defaultValue + " is not a valid email address.");
    }

    /**
     * Copy constructor.
     *
     * @param original The column to copy.
     */
    MailColumn(MailColumn original) {
        super(original);
    }

    /**
     * Create a new mail column with a blank default value.
     *
     * @param id           The id of the column.
     * @param name         The name of the column.
     * @param allowsBlanks Whether or not this column allows blanks.
     */
    public MailColumn(int id, String name, boolean allowsBlanks) {
        this(id, name, allowsBlanks, null);
    }

    /**
     * @return A copy of this column.
     */
    @Override
    public MailColumn copy() {
        return new MailColumn(this);
    }

    /**
     * Create a new mail cell.
     */
    @Override
    public MailCell createCell() {
        return new MailCell(getDefaultValue(), allowsBlank());
    }

    /**
     * Try to parse an email address from a string.
     *
     * @param value The string to try to parse.
     * @throws IllegalArgumentException Thrown when the given string is not a valid email address.
     */
    @Override
    public String parseValue(String value) {
        if (!isValid(value))
            throw new IllegalArgumentException(value + " is not a valid email address");
        return value == null || value.isEmpty() ? null : value;
    }

    /**
     * Check if a email address is valid.
     *
     * @param email The string to check for.
     */
    private boolean isValid(String email) {
        return (allowsBlank() && (email == null || email.isEmpty())) || validator.isValid(email);
    }
}
