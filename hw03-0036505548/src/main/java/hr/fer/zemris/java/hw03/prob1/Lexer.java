package hr.fer.zemris.java.hw03.prob1;

/**
 * This class represents Lexer that will process all tokens in the way it is
 * implemented
 * 
 * @author Antonio Filipovic
 *
 */

public class Lexer {
	/**
	 * This is input text
	 */
	private char[] data;
	/**
	 * This is current token
	 */
	private Token token;
	/**
	 * This is current index
	 */
	private int currentIndex;
	/**
	 * This is current lexer state
	 */
	private LexerState state = LexerState.BASIC;

	/**
	 * This public constructor receives text that is tokenised
	 * 
	 * @param text that will be tokenised
	 */
	public Lexer(String text) {
		if (text == null) {
			throw new NullPointerException();
		}
		data = text.toCharArray();
		token = null;
		currentIndex = 0;
	}

	/**
	 * This method returns next token
	 * 
	 * @return next token
	 * @throws LexerException if exception happens
	 */
	public Token nextToken() {
		if (token != null && token.getType() == TokenType.EOF) {
			throw new LexerException();
		}
		skipBlanks();
		if (currentIndex >= data.length) {
			token = new Token(TokenType.EOF, null);
			return token;
		}
		if (state == LexerState.EXTENDED) {
			token = extendedState();
		} else if (Character.isLetter(data[currentIndex]) || data[currentIndex] == '\\') {
			String value = "";
			int startIndex = currentIndex;

			while (currentIndex < data.length && (Character.isLetter(data[currentIndex]) || data[currentIndex] == '\\'
					|| (Character.isDigit(data[currentIndex]) && data[currentIndex - 1] == '\\'))) {

				if (data[currentIndex] == '\\' && data[currentIndex - 1] == '\\') {
					value = value + String.valueOf(data[currentIndex]);
					startIndex = currentIndex + 1;
				} else if (data[currentIndex] == '\\' && data.length == currentIndex + 1) {
					throw new LexerException();
				} else if (Character.isLetter(data[currentIndex]) && currentIndex > 0
						&& data[currentIndex - 1] == '\\') {
					throw new LexerException();
				} else if (data[currentIndex] == '\\') {
					value = value + new String(data, startIndex, currentIndex - startIndex);
					startIndex = currentIndex + 1;
				}
				currentIndex++;
			}
			int endIndex = currentIndex;
			value = value + new String(data, startIndex, endIndex - startIndex);
			token = new Token(TokenType.WORD, value);
		} else if (Character.isDigit(data[currentIndex])) {
			long value = 0;
			while (currentIndex < data.length && Character.isDigit(data[currentIndex])) {
				value = value * 10 + Character.getNumericValue(data[currentIndex]);
				currentIndex++;
				if (value < 0) {
					throw new LexerException();
				}
			}
			token = new Token(TokenType.NUMBER, value);

		} else {
			if (data[currentIndex] == '#') {
				setState(LexerState.EXTENDED);
			}
			token = new Token(TokenType.SYMBOL, data[currentIndex]);
			currentIndex++;
		}
		return token;

	}

	/**
	 * This method returns next token in extended state
	 * 
	 * @return next token
	 */
	private Token extendedState() {
		if (data[currentIndex] == '#') {
			setState(LexerState.BASIC);
			currentIndex++;
			return new Token(TokenType.SYMBOL, data[currentIndex - 1]);

		}
		String value = "";
		while (!(data[currentIndex] == ' ' || data[currentIndex] == '\r' || data[currentIndex] == '\t'
				|| data[currentIndex] == '\n') && data[currentIndex] != '#' && currentIndex < data.length) {
			value = value + data[currentIndex];
			currentIndex++;
		}

		return new Token(TokenType.WORD, value);
	}

	/**
	 * This method returns last generated token It can be call multiple times
	 * 
	 * @return current token
	 */
	public Token getToken() {
		return token;
	}

	public void setState(LexerState state) {
		if (state == null) {
			throw new NullPointerException();
		}
		this.state = state;
	}

	/**
	 * This method skips all blanks in data
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