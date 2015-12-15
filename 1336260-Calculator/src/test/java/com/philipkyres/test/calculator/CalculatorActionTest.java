package com.philipkyres.test.calculator;

import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collection;
import java.util.Queue;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.philipkyres.calculator.CalculatorAction;
import com.philipkyres.calculator.InvalidInfixException;
import com.philipkyres.test.MethodLogger;

import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * A parameterized test class. Requires JUnit 4.11
 *
 * @author Philip Kyres
 */
@RunWith(Parameterized.class)
public class CalculatorActionTest {
	
	@Rule
	public MethodLogger methodLogger = new MethodLogger();
	
    /**
     * A static method is required to hold all the data to be tested and the
     * expected results for each test. This data must be stored in a
     * two-dimension array. The 'name' attribute of Parameters is a JUnit 4.11
     * feature.
     *
     * @return The list of arrays
     */
    @Parameters(name = "{index}: infix[{0}] postfix[{1}] result[{2}]")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
            {"2", "2", "2"}, //Simple number
            {"( 3 )", "3", "3"}, //Simple number with parenthesis
            {"24", "24", "24"}, //Number multiple digit
            {"4 + 4", "4 4 +", "8"}, //add
            {"4 + 0", "4 0 +", "4"}, //0 in infix
            {"4 - 3", "4 3 -", "1"}, //subtract
            {"4 - 4", "4 4 -", "0"}, //0 as result
            {"23 * 5", "23 5 *", "115"}, //multiply
            {"20 / 4", "20 4 /", "5"}, //divide evenly
            {"1 / 2", "1 2 /", ".5"}, //divide decimal result
            {"10 / 3", "10 3 /", "3.333"}, //divide infinite decimal result
            {"-5", "-5", "-5"}, //negative number and result
            {"-5 + 4", "-5 4 +", "-1"}, //negative number and result with operation
            {"0.01", "0.01", "0.01"}, //decimal number and result
            {"0.01 + 0.01", "0.01 0.01 +", "0.02"}, //decimal number and result with operation
            {"-0.01", "-0.01", "-0.01"}, //negative decimal number and result
            {"0.5 + .25 + 0.25", "0.5 .25 + 0.25 +", "1"}, //multiple types of decimal number with operation and whole number result
            {"10 + 5 * 2", "10 5 2 * +", "20"}, //multiple operation
            {"10 + 5 * 2 - 4", "10 5 2 * + 4 -", "16"}, //multiple operation with lower priority
            {"( 23 * 5 )", "23 5 *", "115"}, //multiple operation with parenthesis
            {"23 * ( 54 + 12 )", "23 54 12 + *", "1518"},
            {"5 + ( 4 ) + 3", "5 4 + 3 +", "12"}, 
            {"( ( ( ( ( 56.9 * 0.09 ) ) ) ) ) / ( ( 3 - 4.4 ) - ( 79 - .3 ) / ( 3 / 2.4 ) ) / 0.5 - 2", "56.9 0.09 * 3 4.4 - 79 .3 - 3 2.4 / / - / 0.5 / 2 -", "-2.16"},
            {"2 * 3 - 4 / 5", "2 3 * 4 5 / -", "5.2"},
            {"10 / 3 + ( 2 * ( 4 - 8.5 / 2.95 ) + 2 )", "10 3 / 2 4 8.5 2.95 / - * 2 + +", "7.571"}
        });
    }
    
    CalculatorAction calculator;
    Queue<String> infix;
    Queue<String> expectedPostfix;
    BigDecimal expectedResult;

    /**
     * Constructor that receives all the data for each test as defined by a row
     * in the list of parameters. Converts the infix and expectedPostfix Strings
     * to Queue objects. Converts the infix queue to a postfix queue. Converts
     * the expectedResult String to a BigDecimal object.
     *
     * @param infix
     * @param expectedPostfix
     * @param expectedResult
     */
    public CalculatorActionTest(final String infix, final String expectedPostfix, final String expectedResult) {
        this.calculator = new CalculatorAction();
        this.infix = stringToQueue(infix);
        this.expectedPostfix = stringToQueue(expectedPostfix);
        this.expectedResult = new BigDecimal(expectedResult);
    }
    
    /**
     * Test of infixToPostfix method, of class CalculatorAction.
     * @throws InvalidInfixException if invalid infix
     */
    @Test
    public void testInfixToPostfix() throws InvalidInfixException {
    	Queue<String> postfix = this.calculator.infixToPostfix(this.infix);
        assertArrayEquals("Expected postfix array and converted infix to postfix arrays are not equal.", expectedPostfix.toArray(), postfix.toArray());
    }

    /**
     * Test of postfixToBigDecimal method, of class CalculatorAction.
     * @throws InvalidInfixException if invalid infix
     */
    @Test
    public void testPostfixToBigDecimal() throws InvalidInfixException {
    	Queue<String> postfix = this.calculator.infixToPostfix(this.infix);
    	
        assertEquals("Expected result and calculated result are not equal.", expectedResult.toPlainString(), calculator.postfixToBigDecimal(postfix).toPlainString());
    }
    
	private Queue<String> stringToQueue(String s) {
		Queue<String> queue = new ArrayDeque<String>(Arrays.asList(s.split(" ")));
		return queue;
	}
}