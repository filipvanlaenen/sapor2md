package net.filipvanlaenen.sapor2md;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>ConfidenceInterval</code> class.
 */
public class ConfidenceIntervalTest {
    /**
     * Test verifying that the constructor sets the lower bound correctly.
     */
    @Test
    void constructorWiresLowerBoundCorrectly() {
        ConfidenceInterval<Integer> ci = new ConfidenceInterval<Integer>(0, 1);
        assertEquals(0, ci.getLowerBound());
    }

    /**
     * Test verifying that the constructor sets the upper bound correctly.
     */
    @Test
    void constructorWiresUpperBoundCorrectly() {
        ConfidenceInterval<Integer> ci = new ConfidenceInterval<Integer>(0, 1);
        assertEquals(1, ci.getUpperBound());
    }
}
