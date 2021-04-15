package com.swopteam07.tablr.model.column;

import com.swopteam07.tablr.model.DomainException;
import com.swopteam07.tablr.model.cell.Cell;
import com.swopteam07.tablr.model.validator.StringValidator;

import java.util.Objects;

/**
 * A column in a table in the database.
 *
 * @param <T> The type of object this Column contains.
 */
public abstract class Column<T> {

    /**
     * The name of the table.
     */
    private String name;

    /**
	 * The type of literal contained in this column.
     */
    private final DataType type;

    /**
     * The default value for this column.
     */
    private T defaultValue;

    /**
     * Flag indicating whether or not this column allows for blank values.
     */
    private boolean allowsBlanks;

    /**
     * The id that uniquely identifies this column in it's table.
     */
    private final int id;

    /**
     * Create a new column.
     *
     * @param id           The id of the column.
     * @param name         The name of the column.
	 * @param type         The type of literal contained in this column.
     * @param allowsBlanks Whether or not this column allows blank values.
     * @param defaultValue The default value for this column.
     */
    public Column(int id, String name, DataType type, boolean allowsBlanks, T defaultValue) {
        setName(name);
        this.type = type;
        this.allowsBlanks = allowsBlanks;
        this.id = id;
        this.defaultValue = defaultValue;
    }

    /**
     * Copy constructor.
     *
     * @param original The column to copy.
     */
    Column(Column<T> original) {
        setName(original.name);
        type = original.type;
        allowsBlanks = original.allowsBlanks;
        id = original.id;
        defaultValue = original.defaultValue;
    }

    /**
     * Create a new column that allows blanks and has blank as the default value.
     *
     * @param id   The id of the column.
     * @param name The name of the column.
     * @param type The type of the column.
     */
    public Column(int id, String name, DataType type) {
        this(id, name, type, true, null);
    }

    /**
     * Update the name of this column.
     *
     * @param name The new name of the column.
     */
    public final void setName(String name) {
        StringValidator validator = new StringValidator();
        if (!validator.isValid(name))
            throw new DomainException("name can not be empty!");
        this.name = name;
    }

    /**
     * @return The name of this column.
     */
    public final String getName() {
        return this.name;
    }

    /**
     * @return The id of this column.
     */
    public final int getId() {
        return this.id;
    }

    /**
     * @return The default value for this column.
     */
    public final T getDefaultValue() {
        return defaultValue;
    }

    /**
     * Set the new value of the allows blanks flag.
     *
     * @param allowsBlanks The new value.
     */
    public final void setAllowsBlanks(boolean allowsBlanks) {
        this.allowsBlanks = allowsBlanks;
    }

    /**
     * @return True if this column allows for blank values.
     */
    public final boolean allowsBlank() {
        return allowsBlanks;
    }

    /**
     * @return The type of this column.
     */
    public final String getType() {
        return type.toString();
    }

    /**
     * Check if the default value is blank.
     *
     * @return True if the default value is blank, false otherwise.
     */
    public final boolean defaultIsBlank() {
        return getDefaultValue() == null;
    }

    /**
     * Create a new cell in this column.
     *
     * @return The new cell.
     */
    public abstract Cell<T> createCell();

    /**
     * Create a new cell with the given value.
     *
     * @param value The new value for this cell.
     * @return The new cell.
     * @throws IllegalArgumentException Thrown when the value is not allowed.
     */
    public Cell<T> createCell(T value) {
        if (value == null && !allowsBlank())
            throw new IllegalArgumentException();

        Cell<T> cell = createCell();
        cell.setValue(value);
        return cell;
    }

    /**
     * Try to parse a valid value for this column from a string.
     *
     * @param value The string to try to parse.
     * @return A valid value for this column.
     * @throws IllegalArgumentException Thrown when the value can't be parsed.
     */
    public abstract T parseValue(String value) throws IllegalArgumentException;

    /**
     * Try to set a new default for this column.
     *
     * @param defaultValue The new default value.
     * @throws DomainException if the string can't be parsed to a valid value.
     */
    public final void updateDefaultValue(String defaultValue) {
        if (!isValidDefault(defaultValue))
            throw new DomainException(defaultValue + " is not a valid default value for column with type");
        this.defaultValue = parseValue(defaultValue);
    }

    /**
     * Check if a string can be parsed as a valid default value for this column.
     *
     * @param defaultValue The value to test for.
     * @return True if the default value is valid, false otherwise.
     */
    public boolean isValidDefault(String defaultValue) {
        try {
            parseValue(defaultValue);
        } catch (Exception exc) {
            return false;
        }
        return true;
    }

    /**
     * Sets the Defaultvalue to the given value
     *
     * @param value The new default value.
     */
    public void setDefaultValue(T value) {
        defaultValue = value;
    }

    /**
     * @return A copy of this column.
     */
    public abstract Column<T> copy();

    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (other == null) return false;
        if (!(other instanceof Column)) return false;
        Column otherColumn = (Column) other;
        return otherColumn.name.equals(this.name) && (otherColumn.defaultValue == null &&
                this.defaultValue == null || otherColumn.defaultValue.equals(this.defaultValue)) &&
                otherColumn.allowsBlanks == this.allowsBlanks && this.type.equals(otherColumn.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, defaultValue, allowsBlanks, type);
    }

    public DataType getDataType()
    {
        return this.type;
    }
}
