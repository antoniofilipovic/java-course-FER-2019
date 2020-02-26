package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 *  
 * This class represents connection to frame when it is connected
 *
 * @author af
 *
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {
	
	/**
	 * Public constructor 
	 * @param localizationProvider localization provider
	 * @param frame frame
	 */
	public FormLocalizationProvider(ILocalizationProvider localizationProvider, JFrame frame) {
		super(localizationProvider);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				connect();
			}

			@Override
			public void windowClosed(WindowEvent e) {
				disconnect();
			}

		});
	}

}
