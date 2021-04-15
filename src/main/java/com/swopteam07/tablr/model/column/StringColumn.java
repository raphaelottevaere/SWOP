package com.swopteam07.tablr.model.column;

import com.swopteam07.tablr.model.cell.Cell;
import com.swopteam07.tablr.model.cell.StringCell;

/**
 * A string column in a table in the database.
 */
public class StringColumn extends Column<String> {

    /**
     * Creat a new string column.
     *
     * @param id           The id of the new column.
     * @param name         The name of the new column.
     * @param allowsBlanks Whether or not this column allows blank values.
     * @param defaultValue The default value for this column.
     */
    public StringColumn(int id, String name, boolean allowsBlanks, String defaultValue) {
        super(id, name, DataType.STRING, allowsBlanks, defaultValue);
    }

    /**
     * Copy constructor.
     *
     * @param original The column to copy.
     */
    StringColumn(StringColumn original) {
        super(original);
    }

    /**
     * Create a new string column that uses a blank default value.
     *
     * @param id           The id of the new column.
     * @param name         The name of the new column.
     * @param allowsBlanks Whether or not this column allows blank values.
     */
    public StringColumn(int id, String name, boolean allowsBlanks) {
        this(id, name, allowsBlanks, null);
    }

    /**
     * Create a new string column that allows blanks and has a blank default value.
     *
     * @param id   The id of the new column.
     * @param name The name of the new column.
     */
    public StringColumn(int id, String name) {
        this(id, name, true);
    }

    /**
     * @return A copy of this column.
     */
    @Override
    public StringColumn copy() {
        return new StringColumn(this);
    }

    /**
     * Create a new cell.
     */
    @Override
    public StringCell createCell() {
        return new StringCell(getDefaultValue(), allowsBlank());
    }

    /**
     * Parse a string from te specified value.
     *
     * @param value The string to try to parse.
     * @return The value that was passed as parameter.
     */
    @Override
    public String parseValue(String value) {
        return value;
    }
}
