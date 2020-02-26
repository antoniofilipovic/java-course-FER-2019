package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.JMenu;

/**
 * This class extends jmenu so it can change its name for localization purpuses
 * 
 * @author af
 *
 */
public class LJMenu extends JMenu {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	/**
	 * Listener
	 */
	private ILocalizationListener listener;

	/**
	 * provider
	 */
	private ILocalizationProvider provider;
	/**
	 * key
	 */
	private String key;

	/**
	 * Public constructor
	 * 
	 * @param key      for which translation will be asked
	 * @param provider providers
	 */
	public LJMenu(String key, ILocalizationProvider provider) {
		this.provider = provider;
		this.key = key;
		this.listener = () -> update();
		update();
		this.provider.addLocalizationListener(listener);
	}

	/**
	 * This method is used for updating name and description of action.
	 */
	private void update() {
		setText(provider.getString(key));
	}
}
