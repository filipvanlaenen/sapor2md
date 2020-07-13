package net.filipvanlaenen.sapor2md;

public class ProbabilityRange implements Comparable<ProbabilityRange> {

    /**
     * The lower bound of the probability range.
     */
    private final Double lowerBound;
    /**
     * The upper bound of the probability range.
     */
    private final Double upperBound;

    ProbabilityRange(Double lowerBound, Double upperBound) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    @Override
    public int compareTo(ProbabilityRange other) {
        if (other.getLowerBound() > lowerBound) {
            return -1;
        } else if (other.getLowerBound() < lowerBound) {
            return 1;
        } else {
            return 0;
        }
    }

    Double getLowerBound() {
        return lowerBound;
    }

    Double getUpperBound() {
        return upperBound;
    }

}
