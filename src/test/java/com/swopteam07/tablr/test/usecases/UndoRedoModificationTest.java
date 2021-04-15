package com.swopteam07.tablr.test.usecases;

import com.swopteam07.tablr.model.Row;
import com.swopteam07.tablr.model.Table;
import com.swopteam07.tablr.model.column.Column;
import com.swopteam07.tablr.test.facade.UiTestFacade;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for use case 4.12: Undo Modification
 * Tests for use case 4.13: Redo Modification
 */
public class UndoRedoModificationTest extends UsecaseTest {

    private int runs = 50;

    private <T> void testUndoRedoList(List<List<T>> states, Supplier<List<T>> stateSupplier) {
        int index = runs - 1;
        int max = runs - 1;

        Random random = new Random();
        for (int i = 0; i < max; i++) {
            if (random.nextInt() % 2 == 0) {
                UiTestFacade.undo();
                if (index > 0) index--;
            } else {
                UiTestFacade.redo();
                if (index < max) index++;
            }
            List<T> currentState = stateSupplier.get();
            List<T> reqState = states.get(index);
            assertEquals(reqState.size(), currentState.size());
            assertTrue(currentState.containsAll(reqState));
        }
    }

    private <T> void testUndoRedo(List<T> states, Supplier<T> stateSupplier) {
        int index = runs - 1;
        int max = runs - 1;

        Random random = new Random();
        for (int i = 0; i < max; i++) {
            if (random.nextInt() % 2 == 0) {
                UiTestFacade.undo();
                if (index > 0) index--;
            } else {
                UiTestFacade.redo();
                if (index < max) index++;
            }
            assertEquals(states.get(index), stateSupplier.get());
        }
    }

    /**
     * Undo/Redo test for use case 4.1: Add table
     */
    @Test
    public void undoRedoAddTableTest() {
        List<List<String>> states = new ArrayList<>();
        Supplier<List<String>> stateSupplier = () -> Arrays.stream(database.getTables()).map(Table::getName).collect(Collectors.toList());

        for (int i = 0; i < runs; i++) {
            controller.addTable();
            states.add(stateSupplier.get());
        }

        testUndoRedoList(states, stateSupplier);
    }

    /**
     * Undo/Redo test for use case 4.2: Edit Table
     */
    @Test
    public void undoRedoEditTableNameTest() {
        List<String> states = new ArrayList<>();
        Supplier<String> stateSupplier = () -> database.getTable(0).getName();
        controller.addTable();
        IntStream.range(0, runs).forEach(i -> {
            controller.editTableName(0, "Name" + i);
            states.add(stateSupplier.get());
        });
        testUndoRedo(states, stateSupplier);
    }

    /**
     * Undo/Redo test for use case 4.3: Edit Table Query
     */
    @Test
    public void undoRedoEditTableQueryTest() {
        assertTrue(true); // TODO
    }

    /**
     * Undo/Redo test for use case 4.4: Delete Table
     */
    @Test
    public void undoRedoDeleteTableTest() {
        List<List<Table>> states = new ArrayList<>();
        Supplier<List<Table>> stateSupplier = () -> Arrays.stream(database.getTables()).collect(Collectors.toList());
        IntStream.range(0, runs).forEach(i -> database.addTable());

        for (int id = runs - 1; id >= 0; id--) {
            controller.deleteTable(id);
            states.add(stateSupplier.get());
        }

        testUndoRedoList(states, stateSupplier);
    }

    /**
     * Undo/Redo test for use case 4.6: Add Column
     */
    @Test
    public void undoRedoAddColumnTest() {
        database.addTable();
        List<List<Column>> states = new ArrayList<>();
        Supplier<List<Column>> stateSupplier = () -> Arrays.stream(database.getColumns(0)).collect(Collectors.toList());

        for (int i = 0; i < runs; i++) {
            controller.addColumnToTable(0);
            states.add(stateSupplier.get());
        }

        testUndoRedoList(states, stateSupplier);
    }

    /**
     * Undo/Redo test for use case 4.7: Edit Column Characteristics
     * Main success scenario: Change Column Name
     */
    @Test
    public void undoRedoEditColumnNameTest() {
        database.addTable();
        database.addColumn(0);

        List<String> states = new ArrayList<>();
        Supplier<String> stateSupplier = () -> database.getColumn(0, 0).getName();

        IntStream.range(0, runs).forEach(i -> {
            controller.updateColumnName(0, 0, "Name" + i);
            states.add(stateSupplier.get());
        });
        testUndoRedo(states, stateSupplier);
    }

