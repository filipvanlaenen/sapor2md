package net.filipvanlaenen.sapor2md;

import java.util.ArrayList;
import java.util.List;

public class VotingIntentionsTestServices {
    /**
     * Magic number 0.95, or 95 percent.
     */
    private static final double NINETY_FIVE_PERCENT = 0.95D;
    /**
     * Magic number 2000.
     */
    private static final double TWO_THOUSAND = 2000D;

    /**
     * Creates a probability mass function for the voting intentions such that the
     * 95 percent confidence interval is as specified.
     *
     * @param lowerBound The lower bound of the 95 percent confidence interval.
     * @param upperBound The lower bound of the 95 percent confidence interval.
     * @return A probability mass function having the 95 percent confidence interval
     *         as specified.
     */
    static ProbabilityMassFunction<ProbabilityRange> createProbabilityMassFunctionForConfidenceInterval(
            final double lowerBound, final double upperBound) {
        int noBelow = (int) (lowerBound * TWO_THOUSAND);
        List<Object> args = new ArrayList<Object>();
        for (int i = 0; i < noBelow; i++) {
            args.add(new ProbabilityRange(((double) i) / TWO_THOUSAND, ((double) (i + 1)) / TWO_THOUSAND));
            args.add(0D);
        }
        int noBetween = (int) ((upperBound - lowerBound) * TWO_THOUSAND);
        double p = NINETY_FIVE_PERCENT / noBetween;
        for (int i = 0; i < noBetween; i++) {
            args.add(new ProbabilityRange(((double) (noBelow + i)) / TWO_THOUSAND,
                    ((double) (noBelow + i + 1)) / TWO_THOUSAND));
            if (i == 0 || i == noBetween - 1) {
                args.add(p + (1D - NINETY_FIVE_PERCENT) / 2D);
            } else {
                args.add(p);
            }
        }
        return new ProbabilityMassFunction<ProbabilityRange>(args.toArray());
    }
}
