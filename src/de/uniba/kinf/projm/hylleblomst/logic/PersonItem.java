package de.uniba.kinf.projm.hylleblomst.logic;

/**
 * PersonItem represents a person for displaying it in the TableView. Is filled
 * with data from the ResultSet, given back from the search.
 * 
 * @author ca
 *
 */
public class PersonItem {

	// these fields are default and must be named in this way
	// TODO soll das doch ein int sein?
	private String id;
	private String vorname_norm;
	private String nachname_norm;
	private String ort_norm;
	private String fakultaet_norm;
	// these fields are associated with the SearchFieldKeys, so they should be
	// named like this
	private String ANREDE;
	private String TITEL;
	private String VORNAME;
	private String NACHNAME;
	private String ADLIG;
	private String JESUIT;
	private String WIRTSCHAFTSLAGE;
	private String ORT;
	private String FACH;
	private String FAKULTAETEN;
	private String SEMINAR;
	private String GRADUIERT;
	private String STUDIENJAHR_VON;
	private String STUDIENJAHR_BIS;
	private String EINSCHREIBEDATUM_VON;
	private String EINSCHREIBEDATUM_BIS;
	private String einschreibe_jahr;
	private String einschreibe_monat;
	private String einschreibe_tag;
	private String ZUSAETZE;
	private String FUNDORTE;
	private String ANMERKUNGEN;
	private String NUMMER;
	private String SEITE_ORIGINALE;
	private String NUMMER_HESS;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
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
	 * @return the aNREDE
	 */
	public String getANREDE() {
		return ANREDE;
	}

	/**
	 * @param aNREDE
	 *            the aNREDE to set
	 */
	public void setANREDE(String aNREDE) {
		ANREDE = aNREDE;
	}

	/**
	 * @return the tITEL
	 */
	public String getTITEL() {
		return TITEL;
	}

	/**
	 * @param tITEL
	 *            the tITEL to set
	 */
	public void setTITEL(String tITEL) {
		TITEL = tITEL;
	}

	/**
	 * @return the vORNAME
	 */
	public String getVORNAME() {
		return VORNAME;
	}

	/**
	 * @param vORNAME
	 *            the vORNAME to set
	 */
	public void setVORNAME(String vORNAME) {
		VORNAME = vORNAME;
	}

	/**
	 * @return the nACHNAME
	 */
	public String getNACHNAME() {
		return NACHNAME;
	}

	/**
	 * @param nACHNAME
	 *            the nACHNAME to set
	 */
	public void setNACHNAME(String nACHNAME) {
		NACHNAME = nACHNAME;
	}

	/**
	 * @return the aDLIG
	 */
	public String getADLIG() {
		return ADLIG;
	}

	/**
	 * @param aDLIG
	 *            the aDLIG to set
	 */
	public void setADLIG(String aDLIG) {
		ADLIG = aDLIG;
	}

	/**
	 * @return the jESUIT
	 */
	public String getJESUIT() {
		return JESUIT;
	}

	/**
	 * @param jESUIT
	 *            the jESUIT to set
	 */
	public void setJESUIT(String jESUIT) {
		JESUIT = jESUIT;
	}

	/**
	 * @return the wIRTSCHAFTSLAGE
	 */
	public String getWIRTSCHAFTSLAGE() {
		return WIRTSCHAFTSLAGE;
	}

	/**
	 * @param wIRTSCHAFTSLAGE
	 *            the wIRTSCHAFTSLAGE to set
	 */
	public void setWIRTSCHAFTSLAGE(String wIRTSCHAFTSLAGE) {
		WIRTSCHAFTSLAGE = wIRTSCHAFTSLAGE;
	}

	/**
	 * @return the oRT
	 */
	public String getORT() {
		return ORT;
	}

	/**
	 * @param oRT
	 *            the oRT to set
	 */
	public void setORT(String oRT) {
		ORT = oRT;
	}

	/**
	 * @return the fACH
	 */
	public String getFACH() {
		return FACH;
	}

	/**
	 * @param fACH
	 *            the fACH to set
	 */
	public void setFACH(String fACH) {
		FACH = fACH;
	}

	/**
	 * @return the fAKULTAETEN
	 */
	public String getFAKULTAETEN() {
		return FAKULTAETEN;
	}

	/**
	 * @param fAKULTAETEN
	 *            the fAKULTAETEN to set
	 */
	public void setFAKULTAETEN(String fAKULTAETEN) {
		FAKULTAETEN = fAKULTAETEN;
	}

	/**
	 * @return the sEMINAR
	 */
	public String getSEMINAR() {
		return SEMINAR;
	}

