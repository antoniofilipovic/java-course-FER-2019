package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * This class represents image loaded
 * 
 * @author af
 *
 */
public class ImageLoader {
	/**
	 * Returns icon for given path
	 * 
	 * @param text path
	 * @return icon
	 */
	public static Icon getIconImage(String text) {

		byte[] bytes = null;
		try (InputStream is = JNotepadPP.class.getResourceAsStream("icons/" + text)) {
			if (is == null) {
				throw new IllegalArgumentException("Invalid path");
			}
			bytes = is.readAllBytes();
		} catch (IOException | OutOfMemoryError e) {
			System.out.println(bytes);
			throw new IllegalArgumentException("Error while reading icon bytes." + e.getMessage());

		}
		ImageIcon icon = new ImageIcon(bytes);
		Image image = icon.getImage();
		Image newimage = image.getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH);
		
		return new ImageIcon(newimage);

	}
}
