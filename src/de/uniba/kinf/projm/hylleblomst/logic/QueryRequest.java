package de.uniba.kinf.projm.hylleblomst.logic;

public class QueryRequest {
	private SearchFieldKeys searchField;
	private Object input;
	private int source;
	private String table;
	private String column;

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

	/**
	 * Returns the name of the table the {@code SearchFieldKey} key belongs to.
	 * 
	 * @param key
	 * @return
	 */
	// TODO Collection/ List durchgehen, PreparedStatement mit so vielen ?
	// (person.vorname=?) wie Queries, dann nochmal durchgehen und fragezeichen
	// füllen. WICHTIG: Collection muss sortiert sein!

	private void searchFieldKeyToDatabaseData(SearchFieldKeys key) {
		switch (key) {
		case ADELIG:
			table = "PERSON";
			column = "ADLIG";
			break;
		case JESUIT:
			table = "PERSON";
			column = "JESUIT";
			break;
		case STUDIENJAHR:
			table = "PERSON";
			column = "STUDIENJAHR";
			break;
		case STUDJAHR_VON:
			// TODO bei von bis wird es ein Problem geben, oder?
			table = "PERSON";
			column = "STUDIENJAHR";
			break;
		case STUDJAHR_BIS:
			// TODO bei von bis wird es ein Problem geben, oder?
			table = "PERSON";
			column = "STUDIENJAHR";
			break;
		case EINSCHREIBEDATUM_TAGE:
			// TODO Das muss am besten vorher in ein Datum umgewandelt werden.
			// Oder übergibt Christian hier eh keine Einzeldaten?
			table = "PERSON";
			column = "";
			break;
		case EINSCHREIBEDATUM_MONATE:
			table = "PERSON";
			column = "";
			break;
		case EINSCHREIBEDATUM_JAHRE:
			table = "PERSON";
			column = "";
			break;
		case ANMERKUNGEN:
			table = "PERSON";
			column = "ANMKERUNG";
			break;
		case NUMMER:
			table = "PERSON";
			column = "PERSONID";
			break;
		case SEITE_ORIGINALE:
			table = "PERSON";
			column = "SEITEORIGINAL";
			break;
		case NUMMER_HESS:
			table = "PERSON";
			column = "NUMMERHESS";
			break;
		case ANREDE_TRAD:
			table = "ANREDE_TRAD";
			column = "NAME";
			break;
		case ANREDE_NORM:
			table = "ANREDE_NORM";
			column = "NAME";
			break;
		case TITEL_TRAD:
			table = "TITEL_TRAD";
			column = "TITEL";
			break;
		case TITEL_NORM:
			table = "TITEL_NORM";
			column = "NAME";
			break;
		case VORNAME_TRAD:
			table = "VORNAME_TRAD";
			column = "NAME";
			break;
		case VORNAME_NORM:
			table = "VORNAME_NORM";
			column = "NAME";
			break;
		case NACHNAME_TRAD:
			table = "NAME_TRAD";
			column = "NAMETRAD";
			break;
		case NACHNAME_NORM:
			table = "NAME_TRAD";
			column = "NAMETRAD";
			break;
		case WIRTSCHAFTSLAGE_TRAD:
			table = "WIRTSCHAFTSLAGE_TRAD";
			column = "NAME";
			break;
		case WIRTSCHAFTSLAGE_NORM:
			table = "WIRTSCHAFTSLAGE_NORM";
			column = "NAME";
			break;
		case ORT_TRAD:
			table = "ORT_TRAD";
			column = "SCHREIBWEISE";
			break;
		case ORT_NORM:
			table = "ORT_NORM";
			column = "ORTNORM";
			break;
		case ORT_ABWEICHUNG_NORM:
			// TODO Hier muss dann auch die Anmerkung mit zurückgegeben werden.
			table = "ORT_ABWEICHUNG_NORM";
			column = "NAME";
			break;
		case FACH_TRAD:
			table = "FACH_TRAD";
			column = "NAME";
			break;
		case FACH_NORM:
			table = "FACH_NORM";
			column = "NAME";
			break;
		case FAKULTAETEN:
			table = "FAKULTAETEN";
			column = "NAME";
			break;
		case SEMINAR_TRAD:
			table = "SEMINAR_TRAD";
			column = "SCHREIBWEISE";
			break;
		case SEMINAR_NORM:
			table = "SEMINAR_NORM";
			column = "NAME";
			break;
		case GRADUIERT:
			table = "PERSON";
			column = "GRADUIERT";
			break;
		case ZUSAETZE:
			table = "ZUSAETZE";
			column = "INHALT";
			break;
		case FUNDORTE:
			table = "FUNDORTE";
			column = "NAME";
			break;
		default:
			throw new IllegalArgumentException(
					"Das zugehörige Tabellenelement für Suchfeld " + key.name()
							+ " ist nicht definiert.");
		}
		// if (key.toString().equals("ADELIG")) {
		// table = "PERSON";
		// column = "ADLIG";
		// } else if (key.toString().equals("JESUIT")) {
		// table = "PERSON";
		// column = "JESUIT";
		// } else if (key.toString().equals("STUDIENJAHR")) {
		// table = "PERSON";
		// column = "STUDIENJAHR";
		// // TODO bei von bis wird es ein Problem geben, oder?
		// } else if (key.toString().equals("STUDJAHR_VON")) {
		// table = "PERSON";
		// column = "STUDIENJAHR";
		// } else if (key.toString().equals("STUDJAHR_BIS")) {
		// table = "PERSON";
		// column = "STUDIENJAHR";
		// } else if (key.toString().equals("EINSCHREIBEDATUM_TAGE")) {
		// table = "PERSON";
		// column = "";
		// } else if (key.toString().equals("EINSCHREIBEDATUM_MONATE")) {
		// table = "PERSON";
		// column = "";
		// } else if (key.toString().equals("EINSCHREIBEDATUM_JAHRE")) {
		// table = "PERSON";
		// column = "";
		// } else if (key.toString().equals("ANMERKUNGEN")) {
		// table = "PERSON";
		// column = "ANMKERUNG";
		// } else if (key.toString().equals("NUMMER")) {
		// table = "PERSON";
		// column = "PERSONID";
		// } else if (key.toString().equals("SEITE_ORIGINALE")) {
		// table = "PERSON";
		// column = "SEITEORIGINAL";
		// } else if (key.toString().equals("NUMMER_HESS")) {
		// table = "PERSON";
		// column = "NUMMERHESS";
		// } else if (key.toString().equals("ANREDE_TRAD")) {
		// table = "ANREDE_TRAD";
		// column = "NAME";
		// } else if (key.toString().equals("ANREDE_NORM")) {
		// table = "ANREDE_NORM";
		// column = "NAME";
		// } else if (key.toString().equals("TITEL_TRAD")) {
		// table = "TITEL_TRAD";
		// column = "TITEL";
		// } else if (key.toString().equals("TITEL_NORM")) {
		// table = "TITEL_NORM";
		// column = "NAME";
		// } else if (key.toString().equals("VORNAME_TRAD")) {
		// table = "VORNAME_TRAD";
		// column = "NAME";
		// } else if (key.toString().equals("VORNAME_NORM")) {
		// table = "VORNAME_NORM";
		// column = "NAME";
		// } else if (key.toString().equals("NACHNAME_TRAD")) {
		// table = "NAME_TRAD";
		// column = "NAMETRAD";
		// } else if (key.toString().equals("NACHNAME_NORM")) {
		// table = "NAME_TRAD";
		// column = "NAMETRAD";
		// } else if (key.toString().equals("WIRTSCHAFTSLAGE_TRAD")) {
		// table = "WIRTSCHAFTSLAGE_TRAD";
		// column = "NAME";
		// } else if (key.toString().equals("WIRTSCHAFTSLAGE_NORM")) {
		// table = "WIRTSCHAFTSLAGE_NORM";
		// column = "NAME";
		// } else if (key.toString().equals("ORT_TRAD")) {
		// table = "ORT_TRAD";
		// column = "SCHREIBWEISE";
		// } else if (key.toString().equals("ORT_NORM")) {
		// table = "ORT_NORM";
		// column = "ORTNORM";
		// } else if (key.toString().equals("ORT_ABWEICHUNG_NORM")) {
		// // TODO Hier muss dann auch die Anmerkung mit zurückgegeben werden.
		// table = "ORT_ABWEICHUNG_NORM";
		// column = "NAME";
		// } else if (key.toString().equals("FACH_TRAD")) {
		// table = "FACH_TRAD";
		// column = "NAME";
		// } else if (key.toString().equals("FACH_NORM")) {
		// table = "FACH_NORM";
		// column = "NAME";
		// } else if (key.toString().equals("FAKULTAETEN")) {
		// table = "FAKULTAETEN";
		// column = "NAME";
		// } else if (key.toString().equals("SEMINAR_TRAD")) {
		// table = "SEMINAR_TRAD";
		// column = "SCHREIBWEISE";
		// } else if (key.toString().equals("SEMINAR_NORM")) {
		// table = "SEMINAR_NORM";
		// column = "NAME";
		// } else if (key.toString().equals("GRADUIERT")) {
		// table = "PERSON";
		// column = "GRADUIERT";
		// } else if (key.toString().equals("ZUSAETZE")) {
		// table = "ZUSAETZE";
		// column = "INHALT";
		// } else if (key.toString().equals("FUNDORTE")) {
		// table = "FUNDORTE";
		// column = "NAME";
		// } else {
		// throw new IllegalArgumentException(
		// "Das zugehörige Tabellenelement für Suchfeld " + key.name()
		// + " ist nicht definiert.");
		// }
	}
}
