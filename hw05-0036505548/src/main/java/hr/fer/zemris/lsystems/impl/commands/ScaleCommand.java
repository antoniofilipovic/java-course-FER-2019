package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.*;

/**
 * This class represents  scale command for turtle movement.
 * @author Antonio Filipović
 *
 */

public class ScaleCommand implements Command {
	/**
	 * Factor for which 
	 */
	private double factor;
	/**
	 * This is public constructor for scale command
	 * @param factor
	 */
	public ScaleCommand(double factor) {
		this.factor=factor;
	}
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState turtle=ctx.getCurrentState();
		turtle.setMoveLength(turtle.getMoveLength()*factor);
		
	}

}
