package hr.fer.zemris.java.hw17.jvdraw.component;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Supplier;

import javax.swing.JComponent;

import hr.fer.zemris.java.hw17.jvdraw.geomtrical.object.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.listener.DrawingModelListener;
import hr.fer.zemris.java.hw17.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.tools.Tool;
import hr.fer.zemris.java.hw17.jvdraw.visitor.GeometricalObjectPainter;
import hr.fer.zemris.java.hw17.jvdraw.visitor.GeometricalObjectVisitor;

/**
 * This class represents component of which coloring actually happens
 * 
 * @author af
 *
 */
public class JDrawingCanvas extends JComponent implements DrawingModelListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Reference to drawing model
	 */
	private DrawingModel model;
	/**
	 * Painter
	 */
	private GeometricalObjectVisitor painter;
	/**
	 * Supplier of current tool
	 */
	private Supplier<Tool> supplier;
	/**
	 * Public construcotr
	 * @param jvdraw supplier
	 * @param model model
	 */
	public JDrawingCanvas(Supplier<Tool> jvdraw, DrawingModel model) {
		this.model = model;
		this.model.addDrawingModelListener(this);
		this.supplier = jvdraw;

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				if (supplier.get() != null) {
					supplier.get().mouseClicked(e);
				}

			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (supplier.get() != null) {
					supplier.get().mousePressed(e);
				}

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (supplier.get() != null) {
					supplier.get().mouseReleased(e);
				}
			}

		});

		addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				if (supplier.get() != null) {
					supplier.get().mouseMoved(e);
				}

			}

			@Override
			public void mouseDragged(MouseEvent e) {

			}

		});

	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		repaint();

	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		repaint();

	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		repaint();

	}

	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		Graphics2D g2d = (Graphics2D) g;

		painter = new GeometricalObjectPainter(g2d);

		for (int i = 0; i < model.getSize(); i++) {
			GeometricalObject o = model.getObject(i);
			o.accept(painter);

		}
		if (supplier.get() != null) {
			supplier.get().paint((Graphics2D) g);
		}

	}

}
