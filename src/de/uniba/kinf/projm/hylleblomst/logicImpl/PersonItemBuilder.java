package de.uniba.kinf.projm.hylleblomst.logicImpl;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

import de.uniba.kinf.projm.hylleblomst.logic.PersonItem;

/**
 * Class builds PersonItems out of a ResultSet.
 * 
 * @author ca
 *
 */
public class PersonItemBuilder {

	/**
	 * Initializes the build of PersonItems and returns the complete list.
	 * 
	 * @param resultSet
	 *            the {@link ResultSet} which should be converted
	 * @return the {@link List} of {@link PersonItem}s
	 */
	public List<PersonItem> startBuildingPersonItems(ResultSet resultSet) {
		if (resultSet == null) {
			throw new InputMismatchException(
					"The given ResultSet has no value (is null).");
		}
		// analyze the given ResultSet
		ResultSetMetaData metaData;
		int numberOfColumns = 0;
		try {
			metaData = resultSet.getMetaData();
			numberOfColumns = metaData.getColumnCount();

			if (numberOfColumns == 0) {
				throw new RuntimeException(
						"ResultSet could not be processed, no columns were found.");
			}

			List<ArrayList<String>> resultSetLists = new ArrayList<ArrayList<String>>();

			for (int i = 1; i <= numberOfColumns; i++) {
				Array idColumn = resultSet.getArray("PersonID");

				// String columnName = metaData.getColumnName(i);

				ArrayList<String> tmp = new ArrayList<String>();
				tmp.add(metaData.getColumnName(i));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// build the PersonItem
		List<PersonItem> personItemsList = new ArrayList<PersonItem>();
		// add it to a list

		// return a list
		return personItemsList;
	}
}
