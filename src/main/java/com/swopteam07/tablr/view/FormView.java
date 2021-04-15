package com.swopteam07.tablr.view;

import com.swopteam07.tablr.UI.*;
import com.swopteam07.tablr.UI.Checkbox;
import com.swopteam07.tablr.UI.Label;
import com.swopteam07.tablr.UI.Panel;
import com.swopteam07.tablr.UI.TextField;
import com.swopteam07.tablr.controller.Controller;
import com.swopteam07.tablr.model.cell.DataCell;
import com.swopteam07.tablr.model.column.DataType;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class FormView extends View {

    /**
     * The ID of the table is edited in this view.
     */
    private final int tableId;

    /**
     * The ID's of the columns in the table mapped to the UI components that show the value for that column.
     */
    private final Map<Integer, DrawableComponent> columnComponents = new HashMap<>();

    /**
     * The ids of the columns in this table.
     */
    private int[] columnIds;

    /**
     * the Ids of the rows
     */
    private int[] rowIds;

    /**
     * the number of the row that currently is being shown
     */
    private int currentRowNumber;

    /**
     * the panel for the labels and textfields
     */
    private Panel panel;

    /**
     * the names of the columns
     */
    private String[] columnNames;

    private TextField currentTextField;
    private int currentColumnId;

    public FormView(Controller controller, int tableId, String[] columnNames, int[] columnIds) {
        super(new WindowPanel(200, 20, 400, 500, null), controller);
        this.tableId = tableId;
        rowIds = controller.getAllRowsID(tableId);
        this.currentRowNumber = 0;
        panel = new Panel(10, 50, 300, 300, getRoot());
        panel.setColor("Background", new Color(203, 203, 203, 243));
        root.addChild(panel);
        root.enableVerticalScrollbar();
        root.enableHorizontalScrollbar();
        updateTitle(currentRowNumber);
        setColumnInfo(columnNames, columnIds);
    }

    /**
     * initiate the variables and empty the panel
     * @param columnNames The names of the columns in this form view.
     * @param columnIds The ids of the columns in this formview.
     */
    public void setColumnInfo(String[] columnNames, int[] columnIds) {
        this.columnIds = columnIds;
        this.columnNames = columnNames;
        columnComponents.clear();
        panel.emptyPanel();
    }

    /**
     * Build the view.
     */
    public void showData() {
        int marginY = 40;
        int y = 0;
        currentColumnId = -1;
        this.rowIds = controller.getAllRowsID(tableId);
        setColumnInfo(controller.getAllColumnsHeader(tableId),controller.getColumnIds(tableId));


        panel.setVisible(rowIds.length > currentRowNumber);

        if (columnNames.length == 0 || currentRowNumber >= rowIds.length) return;

        for (int i = 0; i < columnNames.length; i++) {
            y = y + marginY;

            if (!columnComponents.containsKey(columnIds[i])) {
                Label l = new Label(30, y, 30, 100, columnNames[i], panel);
                panel.addChild(l);
            }
            setDataForColumn(rowIds[currentRowNumber], columnIds[i], y);
        }
        updateTitle(currentRowNumber);

    }

    /**
     * build the view
     * @param rowId
     * @param columnId
     * @param y
     */
    private void setDataForColumn(int rowId, int columnId, int y) {
        DataCell cell = controller.getDataForCell(tableId, rowId, columnId);

        if (columnComponents.containsKey(columnId)) {
            if (cell.getType() == DataType.BOOLEAN)
                ((Checkbox) columnComponents.get(columnId)).setValue((Boolean) cell.getValue());
            else
                ((TextField) columnComponents.get(columnId)).setText(cell.getValue() == null ? "" : cell.getValue().toString());
        } else {
            DrawableComponent drawableComponent = convertCellToUiComponent(cell, 150, y, 20, 100);

            if (drawableComponent instanceof TextField) {
                TextField textField = (TextField) drawableComponent;
                textField.setBorder(true);
                addPropertyListenerTextField(textField, columnId);
            }

            if (drawableComponent instanceof Checkbox) {
                addPropertyListenerCheckbox((Checkbox) drawableComponent, columnId);
            }
            panel.addChild(drawableComponent);
            columnComponents.put(columnId, drawableComponent);
        }
    }

    /**
     * Converts an array DataCell containing a DataType (defined Enum) to an array
     * of UI elements (CheckBox, TextField, ...)
     *
     * @param dc DataCell array
     * @return DrawableComponent[]
     */
    private DrawableComponent convertCellToUiComponent(DataCell dc, int x, int y, int height, int width) {
        CellFactory cf = new CellFactory();
        return cf.dataCellToUI(dc, panel, x, y, height, width);
    }

    /**
     * Adds a property listener to the textfield for editing
     * depending on the users actions
     */
    private void addPropertyListenerTextField(TextField t, int cellId) {
        t.addTextFieldChangeHandler(event -> {

            currentColumnId = cellId;
            currentTextField = t;
            validState = controller.checkValidValue(tableId, cellId, event.getNewValue());
            root.setCanBeSelected(validState);
            root.setAllowsUnselect(validState);
            t.setValid(validState);
        });
    }

    /**
     * Adds a property listener to the Checkbox for editing
     * depending on the users actions
     */
    private void addPropertyListenerCheckbox(Checkbox c, int cellId) {
        c.addCheckboxChangeHandler(event -> {

            currentColumnId = cellId;
            controller.setValue(tableId, rowIds[currentRowNumber], cellId, event.getNewValue());
        });
    }

    /**
     * Update the title of this view.
     * @param row Number in 1.. that indicates which row is currently being shown to the user.
     */
    public void updateTitle(int row) {
        if (rowIds.length == 0 || row == -1) {
            root.setTitle("Table: " + controller.getTableName(tableId) + ", " + "Row: ");
        } else {
            root.setTitle("Table: " + controller.getTableName(tableId) + ", " + "Row: " + (row + 1));
        }
    }

    /**
     * Update the title of this view if the name of the table is edited
     */
    public void updateTitle() {

        int rowsLength = controller.getAllRowsID(tableId).length;

        if (rowsLength == 0) {
            root.setTitle("Table: " + controller.getTableName(tableId) + ", " + "Row: ");
        }
        if (rowsLength == currentRowNumber ) {
            root.setTitle("Table: " + controller.getTableName(tableId) + ", " + "Row: ");
        }
        if(rowsLength!=0 && rowsLength !=currentRowNumber) {
            root.setTitle("Table: " + controller.getTableName(tableId) + ", " + "Row: " + (currentRowNumber + 1));
        }
    }
    /**
     * @return The id of the table that this view allows the user to edit.
     */
    public Integer getTableId() {
        return tableId;
    }

    public Integer getCurrentRowId() {
        return currentRowNumber < rowIds.length ? rowIds[currentRowNumber] : null;
    }

    public int getCurrentRowNumber() {
        return currentRowNumber;
    }

    /**
     * Pressing the page up button shows the next row and if there isn't a next row it will only show the column names
     */
    @Override
    public void pageUpPressed() {
        if (rowIds.length > currentRowNumber + 1) {
            currentRowNumber++;
            showData();
        } else if (rowIds.length == currentRowNumber + 1) {
            currentRowNumber++;
            showData();
            updateTitle(-1);
        }
        currentColumnId = -1;
    }

    /**
     * Pressing the page up button shows the previous row
     */
    @Override
    public void pageDownPressed() {
        if (currentRowNumber > 0) {
            currentRowNumber--;
            showData();
            currentColumnId = -1;
        }
    }


    /**
     * Pressing the CTRL+ N button adds a row
     */
    @Override
    public void ctrlNPressed() { // TODO: only allowed when it is a stored table
        if (columnIds.length > 0) {
            controller.addRow(tableId);
            updateTitle(currentRowNumber);
        }
    }

    /**
     * Pressing the CTRL+ D  deletes the current row
     */
    @Override
    public void ctrlDPressed() {// TODO: only allowed when it is a stored table

        if (currentRowNumber == 0 && rowIds.length != 0) {
            controller.deleteRow(tableId, rowIds[currentRowNumber]);
            showData();
        }
        if ( currentRowNumber > 0) {
            if( rowIds.length-1 == currentRowNumber){
                controller.deleteRow(tableId, rowIds[currentRowNumber]);
                currentRowNumber--;
                showData();
            }else if(rowIds.length != currentRowNumber){
                controller.deleteRow(tableId, rowIds[currentRowNumber]);
                showData();
            }

        }


    }

    /**
     * @param widths The new widths of the columns of this table.
     */
    @Override
    public void updateCellWidths(int[] widths) {

    }

    /**
     * @param y          The y coordinate of the point where the user clicked.
     * @param clickCount The number of times the user clicked beneath view.
     */
    @Override
    public void clickedBeneathView(int y, int clickCount) {

    }

    /**
     * React to the user selecting a component in the view.
     */
    @Override
    public void selectedDifferentComponent() {
        updateTextField();
    }

    /**
     * React the the user pressing the enter key, this updates the table name.
     */
    @Override
    public void enterPressed() {
        updateTextField();
    }

    /**
     * update the value of the textfield if valid value
     */
    private void updateTextField() {
        if (currentColumnId == -1 || currentTextField == null || !validState && currentRowNumber < rowIds.length)
            return;
        controller.setValue(tableId, rowIds[currentRowNumber], currentColumnId, currentTextField.getText());
        currentColumnId = -1;
        currentTextField = null;
    }

}