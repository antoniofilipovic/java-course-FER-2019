package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.Set;

/**
 * This class creates student database
 * 
 * @author Antonio
 *
 */
public class StudentDatabase {
	/**
	 * Represents list of strings from received from text file
	 */
	List<String> database;

	List<StudentRecord> allStudents = new ArrayList<>();
	/**
	 * Map of students
	 */
	Map<String, StudentRecord> students;
	/**
	 * Lowest grade
	 */
	private static final int MIN_GRADE = 1;
	/**
	 * Highest grade
	 */
	private static final int MAX_GRADE = 5;

	/**
	 * Public constructor for StudentDatabase
	 * 
	 * @param database list of strings
	 */
	public StudentDatabase(List<String> database) {
		super();
		this.database = database;
		students = new LinkedHashMap<>();
		addStudents();
	}

	/**
	 * Returns student record for given jmbag
	 * 
	 * @param jmbag for which it returns Student Record
	 * @return Student record
	 */
	public StudentRecord forJMBAG(String jmbag) {
		return students.get(jmbag);
	}

	/**
	 * Returns List of students record that pass filter
	 * 
	 * @param filter returns true for records that are good
	 * @return List<StudentRecord>
	 */
	public List<StudentRecord> filter(IFilter filter) {
		return allStudents.stream().filter(r->filter.accepts(r)).collect(Collectors.toList());
	}

	/**
	 * Adds students to map
	 * 
	 * @throws IllegalArgumentException if students grade is not valid
	 */
	private void addStudents() {
		for (String s : database) {
			String[] studentParsed = s.split("\t");
			int grade=1;
			try {
				grade = Integer.parseInt(studentParsed[3]);
				if (grade < MIN_GRADE || grade > MAX_GRADE) {
					System.out.println("Error.This student" + s + " " + "has invalid grade.");
					System.exit(-1);
				}
			} catch (NumberFormatException e) {
				System.out.println("Error.This student" + s + " " + "has invalid grade.");
				System.exit(-1);
			}
			StudentRecord r = new StudentRecord(studentParsed[0], studentParsed[1], studentParsed[2], grade);
			Object value = students.put(studentParsed[0], r);
			if (value != null) {
				System.out.println("Error.This student" + s + " " + "was already added.");
				System.exit(-1);
				
			}

		}
	}

}
