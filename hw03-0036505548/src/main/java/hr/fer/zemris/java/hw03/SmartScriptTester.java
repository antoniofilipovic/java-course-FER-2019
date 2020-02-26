package hr.fer.zemris.java.hw03;

import java.nio.file.Files;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.*;

/**
 * This class represents smart script tester. It creates text from node that is
 * created from parsing
 * 
 * @author af
 *
 */
public class SmartScriptTester {
	/**
	 * This method starts when main program starts
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		String docBody = "";
		try {
			docBody = new String(Files.readAllBytes(Paths.get("src/test/resources/doc4.txt")), StandardCharsets.UTF_8);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		SmartScriptParser parser = null;
		try {
			parser = new SmartScriptParser(docBody);
		} catch (SmartScriptParserException e) {
			System.out.println(e);
			System.out.println("Unable to parse document!");
			System.exit(-1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}

		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = createOriginalDocumentBody(document);
		System.out.println(originalDocumentBody); // should write something like original

		// SmartScriptParser parser2=new SmartScriptParser(originalDocumentBody);
		// content of docBody

	}

	/**
	 * This method creates original document from node created previously
	 * 
	 * @param node from which it creates it
	 * @return document body
	 */
	public static String createOriginalDocumentBody(Node node) {
		int numberOfChildern = node.numberOfChildren();
		String s = "";
		for (int i = 0; i < numberOfChildern; i++) {
			Node child = node.getChild(i);
			if (child instanceof TextNode) {
				s = s + parseText(((TextNode) child).getText());
			}
			if (child instanceof ForLoopNode) {
				s = s + parseForLoop((ForLoopNode) child);
				if (child.numberOfChildren() > 0) {
					s = s + createOriginalDocumentBody(child);
				}
				s = s + "{$END$}";
			}
			if (child instanceof EchoNode) {
				s = s + parseEcho((EchoNode) child);
			}

		}
		return s;
	}

	/**
	 * This method parses echo tag
	 * 
	 * @param echoNode
	 * @return string from parsed tag
	 */
	private static String parseEcho(EchoNode echoNode) {
		Element[] elements = echoNode.elements();
		int numberOfElements = elements.length;
		String s = "{$=";
		for (int i = 0; i < numberOfElements; i++) {
			if (elements[i] instanceof ElementString) {
				s = s + parseString(elements[i]);
			} else {
				s = s + elements[i].asText();
			}
			s = s + " ";
		}
		return s + "$}";
	}

	/**
	 * This method parses for loop node
	 * 
	 * @param forLoopNode that should be parsed
	 * @return string
	 */
	private static String parseForLoop(ForLoopNode forLoopNode) {
		String s = "{$ FOR";
		s = s + " " + forLoopNode.getVariable().asText();
		Element startExpression = forLoopNode.getStartExpression();
		Element endExpression = forLoopNode.getEndExpression();
		Element stepExpression = forLoopNode.getStepExpression();

		s = s + " ";
		if (startExpression instanceof ElementString) {
			s = s + parseString(startExpression);
		} else {
			s = s + startExpression.asText();
		}

		s = s + " ";
		if (endExpression instanceof ElementString) {
			s = s + parseString(endExpression);
		} else {
			s = s + endExpression.asText();
		}

		if (stepExpression == null)
			return s + "$}";
		s = s + " ";
		if (startExpression instanceof ElementString) {
			s = s + parseString(stepExpression);
		} else {
			s = s + stepExpression.asText();
		}

		return s + "$}";

	}

	/**
	 * Parses string so that it adds escape sequences where needed
	 * 
	 * @param element string
	 * @return string
	 */
	private static String parseString(Element element) {
		char[] data = element.asText().toCharArray();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < data.length; i++) {
			if (data[i] == '\"' || data[i] == '\\') {
				sb.append('\\');
			}
			sb.append(data[i]);
		}
		return "\"" + sb.toString() + "\"";
	}

	/**
	 * This method parses text so that it adds escape sequences where needed
	 * 
	 * @param text to be returned
	 * @return strings
	 */
	private static String parseText(String text) {
		char[] data = text.toCharArray();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < data.length; i++) {
			if (data[i] == '\\' || data[i] == '{') {
				sb.append('\\');
			}

			sb.append(data[i]);
		}
		return sb.toString();
	}

}
