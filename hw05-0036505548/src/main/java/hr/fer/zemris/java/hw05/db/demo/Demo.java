package hr.fer.zemris.java.hw05.db.demo;

import hr.fer.zemris.java.hw05.db.QueryLexer;
import hr.fer.zemris.java.hw05.db.QueryToken;

/**
 * My test class
 * @author af
 *
 */
public class Demo {

	public static void main(String[] args) {
		QueryToken token;
		QueryLexer lexer = new QueryLexer("lastName>=\"A\" aNd firstName<=\"C\" AND jmbag LIKE \"00365*\" ");
		token=lexer.nextToken();
        System.out.println(token.getType()+" "+token.getValue());
        token=lexer.nextToken();
        System.out.println(token.getType()+" "+token.getValue());
        token=lexer.nextToken();
        System.out.println(token.getType()+" "+token.getValue());
        
        
        
        token=lexer.nextToken();
        System.out.println(token.getType()+" "+token.getValue());
        token=lexer.nextToken();
        System.out.println(token.getType()+" "+token.getValue());
        token=lexer.nextToken();
        System.out.println(token.getType()+" "+token.getValue());
        
        token=lexer.nextToken();
        System.out.println(token.getType()+" "+token.getValue());
        token=lexer.nextToken();
        System.out.println(token.getType()+" "+token.getValue());
        token=lexer.nextToken();
        System.out.println(token.getType()+" "+token.getValue());
        token=lexer.nextToken();
        System.out.println(token.getType()+" "+token.getValue());
        token=lexer.nextToken();
        System.out.println(token.getType()+" "+token.getValue());
        token=lexer.nextToken();
        System.out.println(token.getType()+" "+token.getValue());
        
        
        
        

	}

}
