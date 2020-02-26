package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * This class is used to show two lists of prime numbers. It generates new
 * numbers on next button
 * 
 * @author af
 *
 */
public class PrimDemo extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * Public construcotr
	 */
	public PrimDemo() {
		super();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("PrimDemo");
		setLocation(20, 20);
		setSize(500, 200);
		initGUI();
	}

	/**
	 * This method initializes gui
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		cp.setBackground(Color.WHITE);

		ListModel<Integer> model = new PrimListModel(this);

		JScrollPane scPane1 = new JScrollPane(new JList<>(model));
		JScrollPane scPane2 = new JScrollPane(new JList<>(model));

		JPanel listPanel = new JPanel();
		listPanel.setLayout(new GridLayout(0, 2));
		listPanel.add(scPane1);
		listPanel.add(scPane2);

		cp.add(listPanel, BorderLayout.CENTER);

		JButton next = new JButton("Next");
		next.addActionListener(e -> ((PrimListModel) model).next());
		cp.add(next, BorderLayout.SOUTH);
	}

	/**
	 * This method starts when main program stars
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new PrimDemo().setVisible(true);
		});
		;
	}

	/**
	 * This class represents iterator for prime numbers
	 * 
	 * @author af
	 *
	 */
	private static class PrimesIterator implements Iterator<Integer> {

		/**
		 * current prime element;
		 */
		private int current = 0;

		@Override
		public boolean hasNext() {
			return true;
		}

		@Override
		public Integer next() {

			int j = current + 1;
			while (true) {
				boolean prime = true;
				for (int i = 2; i < (int) Math.sqrt(j) + 1; i++) {
					if (j % i == 0) {
						prime = false;
						break;
					}
				}
				if (prime) {
					current = j;
					break;
				}
				j++;
			}
			current = j;
			return current;
		}

	}

	/**
	 * This method returns new iterator
	 * 
	 * @return new iterator
	 */
	public Iterator<Integer> getIterator() {
		return new PrimesIterator();
	}

}
