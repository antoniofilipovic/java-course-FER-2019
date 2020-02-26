package hr.fer.zemris.java.hw06.crypto;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is used for converting from hex text to byte array and other way
 * around.
 * 
 * @author Antonio Filipovic
 *
 */

public class Util {
	/**
	 * Constant representing 1
	 */
	private static final int ONE = 1;
	/**
	 * Constant
	 */
	private static final int TEN = 10;
	/**
	 * Max hexadecimal number
	 */
	private static final int MAX_HEX = 16;
	/**
	 * Ascii value of 'a' char
	 */
	private static final int ASCII_VALUE = (int) 'a';
	/**
	 * Hashmap of values
	 */
	private static final Map<String, Integer> VALUES = new HashMap<>();

	static {
		for (int i = 0; i < MAX_HEX; i++) {
			if (i >= 10) {
				VALUES.put(Character.toString((char) (ASCII_VALUE - TEN + i)), i);
			} else {
				VALUES.put(String.valueOf(i), i);
			}

		}
	}

	/**
	 * This method receives text in hex format and returns byte array. It supports
	 * both upper and lower case letters. If it receives empty it returns zero
	 * length byte array.
	 * 
	 * @param keyText text that should be parsed
	 * @return byte array of text
	 * @throws IllegalArgumentException if text contains invalid letters or its
	 *                                  length is invalid
	 */
	public static byte[] hextobyte(String keyText) {
		if (keyText.length() % 2 == ONE) {
			throw new IllegalArgumentException("Strings length is invalid");
		}
		byte[] array = new byte[keyText.length() / 2];
		keyText = keyText.toLowerCase();
		for (int i = 0; i < keyText.length() / 2; i++) {
			Integer firstValue = VALUES.get(String.valueOf(keyText.charAt(i * 2)));
			Integer secondValue = VALUES.get(String.valueOf(keyText.charAt(2 * i + 1)));
			if (firstValue == null || secondValue == null) {
				throw new IllegalArgumentException("String contains illegal values.");
			}
			array[i] = (byte) (firstValue * MAX_HEX + secondValue);

		}
		return array;
	}

	/**
	 * This method is used to convert from byte array to its hex-encoding in big
	 * endian notation. It returns letters that are lowercase. For zero length array
	 * it returns empty string.
	 * 
	 * @param bytearray
	 * @return
	 */
	public static String bytetohex(byte[] bytearray) {
		char[] hexCharacters=new char[bytearray.length*2];
		char[] hexArray="0123456789abcdef".toCharArray();
		for (int i=0;i<bytearray.length;i++) {
			int temp=bytearray[i] &0xff;
			hexCharacters[i*2]=hexArray[temp>>4];
			hexCharacters[i*2+1]=hexArray[temp&0x0f];
		}
		return new String(hexCharacters);
	}

}
