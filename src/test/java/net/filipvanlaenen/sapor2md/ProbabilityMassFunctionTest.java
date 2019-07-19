package net.filipvanlaenen.sapor2md;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on <code>ProbabilityMassFunction</code>.
 */
public class ProbabilityMassFunctionTest {
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
        ProbabilityMassFunction<Integer> pmf = new ProbabilityMassFunction<Integer>(0, 0.3, 1, 0.4, 2, 0.3);
        assertEquals(0.3, pmf.getProbability(0));
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
     * Test verifying that if the first key has 40%, and the second key 60%
     * probability, the second key is the median.
     */
    @Test
    void keyWithSixtyPercentProbabilityIsMedian() {
        ProbabilityMassFunction<Integer> pmf = new ProbabilityMassFunction<Integer>(0, 0.4D, 1, 0.6D);
        assertEquals(1, pmf.getMedian());
    }

    /**
     * Test verifying that if the first key has 40%, and the second key 20%, and the
     * third key 40% probability, the second key is the median.
     */
    @Test
    void keyWithSurpassingFiftyPercentProbabilityIsMedian() {
        ProbabilityMassFunction<Integer> pmf = new ProbabilityMassFunction<Integer>(0, 0.4D, 1, 0.2D, 2, 0.4D);
        assertEquals(1, pmf.getMedian());
    }
}
