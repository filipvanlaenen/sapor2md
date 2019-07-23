package net.filipvanlaenen.sapor2md;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>SeatProjection</code> class.
 */
public class SeatProjectionTest {

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
                new ProbabilityMassFunction<Integer>(0, 0.3D, 1, 0.4D, 2, 0.3D), "Blue Party",
                new ProbabilityMassFunction<Integer>(0, 0.4D, 1, 0.6D));
        assertEquals(0.3D, seatProjection.getProbability("Red Party", 0));
    }

    /**
     * Test verifying that the correct median is returned for a parliamentary group.
     */
    @Test
    void getMedianShouldReturnTheMedianForTheGroup() {
        SeatProjection seatProjection = new SeatProjection("Red Party",
                new ProbabilityMassFunction<Integer>(0, 0.3D, 1, 0.4D, 2, 0.3D), "Blue Party",
                new ProbabilityMassFunction<Integer>(0, 0.6D, 1, 0.4D));
        assertEquals(1, seatProjection.getMedian("Red Party"));
    }

    /**
     * Test verifying that if the medians of all the groups add up to the total
     * number of seats, the adjusted median is the same as the regular median.
     */
    @Test
    void getAdjustedMedianReturnsTheMedianForTheGroupIfTheMediansAddUp() {
        SeatProjection seatProjection = new SeatProjection("Red Party",
                new ProbabilityMassFunction<Integer>(2, 0.6D, 3, 0.4D), "Blue Party",
                new ProbabilityMassFunction<Integer>(0, 0.4D, 1, 0.6D));
        assertEquals(2, seatProjection.getAdjustedMedian("Red Party", 3));
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
}
