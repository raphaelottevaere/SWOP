package com.swopteam07.tablr.test;

import com.swopteam07.tablr.model.DomainException;
import com.swopteam07.tablr.model.Row;
import com.swopteam07.tablr.model.Table;
import com.swopteam07.tablr.test.facade.ModelTestFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;


//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
/**
 * Basic model test for insuring the underlying model works as planned
 * Not directly part of any single usecase
 * @author rapha
 *
 */
public class ModelTest
{

	@BeforeEach
	public void cleanModel()
	{
		ModelTestFacade.removeModelReference();
	}

	@Test
	public void testCreateTable()
	{
		int before = ModelTestFacade.getTableCount();
		int id = ModelTestFacade.addTable();
		assertEquals(before + 1, ModelTestFacade.getTableCount());
		assertDoesNotThrow(() -> ModelTestFacade.getTable(id));
	}

	@Test
	public void testCreateTableUniqueName() {
		Set<String> oldTables = Arrays.stream(ModelTestFacade.getTables()).map(Table::getName).collect(Collectors.toSet());
		ModelTestFacade.addTable();
		Set<String> newTables = Arrays.stream(ModelTestFacade.getTables()).map(Table::getName).collect(Collectors.toSet());
		assertEquals(newTables.size() - 1, oldTables.size());
	}

	@Test
	public void testDeleteTable()
	{
		assert ModelTestFacade.getTablesNames().stream().noneMatch(obj -> obj.equals("testTable"));
		int id = ModelTestFacade.addTable();
		ModelTestFacade.deleteTable(id);
		assertThrows(DomainException.class, () -> ModelTestFacade.getTable(id));
	}

	@Test
	public void testDeleteNonExcistingTable()
	{
		assert ModelTestFacade.getTableCount() == 0;
		assertThrows(DomainException.class, () -> ModelTestFacade.deleteTable(0));
	}

	@Test
	public void testAddColumn()
	{
		ModelTestFacade.addTable();
		Table table = ModelTestFacade.getTable(0);
		int id = table.addColumn();
		assertEquals(1, table.getNbColumns());
		assertDoesNotThrow(() -> table.getColumn(id));
	}

	@Test
	public void testRemoveColumn()
	{
		int tableId = ModelTestFacade.addTable();
		int columnId = ModelTestFacade.addColumn(tableId);
		ModelTestFacade.removeColumn(tableId, columnId);
		Table table = ModelTestFacade.getTable(tableId);
		assertEquals(0, table.getNbColumns());
		assertThrows(DomainException.class, () -> table.getColumn(columnId));
	}

	@Test
	public void testAddRow()
	{
		int tableId = ModelTestFacade.addTable();
		int rowId = ModelTestFacade.addRow(tableId);
		Table table = ModelTestFacade.getTable(tableId);
		assertEquals(1, table.getNbRows());
		assertDoesNotThrow(() -> table.getRow(rowId));
	}

	@Test
	public void testRemoveRow() {
		int tableId = ModelTestFacade.addTable();
		int rowCount = ModelTestFacade.getTable(tableId).getNbRows();
		int rowId = ModelTestFacade.addRow(tableId);
		ModelTestFacade.removeRow(tableId, rowId);
		Table table = ModelTestFacade.getTable(tableId);
		assertEquals(rowCount, table.getNbRows());
		assertThrows(DomainException.class, () -> table.getRow(rowId));
	}

	@Test
	public void testRemoveColumnAndAssociatedCells() {
		int tableId = ModelTestFacade.addTable();
		int columnId = ModelTestFacade.addColumn(tableId);
		ModelTestFacade.addColumn(tableId);
		int rowId = ModelTestFacade.addRow(tableId);
		ModelTestFacade.removeColumn(tableId, columnId);
		Row row = ModelTestFacade.getRow(tableId, rowId);
		assertEquals(1, row.getNbCells());
	}
}
