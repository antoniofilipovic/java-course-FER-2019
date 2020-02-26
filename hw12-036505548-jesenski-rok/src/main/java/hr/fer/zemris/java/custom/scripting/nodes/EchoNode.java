package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
/**
 * This class represents class for Echo Node
 * @author Antonio Filipović
 *
 */

public class EchoNode extends Node {
	/**
	 * Private variable elements
	 */
	 private Element[]  elements;
	 
	 public EchoNode(Element[] elements) {
		super();
		this.elements = elements;
	}

	/**
	  * This method represents getter for elements
	  * @return Element
	  */
	 
	 public Element[] elements() {
		 return elements;
	 }

	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitEchoNode(this);
		
	}
	
	 

}
