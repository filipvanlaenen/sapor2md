package net.filipvanlaenen.sapor2md;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>SeatProjection</code> class.
 */
public class SeatProjectionTest {
    /**
     * The magic number one quarter.
     */
    private static final double ONE_QUARTER = 0.25D;
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
     * Test verifying that the constructor throws an
     * <code>IllegalArgumentException</code> if the number of arguments is odd.
     */
    @Test
    void constructorShouldThrowIllegalArgumentExceptionIfNoOfArgumentsIsOdd() {
        assertThrows(IllegalArgumentException.class, () -> {
            new SeatProjection("Red Party");
        });
    }

    /**
     * Test verifying that the constructor throws an
     * <code>IllegalArgumentException</code> if one of the uneven arguments isn't a
     * string.
     */
    @Test
    void constructorShouldThrowIllegalArgumentExceptionIfOneOfTheUnevenArgumentsIsNotAString() {
        assertThrows(IllegalArgumentException.class, () -> {
            new SeatProjection(1, "Blue Party");
        });
    }

    /**
     * Test verifying that the constructor throws an
     * <code>IllegalArgumentException</code> if one of the even arguments isn't a
     * probability mass function.
     */
    @Test
    void constructorShouldThrowIllegalArgumentExceptionIfOneOfTheEvenArgumentsIsNotAProbabilityMassFunction() {
        assertThrows(IllegalArgumentException.class, () -> {
            new SeatProjection("Red Party", "Blue Party");
        });
    }

    /**
     * Test verifying that the constructor sets the probability mass functions of
     * the groups correctly.
     */
    @Test
    void constructorWiresProbabilityMassFunctionsCorrectlyToGroups() {
        SeatProjection seatProjection = new SeatProjection("Red Party",
                new ProbabilityMassFunction<Integer>(0, ONE_QUARTER, 1, THREE_QUARTERS), "Blue Party",
                new ProbabilityMassFunction<Integer>(0, A_HALF, 1, A_HALF));
        assertEquals(ONE_QUARTER, seatProjection.getProbability("Red Party", 0));
    }

    /**
     * Test verifying that getGroups returns the groups correctly.
     */
    @Test
    void getGroupsReturnsAllGroups() {
        SeatProjection seatProjection = new SeatProjection("Red Party",
                new ProbabilityMassFunction<Integer>(0, ONE_QUARTER, 1, THREE_QUARTERS), "Blue Party",
                new ProbabilityMassFunction<Integer>(0, A_HALF, 1, A_HALF));
        Set<String> actual = seatProjection.getGroups();
        Set<String> expected = new HashSet<String>();
        expected.add("Red Party");
        expected.add("Blue Party");
        assertEquals(expected, actual);
    }

    /**
     * Test verifying that the correct median is returned for a parliamentary group.
     */
    @Test
    void getMedianShouldReturnTheMedianForTheGroup() {
        SeatProjection seatProjection = new SeatProjection("Red Party",
                new ProbabilityMassFunction<Integer>(0, ONE_QUARTER, 1, THREE_QUARTERS), "Blue Party",
                new ProbabilityMassFunction<Integer>(0, A_HALF, 1, A_HALF));
        assertEquals(1, seatProjection.getMedian("Red Party"));
    }

    /**
     * Test verifying that if the medians of all the groups add up to the total
     * number of seats, the adjusted median is the same as the regular median.
     */
    @Test
    void getAdjustedMedianReturnsTheMedianForTheGroupIfTheMediansAddUp() {
        SeatProjection seatProjection = new SeatProjection("Red Party",
                new ProbabilityMassFunction<Integer>(2, THREE_QUARTERS, THREE, ONE_QUARTER), "Blue Party",
                new ProbabilityMassFunction<Integer>(0, ONE_QUARTER, 1, THREE_QUARTERS));
        assertEquals(2, seatProjection.getAdjustedMedian("Red Party", THREE));
    }

    /**
     * Test verifying that if extra seats need to be allocated, a number with
     * increased probability is picked if possible.
     */
    @Test
    void getAdjustedMedianReturnsAnNumberWithIncreasedProbability() {
        SeatProjection seatProjection = new SeatProjection("Red Party",
                new ProbabilityMassFunction<Integer>(0, 0.4D, 1, 0.2D, 2, 0.4D), "Blue Party",
                new ProbabilityMassFunction<Integer>(0, 0.65D, 1, 0.35D), "Green Party",
                new ProbabilityMassFunction<Integer>(0, 0.75D, 1, 0.25D));
        assertEquals(2, seatProjection.getAdjustedMedian("Red Party", 2));
    }

    /**
     * Test verifying that if extra seats need to be allocated, and the probability
     * cannot be increased, at least the probability is maximized.
     */
    @Test
    void getAdjustedMedianReturnsAnNumberWithHighestProbability() {
        SeatProjection seatProjection = new SeatProjection("Red Party",
                new ProbabilityMassFunction<Integer>(0, 0.4D, 1, 0.35D, 2, 0.25D), "Blue Party",
                new ProbabilityMassFunction<Integer>(0, 0.65D, 1, 0.35D), "Green Party",
                new ProbabilityMassFunction<Integer>(0, 0.75D, 1, 0.25D));
        assertEquals(2, seatProjection.getAdjustedMedian("Red Party", 2));
    }
}
