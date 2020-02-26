package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EmptyStackException;
import java.util.Stack;
import java.util.function.DoubleBinaryOperator;
import java.util.function.UnaryOperator;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * This class represents basic calculator support that is equal to one from
 * windows in older versions.
 * 
 * @author af
 *
 */
public class Calculator extends JFrame {

	private static final long serialVersionUID = 1L;
	/**
	 * Calc model
	 */
	private CalcModel calcModel;
	/**
	 * Jcheckbox
	 */
	private JCheckBox jCheckBox;
	/**
	 * If calculator needs to be cleared
	 */
	private boolean clearCalculator;
	/**
	 * Stack
	 */
	private Stack<Double> stack;

	/**
	 * Public constructor
	 */
	public Calculator() {
		super();
		calcModel = new CalcModelImpl();
		stack = new Stack<>();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Calculator");
		setLocation(20, 20);
		setSize(500, 500);

		initGUI();
	}

	/**
	 * This method initializes gui
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new CalcLayout(3));

		JLabel display = new JLabel("0", SwingConstants.RIGHT);
		display.setBackground(Color.YELLOW);
		display.setOpaque(true);
		display.setFont(display.getFont().deriveFont(30f));

		calcModel.addCalcValueListener(model -> display.setText(model.toString()));

		cp.add(display, new RCPosition(1, 1));

		jCheckBox = new JCheckBox("Inv");
		jCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JCheckBox cb = (JCheckBox) e.getSource();
				for (Component comp : getContentPane().getComponents()) {
					if (!(comp instanceof CalculatorButton)) {
						continue;
					}
					CalculatorButton calcBttn = (CalculatorButton) comp;
					if (!calcBttn.hasInverse())
						continue;
					if (cb.isSelected()) {
						calcBttn.setText(calcBttn.inverseText());
					} else {
						calcBttn.setText(calcBttn.originalText());
					}
				}
			}
		});

		cp.add(jCheckBox, new RCPosition(5, 7));

		addButtons(cp);

	}

	/**
	 * This method add all buttons and associates each one with their function
	 * 
	 * @param cp
	 */
	private void addButtons(Container cp) {
		cp.add(new CalculatorButton("=", false, e -> performDoubleOperatorOperation(), null), "1,6");
		cp.add(new CalculatorButton("clr", false, e -> calcModel.clear(), null), "1,7");

		cp.add(new CalculatorButton("1/x", false,
				e -> performInversiveUnaryOperation((t) -> Math.pow(t, -1), (t) -> Math.pow(t, -1)), null), "2,1");
		cp.add(new CalculatorButton("sin", false, e -> performInversiveUnaryOperation(Math::sin, Math::asin), "arcsin"),
				"2,2");
		cp.add(new CalculatorButton("7", true, e -> performAddDigitOperation(7), null), "2,3");
		cp.add(new CalculatorButton("8", true, e -> performAddDigitOperation(8), null), "2,4");
		cp.add(new CalculatorButton("9", true, e -> performAddDigitOperation(9), null), "2,5");
		cp.add(new CalculatorButton("/", false, e -> performSavingValue2((l, r) -> l / r), null), "2,6");
		cp.add(new CalculatorButton("res", false, e -> calcModel.clearAll(), null), "2,7");

		cp.add(new CalculatorButton("log", false,
				e -> performInversiveUnaryOperation(Math::log10, l -> Math.pow(10, l)), "10^x"), "3,1");
		cp.add(new CalculatorButton("cos", false, e -> performInversiveUnaryOperation(Math::cos, Math::acos), "arccos"),
				"3,2");
		cp.add(new CalculatorButton("6", true, e -> performAddDigitOperation(6), null), "3,5");
		cp.add(new CalculatorButton("5", true, e -> performAddDigitOperation(5), null), "3,4");
		cp.add(new CalculatorButton("4", true, e -> performAddDigitOperation(4), null), "3,3");
		cp.add(new CalculatorButton("*", false, e -> performSavingValue2((l, r) -> l * r), null), "3,6");
		cp.add(new CalculatorButton("push", false, e -> performPush(), null), "3,7");

		cp.add(new CalculatorButton("ln", false, e -> performInversiveUnaryOperation(Math::log, Math::exp), "e^x"),
				"4,1");
		cp.add(new CalculatorButton("tan", false, e -> performInversiveUnaryOperation(Math::tan, Math::atan), "arctan"),
				"4,2");
		cp.add(new CalculatorButton("3", true, e -> performAddDigitOperation(3), null), "4,5");
		cp.add(new CalculatorButton("2", true, e -> performAddDigitOperation(2), null), "4,4");
		cp.add(new CalculatorButton("1", true, e -> performAddDigitOperation(1), null), "4,3");
		cp.add(new CalculatorButton("-", false, e -> performSavingValue2((l, r) -> l - r), null), "4,6");
		cp.add(new CalculatorButton("pop", false, e -> performPop(), null), "4,7");

		cp.add(new CalculatorButton("x^n", false,
				e -> performInversiveBinaryOperation((l, r) -> Math.pow(l, r), (l, r) -> Math.pow(l, 1 / r)),
				"x^(1/n)"), "5,1");
		cp.add(new CalculatorButton("ctg", false,
				e -> performInversiveUnaryOperation(t -> Math.tan(1 / t), t -> Math.atan(1 / t)), "arcctg"), "5,2");
		cp.add(new CalculatorButton("0", true, e -> performAddDigitOperation(0), null), "5,3");
		cp.add(new CalculatorButton("+/-", false, e -> calcModel.swapSign(), null), "5,4");
		cp.add(new CalculatorButton(".", false, e -> calcModel.insertDecimalPoint(), null), "5,5");
		cp.add(new CalculatorButton("+", false, e -> performSavingValue2((l, r) -> l + r), null), "5,6");

	}

