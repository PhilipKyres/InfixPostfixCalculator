package core;

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
	
	private Queue<String> infixToPostfix(final Queue<String> infix) {
		//Queue<String> infix;
		Queue<String> postfix = new ArrayDeque<String>();
		ArrayDeque<String> operator = new ArrayDeque<String>(); //multiply divide plus minus 
		
		for(String s : infix) {			
			if(isNumeric(s)) { //Numbers strait to postfix
				postfix.add(s);
				continue;
			} else if(operator.isEmpty()) { //If is operator and if no operators in stack
				operator.push(s); //Push operator in stack
				continue;
			} else { //Operator is not empty
				if(getPriority(s) > getPriority(operator.peek())) { //If new operator is greater precedence than stack head
					operator.push(s); //Push operator to stack
				} else {
					while(!operator.isEmpty() && getPriority(s) < getPriority(operator.peek())) {
						postfix.add(operator.pop()); //Move stack head to postfix
					}
				}
			}		
		}
		return postfix;
	}
	
	private double postfixToDouble(final Queue<String> postfix) {
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
		}
		return -1;
	}
}
