package hr.fer.zemris.java.hw05.db;

import java.util.Arrays;

/**
 * This class represents lexer. It creates tokens which can be of type
 * SmartScriptTokenType or throws SmartScriptLexerException if string coudnt be
 * parsed. Receives string at beginning.
 * 
 * @author Antonio Filipović
 *
 */

public class QueryLexer {
	/**
	 * This private variable represents array of characters made from string
	 */
	private char[] data;
	/**
	 * This is current token
	 */
	private QueryToken token;
	/**
	 * This is index of first char that wasnt processed.
	 */
	private int currentIndex;

	/**
	 * This is list of regular operators
	 */
	private static final String[] regularOperators = new String[] { ">", "<", "!","=" };

	/**
	 * This is public constructor for lexer
	 * 
	 * @param text that will be processed
	 * @throws SmartScriptLexerException if text is null
	 */

	public QueryLexer(String text) {
		if (text == null) {
			throw new QueryLexerException("String must not be null.");
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

	public QueryToken nextToken() {
		token = extractNextToken();
		return token;

	}

	/**
	 * This method extracts next token. It takes calls other methods.
	 * 
	 * @return next {@link SmartScriptToken}
	 */

	private QueryToken extractNextToken() {
		if (token != null && token.getType() == QueryTokenType.EOF) {
			throw new QueryLexerException("EOF was reached!");
		}
		skipBlanks();
		if (currentIndex >= data.length) {
			token = new QueryToken(QueryTokenType.EOF, null);
			return token;
		}
		
		if (Arrays.asList(regularOperators).contains(String.valueOf(data[currentIndex]))) {
			return getOperator();
		}
		if (data[currentIndex] == '\"') {
			currentIndex++;
			return getString();
		}
		if (Character.isLetter(data[currentIndex])) {
			return getTextToken();
		}
		throw new QueryLexerException("Unsupported  sign in text.");

	}

	/**
	 * Returns next token from text that is not string or symbol. Text token can be
	 * operator(like) logical operator(and) or fieldname
	 * 
	 * @return next text token
	 */
	private QueryToken getTextToken() {
		StringBuilder sb = new StringBuilder();
		while (currentIndex < data.length && Character.isLetter(data[currentIndex])) {
			sb.append(data[currentIndex++]);
		}
		String s = sb.toString();
		if (s.equals("LIKE")) {
			return new QueryToken(QueryTokenType.OPERATOR, "LIKE");
		}
		if (s.toLowerCase().equals("and")) {
			return new QueryToken(QueryTokenType.LOGICAL_OPERATOR, "and");
		}
		return new QueryToken(QueryTokenType.FIELDNAME, s);
	}

	/**
	 * This method returns next operator >=,>,!=,=,<= If it receives == it returns
	 * two tokens(one now and next when next time nextToken is called).
	 * 
	 * @return
	 */
	private QueryToken getOperator() {
		StringBuilder sb = new StringBuilder();
		if (currentIndex + 1 < data.length && data[currentIndex + 1] == '=' && data[currentIndex] != '=') {
			sb.append(data[currentIndex++]);
		}
		sb.append(data[currentIndex++]);
		return new QueryToken(QueryTokenType.OPERATOR, sb.toString());
	}

	/**
	 * This method returns string token
	 * 
	 * @return string token
	 */
	private QueryToken getString() {
		StringBuilder sb = new StringBuilder();
		boolean endString = false;
		boolean containsWildcard = false;
		while (currentIndex < data.length) {
			if (data[currentIndex] == '\"') {
				endString = true;
				currentIndex++;
				break;
			}
			if (!(Character.isLetterOrDigit(data[currentIndex]) || (data[currentIndex] == '*')) ) {
				throw new QueryLexerException("Unsupporeted sign in string.");
			}
			if (data[currentIndex] == '*' && token.getType()==QueryTokenType.OPERATOR
					&& token.getValue().equals("LIKE")) {
				if (containsWildcard) {
					throw new QueryLexerException("String after like operator cannot contain two wildcards.");
				}
				containsWildcard = true;
			}
			sb.append(data[currentIndex++]);
		}
		if (!endString) {
			throw new QueryLexerException("There wasn't end string.");
		}
		return new QueryToken(QueryTokenType.STRING, sb.toString());
	}

	/**
	 * This method returns current SmartScriptToken
	 * 
	 * @return current Token
	 */
	public QueryToken getToken() {
		return token;
	}

	/**
	 * Skips blanks.
	 */
	//bolje sa isWhiteSpace 
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