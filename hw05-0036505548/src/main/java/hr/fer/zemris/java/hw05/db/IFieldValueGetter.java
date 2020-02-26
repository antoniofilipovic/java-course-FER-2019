package hr.fer.zemris.java.hw05.db;

/**
 * Retrieves field from Student Record
 * 
 * @author Antonio Filipović
 *
 */
public interface IFieldValueGetter {
	/**
	 * For Student record retrieves valid field
	 * 
	 * @param record to retrieve field from
	 * @return String
	 */
	public String get(StudentRecord record);
}
