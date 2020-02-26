package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * This class represents tree writer. It constructs text file from generated
 * document node.
 * 
 * @author af
 *
 */
public class TreeWriter {

	/**
	 * This method starts when main program starts
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		String docBody = "";
		try {
			docBody = new String(Files.readAllBytes(Paths.get("src/test/resources/osnovni.smscr")),
					StandardCharsets.UTF_8);
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
		WriterVisitor visitor = new WriterVisitor();
		parser.getDocumentNode().accept(visitor);

		System.out.println(visitor.getString());

	}

	/**
	 * This class represents impl of visitor
	 * 
	 * @author af
	 *
	 */
	private static class WriterVisitor implements INodeVisitor {
		/**
		 * Text
		 */
		private String text = "";

		@Override
		public void visitTextNode(TextNode node) {
			text = text + parseText((node).getText());

		}

		@Override
		public void visitForLoopNode(ForLoopNode forLoopNode) {
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

			if (stepExpression == null) {
				text = text + s + "$}";
				return;
			}

			s = s + " ";
			if (startExpression instanceof ElementString) {
				s = s + parseString(stepExpression);
			} else {
				s = s + stepExpression.asText();
			}
			text = text + s + "$}";

			for (int i = 0; i < forLoopNode.numberOfChildren(); i++) {
				forLoopNode.getChild(i).accept(this);
			}
			text = text + "{$END$}";

			return;

		}

		@Override
		public void visitEchoNode(EchoNode echoNode) {
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
			text = text + s + "$}";
			return;

		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			for (int index = 0; index < node.numberOfChildren(); index++) {
				node.getChild(index).accept(this);

			}

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

		public String getString() {
			return text;
		}

	}

}
