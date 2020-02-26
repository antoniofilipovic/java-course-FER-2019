package hr.fer.zemris.java.custom.scripting.exec;
/**
 * This class performs operations on doubles
 * @author af
 *
 */
public class DoubleOperation implements OperationPerformer<Double> {

	@Override
	public Double performOperationAdd(Double val1, Double val2) {
		return val1+val2;
	}

	@Override
	public Double performOperationSub(Double val1, Double val2) {
		return val1-val2;
	}

	@Override
	public Double performOperationMul(Double val1, Double val2) {
		return val1*val2;
	}

	@Override
	public Double performOperationDiv(Double val1, Double val2) {
		if(val2==0) {
			throw new ArithmeticException("Can't divide with zero.");
		}
		return val1/val2;
	}

	@Override
	public int numCompare(Double val1, Double val2) {
		return Double.compare(val1, val2);
	}



}
