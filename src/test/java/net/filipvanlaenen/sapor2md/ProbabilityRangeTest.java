package net.filipvanlaenen.sapor2md;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>ProbabilityRange</code> class.
 */
public class ProbabilityRangeTest {
    /**
     * Lower number for the tests.
     */
    private static final double LOWER_NUMBER = 0.0005D;
    /**
     * Middle number for the tests.
     */
    private static final double MIDDLE_NUMBER = 0.0010D;
    /**
     * Upper number for the tests.
     */
    private static final double UPPER_NUMBER = 0.0015D;

    /**
     * Test verifying that the constructor sets the lower bound correctly.
     */
    @Test
    void constructorWiresLowerBoundCorrectly() {
        ProbabilityRange pr = new ProbabilityRange(LOWER_NUMBER, MIDDLE_NUMBER);
        assertEquals(LOWER_NUMBER, pr.getLowerBound());
    }

    /**
     * Test verifying that the constructor sets the upper bound correctly.
     */
    @Test
    void constructorWiresUpperBoundCorrectly() {
        ProbabilityRange pr = new ProbabilityRange(LOWER_NUMBER, MIDDLE_NUMBER);
        assertEquals(MIDDLE_NUMBER, pr.getUpperBound());
    }

    /**
     * Test verifying that a probability range compared to itself returns 0.
     */
    @Test
    void compareToReturnsZeroOnItself() {
        ProbabilityRange pr = new ProbabilityRange(LOWER_NUMBER, MIDDLE_NUMBER);
        assertEquals(0, pr.compareTo(pr));
    }

    /**
     * Test verifying that a probability range compared to an another range with the
     * same lower bound returns 0.
     */
    @Test
    void compareToReturnsZeroWhenComparedToAnotherRangeHavingTheSameLowerBound() {
        ProbabilityRange pr1 = new ProbabilityRange(LOWER_NUMBER, MIDDLE_NUMBER);
        ProbabilityRange pr2 = new ProbabilityRange(LOWER_NUMBER, MIDDLE_NUMBER);
        assertEquals(0, pr1.compareTo(pr2));
    }

    /**
     * Test verifying that a probability range compared to an another range with a
     * higher lower bound returns -1.
     */
    @Test
    void compareToReturnsMinusOneWhenComparedToAnotherRangeHavingAHigherLowerBound() {
        ProbabilityRange pr1 = new ProbabilityRange(LOWER_NUMBER, MIDDLE_NUMBER);
        ProbabilityRange pr2 = new ProbabilityRange(MIDDLE_NUMBER, UPPER_NUMBER);
        assertEquals(-1, pr1.compareTo(pr2));
    }

    /**
     * Test verifying that a probability range compared to an another range with a
     * lower lower bound returns -1.
     */
    @Test
    void compareToReturnsMinusOneWhenComparedToAnotherRangeHavingALowerLowerBound() {
        ProbabilityRange pr1 = new ProbabilityRange(MIDDLE_NUMBER, UPPER_NUMBER);
        ProbabilityRange pr2 = new ProbabilityRange(LOWER_NUMBER, MIDDLE_NUMBER);
        assertEquals(1, pr1.compareTo(pr2));
    }
}
