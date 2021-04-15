package com.swopteam07.tablr.test.usecases;

import com.swopteam07.tablr.test.facade.UiTestFacade;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

public class EditTableNameTest extends UsecaseTest {

    /**
     * UseCase 4.2
     * View is in tablesMode, or TablesModeSubwindow has been selected
	 * Changing a tableId by Clicking on said table: Succes
     */
    @Test
    public void successEditTableName() {
        controller.addTable();
        controller.getActiveView().handleMouseEvent(500, 360, 120, 1);
        IntStream.range(0, controller.getTablesName().get(0).length()).forEach(i -> UiTestFacade.pressBackspace());
        UiTestFacade.putString("testTable");
        UiTestFacade.pressEnter();
        assertEquals("testTable", controller.getTablesName().get(0));
    }

    /**
     * UseCase 4.2 extension 5.a
     * View is in tablesMode, or TablesModeSubwindow has been selected
	 * Changing a tableId by Clicking on said table, afterwards cancelling the changes
     * by pressing Escape: Success
     */
    @Test
    public void successCancelEditTableNameTest() {
        controller.addTable();

        controller.getActiveView().handleMouseEvent(500, 360, 120, 1);

        String originalName = controller.getTablesName().get(0);
        IntStream.range(0, originalName.length()).forEach(i -> UiTestFacade.pressBackspace());

        UiTestFacade.putString("testTable");
        UiTestFacade.pressEscape();
        assertEquals(originalName, controller.getTablesName().get(0));
    }

    /**
     * UseCase 4.2 extension 6.a
     * View is in tablesMode, or TablesModeSubwindow has been selected
	 * Changing a tableId by Clicking on said table to a name already in use
     * After pressing enter the tableField stays selected and shows invalid
     */
    @Test
    public void failEditTableNameTest() {
        controller.addTable();
        controller.addTable();
        String checkTableName = controller.getTableName(0);
        String tableName = controller.getTableName(1);

        controller.getActiveView().handleMouseEvent(500, 360, 120, 1);
        assertTrue(controller.getActiveView().getRoot().getSelectedComponent().isValid());

        String originalName = controller.getTablesName().get(0);
        IntStream.range(0, originalName.length()).forEach(i -> UiTestFacade.pressBackspace());

        UiTestFacade.putString(tableName);
        UiTestFacade.pressEnter();

        assertFalse(controller.getActiveView().hasValidState());
        assertFalse(controller.getActiveView().getRoot().getSelectedComponent().isValid());

        UiTestFacade.pressEscape();
        assertEquals(checkTableName, controller.getTableName(0));
    }
}
