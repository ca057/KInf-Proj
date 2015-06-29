package de.uniba.kinf.projm.hylleblomst.logic;

/**
 * PersonItem represents a person for displaying it in the TableView. Is filled
 * with data from the ResultSet, given back from the search.
 * 
 * @author ca
 *
 */
public class PersonItem {

	// TODO ist das wirklich ein int
	private int id;
	private String anrede;

	private String titel;

	private String vorname_norm;
	private String vorname_trad;
	private String nachname_norm;
	private String nachname_trad;
	private String adlig;
	private String jesuit;
	private String wirtschaftslage;
	private String ort_norm;
	private String ort_trad;
	private String fach;
	private String fakultaet_norm;
	private String fakultaet_trad;
	private String seminar;
	private String graduiert;
	private String studienjahr_von;
	private String studienjahr_bis;
	private String einschreibe_datum_von;
	private String einschreibe_datum_bis;
	private String einschreibe_jahr;
	private String einschreibe_monat;
	private String einschreibe_tag;
	private String zusaetze;
	private String anmerkungen;
	private String nummer;
	private String seite_original;
	private String nummer_hess;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the anrede
	 */
	public String getAnrede() {
		return anrede;
	}

	/**
	 * @param anrede
	 *            the anrede to set
	 */
	public void setAnrede(String anrede) {
		this.anrede = anrede;
	}

	/**
	 * @return the titel
	 */
	public String getTitel() {
		return titel;
	}

	/**
	 * @param titel
	 *            the titel to set
	 */
	public void setTitel(String titel) {
		this.titel = titel;
	}

	/**
	 * @return the vorname_norm
	 */
	public String getVorname_norm() {
		return vorname_norm;
	}

	/**
	 * @param vorname_norm
	 *            the vorname_norm to set
	 */
	public void setVorname_norm(String vorname_norm) {
		this.vorname_norm = vorname_norm;
	}

	/**
	 * @return the vorname_trad
	 */
	public String getVorname_trad() {
		return vorname_trad;
	}

	/**
	 * @param vorname_trad
	 *            the vorname_trad to set
	 */
	public void setVorname_trad(String vorname_trad) {
		this.vorname_trad = vorname_trad;
	}

	/**
	 * @return the nachname_norm
	 */
	public String getNachname_norm() {
		return nachname_norm;
	}

	/**
	 * @param nachname_norm
	 *            the nachname_norm to set
	 */
	public void setNachname_norm(String nachname_norm) {
		this.nachname_norm = nachname_norm;
	}

	/**
	 * @return the nachname_trad
	 */
	public String getNachname_trad() {
		return nachname_trad;
	}

	/**
	 * @param nachname_trad
	 *            the nachname_trad to set
	 */
	public void setNachname_trad(String nachname_trad) {
		this.nachname_trad = nachname_trad;
	}

	/**
	 * @return the adlig
	 */
	public String getAdlig() {
		return adlig;
	}

	/**
	 * @param adlig
	 *            the adlig to set
	 */
	public void setAdlig(String adlig) {
		this.adlig = adlig;
	}

	/**
	 * @return the jesuit
	 */
	public String getJesuit() {
		return jesuit;
	}

	/**
	 * @param jesuit
	 *            the jesuit to set
	 */
	public void setJesuit(String jesuit) {
		this.jesuit = jesuit;
	}

	/**
	 * @return the wirtschaftslage
	 */
	public String getWirtschaftslage() {
		return wirtschaftslage;
	}

	/**
	 * @param wirtschaftslage
	 *            the wirtschaftslage to set
	 */
	public void setWirtschaftslage(String wirtschaftslage) {
		this.wirtschaftslage = wirtschaftslage;
	}

	/**
	 * @return the ort_norm
	 */
	public String getOrt_norm() {
		return ort_norm;
	}

	/**
	 * @param ort_norm
	 *            the ort_norm to set
	 */
	public void setOrt_norm(String ort_norm) {
		this.ort_norm = ort_norm;
	}

	/**
	 * @return the ort_trad
	 */
	public String getOrt_trad() {
		return ort_trad;
	}

	/**
	 * @param ort_trad
	 *            the ort_trad to set
	 */
	public void setOrt_trad(String ort_trad) {
		this.ort_trad = ort_trad;
	}

	/**
	 * @return the fach
	 */
	public String getFach() {
		return fach;
	}

	/**
	 * @param fach
	 *            the fach to set
	 */
	public void setFach(String fach) {
		this.fach = fach;
	}

	/**
	 * @return the fakultaet_norm
	 */
	public String getFakultaet_norm() {
		return fakultaet_norm;
	}

	/**
	 * @param fakultaet_norm
	 *            the fakultaet_norm to set
	 */
	public void setFakultaet_norm(String fakultaet_norm) {
		this.fakultaet_norm = fakultaet_norm;
	}

