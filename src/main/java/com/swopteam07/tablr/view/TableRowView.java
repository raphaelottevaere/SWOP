package com.swopteam07.tablr.view;

import com.swopteam07.tablr.UI.*;
import com.swopteam07.tablr.controller.Controller;
import com.swopteam07.tablr.model.cell.DataCell;

/**
 * The view class for the TableRowView
 *
 * @author Raphaël Ottevaere
 */
public class TableRowView extends View {

	/**
	 * The id of the table being edited.
	 */
	private final int tableId;

	/**
	 * The UI table used to display the information.
	 */
	private Table table;

	/**
	 * The id of the cell currently being edited.
	 */
	private int cellID = -1;

	/**
	 * The id of the row currently being edited.
	 */
	private int rowID = -1;

	/**
	 * The cell currently being edited.
	 */
	private DrawableComponent currentCell;


    /**
     * Creates a tableRowView
     *
     * @param controller A reference to the controller of this application.
     * @param tableId    The id of the table being edited.
     */
    public TableRowView(Controller controller, int tableId) {
        super(new WindowPanel(200, 20, 400, 400, null), controller);
        this.tableId = tableId;
        table = new Table(30, 50, 200, 30, new String[0], root);
        root.addChild(table);
        root.enableVerticalScrollbar();
        root.enableHorizontalScrollbar();
        updateTitle();
        addPropertyListener();
        table.addCellWidthsHandler(evt ->
                controller.updateTableRowViewCellWidths(evt.getNewValue().stream().mapToInt(Integer::intValue).toArray()));
    }


	/**
	 * Update the title of this view.
	 */
	public void updateTitle() {
		root.setTitle("Table Row: " + controller.getTableName(tableId));
	}

	/**
	 * Makes the view from scratch Gets the data from the Controller for the Columns
	 * and row for the already set tableID
	 */
	public void make() {
		int[] rows = controller.getAllRowsID(tableId);
		int[] columnIds = controller.getColumnIds(tableId);
		String[] column = controller.getAllColumnsHeader(tableId);
		table.empty();
		table.setHeaders(column, columnIds);

		for (int r : rows)
			table.addRow(convertCellsToRowUi(controller.getDataCellsFromRow(tableId, r)),r);
	}

	/**
	 * Adds a property listener to the Table Which deletes a row or edits a row
	 * depending on the users actions
	 */
	private void addPropertyListener() {
		table.addTableChangeHandler(event -> {
			if (event.getCellId() == null) {
				deleteRow(event.getRowId());
				rowID = -1;
				cellID = -1;
				currentCell = null;
			} else {
				currentCell = event.getChangedComponent();
				rowID = event.getRowId();
				cellID = event.getCellId();
				changedCell(rowID, cellID, currentCell);
			}
		});
	}

	/**
	 * Add a new row to the table for this view.
	 *
	 * @param y          The y coordinate of the point where the user clicked.
	 * @param clickCount The number of times the user clicked beneath view.
	 */
	@Override
	public void clickedBeneathView(int y, int clickCount) {
		if (!hasValidState())
			return;
		if (y > table.getDrawY() + table.getHeight() && table.contains(table.getDrawX(), y)) {
			if (clickCount == 2) {
				controller.addRow(tableId);
			}
		}
	}

	/**
	 * React to the user pressing escape. The value of the cell that is being edited
	 * is reset to the value it had before editing began.
	 */
	@Override
	public void escapePressed() {
		if (hasValidState() && !root.childIsSelected())
			controller.tablesView();
	}

	/**
	 * React to the user pressing enter. If a cell is being edited, it's value is
	 * pushed to the controller.
	 */
	@Override
	public void enterPressed() {
		pushCellChange();
	}

    /**
     * Converts an array DataCell containing a DataType (defined Enum) to an array
     * of RowUI elements (CheckBox, TextField, ...)
     *
     * @param dc DataCell array
     * @return DrawableComponent[]
     */
    private DrawableComponent[] convertCellsToRowUi(DataCell[] dc) {
        DrawableComponent[] rowUi = new DrawableComponent[dc.length];
        CellFactory cf = new CellFactory();
        for (int i = 0; i < dc.length; ++i)
            rowUi[i] = cf.dataCellToUI(dc[i], table);
        return rowUi;
    }

	/**
	 * Deletes a row from the model
	 *
	 * @param rowId The id of the row to delete.
	 */
	private void deleteRow(int rowId) {
		controller.deleteRow(tableId, rowId);
	}

	/**
	 * Fetches the value from the changed cell first validates it using the
	 * controller If (newValue.isValid()) Controller.changeValue() else
	 * UIElement.SetInvalid()
	 *
	 * @param rowId  RowId where the change occurred.
	 * @param cellId cellId where the change occurred.
	 * @param cell   DrawableComponent where the change occurred.
	 */
	private void changedCell(int rowId, int cellId, DrawableComponent cell) {
		Object value;
		try {
			value = ((TextField) cell).getText();
			validState = controller.checkValidValue(tableId, cellId, value);
			blockUnblockInteraction(rowId, cellId, table);
		} catch (ClassCastException e) {
			try {
				value = ((Checkbox) cell).getValue();
				controller.setValue(tableId, rowId, cellId, value);
			} catch (ClassCastException e2) {
				throw new ClassCastException("can't cast component to Either an textField or a checkBox");
			}
		}
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
     * Pushes a field value to the database
     */
    private void pushCellChange() {
    	try {
    		if (currentCell != null && currentCell.isValid()) {
            if (currentCell instanceof TextField) {
                String newValue = ((TextField) currentCell).getText();
                controller.setValue(tableId, rowID, cellID, newValue.isEmpty() ? null : newValue);
                currentCell = null;
            } else if (currentCell instanceof Checkbox) {
                controller.setValue(tableId, rowID, cellID, ((Checkbox) currentCell).getValue());
            }
            rowID = -1;
            cellID = -1;
            currentCell = null;
        	}
    	}
    	catch (IllegalArgumentException exc) {
    		validState=false;
    	}
    }

	/**
	 * Get the id of the table this view shows the rows for.
	 *
	 * @return The if of the table.
	 */
	public Integer getTableId() {
		return tableId;
	}

	/**
	 * React to the user selecting a component in this view. Pushes changes to the
	 * controller.
	 */
	@Override
	public void selectedDifferentComponent() {
		pushCellChange();
	}

	/**
	 * Reacts to the control + enter key combination: navigate to table design mode.
	 */
	@Override
	public void ctrlEnterPressed() {
		controller.tableDesignView(tableId);
	}
}
