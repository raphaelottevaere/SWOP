package com.swopteam07.tablr.test.query;

import com.swopteam07.tablr.controller.QueryFacade;
import com.swopteam07.tablr.model.Database;
import com.swopteam07.tablr.model.DomainException;
import com.swopteam07.tablr.model.Table;
import com.swopteam07.tablr.model.column.Column;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SqlParseTest
{
	static void assertRoundTrip(String query, Database database)
	{
		QueryFacade.parseQuery(query, database, "testTable");
		assertTrue(true);
	}

	@BeforeEach
	public void dereferenceDatabase()
	{
		Database.removeInstanceReference();
	}

	@Test
	public void queryTest1()
	{
		Database database = Database.getInstance();
		int tableId = database.addTable();
		database.editTableName(tableId, "students");

		int columnId1 = database.addColumn(tableId);
		database.updateColumnName(tableId, columnId1, "name");
		database.updateDefaultValue(tableId, columnId1, "defaultValue");
		database.updateBlanksAllowed(tableId, columnId1, false);

		int columnId2 = database.addColumn(tableId);
		database.updateColumnName(tableId, columnId2, "program");
		database.updateDefaultValue(tableId, columnId2, "defaultValue");
		database.updateBlanksAllowed(tableId, columnId2, false);

		int columnId3 = database.addColumn(tableId);
		database.updateColumnName(tableId, columnId3, "student_id");
		database.updateColumnType(tableId, columnId3, "INTEGER");
		database.updateDefaultValue(tableId, columnId3, "0");
		database.updateBlanksAllowed(tableId, columnId3, false);

		int rowId = database.addRow(tableId);
		database.setValue(tableId, rowId, columnId1, "Louis");
		database.setValue(tableId, rowId, columnId2, "informatica");
		database.setValue(tableId, rowId, columnId3, 1);


		tableId = database.addTable();
		database.editTableName(tableId, "enrollments");

		columnId1 = database.addColumn(tableId);
		database.updateColumnName(tableId, columnId1, "student_id");
		database.updateColumnType(tableId, columnId1, "INTEGER");
		database.updateDefaultValue(tableId, columnId1, "0");
		database.updateBlanksAllowed(tableId, columnId1, false);

		columnId2 = database.addColumn(tableId);
		database.updateColumnName(tableId, columnId2, "courseName");
		database.updateDefaultValue(tableId, columnId2, "default");
		database.updateBlanksAllowed(tableId, columnId2, false);

		columnId3 = database.addColumn(tableId);
		database.updateColumnName(tableId, columnId3, "course_id");
		database.updateDefaultValue(tableId, columnId3, "default");
		database.updateBlanksAllowed(tableId, columnId3, false);

		rowId = database.addRow(tableId);
		database.setValue(tableId, rowId, columnId1, 1);
		database.setValue(tableId, rowId, columnId2, "swop");
		database.setValue(tableId, rowId, columnId3, "SWOP");
		String strSql = "SELECT student.name AS name, student.program AS program FROM enrollments AS " +
				"enrollment INNER JOIN students AS student ON enrollment.student_id = student.student_id " +
				"WHERE enrollment.course_id = \"SWOP\";";

		int testTableId = QueryFacade.parseQuery(strSql, database, "testTable");
		Table table = database.getTable(testTableId);
		Column[] columns = table.getColumns();
		assertEquals(1, table.getRows().length);
		assertEquals("name", columns[0].getName());
		assertEquals("program", columns[1].getName());
		assertEquals(2, table.getRows()[0].getNbCells());
		assertEquals("Louis", table.getRows()[0].getCell(0).getValue());
		assertEquals("informatica", table.getRows()[0].getCell(1).getValue());
	}

	@Test
	public void queryTest2()
	{
		String strSql = "SELECT movie.title AS title FROM movies AS movie WHERE movie.imdb_score > 7 - 2";
		Database database = Database.getInstance();

		//movie table
		int tableId = database.addTable();
		database.editTableName(tableId, "movies");

		//move title
		int columnId1 = database.addColumn(tableId);
		database.updateColumnName(tableId, columnId1, "title");
		database.updateDefaultValue(tableId, columnId1, "defaultValue");
		database.updateBlanksAllowed(tableId, columnId1, false);
		//imdb score
		int columnId2 = database.addColumn(tableId);
		database.updateColumnName(tableId, columnId2, "imdb_score");
		database.updateColumnType(tableId, columnId2, "INTEGER");
		database.updateDefaultValue(tableId, columnId2, "0");
		database.updateBlanksAllowed(tableId, columnId2, false);

		int rowId = database.addRow(tableId);
		database.setValue(tableId, rowId, columnId1, "film1");
		database.setValue(tableId, rowId, columnId2, 8);

		rowId = database.addRow(tableId);
		database.setValue(tableId, rowId, columnId1, "film2");
		database.setValue(tableId, rowId, columnId2, 6);

		rowId = database.addRow(tableId);
		database.setValue(tableId, rowId, columnId1, "film3");
		database.setValue(tableId, rowId, columnId2, 5);

		int testTableId = QueryFacade.parseQuery(strSql, database, "testTable");
		Table table = database.getTable(testTableId);
		Column[] columns = table.getColumns();
		assertEquals("title", columns[0].getName());
		assertEquals(2, table.getRows().length);
		assertEquals(1, table.getRows()[0].getNbCells());
		assertEquals("film1", table.getRows()[0].getCell(0).getValue());
		assertEquals("film2", table.getRows()[1].getCell(0).getValue());
	}

	@Test
	void queryTest3()
	{
		String strSql = "SELECT parent.name AS parentName, child.name AS childName" +
				" FROM persons AS parent" +
				" INNER JOIN is_child_of AS link ON parent.id = link.parent_id" +
				" INNER JOIN persons AS child ON link.child_id = child.id" +
				" WHERE TRUE";
		Database database = Database.getInstance();

		//enrollments table
		int personTableId = database.addTable();
		database.editTableName(personTableId, "persons");
		int is_child_ofTable = database.addTable();
		database.editTableName(is_child_ofTable, "is_child_of");

		//person id
		int personIdColumn = database.addColumn(personTableId);
		database.updateColumnName(personTableId, personIdColumn, "id");
		database.updateColumnType(personTableId, personIdColumn, "INTEGER");
		//person name
		int personNameColumn = database.addColumn(personTableId);
		database.updateColumnName(personTableId, personNameColumn, "name");

		// link id
		int child_id = database.addColumn(is_child_ofTable);
		database.updateColumnName(is_child_ofTable, child_id, "child_id");
		database.updateColumnType(is_child_ofTable, child_id, "INTEGER");
		//database.updateBlanksAllowed(is_child_ofTable, child_id, false);

		int parent_id = database.addColumn(is_child_ofTable);
		database.updateColumnName(is_child_ofTable, parent_id, "parent_id");
		database.updateColumnType(is_child_ofTable, parent_id, "INTEGER");
		//database.updateBlanksAllowed(is_child_ofTable, parent_id, false);

		int parentRowId = database.addRow(personTableId);
		database.setValue(personTableId, parentRowId, personIdColumn, 1);
		database.setValue(personTableId, parentRowId, personNameColumn, "father");

		int childRowId = database.addRow(personTableId);
		database.setValue(personTableId, childRowId, personIdColumn, 2);
		database.setValue(personTableId, childRowId, personNameColumn, "son");

		int grandfatherID = database.addRow(personTableId);
		database.setValue(personTableId, grandfatherID, personIdColumn, 3);
		database.setValue(personTableId, grandfatherID, personNameColumn, "grandfather");


		int parentChildRowId = database.addRow(is_child_ofTable);
		database.setValue(is_child_ofTable, parentChildRowId, parent_id, 1);
		database.setValue(is_child_ofTable, parentChildRowId, child_id, 2);

		int parentGrandchildRowId = database.addRow(is_child_ofTable);
		database.setValue(is_child_ofTable, parentGrandchildRowId, parent_id, 3);
		database.setValue(is_child_ofTable, parentGrandchildRowId, child_id, 1);

		int testTableId = QueryFacade.parseQuery(strSql, database, "testTable");
		Table table = database.getTable(testTableId);
		Column[] columns = table.getColumns();
		assertEquals(2, table.getRows().length);
		assertEquals("parentName", columns[0].getName());
		assertEquals("childName", columns[1].getName());
		assertEquals(2, table.getRows()[0].getNbCells());
		assertEquals("father", table.getRows()[0].getCell(0).getValue());
		assertEquals("son", table.getRows()[0].getCell(1).getValue());
		assertEquals("grandfather", table.getRows()[1].getCell(0).getValue());
		assertEquals("father", table.getRows()[1].getCell(1).getValue());
	}

	@Test
	void queryTest3ReversedOnOrder()
	{
		String strSql = "SELECT parent.name AS parentName, child.name AS childName" +
				" FROM persons AS parent" +
				" INNER JOIN is_child_of AS link ON link.parent_id = parent.id" +
				" INNER JOIN persons AS child ON child.id = link.child_id" +
				" WHERE TRUE";
		Database database = Database.getInstance();

		//enrollments table
		int personTableId = database.addTable();
		database.editTableName(personTableId, "persons");
		int is_child_ofTable = database.addTable();
		database.editTableName(is_child_ofTable, "is_child_of");

		//person id
		int personIdColumn = database.addColumn(personTableId);
		database.updateColumnName(personTableId, personIdColumn, "id");
		database.updateColumnType(personTableId, personIdColumn, "INTEGER");
		//person name
		int personNameColumn = database.addColumn(personTableId);
		database.updateColumnName(personTableId, personNameColumn, "name");

		// link id
		int child_id = database.addColumn(is_child_ofTable);
		database.updateColumnName(is_child_ofTable, child_id, "child_id");
		database.updateColumnType(is_child_ofTable, child_id, "INTEGER");
		//database.updateBlanksAllowed(is_child_ofTable, child_id, false);

		int parent_id = database.addColumn(is_child_ofTable);
		database.updateColumnName(is_child_ofTable, parent_id, "parent_id");
		database.updateColumnType(is_child_ofTable, parent_id, "INTEGER");
		//database.updateBlanksAllowed(is_child_ofTable, parent_id, false);

		int parentRowId = database.addRow(personTableId);
		database.setValue(personTableId, parentRowId, personIdColumn, 1);
		database.setValue(personTableId, parentRowId, personNameColumn, "father");

		int childRowId = database.addRow(personTableId);
		database.setValue(personTableId, childRowId, personIdColumn, 2);
		database.setValue(personTableId, childRowId, personNameColumn, "son");

		int grandfatherID = database.addRow(personTableId);
		database.setValue(personTableId, grandfatherID, personIdColumn, 3);
		database.setValue(personTableId, grandfatherID, personNameColumn, "grandfather");


		int parentChildRowId = database.addRow(is_child_ofTable);
		database.setValue(is_child_ofTable, parentChildRowId, parent_id, 1);
		database.setValue(is_child_ofTable, parentChildRowId, child_id, 2);

		int parentGrandchildRowId = database.addRow(is_child_ofTable);
		database.setValue(is_child_ofTable, parentGrandchildRowId, parent_id, 3);
		database.setValue(is_child_ofTable, parentGrandchildRowId, child_id, 1);

		int testTableId = QueryFacade.parseQuery(strSql, database, "testTable");
		Table table = database.getTable(testTableId);
		Column[] columns = table.getColumns();
		assertEquals(2, table.getRows().length);
		assertEquals("parentName", columns[0].getName());
		assertEquals("childName", columns[1].getName());
		assertEquals(2, table.getRows()[0].getNbCells());
		assertEquals("father", table.getRows()[1].getCell(0).getValue());
		assertEquals("son", table.getRows()[1].getCell(1).getValue());
		assertEquals("grandfather", table.getRows()[0].getCell(0).getValue());
		assertEquals("father", table.getRows()[0].getCell(1).getValue());
	}

	@Test
	public void TestLockingMechanism()
	{
		String strSql = "SELECT movie.title AS title FROM movies AS movie WHERE movie.imdb_score > 7";
		Database database = Database.getInstance();

		//movie table
		int tableId = database.addTable();
		database.editTableName(tableId, "movies");

		//move title
		int columnId1 = database.addColumn(tableId);
		database.updateColumnName(tableId, columnId1, "title");
		database.updateDefaultValue(tableId, columnId1, "defaultValue");
		database.updateBlanksAllowed(tableId, columnId1, false);
		//imdb score
		int columnId2 = database.addColumn(tableId);
		database.updateColumnName(tableId, columnId2, "imdb_score");
		database.updateColumnType(tableId, columnId2, "INTEGER");
		database.updateDefaultValue(tableId, columnId2, "0");
		database.updateBlanksAllowed(tableId, columnId2, false);

		int rowId = database.addRow(tableId);
		database.setValue(tableId, rowId, columnId1, "film1");
		database.setValue(tableId, rowId, columnId2, 8);

		rowId = database.addRow(tableId);
		database.setValue(tableId, rowId, columnId1, "film2");
		database.setValue(tableId, rowId, columnId2, 6);

		int queryTable = database.addTable();
		database.editQuery(queryTable, strSql);

		assertThrows(DomainException.class, () -> database.editTableName(tableId, "newMoviesTableName"));
		assertThrows(DomainException.class, () -> database.updateColumnName(tableId, columnId1, "newtitlecolumnName"));
	}

	@Test
	public void TestRemoveLockingMechanism()
	{
		String strSql = "SELECT movie.title AS title FROM movies AS movie WHERE movie.imdb_score > 7 - 2";
		Database database = Database.getInstance();

		//movie table
		int tableId = database.addTable();
		database.editTableName(tableId, "movies");

		//move title
		int columnId1 = database.addColumn(tableId);
		database.updateColumnName(tableId, columnId1, "title");
		database.updateDefaultValue(tableId, columnId1, "defaultValue");
		database.updateBlanksAllowed(tableId, columnId1, false);
		//imdb score
		int columnId2 = database.addColumn(tableId);
		database.updateColumnName(tableId, columnId2, "imdb_score");
		database.updateColumnType(tableId, columnId2, "INTEGER");
		database.updateDefaultValue(tableId, columnId2, "0");
		database.updateBlanksAllowed(tableId, columnId2, false);

		int rowId = database.addRow(tableId);
		database.setValue(tableId, rowId, columnId1, "film1");
		database.setValue(tableId, rowId, columnId2, 8);

		rowId = database.addRow(tableId);
		database.setValue(tableId, rowId, columnId1, "film2");
		database.setValue(tableId, rowId, columnId2, 6);

		int queryTable = database.addTable();
		database.editQuery(queryTable, strSql);
		database.deleteTable(1);
		String newMoviesTableName = "newMoviesTableName";
		assertDoesNotThrow(() -> database.editTableName(tableId, newMoviesTableName));
		String newtitlecolumnName = "newtitlecolumnName";
		assertDoesNotThrow(() -> database.updateColumnName(tableId, columnId1, newtitlecolumnName));
		assertTrue(database.getTable(tableId).getName().equals(newMoviesTableName));
		assertTrue(database.getTable(tableId).getColumn(columnId1).getName().equals(newtitlecolumnName));
	}


	@Test
	public void linkTest()
	{
		String strSql = "SELECT movie.title AS title FROM movies AS movie WHERE movie.imdb_score > 7 - 2";
		Database database = Database.getInstance();

		//movie table
		int tableId = database.addTable();
		database.editTableName(tableId, "movies");

		//move title
		int columnId1 = database.addColumn(tableId);
		database.updateColumnName(tableId, columnId1, "title");
		database.updateDefaultValue(tableId, columnId1, "defaultValue");
		database.updateBlanksAllowed(tableId, columnId1, false);
		//imdb score
		int columnId2 = database.addColumn(tableId);
		database.updateColumnName(tableId, columnId2, "imdb_score");
		database.updateColumnType(tableId, columnId2, "INTEGER");
		database.updateDefaultValue(tableId, columnId2, "0");
		database.updateBlanksAllowed(tableId, columnId2, false);

		int rowId = database.addRow(tableId);
		database.setValue(tableId, rowId, columnId1, "film1");
		database.setValue(tableId, rowId, columnId2, 8);

		int rowId2 = database.addRow(tableId);
		database.setValue(tableId, rowId2, columnId1, "film2");
		database.setValue(tableId, rowId2, columnId2, 6);

		int rowId3 = database.addRow(tableId);
		database.setValue(tableId, rowId3, columnId1, "film3");
		database.setValue(tableId, rowId3, columnId2, 5);

		int testTableId = QueryFacade.parseQuery(strSql, database, "testTable");
		Table table = database.getTable(testTableId);
		Column[] columns = table.getColumns();
		String newFilm1 = "newFilm1";
		database.setValue(testTableId, 0, 0, newFilm1);
		String newFilm2 = "newFilm2";
		database.setValue(testTableId, 1, 0, newFilm2);
		assertEquals(newFilm1, table.getRows()[0].getCell(0).getValue());
		assertEquals(newFilm2, table.getRows()[1].getCell(0).getValue());
	}

	@Test
	public void removeParentTest()
	{
		String strSql = "SELECT movie.title AS title FROM movies AS movie WHERE movie.imdb_score > 7 - 2";
		Database database = Database.getInstance();

		//movie table
		int tableId = database.addTable();
		database.editTableName(tableId, "movies");

		//move title
		int columnId1 = database.addColumn(tableId);
		database.updateColumnName(tableId, columnId1, "title");
		database.updateDefaultValue(tableId, columnId1, "defaultValue");
		database.updateBlanksAllowed(tableId, columnId1, false);
		//imdb score
		int columnId2 = database.addColumn(tableId);
		database.updateColumnName(tableId, columnId2, "imdb_score");
		database.updateColumnType(tableId, columnId2, "INTEGER");
		database.updateDefaultValue(tableId, columnId2, "0");
		database.updateBlanksAllowed(tableId, columnId2, false);

		int rowId = database.addRow(tableId);
		database.setValue(tableId, rowId, columnId1, "film1");
		database.setValue(tableId, rowId, columnId2, 8);

		int rowId2 = database.addRow(tableId);
		database.setValue(tableId, rowId2, columnId1, "film2");
		database.setValue(tableId, rowId2, columnId2, 6);

		int rowId3 = database.addRow(tableId);
		database.setValue(tableId, rowId3, columnId1, "film3");
		database.setValue(tableId, rowId3, columnId2, 5);

		var queryTable = database.addTable();
		database.editQuery(queryTable, strSql);
		assertEquals(2, database.getTableCount());
		database.deleteTable(tableId);
		assertEquals(0, database.getTableCount());
	}

	@Test
	public void removeParentColumnTest()
	{
		String strSql = "SELECT movie.title AS title FROM movies AS movie WHERE movie.imdb_score > 7 - 2";
		Database database = Database.getInstance();

		//movie table
		int tableId = database.addTable();
		database.editTableName(tableId, "movies");

		//move title
		int columnId1 = database.addColumn(tableId);
		database.updateColumnName(tableId, columnId1, "title");
		database.updateDefaultValue(tableId, columnId1, "defaultValue");
		database.updateBlanksAllowed(tableId, columnId1, false);
		//imdb score
		int columnId2 = database.addColumn(tableId);
		database.updateColumnName(tableId, columnId2, "imdb_score");
		database.updateColumnType(tableId, columnId2, "INTEGER");
		database.updateDefaultValue(tableId, columnId2, "0");
		database.updateBlanksAllowed(tableId, columnId2, false);

		int rowId = database.addRow(tableId);
		database.setValue(tableId, rowId, columnId1, "film1");
		database.setValue(tableId, rowId, columnId2, 8);

		int rowId2 = database.addRow(tableId);
		database.setValue(tableId, rowId2, columnId1, "film2");
		database.setValue(tableId, rowId2, columnId2, 6);

		int rowId3 = database.addRow(tableId);
		database.setValue(tableId, rowId3, columnId1, "film3");
		database.setValue(tableId, rowId3, columnId2, 5);
		int queryTable = database.addTable();
		database.editQuery(queryTable, strSql);
		assertEquals(2, database.getTableCount());
		database.deleteColumn(tableId, columnId1);
		assertEquals(1, database.getTableCount());
	}
}
