package testing.johannesTest;

public class Person {
	private String id;
	private String firstName;
	private String surname;
	private String status;
	
	public Person (String id, String firstName, String surname, String status) {
		this.id = id;
		this.firstName = firstName;
		this.surname = surname;
		this.status = status;
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
		return "Person: " + firstName + " " + surname + ", " + status + "Nummer: " + id;
	}
	
	
	
}
