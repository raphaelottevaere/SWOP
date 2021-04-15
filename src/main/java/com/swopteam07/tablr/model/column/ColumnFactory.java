package com.swopteam07.tablr.model.column;

/**
 * Factory to create new column.
 */
public class ColumnFactory {

    public Column getColumn(DataType type, int id, String name, boolean allowsBlanks, String defaultValue)
            throws UnsupportedOperationException {
        if (type == DataType.EMAIL)
            return new MailColumn(id, name, allowsBlanks, defaultValue);
        else if (type == DataType.STRING)
            return new StringColumn(id, name, allowsBlanks, defaultValue);
        else if (type == DataType.BOOLEAN) {
            Boolean bool = BooleanColumn.parseBooleanFromString(defaultValue);
            return new BooleanColumn(id, name, allowsBlanks, bool);
        } else if (type == DataType.INTEGER) {
            Integer integer = IntegerColumn.parseInteger(defaultValue);
            return new IntegerColumn(id, name, allowsBlanks, integer);
        } else
			throw new UnsupportedOperationException("The literal type " + type + " is not supported.");
    }

    public Column getColumn(DataType type, int id, String name, boolean allowsBlanks)
            throws UnsupportedOperationException {
        if (type == DataType.EMAIL)
            return new MailColumn(id, name, allowsBlanks);
        else if (type == DataType.STRING)
            return new StringColumn(id, name, allowsBlanks);
        else if (type == DataType.BOOLEAN)
            return new BooleanColumn(id, name, allowsBlanks);
        else if (type == DataType.INTEGER)
            return new IntegerColumn(id, name, allowsBlanks);
        else
			throw new UnsupportedOperationException("The literal type " + type + " is not supported.");
    }
}
