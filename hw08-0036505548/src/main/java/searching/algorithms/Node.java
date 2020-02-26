package searching.algorithms;

import searching.slagalica.KonfiguracijaSlagalice;

/**
 * This class represents node. It holds current State
 * {@link KonfiguracijaSlagalice}, parent and cost to get to this state
 * 
 * @author af
 *
 * @param <S>
 */
public class Node<S> {
	/**
	 * This is current state of configuration
	 */
	private S currentState;
	/**
	 * Parrent of configuration
	 */
	private Node<S> parent;
	/**
	 * Cost to get to this state
	 */
	private double cost;

	/**
	 * Public constructor
	 * 
	 * @param parent       parent
	 * @param currentState current state
	 * @param cost         cost
	 */
	public Node(Node<S> parent, S currentState, double cost) {
		super();
		this.currentState = currentState;
		this.parent = parent;
		this.cost = cost;
	}

	/**
	 * getter for state
	 * 
	 * @return state
	 */
	public S getState() {
		return currentState;
	}

	/**
	 * getter for cost
	 * 
	 * @return cost
	 */
	public double getCost() {
		return cost;
	}

	/**
	 * Getter for parent
	 * 
	 * @return parent
	 */
	public Node<S> getParent() {
		return parent;
	}
}
