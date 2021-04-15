package com.swopteam07.tablr.controller;

import com.swopteam07.tablr.CanvasWindow.CanvasWindow;
import com.swopteam07.tablr.UI.handlers.WindowPanelClosedHandler;
import com.swopteam07.tablr.command.*;
import com.swopteam07.tablr.model.Database;
import com.swopteam07.tablr.model.Table;
import com.swopteam07.tablr.model.cell.DataCell;
import com.swopteam07.tablr.model.column.Column;
import com.swopteam07.tablr.view.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * Singleton Takes care of communication between the views and the database.
 */
public class Controller extends CanvasWindow {

	/**
	 * Database: provides access to the model.
	 */
	private Database database = Database.getInstance();

	private final static AtomicReference<Controller> uniqueInstance = new AtomicReference<>();

	/**
	 * The view that is currently being shown to the user.
	 */
	private View activeView;

	/**
	 * List of the views that are currently shown in the order that they were last
	 * activate.
	 */
	private ArrayList<View> views = new ArrayList<>();

	/**
	 * Is true if the previous keypress was control.
	 */
	private boolean ctrlPressed;

	/**
	 * Is true if the previous keypress was shift.
	 */
	private boolean shiftPressed;

	/**
	 * Indicates whether or not the previous click event that was registered was a
	 * drag event.
	 */
	private boolean previousClickWasDrag = false;

	/**
	 * Handler that reacts to a user clicking the active view.
	 */
	private WindowPanelClosedHandler closeHandler = event -> removeActiveView();

	/**
	 * The widths used for columns of the table view tables.
	 */
	private int[] tablesViewWidths;

	/**
	 * The widths used for the columns of the table design view tables.
	 */
	private int[] tableDesignViewWidths;

	/**
	 * The widths used for the columns of the table row view tables.
	 */
	private int[] tableRowViewWidths;

	/**
	 * The stack of last executed commands for undo function.
	 */
	private Stack<ICommand> undo = new Stack<>();

	/**
	 * The stack of last executed commands for redo function.
	 */
	private Stack<ICommand> redo = new Stack<>();

	/**
	 * Validator if redos are allowed at the current position
	 */
	private boolean redoAllowed = false;

	/**
	 * Create a new controller and ensure that the table view is loaded.
	 */
	private Controller() {
		super("Tablr", 1200, 600);
		tablesView();
		java.awt.EventQueue.invokeLater(this::show);
	}

	/**
	 * @return A reference to this singleton controller.
	 */
	public synchronized static Controller getInstance() {
		if (uniqueInstance.get() == null)
			uniqueInstance.set(new Controller());

		return uniqueInstance.get();
	}

	/**
	 * @apiNote FOR USE IN TESTING ONLY
	 */
	public synchronized static void removeInstanceReference() {
		uniqueInstance.set(null);
	}

	/**
	 * @return The names of the tables in the domain model.
	 */
	public List<String> getTablesName() {
		return database.getTablesNames();
	}

	/**
	 * @return A list of all the views managed by this controller that are of the
	 *         tables view type.
	 */
	private List<TablesView> getTablesViews() {
		List<TablesView> filtered = views.stream().filter(v -> v instanceof TablesView).map(TablesView.class::cast)
				.collect(Collectors.toList());
		if (activeView instanceof TablesView)
			filtered.add((TablesView) activeView);
		return filtered;
	}

    /**
     * @return A list of all the views managed by this controller that are of the
     * form view type.
     */
    private List<FormView> getFormViews() {
        List<FormView> filtered = views.stream().filter(v -> v instanceof FormView).map(FormView.class::cast)
                .collect(Collectors.toList());
        if (activeView instanceof FormView)
            filtered.add((FormView) activeView);
        return filtered;
    }

	/**
	 * @return A list of all the views managed by this controller that are of the
	 *         table design view type.
	 */
	private List<TableDesignView> getTablesDesignViews() {
		List<TableDesignView> filtered = views.stream().filter(v -> v instanceof TableDesignView)
				.map(TableDesignView.class::cast)
                .collect(Collectors.toList());
		if (activeView instanceof TableDesignView)
			filtered.add((TableDesignView) activeView);
		return filtered;
	}

