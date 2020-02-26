package hr.fer.zemris.java.hw17.jvdraw.listener;

import java.awt.Color;

import hr.fer.zemris.java.hw17.jvdraw.provider.IColorProvider;
/**
 * Listener for change color
 * @author af
 *
 */
public interface ColorChangeListener {
	/**
	 * new color selected 
	 * @param source source
	 * @param oldColor old color
	 * @param newColor new color
	 */
	void newColorSelected(IColorProvider source, Color oldColor, Color newColor);


}