	/**
	 * @param sEMINAR
	 *            the sEMINAR to set
	 */
	public void setSEMINAR(String sEMINAR) {
		SEMINAR = sEMINAR;
	}

	/**
	 * @return the gRADUIERT
	 */
	public String getGRADUIERT() {
		return GRADUIERT;
	}

	/**
	 * @param gRADUIERT
	 *            the gRADUIERT to set
	 */
	public void setGRADUIERT(String gRADUIERT) {
		GRADUIERT = gRADUIERT;
	}

	/**
	 * @return the sTUDIENJAHR_VON
	 */
	public String getSTUDIENJAHR_VON() {
		return STUDIENJAHR_VON;
	}

	/**
	 * @param sTUDIENJAHR_VON
	 *            the sTUDIENJAHR_VON to set
	 */
	public void setSTUDIENJAHR_VON(String sTUDIENJAHR_VON) {
		STUDIENJAHR_VON = sTUDIENJAHR_VON;
	}

	/**
	 * @return the sTUDIENJAHR_BIS
	 */
	public String getSTUDIENJAHR_BIS() {
		return STUDIENJAHR_BIS;
	}

	/**
	 * @param sTUDIENJAHR_BIS
	 *            the sTUDIENJAHR_BIS to set
	 */
	public void setSTUDIENJAHR_BIS(String sTUDIENJAHR_BIS) {
		STUDIENJAHR_BIS = sTUDIENJAHR_BIS;
	}

	/**
	 * @return the eINSCHREIBEDATUM_VON
	 */
	public String getEINSCHREIBEDATUM_VON() {
		return EINSCHREIBEDATUM_VON;
	}

	/**
	 * @param eINSCHREIBEDATUM_VON
	 *            the eINSCHREIBEDATUM_VON to set
	 */
	public void setEINSCHREIBEDATUM_VON(String eINSCHREIBEDATUM_VON) {
		EINSCHREIBEDATUM_VON = eINSCHREIBEDATUM_VON;
	}

	/**
	 * @return the eINSCHREIBEDATUM_BIS
	 */
	public String getEINSCHREIBEDATUM_BIS() {
		return EINSCHREIBEDATUM_BIS;
	}

	/**
	 * @param eINSCHREIBEDATUM_BIS
	 *            the eINSCHREIBEDATUM_BIS to set
	 */
	public void setEINSCHREIBEDATUM_BIS(String eINSCHREIBEDATUM_BIS) {
		EINSCHREIBEDATUM_BIS = eINSCHREIBEDATUM_BIS;
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
	 * @return the zUSAETZE
	 */
	public String getZUSAETZE() {
		return ZUSAETZE;
	}

	/**
	 * @param zUSAETZE
	 *            the zUSAETZE to set
	 */
	public void setZUSAETZE(String zUSAETZE) {
		ZUSAETZE = zUSAETZE;
	}

	/**
	 * @return the fUNDORTE
	 */
	public String getFUNDORTE() {
		return FUNDORTE;
	}

	/**
	 * @param fUNDORTE
	 *            the fUNDORTE to set
	 */
	public void setFUNDORTE(String fUNDORTE) {
		FUNDORTE = fUNDORTE;
	}

	/**
	 * @return the aNMERKUNGEN
	 */
	public String getANMERKUNGEN() {
		return ANMERKUNGEN;
	}

	/**
	 * @param aNMERKUNGEN
	 *            the aNMERKUNGEN to set
	 */
	public void setANMERKUNGEN(String aNMERKUNGEN) {
		ANMERKUNGEN = aNMERKUNGEN;
	}

	/**
	 * @return the nUMMER
	 */
	public String getNUMMER() {
		return NUMMER;
	}

	/**
	 * @param nUMMER
	 *            the nUMMER to set
	 */
	public void setNUMMER(String nUMMER) {
		NUMMER = nUMMER;
	}

	/**
	 * @return the sEITE_ORIGINALE
	 */
	public String getSEITE_ORIGINALE() {
		return SEITE_ORIGINALE;
	}

	/**
	 * @param sEITE_ORIGINALE
	 *            the sEITE_ORIGINALE to set
	 */
	public void setSEITE_ORIGINALE(String sEITE_ORIGINALE) {
		SEITE_ORIGINALE = sEITE_ORIGINALE;
	}

	/**
	 * @return the nUMMER_HESS
	 */
	public String getNUMMER_HESS() {
		return NUMMER_HESS;
	}

	/**
	 * @param nUMMER_HESS
	 *            the nUMMER_HESS to set
	 */
	public void setNUMMER_HESS(String nUMMER_HESS) {
		NUMMER_HESS = nUMMER_HESS;
	}
}
