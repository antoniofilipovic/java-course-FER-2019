package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * This class represents color command for turtle to be executed. Sets turtle
 * color to Color received in constructor.
 * 
 * @author af
 *
 */
public class ColorCommand implements Command {
	/**
	 * This is private variable color
	 */
	private Color color;

	/**
	 * Public constructor for color command
	 * 
	 * @param color
	 */
	public ColorCommand(Color color) {
		this.color = color;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().setColor(color);
	}

}
