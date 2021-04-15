package com.swopteam07.tablr.test.usecases;

import com.swopteam07.tablr.model.column.Column;
import com.swopteam07.tablr.model.column.StringColumn;
import com.swopteam07.tablr.view.TableDesignView;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddColumnTest extends UsecaseTest {

    /**
     * UseCase 4.6
     * View is in tableDesignView, or TablesDesignSubwindow has been selected
     * Adding a column by doubleClicking beneath the column
     */
    @Test
    public void successAddColumnTest() {
        controller.addTable();
        controller.tableDesignView(0);

        // There shouldn't be any columns in the selected table.
        assertEquals(0, database.getNbColumns(0));

        // Add a column.
        controller.getActiveView().handleMouseEvent(500, 100, 110, 2);
        assertEquals(1, database.getNbColumns(0));

        // The column needs to allow blanks, be of the string type and have blank as the default value.
        Column column = database.getColumn(0, 0);
        assertTrue(column.allowsBlank());
        assertTrue(column.defaultIsBlank());
        assertTrue(column instanceof StringColumn);
        assertEquals("STRING", column.getType());
    }

    /**
     * Columns need to be named ColumnN with N a number so that ColumnN is unique in the database.
     */
    @Test
    public void columnCorrectNameTest() {
        int tableId = database.addTable();
        IntStream.range(0, 50).forEach(i -> database.addColumn(tableId));
        Set<String> names = Arrays.stream(controller.getAllColumnsHeader(tableId)).collect(Collectors.toSet());
        assertEquals(50, names.size());
        assertTrue(names.stream().allMatch(n -> n.matches("^[A-z]*[0-9]+$")));
    }
}