	/**
	 * This method pops value from stack
	 */
	private void performPop() {
		try {
			calcModel.setValue(stack.pop());
		} catch (EmptyStackException e) {
			System.out.println("Stack is empty");
			return;
		}

	}

	/**
	 * This method pushes current number to stack
	 */
	private void performPush() {
		stack.push(calcModel.getValue());
	}

	/**
	 * This method performs double operator operation. It needs two operators
	 */
	private void performDoubleOperatorOperation() {
		double activeOperand = calcModel.getActiveOperand();
		DoubleBinaryOperator op = calcModel.getPendingBinaryOperation();
		if (op == null) {
			return;
		}
		double result = op.applyAsDouble(activeOperand, calcModel.getValue());
		calcModel.setActiveOperand(0);
		calcModel.setPendingBinaryOperation(null);
		calcModel.setValue(result);

	}

	/**
	 * This method saves value to calc model depending on operation
	 * 
	 * @param type
	 */
	private void performSavingValue(CalculatorOperationType type) {
		if (calcModel.getPendingBinaryOperation() != null) {
			performDoubleOperatorOperation();
		}
		calcModel.setActiveOperand(calcModel.getValue());
		if (type == CalculatorOperationType.ADD) {
			calcModel.setPendingBinaryOperation((l, r) -> l + r);
		} else if (type == CalculatorOperationType.SUB) {
			calcModel.setPendingBinaryOperation((l, r) -> l - r);
		} else if (type == CalculatorOperationType.MUL) {
			calcModel.setPendingBinaryOperation((l, r) -> l * r);
		} else if (type == CalculatorOperationType.DIV) {
			calcModel.setPendingBinaryOperation((l, r) -> l / r);
		} else if (type == CalculatorOperationType.POW) {
			calcModel.setPendingBinaryOperation((l, r) -> Math.pow(l, jCheckBox.isSelected() == false ? r : (1 / r)));
		}
		clearCalculator = true;

	}

	/**
	 * This method saves value to calc model depending on operation
	 * 
	 * @param type
	 */
	private void performSavingValue2(DoubleBinaryOperator op) {
		if (calcModel.getPendingBinaryOperation() != null) {
			performDoubleOperatorOperation();
		}
		calcModel.setActiveOperand(calcModel.getValue());
		calcModel.setPendingBinaryOperation(op);
		clearCalculator = true;
	}

	/**
	 * This method performs unary operation that has two options
	 * 
	 * @param operatorOriginal
	 * @param operatorInverisve
	 */
	private void performInversiveUnaryOperation(UnaryOperator<Double> operatorOriginal,
			UnaryOperator<Double> operatorInverisve) {
		if (!jCheckBox.isSelected()) {
			double result = operatorOriginal.apply(calcModel.getValue());
			calcModel.setValue(result);
		} else {
			double result = operatorInverisve.apply(calcModel.getValue());
			calcModel.setValue(result);
		}

	}

	/**
	 * This method is used for inversive buttons that perform binary operation
	 * 
	 * @param operatorOriginal  orignal
	 * @param operatorInversive inversive
	 * @param hasInverse        if has inverse
	 */
	private void performInversiveBinaryOperation(DoubleBinaryOperator operatorOriginal,DoubleBinaryOperator operatorInversive) {

		if (!jCheckBox.isSelected()) {
			performSavingValue2(operatorOriginal);
		} else {
			performSavingValue2(operatorInversive);
		}

	}

	/**
	 * This method adds digit to calculator view
	 * 
	 * @param digit
	 */
	private void performAddDigitOperation(int digit) {
		if (clearCalculator) {
			calcModel.clear();
			clearCalculator = false;
		}
		calcModel.insertDigit(digit);
	}

	/**
	 * This is main method
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new Calculator().setVisible(true);
		});

	}
}