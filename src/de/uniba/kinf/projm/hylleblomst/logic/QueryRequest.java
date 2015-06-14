package de.uniba.kinf.projm.hylleblomst.logic;

public class QueryRequest {
	private SearchFieldKeys searchField;
	private Object input;
	private int source;

	public QueryRequest(SearchFieldKeys columns, Object input, int source) {
		setSearchField(columns);
		setInput(input);
		setSource(source);
	}

	QueryRequest() {
	}

	public SearchFieldKeys getSearchField() {
		return searchField;
	}

	public void setSearchField(SearchFieldKeys columns) {
		this.searchField = columns;
	}

	public Object getInput() {
		return input;
	}

	public void setInput(Object input) {
		this.input = input;
	}

	public int getSource() {
		return source;
	}

	public void setSource(int source) {
		this.source = source;
	}
}
