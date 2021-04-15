package com.swopteam07.tablr.model.cell;

import com.swopteam07.tablr.model.column.DataType;
import com.swopteam07.tablr.model.validator.Validator;

import java.util.Objects;

public abstract class Cell<T> {

    /**
     * Value of this cell.
     */
    protected T value;

    /**
     * Validator used to validate this cell.
     */
    protected Validator validator;

    /**
     * Indicates whether or not this cell allows blank values.
     */
    private boolean allowsBlanks;

    /**
     * Create a new cell.
     *
     * @param value        The value for this cell.
     * @param validator    The validator for this cell.
     * @param allowsBlanks Flag indicating whether or not this cell allows blanks.
     */
    public Cell(T value, Validator validator, boolean allowsBlanks) {
        this.allowsBlanks = allowsBlanks;
        this.validator = validator;
        setValue(value);
    }

    /**
     * Create a new cell.
     *
     * @param value        The value for this cell.
     * @param allowsBlanks Flag indicating whether or not this cell allows blanks.
     */
    public Cell(T value, boolean allowsBlanks) {
        this.allowsBlanks = allowsBlanks;
        setValue(value);
    }

    /**
     * Copy constructor.
     *
     * @param original The cell to copy.
     */
    Cell(Cell<T> original) {
        allowsBlanks = original.allowsBlanks;
        validator = original.validator;
        setValue(original.value);
    }

    /**
     * returns the current value of the object
     *
     * @return The value contained in this cell.
     */
    public T getValue() {
        return value;
    }

    /**
     * Sets the value to this value if this.isValid(o)
     *
     * @param value The new value for this cell.
     */
    public void setValue(T value) {
        if (!isValid(value))
            throw new IllegalArgumentException("The specified value is not valid.");
        this.value = value;
    }

    /**
     * Checks if a value is valid for this type of cell
     *
     * @param value The new value
     * @return True if the specified value is valid, false otherwise.
     */
    public boolean isValid(T value) {
        return (value != null || allowsBlanks()) && (validator == null || validator.isValid(value));
    }

    /**
     * Gets the DataType from this object
     *
     * @return The datatype of this object.
     */
    public abstract DataType getDataType();

    /**
     * Test if this cell has the specified object as value.
     *
     * @param o The object to test for.
     * @return True if both are equal (null or equals()), false otherwise.
     */
    public boolean hasAsValue(Object o) {
        if (getValue() == null) return o == null;
        return getValue().equals(o);
    }

    /**
     * Check if this cell allows blank values.
     *
     * @return True if the cell allows blank values, false otherwise.
     */
    public boolean allowsBlanks() {
        return allowsBlanks;
    }

    /**
     * Set the new value for the allows blank flag.
     *
     * @param allowsBlanks The new value.
     * @post The new value if equal to the specified value.
     * | new.allowsBlanks() == allowsBlanks
     */
    public void setAllowsBlanks(boolean allowsBlanks) {
        this.allowsBlanks = allowsBlanks;
    }

    /**
     * @return A copy of this cell.
     */
    public abstract Cell<T> copy();

    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (other == null) return false;
        if (!(other instanceof Cell)) return false;
        Cell otherCell = (Cell) other;
        return this.allowsBlanks == ((Cell) other).allowsBlanks &&
                (this.value == null && otherCell.value == null || this.value.equals(otherCell.value));
    }

    @Override
    public int hashCode() {
        return Objects.hash(allowsBlanks, value);
    }
}
