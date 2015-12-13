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
     * feature
     *
     * @return The list of arrays
     */
    @Parameters(name = "{index}: infix[{0}] postfix[{1}] result[{2}]")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
        	//{"", "", "0"},
            {"23", "23", "23"},
            {"23 * 5", "23 5 *", "115"},
            {"10 + 5 * 2", "10 5 2 * +", "20"},
            {"( 23 * 5 )", "23 5 *", "115"},
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
     * Test of validate method, of class CalculatorAction.
     */
    @Test
    public void testValidate() {
        assertEquals("infix is not valid.", true, true);
    }

    /**
     * Test of infixToPostfix method, of class CalculatorAction.
     */
    @Test
    public void testInfixToPostfix() {
    	Queue<String> postfix = this.calculator.infixToPostfix(this.infix);
        assertArrayEquals("Expected postfix array and converted infix to postfix arrays are not equal.", expectedPostfix.toArray(), postfix.toArray());
    }

    /**
     * Test of postfixToBigDecimal method, of class CalculatorAction.
     */
    @Test
    public void testPostfixToBigDecimal() {
    	Queue<String> postfix = this.calculator.infixToPostfix(this.infix);
    	
        assertEquals("Expected result and calculated result are not equal.", expectedResult.toPlainString(), calculator.postfixToBigDecimal(postfix).toPlainString());
    }
    
	private Queue<String> stringToQueue(String s) {
		Queue<String> queue = new ArrayDeque<String>(Arrays.asList(s.split(" ")));
		return queue;
	}
}