package com.swopteam07.tablr.test.facade;

import com.swopteam07.tablr.model.Database;
import com.swopteam07.tablr.model.Row;
import com.swopteam07.tablr.model.Table;

import java.util.List;

/**
 * Interface for creating easier tests in the model
 * Allows faster testing
 *
 * @author rapha
 */
public interface ModelTestFacade {
    static Database getModel() {
        return Database.getInstance();
    }

    static int addTable() {
        return getModel().addTable();
    }

    static int getTableCount() {
        return getModel().getTables().length;
    }

    static Table[] getTables() {
        return getModel().getTables();
    }

    static List<String> getTablesNames() {
        return getModel().getTablesNames();
    }

    static void deleteTable(int id) {
        getModel().deleteTable(id);
    }

    static Table getTable(int i) {
        return getModel().getTable(i);
    }

    static void removeModelReference() {
        Database.removeInstanceReference();
    }

    static int addColumn(int tableId) {
        return getModel().addColumn(tableId);
    }

    static void removeColumn(int tableId, int columnId) {
        getModel().deleteColumn(tableId, columnId);
    }

    static int addRow(int tableId) {
        return getModel().addRow(tableId);
    }

    static void removeRow(int tableId, int rowId) {
        getModel().deleteRow(tableId, rowId);
    }

    static Row getRow(int tableId, int rowId) {
        return getTable(tableId).getRow(rowId);
    }

    static void removeTable(int tableId) {
        getModel().deleteTable(tableId);

    }
}
