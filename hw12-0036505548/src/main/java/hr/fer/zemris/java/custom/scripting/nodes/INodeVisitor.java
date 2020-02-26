package hr.fer.zemris.java.custom.scripting.nodes;
/**
 * This class represents visitor
 * @author af
 *
 */
public interface INodeVisitor {
	/**
	 * This method parses for text node
	 * 
	 * @param forLoopNode that should be parsed
	 * @return string
	 */
	public void visitTextNode(TextNode node);

	/**
	 * This method parses for loop node
	 * 
	 * @param forLoopNode that should be parsed
	 * @return string
	 */
	public void visitForLoopNode(ForLoopNode node);

	/**
	 * This method parses echo tag
	 * 
	 * @param echoNode
	 * @return string from parsed tag
	 */
	public void visitEchoNode(EchoNode node);

	/**
	 * This method creates original document from node created previously
	 * 
	 * @param node from which it creates it
	 * @return document body
	 */
	public void visitDocumentNode(DocumentNode node);
}
