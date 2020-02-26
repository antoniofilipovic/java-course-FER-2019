package hr.fer.zemris.java.hw05.db;

import java.util.List;

/**
 * This class represents implementation of IFilter. It receives in public
 * constructor list of Conditional Expressions that need to be satisfied
 * 
 * @author Antonio Filipović
 *
 */
public class QueryFilter implements IFilter {
	/**
	 * List of conditional expressions;
	 */
	private List<ConditionalExpression> expressions;

	public QueryFilter(List<ConditionalExpression> expressions) {
		super();
		this.expressions = expressions;
	}

	@Override
	public boolean accepts(StudentRecord record) {
		for (ConditionalExpression c : expressions) {
			IFieldValueGetter getter = c.getFieldGetter();
			String fieldValue = getter.get(record);
			String toCompare = c.getStringLiteral();
			IComparisonOperator operator = c.getComparisonOperator();
			if (!operator.satisfied(fieldValue, toCompare)) {
				return false;
			}
		}
		return true;
	}

}
