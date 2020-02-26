package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * This class represents context of 
 * @author Antonio Filipović
 *
 */

public class Context {
	private ObjectStack<TurtleState> stack;
	
	public Context() {
		stack=new ObjectStack<>();
	}
	/**
	 * Returns state from stack without removing it
	 * @return state from stack if stack is not empty else returns null 
	 * @throws EmptyStackException if empty
	 */
	public TurtleState getCurrentState() {
		return stack.peek();
	}
	/**
	 * This method pushes on stack current state
	 * @param state that will be pushed on stack
	 */
	public void pushState(TurtleState state) {
		stack.push(state);
	}
	/**
	 * Removes state from top of stack
	 */
	public void popState() {
		if(!stack.isEmpty()) {
			stack.pop();
		}
	}


}
