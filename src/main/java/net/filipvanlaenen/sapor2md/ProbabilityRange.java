package net.filipvanlaenen.sapor2md;

/**
 * Class implementing a probability range.
 */
public final class ProbabilityRange implements Comparable<ProbabilityRange> {

    /**
     * The lower bound of the probability range.
     */
    private final Double lowerBound;
    /**
     * The upper bound of the probability range.
     */
    private final Double upperBound;

    /**
     * Constructor taking the lower and the upper bound of the probability range as
     * its parameters.
     *
     * @param lowerBound
     *            The lower bound of the probability range.
     * @param upperBound
     *            The upper bound of the probability range.
     */
    ProbabilityRange(final Double lowerBound, final Double upperBound) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    @Override
    public int compareTo(final ProbabilityRange other) {
        if (other.getLowerBound() > lowerBound) {
            return -1;
        } else if (other.getLowerBound() < lowerBound) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * Returns the lower bound of the probability range.
     *
     * @return The lower bound of the probability range.
     */
    Double getLowerBound() {
        return lowerBound;
    }

    /**
     * Returns the upper bound of the probability range.
     *
     * @return The upper bound of the probability range.
     */
    Double getUpperBound() {
        return upperBound;
    }

}
