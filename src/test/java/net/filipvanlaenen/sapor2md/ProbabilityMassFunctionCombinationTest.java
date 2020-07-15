package net.filipvanlaenen.sapor2md;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>ProbabilityMassFunctionCombination</code> class.
 */
public class ProbabilityMassFunctionCombinationTest {

    /**
     * The name of the first group.
     */
    private static final String GROUP1 = "Group 1";
    /**
     * The name of the second group.
     */
    private static final String GROUP2 = "Group 2";
    /**
     * The magic number one quarter.
     */
    private static final double ONE_QUARTER = 0.25D;
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
     * The third probability range, ranging from 0.1% to 0.15%.
     */
    private static final ProbabilityRange THIRD_PROBABILITY_RANGE = new ProbabilityRange(0.001D, 0.0015D);

    /**
     * Test verifying that a group will be sorted before another group if the median
     * is higher.
     */
    @Test
    void groupWithHigherMedianComesFirst() {
        VotingIntentions votingIntentions = new VotingIntentions(GROUP1,
                new ProbabilityMassFunction<Integer>(FIRST_PROBABILITY_RANGE, 0D, SECOND_PROBABILITY_RANGE, 1D), GROUP2,
                new ProbabilityMassFunction<Integer>(FIRST_PROBABILITY_RANGE, 1D, SECOND_PROBABILITY_RANGE, 0D));
        assertEquals(-1, votingIntentions.compareGroups(GROUP1, GROUP2));
    }

    /**
     * Test verifying that when the medians are equal, the group with the higher
     * upper bound of the 95 percent confidence interval comes first.
     */
    @Test
    void groupWithHigherUpperBoundComesFirstWhenMediansAreEqual() {
        VotingIntentions votingIntentions = new VotingIntentions(GROUP1,
                new ProbabilityMassFunction<Integer>(FIRST_PROBABILITY_RANGE, 0D, SECOND_PROBABILITY_RANGE,
                        THREE_QUARTERS, THIRD_PROBABILITY_RANGE, ONE_QUARTER),
                GROUP2, new ProbabilityMassFunction<Integer>(FIRST_PROBABILITY_RANGE, ONE_QUARTER,
                        SECOND_PROBABILITY_RANGE, THREE_QUARTERS));
        assertEquals(-1, votingIntentions.compareGroups(GROUP1, GROUP2));
    }

    /**
     * Test verifying that when the medians and the upper bounds of the 95 percent
     * confidence intervals are equal, the group with the higher lower bound of the
     * 95 percent confidence interval comes first.
     */
    @Test
    void groupWithHigherLowerBoundComesFirstWhenMediansAndUpperBoundsAreEqual() {
        VotingIntentions votingIntentions = new VotingIntentions(GROUP1,
                new ProbabilityMassFunction<Integer>(FIRST_PROBABILITY_RANGE, 0D, SECOND_PROBABILITY_RANGE, 1D), GROUP2,
                new ProbabilityMassFunction<Integer>(FIRST_PROBABILITY_RANGE, ONE_QUARTER, SECOND_PROBABILITY_RANGE,
                        THREE_QUARTERS));
        assertEquals(-1, votingIntentions.compareGroups(GROUP1, GROUP2));
    }

    /**
     * Test verifying that when the medians and the bounds of the 95 percent
     * confidence intervals are equal, the group with the name that comes first in
     * the alphabet comes first.
     */
    @Test
    void groupWithLowestNameInAlphabetComesFirstWhenMediansAndBoundsAreEqual() {
        VotingIntentions votingIntentions = new VotingIntentions(GROUP1,
                new ProbabilityMassFunction<Integer>(FIRST_PROBABILITY_RANGE, ONE_QUARTER, SECOND_PROBABILITY_RANGE,
                        THREE_QUARTERS),
                GROUP2, new ProbabilityMassFunction<Integer>(FIRST_PROBABILITY_RANGE, ONE_QUARTER,
                        SECOND_PROBABILITY_RANGE, THREE_QUARTERS));
        assertEquals(-1, votingIntentions.compareGroups(GROUP1, GROUP2));
    }
}
