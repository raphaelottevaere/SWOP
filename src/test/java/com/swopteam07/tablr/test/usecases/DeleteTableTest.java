package com.swopteam07.tablr.test.usecases;

import com.swopteam07.tablr.test.facade.UiTestFacade;
import com.swopteam07.tablr.view.TablesView;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeleteTableTest extends UsecaseTest {

    /**
     * UseCase 4.4
     * View is in tablesMode, or TablesModeSubwindow has been selected
     * Deleting a table by Clicking on left of said table
     * After pressing delete the table is removed
     */
    @Test
    public void successDeleteTableTest() {
        controller.addTable();
        assertTrue(controller.getTableCount() == 1);
        UiTestFacade.pressEscape();
        assertTrue(controller.getActiveView() instanceof TablesView);
        controller.getActiveView().handleMouseEvent(500, 240, 110, 1);
        UiTestFacade.pressDelete();
        assertEquals(0, controller.getTableCount());
    }

    /**
     * UseCase 4.4
     * When deleting a table all computed tables that reference this table need to be deleted as well.
     */
    @Test
    public void successDeleteComputedTablesTests() {
        assertTrue(true); // TODO
    }
}