	/**
	 * @return A list of all the views managed by this controller that are of the
	 *         table row view type.
	 */
	private List<TableRowView> getTablesRowViews() {
		List<TableRowView> filtered = views.stream().filter(v -> v instanceof TableRowView)
                .map(TableRowView.class::cast)
                .collect(Collectors.toList());
		if (activeView instanceof TableRowView)
			filtered.add((TableRowView) activeView);
		return filtered;
	}

	/**
	 * Show the view in which CRUD operation can be performed on the tables in the
	 * model.
	 */
	public void tablesView() {
		TablesView view = new TablesView(this);
		if (tablesViewWidths != null)
			view.updateCellWidths(tablesViewWidths);
		populateTablesView(view);
		setActiveView(view);
	}

	/**
	 * Fill in the data for a table design view.
	 *
	 * @param view The view for which to set the data.
	 */
	private void populateTablesView(TablesView view) {
		Table[] tables = database.getTables();

		String[] names = new String[tables.length];
		int[] ids = new int[tables.length];
		String[] queries = new String[tables.length];
		
		for (int i = 0; i < tables.length; i++) {

			names[i] = tables[i].getName();
			ids[i] = tables[i].getId();
			queries[i] = tables[i].getQuery();
		}

		view.showDataForTable(names, ids, queries);
	}

	/**
	 * Check if the specified name is valid for the specified table.
	 *
	 * @param id      The id of the table to check for.
	 * @param newName The new name.
	 * @return True if the name is valid, false otherwise.
	 */
	public boolean isValidTableName(int id, String newName) {
		return database.isValidTableName(id, newName);
	}

	/**
	 * Handles a user clicking on a table row and determines how which view to open.
	 *
	 * @param tableId The id of the table that was clicked.
	 */
	public void tableRowClicked(int tableId) {
		if (database.getNbColumns(tableId) > 0)
			tableRowView(tableId);
		else
			tableDesignView(tableId);
	}

	/**
	 * Create a new table design view and set it as the new active view.
	 *
	 * @param tableId The id of the table for which the view shows data.
	 */
	public void tableDesignView(int tableId) {
		TableDesignView view = new TableDesignView(this, tableId);
		if (tableDesignViewWidths != null)
			view.updateCellWidths(tableDesignViewWidths);
		populateTableDesignView(view);
		setActiveView(view);
	}

	/**
	 * Show the table design view in which characteristics of the column in a
	 * specific table can be edited.
	 *
	 * @param view The view that is to be populated.
	 */
	private void populateTableDesignView(TableDesignView view) {
		int tableId = view.getTableId();
		Column[] columns = database.getColumns(tableId);

		String[] names = new String[columns.length];
		String[] type = new String[columns.length];
		boolean[] allowBlanks = new boolean[columns.length];
		Object[] defaultValues = new Object[columns.length];
		int[] ids = new int[columns.length];

		for (int i = 0; i < columns.length; i++) {
			names[i] = columns[i].getName();
			type[i] = columns[i].getType();
			allowBlanks[i] = columns[i].allowsBlank();
			defaultValues[i] = columns[i].getDefaultValue();
			ids[i] = columns[i].getId();
		}

		view.showDataForTable(names, type, getDataTypes(), allowBlanks, defaultValues, ids);
	}

    /**
     * Create a new form design view and set it as the new active view.
     *
     * @param tableId The id of the table for which the view shows data.
     */
    public void formView(int tableId) {
        FormView view = new FormView(this, tableId, getAllColumnsHeader(tableId), getColumnIds(tableId));
        populateFormView(view);
        setActiveView(view);
    }

    public DataCell getDataForCell(int tableId, int rowId, int columnId) {
        return database.getDataForCell(tableId, rowId, columnId);
    }

    /**
     * Show the Form view in which characteristics of the row in a specific table can be edited.
     *
     * @param view The view that is to be populated.
     */
    private void populateFormView(FormView view) {
        view.showData();
    }

