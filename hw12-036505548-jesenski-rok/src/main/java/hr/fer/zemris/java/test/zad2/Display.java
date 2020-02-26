package hr.fer.zemris.java.test.zad2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.swing.AbstractAction;
import javax.swing.AbstractListModel;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class Display extends JFrame {

	/**
	 * bar chart
	 */
	private static final long serialVersionUID = 1L;
	private Map<String, Integer> data;

	private static Path selectedFile;

	private JList<String> jList;
	private List<String> possibleDates = new ArrayList<>();
	private DisplayListModel listModelDates;

	private JLabel label = new JLabel();

	/**
	 * Public constructor for barchat
	 * 
	 * @param barchart2 barchart
	 * @param args      arg
	 */
	public Display() {
		super();

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Dates");
		setLocation(20, 20);
		setSize(500, 500);

		initGUI();
		initMenu();
	}

	private void initMenu() {
		JMenuBar mb = new JMenuBar();
		JMenu file = new JMenu("file");

		file.add(new JMenuItem(selectDirectory));

		mb.add(file);

		setJMenuBar(mb);

	}

	/**
	 * This method initializes gui
	 * 
	 * @param args
	 */
	private void initGUI() {

		GridLayout experimentLayout = new GridLayout(0, 2);
		

		Container cp = getContentPane();
		cp.setLayout(experimentLayout);

		possibleDates.add("baaa");

		listModelDates = new DisplayListModel(possibleDates);
		jList = new JList<>(listModelDates);
		//
		cp.add(new JScrollPane(jList), BorderLayout.WEST);
		
//		experimentLayout.addLayoutComponent("list", new JScrollPane(jList));
//		experimentLayout.addLayoutComponent("labela", label);

		//cp.add(label, BorderLayout.EAST);
		cp.add(label);
		jList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String o = jList.getSelectedValue();

				
				SimpleDateFormat format1 = new SimpleDateFormat("yyyy-mm-dd");
				Date dt1=null;
				try {
					dt1 = format1.parse(o);
				} catch (ParseException e1) {
					
				}
//				DateFormat format2 = new SimpleDateFormat("EEEE");
//				String finalDay = format2.format(dt1);
				
				Calendar c = Calendar.getInstance();
				c.setTime(dt1);
				int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
				String finalDay="petak";
				System.out.println(dayOfWeek);
				if(dayOfWeek==2) {
					finalDay="subota";
				}
				if(dayOfWeek==3) {
					finalDay="nedjelja";
				}
				if(dayOfWeek==4) {
					finalDay="ponedjeljak";
				}
				if(dayOfWeek==5) {
					finalDay="utorak";
				}
				if(dayOfWeek==6) {
					finalDay="srijeda";
				}
				if(dayOfWeek==7) {
					finalDay="četvrtak";
				}
			
				
				String glagol=" bila je ";
				if(finalDay.equals("ponedjeljak") || finalDay.equals("utorak") || finalDay.equals("četvrtak") || finalDay.equals("petak") ) {
					glagol=" bio je ";
				}
				label.setText("Na datum " + o + glagol +finalDay);

				System.out.println(o);

			}
		});

		//cp.setBackground(Color.WHITE);

	}

	/**
	 * This method starts when main program stars
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		try {

			SwingUtilities.invokeLater(() -> {
				new Display().setVisible(true);
			});
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
			System.exit(-1);
		}

	}

	private final Action selectDirectory = new AbstractAction("open") {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser jfc = new JFileChooser();
			//
			// disable the "All files" option.
			//
			jfc.setAcceptAllFileFilterUsed(false);
			jfc.setDialogTitle("Open directory");
			if (jfc.showOpenDialog(Display.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}
			selectedFile = jfc.getSelectedFile().toPath();
			List<String> lines = null;
			try {
				lines = Files.readAllLines(selectedFile);
			} catch (IOException e1) {
				System.out.println("Exception while reading from file");
				return;
			}
			List<String> dates = new ArrayList<>();
			for (String s : lines) {
				if (s == null || s.isEmpty() || s.startsWith("#")) {
					continue;
				}
				dates.add(s);
			}
			triggerChanges(dates);

		}

	};

	private void triggerChanges(List<String> dates) {
		int size = possibleDates.size();
		for (int i = 0; i < size; i++) {
			listModelDates.remove(0);
		}
		for (String s : dates) {
			System.out.println(s);
			listModelDates.add(s);
		}

	}

	private class DisplayListModel extends AbstractListModel<String> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private List<ListDataListener> listeners = new ArrayList<>();
		private List<String> possibleDates = new ArrayList<>();

		public DisplayListModel(List<String> possibleDates) {
			this.possibleDates = possibleDates;
		}

		@Override
		public void addListDataListener(ListDataListener l) {
			listeners.add(l);
		}

		@Override
		public void removeListDataListener(ListDataListener l) {
			listeners.remove(l);
		}

		@Override
		public int getSize() {
			return possibleDates.size();
		}

		@Override
		public String getElementAt(int index) {
			return possibleDates.get(index);
		}

		public void add(String element) {
			int pos = possibleDates.size();
			possibleDates.add(element);

			ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, pos, pos);
			for (ListDataListener l : listeners) {
				l.intervalAdded(event);
			}
		}

		public void remove(int pos) {
			possibleDates.remove(pos);
			ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_REMOVED, pos, pos);
			for (ListDataListener l : listeners) {
				l.intervalRemoved(event);
			}
		}

	}

}