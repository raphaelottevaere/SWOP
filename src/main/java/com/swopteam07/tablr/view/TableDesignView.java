package com.swopteam07.tablr.view;

import com.swopteam07.tablr.UI.*;
import com.swopteam07.tablr.UI.event.TableChangeEvent;
import com.swopteam07.tablr.controller.Controller;


/**
 * View to edit the characteristics of a column.
 *
 * @author Sebastiaan Henau
 */
public class TableDesignView extends View {

    /**
     * The ID of the table is edited in this view.
     */
    private final int tableId;

    /**
     * The table UI component used to display information about the table (Domain model) that is being edited.
     */
    private Table table;

    /**
     * The textfield containing the name of the column that is being edited.
     */
    private TextField columnName = null;

    /**
     * The textfield containing the default value of the column that is being edited.
     */
    private TextField columnDefault = null;

    /**
     * The id of the column that is being edited.
     */
    private int columnId = -1;

    /**
     * @return The id of the table that this view allows the user to edit.
     */
    public Integer getTableId() {
        return tableId;
    }

    /**
     * Create a new table design view.
     *
     * @param controller Reference to the controller.
     * @param tableId    The id of the table that this view is used for.
     */
    public TableDesignView(Controller controller, int tableId) {
        super(new WindowPanel(200, 20, 400, 800, null), controller);
        String[] headers = {"Name", "Type", "Blanks allowed", "Default values"};
        table = new Table(30, 50, 200, 30, headers, root);
        addListeners();
        root.addChild(table);
        root.enableVerticalScrollbar();
        root.enableHorizontalScrollbar();
        this.tableId = tableId;
        updateTitle();
        table.addCellWidthsHandler(evt ->
                controller.updateTableDesignCellWidths(evt.getNewValue().stream().mapToInt(Integer::intValue).toArray()));
    }

    /**
     * Update the title of this view.
     */
    public void updateTitle() {
		root.setTitle("TableId Design: " + controller.getTableName(tableId));
    }

    /**
     * Add a listener that responds to changes in the table table.
     */
    private void addListeners() {
        table.addTableChangeHandler(event -> {
            Integer cellId = event.getCellId();

            if (cellId == null) {       // A row was deleted.
                controller.deleteColumn(tableId, event.getRowId());
            } else if (cellId == 0)     // The name of the column was updated.
                updateColumnName(event);
            else if (cellId == 1)       // The type of the column was updated.
                updateColumnType(event);
            else if (cellId == 2)       // The allowsBlank value was changed.
                updateBlanks(event);
            else if (cellId == 3)       // The default value was changed.
                updateDefault(event);
        });
    }

    /**
     * Check if the name that the user types is valid.
     *
     * @param evt Event containing information about which cell in which row was updated.
     */
    private void updateColumnName(TableChangeEvent evt) {
        columnName = (TextField) evt.getChangedComponent();
        String name = columnName.getText();
        validState = controller.isValidColumnName(tableId, evt.getRowId(), name);
        columnId = evt.getRowId();
        blockUnblockInteraction(evt.getRowId(), evt.getCellId(), table);
    }

    /**
     * Update the name of a column.
     */
    private void writeColumnName() {
        if (columnName != null && validState)
            controller.updateColumnName(tableId, columnId, columnName.getText());
        columnName = null;
    }

    /**
     * Update the type of the column.
     *
     * @param evt Event containing information about which cell in which row was updated.
     */
    private void updateColumnType(TableChangeEvent evt) {
        String type = ((OptionLabel) evt.getChangedComponent()).getText();
        validState = controller.isValidColumnType(tableId, evt.getRowId(), type);
        if (validState) controller.updateColumnType(tableId, evt.getRowId(), type);
        blockUnblockInteraction(evt.getRowId(), evt.getCellId(), table);
    }

    /**
     * Change whether or not a column allows blanks.
     *
     * @param evt Event containing information about which cell in which row was updated.
     */
    private void updateBlanks(TableChangeEvent evt) {
        boolean blanksAllowed = ((Checkbox) table.getCell(evt.getRowId(), evt.getCellId())).getValue();
        validState = controller.isValidBlanksAllowed(tableId,evt.getRowId(),blanksAllowed);
        if(validState && controller.getBlanksAllowed(tableId, evt.getRowId())!=blanksAllowed)
        	controller.updateBlanksAllowed(tableId, evt.getRowId(), blanksAllowed);
        blockUnblockInteraction(evt.getRowId(), evt.getCellId(), table);
    }

