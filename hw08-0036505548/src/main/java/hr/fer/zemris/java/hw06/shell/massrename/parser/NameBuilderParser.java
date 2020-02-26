package hr.fer.zemris.java.hw06.shell.massrename.parser;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.massrename.NameBuilder;
import hr.fer.zemris.java.hw06.shell.massrename.NameBuilderComposit;
import hr.fer.zemris.java.hw06.shell.massrename.NameBuilderGroup;
import hr.fer.zemris.java.hw06.shell.massrename.NameBuilderGroupPadding;
import hr.fer.zemris.java.hw06.shell.massrename.NameBuilderText;
import hr.fer.zemris.java.hw06.shell.massrename.lexer.*;

/**
 * This class represents namebuilder parser
 * @author af
 *
 */
public class NameBuilderParser {

	/**
	 * Current token
	 */
	private NameBuilderToken token;
	/**
	 * Lexer created
	 */
	private NameBuilderLexer lexer;


	private List<NameBuilder> nameBuilders;

	public NameBuilder getNameBuilder() {
		try {
			startParsing();
		} catch (NameBuilderLexerException e) {
			throw new NameBuilderParserException("Exception happend in lexer."+ e.getMessage());
		}

		return new NameBuilderComposit(nameBuilders);
	}

	/**
	 * constructor, constructs lexer,stack
	 * 
	 * @param s string to be parsed
	 */
	public NameBuilderParser(String s) {
		if (s == null) {
			throw new NameBuilderParserException("Input string must not be null.");
		}
		nameBuilders = new ArrayList<>();
		lexer = new NameBuilderLexer(s);

	}

	/**
	 * Method called for parsing. It gets next token and depending what it is calls
	 * that method. It creates list of namebuilders
	 * 
	 * @return 
	 */
	private void startParsing() {

		token = lexer.nextToken();
		while (token.getType() != NameBuilderTokenType.EOF) {

			if (token.getType() == NameBuilderTokenType.TEXT) {
				nameBuilders.add(new NameBuilderText(token.getValue().toString()));
			}
			if (token.getType() == NameBuilderTokenType.TAG) {
				lexer.setState(NameBuilderLexerState.TAG);
				parseTag();
				lexer.setState(NameBuilderLexerState.TEXT);
			}
		
			token = lexer.nextToken();
			

		}

	}
	/**
	 * This method parses in tag
	 */
	private void parseTag() {
		token = lexer.nextToken();
		int firstNumber=0;
		
		if (token.getType() != NameBuilderTokenType.NUMBER) {
			throw new NameBuilderParserException("Wrong token. Expected  at least one number in tag.");
			
		}
		firstNumber=(Integer) token.getValue();
		
		token = lexer.nextToken();
		if (token.getType() == NameBuilderTokenType.TAG) {
			nameBuilders.add(new NameBuilderGroup(firstNumber));
			return;
		}
			
		if (token.getType() != NameBuilderTokenType.COMMA) {
			throw new NameBuilderParserException("Wrong token. Expected comma in tag.");
		}
		token=lexer.nextToken();
		if(token.getType()==NameBuilderTokenType.ZERO_NUMBER) {
			nameBuilders.add(new NameBuilderGroupPadding(firstNumber, '0', (Integer)token.getValue()));
		}
		else if(token.getType()==NameBuilderTokenType.NUMBER) {
			nameBuilders.add(new NameBuilderGroupPadding(firstNumber, ' ', (Integer)token.getValue()));
		}
		else {
			throw new NameBuilderParserException("Wrong token. Expected number or zeronumber after comma tag.");
		}
		token=lexer.nextToken();
		if (token.getType() == NameBuilderTokenType.TAG) {
			return;
		}
		throw new NameBuilderParserException("Wrong token. Expected one number or two numbers.") ;
	}

}
