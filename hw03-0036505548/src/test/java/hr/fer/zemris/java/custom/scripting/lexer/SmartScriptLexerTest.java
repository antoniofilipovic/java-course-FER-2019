package hr.fer.zemris.java.custom.scripting.lexer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


class SmartScriptLexerTest {
	
	
	@Test
	public void testNotNull() {
		SmartScriptLexer lexer = new SmartScriptLexer("");

		assertNotNull(lexer.nextToken(), "Token was expected but null was returned.");
	}

	@Test
	public void testNullInput() {
		// must throw!
		assertThrows(SmartScriptLexerException.class, () -> new SmartScriptLexer(null));
	}

	@Test
	public void testEmpty() {
		SmartScriptLexer lexer = new SmartScriptLexer("");

		assertEquals(SmartScriptTokenType.EOF, lexer.nextToken().getType(),
				"Empty input must generate only EOF token.");
	}

	@Test
	public void testGetReturnsLastNext() {
		// Calling getToken once or several times after calling nextToken must return
		// each time what nextToken returned...
		SmartScriptLexer lexer = new SmartScriptLexer("");

		SmartScriptToken token = lexer.nextToken();
		assertEquals(token, lexer.getToken(), "getToken returned different token than nextToken.");
		assertEquals(token, lexer.getToken(), "getToken returned different token than nextToken.");
	}

	@Test
	public void testRadAfterEOF() {
		SmartScriptLexer lexer = new SmartScriptLexer("");

		// will obtain EOF
		lexer.nextToken();
		// will throw!
		assertThrows(SmartScriptLexerException.class, () -> lexer.nextToken());
	}

	@Test
	public void testNoActualContent() {
		// When input is only of spaces, tabs, newlines, etc...
		SmartScriptLexer lexer = new SmartScriptLexer("   \r\n\t   ");
		SmartScriptToken correctData[] = { new SmartScriptToken(SmartScriptTokenType.TEXT, "   \r\n\t   ") };
		checkTokenStream(lexer, correctData);

	}