    /**
     * Update the default value of a column.
     *
     * @param evt Event containing information about which cell in which row was updated.
     */
    private void updateDefault(TableChangeEvent evt) {
        columnId = evt.getRowId();
        DrawableComponent cell = table.getCell(columnId, evt.getCellId());

        String newValue;
        if (cell instanceof TextField) {
            columnDefault = ((TextField) cell);
            String text = columnDefault.getText();
            newValue = text.equals("|") || text.isEmpty() ? null : text;
            validState = controller.isValidDefaultValue(tableId, columnId, newValue);
        } else {
            newValue = ((Checkbox) cell).getValue() == null ? null : ((Checkbox) cell).getValue().toString();
            validState = controller.isValidDefaultValue(tableId, columnId, newValue);
            if(validState) {
            	controller.updateDefaultValue(tableId, evt.getRowId(), newValue);
            }
        }
        blockUnblockInteraction(columnId, evt.getCellId(), table);
    }

    /**
     * Update the default value of the column currently being edited.
     */
    private void writeDefault() {
        if (columnDefault != null && validState) {
            String text = columnDefault.getText();
            String newValue = text.equals("|") || text.isEmpty() ? null : text;
            controller.updateDefaultValue(tableId, columnId, newValue);
            columnDefault = null;
        }
    }

    /**
     * Build a table showing information about the columns in a specific table.
     *
     * @param columnNames   The names of the columns in this table.
     * @param types         The types of the columns in this table.
     * @param dataTypes     All the possible types a column can have.
     * @param allowBlanks   Booleans indicating whether or not a column allows blank values.
     * @param defaultValues The default value for each column.
     * @param ids           The ids for each column.
     * @throws IllegalArgumentException Thrown when there is a difference in the length of the different arrays.
     *                                  Not applicable to the dataTypes array as entries don't correspond to specific columns.
     */
    public void showDataForTable(String[] columnNames, String[] types, String[] dataTypes, boolean[] allowBlanks, Object[] defaultValues, int[] ids) {

        columnName = null;
        columnDefault = null;

        if (columnNames.length != types.length && types.length != allowBlanks.length &&
                allowBlanks.length != defaultValues.length && defaultValues.length != ids.length)
            throw new IllegalArgumentException("All array arguments need to have the same length");

        table.emptyRows();

        for (int i = 0; i < columnNames.length; i++) {
            DrawableComponent[] cells = new DrawableComponent[4]; // length == # of columns;

            cells[0] = new TextField(columnNames[i], table);
            cells[1] = new OptionLabel(types[i], dataTypes, table);
            cells[2] = new Checkbox(allowBlanks[i], table);

            if (types[i].equals("BOOLEAN"))
                cells[3] = new Checkbox((Boolean) defaultValues[i], allowBlanks[i], null);
            else
                cells[3] = new TextField(defaultValues[i] == null ? "" : defaultValues[i].toString(), null);
            table.addRow(cells, ids[i]);
        }
        
        validState=true;
    }

    /**
     * Set new widths for the tables contained in this view.
     *
     * @param widths The new widths of the columns of this table.
     */
    @Override
    public void updateCellWidths(int[] widths) {
        table.setColumnWidths(widths);
    }

    /**
     * React to the user selecting the a component.
     * Pushes changes to the controller.
     */
    @Override
    public void selectedDifferentComponent() {
        writeColumnName();
        writeDefault();
    }

    /**
     * React to the user pressing enter.
     * Pushes changes to the controller.
     */
    @Override
    public void enterPressed() {
        writeColumnName();
        writeDefault();
    }

    /**
     * Deal with a user clicking underneath the table.
     *
     * @param clickCount The number of times the user clicked beneath view.
     */
    @Override
    public void clickedBeneathView(int y, int clickCount) {
        if (!hasValidState()) return;
        if (y > table.getDrawY() + table.getHeight() && table.contains(table.getDrawX(), y))
            if (clickCount == 2)
                controller.addColumnToTable(tableId);
    }

    /**
     * React to a user pressing the escape key.
     */
    @Override
    public void escapePressed() {
        if (columnName != null) {
            columnName.setText(controller.getColumnName(tableId, columnId));
            columnName = null;
        }

        if (columnDefault != null) {
            Object defaultValue = controller.getDefaultValue(tableId, columnId);
            columnDefault.setText(defaultValue == null ? "" : defaultValue.toString());
            columnDefault = null;
        }
    }

    /**
     * Reacts to the control + enter key combination: navigate to table row mode.
     */
    @Override
    public void ctrlEnterPressed() {
        controller.tableRowView(tableId);
    }
}
