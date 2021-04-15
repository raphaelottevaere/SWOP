package com.swopteam07.tablr.test.usecases;

import com.swopteam07.tablr.test.facade.UiTestFacade;
import com.swopteam07.tablr.view.TableDesignView;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeleteColumnTest extends UsecaseTest {

    /**
     * UseCase 4.8
     * View is in tablesDesignMode, or TablesDesignSubwindow has been selected
     * Selecting A column by left clicking said column
     * Pressing delete afterwards deletes the column
     **/
    @Test
    public void successDeleteColumnTest() {
        controller.addTable();
        controller.tableDesignView(0);

        assertEquals(0, controller.getAllColumnsHeader(0).length);
        UiTestFacade.clickBeneathView(110, 2);
        assertEquals(1, controller.getAllColumnsHeader(0).length);

        controller.getActiveView().handleMouseEvent(500, 240, 115, 1);
        UiTestFacade.pressDelete();
        assertEquals(0, controller.getAllColumnsHeader(0).length);
    }

    /**
     * UseCase 4.8
     * Computed tables that reference the deleted column are also deleted.
     */
    @Test
    public void deleteComputedTablesTest() {
        assertTrue(true); // TODO
    }
}
