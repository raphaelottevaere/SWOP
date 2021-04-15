package com.swopteam07.tablr.model.cell;

import com.swopteam07.tablr.model.column.DataType;
import com.swopteam07.tablr.model.validator.MailValidator;

public class MailCell extends Cell<String> {

    /**
     * Create a new mail cell.
     *
     * @param value        The mail address contained in this cell.
     * @param allowsBlanks Flag indicating whether or not this cell allows blanks.
     */
    public MailCell(String value, boolean allowsBlanks) {
        super(value, new MailValidator(), allowsBlanks);
    }

    /**
     * Copy constructor.
     *
     * @param original The cell to copy.
     */
    MailCell(MailCell original) {
        super(original);
    }

    /**
     * Gets the DataType from this object
     *
     * @return The datatype of this object.
     */
    @Override
    public DataType getDataType() {
        return DataType.EMAIL;
    }

    /**
     * Checks if a value is valid for this type of cell
     *
     * @param value The new value
     * @return True if the specified value is valid, false otherwise.
     */
    @Override
    public boolean isValid(String value) {
        return super.isValid(value) || (allowsBlanks() && value.isEmpty());
    }

    /**
     * @return A copy of this cell.
     */
    @Override
    public MailCell copy() {
        return new MailCell(this);
    }
}