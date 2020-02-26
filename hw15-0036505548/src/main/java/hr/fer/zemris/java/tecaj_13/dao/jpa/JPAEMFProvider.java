package hr.fer.zemris.java.tecaj_13.dao.jpa;

import javax.persistence.EntityManagerFactory;

/**
 * This class represents entity manager factor
 * 
 * @author af
 *
 */
public class JPAEMFProvider {
	/**
	 * Entity manager
	 */
	public static EntityManagerFactory emf;

	/**
	 * Getter for manager factory
	 * 
	 * @return emf
	 */
	public static EntityManagerFactory getEmf() {
		return emf;
	}

	/**
	 * Setter for entity manager
	 * 
	 * @param emf
	 */
	public static void setEmf(EntityManagerFactory emf) {
		JPAEMFProvider.emf = emf;
	}
}