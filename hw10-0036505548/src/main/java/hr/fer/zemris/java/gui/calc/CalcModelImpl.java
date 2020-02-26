package hr.fer.zemris.java.gui.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;
/**
 * This class represents implementation of calc model.
 * @author af
 *
 */
public class CalcModelImpl implements CalcModel{
	/**
	 * All listeners
	 */
	private List<CalcValueListener> listeners;
	/**
	 * If current number is postive
	 */
	private boolean isPositive=true;
	/**
	 * If model is editable
	 */
	private boolean isEditable=true;
	/**
	 * Current input characters, all input characters including leading zeros, etc
	 */
	private String currentInputCharacters="";
	/**
	 * Current value, always stores positive number
	 */
	private double currentValue;
	/**
	 * Active operand
	 */
	private double activeOperand;
	/**
	 * Pending operation
	 */
	private DoubleBinaryOperator pendingOperation;
	/**
	 * If active operand is set
	 */
	private boolean isActiveOperandSet;
	/**
	 * Public constructor
	 */
	public CalcModelImpl() {
		listeners=new ArrayList<>();
	}
	
	@Override
	public void addCalcValueListener(CalcValueListener l) {
		if(l!=null) {
			listeners.add(l);
		}
	}

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		listeners.remove(l);
		
	}

	@Override
	public double getValue() {
		return currentValue*(isPositive==true?1:-1);
	}

	@Override
	public void setValue(double value) {
		isEditable=false;
		if(value<0) {
			isPositive=false;
			value*=-1;
		}else {
			isPositive=true;
		}
		this.currentValue=value;
		currentInputCharacters=String.valueOf(value);
		informAllListeners();
		
	}


	@Override
	public boolean isEditable() {
		return isEditable;
	}

	@Override
	public void clear() {
		currentInputCharacters="";
		currentValue=0;
		isEditable=true;
		isPositive=true;
		informAllListeners();
		
	}

	@Override
	public void clearAll() {
		currentInputCharacters="";
		currentValue=0;
		pendingOperation=null;
		activeOperand=0;
		isActiveOperandSet=false;
		isEditable=true;
		informAllListeners();
		
		
	}

	@Override
	public void swapSign() throws CalculatorInputException {
		if(!isEditable) {
			throw new CalculatorInputException("Model isn't editable. Can't swap sign.");
		}
		if(isPositive) {
			isPositive=false;
		}else {
			isPositive=true;
		}informAllListeners();
		
	}

	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		if(!isEditable) {
			throw new CalculatorInputException("Can't insert decimal point. Model isn't editable.");
		}
		if(currentInputCharacters.contains(".")) {
			throw new CalculatorInputException("Input string already contains decimal point.");
		}
		if(currentInputCharacters.isEmpty()) {
			throw new CalculatorInputException("There are no input numbers.");
		}
		currentInputCharacters=currentInputCharacters+".";
		informAllListeners();
		
		
	}

	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		if(!isEditable) {
			throw new CalculatorInputException("Model is not editable.");
		}
		if(currentInputCharacters.equals("0") && digit==0) {
			return;
		}
		String oldInputCharacters=currentInputCharacters;
		double oldValue=currentValue;
		if(currentInputCharacters.equals("0")) {
			currentInputCharacters=digit+"";
		}
		else {
			currentInputCharacters=currentInputCharacters+digit;
		}
		
		try {
			currentValue=Double.parseDouble(currentInputCharacters);
			if(currentValue>Double.MAX_VALUE) {
				currentInputCharacters=oldInputCharacters;
				currentValue=oldValue;
				throw new CalculatorInputException();
			}
		}catch(NumberFormatException e){
			currentInputCharacters=oldInputCharacters;
			currentValue=oldValue;
			throw new CalculatorInputException("Number was not parseable.");
		}informAllListeners();
		
	}

	@Override
	public boolean isActiveOperandSet() {
		return isActiveOperandSet;
	}

	@Override
	public double getActiveOperand() throws IllegalStateException {
		if(!isActiveOperandSet) {
			throw new IllegalStateException("Active operand isn't set.");
		}
		return activeOperand;
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand=activeOperand;
		isActiveOperandSet=true;
		
	}

	@Override
	public void clearActiveOperand() {
		activeOperand=0;
		isActiveOperandSet=false;
		
	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return pendingOperation;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		pendingOperation=op;
		
	}
	@Override
	public String toString() {
		if(currentInputCharacters.isEmpty()) {
			return (isPositive==false?"-":"")+"0";
		}
		return (isPositive==false?"-":"")+currentInputCharacters;
	}
	
	private void informAllListeners() {
		if(listeners.isEmpty()) return;
		for(CalcValueListener l:listeners) {
			l.valueChanged(this);
		}
		
	}
}
