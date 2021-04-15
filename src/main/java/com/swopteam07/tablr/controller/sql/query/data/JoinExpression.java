package com.swopteam07.tablr.controller.sql.query.data;

import com.swopteam07.tablr.controller.sql.SourceLocation;
import com.swopteam07.tablr.controller.sql.query.id.SqlId;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class JoinExpression
{
	private SqlId leftid;
	private SqlId rightId;

	/**
	 * class responsible for merging tables
	 *
	 * @param ctx     location inside the query string
	 * @param leftid  reference to first table
	 * @param rightId reference to second table
	 */
	public JoinExpression(SourceLocation ctx, SqlId leftid, SqlId rightId)
	{
		this.leftid = leftid;
		this.rightId = rightId;
	}

	/**
	 * returns the merged data
	 * @param data1 data of the first table
	 * @param data2 data of the second table
	 * @return RowData containing merged data
	 */
	public RowData merge(RowData data1, RowData data2)
	{
		RowData left = (data1.getSqlId().matchAlias(leftid.getAlias())) ? data1
				: (data2.getSqlId().matchAlias(leftid.getAlias())) ? data2 : null;
		RowData right = (data1.getSqlId().matchAlias(rightId.getAlias())) ? data1 :
				(data2.getSqlId().matchAlias(rightId.getAlias())) ? data2 : null;
		if (left == null || right == null)
			throw new IllegalStateException();

		int leftColumnId = left.getColumnId(leftid);
		int rightColumnId = right.getColumnId(rightId);
		List<String> newColumns = new ArrayList<>();
		if (left.getSqlId().getNames().size() == 0)
			newColumns.addAll(new ArrayList<>(left.getColumns()));
		else
			newColumns.addAll(left.getColumns().stream().map(obj -> left.getName() + "/" + obj).collect(Collectors.toList()));

		if (right.getSqlId().getNames().size() == 0)
			newColumns.addAll(new ArrayList<>(right.getColumns()));
		else
			newColumns.addAll(right.getColumns().stream().map(obj -> right.getName() + "/" + obj).collect(Collectors.toList()));

		//rightColumns.remove(rightColumnId);//REMOVE FOR DUPLICATE ID
		List<List<RawCell>> newRows = new LinkedList<>();
		for (List<RawCell> row : left.getRows())
		{
			RawCell leftObj = row.get(leftColumnId);

			if (right.search(rightColumnId, leftObj) != null)
			{
				List<RawCell> search = new ArrayList<>(right.search(rightColumnId, leftObj));
				//remove id otherwise duplicate
				//search.remove(rightColumnId);
				LinkedList<RawCell> newRow = new LinkedList<>();
				newRow.addAll(row);
				newRow.addAll(search);
				newRows.add(newRow);
			}
		}

		return new RowData(newColumns, newRows, left.getName() + "//" + right.getName());//TODO MULTIPLE SQLID
	}

	public SqlId getLeftid()
	{
		return leftid;
	}

	public SqlId getRightId()
	{
		return rightId;
	}
}
