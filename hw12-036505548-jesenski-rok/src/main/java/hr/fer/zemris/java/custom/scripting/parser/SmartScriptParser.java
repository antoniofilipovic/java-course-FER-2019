package hr.fer.zemris.java.custom.scripting.parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import javax.lang.model.util.Elements;

import hr.fer.zemris.java.custom.collections.LinkedListIndexedCollection;
import hr.fer.zemris.java.custom.scripting.elems.*;
import hr.fer.zemris.java.custom.scripting.lexer.*;
import hr.fer.zemris.java.custom.scripting.nodes.*;

/**
 * This class represents parser. It receives tokens from SmartScriptLexer and
 * constructs tree
 * 
 * @author Antonio Filipović
 *
 */

public class SmartScriptParser {
	/**
	 * This is helping structure used to create tree
	 */
	private ObjectStack stack;
	/**
	 * Node to be returned
	 */
	private DocumentNode documentNode;
	/**
	 * Current token
	 */
	private SmartScriptToken token;
	/**
	 * Lexer created
	 */
	private SmartScriptLexer lexer;
	
	private String nowPattern="yyyy-MM-dd HH:mm:ss";

	/**
	 * constructor, constructs lexer,stack
	 * 
	 * @param s string to be parsed
	 */
	public SmartScriptParser(String s) {
		if (s == null) {
			throw new SmartScriptParserException("Input string must not be null.");
		}
		lexer = new SmartScriptLexer(s);
		stack = new ObjectStack();
		stack.push(new DocumentNode());
		try {
			documentNode = startParsing();
		} catch (SmartScriptLexerException e) {
			throw new SmartScriptParserException("Exception happend in lexer.");
		}

	}

	/**
	 * Returns document node from tree created
	 * 
	 * @return document node
	 */
	public DocumentNode getDocumentNode() {
		return documentNode;
	}

	/**
	 * Method called for parsing. It gets next token and depending what it is calls
	 * that method. Only for can have childern and document node that is first node
	 * 
	 * @return document node
	 */
	private DocumentNode startParsing() {

		Node node = null;
		token = lexer.nextToken();
		while (token.getType() != SmartScriptTokenType.EOF) {
			
			if (token.getType() == SmartScriptTokenType.TEXT) {
				node = (Node) stack.peek();
				node.addChildNode(new TextNode((String) token.getValue()));
			}
			
			
			if (token.getType() == SmartScriptTokenType.TAG) {
				lexer.setState(SmartScriptLexerState.TAG);
				
			}
			
			
			
			if (token.getType() == SmartScriptTokenType.TAGNAME) {
				if (token.getValue().equals("=")) {
					parseEcho();
				} else if (((String) token.getValue()).toLowerCase().equals("for")) {
					parseFor();
				} else if (((String) token.getValue()).toLowerCase().equals("end")) {
					parseEnd();
				} else if(((String) token.getValue()).toUpperCase().equals("NOW")) {
					parseNow();
				}
				
				else {
					throw new SmartScriptParserException("Wrong tagname!");
				}
			}
			token = lexer.nextToken();

		}
		try {
			documentNode = (DocumentNode) stack.pop();
		} catch (EmptyStackException | ClassCastException e) {
			throw new SmartScriptParserException("Problem occured with parser.");
		}
		if (stack.size() != 0) {
			throw new SmartScriptParserException("Problem occured with parser.");
		}

		return documentNode;

	}
	
	private void parseNow() {
		token = lexer.nextToken();
		if(!(token.getType()==SmartScriptTokenType.STRING || token.getType()==SmartScriptTokenType.TAG)) {
			throw new SmartScriptParserException("Problem while parsing now tag.");
		}
		String formatDateTime="";
		LocalDateTime now = LocalDateTime.now();
		if(token.getType()==SmartScriptTokenType.STRING) {
			
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern((String)token.getValue());

	        formatDateTime = now.format(formatter);
	        token=lexer.nextToken();
	        if(token.getType()!=SmartScriptTokenType.TAG) {
	        	throw new SmartScriptParserException("After string in now tag there must be end tag");
	        }
		}else if(token.getType()==SmartScriptTokenType.TAG) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(nowPattern);
			formatDateTime=now.format(formatter);
		}
		Node node=(Node)stack.peek();
		node.addChildNode(new TextNode(formatDateTime));
		
