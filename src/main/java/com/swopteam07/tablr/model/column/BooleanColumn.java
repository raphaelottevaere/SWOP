package com.swopteam07.tablr.model.column;

import com.swopteam07.tablr.model.cell.BooleanCell;
import com.swopteam07.tablr.model.cell.Cell;

/**
 * A column in a table in the database that contains boolean values.
 */
public class BooleanColumn extends Column<Boolean> {

    /**
     * Create a new boolean column.
     *
     * @param id           The id of this column.
     * @param name         The name of this column.
     * @param allowsBlanks Flag indicating whether or not blank values are allowed.
     * @param defaultValue The default value for this column.
     */
    public BooleanColumn(int id, String name, boolean allowsBlanks, Boolean defaultValue) {
        super(id, name, DataType.BOOLEAN, allowsBlanks, defaultValue);
    }

    /**
     * Copy constructor.
     *
     * @param original The column to copy.
     */
    BooleanColumn(BooleanColumn original) {
        super(original);
    }

    /**
     * Create a new column with a blank default value.
     *
     * @param id   The id of this column.
     * @param name The name of this column.
     * @param allowsBlanks Flag indicating whether or not blank values are allowed.
     */
    public BooleanColumn(int id, String name, boolean allowsBlanks) {
        this(id, name, allowsBlanks, null);
    }

    /**
     * Create a new boolean cell.
     */
    @Override
    public BooleanCell createCell() {
        return new BooleanCell(getDefaultValue(), allowsBlank());
    }

    /**
     * Parse a string to a valid boolean value.
     * @param in The string to parse.
     * @return The parsed value.
     */
    @Override
    public Boolean parseValue(String in) {
        return BooleanColumn.parseBooleanFromString(in);
    }

    /**
     * @return A copy of this column.
     */
    @Override
    public BooleanColumn copy() {
        return new BooleanColumn(this);
    }

    /**
     * Parse a string to a valid boolean value.
     * @param in The string to parse.
     * @return The parsed value.
     */
    public static Boolean parseBooleanFromString(String in) {
        if (in == null)
            return null;

        if (in.equals("1") || in.equals("true") || in.equals("True"))
            return true;

        if (in.equals("0") || in.equals("false") || in.equals("False"))
            return false;

        throw new IllegalArgumentException("The string " + in + " cannot be parsed as a Boolean.");
    }
}
