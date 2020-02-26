package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.Color;

import javax.swing.JLabel;

import hr.fer.zemris.java.hw17.jvdraw.listener.ColorChangeListener;
import hr.fer.zemris.java.hw17.jvdraw.provider.IColorProvider;

/**
 * This class represets status label
 * 
 * @author af
 *
 */
public class JStatusLabel extends JLabel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * foreground provider
	 */
	private IColorProvider fgColorProvider;
	/**
	 * bgcolor provider
	 */
	private IColorProvider bgColorProvider;
	/**
	 * Foreground text
	 */
	private String foregroundText;
	/**
	 * Background text
	 */
	private String backgroundText;

	/**
	 * Public constructor
	 * 
	 * @param fgColorProvider fg color provider
	 * @param bgColorProvider bgcolor provider
	 */
	public JStatusLabel(IColorProvider fgColorProvider, IColorProvider bgColorProvider) {
		this.fgColorProvider = fgColorProvider;
		this.bgColorProvider = bgColorProvider;

		this.fgColorProvider.addColorChangeListener(new ColorChangeListener() {

			@Override
			public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
				setForegroundText(newColor);

			}
		});
		this.bgColorProvider.addColorChangeListener(new ColorChangeListener() {

			@Override
			public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
				setBackgroundText(newColor);

			}
		});
		setForegroundText(fgColorProvider.getCurrentColor());
		setBackgroundText(bgColorProvider.getCurrentColor());

	}
	/**
	 * Setter for foreground color
	 * @param newColor
	 */
	public void setForegroundText(Color newColor) {
		foregroundText = "Foreground color: (" + newColor.getRed() + "," + newColor.getGreen() + ","
				+ newColor.getBlue() + ")";
		setText("");
	}
	/**
	 * Setter for background color
	 * @param newColor
	 */
	public void setBackgroundText(Color newColor) {
		backgroundText = "background color: (" + newColor.getRed() + "," + newColor.getGreen() + ","
				+ newColor.getBlue() + ")";
		setText("");
	}

	@Override
	public void setText(String text) {

		super.setText(foregroundText + "," + backgroundText);
	}

}