    /**
     * Get the default value for the specified column and table.
     *
     * @param tableId  The id of the table for which to get the column.
     * @param columnId The column whose default value to get.
     * @return The default value.
     */
    public Object getDefaultValue(int tableId, int columnId) {
        return database.getColumn(tableId, columnId).getDefaultValue();
    }

	/**
	 * Update all the table design views that show information about a certain
	 * table.
	 *
	 * @param tableId The id of the table for which to update the design views.
	 */
	private void populateAllTableDesignViewsForTable(int tableId) {
		getTablesDesignViews().stream().filter(t -> t.getTableId() == tableId).forEach(this::populateTableDesignView);
	}

	/**
	 * Update all the table views
	 */
	private void populateAllTableViews() {
		getTablesViews().forEach(this::populateTablesView);
	}

	/**
	 * Update all the table row views that show information about a certain table.
	 *
	 * @param tableId The id of the table for which to update the row views.
	 */
	private void populateAllTableRowViewsForTable(int tableId) {
		getTablesRowViews().stream().filter(t -> t.getTableId() == tableId).forEach(TableRowView::make);
	}

	/**
	 * Check if a name if valid for a column in a table.
	 *
	 * @param tableId  The id of the table that contains the column to update.
	 * @param columnId The id of the column to update.
	 * @param name     The new name of the column.
	 * @return True if the name if valid, false otherwise.
	 */
	public boolean isValidColumnName(int tableId, int columnId, String name) {
		return database.isValidColumnName(tableId, columnId, name);
	}

    /**
     * Update all fields form views that show information.
     *
     * @param tableId The id of the table for which to update the form views.
     */
    private void populateAllFormViewViewsForTable(int tableId) {
        getFormViews().stream().filter(t -> t.getTableId() == tableId).forEach(FormView::showData);
    }

    /**
     * Update a column in the database.
     *
     * @param tableId  The id of the table to update.
     * @param columnId The id of the column to update.
     * @param name     The new name of the column.
     */
    public void updateColumnName(int tableId, int columnId, String name) {
        executeCommand(new ColumnNameCommand(name, tableId, columnId));
        redoAllowed = false;
    }

	/**
	 * Get a list of string representations of the data types supported in the
	 * model.
	 *
	 * @return The data types.
	 */
	public String[] getDataTypes() {
		return database.getDataTypes();
    }

    /**
     * Add a new column to the table.
     *
     * @param tableId The id of the table for which a column needs to be added.
     */
    public void addColumnToTable(int tableId) {
        executeCommand(new AddColumnCommand(tableId));
        redoAllowed = false;
    }

    /**
     * Update the type of a column in the model.
     *
     * @param tableId  The id of the table for which the column needs to be updated.
     * @param columnId The id of the column which needs to be updated.
     * @param type     The new type of the column
     */
    public void updateColumnType(int tableId, int columnId, String type) {
        executeCommand(new UpdateColumnTypeCommand(tableId, columnId, type));
        redoAllowed = false;
	}

    /**
     * Gets all column names from the given table
     *
     * @param tableId The table for which to retrieve the names.
     * @return All Column headers from the table.
     */
    public String[] getAllColumnsHeader(int tableId) {
        return database.getAllColumnsHeader(tableId);
    }

	/**
	 * Gets all RowIDs from the given table
	 *
	 * @param tableId The table for which to get the ids.
	 * @return AllRow IDs (int[]) from the given table
	 */
	public int[] getAllRowsID(int tableId) {
		return database.getAllRowsID(tableId);
	}

	/**
	 * Get the ids of all the columns in a given table.
	 *
	 * @param tableId The id of the table for which to get the column ids.
	 * @return The ids of the columns in the specified table.
	 */
	public int[] getColumnIds(int tableId) {
		return database.getColumnIds(tableId);
	}

	/**
	 * Check for the given cell in the given row in the given tableID if the given
	 * value is valid
	 *
	 * @param tableId  The id of the table for which to check.
	 * @param columnId The id of the cell for which to check.
	 * @param value    The value for which to check.
	 * @return True if the value is valid for the specified column of the specified
	 *         table.
	 */
	public boolean checkValidValue(int tableId, int columnId, Object value) {
		return database.checkValidValue(tableId, columnId, value);
    }

