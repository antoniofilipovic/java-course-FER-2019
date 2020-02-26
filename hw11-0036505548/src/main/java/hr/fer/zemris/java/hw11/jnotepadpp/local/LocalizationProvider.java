package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;
/**
 * This class extends {@link AbstractLocalizationProvider} and adds implementation getstring
 * method so it returns translation for given key. It represents singleton class so it hold only
 * one reference
 * @author af
 *
 */
public class LocalizationProvider extends AbstractLocalizationProvider {
	/**
	 * Instance
	 */
	private static final LocalizationProvider instance = new LocalizationProvider();
	/**
	 * Bundle
	 */
	private ResourceBundle bundle;
	/**
	 * Language
	 */
	private String language;
	
	
	/**
	 * Private constructor
	 */
	private LocalizationProvider() {
		setLanguage("en");
	}
	/**
	 * Returns instance 
	 * @return
	 */
	public static LocalizationProvider getInstance() {
		return instance;
	}

	@Override
	public String getString(String key) {
		return bundle.getString(key);
	}
	/**
	 * Sets language and translation
	 * @param language
	 */
	public void setLanguage(String language) {
		this.language=language;
		Locale locale = Locale.forLanguageTag(language);
		bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.jnotepadpp.local.prijevodi", locale);
		fire();
	}
	/**
	 * Gets language
	 * @return
	 */
	public String getLanguage() {
		return language;
	}
	

}
