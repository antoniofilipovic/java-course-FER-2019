package hr.fer.zemris.java.custom.scripting.exec;

/**
 * This class performs operations on integers
 * @author af
 *
 */
public class IntOperation implements OperationPerformer<Integer> {


	@Override
	public Integer performOperationAdd(Integer val1, Integer val2) {
		return val1+val2;
	}

	@Override
	public Integer performOperationSub(Integer val1, Integer val2) {
		return val1-val2;
	}

	@Override
	public Integer performOperationMul(Integer val1, Integer val2) {
		return val1*val2;
	}

	@Override
	public Integer performOperationDiv(Integer val1, Integer val2) {
		if(val2==0) {
			throw new ArithmeticException("Can't divide with zero.");
		}return val1/val2;
	}

	@Override
	public int numCompare(Integer val1, Integer val2) {
		return Integer.compare(val1, val2);
	}

	
	
	

}
