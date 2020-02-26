package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.List;

/**
 * This is parser implementation used to create Conditional Expressions
 * 
 * @author Antonio Filipović
 *
 */
public class QueryParser {
	/**
	 * Conditional expressions from query are stored in list
	 */
	private List<ConditionalExpression> expressions;
	/**
	 * Lexer for parser
	 */
	private QueryLexer lexer;
	/**
	 * Current token
	 */
	private QueryToken token;
	/**
	 * Valid field names
	 */
	private static final String[] fieldNames = new String[] { "jmbag", "firstName", "lastName" };

	/**
	 * Public parser constructor
	 */
	public QueryParser(String s) {

		if (s == null) {
			System.out.println("String to parse was null.");
			return;
		}
		expressions = new ArrayList<>();
		lexer = new QueryLexer(s);
		try {
			startParsing();
		} catch (QueryLexerException e) {
			throw new QueryParserException("Query was not valid." + e.getMessage());

		}
	}

	/**
	 * Method returns true if query was of of the form jmbag="xxx", this query form
	 * is direct query
	 * 
	 * @return true if it is direct query
	 */
	public boolean isDirectQuery() {
		if (expressions.size() != 1) {
			return false;
		}
		ConditionalExpression conditionalExpression = expressions.get(0);
		if (conditionalExpression.getComparisonOperator() == ComparisonOperators.EQUALS
				&& conditionalExpression.getFieldGetter() == FieldValueGetters.JMBAG) {
			return true;
		}
		return false;
	}

	/**
	 * Method returns the string which was given in equality comparison in direct
	 * query.
	 * 
	 * @throws IllegalStateException if the query was not a direct one
	 * @return String if it was direct query
	 */
	public String getQueriedJMBAG() {
		if (isDirectQuery()) {
			return expressions.get(0).getStringLiteral();
		}
		throw new IllegalStateException("It wasn't direct query");
	}

	/**
	 * Returns all querys
	 * 
	 * @return list of querys
	 */
	public List<ConditionalExpression> getQuery() {
		return expressions;
	}

	/**
	 * Method parses received text by calling next token from lexer Every command
	 * must consist of Field name , operator and string Exception is thrown if that
	 * order is not valid
	 */
	private void startParsing() {
		token = lexer.nextToken();
		while (token.getType() != QueryTokenType.EOF) {
			IFieldValueGetter fieldValueGetter = getFieldName();
			IComparisonOperator comparisonOperator = getOperator();
			String stringToCompare = getString();

			expressions.add(new ConditionalExpression(fieldValueGetter, stringToCompare, comparisonOperator));
			token = lexer.nextToken();
			if (token.getType() == QueryTokenType.LOGICAL_OPERATOR) {
				token = lexer.nextToken();
				if (token.getValue() == QueryTokenType.EOF) {
					throw new QueryParserException("Query cannot end with and operator.");
				}
			}
		}
	}

	/**
	 * Returns field name getter
	 * 
	 * @return one of FieldValueGetters
	 * @throws QueryParserException if token type was not FieldValueGetters
	 */
	private IFieldValueGetter getFieldName() {
		if (token.getType() != QueryTokenType.FIELDNAME) {
			throw new QueryParserException("Field name was expected.");
		}
		String fieldName = token.getValue().toString();
		if (fieldName.equals("jmbag")) {
			return FieldValueGetters.JMBAG;
		} else if (fieldName.equals("lastName")) {
			return FieldValueGetters.LAST_NAME;
		} else if (fieldName.equals("firstName")) {
			return FieldValueGetters.FIRST_NAME;
		}
		throw new QueryParserException("Field name was not valid.");
	}

	/**
	 * Method gets operator
	 * 
	 * @return one of Comparison Operators
	 * @throws QueryParserException if token was not Operator
	 */
	// bolja implementacija getOperator se mogao rijesiti preko staticke mape
	// Map<String, IComparisonOperator> i statickog inicijalizacijskog bloka.
	private IComparisonOperator getOperator() {
		token = lexer.nextToken();
		if (token.getType() != QueryTokenType.OPERATOR) {
			throw new QueryParserException("Operator was expected.");
		}
		String operator = token.getValue().toString();
		if (operator.equals(">")) {
			return ComparisonOperators.GREATER;
		} else if (operator.equals(">=")) {
			return ComparisonOperators.GREATER_OR_EQUALS;
		} else if (operator.equals("<=")) {
			return ComparisonOperators.LESS_OR_EQUALS;
		} else if (operator.equals("<")) {
			return ComparisonOperators.LESS;
		} else if (operator.equals("!=")) {
			return ComparisonOperators.NOT_EQUALS;
		} else if (operator.equals("=")) {
			return ComparisonOperators.EQUALS;
		} else {
			return ComparisonOperators.LIKE;
		}
	}

	/**
	 * Method gets String from string token
	 * 
	 * @return string
	 * @throws QueryParserException if token type is not string
	 */
	private String getString() {
		token = lexer.nextToken();
		if (token.getType() != QueryTokenType.STRING) {
			throw new QueryParserException("String was expected");
		}
		return token.getValue().toString();
	}

}
