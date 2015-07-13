package de.uniba.kinf.projm.hylleblomst.database.utils;

import java.util.ArrayList;
import java.util.Collections;

import org.apache.derby.agg.Aggregator;

/**
 * @author Simon
 *
 */
@SuppressWarnings("serial")
public final class GroupConcat implements
		Aggregator<String, String, GroupConcat> {

	private ArrayList<String> values;

	public GroupConcat() {

	}

	@Override
	public void accumulate(String nextArg) {
		values.add(nextArg);
	}

	@Override
	public void init() {
		values = new ArrayList<String>();
	}

	@Override
	public void merge(GroupConcat args) {
		values.addAll(args.values);
	}

	@Override
	public String terminate() {
		Collections.sort(values);

		int count = values.size();

		if (count == 0) {
			return null;
		} else {
			StringBuilder result = new StringBuilder("");
			for (String value : values) {
				result.append(value);
				result.append(";");
			}
			return result.toString().substring(0, result.length() - 1);
		}
	}
}
