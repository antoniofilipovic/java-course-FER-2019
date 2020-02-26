package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.AbstractAction;
import javax.swing.Action;

/**
 * This class extends abstract action but uses additional implementation to
 * chage name and description of action
 * 
 * @author af
 *
 */
public abstract class LocalizableAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Listener
	 */
	private ILocalizationListener listener;
	/**
	 * Provider
	 */

	private ILocalizationProvider provider;
	/**
	 * Key
	 */
	private String key;

	/**
	 * Public constructor for action
	 * 
	 * @param key      key
	 * @param provider provider
	 */
	public LocalizableAction(String key, ILocalizationProvider provider) {
		this.provider = provider;
		this.key = key;
		this.listener = () -> update();
		this.provider.addLocalizationListener(listener);
		update();

	}

	/**
	 * This method is used for updating name and description of action.
	 */
	private void update() {
		putValue(NAME, provider.getString(key));
		putValue(Action.SHORT_DESCRIPTION, provider.getString(key + "_desc"));
	}
}
