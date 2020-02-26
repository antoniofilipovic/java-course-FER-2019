package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * This class represents bridge between {@link AbstractLocalizationProvider} and
 * {@link FormLocalizationProvider} it holds reference to parent to which all
 * listeners are registered
 * 
 * @author af
 *
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {
	/**
	 * connection status
	 */
	private boolean connected;
	/**
	 * parent
	 */
	private ILocalizationProvider parent;
	/**
	 * listener
	 */
	private ILocalizationListener listener;

	/**
	 * Public constructor
	 * 
	 * @param localizationProvider
	 */
	public LocalizationProviderBridge(ILocalizationProvider localizationProvider) {
		this.parent = localizationProvider;
		connected = false;
		listener = () -> fire();
	}

	/**
	 * Disconnects parent
	 */
	public void disconnect() {
		if (!connected)
			return;
		parent.removeLocalizationListener(listener);
		connected = false;

	}

	/**
	 * Connects parrent
	 */
	public void connect() {
		if (connected) {
			return;
		}
		parent.addLocalizationListener(listener);

	}

	@Override
	public String getString(String key) {
		return parent.getString(key);
	}

}
