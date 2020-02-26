package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.lsystems.impl.Vector2D;

/**
 * This command rotates current direction of turtle
 * 
 * @author af
 *
 */

public class RotateCommand implements Command {
	/**
	 * Private variable represents angle
	 */

	private double angle;

	/**
	 * This is public constructor for Rotate Command
	 * 
	 * @param angle
	 */
	public RotateCommand(double angle) {
		this.angle = angle;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState turtle=ctx.getCurrentState();
		Vector2D newDirection = turtle.getDirection().rotated(angle);
		turtle.setDirection(newDirection);

	}

}
