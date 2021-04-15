package com.swopteam07.tablr.model.cell;

import com.swopteam07.tablr.model.column.DataType;

public class BooleanCell extends Cell<Boolean> {

    /**
     * Create a new boolean cell.
     *
     * @param value        The value contained in this cell.
     * @param allowsBlanks Flag indicating whether or not this cell allows blanks.
     */
    public BooleanCell(Boolean value, boolean allowsBlanks) {
        super(value, allowsBlanks);
    }

    /**
     * Copy constructor.
     *
     * @param original The cell to copy.
     */
    BooleanCell(BooleanCell original) {
        super(original);
    }

    /**
     * Gets the DataType from this object
     *
     * @return The datatype of this object.
     */
    @Override
    public DataType getDataType() {
        return DataType.BOOLEAN;
    }

    /**
     * @return A copy of this cell.
     */
    @Override
    public BooleanCell copy() {
        return new BooleanCell(this);
    }

}