package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;



class QueryLexerTest {
	
	@Test
	public void testNotNull() {
		QueryLexer lexer = new QueryLexer("");

		assertNotNull(lexer.nextToken(), "Token was expected but null was returned.");
	}

	@Test
	public void testNullInput() {
		// must throw!
		assertThrows(QueryLexerException.class, () -> new QueryLexer(null));
	}

	@Test
	public void testEmpty() {
		QueryLexer lexer = new QueryLexer("");

		assertEquals(QueryTokenType.EOF, lexer.nextToken().getType(),
				"Empty input must generate only EOF token.");
	}

	@Test
    public void queryJmbagNoSpacesTest() {
        QueryLexer lexer = new QueryLexer("jmbag=\"0036505548\"");

        QueryToken correctData[] = {
                new QueryToken( QueryTokenType.FIELDNAME,"jmbag"),
                new QueryToken(QueryTokenType.OPERATOR,"="),
                new QueryToken( QueryTokenType.STRING,"0036505548"),
                new QueryToken( QueryTokenType.EOF,null)
        };
        checkTokenStream(lexer, correctData);
    }

    @Test
    public void queryLastNameSpacesTest() {
        QueryLexer lexer = new QueryLexer(" lastName >= \"Messi\"");

        QueryToken correctData[] = {
        		   new QueryToken( QueryTokenType.FIELDNAME,"lastName"),
                   new QueryToken(QueryTokenType.OPERATOR,">="),
                   new QueryToken( QueryTokenType.STRING,"Messi"),
                   new QueryToken( QueryTokenType.EOF,null)
        };
        checkTokenStream(lexer, correctData);
    }

    @Test
    public void queryAndTest() {
        QueryLexer lexer = new QueryLexer("fiRStName!=\"Leo\" aNd lastName LIKE \"B*ć\"");

        QueryToken correctData[] = {
        		 new QueryToken( QueryTokenType.FIELDNAME,"fiRStName"),
                 new QueryToken(QueryTokenType.OPERATOR,"!="),
                 new QueryToken( QueryTokenType.STRING,"Leo"),
                 new QueryToken( QueryTokenType.LOGICAL_OPERATOR,"and"),
                 new QueryToken( QueryTokenType.FIELDNAME,"lastName"),
                 new QueryToken(QueryTokenType.OPERATOR,"LIKE"),
                 new QueryToken( QueryTokenType.STRING,"B*ć"),
                 new QueryToken( QueryTokenType.EOF,null)

        };
        checkTokenStream(lexer, correctData);
    }
    
    @Test
    public void queryTwoAndTest() {
        QueryLexer lexer = new QueryLexer("lastName>=\"A\" aNd firstName<=\"C\" AND jmbag LIKE \"00365*\" ");

        QueryToken correctData[] = {
        		 new QueryToken( QueryTokenType.FIELDNAME,"lastName"),
                 new QueryToken(QueryTokenType.OPERATOR,">="),
                 new QueryToken( QueryTokenType.STRING,"A"),
                 new QueryToken( QueryTokenType.LOGICAL_OPERATOR,"and"),
                 
                 new QueryToken( QueryTokenType.FIELDNAME,"firstName"),
                 new QueryToken(QueryTokenType.OPERATOR,"<="),
                 new QueryToken( QueryTokenType.STRING,"C"),
                 new QueryToken( QueryTokenType.LOGICAL_OPERATOR,"and"),
                 
                 
                 new QueryToken( QueryTokenType.FIELDNAME,"jmbag"),
                 new QueryToken(QueryTokenType.OPERATOR,"LIKE"),
                 new QueryToken( QueryTokenType.STRING,"00365*"),
              
                 new QueryToken( QueryTokenType.EOF,null)

        };
        checkTokenStream(lexer, correctData);
    }
    @Test
    public void LexerExceptionTest() {
    	QueryLexer lexer = new QueryLexer("lastName>=\"Ant*on*\"");
    	lexer.nextToken();
    	lexer.nextToken();
    	assertThrows(QueryLexerException.class, ()->lexer.nextToken());
    }



    private void checkTokenStream(QueryLexer lexer, QueryToken[] correctData) {
		int counter = 0;
		for (QueryToken expected : correctData) {
			QueryToken actual = lexer.nextToken();
			String msg = "Checking token " + counter + ":";
			assertEquals(expected.getValue(), actual.getValue(), msg);
			assertEquals(expected.getType(), actual.getType(), msg);
			
			counter++;
		}
	}

}
