package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/**
 * This class extends jbutton and offers new functions for buttons like
 * inversive text
 * 
 * @author af
 *
 */
public class CalculatorButton extends JButton {

	private static final long serialVersionUID = 1L;
	/**
	 * This is inverse text
	 */
	private String inverseText;
	/**
	 * This is original text
	 */
	private String originalText;
	/**
	 * Public constructor for calc buton
	 * @param originalText orgiinal text
	 * @param font if font needs to be bigger
	 * @param action action for button
	 * @param inverseFormat inverse text
	 */
	public CalculatorButton(String originalText, boolean font, ActionListener action, String inverseFormat) {
		this.originalText = originalText;
		this.inverseText = inverseFormat;
		setText(originalText);
		setBackground(Color.MAGENTA);
		if (font) {
			setFont(getFont().deriveFont(30f));
		}
		setOpaque(true);
		addActionListener(action);

	}
	/**
	 * If button has inverse
	 * @return
	 */
	public boolean hasInverse() {
		return !(inverseText == null);
	}
	/**
	 * Original text
	 * @return
	 */
	public String originalText() {
		return originalText;
	}
	/**
	 * Inverse text
	 * @return
	 */
	public String inverseText() {
		return inverseText;
	}

}
