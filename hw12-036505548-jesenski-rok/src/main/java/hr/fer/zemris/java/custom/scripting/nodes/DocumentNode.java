package hr.fer.zemris.java.custom.scripting.nodes;
/**
 * This class represents document node
 * @author af
 *
 */
public class DocumentNode extends Node{
	
	/**
	 * This is public constructor
	 */
	
	public DocumentNode() {
		super();
	}

	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitDocumentNode(this);
	}
	

}
