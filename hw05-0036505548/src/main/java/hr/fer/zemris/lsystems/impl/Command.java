package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;

/**
 * This interface represents command to be executed
 * 
 * @author Antonio Filipović
 *
 */
public interface Command {
	/**
	 * This method defines what will turtle do
	 * 
	 * @param ctx     context
	 * @param painter
	 */
	public void execute(Context ctx, Painter painter);

}
