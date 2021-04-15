package com.swopteam07.tablr.test.usecases;

import com.swopteam07.tablr.test.facade.UiTestFacade;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EditRowValueTest extends UsecaseTest {

    /**
     * UseCase 4.10
     * View is in tablesRowMode, or TablesRowSubwindow has been selected
     * Entering a valid Value for a cell changes the value of that cell.
     */
    @Test
    public void successEditRowValueStringColumnTest() {
        controller.addTable();
        controller.addColumnToTable(0);
        controller.addRow(0);
        controller.tableRowView(0);

        controller.getActiveView().handleMouseEvent(500, 280, 120, 1);
        UiTestFacade.putString("testRowName");
        UiTestFacade.pressEnter();
        assertEquals("testRowName", database.getCellValue(0, 0, 0));
    }

    /**
     * UseCase 4.10
     * View is in tablesRowMode, or TablesRowSubwindow has been selected
     * Entering a valid Value for a cell changes the value of that cell.
     */
    @Test
    public void successEditRowValueEmailColumnTest() {
        controller.addTable();
        controller.addColumnToTable(0);
        controller.updateColumnType(0, 0, "EMAIL");
        controller.addRow(0);
        controller.tableRowView(0);

        controller.getActiveView().handleMouseEvent(500, 280, 120, 1);
        UiTestFacade.putString("test@test.test");
        UiTestFacade.pressEnter();
        assertEquals("test@test.test", database.getCellValue(0, 0, 0));
    }

    /**
     * UseCase 4.10
     * View is in tablesRowMode, or TablesRowSubwindow has been selected
     * Entering a valid Value for a cell changes the value of that cell.
     */
    @Test
    public void successEditRowValueBooleanColumnTest() {
        controller.addTable();
        controller.addColumnToTable(0);
        controller.updateColumnType(0, 0, "BOOLEAN");
        controller.addRow(0);
        controller.tableRowView(0);

        controller.getActiveView().clickedBeneathView(110, 2);
        controller.getActiveView().handleMouseEvent(500, 280, 120, 1);

        assertEquals(true, database.getCellValue(0, 0, 0));
    }

    /**
     * UseCase 4.10
     * View is in tablesRowMode, or TablesRowSubwindow has been selected
     * Entering a valid Value for a cell changes the value of that cell.
     */
    @Test
    public void successEditRowValueIntegerColumnTest() {
        controller.addTable();
        controller.addColumnToTable(0);
        controller.updateColumnType(0, 0, "INTEGER");
        controller.addRow(0);
        controller.tableRowView(0);

        controller.getActiveView().handleMouseEvent(500, 280, 120, 1);
        UiTestFacade.putString("55");
        UiTestFacade.pressEnter();
        assertEquals(55, database.getCellValue(0, 0, 0));
    }

    /**
     * UseCase 4.10
     * Rows can be edited via the form view.
     */
    @Test
    public void successEditRowValueStringCellFormViewTest() {
        controller.addTable();
        controller.addColumnToTable(0);
        controller.addRow(0);
        controller.formView(0);

        controller.getActiveView().handleMouseEvent(500, 402, 120, 1);
        UiTestFacade.putString("this is a test string");
        UiTestFacade.pressEnter();
        assertEquals("this is a test string", database.getCellValue(0, 0, 0));
    }

    /**
     * UseCase 4.10
     * Rows can be edited via the form view.
     */
    @Test
    public void successEditRowValueIntegerCellFormViewTest() {
        controller.addTable();
        controller.addColumnToTable(0);
        controller.updateColumnType(0, 0, "INTEGER");
        controller.addRow(0);
        controller.formView(0);

        controller.getActiveView().handleMouseEvent(500, 402, 120, 1);
        UiTestFacade.putString("2909");
        UiTestFacade.pressEnter();
        assertEquals(2909, database.getCellValue(0, 0, 0));
    }

    /**
     * UseCase 4.10
     * Rows can be edited via the form view.
     */
    @Test
    public void successEditRowValueEmailCellFormViewTest() {
        controller.addTable();
        controller.addColumnToTable(0);
        controller.updateColumnType(0, 0, "EMAIL");
        controller.addRow(0);
        controller.formView(0);

        controller.getActiveView().handleMouseEvent(500, 402, 120, 1);
        UiTestFacade.putString("test@test.com");
        UiTestFacade.pressEnter();
        assertEquals("test@test.com", database.getCellValue(0, 0, 0));
    }

    /**
     * UseCase 4.10
     * Rows can be edited via the form view.
     */
    @Test
    public void successEditRowValueBooleanCellFormViewTest() {
        controller.addTable();
        controller.addColumnToTable(0);
        controller.updateColumnType(0, 0, "BOOLEAN");
        controller.addRow(0);
        controller.formView(0);

        controller.getActiveView().handleMouseEvent(500, 402, 120, 1);
        assertEquals(true, database.getCellValue(0, 0, 0));
    }

    /**
     * UseCase 4.10
     * View is in tablesRowMode, or TablesRowSubwindow has been selected
     * Pressing Escape Doesnt push changes
     */
    @Test
    public void ExitIntegerColumnTest() {
        controller.addTable();
        controller.addColumnToTable(0);
        controller.updateColumnType(0, 0, "INTEGER");
        controller.addRow(0);
        controller.tableRowView(0);

        controller.getActiveView().handleMouseEvent(500, 280, 120, 1);
        UiTestFacade.putString("55");
        UiTestFacade.pressEnter();
        assertEquals(55, database.getCellValue(0, 0, 0));
        controller.getActiveView().handleMouseEvent(500, 280, 120, 1);
        UiTestFacade.putString("100");
        UiTestFacade.pressEscape();
        UiTestFacade.pressEnter();
        assertEquals(55, database.getCellValue(0, 0, 0));
    }

    /**
     * UseCase 4.10
     * View is in tablesRowMode, or TablesRowSubwindow has been selected
     * Entering a invalid Value for said column doesnt change the value
     * For valid values see validator tests
     */
    @Test
    public void failEditRowValueIntegerColumnTest() {
        controller.addTable();
        controller.addColumnToTable(0);
        controller.updateColumnType(0, 0, "INTEGER");
        controller.addRow(0);
        controller.tableRowView(0);

        controller.getActiveView().handleMouseEvent(500, 280, 120, 1);
        UiTestFacade.putString("test");
        UiTestFacade.pressEnter();
        assertNotEquals("test", database.getCellValue(0, 0, 0));
        assertNull(database.getCellValue(0, 0, 0));

        selectingLocked(controller.getActiveView().getRoot().getSelectedComponent(), 53, 397, 206, 581);
    }

    /**
     * UseCase 4.10
     * View is in tablesRowMode, or TablesRowSubwindow has been selected
     * Entering a valid Value for said column changes the value
     */
    @Test
    public void failEditRowValueEmailColumnTest() {
        controller.addTable();
        controller.addColumnToTable(0);
        controller.addRow(0);
        controller.updateColumnType(0, 0, "EMAIL");
        controller.tableRowView(0);

        controller.getActiveView().handleMouseEvent(500, 280, 120, 1);
        UiTestFacade.putString("testtest.test");
        UiTestFacade.pressEnter();
        assertNotEquals("testtest.test", database.getCellValue(0, 0, 0));
        assertNull(database.getCellValue(0, 0, 0));

        selectingLocked(controller.getActiveView().getRoot().getSelectedComponent(), 53, 397, 206, 581);
    }
}
