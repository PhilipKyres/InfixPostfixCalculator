package com.philipkyres.test.calculator;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Queue;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import com.philipkyres.calculator.CalculatorAction;
import com.philipkyres.test.MethodLogger;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * A parameterized test class. Requires JUnit 4.11
 *
 * @author Philip Kyres
 */
@RunWith(Parameterized.class)
public class CalculatorActionExceptionTest {
	
	@Rule
	public MethodLogger methodLogger = new MethodLogger();
    
    public class UnderTest {
        public void execute(Queue<String> i) {
        	calculator.postfixToBigDecimal(calculator.infixToPostfix(i));
        }
    }
    
    public CalculatorAction calculator;
    
    @Rule
    public ExpectedException expected = ExpectedException.none();

    @Parameter(value = 0)
    public String input;

    @Parameter(value = 1)
    public Class<Throwable> exceptionClass;
    
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
        	{"8 + 8", NoSuchElementException.class},
        });
    }

    /**
     * Constructor that receives all the data for each test as defined by a row
     * in the list of parameters. Converts the infix and expectedPostfix Strings
     * to Queue objects. Converts the infix queue to a postfix queue. Converts
     * the expectedResult String to a BigDecimal object.
     */
    public CalculatorActionExceptionTest() {
    	 calculator = new CalculatorAction();
    }
    

    @Test
    public void exceptionTest() {
    	
    	Queue<String> infix = stringToQueue(input);
    	
    	if (exceptionClass != null) {
            expected.expect(exceptionClass);
        }

        UnderTest underTest = new UnderTest();          
        underTest.execute(infix);
    }
    
	private Queue<String> stringToQueue(String s) {
		Queue<String> queue = new ArrayDeque<String>(Arrays.asList(s.split(" ")));
		return queue;
	}
}