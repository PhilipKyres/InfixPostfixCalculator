package com.philipkyres.calculator;

/**
 * Custom exception class for the CalculatorAction methods. 
 * Pre-written strings for cleaner messages.
 * 
 * @author Philip Kyres
 */
public class InvalidInfixException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public static final String INVALID_OPERATOR = "Invalid infix: Operator must be surrounded by numbers or parenthesis.";
	public static final String INVALID_PARENTHESIS = "Invalid infix: Parenthesis must align properly. Same number of open and closed parenthesis also required.";
	public static final String MISSING_OPERATOR = "Invalid infix: Numbers and parenthesis groupings must be joined by an operator.";
	public static final String DIVIDE_BY_0 = "Invalid calculation: Cannot divide by 0.";

	public InvalidInfixException(String message) {
		super(message);
	}
}
