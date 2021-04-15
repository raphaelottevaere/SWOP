package com.swopteam07.tablr.view;

import com.swopteam07.tablr.UI.*;
import com.swopteam07.tablr.UI.handlers.MouseClickListener;
import com.swopteam07.tablr.UI.event.TableChangeEvent;
import com.swopteam07.tablr.controller.Controller;

/**
 * View to edit the tables.
 *
 * @author Serhat Erdogan
 */
public class TablesView extends View {

    /**
     * The table used to display the list of tables.
     */
    private Table table;

    /**
     * The id of the table whose name is being edited.
     */
    private int editedId = -1;

    /**
     * The textfield for the table whose name is being edited.
     */
    private TextField nameField = null;

    /**
     * The row that is currently selected in the table.
     */
    private int selectedRow = -1;
    
    /**
     * The column that is currently selected in the table.
     */
    private int columnId = -1;

    /**
     * Create a new table view. Used for editing the tables in the database.
     * @param controller Reference to the controller.
     */
    public TablesView(Controller controller) {
        super(new WindowPanel(200, 20, 400, 400, null), controller);
        String[] headers = {"Tables", "TableQueries"};
        table = new Table(30, 50, 200, 30, headers, root);
        table.addCellWidthsHandler(evt ->
                controller.updateTablesViewCellWidths(evt.getNewValue().stream().mapToInt(Integer::intValue).toArray()));
        addListener();
        root.addChild(table);
        root.enableVerticalScrollbar();
        root.enableHorizontalScrollbar();
        root.setTitle("Tables");
    }

    /**
     * Build the view.
     *
     * @param names The names of the tables.
     * @param ids   The ids corresponding to the names.
     */
    public void showDataForTable(String[] names, int[] ids, String[] queries) {
        nameField = null;
        table.emptyRows();
        for (int i = 0; i < names.length; i++) {
            TextField name = new TextField(names[i], table);

            // Allows for navigation to the table design view.
            name.addMouseClickListener(new MouseClickListener(ids[i]) {
                @Override
                public void handle(int clickCount) {
                    if (hasValidState() && clickCount == 2)
                        controller.tableRowClicked(getTableId());
                }
            });

            TextField querie = new TextField(queries[i], table);
            DrawableComponent[] cells = {name,querie};
            table.addRow(cells, ids[i]);
        }
    }

    /**
     * Attach a listener to the table that reacts when a row in the table changes.
     */
    private void addListener() {
        table.addTableChangeHandler(event -> {
            Integer cellId = event.getCellId();
            if (cellId == null) {
                controller.deleteTable(event.getRowId());
                selectedRow = -1;
                columnId=-1;
            }

            else if (cellId == 0)
                updateTableName(event);
            else if (cellId == 1)
                updateQuery(event);
        });
        table.addTableRowSelectedHandler(evt -> selectedRow = evt.getRowId());
    }

    private void updateQuery(TableChangeEvent event) {
    	nameField = (TextField) event.getChangedComponent();
        String name = nameField.getText();
        validState = controller.isValidQuery(event.getRowId(), name);
        blockUnblockInteraction(event.getRowId(), event.getCellId(), table);

        // Save the values so they can be updated if the user chooses to do so.
        editedId = event.getRowId();
        columnId = event.getCellId();
	}

	/**
     * Extract the updated name from the changed table cell.
     *
     * @param event Event containing information about the changed rows.
     */
    private void updateTableName(TableChangeEvent event) {
        nameField = (TextField) event.getChangedComponent();

        String name = nameField.getText();
        validState = controller.isValidTableName(event.getRowId(), name);
        blockUnblockInteraction(event.getRowId(), event.getCellId(), table);

        // Save the values so they can be updated if the user chooses to do so.
        editedId = event.getRowId();
        columnId = event.getCellId();
    }

    /**
     * Send the updated name to controller.
     */
    private void updateFields() {
        if (!validState || editedId == -1 || nameField == null) return;
        if(columnId==0) {
        	controller.editTableName(editedId, nameField.getText());}
        else if(columnId == 1) {
        	controller.editQuery(editedId, nameField.getText());
        }
        editedId = -1;
        columnId = -1;
        nameField = null;
    }

    /**
     * React to the user pressing the escape button.
     * Resets the value that is being edited to the value it had before the editing.
     */
    @Override
    public void escapePressed() {
        if (nameField != null && editedId != -1) {
        	if(columnId==0) {
        		 nameField.setText(controller.getTableName(editedId));}
            else if(columnId == 1) {
            	nameField.setText(controller.getQuery(editedId));}
            nameField = null;
            editedId = -1;
            columnId = -1;
        }
    }

    /**
     * React to the user pressing  ctrl + F
     * Open Form view of current selected table
     */
    @Override
    public void ctrlFPressed() {
        if (selectedRow != -1) controller.formView(selectedRow);
    }

    /**
     * Update the widths of the columns in the tables of this view.
     * @param widths The new widths of the columns of this table.
     */
    @Override
    public void updateCellWidths(int[] widths) {
        table.setColumnWidths(widths);
    }

    /**
     * React the the user pressing the enter key, this updates the table name.
     */
    @Override
    public void enterPressed() {
        updateFields();
    }

    /**
     * React to the user selecting a component in the view.
     */
    @Override
    public void selectedDifferentComponent() {
        updateFields();
    }

    /**
     * Add a new row to the table.
     * @param y          The y coordinate of the point where the user clicked.
     * @param clickCount The number of times the user clicked beneath view.
     */
    @Override
    public void clickedBeneathView(int y, int clickCount) {
        if (!hasValidState()) return;
        if (y > table.getDrawY() + table.getHeight() && table.contains(table.getDrawX(), y))
            if (clickCount == 2)
                controller.addTable();
    }

    /**
     * @return 	null because the view doesn't allow a table to be ediitted
     */
	@Override
	public Integer getTableId() {
		return null;
	}


}
