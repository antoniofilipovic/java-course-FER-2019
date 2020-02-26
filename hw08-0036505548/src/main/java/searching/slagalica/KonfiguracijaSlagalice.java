package searching.slagalica;

import java.util.Arrays;

/**
 * This class represents configuration of puzzle. It holds field of integers
 * that represent current state of puzzle.
 * 
 * @author af
 *
 */
public class KonfiguracijaSlagalice {
	/**
	 * Field of int values
	 */
	private int[] polje;

	/**
	 * Public constructor
	 * 
	 * @param polje field
	 */
	public KonfiguracijaSlagalice(int[] polje) {
		super();
		this.polje = polje;
	}

	/**
	 * Getter for field
	 * 
	 * @return
	 */
	public int[] getPolje() {
		return Arrays.copyOf(polje, polje.length);
	}

	/**
	 * Index of space in field
	 * 
	 * @return
	 */
	public int indexOfSpace() {
		for (int i = 0; i < polje.length; i++) {
			if (polje[i] == 0) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (polje[i * 3 + j] == 0) {
					sb.append("*");
				} else {
					sb.append(String.valueOf(polje[i * 3 + j]));
				}
			}
			sb.append("\r\n");
		}
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(polje);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof KonfiguracijaSlagalice))
			return false;
		KonfiguracijaSlagalice other = (KonfiguracijaSlagalice) obj;
		return Arrays.equals(polje, other.polje);
	}

}