    /**
     * Deletes the given row from the given table.
     *
     * @param tableId The id of the table from which to delete the row.
     * @param rowId   The id of the row which should be deleted.
     */
    public void deleteRow(int tableId, int rowId) {
		if(!database.isComputed(tableId)) {
			executeCommand(new DeleteRowCommand(tableId, rowId));
			redoAllowed = false;
		}
    }

    /**
     * Sets the value for the given cell in the given row in the given tableID to
     * the given value if allowed
     *
     * @param tableId The id of the table in which the cell is located.
     * @param rowId   The id of the row in which the cell is located.
     * @param cellId  The id of the cell for which to set a new value.
     * @param value   The new value.
     */
    public void setValue(int tableId, int rowId, int cellId, Object value) {
        executeCommand(new CellChangeCommand(value, tableId, rowId, cellId));
		redoAllowed = false;
	}

    /**
     * Get the name of a table.
     *
     * @param id The id of the table whose name to get.
     * @return The name of the specified table.
     */
    public String getTableName(int id) {
        return database.getTableName(id);
    }

	/**
	 * gets DataCells from the database for the given tableID and RowID
	 *
	 * @param tableId The id of the table from which to get a DataCell.
	 * @param rowID   The id of the row for which to get the DataCells.
	 * @return DataCell[] containing pure DataCells.
	 */
	public DataCell[] getDataCellsFromRow(int tableId, int rowID) {
		return database.getDataCellsFromRow(tableId, rowID);
	}

	/**
	 * Gets the state of blanksAllowed from
	 *
	 * @param tableId The id of the table for which to check if blanks are allowed.
	 * @param columnId The id of the column for which to check if blanks are allowed.
	 * @return true if blanksAllowed == column.blankAllowed
	 */
	public boolean getBlanksAllowed(int tableId, Integer columnId) {
		return database.checkBlanksAllowed(tableId, columnId);
	}

	/**
	 * Checks if the specified column can be changed to the specified type.
	 *
	 * @param tableId The id of the table for which to check.
	 * @param columnId The id of the column for which to check.
	 * @param type The type for which to check.
	 * @return True if the column can be changed to the specified type.
	 */
    public boolean isValidColumnType(int tableId, int columnId, String type) {
		return database.isValidColumnType(tableId, columnId, type);
	}

	/**
	 * Checks is the new blanksAllowed boolean is valid for the given params
	 *
	 * @param tableId       tableId
	 * @param colId         columnId
	 * @param blanksAllowed Newblanks Allowed value
	 * @return isValid(blanksAllowed)
	 */
	public boolean isValidBlanksAllowed(int tableId, Integer colId, boolean blanksAllowed) {
		return database.isValidBlanksAllowed(tableId, colId, blanksAllowed);
	}

	/**
	 * Update a columns allows blank value.
	 *
	 * @param tableId       The id of the table for which to update.
	 * @param columnId      The id of the column in the table to update.
	 * @param blanksAllowed The new value for the allows blank value.
	 */
	public void updateBlanksAllowed(int tableId, Integer columnId, boolean blanksAllowed) {
		executeCommand(new BlankAllowedCommand(tableId, columnId, blanksAllowed));
		redoAllowed = false;
	}

	/**
	 * Delete a table from the model.
	 *
	 * @param id The id of the table to be deleted.
	 */
	public void deleteTable(int id) {
		executeCommand(new DeleteTableCommand(id));
		redoAllowed = false;
	}

	/**
	 * Adds a row to the table.
	 *
	 * @param tableId The id of the table to which a new row should be added.
	 */
	public void addRow(int tableId) {
		if(!database.isComputed(tableId)) {
			executeCommand(new AddRowCommand(tableId));
			redoAllowed = false;
		}
	}

	/**
	 * Add a new table to the domain model. Then push these changes to the view.
	 */
	public void addTable() {
		executeCommand(new AddTableCommand());
		redoAllowed = false;
	}

