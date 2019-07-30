package net.filipvanlaenen.sapor2md;

/**
 * A value object class representing a confidence interval. A confidence
 * interval has a lower bound and an upper bound.
 *
 * @param <T>
 *            The domain type for the confidence interval.
 */
public class ConfidenceInterval<T> {

    /**
     * The lower bound of the confidence interval.
     */
    private final T lowerBound;
    /**
     * The upper bound of the confidence interval.
     */
    private final T upperBound;

    /**
     * Creates a confidence interval with the provided lower and upper bound.
     *
     * @param lowerBound
     *            The lower bound of the confidence interval.
     * @param upperBound
     *            The upper bound of the confidence interval.
     */
    ConfidenceInterval(final T lowerBound, final T upperBound) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    /**
     * Returns the lower bound of the confidence interval.
     *
     * @return The lower bound of the confidence interval.
     */
    T getLowerBound() {
        return lowerBound;
    }

    /**
     * Returns the upper bound of the confidence interval.
     *
     * @return The upper bound of the confidence interval.
     */
    T getUpperBound() {
        return upperBound;
    }

}
