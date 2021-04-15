package com.swopteam07.tablr.model.cell;

import com.swopteam07.tablr.model.column.DataType;
import com.swopteam07.tablr.model.validator.IntValidator;
import com.swopteam07.tablr.model.validator.Validator;

public class IntegerCell extends Cell<Integer> {

    /**
     * Create a new integer cell.
     *
     * @param value        The value contained in this cell.
     * @param allowsBlanks Flag indicating whether or not this cell allows blanks.
     */
    public IntegerCell(Integer value, boolean allowsBlanks) {
        super(value, new IntValidator(), allowsBlanks);
    }

    /**
     * Copy constructor.
     *
     * @param original The cell to copy.
     */
    IntegerCell(IntegerCell original) {
        super(original);
    }

    /**
     * Gets the DataType from this object
     *
     * @return The datatype of this object.
     */
    @Override
    public DataType getDataType() {
        return DataType.INTEGER;
    }

    /**
     * @return A copy of this cell.
     */
    @Override
    public IntegerCell copy() {
        return new IntegerCell(this);
    }
}

