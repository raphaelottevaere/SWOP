package com.swopteam07.tablr.model.cell;

import com.swopteam07.tablr.model.column.DataType;

public class DataCell {

    private final DataType type;
    private final Object value;
    private final boolean allowsBlanks;

    /**
     * Constructs a dataCell
     *
     * @param type         The type this dataCell.
     * @param value        The value of this dataCell.
     * @param allowsBlanks Indicates whether or not this cell allows blanks.
     */
    public DataCell(DataType type, Object value, boolean allowsBlanks) {
        this.type = type;
        this.value = value;
        this.allowsBlanks = allowsBlanks;
    }

    /**
     * Return the type of the Object in DATAType enum
     *
     * @return DataType
     */
    public DataType getType() {
        return type;
    }

    /**
     * Return the value of the Object
     *
     * @return value
     */
    public Object getValue() {
        return value;
    }

    public boolean allowsBlanks() {
        return allowsBlanks;
    }
}