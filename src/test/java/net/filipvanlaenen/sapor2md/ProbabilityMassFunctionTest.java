package net.filipvanlaenen.sapor2md;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on <code>ProbabilityMassFunction</code>.
 */
public class ProbabilityMassFunctionTest {
    /**
     * The magic number one tenth.
     */
    private static final double ONE_TENTH = 0.1D;
    /**
     * The magic number one eighth.
     */
    private static final double ONE_EIGHTH = 0.125D;
    /**
     * The magic number one fifth.
     */
    private static final double ONE_FIFTH = 0.2D;
    /**
     * The magic number one quarter.
     */
    private static final double ONE_QUARTER = 0.25D;
    /**
     * The magic number three eighths.
     */
    private static final double THREE_EIGHTHS = 0.375D;
    /**
     * The magic number two fifths.
     */
    private static final double TWO_FIFTHS = 0.4D;
    /**
     * The magic number a half.
     */
    private static final double A_HALF = 0.5D;
    /**
     * The magic number three quarters.
     */
    private static final double THREE_QUARTERS = 0.75D;
    /**
     * The magic number three.
     */
    private static final int THREE = 3;
    /**
     * The magic number minus four.
     */
    private static final int MINUS_FOUR = -4;

    /**
     * Test verifying that the constructor throws an
     * <code>IllegalArgumentException</code> if the number of arguments is odd.
     */
    @Test
    void constructorShouldThrowIllegalArgumentExceptionIfNoOfArgumentsIsOdd() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ProbabilityMassFunction<Integer>(0);
        });
    }

    /**
     * Test verifying that the constructor throws an
     * <code>IllegalArgumentException</code> if one of the even arguments isn't a
     * number.
     */
    @Test
    void constructorShouldThrowIllegalArgumentExceptionIfOneOfTheEvenArgumentsIsNotANumber() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ProbabilityMassFunction<Integer>(0, "foo");
        });
    }

    /**
     * Test verifying that the constructor puts the arguments correctly in the map,
     * and the getter can fetch one of the probabilities correctly.
     */
    @Test
    void constructorMapsArgumentsCorrectlyToItsInternalMap() {
        ProbabilityMassFunction<Integer> pmf = new ProbabilityMassFunction<Integer>(0, ONE_QUARTER, 1, THREE_QUARTERS);
        assertEquals(ONE_QUARTER, pmf.getProbability(0));
    }

    /**
     * Test verifying that if one key has a 100% probability, it is returned as the
     * median.
     */
    @Test
    void keyWithHundredPercentProbabilityIsMedian() {
        ProbabilityMassFunction<Integer> pmf = new ProbabilityMassFunction<Integer>(0, 1D);
        assertEquals(0, pmf.getMedian());
    }

    /**
     * Test verifying that if the first key has 25%, and the second key 75%
     * probability, the second key is the median.
     */
    @Test
    void keyWithSixtyPercentProbabilityIsMedian() {
        ProbabilityMassFunction<Integer> pmf = new ProbabilityMassFunction<Integer>(0, ONE_QUARTER, 1, THREE_QUARTERS);
        assertEquals(1, pmf.getMedian());
    }

    /**
     * Test verifying that if the first key has 40%, and the second key 20%, and the
     * third key 40% probability, the second key is the median.
     */
    @Test
    void keyWithSurpassingFiftyPercentProbabilityIsMedian() {
        ProbabilityMassFunction<Integer> pmf = new ProbabilityMassFunction<Integer>(0, TWO_FIFTHS, 1, ONE_FIFTH, 2,
                TWO_FIFTHS);
        assertEquals(1, pmf.getMedian());
    }

    /**
     * Test verifying that if the first key has 40%, and the second key 10%, and the
     * third key 50% probability, the second key is the median.
     */
    @Test
    void keyAtFiftyPercentProbabilityIsMedian() {
        ProbabilityMassFunction<Integer> pmf = new ProbabilityMassFunction<Integer>(0, TWO_FIFTHS, 1, ONE_TENTH, 2,
                A_HALF);
        assertEquals(1, pmf.getMedian());
    }

    /**
     * Test verifying that if the first key has 40%, and the second key 20%, and the
     * third key 40% probability, the second key is the median, but only after
     * sorting the keys according to their natural order.
     */
    @Test
    void keyWithSurpassingFiftyPercentProbabilityAfterSortingIsMedian() {
        ProbabilityMassFunction<Integer> pmf = new ProbabilityMassFunction<Integer>(2, TWO_FIFTHS, 1, ONE_FIFTH,
                MINUS_FOUR, TWO_FIFTHS);
        assertEquals(1, pmf.getMedian());
    }

    /**
     * Test verifying the calculation of the lower bound of the confidence interval.
     */
    @Test
    void lowerBoundOfConfidenceIntervalIsCalculatedCorrectly() {
        ProbabilityMassFunction<Integer> pmf = new ProbabilityMassFunction<Integer>(0, ONE_EIGHTH, 1, THREE_EIGHTHS, 2,
                THREE_EIGHTHS, THREE, ONE_EIGHTH);
        assertEquals(1, pmf.getConfidenceInterval(THREE_QUARTERS).getLowerBound());
    }

    /**
     * Test verifying the calculation of the upper bound of the confidence interval.
     */
    @Test
    void upperBoundOfConfidenceIntervalIsCalculatedCorrectly() {
        ProbabilityMassFunction<Integer> pmf = new ProbabilityMassFunction<Integer>(0, ONE_EIGHTH, 1, THREE_EIGHTHS, 2,
                THREE_EIGHTHS, THREE, ONE_EIGHTH);
        assertEquals(2, pmf.getConfidenceInterval(THREE_QUARTERS).getUpperBound());
    }
}
