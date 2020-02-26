package hr.fer.zemris.java.hw11.jnotepadpp.local;
/**
 * This interface is used for translation
 * @author af
 *
 */
public interface ILocalizationProvider {
	/**
	 * Adds localization listeners
	 * @param listener
	 */
    void addLocalizationListener(ILocalizationListener listener);

    /**
     * Removes listeners
     * @param listener
     */
    void removeLocalizationListener(ILocalizationListener listener);

    /**
     * Gets translation for given key
     * @param key returns translation for this key
     * @return translation
     */
    String getString(String key);

}