	/**
	 * @return the fakultaet_trad
	 */
	public String getFakultaet_trad() {
		return fakultaet_trad;
	}

	/**
	 * @param fakultaet_trad
	 *            the fakultaet_trad to set
	 */
	public void setFakultaet_trad(String fakultaet_trad) {
		this.fakultaet_trad = fakultaet_trad;
	}

	/**
	 * @return the seminar
	 */
	public String getSeminar() {
		return seminar;
	}

	/**
	 * @param seminar
	 *            the seminar to set
	 */
	public void setSeminar(String seminar) {
		this.seminar = seminar;
	}

	/**
	 * @return the graduiert
	 */
	public String getGraduiert() {
		return graduiert;
	}

	/**
	 * @param graduiert
	 *            the graduiert to set
	 */
	public void setGraduiert(String graduiert) {
		this.graduiert = graduiert;
	}

	/**
	 * @return the studienjahr_von
	 */
	public String getStudienjahr_von() {
		return studienjahr_von;
	}

	/**
	 * @param studienjahr_von
	 *            the studienjahr_von to set
	 */
	public void setStudienjahr_von(String studienjahr_von) {
		this.studienjahr_von = studienjahr_von;
	}

	/**
	 * @return the studienjahr_bis
	 */
	public String getStudienjahr_bis() {
		return studienjahr_bis;
	}

	/**
	 * @param studienjahr_bis
	 *            the studienjahr_bis to set
	 */
	public void setStudienjahr_bis(String studienjahr_bis) {
		this.studienjahr_bis = studienjahr_bis;
	}

	/**
	 * @return the einschreibe_datum_von
	 */
	public String getEinschreibe_datum_von() {
		return einschreibe_datum_von;
	}

	/**
	 * @param einschreibe_datum_von
	 *            the einschreibe_datum_von to set
	 */
	public void setEinschreibe_datum_von(String einschreibe_datum_von) {
		this.einschreibe_datum_von = einschreibe_datum_von;
	}

	/**
	 * @return the einschreibe_datum_bis
	 */
	public String getEinschreibe_datum_bis() {
		return einschreibe_datum_bis;
	}

	/**
	 * @param einschreibe_datum_bis
	 *            the einschreibe_datum_bis to set
	 */
	public void setEinschreibe_datum_bis(String einschreibe_datum_bis) {
		this.einschreibe_datum_bis = einschreibe_datum_bis;
	}

	/**
	 * @return the einschreibe_jahr
	 */
	public String getEinschreibe_jahr() {
		return einschreibe_jahr;
	}

	/**
	 * @param einschreibe_jahr
	 *            the einschreibe_jahr to set
	 */
	public void setEinschreibe_jahr(String einschreibe_jahr) {
		this.einschreibe_jahr = einschreibe_jahr;
	}

	/**
	 * @return the einschreibe_monat
	 */
	public String getEinschreibe_monat() {
		return einschreibe_monat;
	}

	/**
	 * @param einschreibe_monat
	 *            the einschreibe_monat to set
	 */
	public void setEinschreibe_monat(String einschreibe_monat) {
		this.einschreibe_monat = einschreibe_monat;
	}

	/**
	 * @return the einschreibe_tag
	 */
	public String getEinschreibe_tag() {
		return einschreibe_tag;
	}

	/**
	 * @param einschreibe_tag
	 *            the einschreibe_tag to set
	 */
	public void setEinschreibe_tag(String einschreibe_tag) {
		this.einschreibe_tag = einschreibe_tag;
	}

	/**
	 * @return the zusaetze
	 */
	public String getZusaetze() {
		return zusaetze;
	}

	/**
	 * @param zusaetze
	 *            the zusaetze to set
	 */
	public void setZusaetze(String zusaetze) {
		this.zusaetze = zusaetze;
	}

	/**
	 * @return the anmerkungen
	 */
	public String getAnmerkungen() {
		return anmerkungen;
	}

	/**
	 * @param anmerkungen
	 *            the anmerkungen to set
	 */
	public void setAnmerkungen(String anmerkungen) {
		this.anmerkungen = anmerkungen;
	}

	/**
	 * @return the nummer
	 */
	public String getNummer() {
		return nummer;
	}

	/**
	 * @param nummer
	 *            the nummer to set
	 */
	public void setNummer(String nummer) {
		this.nummer = nummer;
	}

	/**
	 * @return the seite_original
	 */
	public String getSeite_original() {
		return seite_original;
	}

	/**
	 * @param seite_original
	 *            the seite_original to set
	 */
	public void setSeite_original(String seite_original) {
		this.seite_original = seite_original;
	}

	/**
	 * @return the nummer_hess
	 */
	public String getNummer_hess() {
		return nummer_hess;
	}

	/**
	 * @param nummer_hess
	 *            the nummer_hess to set
	 */
	public void setNummer_hess(String nummer_hess) {
		this.nummer_hess = nummer_hess;
	}

}
