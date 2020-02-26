package hr.fer.zemris.java.hw05.db;

/**
 * Filters student records
 * 
 * @author Antonio Filipovic
 *
 */
public interface IFilter {
	/**
	 * Returns true if record is accepted
	 * 
	 * @param record that will be tested
	 * @return true if accepted
	 */
	public boolean accepts(StudentRecord record);
}
