package com.swopteam07.tablr.test.usecases;

import com.swopteam07.tablr.view.FormView;
import com.swopteam07.tablr.view.TableDesignView;
import com.swopteam07.tablr.view.TableRowView;
import com.swopteam07.tablr.view.TablesView;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OpenViewsTest extends UsecaseTest {

    @Test
    public void startWithTablesViewTest() {
        assertTrue(controller.getActiveView() instanceof TablesView);
    }

    @Test
    public void tablesSubwindowTest() {
        controller.tablesView();
        assertTrue(controller.getActiveView() instanceof TablesView);
    }

    @Test
    public void tableDesignSubwindowTest() {
        controller.addTable();
        controller.tableDesignView(0);
        assertTrue(controller.getActiveView() instanceof TableDesignView);
    }

    @Test
    public void tableRowSubwindowTest() {
        controller.addTable();
        controller.tableRowView(0);
        assertTrue(controller.getActiveView() instanceof TableRowView);
    }

    @Test
    public void formSubwindowTest() {
        controller.addTable();
        controller.formView(0);
        assertTrue(controller.getActiveView() instanceof FormView);
    }
}
