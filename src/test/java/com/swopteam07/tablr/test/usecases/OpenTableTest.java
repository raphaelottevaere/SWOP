package com.swopteam07.tablr.test.usecases;

import com.swopteam07.tablr.view.TableDesignView;
import com.swopteam07.tablr.view.TableRowView;
import com.swopteam07.tablr.view.TablesView;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class OpenTableTest extends UsecaseTest {

    /**
     * UseCase 4.5
     * Double clicking a stored table should open a table design subwindow when the table is doesn't contain columns.
     */
    @Test
    public void openStoredTableNoColumnsTest() {
        controller.addTable();
        assertTrue(controller.getActiveView() instanceof TablesView);
        controller.getActiveView().handleMouseEvent(500, 330, 120, 2);
        assertTrue(controller.getActiveView() instanceof TableDesignView);
    }

    /**
     * UseCase 4.5
     * Double clicking a stored table should open a table row subwindow when the table contains columns.
     */
    @Test
    public void openStoredTableWithColumnsTest() {
        controller.addTable();
        controller.addColumnToTable(0);
        assertTrue(controller.getActiveView() instanceof TablesView);
        controller.getActiveView().handleMouseEvent(500, 330, 120, 2);
        assertTrue(controller.getActiveView() instanceof TableRowView);
    }

    /**
     * UseCase 4.5
     * Double clicking on a computed table should always open a table row subwindow.
     */
    @Test
    public void openComputedTableNoColumns() {
        // TODO
        assertTrue(true);
    }

    /**
     * UseCase 4.5
     * Double clicking on a computed table should always open a table row subwindow.
     */
    @Test
    public void openComputedTableWithColumns() {
        // TODO
        assertTrue(true);
    }
}