		lexer.setState(SmartScriptLexerState.TEXT);

	}

	/**
	 * This method parses end token. If there isnt tag after END tokentagname than
	 * exception is thrown
	 * 
	 * @throws SmartScriptParserException
	 */
	private void parseEnd() {
		token = lexer.nextToken();
		if (token.getType() != SmartScriptTokenType.TAG) {
			throw new SmartScriptParserException("Tag was excpected after end tagname.");
		}
		lexer.setState(SmartScriptLexerState.TEXT);
		stack.pop();
	}

	/**
	 * Parses echo token (this is echo-> =). Echo can receive as many tokens as it
	 * likes
	 * 
	 * @throws SmartScriptParserException if token is not one of these:
	 *                                    variable,number, string, function,operator
	 */
	private void parseEcho() {
		LinkedListIndexedCollection coll = new LinkedListIndexedCollection();
		Element[] elements = null;
		token = lexer.nextToken();
		while (token.getType() != SmartScriptTokenType.TAG) {

			if (token.getType() == SmartScriptTokenType.VARIABLE) {
				coll.add(new ElementVariable((String) token.getValue()));
			} else if ((token.getType() == SmartScriptTokenType.INTEGER)) {
				coll.add(new ElementConstantInteger((int) token.getValue()));
			} else if ((token.getType() == SmartScriptTokenType.DOUBLE)) {
				coll.add(new ElementConstantDouble((double) token.getValue()));
			} else if ((token.getType() == SmartScriptTokenType.STRING)) {
				coll.add(new ElementString((String) token.getValue()));
			} else if ((token.getType() == SmartScriptTokenType.OPERATOR)) {
				coll.add(new ElementOperator((String) token.getValue()));
			} else if ((token.getType() == SmartScriptTokenType.FUNCTION)) {
				coll.add(new ElementFunction((String) token.getValue()));
			} else {
				throw new SmartScriptParserException("This was not expected inside of echo");
			}
			token = lexer.nextToken();
		}
		elements = new Element[coll.size()];
		lexer.setState(SmartScriptLexerState.TEXT);
		for (int i = 0; i < coll.size(); i++) {
			elements[i] = (Element) coll.get(i);
		}

		EchoNode echoNode = new EchoNode(elements);
		Node node = (Node) stack.peek();
		node.addChildNode(echoNode);

	}

	/**
	 * This method parses for token. For can receive three or for tokens nothing
	 * more. First there must be variable next two tokens can be string,variable or
	 * number And then tag or something from above
	 * 
	 * @throws SmartScriptParserException if tokens are not in right order
	 */
	private void parseFor() {
		ForLoopNode forNode = null;
		ElementVariable variable = null;
		Element elementFirstIndex = null, elementSecondIndex = null, elementThirdIndex = null;
		for (int i = 0; i < 4; i++) {
			token = lexer.nextToken();
			if (i == 0 && token.getType() != SmartScriptTokenType.VARIABLE) {
				throw new SmartScriptParserException("Variable was expected after for.");
			}
			if (i == 0) {
				variable = new ElementVariable((String) token.getValue());
			}
			if (i == 1) {
				elementFirstIndex = parseForTwoAndThree();
			}
			if (i == 2) {
				elementSecondIndex = parseForTwoAndThree();
			}
			if (i == 3) {
				if (token.getType() != SmartScriptTokenType.TAG) {
					elementThirdIndex = parseForTwoAndThree();
					token = lexer.nextToken();
					if (token.getType() != SmartScriptTokenType.TAG) {
						throw new SmartScriptParserException("End of tag was expected.");
					}
				}
				lexer.setState(SmartScriptLexerState.TEXT);
			}
		}
		forNode = new ForLoopNode(variable, elementFirstIndex, elementSecondIndex, elementThirdIndex);
		Node node = (Node) stack.peek();
		node.addChildNode(forNode);
		stack.push(forNode);

	}

	/**
	 * Parses first second and maybe thirds part of for tag
	 * 
	 * @return Element
	 */
	private Element parseForTwoAndThree() {
		if ((token.getType() == SmartScriptTokenType.STRING)) {
			return new ElementString((String) token.getValue());
		}
		if ((token.getType() == SmartScriptTokenType.VARIABLE)) {
			return new ElementVariable((String) token.getValue());
		}
		if ((token.getType() == SmartScriptTokenType.INTEGER)) {
			return new ElementConstantInteger((int) token.getValue());
		}
		if ((token.getType() == SmartScriptTokenType.DOUBLE)) {
			return new ElementConstantDouble((double) token.getValue());
		}
		throw new SmartScriptParserException("This was not expected in for tag.");
	}

}