	@Test
	public void testTwoWords() {
		// Lets check for several words...
		SmartScriptLexer lexer = new SmartScriptLexer("Štefanija\r\n\t Automobili.");

		SmartScriptToken correctData[] = {
				new SmartScriptToken(SmartScriptTokenType.TEXT, "Štefanija\r\n\t Automobili."),
				new SmartScriptToken(SmartScriptTokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void testWordStartingWithEscape() {
		SmartScriptLexer lexer = new SmartScriptLexer("\\\\1st  \r\n\t");

		// We expect the following stream of tokens
		SmartScriptToken correctData[] = { new SmartScriptToken(SmartScriptTokenType.TEXT, "\\1st  \r\n\t"),
				new SmartScriptToken(SmartScriptTokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void testWordStartingWithEscape2() {
		SmartScriptLexer lexer = new SmartScriptLexer("\\{ 1st \r\n");

		// We expect the following stream of tokens
		SmartScriptToken correctData[] = { new SmartScriptToken(SmartScriptTokenType.TEXT, "{ 1st \r\n"),
				new SmartScriptToken(SmartScriptTokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void testWordStartingWithEscapeThrowException() {
		SmartScriptLexer lexer = new SmartScriptLexer("\\1 1st \r\n");
		assertThrows(SmartScriptLexerException.class, () -> lexer.nextToken());

	}

	@Test
	public void testWordStartingWithEscapeThrowException2() {
		SmartScriptLexer lexer = new SmartScriptLexer(" \\");
		assertThrows(SmartScriptLexerException.class, () -> lexer.nextToken());

	}

	@Test
	public void testOfTag() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$ FOR i -1 10 1 $}");

		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.TAG, null));
		lexer.setState(SmartScriptLexerState.TAG);

		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.TAGNAME, "FOR"));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.VARIABLE, "i"));

		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.INTEGER, -1));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.INTEGER, 10));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.INTEGER, 1));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.TAG, null));

	}

	@Test
	public void testOfTag1() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$ FOR sco_re \"-1\"10 \"1\" $}");

		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.TAG, null));
		lexer.setState(SmartScriptLexerState.TAG);

		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.TAGNAME, "FOR"));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.VARIABLE, "sco_re"));

		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.STRING, "-1"));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.INTEGER, 10));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.STRING, "1"));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.TAG, null));

	}

	@Test
	public void testOfTag2() {
		SmartScriptLexer lexer = new SmartScriptLexer("A tag follows\r\n {$= \"Joe \\\"Long\\\" Smith\"$}.");

		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.TEXT, "A tag follows\r\n "));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.TAG, null));
		lexer.setState(SmartScriptLexerState.TAG);

		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.TAGNAME, "="));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.STRING, "Joe \"Long\" Smith"));

		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.TAG, null));
		lexer.setState(SmartScriptLexerState.TEXT);
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.TEXT, "."));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.EOF, null));

	}

	@Test
	public void testOfTag3() {
		SmartScriptLexer lexer = new SmartScriptLexer("Example \\{$=1$}. Now actually write one {$=1$}");

		checkToken(lexer.nextToken(),
				new SmartScriptToken(SmartScriptTokenType.TEXT, "Example {$=1$}. Now actually write one "));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.TAG, null));
		lexer.setState(SmartScriptLexerState.TAG);

		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.TAGNAME, "="));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.INTEGER, 1));

		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.TAG, null));
		lexer.setState(SmartScriptLexerState.TEXT);
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.EOF, null));

	}

	@Test
	public void testOfTag4() {
		SmartScriptLexer lexer = new SmartScriptLexer("Example { bla } blu \\{$=1$}. Nothing interesting {=here}.");
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.TEXT,
				"Example { bla } blu {$=1$}. Nothing interesting {=here}."));

	}

	@Test
	public void testOfTag5() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$ FOR @decfmt @sin+10*^+1.23iPer_20jg-1.35bbb\"1\" \"prciguz\\r\\n \\\\ \\\" \" $} " );

		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.TAG, null));
		lexer.setState(SmartScriptLexerState.TAG);
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.TAGNAME, "FOR"));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.FUNCTION, "@decfmt"));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.FUNCTION, "@sin"));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.OPERATOR, "+"));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.INTEGER, 10));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.OPERATOR, "*"));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.OPERATOR, "^"));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.OPERATOR, "+"));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.DOUBLE, 1.23));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.VARIABLE, "iPer_20jg"));

		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.DOUBLE, -1.35));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.VARIABLE, "bbb"));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.STRING, "1"));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.STRING, "prciguz\r\n \\ \" " ));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.TAG, null));

	}
	@Test
	public void testOfTagException1() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$ FOR _blaf $}");

		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.TAG, null));
		lexer.setState(SmartScriptLexerState.TAG);
		lexer.nextToken();
		assertThrows(SmartScriptLexerException.class, () -> lexer.nextToken());
	}
	@Test
	public void testOfTagException2() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$ FOR \"  prciguz234\\2 \" $}");

		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.TAG, null));
		lexer.setState(SmartScriptLexerState.TAG);
		lexer.nextToken();
		assertThrows(SmartScriptLexerException.class, () -> lexer.nextToken());
	}
	@Test
	public void testOfTagException3() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$ FOR \"  prciguz234\\2 \" $}");

		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.TAG, null));
		lexer.setState(SmartScriptLexerState.TAG);
		lexer.nextToken();
		assertThrows(SmartScriptLexerException.class, () -> lexer.nextToken());
	}
	@Test
	public void testOfTagException4() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$ FOR 1.11.1 $}");

		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.TAG, null));
		lexer.setState(SmartScriptLexerState.TAG);
		lexer.nextToken();
		lexer.nextToken();
		assertThrows(SmartScriptLexerException.class, () -> lexer.nextToken());
	}
	@Test
	public void testOfTagException5() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$ FOR sudacAmater; $}");

		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.TAG, null));
		lexer.setState(SmartScriptLexerState.TAG);
		lexer.nextToken();
		lexer.nextToken();
		assertThrows(SmartScriptLexerException.class, () -> lexer.nextToken());
	}
	
	@Test
	public void testFinal() {
		SmartScriptLexer lexer = new SmartScriptLexer("This is sample text.{$ FOR i 1 10 1 $}This is {$= i $}-th time this message is generated.{$END$}");
//		{$FOR i 0 10 2 $}
//		 sin({$=i$}^2) = {$= i i * @sin "0.000" @decfmt $}
//		{$END$}
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.TEXT, "This is sample text."));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.TAG, null));
		lexer.setState(SmartScriptLexerState.TAG);
		
		
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.TAGNAME, "FOR"));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.VARIABLE, "i"));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.INTEGER, 1));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.INTEGER, 10));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.INTEGER, 1));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.TAG, null));
		
		lexer.setState(SmartScriptLexerState.TEXT);
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.TEXT, "This is "));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.TAG, null));
		lexer.setState(SmartScriptLexerState.TAG);
		
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.TAGNAME, "="));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.VARIABLE, "i"));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.TAG, null));
		
		lexer.setState(SmartScriptLexerState.TEXT);
		
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.TEXT, "-th time this message is generated."));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.TAG, null));
		
		lexer.setState(SmartScriptLexerState.TAG);
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.TAGNAME, "END"));
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.TAG, null));
		lexer.setState(SmartScriptLexerState.TEXT);
		checkToken(lexer.nextToken(), new SmartScriptToken(SmartScriptTokenType.EOF, null));
		

	}


	private void checkTokenStream(SmartScriptLexer lexer, SmartScriptToken[] correctData) {
		int counter = 0;
		for (SmartScriptToken expected : correctData) {
			SmartScriptToken actual = lexer.nextToken();
			String msg = "Checking token " + counter + ":";
			assertEquals(expected.getValue(), actual.getValue(), msg);
			assertEquals(expected.getType(), actual.getType(), msg);
			
			counter++;
		}
	}

	private void checkToken(SmartScriptToken actual, SmartScriptToken expected) {
		String msg = "Token are not equal.";
		assertEquals(expected.getType(), actual.getType(), msg);
		assertEquals(expected.getValue(), actual.getValue(), msg);
	}
}
