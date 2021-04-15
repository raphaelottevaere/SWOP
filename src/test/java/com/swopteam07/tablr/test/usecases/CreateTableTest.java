package com.swopteam07.tablr.test.usecases;

import com.swopteam07.tablr.controller.Controller;
import com.swopteam07.tablr.test.facade.UiTestFacade;
import com.swopteam07.tablr.view.TablesView;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateTableTest extends UsecaseTest {

    /**
     * UseCase 4.1
     * View is in tablesMode, or TablesModeSubwindow has been selected
     * Successfully adding a table by DoubleClicking beneath table
     */
    @Test
    public void successAddTableTest() {
        controller.getActiveView().handleMouseEvent(500, 100, 110, 2);
        assertEquals(1, controller.getTableCount());
        assertEquals("Table0", controller.getTablesName().get(0));

        // TODO Test blank query.
    }

    /**
     * UseCase 4.1
     * View is in tablesMode, or TablesModeSubwindow has been selected
     * Not adding a table by SingleClicking beneath table
     */
    @Test
    public void failOneClickAddTableTest() {
        controller.getActiveView().handleMouseEvent(500, 100, 110, 1);
        assertEquals(0, controller.getTableCount());
    }

    /**
     * UseCase 4.1
     * View is in tablesMode, or TablesModeSubwindow has been selected
     * Not adding a table by tripleClicking beneath table
     */
    @Test
    public void failThreeClickAddTableTest() {
        controller.getActiveView().handleMouseEvent(500, 100, 110, 3);
        assertEquals(0, controller.getTableCount());
    }

    /**
     * Checking if NameGiving is performed as requested in UseCase 4.1
     */
    @Test
    public void verifyTableStateTest() {
        for (int i = 0; i < 5; i++) {
            controller.addTable();
        }
        for (int i = 0; i < controller.getTablesName().size(); i++) {
            assertEquals(controller.getTablesName().get(i), "Table" + i);
            assertEquals(0, controller.getAllRowsID(i).length);
            assertEquals(0, controller.getAllColumnsHeader(i).length);
        }
    }
}
