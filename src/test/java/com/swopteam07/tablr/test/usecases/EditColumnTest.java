package com.swopteam07.tablr.test.usecases;

import com.swopteam07.tablr.UI.Component;
import com.swopteam07.tablr.controller.Controller;
import com.swopteam07.tablr.model.column.Column;
import com.swopteam07.tablr.test.facade.UiTestFacade;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EditColumnTest extends UsecaseTest {

    /**
     * UseCase 4.7 Main Success Scenario
     * View is in tablesDesignMode, or TablesDesignSubwindow has been selected
     * Changing a ColumnName by Clicking on said table: Success
     */
    @Test
    public void successEditColumnName() {
        controller.addTable();
        controller.tableDesignView(0);
        controller.addColumnToTable(0);

        Controller.getInstance().getActiveView().handleMouseEvent(500, 360, 120, 1);
        int strLength = Controller.getInstance().getColumnName(0, 0).length();
        IntStream.range(0, strLength).forEach(i -> UiTestFacade.pressBackspace());

        UiTestFacade.putString("testColumn");
        UiTestFacade.pressEnter();
        assertEquals("testColumn", controller.getColumnName(0, 0));
    }

    /**
     * UseCase 4.7 Main Scenario
     * Changing the name of a column to a name that is already in use is impossible, the view is marked as invalid.
     * Pressing escape should cancel the editing and the value should be reset to the value it had before editing began.
     */
    @Test
    public void failEditTableNameTest() {
        controller.addTable();
        controller.addColumnToTable(0);
        controller.addColumnToTable(0);
        controller.tableDesignView(0);

        String columnName1 = controller.getColumnName(0, 0);
        String columnName2 = controller.getColumnName(0, 1);

        controller.getActiveView().handleMouseEvent(500, 360, 120, 1);
        IntStream.range(0, columnName1.length()).forEach(i -> UiTestFacade.pressBackspace());
        UiTestFacade.putString(columnName2);
        UiTestFacade.pressEnter();

        assertFalse(controller.getActiveView().hasValidState());
        assertFalse(controller.getActiveView().getRoot().getSelectedComponent().isValid());

        UiTestFacade.pressEscape();
        UiTestFacade.pressEnter();
        assertEquals(columnName1, controller.getColumnName(0, 0));
    }

    /**
     * UseCase 4.7.1 extension of 4.2.5.a
     * Pressing escape should cancel editing and reset the value of the textfield to the value it had before editing.
     */
    @Test
    public void successCancelEditColumnNameTest() {
        controller.addTable();
        controller.tableDesignView(0);
        controller.addColumnToTable(0);

        controller.getActiveView().handleMouseEvent(500, 360, 120, 1);

        String name = controller.getColumnName(0, 0);
        IntStream.range(0, name.length()).forEach(i -> UiTestFacade.pressBackspace());

        UiTestFacade.putString("testColumn");
        UiTestFacade.pressEscape();
        UiTestFacade.pressEnter();
        assertEquals(name, controller.getColumnName(0, 0));
    }

    /**
     * UseCase 4.7 Main Success Scenario
     * Changing the name of a column is impossible if the column is referenced in a query.
     */
    @Test
    public void failureEditColumnNameForReferencedColumn() {
        assertTrue(true); // TODO
    }

    /**
     * UseCase 4.7 Extension A
     * The column type should change as follows:
     * String -> Email
     * Email -> Boolean
     * Boolean -> Integer
     * Integer -> String
     */
    @Test
    public void successChangeColumnTypeTest() {
        controller.addTable();
        controller.addColumnToTable(0);
        controller.tableDesignView(0);

        assertEquals("STRING", database.getColumn(0, 0).getType());

        controller.getActiveView().handleMouseEvent(500, 500, 115, 1);
        assertEquals("EMAIL", database.getColumn(0, 0).getType());

        controller.getActiveView().handleMouseEvent(500, 500, 115, 1);
        assertEquals("BOOLEAN", database.getColumn(0, 0).getType());

        controller.getActiveView().handleMouseEvent(500, 500, 115, 1);
        assertEquals("INTEGER", database.getColumn(0, 0).getType());

        controller.getActiveView().handleMouseEvent(500, 500, 115, 1);
        assertEquals("STRING", database.getColumn(0, 0).getType());
    }

    /**
     * Changing columns shouldn't be possible when the default value can't be parsed as the new type.
     */
    @Test
    public void failureChangeColumnTypeTest() {
        controller.addTable();
        controller.addColumnToTable(0);
        controller.updateDefaultValue(0, 0, "This is a test");
        controller.tableDesignView(0);

        controller.getActiveView().handleMouseEvent(500, 500, 115, 1);
        assertFalse(controller.getActiveView().hasValidState());
        assertFalse(controller.getActiveView().getRoot().getSelectedComponent().isValid());
        selectingLocked(controller.getActiveView().getRoot().getSelectedComponent(), 51, 283, 205, 980);
    }

    /**
     * UseCase 4.7.1b
     * Changing The BlankCheckBox after adding a Default value
     */
    @Test
    public void successChangeBlankColumnTest() {
        controller.addTable();
        controller.getDatabase().addColumn(0);
        controller.tableDesignView(0);

        Column column = database.getTable(0).getColumns()[0];
        assertTrue(column.allowsBlank());
        assertTrue(column.defaultIsBlank());

        controller.getActiveView().handleMouseEvent(500, 890, 120, 1);
        UiTestFacade.putString("default");
        UiTestFacade.pressEnter();
        controller.getActiveView().handleMouseEvent(500, 710, 120, 1);

        column = database.getTable(0).getColumns()[0];
        assertFalse(column.allowsBlank());
        assertFalse(column.defaultIsBlank());
        assertEquals("default", column.getDefaultValue());
    }

    /**
     * UseCase 4.7.1b
     * View is in tablesDesignMode, or TablesDesignSubwindow has been selected
     * Changing The BlankCheckBox without adding a default value and giving an invalid State
     * For Checking if valid value see Tests Validators
     */
    @Test
    public void failChangeBlankColumnTest() {
        controller.addTable();
        controller.getDatabase().addColumn(0);
        controller.tableDesignView(0);

        Column column = database.getTable(0).getColumn(0);
        assertTrue(column.allowsBlank());
        assertTrue(column.defaultIsBlank());

        // Click the checkbox
        controller.getActiveView().handleMouseEvent(500, 710, 120, 1);

        // The view and the checkbox should be marked invalid.
        assertFalse(controller.getActiveView().hasValidState());
        Component component = controller.getActiveView().getRoot().getSelectedComponent();
        assertFalse(component.isValid());

        // Clicking elsewhere in the ui shouldn't do anything.
        selectingLocked(component, 51, 283, 205, 980);

        column = database.getTable(0).getColumn(0);
        assertTrue(column.allowsBlank());
        assertTrue(column.defaultIsBlank());
    }

    /**
     * UseCase 4.7.1c
     * View is in tablesDesignMode, or TablesDesignSubwindow has been selected
     * Changing the default Values for StringColumn
     */
    @Test
    public void successChangeStringColumnDefaultValueTest() {
        controller.addTable();
        controller.getDatabase().addColumn(0);
        controller.tableDesignView(0);

        controller.getActiveView().handleMouseEvent(500, 890, 120, 1);// select default value
        UiTestFacade.putString("test");
        UiTestFacade.pressEnter();
        controller.getActiveView().handleMouseEvent(500, 710, 120, 1);// select blank

        Column column = database.getColumn(0, 0);
        assertFalse(column.allowsBlank());
        assertFalse(column.defaultIsBlank());
        assertEquals("test", column.getDefaultValue());
    }

    /**
     * UseCase 4.7.1c
     * Changing the default Values for EmailColumn
     * For Checking if valid value see Tests Validators
     */
    @Test
    public void successChangeEmailColumnDefaultValueTest() {
        controller.addTable();
        controller.getDatabase().addColumn(0);
        controller.updateColumnType(0, 0, "EMAIL");
        controller.tableDesignView(0);

        controller.getActiveView().handleMouseEvent(500, 890, 120, 1);// select default value
        UiTestFacade.putString("test@test");
        UiTestFacade.pressEnter();
        controller.getActiveView().handleMouseEvent(500, 710, 120, 1);// select blank

        Column column = database.getColumn(0, 0);
        assertFalse(column.allowsBlank());
        assertFalse(column.defaultIsBlank());
        assertEquals("test@test", column.getDefaultValue());
    }

    /**
     * UseCase 4.7.1c
     * Changing the default Values for BooleanColumn
     * For Checking if valid value see Tests Validators
     */
    @Test
    public void successChangeBooleanColumnDefaultValueTest() {
        controller.addTable();
        controller.addColumnToTable(0);
        controller.updateColumnType(0, 0, "BOOLEAN");
        controller.tableDesignView(0);

        controller.getActiveView().handleMouseEvent(500, 890, 120, 1); // Change the default value.
        controller.getActiveView().handleMouseEvent(500, 710, 120, 1); // Disable blanks

        Column column = database.getColumn(0, 0);
        assertFalse(column.allowsBlank());
        assertFalse(column.defaultIsBlank());
        assertEquals(true, column.getDefaultValue());
    }

    /**
     * UseCase 4.7.1c
     * Changing the default Values for IntegerColumn
     */
    @Test
    public void successChangeIntegerColumnDefaultValueTest() {
        controller.addTable();
        controller.addColumnToTable(0);
        controller.updateColumnType(0, 0, "INTEGER");
        controller.tableDesignView(0);

        controller.getActiveView().handleMouseEvent(500, 890, 120, 1); // Select default value
        UiTestFacade.putString("55");
        UiTestFacade.pressEnter();
        controller.getActiveView().handleMouseEvent(500, 710, 120, 1); // Disable blanks

        Column column = database.getColumn(0, 0);
        assertFalse(column.allowsBlank());
        assertFalse(column.defaultIsBlank());
        assertEquals(55, column.getDefaultValue());
    }

    /**
     * UseCase 4.7 Extension 1c
     * When an invalid default value has been entered, nothing else can happen before the value is changed.
     */
    @Test
    public void failureChangeDefaultValueTest() {
        controller.addTable();
        controller.addColumnToTable(0);
        controller.updateColumnType(0, 0, "INTEGER");
        controller.tableDesignView(0);

        controller.getActiveView().handleMouseEvent(500, 890, 120, 1);
        Component defaultValue = controller.getActiveView().getRoot().getSelectedComponent();
        UiTestFacade.putString("aasasasfdgsgsgs");
        UiTestFacade.pressEnter();

        selectingLocked(defaultValue, 51, 283, 205, 980);
    }
}