	/**
	 * Edit the name of a table in the model.
	 *
	 * @param id      The id of the table to be updated.
	 * @param newName The new name of the table.
	 * @throws IllegalArgumentException Thrown when the new name isn't valid. | !
	 *                                  isValidTableName(id, newName)
	 */
	public void editTableName(int id, String newName) throws IllegalArgumentException {
		if (!isValidTableName(id, newName))
			throw new IllegalArgumentException("The specified name is not a valid name!");

		executeCommand(new TableNameCommand(newName, id));
		redoAllowed = false;
	}

	/**
	 * Delete a column in the model.
	 *
	 * @param tableId  The id of the table from which a column needs to be deleted.
	 * @param columnId The id of the column to be deleted.
	 */
	public void deleteColumn(int tableId, int columnId) {
		executeCommand(new DeleteColumnCommand(tableId, columnId));
		redoAllowed = false;
	}

	/**
	 * Update the default value for a column.
	 *
	 * @param tableId      The id of the table for which to update.
	 * @param columnId     The id of the column in the table to update.
	 * @param defaultValue The new default value of the column.
	 */
	public void updateDefaultValue(int tableId, int columnId, String defaultValue) {
		executeCommand(new DefaultColumnValueCommand(tableId, columnId, defaultValue));
		redoAllowed = false;
	}

	/**
	 * Check if the specified value is valid for the specified column in the
	 * specified table.
	 *
	 * @param tableId  The table to check for.
	 * @param columnId The id of the column to check for.
	 * @param newValue The value to check for.
	 * @return True is the value is valid, false otherwise.
	 */
	public boolean isValidDefaultValue(int tableId, int columnId, String newValue) {
		return database.isValidDefaultValue(tableId, columnId, newValue);
	}

	/**
	 * Show a view that allows for CRUD operation on the rows of a table.
	 *
	 * @param tableId The id of the table for which to show the view.
	 */
	public void tableRowView(int tableId) {
		TableRowView view = new TableRowView(this, tableId);
		view.make();
		if (tableRowViewWidths != null)
			view.updateCellWidths(tableRowViewWidths);
		setActiveView(view);
	}

	/**
	 * @apiNote ONLY USED IN TESTING
	 * @return The number of tables in the database.
	 */
	public int getTableCount() {
		return this.database.getTableCount();
	}

	/**
	 * @apiNote FOR TESTING ONLY
	 * @return Reference to the database.
	 */
	public Database getDatabase() {
		return database;
	}

	/**
	 * Set the new active view for the application.
	 *
	 * @param activeView The view to be shown to the user.
	 */
	public void setActiveView(View activeView) {
		if (activeView == null)
			throw new IllegalArgumentException("The view for this component cannot be null");

		// If the activeView is not null, the currently active view needs to be saved on
		// the stack containing all the
		// views before a new active view can be set.
		if (this.activeView != null) {
			this.activeView.getRoot().unselect();
			views.add(this.activeView);
		}

		// If the active view is already contained within the list of views it needs to
		// be removed since we don't want
		// to draw the same view twice.
		views.remove(activeView);
		this.activeView = activeView;
		this.activeView.getRoot().setSelected(true);

		// Add handlers that react when the view is closed. First remove any existing
		// handlers so as not to react to
		// the same event more than once.
		this.activeView.getRoot().removeWindowPanelCloseHandler(closeHandler);
		this.activeView.getRoot().addWindowPanelCloseHandler(closeHandler);

		repaint();
	}

	/**
	 * Delete/close the active view and, if there is another view to show make that
	 * view the active view.
	 */
	private void removeActiveView() {
		if (views.size() == 0)
			activeView = null;
		else {
			setActiveView(views.get(views.size() - 1));
			views.remove(views.size() - 1);
		}
	}

	/**
	 * Delete all views that show information about the specified table.
	 *
	 * @param tableId The id of the table for which views need to be deleted.
	 */
	public void removeViewsForTable(int tableId) {
		List<View> remaining = views.stream().filter(v -> !isViewForTable(v, tableId)).collect(Collectors.toList());
		views.clear();
		views.addAll(remaining);
		if (isViewForTable(activeView, tableId))
			removeActiveView();
	}

