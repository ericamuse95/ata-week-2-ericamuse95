package com.kenzie.unittesting.kenziemath.math;

import com.amazonaws.services.s3.internal.crypto.keywrap.KeyWrapAlgorithmResolver;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * We're keeping the tests for {@code add()} from the pre-work, and adding new ones for {@code average()}.
 */
public class KenzieMathTest {

    // add()

    @Test
    public void add_singleInteger_returnsTheInteger() {
        // GIVEN
        int[] oneInteger = {42};
        KenzieMath kenzieMath = new KenzieMath();

        // WHEN
        int result = kenzieMath.add(oneInteger);

        // THEN
        assertEquals(42, result, "Expected adding a single int to return the int");
    }

    @Test
    public void add_twoIntegers_returnsTheirSum() {
        // GIVEN
        int[] tuple = {6, 9};
        KenzieMath kenzieMath = new KenzieMath();

        // WHEN
        int result = kenzieMath.add(tuple);

        // THEN
        assertEquals(15, result, String.format(
            "Expected adding two ints (%s) to return their sum (15)",
            Arrays.toString(tuple))
        );
    }

    @Test
    public void add_emptyArray_returnsZero() {
        // GIVEN
        int[] emptyArray = {};
        KenzieMath kenzieMath = new KenzieMath();

        // WHEN
        int result = kenzieMath.add(emptyArray);

        // THEN
        assertEquals(0, result, "Expected empty array to sum to zero");
    }

    @Test
    public void add_nullArray_returnsZero() {
        // GIVEN
        int[] nullArray = null;
        KenzieMath kenzieMath = new KenzieMath();

        // WHEN
        int result = kenzieMath.add(nullArray);

        // THEN
        assertEquals(0, result, "Expected null array to sum to zero");
    }

    @Test
    public void add_sumOutOfBounds_throwsArithmeticException() {
        // GIVEN
        int[] values = {Integer.MAX_VALUE - 5, 3, 3};
        KenzieMath kenzieMath = new KenzieMath();

        // WHEN - attempt to compute the sum
        // PARTICIPANTS: ADD YOUR WHEN CONDITION HERE AND DELETE THE NEXT LINE
        //int result = kenzieMath.add(values);

        // THEN - exception thrown

        // the following syntax is a little fancy, just know that it's
        // asserting that when the second line calls the add()
        // method that we should see an `ArithmeticException` thrown
        assertThrows(ArithmeticException.class,
                     () -> kenzieMath.add(values),
                     "Summing above MAX_VALUE should result in ArithmeticException thrown");
    }

    @Test
    public void add_sumOutofBoundsUnderflow_throwsArithmeticException() {
        // GIVEN
        int[] values = {Integer.MIN_VALUE + 5, -3, -3};
        KenzieMath kenzieMath = new KenzieMath();

        // WHEN - attempt to compute the sum
        // PARTICIPANTS: ADD YOUR WHEN CONDITION HERE AND DELETE THE NEXT LINE
       //int result = kenzieMath.add(values);

        // THEN - exception thrown
        assertThrows(ArithmeticException.class,
                     () -> kenzieMath.add(values),
                     "Summing below MIN_VALUE should result in ArithmeticException thrown");
    }


    // average()
    @Test
    public void average_ofSingleInteger_isThatInteger(){
        //GIVEN single int array
        int[] oneInteger = {42};
        KenzieMath kenzieMath = new KenzieMath();

        //WHEN compute the average
        double result = kenzieMath.average(oneInteger);

        //THEN is (the double form of) the single value
        assertEquals(42, result, "Expected average a single int to return the int");

    }
    @Test
    public void average_ofSeveralIntegers_isCorrect(){
        //GIVEN array of several integers (with easy to compute average)
        int [] severalIntegers = {1,2,3};
        KenzieMath kenzieMath = new KenzieMath();

        //WHEN compute the average
        int sum = kenzieMath.add(severalIntegers);
        int avgOfIntegers = sum / severalIntegers.length;

        //THEN average is correct
        assertEquals(2,avgOfIntegers);
    }
    @Test
    public void average_ofNullArray_throwsIllegalArgumentException(){
        //GIVEN null array
        int [] nullArray = null;
        KenzieMath kenzieMath = new KenzieMath();

        //WHEN attempt to compute the average
        //THEN exception thrown
        assertThrows(IllegalArgumentException.class,
                () -> kenzieMath.average(nullArray),
                "Expected IllegalArgumentException when attempting to average null array.");

    }
    @Test
    public void average_ofPositiveAndNegativeIntegers_isCorrect(){
        //GIVEN array of mix of positive and negative integers (easy to compute)
        int [] mixedNums = {4,6,-1};
        KenzieMath kenzieMath = new KenzieMath();
        //WHEN compute average
        int result = kenzieMath.add(mixedNums)/ mixedNums.length;
        //THEN average is correct
        assertEquals(3,result);
    }

    @Test
    public void average_ofIntegersIncludingZeroes_isCorrect(){
        //GIVEN Array of ins including 0's
        int [] numbers = {0,1,2,8,9};
        KenzieMath kenzieMath = new KenzieMath();
        //WHEN compute average
        int result = kenzieMath.add(numbers) / numbers.length;
        //THEN average is correct
        assertEquals(4,result);
    }
    @Test
    public void average_ofEmptyArray_throwsIllegalArgumentException(){
        //GIVEN empty array
        int [] emptyArray = {};
        KenzieMath kenzieMath = new KenzieMath();
        //WHEN compute average
        //THEN throws Exception
        assertThrows(IllegalArgumentException.class,
                () -> kenzieMath.average(emptyArray),
                "Expected IllegalArgumentException when attempting to average empty array");
    }
    @Test
    public void average_ofLargeNumbersThatOverflow_throwsArithmeticException(){
        //GIVEN array of large numbers that sum greater than integer.MAX_VALUE
        int [] values = {Integer.MAX_VALUE - 5,8,7};
        KenzieMath kenzieMath = new KenzieMath();
        //WHEN attempt to compute
        //THEN throws exception

        assertThrows(ArithmeticException.class,
                () -> kenzieMath.average(values),
                "Expected ArithmeticException when attempting to average large numbers that overflow.");
    }

}
