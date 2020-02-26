package hr.fer.zemris.java.hw05.db;

/**
 * This class offers simple comparison operators defined from strategy
 * IComparisonOperator
 * 
 * @author Antonio Filipović
 *
 */

//ComparisonOperators.LIKE.satisfied("Ana", "Ana") i 
//ComparisonOperators.LIKE.satisfied("AAAA", "AAAAA*") 
//se raspadaju ili vracaju krivi rezultat.

//LESS_OR_EQUALS je mogao delegirati poziv na GREATER pa negirati vrijednost,
//i slicno za jos 2 para.
public class ComparisonOperators {
	/**
	 * Returns true if first string is alphabetically before second string
	 */
	public static final IComparisonOperator LESS = (s1, s2) -> s1.compareTo(s2) < 0;
	/**
	 * Returns true if first string is alphabetically before or equal to second
	 * string
	 */
	public static final IComparisonOperator LESS_OR_EQUALS = (s1, s2) -> s1.compareTo(s2) <= 0;
	/**
	 * Returns true if first string is alphabetically after second string
	 */
	public static final IComparisonOperator GREATER = (s1, s2) -> s1.compareTo(s2) > 0;
	/**
	 * Returns true if first string is alphabetically after or equals to second
	 * string
	 */
	public static final IComparisonOperator GREATER_OR_EQUALS = (s1, s2) -> s1.compareTo(s2) >= 0;
	/**
	 * Returns true if first string is equal to second string
	 */
	public static final IComparisonOperator EQUALS = (s1, s2) -> s1.compareTo(s2) == 0;
	/**
	 * Returns true if first and second string are not same
	 */
	public static final IComparisonOperator NOT_EQUALS = (s1, s2) -> s1.compareTo(s2) != 0;
	/**
	 * Compares two strings where second one contains exactly one wildcard
	 */
	public static final IComparisonOperator LIKE = (s1, s2) -> {
		int remainedCharsSecondString, remainedCharsFirstString = s1.length();// processedChars ->
																				// characters process up to
																				// wildcard
		char[] secondString = s2.toCharArray(); // remainedChars-> characters remained in second string after wildcard
		char[] firstString = s1.toCharArray();
		for (int i = 0; i < secondString.length; i++) {
			if (secondString[i] == '*') {
				break;
			}
			if (firstString[i] != secondString[i]) {
				return false;
			}
			remainedCharsFirstString--;
		}
		remainedCharsSecondString = s2.length() - s2.indexOf("*") - 1;

		if (remainedCharsFirstString < remainedCharsSecondString) {
			return false;
		}
		for (int i = s1.length() - remainedCharsSecondString, j = s2.length() - remainedCharsSecondString; i < s1
				.length(); i++, j++) {
			if (firstString[i] != secondString[j]) {
				return false;
			}
		}
		return true;

	};

}

/* rješenje koje radi
 * @Override
		public boolean satisfied(String value1, String value2) {
			char[] polje = value2.toCharArray();
			int duljina = polje.length, zastavica = 0;
			StringBuilder pocetak = new StringBuilder(), kraj = new StringBuilder();
			int pocetakKraja = duljina;
			for(int i = 0; i < duljina; i++) {
				if(polje[i] == '*') {
					zastavica++;
					continue;
				}
				if(zastavica == 1) {
					kraj.append(polje[i]);
				}
				else if(zastavica == 0) {
					pocetak.append(polje[i]);
				}
				else {
					return false;
				}
			}
			if(value1.length() < value2.length()-1) {
				return false;
			}
			return value1.startsWith(pocetak.toString()) && value1.endsWith(kraj.toString());
			
		}
		*/