	/**
	 * Check if the specified view is a view for the specified table.
	 *
	 * @param view    The view for which to check.
	 * @param tableId The id of the table for which to check.
	 * @return True if the view is a view for the specified table, false otherwise.
	 */
	private boolean isViewForTable(View view, int tableId) {
		if (view.getTableId() == null)
			return false;
		int id = view.getTableId();
		return tableId == id;
	}

	/**
	 * @apiNote ONLY USED IN TESTING
	 * @return The view that is currently active.
	 */
	public View getActiveView() {
		return this.activeView;
	}

	/**
	 * Update the widths of the columns in the tables in the table views.
	 *
	 * @param widths The widths of the column used to display the table names.
	 */
	public void updateTablesViewCellWidths(int[] widths) {
		tablesViewWidths = widths;
		getTablesViews().forEach(v -> v.updateCellWidths(widths));
	}

	/**
	 * Update the widths of the columns in the tables in the table design views.
	 *
	 * @param widths The width of the columns used to display information about the
	 *               table design.
	 */
	public void updateTableDesignCellWidths(int[] widths) {
		tableDesignViewWidths = widths;
		getTablesDesignViews().forEach(v -> v.updateCellWidths(widths));
	}

	/**
	 * Update the widths of the columns in the tables in the table row views.
	 *
	 * @param widths The width of columns int the tables.
	 */
	public void updateTableRowViewCellWidths(int[] widths) {
		tableRowViewWidths = widths;
		getTablesRowViews().forEach(v -> v.updateCellWidths(widths));
	}

	/**
	 * Get the name of the column with the specified id in the specified table.
	 *
	 * @param tableId  The id of the table for which to get the column.
	 * @param columnId The id of the column to get.
	 * @return The name of the column.
	 */
	public String getColumnName(int tableId, int columnId) {
		return database.getColumnName(tableId, columnId);
	}

	/**
	 * React to a user clicking somewhere in the application. Propagates clicks to
	 * the active view. If the click isn't contained within the active view select
	 * the new active view, except if the click event was a drag event, then clicks
	 * are propagated to currently the active view.
	 *
	 * @param id         The type of mouse event.
	 * @param x          The x coordinate of the mouse click.
	 * @param y          The y coordinate of the mouse click.
	 * @param clickCount The number of times that was clicked.
	 */
	@Override
	protected void handleMouseEvent(int id, int x, int y, int clickCount) {
		// Only change when the user isn't dragging something around.
		if (activeView != null && !previousClickWasDrag && !activeView.contains(x, y)) {
			View[] PossibleViews = views.stream().filter(v -> v.contains(x, y)).toArray(View[]::new);
			if (PossibleViews.length != 0) {
				setActiveView(PossibleViews[PossibleViews.length - 1]);
			}
		}
		previousClickWasDrag = id == 506;
		if (activeView != null)
			activeView.handleMouseEvent(id, x, y, clickCount);
		repaint();
	}

	/**
	 * React to a user pressing a key.
	 *
	 * @param id      The type of key event.
	 * @param keyCode The number of key that was pressed.
	 * @param keyChar The character that was typed.
	 * @effect If the escape key was pressed call the escape pressed function of the
	 *         active view. | if (keyCode == 27 &amp;&amp; getActiveView() != null)
	 *         | activeView.escapePressed()
	 * @effect If a the previous keypress was control and this keypress was t open a
	 *         new tablesView. | if (keyCode == 84 &amp;&amp; ctrlPressed) |
	 *         tablesView()
	 * @effect If the active view isn't null, propagate the event to the active
	 *         view. | if (getActiveView() != null) |
	 *         getActiveView().handleKeyEvent(id, keyCode, keyChar)
	 */
	@Override
	public void handleKeyEvent(int id, int keyCode, char keyChar) {
		if (keyCode == 27 && activeView != null)
			activeView.escapePressed();
		else if (keyCode == 84 && ctrlPressed)
			tablesView();
		else if (ctrlPressed && shiftPressed && keyCode == 90)
			redo();
		else if (ctrlPressed && keyCode == 90)
			undo();
		if (keyCode == 16)
			shiftPressed = true;
		else if (keyCode == 17)
			ctrlPressed = true;
		else {
			shiftPressed = false;
			ctrlPressed = false;
		}
		if (activeView != null)
			activeView.handleKeyEvent(id, keyCode, keyChar);
		repaint();
	}

