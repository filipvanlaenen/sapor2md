package net.filipvanlaenen.sapor2md;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>VotingIntentions</code> class.
 */
public class VotingIntentionsTest {
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
     * The first probability range, ranging from 0% to 0.05%.
     */
    private static final ProbabilityRange FIRST_PROBABILITY_RANGE = new ProbabilityRange(0D, 0.0005D);
    /**
     * The second probability range, ranging from 0.05% to 0.1%.
     */
    private static final ProbabilityRange SECOND_PROBABILITY_RANGE = new ProbabilityRange(0.0005D, 0.001D);

    /**
     * Test verifying that the constructor throws an
     * <code>IllegalArgumentException</code> if the number of arguments is odd.
     */
    @Test
    void constructorShouldThrowIllegalArgumentExceptionIfNoOfArgumentsIsOdd() {
        assertThrows(IllegalArgumentException.class, () -> {
            new VotingIntentions("Red Party");
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
            new VotingIntentions(1, "Blue Party");
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
            new VotingIntentions("Red Party", "Blue Party");
        });
    }

    /**
     * Test verifying that the constructor sets the probability mass functions of
     * the groups correctly.
     */
    @Test
    void constructorWiresProbabilityMassFunctionsCorrectlyToGroups() {
        VotingIntentions votingIntentions = new VotingIntentions("Red Party",
                new ProbabilityMassFunction<Integer>(FIRST_PROBABILITY_RANGE, ONE_QUARTER, SECOND_PROBABILITY_RANGE,
                        THREE_QUARTERS),
                "Blue Party", new ProbabilityMassFunction<Integer>(FIRST_PROBABILITY_RANGE, A_HALF,
                        SECOND_PROBABILITY_RANGE, A_HALF));
        assertEquals(ONE_QUARTER, votingIntentions.getProbability("Red Party", FIRST_PROBABILITY_RANGE));
    }
}
