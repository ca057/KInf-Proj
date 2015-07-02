package de.uniba.kinf.projm.hylleblomst.logicImpl;

import java.sql.ResultSet;
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
		//
		return null;
	}
}
