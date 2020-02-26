package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.lsystems.impl.Vector2D;

/**
 * This class draws line from current postition to calculated one, with current
 * color, by calculating where turtle should go
 * 
 * @author Antonio Filipović
 *
 */
public class DrawCommand implements Command {
	private double step;

	/**
	 * This is public constructor for step
	 * 
	 * @param step
	 */
	public DrawCommand(double step) {
		this.step = step;
	}
	public double getStep() {
		return step;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState turtle=ctx.getCurrentState();
		Vector2D direction=turtle.getDirection();
		Vector2D position=turtle.getCurrentPosition();
		Vector2D nextPosition=position.translated(direction.scaled(turtle.getMoveLength()*step));
		painter.drawLine(position.getX(), position.getY(),
				nextPosition.getX(),nextPosition.getY(), 
				turtle.getColor(), 1);
		turtle.setCurrentPosition(nextPosition);
	}

}