	/**
	 * Redoes the last undone command, present in the Redo stack if this is allowed
	 * and adds this command to the Undo stack again
	 */
	private void redo() {
		if (redoAllowed) {
			if (!redo.isEmpty()) {
				executeCommand(redo.pop());
			}
		}
	}

	/**
	 * Undoes the last executed command, present in the Undo stack Afterwards this
	 * command is added to the Redo stack
	 */
	private void undo() {
		if (!undo.isEmpty()) {
			ICommand com = undo.pop();
			com.undo();
			redo.add(com);
			redoAllowed = true;
			populateAfterCommand(com);
		}
	}

	/**
	 * Executes the given command and adds it to the undoStack
	 *
	 * @param com
	 */
	private void executeCommand(ICommand com) {
		com.execute();
		undo.add(com);
		populateAfterCommand(com);
	}


	/**
	 * Update all the views that a command has influence over
	 *
	 * @param com the command that was inacted
	 */
	private void populateAfterCommand(ICommand com) {
		//TODO if the table in question is involved with aquery more tables can be removed!
        if (com.tableDeleteRequired()) {
            removeViewsForTable(com.tableToDelete());
            //TODO Find all tableViews that depended on the table and also remove those
        }
        if (com.rowDeleteRequired() || com.rowUpdateRequired()) {
            populateAllTableDesignViewsForTable(com.tableToUpdate());
            populateAllTableRowViewsForTable(com.tableToUpdate());
			populateAllFormViewViewsForTable(com.tableToUpdate());
			getFormViews().forEach(FormView::updateTitle);
		}
		if (com.tablenameUpdateRequired()) {
			getTablesRowViews().forEach(TableRowView::updateTitle);
			getTablesDesignViews().forEach(TableDesignView::updateTitle);
			getFormViews().forEach(FormView::updateTitle);
		}
        if (com.tableUpdateRequired()) {
			populateAllTableViews();
		}
		// TODO Column edits
		//rerun query
		//if (com.rowUpdateRequired()) TODO CLEAN
		//this.editQuery(com.tableToUpdate(),this.getDatabase().getTable(com.tableToUpdate()).getQuery());
	}

	/**
	 * Draw all the view in the order that they were last active, with the active
	 * view on top.
	 *
	 * @param g This object offers the methods that allow you to paint on the
	 *          canvas.
	 */
	@Override
	protected void paint(Graphics g) {

		// This is a terrible way to set the background, but I can't think of a better
		// way.
		Color tmp = g.getColor();
		g.setColor(new Color(240, 240, 240));
		g.fillRect(0, 0, 100000, 100000);
		g.setColor(tmp);

		if (activeView != null) {
			if (!views.isEmpty()) {
				views.forEach(v -> v.paint(g));
			}
			activeView.paint(g);
		}
	}

	/**
	 * Checks if a query is valid
	 * @param tableId, the table for which the query has be checked
	 * @param query, the query that needs to be checked if it is valid
	 * @return isValid(query) == True
	 */
	public boolean isValidQuery(Integer tableId, String query) {
		if(query.isEmpty())
			return true;
		try{
			return QueryFacade.isValidQuery(query, database);
		} catch (Exception e)
		{
			return false;
		}
	}

	/**
	 * Sets a Query for a Computed Table
	 * @param tableId
	 * @param query
	 */
	public void editQuery(int tableId, String query) {
		executeCommand(new EditQueryCommand(tableId, query));
	}

	/**
	 * returns the Query for a table
	 * @param tableId
	 * @return Query in String
	 */
	public String getQuery(int tableId) {
		return database.getQuery(tableId);
	}
}
