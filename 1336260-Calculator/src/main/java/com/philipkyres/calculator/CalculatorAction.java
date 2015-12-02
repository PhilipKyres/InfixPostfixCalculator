package com.philipkyres.calculator; 
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
	 * Logic to convert infix queue to postfix queue
	 * 
	 * @param infix queue
	 * @return postfix queue
	 */
	public Queue<String> infixToPostfix(final Queue<String> infix) {
		Queue<String> postfix = new ArrayDeque<String>();
		ArrayDeque<String> operator = new ArrayDeque<String>(); //multiply divide plus minus 
		
		for(String s : infix) {		
			if(isNumeric(s)) { //Numbers strait to postfix
				postfix.add(s);
				continue;
			} else { //s is operator
				int sPriority = getPriority(s);
				if(operator.isEmpty() || getPriority(operator.peek()) == 3) { //If no operators in stack or if previous operator is (
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
						while(!operator.isEmpty() && getPriority(s) < getPriority(operator.peek())) {
							postfix.add(operator.pop()); //Move stack head to postfix
						}
					}
				}
			}
		}
		while(!operator.isEmpty()) {
			postfix.add(operator.pop()); //Move stack to postfix
		}
		return postfix;
	}
	
	public double postfixToDouble(final Queue<String> postfix) {
		return 0;
	}
	
	private boolean isNumeric(String s) {
		return s.matches("\\d+(\\.\\d+)?");
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
