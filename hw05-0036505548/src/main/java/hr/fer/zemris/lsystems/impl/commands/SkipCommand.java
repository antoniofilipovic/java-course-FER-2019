package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.lsystems.impl.Vector2D;

/**
 * This class calculates next postion and moves turtle to that postion but it
 * doesn't draw line.
 * 
 * @author Antonio Filipović
 *
 */
public class SkipCommand implements Command {
	/**
	 * This variable represents step for which this turtle moves
	 */
	private double step;
	/**
	 * This is public constructor for SkipCommand
	 * @param step
	 */
	public SkipCommand(double step) {
		this.step = step;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState turtle = ctx.getCurrentState();
		Vector2D direction = turtle.getDirection();
		Vector2D position = turtle.getCurrentPosition();
		Vector2D nextPostion = position.translated(direction.scaled(turtle.getMoveLength() * step));
		turtle.setCurrentPosition(nextPostion);
	}

}
