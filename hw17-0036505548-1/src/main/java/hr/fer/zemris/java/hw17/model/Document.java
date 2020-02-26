package hr.fer.zemris.java.hw17.model;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents document
 * 
 * @author af
 *
 */
public class Document {
	/**
	 * Filepath
	 */

	private Path filePath;
	/**
	 * Tf vector
	 */
	private Vector tf;
	/**
	 * Tfidmap
	 */

	private Map<String, Double> tfidfMap;

	/**
	 * Public constructor for docuent
	 * 
	 * @param filePath filepath
	 * @param tf       tf vector
	 */

	public Document(Path filePath, Vector tf) {
		super();
		this.filePath = filePath;
		this.tf = tf;
	}

	/**
	 * Get file path
	 * 
	 * @return
	 */
	public Path getFilePath() {
		return filePath;
	}

	/**
	 * Set file path
	 * 
	 * @param filePath
	 */
	public void setFilePath(Path filePath) {
		this.filePath = filePath;
	}

	/**
	 * Get tf idf
	 * 
	 * @return
	 */
	public Vector getTfidf() {
		return tf;
	}

	/**
	 * Setter for tfidf
	 * 
	 * @param tf
	 */
	public void setTfidf(Vector tf) {
		this.tf = tf;
	}

	/**
	 * Creator of tfdif
	 * 
	 * @param idfVector         idfvector
	 * @param numberOfDocuments number of docs
	 */
	public void createTfidf(Vector idfVector, int numberOfDocuments) {
		Map<String, Integer> idfMap = idfVector.getValues();
		Map<String, Double> tfidfMap = new HashMap<>();
		Map<String, Integer> tfMap = tf.getValues();
		idfMap.forEach((k, v) -> tfidfMap.put(k, tfMap.get(k) * Math.log(numberOfDocuments * 1.0 / v)));

		this.tfidfMap = tfidfMap;

	}
	/**
	 * Getter for tf
	 * @return
	 */
	public Vector getTf() {
		return tf;
	}
	/**
	 * Getter for tf map
	 * @return
	 */
	public Map<String, Double> getTfidfMap() {
		return tfidfMap;
	}

}
