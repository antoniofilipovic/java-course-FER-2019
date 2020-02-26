package hr.fer.zemris.java.hw17.jvdraw.component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JColorChooser;
import javax.swing.JComponent;

import hr.fer.zemris.java.hw17.jvdraw.listener.ColorChangeListener;
import hr.fer.zemris.java.hw17.jvdraw.provider.IColorProvider;

/**
 * This class represents color area with whom we chooes color for painting
 * 
 * @author af
 *
 */
public class JColorArea extends JComponent implements IColorProvider {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Selected color
	 */
	private Color selectedColor;
	/**
	 * Listeners
	 */
	private List<ColorChangeListener> listeners = new ArrayList<>();

	/**
	 * Public constructor
	 * 
	 * @param selectedColor
	 */
	public JColorArea(Color selectedColor) {
		this.selectedColor = selectedColor;

		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Color newColor = JColorChooser.showDialog(null, "JColorChooser Sample", JColorArea.this.selectedColor);
				if (newColor != null) {
					Color oldColor = JColorArea.this.selectedColor;
					JColorArea.this.selectedColor = newColor;
					paintComponent(getGraphics());
					notifyListeners(oldColor);
				}

			}

		});

	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(30, 30);
	}

	@Override
	public Dimension getMaximumSize() {
		return new Dimension(30, 30);
	}

	@Override
	public Color getCurrentColor() {
		return this.selectedColor;
	}

	@Override
	public void addColorChangeListener(ColorChangeListener l) {
		this.listeners.add(l);

	}

	@Override
	public void removeColorChangeListener(ColorChangeListener l) {
		this.listeners.remove(l);

	}

	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(this.selectedColor);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

	}

	/**
	 * Method that notifies listeners
	 * 
	 * @param oldColor old color
	 */
	private void notifyListeners(Color oldColor) {
		for (ColorChangeListener l : listeners) {
			l.newColorSelected(this, oldColor, this.selectedColor);
		}
	}

}
