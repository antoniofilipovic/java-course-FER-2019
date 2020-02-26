package hr.fer.zemris.java.hw17.jvdraw.provider;

import java.awt.Color;

import hr.fer.zemris.java.hw17.jvdraw.listener.ColorChangeListener;

/**
 * This class represents icolorprovider
 * 
 * @author af
 *
 */
public interface IColorProvider {
	/**
	 * Getter of curent color
	 * 
	 * @return
	 */
	Color getCurrentColor();

	/**
	 * Adds change listener
	 * 
	 * @param l
	 */
	void addColorChangeListener(ColorChangeListener l);

	/**
	 * Removes chagne listenre
	 * 
	 * @param l
	 */
	void removeColorChangeListener(ColorChangeListener l);
}
