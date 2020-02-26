package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

/**
 * This class extends class Node and represents for loop node
 * 
 * @author Antonio Filipović
 *
 */

public class ForLoopNode extends Node {
	/**
	 * variable that is stored
	 */
	private ElementVariable variable;
	/**
	 * starting expression of for loop node
	 */
	private Element startExpression;
	/**
	 * end expression
	 */
	private Element endExpression;
	/**
	 * Can be null
	 */
	private Element stepExpression;
	
	/**
	 * This is public constructor for for loop node
	 * @param variable type elementvariable 
	 * @param startExpression type element
	 * @param endExpression type element 
	 * @param stepExpression type element, can be null
	 */

	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
			Element stepExpression) {
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}

	/**
	 * This is getter for {@link variable}
	 * 
	 * @return ElementVariable for variable
	 */
	public ElementVariable getVariable() {
		return variable;
	}

	/**
	 * This is getter for startExpression
	 * 
	 * @return Element
	 */
	public Element getStartExpression() {
		return startExpression;
	}

	/**
	 * This is getter for end Expression
	 * 
	 * @return Element
	 */
	public Element getEndExpression() {
		return endExpression;
	}

	/**
	 * This is getter for stepExpression
	 * 
	 * @return Element
	 */
	public Element getStepExpression() {
		return stepExpression;
	}

	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitForLoopNode(this);
		
	}

}
