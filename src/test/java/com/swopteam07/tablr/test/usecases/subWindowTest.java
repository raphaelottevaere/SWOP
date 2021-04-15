package com.swopteam07.tablr.test.usecases;

import com.swopteam07.tablr.test.facade.UiTestFacade;
import com.swopteam07.tablr.view.TableDesignView;
import com.swopteam07.tablr.view.TableRowView;
import com.swopteam07.tablr.view.TablesView;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class subWindowTest extends UsecaseTest {

    /**
     * Starting UseCase
     * See if SubViews can be opened and closed as per previous rules
     * Closing happens with the Close Button on the top Right
     * Opening happens as per determined in the use case
     */
    @Test
    public void closingSubViews() {
        assertTrue(controller.getActiveView() instanceof TablesView);
        controller.getActiveView().handleMouseEvent(500, 360, 120, 2);
        assertTrue(controller.getActiveView() instanceof TablesView);
        //force a redraw
        controller.getActiveView().handleMouseEvent(500, 360, 120, 1);
        controller.getActiveView().handleMouseEvent(500, 360, 120, 2);
        assertTrue(controller.getActiveView() instanceof TableDesignView);
        UiTestFacade.pressControl();
        UiTestFacade.pressEnter();
        assertTrue(controller.getActiveView() instanceof TableRowView);
        controller.getActiveView().handleMouseEvent(500, 580, 35, 1);
        assertTrue(controller.getActiveView() instanceof TableDesignView);
        controller.getActiveView().handleMouseEvent(500, 990, 35, 1);
        assertTrue(controller.getActiveView() instanceof TablesView);
        int tableId = controller.getDatabase().getAllTableIds()[controller.getDatabase().getAllTableIds().length - 1];
        controller.getDatabase().deleteTable(tableId);
    }

    /**
     * Starting UseCase
     * See if SubViews can be opened and closed as per previous rules
     * Closing happens with the Close Button on the top Right
     * Opening happens as per determined in the use case
     */
    @Test
    public void openingTableView() {
        assertTrue(controller.getActiveView() instanceof TablesView);
        controller.getActiveView().handleMouseEvent(500, 580, 35, 1);
        assertNull(controller.getActiveView());
        UiTestFacade.pressControlController();
        UiTestFacade.pressTController();
        assertTrue(controller.getActiveView() instanceof TablesView);
    }

    /**
     * Starting UseCase
     * See if SubViews can be dragged around as expected
     */
    @Test
    public void draggingView() {
        int prevX;
        int prevY;
        assertTrue(controller.getActiveView() instanceof TablesView);
        prevX = controller.getActiveView().getRoot().getX();
        prevY = controller.getActiveView().getRoot().getY();
        //moving the subwindow
        controller.getActiveView().handleMouseEvent(506, 300, 30, 1);
        controller.getActiveView().handleMouseEvent(506, 305, 35, 1);
        controller.getActiveView().handleMouseEvent(506, 310, 40, 1);
        assertTrue(controller.getActiveView() instanceof TablesView);
        assertTrue((controller.getActiveView().getRoot().getX() != prevX));
        assertTrue((controller.getActiveView().getRoot().getY() != prevY));
    }

    /**
     * Starting UseCase
     * See if views can be resized as expected
     */
    @Test
    public void resizingView() {
        int prevX;
        int prevY;
        assertTrue(controller.getActiveView() instanceof TablesView);
        prevX = controller.getActiveView().getRoot().getX();
        prevY = controller.getActiveView().getRoot().getY() + controller.getActiveView().getRoot().getHeight();
        //dragging X
        controller.getActiveView().handleMouseEvent(506, 200, 226, 1);
        controller.getActiveView().handleMouseEvent(506, 210, 226, 1);
        controller.getActiveView().handleMouseEvent(506, 220, 226, 1);
        assertTrue(controller.getActiveView() instanceof TablesView);
        assertTrue((controller.getActiveView().getRoot().getX() != prevX));
        assertTrue((controller.getActiveView().getRoot().getY() + controller.getActiveView().getRoot().getHeight() == prevY));

        prevX = controller.getActiveView().getRoot().getX();
        prevY = controller.getActiveView().getRoot().getY();
        //DraggingY
        controller.getActiveView().handleMouseEvent(506, 414, 420, 1);
        controller.getActiveView().handleMouseEvent(506, 414, 430, 1);
        controller.getActiveView().handleMouseEvent(506, 414, 440, 1);
        assertTrue(controller.getActiveView() instanceof TablesView);
        assertEquals(controller.getActiveView().getRoot().getX(), prevX);
        assertTrue(controller.getActiveView().getRoot().getY() + controller.getActiveView().getRoot().getHeight() != prevY);
    }

}
