package hr.fer.zemris.java.hw06.shell.massrename.lexer;


public class NameBuilderLexer {
	/**
	 * This private variable represents array of characters made from string
	 */
	private char[] data;
	/**
	 * This is current token
	 */
	private NameBuilderToken token;
	/**
	 * This is index of first char that wasnt processed.
	 */
	private int currentIndex;
	/**
	 * This is current lexer state. It changes from outside.
	 */
	private NameBuilderLexerState state = NameBuilderLexerState.TEXT;

	/**
	 * This is public constructor for lexer
	 * 
	 * @param text that will be processed
	 * @throws NameBuilderLexerException if text is null
	 */

	public NameBuilderLexer(String text) {
		if (text == null) {
			throw new NameBuilderLexerException("String must not be null.");
		}
		data = text.toCharArray();
		token = null;
		currentIndex = 0;
	}

	/**
	 * This method returns next token
	 * 
	 * @return next {@link NameBuilderToken}
	 */

	public NameBuilderToken nextToken() {
		token = extractNextToken();
		return token;

	}

	/**
	 * This method extracts next token. It takes calls other methods depending on
	 * lexer state.
	 * 
	 * @return next {@link NameBuilderToken}
	 */

	private NameBuilderToken extractNextToken() {
		if (token != null && token.getType() == NameBuilderTokenType.EOF) {
			throw new NameBuilderLexerException("EOF was reached!");
		}
		if (currentIndex >= data.length) {
			token = new NameBuilderToken(NameBuilderTokenType.EOF, null);
			return token;
		}
		if (data[currentIndex] == '$' && currentIndex + 1 < data.length && data[currentIndex + 1] == '{') {
			currentIndex += 2;
			return new NameBuilderToken(NameBuilderTokenType.TAG, null);
		}
		if (state == NameBuilderLexerState.TEXT) {
			return textStateMode();
		}
		return tagStateMode();

	}

	/**
	 * This private method returns next token in text state mode
	 * 
	 * @return next token
	 */
	private NameBuilderToken textStateMode() {
		StringBuilder sb = new StringBuilder();
		while (currentIndex < data.length) {
			if (data[currentIndex] == '$' && currentIndex + 1 < data.length && data[currentIndex + 1] == '{') {
				break;
			}
			sb.append(data[currentIndex]);
			currentIndex++;

		}
		token = new NameBuilderToken(NameBuilderTokenType.TEXT, sb.toString());

		return token;
	}

	/**
	 * This class returns next token in tag state mode
	 * 
	 * @return next token
	 */
	private NameBuilderToken tagStateMode() {
		skipBlanks();
		if ((data[currentIndex] == ',') && currentIndex + 1 < data.length) {
			currentIndex++;
			return new NameBuilderToken(NameBuilderTokenType.COMMA, null);
		}
		if (Character.isDigit(data[currentIndex])) {
			return parseNumber();
		}

		if (data[currentIndex] == '}') {
			currentIndex ++;
			return new NameBuilderToken(NameBuilderTokenType.TAG, null);

		}
		throw new NameBuilderLexerException("This sign " + data[currentIndex] + " was not expected!");

	}

	/**
	 * This method parses integer
	 * 
	 * @return integer or zerointeger
	 */
	private NameBuilderToken parseNumber() {
		int startIndex = currentIndex;
		boolean zero=false;
		while (currentIndex < data.length && Character.isDigit(data[currentIndex])) {
			currentIndex++;
		}
		if (data[startIndex] == '0') {
			startIndex++;
			zero=true;
		}
		
		
		String value = new String(data, startIndex, currentIndex - startIndex);

		int parsedInt = Integer.parseInt(value);
		if (zero) {
			return new NameBuilderToken(NameBuilderTokenType.ZERO_NUMBER, parsedInt);
		}
		return new NameBuilderToken(NameBuilderTokenType.NUMBER, parsedInt);
	}

	/**
	 * This method returns current NameBuilderToken
	 * 
	 * @return current Token
	 */
	public NameBuilderToken getToken() {
		return token;
	}

	/**
	 * Sets state of lexer
	 * 
	 * @param state
	 */
	public void setState(NameBuilderLexerState state) {
		if (state == null) {
			throw new NameBuilderLexerException("State cannot be null!");
		}
		this.state = state;
	}

	/**
	 * Skips blanks. only used in string
	 */
	private void skipBlanks() {
		while (currentIndex < data.length) {
			if (Character.isWhitespace(data[currentIndex])) {
				currentIndex++;
				continue;
			}
			return;
		}
	}
}
