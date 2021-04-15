package com.swopteam07.tablr.test.usecases;

import com.swopteam07.tablr.model.Row;
import com.swopteam07.tablr.model.cell.Cell;
import com.swopteam07.tablr.model.column.DataType;
import com.swopteam07.tablr.test.facade.UiTestFacade;
import com.swopteam07.tablr.view.TableDesignView;
import com.swopteam07.tablr.view.TableRowView;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AddRowTest extends UsecaseTest {

    /**
     * UseCase 4.9
     * View is in tableRowView, or TablesRowSubwindow has been selected
     * Adding a row by doubleClicking beneath the column
     * The row has the default properties of the column (in this case null)
     */
    @Test
    public void successAddRowNullTest() {
        controller.addTable();
        controller.addColumnToTable(0);
        controller.tableRowView(0);

        assertEquals(0, database.getTable(0).getNbRows());
        UiTestFacade.clickBeneathView(110, 2);
        assertEquals(1, database.getTable(0).getNbRows());

        Row row = database.getRow(0, 0);
        Cell cell = row.getCell(0);
        assertSame(cell.getDataType(), DataType.STRING);
        assertNull(cell.getValue());
    }

    /**
     * UseCase 4.9
     * View is in tableRowView, or TablesRowSubwindow has been selected
     * Adding a row by doubleClicking beneath the column
     * The row has the default properties of the column
     */
    @Test
    public void rowDefaultValueTest() {
        controller.addTable();
        controller.addColumnToTable(0);
        controller.updateDefaultValue(0, 0, "defaultValueTest");
        controller.tableRowView(0);

        assertEquals(0, database.getTable(0).getNbRows());
        UiTestFacade.clickBeneathView(110, 2);
        assertEquals(1, database.getTable(0).getNbRows());

        Row row = controller.getDatabase().getRow(0, 0);
        Cell cell = row.getCell(0);
        assertSame(cell.getDataType(), DataType.STRING);
        assertEquals("defaultValueTest", cell.getValue());
    }

    /**
     * UseCase 4.9
     * A new row can be added from a form window by pressing Ctrl + N.
     */
    @Test
    public void addRowFromFormView() {
        controller.addTable();
        controller.addColumnToTable(0);
        controller.formView(0);

        assertEquals(0, database.getTable(0).getNbRows());
        UiTestFacade.pressControl();
        UiTestFacade.pressN();
        assertEquals(1, database.getTable(0).getNbRows());
    }
}
