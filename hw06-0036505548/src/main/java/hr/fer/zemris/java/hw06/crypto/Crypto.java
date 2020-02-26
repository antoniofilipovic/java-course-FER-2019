package hr.fer.zemris.java.hw06.crypto;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * This class allows user to encrypt/decrypt given file using the AES
 * cryptoalgorithm and the 128-bit encryption key or calculate and check the
 * SHA-256 file digest
 * 
 * @author Antonio Filipović
 *
 */
public class Crypto {

	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("Wrong number of arguments.");
			return;
		}
		String keyWord = args[0];
		switch (keyWord) {
		case "checksha":
			if (args.length != 2) {
				System.out.println("Wrong number of arguments for checksha.");
			}
			messageDigest(args[1]);
			break;

		case "encrypt":
		case "decrypt":
			if (args.length != 3) {
				System.out.println("Wrong number of arguments for encrypt/decrypt.");
			}
			initializeEncrypt(args[0], args[1], args[2]);

			break;
		default:
			System.out.println("Unknown command");
			return;
		}

	}

	/**
	 * This method calculates a fixed-size binary digest from from arbitrary long
	 * data. For same string it always returns same digest. It receives path to file
	 * from which it will calculate binary digest. It creates instance of message
	 * digest using instance SHA-256.
	 * 
	 * @param path to file from which digest will be calculated
	 */

	private static void messageDigest(String path) {

		System.out.printf("Please provide expected sha-256 digest for " + path + ":%n>");

		try (InputStream is = Files.newInputStream(Paths.get(path)); Scanner sc = new Scanner(System.in)) {

			String expectedHex = sc.next().trim();

			MessageDigest sha = MessageDigest.getInstance("SHA-256");

			byte[] buff = new byte[4096];
			while (true) {
				int r = is.read(buff);
				if (r < 1)
					break;
				sha.update(buff, 0, r);
			}
			byte[] result = sha.digest();
			String resultHex = Util.bytetohex(result);
			StringBuilder sb = new StringBuilder();
			sb.append("Digesting completed. Digest of " + path + " ");

			if (resultHex.equals(expectedHex)) {
				sb.append(" matches expected digest.");
			} else {
				sb.append("does not match the expected digest. Digest was: ").append(resultHex);
			}

			System.out.println(sb.toString());

		} catch (IOException | NoSuchAlgorithmException | InvalidPathException e) {
			System.out.println("Problem occured while digesting.");
			System.exit(-1);
		}

	}

	/**
	 * This method is used for initializing encryption/decryption. It receives path
	 * for reading/writing as string and {@link keyword} that defines if we are
	 * doing encryption or decryption. It also writes to command line asking user to
	 * give password for encoded text and initialization vector. Neither of them are
	 * checked if user provided good hex password or vector.
	 * 
	 * @param keyWord defines whether it is decrypting or encrypting
	 * @param read    string of path to read from
	 * @param write   string of path to write to
	 */
	private static void initializeEncrypt(String keyWord, String read, String write) {
		Path readFile = null;
		Path writeFile = null;
		boolean encrypt = keyWord.equals("encrypt");
		try {
			readFile = Paths.get(read);
			writeFile = Paths.get(write);
		} catch (InvalidPathException e) {
			System.out.println("Invalide path for reading or writing.");
			System.exit(-1);
		}

		try (Scanner sc = new Scanner(System.in)) {
			System.out.printf("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):%n>");
			String password = sc.next().trim();
			System.out.printf("Please provide initialization vector as hex-encoded text (32 hex-digits):%n>");
			String initializationVector = sc.next().trim();

			encryptDecrypt(password, initializationVector, encrypt, readFile, writeFile);
		}

	}

	

	/**
	 * This private method is used for encrypting or decrypting text from
	 * {@link readFile}. It creates new file {@link writeFile} and if boolean
	 * {@link encrypt} is true, it encrypts text, otherwise it decrypts it.
	 * 
	 * @param keyText   key for encrypt/decrypt
	 * @param ivText    initialization vector for encrypt/decrypt
	 * @param encrypt   boolean that defines whether we are doing decrypt/encrypt
	 * @param readFile  file to read
	 * @param writeFile file to create
	 */
	private static void encryptDecrypt(String keyText, String ivText, boolean encrypt, Path readFile, Path writeFile) {
		if (keyText == null || ivText == null) { // jel moramo provjerit da oboje nije null
			throw new NullPointerException("Password and vector must be non null values.");
		}

		Cipher cipher = null;
		try {
			SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
				| InvalidAlgorithmParameterException | IllegalArgumentException e) {
			System.out.println("Exception occured while creating cipher.");
			System.exit(-1);
		}

		try (InputStream is = Files.newInputStream(readFile);
				OutputStream os = Files.newOutputStream(writeFile, StandardOpenOption.CREATE)) {

			byte[] buff = new byte[4096];
			while (true) {
				int r = is.read(buff);
				if (r < 1)
					break;
				os.write(cipher.update(buff, 0, r));
			}

			os.write(cipher.doFinal());
			StringBuilder sb = new StringBuilder();
			if (encrypt) {
				sb.append("Encryption completed");
			} else {
				sb.append("Decryption completed.");

			}
			sb.append("Generated file " + writeFile + " based on file " + readFile.getFileName() + ".");
			System.out.println(sb.toString());

		} catch (IOException | IllegalBlockSizeException | BadPaddingException e) {
			System.out.println("Exception while doing encryption/decryption");
		}

	}

}
