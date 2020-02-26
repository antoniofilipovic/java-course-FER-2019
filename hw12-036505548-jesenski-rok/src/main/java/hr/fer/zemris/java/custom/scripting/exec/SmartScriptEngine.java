package hr.fer.zemris.java.custom.scripting.exec;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Stack;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * This class represents engine for smart scripts
 * 
 * @author af
 *
 */
public class SmartScriptEngine {
	/**
	 * node
	 */
	private DocumentNode documentNode;
	/**
	 * Context
	 */
	private RequestContext requestContext;
	/**
	 * Stack
	 */
	private ObjectMultistack multistack = new ObjectMultistack();

	/**
	 * Public constructor for engine
	 * 
	 * @param documentNode   node
	 * @param requestContext ctxt
	 */
	public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
		this.documentNode = documentNode;
		this.requestContext = requestContext;

	}

	/**
	 * This class represents implementation of visitor
	 */
	private INodeVisitor visitor = new INodeVisitor() {

		@Override
		public void visitTextNode(TextNode node) {
			try {
				requestContext.write(node.getText());

			} catch (IOException e) {
				System.out.println(e.getMessage());
			}

		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			ValueWrapper startExpression = new ValueWrapper(node.getStartExpression().asText());
			ValueWrapper endExpression = new ValueWrapper(node.getEndExpression().asText());
			ValueWrapper stepExpression = new ValueWrapper(node.getStepExpression().asText());

			String variable = node.getVariable().getValue();
			multistack.push(variable, new ValueWrapper(startExpression.getValue()));

			while (multistack.peek(variable).numCompare(endExpression.getValue()) <= 0) {
				for (int i = 0; i < node.numberOfChildren(); i++) {
					node.getChild(i).accept(this);
				}

				multistack.peek(variable).add(stepExpression.getValue());

			}

			multistack.pop(variable);

		}

		@Override
		public void visitEchoNode(EchoNode node) {
			Element[] elements = node.elements();
			Stack<Object> stack = new Stack<>();
			int numberOfElements = elements.length;
			for (int i = 0; i < numberOfElements; i++) {
				if (elements[i] instanceof ElementConstantDouble || elements[i] instanceof ElementConstantInteger
						|| elements[i] instanceof ElementString) {
					stack.push(new ValueWrapper(elements[i].asText()));
				}
				if (elements[i] instanceof ElementOperator) {
					ValueWrapper value1 = (ValueWrapper) stack.pop();

					ValueWrapper value2 = (ValueWrapper) stack.pop();
					switch (elements[i].asText()) {
					case "*":
						value1.multiply(value2.getValue());
						break;
					case "/":
						value1.divide(value2.getValue());
						break;
					case "+":
						value1.add(value2.getValue());
						break;
					case "-":
						value1.subtract(value2.getValue());
						break;
					default:
						throw new RuntimeException("Unknown operation.");
					}
					stack.push(value1);
				}
				if (elements[i] instanceof ElementVariable) {
					stack.push(new ValueWrapper(multistack.peek(elements[i].asText()).getValue()));
				}
				if (elements[i] instanceof ElementFunction) {
					performOperation(elements[i].asText(), stack);
				}
			}
			outputForElemets(stack);
			return;

		}

		/**
		 * This method represents output
		 * 
		 * @param stack
		 */
		private void outputForElemets(Stack<Object> stack) {
			for (int i = 0; i < stack.size(); i++) {
				ValueWrapper vw = (ValueWrapper) stack.get(i);
				try {
					requestContext.write(String.valueOf(vw.getValue()));
				} catch (IOException e) {
					System.out.println("Exception happend in smart script engine while writing.");
				}
			}

		}

		/**
		 * This method performs operation
		 * 
		 * @param text  text
		 * @param stack stack
		 */
		private void performOperation(String text, Stack<Object> stack) {

			if (text.equals("@sin")) {
				Object object = stack.pop();
				if (object instanceof ValueWrapper) {
					ValueWrapper value = (ValueWrapper) object;
					value.add(0.0);
					stack.push(new ValueWrapper(Math.sin(((double) value.getValue()) * Math.PI / 180)));
					return;
				}
				throw new RuntimeException("Can't perform operation with " + object);
			}
			
			if(text.equals("@now")) {
				
				Object objectFormat = stack.pop();
				if (objectFormat instanceof ValueWrapper) {
					ValueWrapper vwFormat = (ValueWrapper) objectFormat;
					
					try {
						String format = String.valueOf(vwFormat.getValue());
						
						LocalDateTime now = LocalDateTime.now();
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);

				        String formatDateTime = now.format(formatter);
				        
				      

						stack.push(new ValueWrapper(formatDateTime));
					} catch (ClassCastException e) {
						System.out.println(e.getMessage());
						System.exit(-1);
					}
					return;

				}
				throw new RuntimeException();
				
			}

			else if (text.equals("@decfmt")) {
				Object objectFormat = stack.pop();
				Object objectValue = stack.pop();
				if (objectFormat instanceof ValueWrapper && objectValue instanceof ValueWrapper) {
					ValueWrapper vwFormat = (ValueWrapper) objectFormat;
					ValueWrapper vwValue = (ValueWrapper) objectValue;
					try {
						String format = String.valueOf(vwFormat.getValue());
						DecimalFormat dmformat = new DecimalFormat(format);

						double value = (double) vwValue.getValue();
						stack.push(new ValueWrapper(dmformat.format(value)));
					} catch (ClassCastException e) {
						System.out.println(e.getMessage());
						System.exit(-1);
					}
					return;

				}
				throw new RuntimeException();
			}

			else if (text.equals("@dup")) {
				Object value = stack.pop();
				stack.push(value);
				stack.push(value);
				return;
			}

			else if (text.equals("@swap")) {
				Object value1 = stack.pop();
				Object value2 = stack.pop();
				stack.push(value1);
				stack.push(value2);
				return;
			} else if (text.equals("@setMimeType")) {
				Object object = stack.pop();
				if (object instanceof ValueWrapper) {
					ValueWrapper value = (ValueWrapper) object;
					try {
						requestContext.setMimeType(String.valueOf(value.getValue()));
					} catch (ClassCastException e) {
						System.out.println(e.getMessage());
						System.exit(-1);
					}

					return;
				}
				throw new RuntimeException("Error for setMimeType operation.");
			}

			else if (text.equals("@paramGet")) {
				Object defineValue = stack.pop();
				Object name = stack.pop();
				if (name instanceof ValueWrapper) {
					ValueWrapper vlwpr = (ValueWrapper) name;
					ValueWrapper vwValue = (ValueWrapper) defineValue;
					try {
						String value = requestContext.getParameter(String.valueOf(vlwpr.getValue()));
						stack.push(new ValueWrapper(value == null ? vwValue.getValue() : value));
					} catch (ClassCastException e) {
						System.out.println(e.getMessage());
						System.exit(-1);
					}

					return;
				}
				throw new RuntimeException("Can't perform paramget.");

			}

			else if (text.equals("@pparamGet")) {
				Object defineValue = stack.pop();
				Object name = stack.pop();
				if (name instanceof ValueWrapper) {
					ValueWrapper vlwpr = (ValueWrapper) name;
					ValueWrapper vlwprdefineValue = (ValueWrapper) defineValue;
					try {
						String value = requestContext.getPersistentParameter(String.valueOf(vlwpr.getValue()));
						stack.push(new ValueWrapper(value == null ? vlwprdefineValue.getValue() : value));
					} catch (ClassCastException e) {
						System.out.println(e.getMessage());
						System.exit(-1);
					}
					return;
				}
				throw new RuntimeException("Can't perform pparamget.");
			}

			else if (text.equals("@pparamSet")) {
				Object name = stack.pop();
				Object value = stack.pop();
				if (name instanceof ValueWrapper && value instanceof ValueWrapper) {
					ValueWrapper vwName = (ValueWrapper) name;
					ValueWrapper vwValue = (ValueWrapper) value;
					try {
						String nameString = String.valueOf(vwName.getValue());
						String valueString = String.valueOf(vwValue.getValue());
						requestContext.setPersistentParameter(nameString, valueString);

					} catch (ClassCastException e) {
						System.out.println(e.getMessage());
						System.exit(-1);
					}
					return;
				}
				throw new RuntimeException("Can't perform pparamset.");
			}

			else if (text.equals("@pparamDel")) {
				Object name = stack.pop();
				if (name instanceof ValueWrapper) {
					ValueWrapper vwName = (ValueWrapper) name;
					try {
						String nameString = String.valueOf(vwName.getValue());
						requestContext.removePersistentParameter(nameString);

					} catch (ClassCastException e) {
						System.out.println(e.getMessage());
						System.exit(-1);
					}
					return;
				}
				throw new RuntimeException("Can't perform pparamdel.");
			} else if (text.equals("@tparamGet")) {
				Object defineValue = stack.pop();
				Object name = stack.pop();
				if (name instanceof ValueWrapper && defineValue instanceof ValueWrapper) {
					ValueWrapper vlwpr = (ValueWrapper) name;
					ValueWrapper vwValue = (ValueWrapper) defineValue;
					try {
						String value = requestContext.getTemporaryParameter(String.valueOf(vlwpr.getValue()));
						stack.push(new ValueWrapper(value == null ? vwValue.getValue() : value));
					} catch (ClassCastException e) {
						System.out.println(e.getMessage());
						System.exit(-1);
					}

					return;
				}
				throw new RuntimeException("Can't perform tparamGet.");
			}

			else if (text.equals("@tparamSet")) {
				Object name = stack.pop();
				Object value = stack.pop();
				if (name instanceof ValueWrapper && value instanceof ValueWrapper) {
					ValueWrapper vwName = (ValueWrapper) name;
					ValueWrapper vwValue = (ValueWrapper) value;
					try {
						String nameString = String.valueOf(vwName.getValue());
						String valueString = String.valueOf(vwValue.getValue());
						requestContext.setTemporaryParameter(nameString, valueString);

					} catch (ClassCastException e) {
						System.out.println(e.getMessage());
						System.exit(-1);
					}
					return;
				}
				throw new RuntimeException("Can't perform pparamset.");
			}

			else if (text.equals("@tparamDel")) {
				Object name = stack.pop();
				if (name instanceof ValueWrapper) {
					ValueWrapper vwName = (ValueWrapper) name;
					try {
						String nameString = String.valueOf(vwName.getValue());
						requestContext.removeTemporaryParameter(nameString);

					} catch (ClassCastException e) {
						System.out.println(e.getMessage());
						System.exit(-1);
					}
					return;
				}
				throw new RuntimeException("Can't perform tparamdel.");
			}
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			for (int index = 0; index < node.numberOfChildren(); index++) {
				node.getChild(index).accept(this);

			}

		}

	};

	/**
	 * This method exectures
	 */
	public void execute() {
		documentNode.accept(visitor);

	}

}
