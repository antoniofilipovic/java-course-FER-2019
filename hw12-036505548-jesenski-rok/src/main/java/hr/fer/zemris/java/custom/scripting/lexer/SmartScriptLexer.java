package hr.fer.zemris.java.custom.scripting.lexer;

import java.util.Arrays;

/**
 * This class represents lexer. It creates tokens which can be of type
 * SmartScriptTokenType or throws SmartScriptLexerException if string coudnt be
 * parsed. Receives string at beginning.
 * 
 * @author Antonio Filipović
 *
 */

public class SmartScriptLexer {
	/**
	 * This private variable represents array of characters made from string
	 */
	private char[] data;
	/**
	 * This is current token
	 */
	private SmartScriptToken token;
	/**
	 * This is index of first char that wasnt processed.
	 */
	private int currentIndex;
	/**
	 * This is current lexer state. It changes from outside.
	 */
	private SmartScriptLexerState state = SmartScriptLexerState.TEXT;
	/**
	 * This is private variable that takes care that tag name is provided after tag
	 */
	private boolean tagNameNotProvided;
	/**
	 * This is list of regular operators
	 */
	private static final String[] regularOperators = new String[] { "+", "-", "*", "/", "^" };

	/**
	 * This is public constructor for lexer
	 * 
	 * @param text that will be processed
	 * @throws SmartScriptLexerException if text is null
	 */

	public SmartScriptLexer(String text) {
		if (text == null) {
			throw new SmartScriptLexerException("String must not be null.");
		}
		data = text.toCharArray();
		token = null;
		currentIndex = 0;
	}

	/**
	 * This method returns next token
	 * 
	 * @return next {@link SmartScriptToken}
	 */

	public SmartScriptToken nextToken() {
		token = extractNextToken();
		return token;

	}

	/**
	 * This method extracts next token. It takes calls other methods depending on
	 * lexer state.
	 * 
	 * @return next {@link SmartScriptToken}
	 */

	private SmartScriptToken extractNextToken() {
		if (token != null && token.getType() == SmartScriptTokenType.EOF) {
			throw new SmartScriptLexerException("EOF was reached!");
		}
		if (currentIndex >= data.length) {
			token = new SmartScriptToken(SmartScriptTokenType.EOF, null);
			return token;
		}
		if (data[currentIndex] == '{' && data[currentIndex + 1] == '$') { // if we stumbled upon tag we send tag
			currentIndex += 2; // i took care of \{$ case in text state mode
			tagNameNotProvided = true; // so that we jump two indexes if that happens
			return new SmartScriptToken(SmartScriptTokenType.TAG, null);
		}
		if (state == SmartScriptLexerState.TEXT) {
			return textStateMode();
		}

		return tagStateMode();

	}

	/**
	 * This private method returns next token in text state mode
	 * 
	 * @return next token
	 */
	private SmartScriptToken textStateMode() {
		StringBuilder sb = new StringBuilder();
		while (currentIndex < data.length) {
			if (data[currentIndex] == '\\') { // if we stumbled upon \ and next is { or \ we append it else exception
				if (currentIndex + 1 == data.length
						|| !(data[currentIndex + 1] == '{' || data[currentIndex + 1] == '\\')) {
					throw new SmartScriptLexerException("This text" + " is not accepted.");
				}
				if (data[currentIndex + 1] == '{') {
					sb.append('{');
				} else {
					sb.append('\\'); // \ samo
				}
				currentIndex += 2;
				continue;
			}
			if (data[currentIndex] == '{' && currentIndex + 1 < data.length && data[currentIndex + 1] == '$') {
				break; // this case and \{ are not intersectable because we skip
						// two indexes before so we dont double check {, if \{ this is the case we
						// append
			}
			sb.append(data[currentIndex]);
			currentIndex++;

		}
		token = new SmartScriptToken(SmartScriptTokenType.TEXT, sb.toString());

		return token;
	}

	/**
	 * This class returns next token in tag state mode
	 * 
	 * @return next token
	 */
	private SmartScriptToken tagStateMode() {
		skipBlanks();
		if (tagNameNotProvided) {
			return tagNameProvider();
		}
		if ((data[currentIndex] == '-' ) && currentIndex + 1 < data.length
				&& Character.isDigit(data[currentIndex + 1])) {
			return parseNumber();
		}
		if (Character.isDigit(data[currentIndex])) {
			return parseNumber();
		}if (currentIndex+2<data.length && Character.toUpperCase(data[currentIndex])=='N' &&  Character.toUpperCase(data[currentIndex+1])=='O' 
				&& Character.toUpperCase(data[currentIndex+2])=='W') {
			currentIndex+=3;
			return new SmartScriptToken(SmartScriptTokenType.NOW, null);
		}
		
		if (data[currentIndex] == '\"') {
			currentIndex++;
			return parseString();
		}
		if (data[currentIndex] == '$' && currentIndex + 1 < data.length && data[currentIndex + 1] == '}') {
			currentIndex += 2;
			return new SmartScriptToken(SmartScriptTokenType.TAG, null);

		}
		if (data[currentIndex] == '@') {
			currentIndex++;
			return parseFunction();
		}
		if (Arrays.asList(regularOperators).contains(String.valueOf(data[currentIndex]))) {
			currentIndex++;
			return new SmartScriptToken(SmartScriptTokenType.OPERATOR, String.valueOf(data[currentIndex - 1]));
		}
		if (Character.isLetter(data[currentIndex])) {
			String value = goodVariableOrFunctionName();
			return new SmartScriptToken(SmartScriptTokenType.VARIABLE, value);
		}

		throw new SmartScriptLexerException("This sign " + data[currentIndex] + " was not expected!");

	}

