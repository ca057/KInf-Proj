package testing.johannesTest;

public class Person {
	private String[] variables;
	private String id;
	private String firstName;
	private String surname;
	private String status;
	private String pageOrig;
	private String numHess;
	private String form;
	private String formN;
	private String firstNameN;
	private String firstNameHSBAUBI11;
	private String firstNameHSCAUBI131;
	private String firstNameHSCAUBI132;
	private String firstNameHSCAUBI9;
	private String firstNameHSCAUBI8;
	private String firstNameHSCAUBI6;
	
	public Person (String id, String firstName, String surname, String status) {
		this.id = id;
		this.firstName = firstName;
		this.surname = surname;
		this.status = status;
	}
	
	public Person() {
		
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public void setLastName(String surname) {
		this.surname = surname;
	}
	
	public String toString() {
		// return "Person: " + firstName + " " + surname + ", " + status + ", Nummer: " + id;
		String tmp;
		return "Nummer: " + id + " Seite Original: " + pageOrig + " Nummer(Hess): " + numHess + " Anrede: " + form + " Anrede normalisiert: " + formN + " Vorname: " + firstName + " Vorname normalisiert: " + firstNameN;
	}
	
	public Person setAll(String[] row) {
		
		id = row[0];
		pageOrig = row[1];
		numHess = row [2];
		form = row[3];
		formN = row[4];
		firstName = row[5];
		firstNameN = row[6];
		firstNameHSBAUBI11 = row[7];
		firstNameHSCAUBI131 = row[8];
		firstNameHSCAUBI132 = row[9];
		firstNameHSCAUBI9 = row[10];
		firstNameHSCAUBI8 = row[11];
		firstNameHSCAUBI6 = row[12];
		
		// "VORNAME HS H (AEB, Rep. I, Nr. 321)","VORNAME HS I (SB Bamberg, Msc.Add.3a)","VORNAME HS J (SB Bamberg, Msc.Add.3)","VORNAME AUB, V E 38",ADELIGER,NACHNAME,"NACHNAME HS B (AUB, I 11)","NACHNAME HS C (AUB, I 13/1)","NACHNAME HS D (AUB, I 13/2)","NACHNAME HS E (AUB, I 9)","NACHNAME HS F (AUB, I 8)","NACHNAME HS G (AUB, I 6)","NACHNAME HS H (AEB, Rep. I, Nr. 321)","NACHNAME HS I (SB Bamberg, Msc.Add.3a)","NACHNAME HS J (SB Bamberg, Msc.Add.3)","NACHNAME AUB, V E 38",JESUIT,ORT,"ORT HS B (AUB, I 11)","ORT HS C (AUB, I 13/1)","ORT HS D (AUB, I 13/2)","ORT HS E (AUB, I 9)","ORT HS F (AUB, I 8)","ORT HS G (AUB, I 6)","ORT HS H (AEB, Rep. I, Nr. 321)","ORT HS I (SB Bamberg, Msc.Add.3a)","ORT HS J (SB Bamberg, Msc.Add.3)","ORT AUB, V E 38",ORT_NORMAL,ORT_ABWEICH_NORMAL,TAG_NORMAL,MONAT_NORMAL,JAHR_NORMAL,STUDIENJAHR_NORMAL,FAKULTAET_NORMAL,STUDIENFACH,STUDIENFACH_NORMAL,"STUDIENFACH HS B (AUB, I 11)",WIRT_LAGE,WIRT_LAGE_NORMAL,"WIRT_LAGE HS B (AUB, I 11)","WIRT_LAGE HS D (AUB, I 13/2)","WIRT_LAGE HS J (SB Bamberg, Msc.Add.3)",SEMINAR,SEMINAR_NORM,"SEMINAR HS B (AUB, I 11)","SEMINAR HS D (AUB, I 13/2)","SEMINAR HS E (AUB, I 9)","SEMINAR HS I (SB Bamberg, Msc.Add.3a)","SEMINAR HS J (SB Bamberg, Msc.Add.3)",GRADUIERTER,TITEL,TITEL_NORMAL,ZUSAETZE,"ZUSAETZE HS B (AUB, I 11)","ZUSAETZE HS C (AUB, I 13/1)","ZUSAETZE HS D (AUB, I 13/2)","ZUSAETZE HS E (AUB, I 9)","ZUSAETZE HS F (AUB, I 8)","ZUSAETZE HS G (AUB, I 6)","ZUSAETZE HS H (AEB, Rep. I, Nr. 321)","ZUSAETZE HS I (SB Bamberg, Msc.Add.3a)","ZUSAETZE HS J (SB Bamberg, Msc.Add.3)","ZUSAETZE AUB, V E 38",FUNDORT,ANMERKUNGEN
		return this;
	}
	
	
}
