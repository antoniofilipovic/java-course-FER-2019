package hr.fer.zemris.java.hw05.db;

import java.util.Objects;

/**
 * This class presents Student Record which constists of jmbag,firstname,
 * lastName and jmbag
 * 
 * @author Antonio
 *
 */
public class StudentRecord {
	/**
	 * Private variable representing jmbag of student, different for every student
	 */
	private String jmbag;
	/**
	 * Students last name
	 */
	private String lastName;
	/**
	 * Students first name
	 */
	private String firstName;
	/**
	 * Students final grade, can be from 1 to 5
	 */
	private int finalGrade;
	/**
	 * Constructor for Student Record
	 * @param jmbag of student
	 * @param lastName of student
	 * @param firstName of student
	 * @param finalGrade of studen 
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, int finalGrade) {
		super();
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.finalGrade = finalGrade;
	}
	/**
	 * Getter for jmbag
	 * @return jmbag
	 */
	public String getJmbag() {
		return jmbag;
	}
	/**
	 * Getter for last name
	 * @return last name
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * Getter for first name
	 * @return first name
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * Getter for grade
	 * @return grade
	 */
	public int getFinalGrade() {
		return finalGrade;
	}
	
	@Override
	public int hashCode() {
		return jmbag.hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof StudentRecord))
			return false;
		StudentRecord other = (StudentRecord) obj;
		return jmbag.equals(other.jmbag);
	}
	
	

}
