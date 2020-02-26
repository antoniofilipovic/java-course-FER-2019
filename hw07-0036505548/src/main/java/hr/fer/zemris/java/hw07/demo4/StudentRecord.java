package hr.fer.zemris.java.hw07.demo4;

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
	 * Points on mid term exam
	 */
	private double midTermPoints;
	/**
	 * Points on final term exam
	 */
	private double finalTermPoints;
	/**
	 * Points on laboratory exercises
	 */
	private double laboratoryExercisePoints;

	/**
	 * This is public constructor for student record
	 * 
	 * @param jmbag                    of student
	 * @param lastName                 of student
	 * @param firstName                of student
	 * @param finalGrade               of student
	 * @param midTermExamPoints        that student got on mid term
	 * @param finalTermExamPoints      that student got on final term
	 * @param laboratoryExercisePoints that student got from laboratory exercises
	 */

	public StudentRecord(String jmbag, String lastName, String firstName, double midTermPoints,
			double finalTermPoints, double laboratoryExercisePoints, int finalGrade) {
		super();
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.finalGrade = finalGrade;
		this.midTermPoints = midTermPoints;
		this.finalTermPoints = finalTermPoints;
		this.laboratoryExercisePoints = laboratoryExercisePoints;
	}

	/**
	 * Getter for jmbag
	 * 
	 * @return jmbag
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * getter for last name
	 * 
	 * @return last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * getter for first name
	 * 
	 * @return first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * getter for final grade
	 * 
	 * @return points
	 */
	public int getFinalGrade() {
		return finalGrade;
	}

	/**
	 * getter for midterm points
	 * 
	 * @return points
	 */
	public double getMidTermPoints() {
		return midTermPoints;
	}

	/**
	 * getter for final term points
	 * 
	 * @return points
	 */
	public double getFinalTermPoints() {
		return finalTermPoints;
	}

	/**
	 * getter for points on laboratory exercises
	 * 
	 * @return points
	 */
	public double getLaboratoryExercisePoints() {
		return laboratoryExercisePoints;
	}
	
	@Override
    public String toString() {
        return jmbag + '\t'
        		+lastName + '\t' +
                firstName + '\t' +
                midTermPoints + '\t'+
                finalTermPoints + '\t'+
                laboratoryExercisePoints + '\t'+
                finalGrade+"\r\n" ;
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
