package com.philipkyres.test.calculator;

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
import org.junit.rules.ExpectedException;

/**
 * A parameterized Exception test class. Requires JUnit 4.11
 *
 * @author Philip Kyres
 */
@RunWith(Parameterized.class)
public class CalculatorActionExceptionTest {
	
	@Rule
	public MethodLogger methodLogger = new MethodLogger(); 
    
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
    public CalculatorAction calculator;
    public Queue<String> infix;
    public String expectedMessage;
    
    /**
     * A static method is required to hold all the data to be tested and the
     * expected results for each test. This data must be stored in a
     * two-dimension array. The 'name' attribute of Parameters is a JUnit 4.11
     * feature
     *
     * @return The list of arrays
     */
    @Parameters(name = "{index}: input[{0}] expectedMessage[{1}]")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
        	{"8 + + 8", InvalidInfixException.INVALID_OPERATOR},
        	{"+", InvalidInfixException.INVALID_OPERATOR},
        	{"- 4", InvalidInfixException.INVALID_OPERATOR},
        	{"2 /", InvalidInfixException.INVALID_OPERATOR},
        	{"( ( 2 )", InvalidInfixException.INVALID_PARENTHESIS},
        	{"( 2", InvalidInfixException.INVALID_PARENTHESIS},
        	{"( 2 ) )", InvalidInfixException.INVALID_PARENTHESIS},
        	{"2 )", InvalidInfixException.INVALID_PARENTHESIS},
        	{") (", InvalidInfixException.INVALID_PARENTHESIS},
        	{"2 ) + 5 )", InvalidInfixException.INVALID_PARENTHESIS},
        	{"( 4 ) ( 6 )", InvalidInfixException.MISSING_OPERATOR},
        	{"2 5", InvalidInfixException.MISSING_OPERATOR},
        	{"2 / 0", InvalidInfixException.DIVIDE_BY_0}
        });
    }

    /**
     * Constructor that receives all the data for each test as defined by a row
     * in the list of parameters. Converts the infix String
     * to a Queue object. Creates a new CalculatorAction object.
     */
    public CalculatorActionExceptionTest(final String infix, final String expectedMessage) {
    	 this.calculator = new CalculatorAction();
    	 this.infix = stringToQueue(infix);
    	 this.expectedMessage = expectedMessage;
    }
    
    /**
     * Tests if an InvalidInfixException is thrown. 
     */
    @Test
    public void exceptionTest() throws InvalidInfixException {
    	expectedEx.expect(InvalidInfixException.class);
        expectedEx.expectMessage(expectedMessage);
        calculator.postfixToBigDecimal(calculator.infixToPostfix(infix));
    }
    
	private Queue<String> stringToQueue(String s) {
		Queue<String> queue = new ArrayDeque<String>(Arrays.asList(s.split(" ")));
		return queue;
	}
}