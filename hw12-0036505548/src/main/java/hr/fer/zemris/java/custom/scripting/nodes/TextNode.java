package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * This class represents TextNode that inherits Node and consists of only one 
 * read only variable text
 * @author Antonio Filipović
 *
 */

public class TextNode extends Node{
	
	/**
	 * Private read only variable text
	 */
	private String text;
	/**
	 * This is constructor for TextNode
	 * @param text that will TextNode have
	 */
	public TextNode(String text){
		this.text=text;
	}
	/**
	 * Represents getter for text
	 * @return String text of TextNode
	 */
	public String getText() {
		return text;
	}
	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitTextNode(this);
		
	}
}
