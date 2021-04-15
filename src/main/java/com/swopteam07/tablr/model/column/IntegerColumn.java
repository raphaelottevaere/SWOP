package com.swopteam07.tablr.model.column;

import com.swopteam07.tablr.model.cell.Cell;
import com.swopteam07.tablr.model.cell.IntegerCell;
import com.swopteam07.tablr.model.validator.IntValidator;

/**
 * Integer column in a table in this database.
 */
public class IntegerColumn extends Column<Integer> {

    /**
     * Create a new integer column.
     *
     * @param id           The id of this column.
     * @param name         The name of this column.
     * @param allowsBlanks Whether or not this column allows blank values;
     * @param defaultValue The default value for this column.
     */
    public IntegerColumn(int id, String name, boolean allowsBlanks, Integer defaultValue) {
        super(id, name, DataType.INTEGER, allowsBlanks, defaultValue);
    }

    /**
     * Copy constructor.
     *
     * @param original The column to copy.
     */
    IntegerColumn(IntegerColumn original) {
        super(original);
    }

    /**
     * Create a new integer column with a blank default value.
     *
     * @param id           The id of this column.
     * @param name         The name of this column.
     * @param allowsBlanks Whether or not this column allows blank values;
     */
    public IntegerColumn(int id, String name, boolean allowsBlanks) {
        this(id, name, allowsBlanks, null);
    }

    /**
     * @return A copy of this column.
     */
    @Override
    public IntegerColumn copy() {
        return new IntegerColumn(this);
    }

    /**
     * Create a new Integer cell.
     * @return The new cell.
     */
    @Override
    public IntegerCell createCell() {
        return new IntegerCell(getDefaultValue(), allowsBlank());
    }

    /**
     * Parse an integer from a string.
     *
     * @param value The string to try to parse.
     * @return The parsed value.
     */
    @Override
    public Integer parseValue(String value) {
        return IntegerColumn.parseInteger(value);
    }

    /**
     * Parse an integer from a string.
     *
     * @param in The string to parse.
     * @return The parsed value.
     * @throws IllegalArgumentException Thrown when the string can't be parsed as an integer.
     */
    public static Integer parseInteger(String in) {
        IntValidator validator = new IntValidator();
        if (in == null || in.isEmpty())
            return null;

        if (!validator.isValid(in))
            throw new IllegalArgumentException(in + " can't be parsed as an integer.");

        return Integer.parseInt(in);
    }
}
