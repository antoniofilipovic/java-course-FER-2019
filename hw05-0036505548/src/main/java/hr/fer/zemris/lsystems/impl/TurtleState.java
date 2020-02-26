package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

public class TurtleState {
	/**
	 * Position of turtle.
	 */
	private Vector2D currentPosition;

	/**
	 * Turtle direction
	 */
	private Vector2D direction;

	/**
	 * Turtle color.
	 */
	private Color color;

	/**
	 * Length of move
	 */
	private double moveLength;

	/**
	 * This is public constructor for TurtleState
	 * 
	 * @param currentPosition represents current position of Turtle
	 * @param direction       represents direction of turtle
	 * @param color           represents color of turtle
	 * @param moveLength      represents length of move for turtle
	 */

	public TurtleState(Vector2D currentPosition, Vector2D direction, Color color, double moveLength) {
		super();
		this.currentPosition = currentPosition;
		this.direction = direction;
		this.color = color;
		this.moveLength = moveLength;
	}

	/**
	 * Getter for current postion
	 * 
	 * @return current position
	 */
	public Vector2D getCurrentPosition() {
		return currentPosition;
	}

	/**
	 * Setter for current postion
	 * 
	 * @param currentPosition
	 */
	public void setCurrentPosition(Vector2D currentPosition) {
		this.currentPosition = currentPosition;
	}

	/**
	 * This method represents getter for dicetion
	 * 
	 * @return direction
	 */
	public Vector2D getDirection() {
		return direction;
	}

	/**
	 * This is setter for direction
	 * 
	 * @param direction to be set
	 */
	public void setDirection(Vector2D direction) {
		this.direction = direction;
	}

	/**
	 * Getter for color
	 * 
	 * @return color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Setter for color
	 * 
	 * @param color
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Getter for length move
	 * 
	 * @return
	 */
	public double getMoveLength() {
		return moveLength;
	}

	/**
	 * Setter for move length
	 * 
	 * @param moveLength
	 */
	public void setMoveLength(double moveLength) {
		this.moveLength = moveLength;
	}

	/**
	 * This method copies turtle state
	 * 
	 * @return TurtleState copy
	 */
	public TurtleState copy() {
		return new TurtleState(currentPosition.copy(), direction.copy(), color, moveLength);
	}
}
