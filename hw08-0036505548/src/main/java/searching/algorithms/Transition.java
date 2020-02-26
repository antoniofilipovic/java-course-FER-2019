package searching.algorithms;

import searching.slagalica.KonfiguracijaSlagalice;

/**
 * This class holds reference to {@link KonfiguracijaSlagalice} and cost to move
 * from one state to another
 * 
 * @author af
 *
 * @param <S>
 */
public class Transition<S> {
	/**
	 * current state
	 */
	private S state;
	/**
	 * cost to get to this state
	 */
	private double cost;

	/**
	 * Public constructor
	 * 
	 * @param state
	 * @param cost
	 */
	public Transition(S state, double cost) {
		super();
		this.state = state;
		this.cost = cost;
	}

	/**
	 * Getter for state
	 * 
	 * @return
	 */
	public S getState() {
		return state;
	}

	/**
	 * Setter for state
	 * 
	 * @param state
	 */
	public void setState(S state) {
		this.state = state;
	}

	/**
	 * Getter for cost
	 * 
	 * @return cost
	 */
	public double getCost() {
		return cost;
	}

	/**
	 * Setter for cost
	 * 
	 * @param cost
	 */
	public void setCost(double cost) {
		this.cost = cost;
	}

}
