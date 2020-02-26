package hr.fer.zemris.java.fractals;

import java.util.concurrent.ThreadFactory;

/**
 * This class represents daemon thread factory
 * 
 * @author af
 *
 */
public class DaemonicThreadFactory implements ThreadFactory {

	@Override
	public Thread newThread(Runnable r) {
		Thread t = new Thread(r);
		t.setDaemon(true);
		return t;
	}

}
