package com.philipkyres.test.calculator;

import static org.junit.Assert.*;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.philipkyres.calculator.CalculatorAction;
import com.philipkyres.test.MethodLogger;

/**
 * Test class for the CalculatorAction class
 * 
 * @author Philip Kyres
 *
 */
public class CalculatorActionTest {

	@Rule
	public MethodLogger methodLogger = new MethodLogger();

	private final Logger log = LoggerFactory.getLogger(getClass().getName());
	
	private static CalculatorAction a;
	
	@BeforeClass
	public static void prepare() {
		a = new CalculatorAction();
	}
	
	/**
	 * In this test an infix queue is created and sent through the CalculatorAction infixToPostfix method and checked against the expected queue
	 * Both queues are converted to arrays before testing so the assert checks only the values
	 * 
	 * Test just a number
	 */
	@Test
	public void testInfixToPostfix1() {
		Queue<String> infix = stringToQueue("23");
		Queue<String> expected = stringToQueue("23");
		
		assertArrayEquals("Expected result array and converted postfix arrays not equal", expected.toArray(), a.infixToPostfix(infix).toArray());	
	}
	
	/**
	 * In this test an infix queue is created and sent through the CalculatorAction infixToPostfix method and checked against the expected queue
	 * Both queues are converted to arrays before testing so the assert checks only the values
	 * 
	 * Test a simple operation
	 */
	@Test
	public void testInfixToPostfix2() {
		Queue<String> infix = stringToQueue("23 * 5");
		Queue<String> expected = stringToQueue("23 5 *");

		assertArrayEquals("Expected result array and converted postfix arrays not equal", expected.toArray(), a.infixToPostfix(infix).toArray());	
	}
	
	/**
	 * In this test an infix queue is created and sent through the CalculatorAction infixToPostfix method and checked against the expected queue
	 * Both queues are converted to arrays before testing so the assert checks only the values
	 * 
	 * Test with brackets on outsides
	 */
	@Test
	public void testInfixToPostfix3() {
		Queue<String> infix = stringToQueue("( 23 * 5 )");
		Queue<String> expected = stringToQueue("23 5 *");

		assertArrayEquals("Expected result array and converted postfix arrays not equal", expected.toArray(), a.infixToPostfix(infix).toArray());	
	}
	
	/**
	 * In this test an infix queue is created and sent through the CalculatorAction infixToPostfix method and checked against the expected queue
	 * Both queues are converted to arrays before testing so the assert checks only the values
	 * 
	 * Test with operations brackets
	 */
	@Test
	public void testInfixToPostfix4() {
		Queue<String> infix = stringToQueue("23 * ( 54 + 12 )");
		Queue<String> expected = stringToQueue("23 54 12 + *");
		
		assertArrayEquals("Expected result array and converted postfix arrays not equal", expected.toArray(), a.infixToPostfix(infix).toArray());
	}
	
	private Queue<String> stringToQueue(String s) {
		Queue<String> queue = new ArrayDeque<String>(Arrays.asList(s.split(" ")));
		return queue;
	}
}
