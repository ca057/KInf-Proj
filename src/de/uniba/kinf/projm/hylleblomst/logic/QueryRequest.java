package de.uniba.kinf.projm.hylleblomst.logic;

public class QueryRequest {
	private SearchFieldKeys columns;
	private Object input;
	private int source;

	public QueryRequest(SearchFieldKeys columns, Object input, int source) {
		setColumns(columns);
		setInput(input);
		setSource(source);
	}

	QueryRequest() {
	}

	public SearchFieldKeys getColumns() {
		return columns;
	}

	public void setColumns(SearchFieldKeys columns) {
		this.columns = columns;
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
