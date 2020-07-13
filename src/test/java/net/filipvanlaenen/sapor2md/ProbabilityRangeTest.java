package net.filipvanlaenen.sapor2md;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ProbabilityRangeTest {
    /**
     * Test verifying that the constructor sets the lower bound correctly.
     */
    @Test
    void constructorWiresLowerBoundCorrectly() {
        ProbabilityRange pr = new ProbabilityRange(0.0005D, 0.0010D);
        assertEquals(0.0005D, pr.getLowerBound());
    }

    /**
     * Test verifying that the constructor sets the upper bound correctly.
     */
    @Test
    void constructorWiresUpperBoundCorrectly() {
        ProbabilityRange pr = new ProbabilityRange(0.0005D, 0.0010D);
        assertEquals(0.0010D, pr.getUpperBound());
    }

    /**
     * Test verifying that a probability range compared to itself returns 0.
     */
    @Test
    void compareToReturnsZeroOnItself() {
        ProbabilityRange pr = new ProbabilityRange(0.0005D, 0.0010D);
        assertEquals(0, pr.compareTo(pr));
    }

    /**
     * Test verifying that a probability range compared to an another range with the
     * same lower bound returns 0.
     */
    @Test
    void compareToReturnsZeroWhenComparedToAnotherRangeHavingTheSameLowerBound() {
        ProbabilityRange pr1 = new ProbabilityRange(0.0005D, 0.0010D);
        ProbabilityRange pr2 = new ProbabilityRange(0.0005D, 0.0010D);
        assertEquals(0, pr1.compareTo(pr2));
    }

    /**
     * Test verifying that a probability range compared to an another range with a
     * higher lower bound returns -1.
     */
    @Test
    void compareToReturnsMinusOneWhenComparedToAnotherRangeHavingAHigherLowerBound() {
        ProbabilityRange pr1 = new ProbabilityRange(0.0005D, 0.0010D);
        ProbabilityRange pr2 = new ProbabilityRange(0.0010D, 0.0015D);
        assertEquals(-1, pr1.compareTo(pr2));
    }

    /**
     * Test verifying that a probability range compared to an another range with a
     * lower lower bound returns -1.
     */
    @Test
    void compareToReturnsMinusOneWhenComparedToAnotherRangeHavingALowerLowerBound() {
        ProbabilityRange pr1 = new ProbabilityRange(0.0010D, 0.0015D);
        ProbabilityRange pr2 = new ProbabilityRange(0.0005D, 0.0010D);
        assertEquals(1, pr1.compareTo(pr2));
    }
}