	/**
	 * This method provides tag Name, it can be = or regular variable name
	 * 
	 * @return tagname token
	 */
	private SmartScriptToken tagNameProvider() {
		tagNameNotProvided = false;
		if (data[currentIndex] == '=') {
			currentIndex++;
			return new SmartScriptToken(SmartScriptTokenType.TAGNAME, "=");
		}
		return new SmartScriptToken(SmartScriptTokenType.TAGNAME, goodVariableOrFunctionName());
	}

	/**
	 * This method parses number decimal or integer
	 * 
	 * @return integer or double number
	 */
	private SmartScriptToken parseNumber() {
		boolean negative = false;
		boolean isDouble = false;
		if (data[currentIndex] == '-') {
			negative = true;
			currentIndex++;
		}
		int startIndex = currentIndex;
		while (currentIndex < data.length && Character.isDigit(data[currentIndex])) {
			currentIndex++;
		}
		if (data[currentIndex] == '.' && currentIndex + 1 < data.length && Character.isDigit(data[currentIndex + 1])) {
			currentIndex++;
			isDouble = true;
			while (currentIndex < data.length && Character.isDigit(data[currentIndex])) {
				currentIndex++;
			}
		}
		int endIndex = currentIndex;
		String value = new String(data, startIndex, endIndex - startIndex);
		if (isDouble) {
			double parsedDouble = Double.parseDouble(value);
			if (negative)
				parsedDouble *= -1;
			return new SmartScriptToken(SmartScriptTokenType.DOUBLE, parsedDouble);
		}
		int parsedInt = Integer.parseInt(value);
		if (negative)
			parsedInt *= -1;
		return new SmartScriptToken(SmartScriptTokenType.INTEGER, parsedInt);

	}

	/**
	 * This method parses string. Only few escape sequences are valid In java mode
	 * \\n->\n and similar, \\\\->\ ,\\"-> " , in document \"->"
	 * 
	 * @return string token
	 * @throws SmartScriptLexerException if string is not valid
	 */
	private SmartScriptToken parseString() {
		StringBuilder sb = new StringBuilder();
		boolean endString = false;
		while (currentIndex < data.length) {
			if (data[currentIndex] == '\"') {
				currentIndex++;
				endString=true;
				break;
			}
			if (data[currentIndex] == '\\' && currentIndex + 1 < data.length) {

				if (data[currentIndex + 1] == '\\') {
					sb.append("\\");
					currentIndex += 2;
					continue;
				}
				if (data[currentIndex + 1] == '\"') {
					sb.append("\"");
					currentIndex += 2;
					continue;
				}
				if (data[currentIndex + 1] == 'n') {
					sb.append("\n");
					currentIndex += 2;
					continue;
				}
				if (data[currentIndex + 1] == 't') {
					sb.append("\t");
					currentIndex += 2;
					continue;
				}
				if (data[currentIndex + 1] == 'r') {
					sb.append("\r");
					currentIndex += 2;
					continue;
				}
				throw new SmartScriptLexerException("This escape sequence is not valid.");
			}
			sb.append(data[currentIndex]);
			currentIndex++;
		}
		if(!endString) {
			throw new SmartScriptLexerException("String had no end.");
		}
		return new SmartScriptToken(SmartScriptTokenType.STRING, sb.toString());
	}

	/**
	 * Parses function name.Calls {@link goodVariableOrFunctionName because same
	 * names are valid
	 * 
	 * @return function token
	 */
	private SmartScriptToken parseFunction() {
		return new SmartScriptToken(SmartScriptTokenType.FUNCTION, "@" + goodVariableOrFunctionName());

	}

	/**
	 * Returns string if name is valid
	 * 
	 * @return string
	 */
	private String goodVariableOrFunctionName() {
		int startIndex = currentIndex;
		if (currentIndex==data.length || !Character.isLetter(data[currentIndex])) {
			throw new SmartScriptLexerException("Name is not valid variable name.");
		}
		currentIndex++;
		while (currentIndex < data.length && (Character.isLetter(data[currentIndex])
				|| Character.isDigit(data[currentIndex]) || data[currentIndex] == '_')) {
			currentIndex++;
		}
		int endIndex = currentIndex;
		return new String(data, startIndex, endIndex - startIndex);
	}

	/**
	 * This method returns current SmartScriptToken
	 * 
	 * @return current Token
	 */
	public SmartScriptToken getToken() {
		return token;
	}

	/**
	 * Sets state of lexer
	 * 
	 * @param state
	 */
	public void setState(SmartScriptLexerState state) {
		if (state == null) {
			throw new SmartScriptLexerException("State cannot be null!");
		}
		this.state = state;
	}

	/**
	 * Skips blanks. only used in string
	 */
	private void skipBlanks() {
		while (currentIndex < data.length) {
			if (data[currentIndex] == ' ' || data[currentIndex] == '\r' || data[currentIndex] == '\t'
					|| data[currentIndex] == '\n') {
				currentIndex++;
				continue;
			}
			return;
		}
	}

}