    /**
     * Undo/Redo test for use case 4.7: Edit Column Characteristics
     * Extension 1A: Change Column Type
     */
    @Test
    public void undoRedoEditColumnTypeTest() {
        database.addTable();
        database.addColumn(0);

        String[] type = {"STRING", "INTEGER", "BOOLEAN", "EMAIL"};

        List<String> states = new ArrayList<>();
        Supplier<String> stateSupplier = () -> database.getColumn(0, 0).getType();

        IntStream.range(0, runs).forEach(i -> {
            controller.updateColumnType(0, 0, type[i % 4]);
            states.add(stateSupplier.get());
        });
        testUndoRedo(states, stateSupplier);
    }

    /**
     * Undo/Redo test for use case 4.7: Edit Column Characteristics
     * Extension 1B: Change Allows Blanks
     */
    @Test
    public void undoRedoEditColumnAllowsBlanksTest() {
        database.addTable();
        database.addColumn(0);

        List<String> states = new ArrayList<>();
        Supplier<String> stateSupplier = () -> database.getColumn(0, 0).getType();

        Random r = new Random();
        for (int i = 0; i < runs; i++) {
            controller.updateBlanksAllowed(0, 0, i % 2 == 1);
            states.add(stateSupplier.get());
        }
        testUndoRedo(states, stateSupplier);
    }

    /**
     * Undo/Redo test for use case 4.7: Edit Column Characteristics
     * Extension 1C: Change Column Default Value
     */
    @Test
    public void undoRedoEditColumnDefaultValueTest() {
        database.addTable();
        database.addColumn(0);

        List<Object> states = new ArrayList<>();
        Supplier<Object> stateSupplier = () -> database.getColumn(0, 0).getDefaultValue();

        IntStream.range(0, runs).forEach(i -> {
            controller.updateDefaultValue(0, 0, "Default Value " + i);
            states.add(stateSupplier.get());
        });
        testUndoRedo(states, stateSupplier);
    }

    /**
     * Undo/Redo test for use case 4.8: Delete Column
     */
    @Test
    public void undoRedoDeleteColumnTest() {
        database.addTable();

        List<List<Column>> states = new ArrayList<>();
        Supplier<List<Column>> stateSupplier = () -> Arrays.stream(database.getColumns(0)).collect(Collectors.toList());
        IntStream.range(0, runs).forEach(i -> database.addColumn(0));

        for (int id = runs - 1; id >= 0; id--) {
            controller.deleteColumn(0, id);
            states.add(stateSupplier.get());
        }

        testUndoRedoList(states, stateSupplier);
    }

    /**
     * Undo/Redo test for use case 4.9: Add Row
     */
    @Test
    public void undoRedoAddRowTest() {
        database.addTable();
        database.addColumn(0);
        List<List<Row>> states = new ArrayList<>();
        Supplier<List<Row>> stateSupplier = () -> Arrays.stream(database.getRows(0)).collect(Collectors.toList());

        for (int i = 0; i < runs; i++) {
            controller.addColumnToTable(0);
            states.add(stateSupplier.get());
        }

        testUndoRedoList(states, stateSupplier);
    }

    /**
     * Undo/Redo test for use case 4.10: Edit Row Value
     */
    @Test
    public void undoRedoEditRowValueTest() {
        database.addTable();
        database.addColumn(0);
        database.addRow(0);
        List<List<Row>> states = new ArrayList<>();
        Supplier<List<Row>> stateSupplier = () -> Arrays.stream(database.getRows(0)).collect(Collectors.toList());

        for (int i = 0; i < runs; i++) {
            controller.setValue(0, 0, 0, "Value" + i);
            states.add(stateSupplier.get());
        }

        testUndoRedoList(states, stateSupplier);
    }

    /**
     * Undo/Redo test for use case 4.11: Delete row
     */
    @Test
    public void undoRedoDeleteRowTest() {
        database.addTable();
        database.addColumn(0);
        database.addRow(0);

        List<List<Row>> states = new ArrayList<>();
        Supplier<List<Row>> stateSupplier = () -> Arrays.stream(database.getRows(0)).collect(Collectors.toList());
        IntStream.range(0, runs).forEach(i -> database.addRow(0));

        for (int id = runs - 1; id >= 0; id--) {
            controller.deleteRow(0, id);
            states.add(stateSupplier.get());
        }

        testUndoRedoList(states, stateSupplier);
    }
}
