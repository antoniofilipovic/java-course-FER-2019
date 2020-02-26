package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;

/**
 * This class represents basic class for all nodes
 * 
 * @author Antonio Filipović
 *
 */

public abstract class Node {
	/**
	 * This is reference to {@link ArrayIndexedCollection} which will store all
	 * nodes
	 */
	private ArrayIndexedCollection coll = null;
	/**
	 * This variable represents if instance was created
	 */
	private boolean notCreated = true;

	/**
	 * This method adds child to collection
	 * @param child node that will be added
	 */

	public void addChildNode(Node child) {
		if (notCreated) {
			coll = new ArrayIndexedCollection();
			notCreated = false;
		}
		coll.add(child);

	}
	/**
	 * This method returns number of  direct childern
	 * @return number of childern
	 */
	public int numberOfChildren() {
		if(coll==null) {
			return 0;
		}
		return coll.size();

	}
	/**
	 * This method returns node on index
	 * @param index of node that will be returned
	 * @return Node on index
	 */
	public Node getChild(int index) {
		return (Node) coll.get(index);
	}
	
	/**
	 * Accept method for visitor
	 * @param visitor
	 */
	public abstract void accept(INodeVisitor visitor);

}
