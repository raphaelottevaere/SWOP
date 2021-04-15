package com.swopteam07.tablr.test.usecases;

import com.swopteam07.tablr.model.Table;
import com.swopteam07.tablr.model.column.DataType;
import com.swopteam07.tablr.test.facade.UiTestFacade;

import com.swopteam07.tablr.view.FormView;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DeleteRowTest extends UsecaseTest {

    /**
     * UseCase 4.11
     * View is in tablesRowMode, or TablesRowSubwindow has been selected
     * Selecting A Row by left clicking said column
     * Pressing delete afterwards deletes the row
     */
    @Test
    public void successDeleteRowValueTest() {
        controller.addTable();
        controller.addColumnToTable(0);
        controller.addRow(0);
        controller.tableRowView(0);

        assertEquals(1, database.getTable(0).getNbRows());
        controller.getActiveView().handleMouseEvent(500, 240, 110, 1);
        UiTestFacade.pressDelete();
        assertEquals(0, database.getTable(0).getNbRows());
    }

    /**
     * UseCase 4.11
     * A row in a computed table shouldn't be deletable.
     */
    public void failureDeleteRowStoredTableTest() {
        assertTrue(true); // TODO
    }

    /**
     * UseCase 4.11 extension 1
     * A row can be deleted from the form view when Ctrl + D is pressed.
     */
    @Test
    public void deleteRowFormView() {
        controller.addTable();
        controller.addColumnToTable(0);
        controller.addRow(0);
        controller.formView(0);

        // No need to check cast since this is already tested in OpenViewsTest.
        FormView view = (FormView) controller.getActiveView();
        assertNotNull(view.getCurrentRowId());
        int currentRowNumber = view.getCurrentRowNumber();

        assertEquals(1, database.getTable(0).getNbRows());
        UiTestFacade.pressControl();
        UiTestFacade.pressD();
        assertEquals(0, database.getTable(0).getNbRows());

        assertNull(view.getCurrentRowId());
        assertEquals(currentRowNumber, view.getCurrentRowNumber());
    }

    /**
     * UseCase 4.11 extension 1
     * When a row is deleted from the from view the new k'th row is shown.
     */
    @Test
    public void deleteRowFormViewRowNumberTest() {
        controller.addTable();
        controller.addColumnToTable(0);
        controller.addRow(0);
        controller.addRow(0);
        controller.formView(0);

        // No need to check cast since this is already tested in OpenViewsTest.
        FormView view = (FormView) controller.getActiveView();
        assertEquals(0, view.getCurrentRowNumber());
        assertEquals(0, (int) view.getCurrentRowId());

        UiTestFacade.pressControl();
        UiTestFacade.pressD();

        assertEquals(0, view.getCurrentRowNumber());
        assertEquals(1, (int) view.getCurrentRowId());
    }
}
