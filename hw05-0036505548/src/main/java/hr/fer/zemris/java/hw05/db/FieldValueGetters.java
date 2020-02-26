package hr.fer.zemris.java.hw05.db;

/**
 * Class offers static final variables that retreive field from student record
 * 
 * @author Antonio Filipovic
 *
 */
public class FieldValueGetters {
	/**
	 * Retrieves first name from student record
	 */
	public static final IFieldValueGetter FIRST_NAME = r -> r.getFirstName();
	/**
	 * Retrieves last name from student
	 */
	public static final IFieldValueGetter LAST_NAME = r -> r.getLastName();
	/**
	 * Retrieves jmbag from student
	 */
	public static final IFieldValueGetter JMBAG = r -> r.getJmbag();

}
