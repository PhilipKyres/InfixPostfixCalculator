package com.philipkyres.calculator; 
import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Main calculator logic class. 
 * 
 * @author Philip Kyres
 *
 */
public class CalculatorAction {
	
	public CalculatorAction() {}
	
	/**
	 * Validates the infix queue. Throws an IllegalArgumentException if it's invalid
	 * 
	 * @param infix queue
	 * @throws IllegalArgumentException if invalid infix
	 */
	public void validate(final Queue<String> infix) throws IllegalArgumentException {
		if (infix == null)
			throw new IllegalArgumentException("infix queue must not be null");
		if (infix.isEmpty())
			throw new IllegalArgumentException("infix queue must not be empty");
	}
	
	/**
	 * Logic to convert infix queue to postfix queue
	 * 
	 * @param infix queue
	 * @return postfix queue
	 * @throws IllegalArgumentException if invalid infix
	 */
	public Queue<String> infixToPostfix(final Queue<String> infix) throws IllegalArgumentException {
		validate(infix);
		
		Queue<String> postfix = new ArrayDeque<String>();
		ArrayDeque<String> operator = new ArrayDeque<String>(); //Operator stack //multiply divide plus minus 
		
		for(String s : infix) {		
			if(isNumeric(s)) { //Numbers strait to postfix
				postfix.add(s);
				continue;
			} else { //s is operator
				int sPriority = getPriority(s);
				if((operator.isEmpty() || getPriority(operator.peek()) == 3) && sPriority != 4) { //If no operators in stack or if previous operator is ( and s is not )
					operator.push(s); //Push operator in stack
				continue;
				} else { //Operator is not empty
					if(sPriority > getPriority(operator.peek())) { //If new operator is greater precedence than stack head
						if (sPriority == 4) { //If )
							while(getPriority(operator.peek()) != 3){ //While operator is not (
								postfix.add(operator.pop()); //Pop operator and add it to postfix
				             }
							operator.pop(); //Pop ( from operator stack
						} else {
							operator.push(s); //Push operator to stack
						}
					} else {
						while(!operator.isEmpty() && sPriority <= getPriority(operator.peek()) && getPriority(operator.peek()) != 3) {
							postfix.add(operator.pop()); //Move stack head to postfix
						}
						operator.push(s);
					}
				}
			}
		}
		while(!operator.isEmpty()) {
			postfix.add(operator.pop()); //Move remaining stack to postfix
		}
		return postfix;
	}
	
	/**
	 * Logic to evaluate a postfix queue into a BigDecimal number
	 * 
	 * @param postfix queue
	 * @return evaluation
	 */
	public BigDecimal postfixToBigDecimal(final Queue<String> postfix) {
		ArrayDeque<String> operand = new ArrayDeque<String>(); //Operand stack
		
		for(String s : postfix) {
			if(isNumeric(s)) { //Numbers strait to operand stack
				operand.push(s);
				continue;
			} else { //s is operator
				BigDecimal val1 = new BigDecimal(operand.pop());
				BigDecimal val2 = new BigDecimal(operand.pop());
				BigDecimal retVal;
				
				switch (s) {
					case "+": 
						retVal = val2.add(val1);
						break;
					case "-": 
						retVal = val2.subtract(val1);
						break;
					case "*": 
						retVal = val2.multiply(val1);
						break;
					case "/": 
						retVal = val2.divide(val1, 3, BigDecimal.ROUND_HALF_UP); //Rounds infinite numbers
						break;
					default:
						throw new IllegalArgumentException("Invalid operand: " + s);
				}
				operand.push(retVal.toPlainString());
			}
		}
		
		return (new BigDecimal(operand.peek())).stripTrailingZeros();
	}
	
	private boolean isNumeric(String s) {
		return s.matches("(\\d+)?(\\.\\d+)?");
	}
	
	private int getPriority(String s) {
		switch (s) {
			case "+": 
				return 1;
			case "-": 
				return 1;
			case "*": 
				return 2;
			case "/": 
				return 2;
			case "(": 
				return 3;
			case ")": 
				return 4;
		}
		throw new IllegalArgumentException("Invalid Operator: " + s);
	}
}
