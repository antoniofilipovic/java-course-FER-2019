package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;
/**
 * This class can register and remove registers from its list and notify listners
 * @author af
 *
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider {
	/**
	 * List of listeners
	 */
	 private List<ILocalizationListener> listeners = new ArrayList<>();
	 /**
	  * Public constructor
	  */
	public AbstractLocalizationProvider() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void addLocalizationListener(ILocalizationListener listener) {
		listeners.add(listener);
		
	}

	@Override
	public void removeLocalizationListener(ILocalizationListener listener) {
		listeners.remove(listener);
		
	}
	/**
	 * This method notifies all listeners
	 */
	public void fire() {
		for (ILocalizationListener l : listeners) {
            l.localizationChanged();
        }
	}

	

}
